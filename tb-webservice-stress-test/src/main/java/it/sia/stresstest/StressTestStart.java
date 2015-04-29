package it.sia.stresstest;

import it.sia.stresstest.beans.BombingBean;
import it.sia.stresstest.beans.BombingThread;
import it.sia.stresstest.beans.TestWebService;

import java.util.ArrayList;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Call .start() to start a webservice stress test.
 */
public class StressTestStart {

  private final static Logger log = LoggerFactory.getLogger(StressTestStart.class);

  private final static String wsAddress = StressTestApplication.props.getProperty("wssearch.address");
  private final static String wsPort = StressTestApplication.props.getProperty("wssearch.port");

  private final static String wsUser = StressTestApplication.props.getProperty("wssearch.user");
  private final static String wsPwd = StressTestApplication.props.getProperty("wssearch.pwd");
  private final static String wsInstitute = StressTestApplication.props.getProperty("wssearch.institute");

  private final static int threadNumber = Integer.valueOf(StressTestApplication.props
      .getProperty("stresstest.threadNumber"));
  private final static int secondsToWait = Integer.valueOf(StressTestApplication.props
      .getProperty("stresstest.secondsToWait"));
  private final static int range = Integer.valueOf(StressTestApplication.props.getProperty("stresstest.range"));

  /**
   * How many tests I did in "range" minutes?
   */
  private static int iterations = 0;

  private static ArrayList<Double> mins = new ArrayList<Double>();
  private static ArrayList<Double> maxs = new ArrayList<Double>();
  private static ArrayList<Double> avgs = new ArrayList<Double>();

  private static Date start = null;
  private static Date end = null;

  public static void start(final TestWebService serviceToTest) {
    log.info("Test started with " + threadNumber + " thread(s). \n" +
        "The test will stay active for " + range + " minutes and will stress every " + secondsToWait + " seconds");

    start = new Date();

    try {
      do {

        Thread[] bts = new Thread[threadNumber];
        iterations++;

        BombingBean bb = new BombingBean();
        bb.setWsUser(wsUser);
        bb.setWsPwd(wsPwd);
        bb.setServiceToTest(serviceToTest);
        bb.setTag(serviceToTest.getClass().getName());

        for (int i = 1; i <= threadNumber; i++) {
          Thread bt = new BombingThread(i, bb);
          bts[i - 1] = bt;
          bt.start();

          Thread.sleep(200);
        }

        for (Thread bt : bts)
          bt.join();

        end = new Date();

        mins.add(bb.getMinTime() / 1000.00);
        maxs.add(bb.getMaxTime() / 1000.00);
        avgs.add(bb.getAvgTime() / 1000.00);

        log.info("\nCalls correctly worked: " + bb.getWorked() + " on " + threadNumber);
        log.info("Minimum time elapsed: " + mins.get(mins.size() - 1)); // get last inserted
        log.info("Maximum time elapsed: " + maxs.get(maxs.size() - 1));
        log.info("Average time: " + avgs.get(avgs.size() - 1) + "\n");

        // if the time elapsed is higher than the range we set the stress test has to stop
        if ((end.getTime() - start.getTime()) >= range * 60 * 1000)
          break;

        // wait for next iteration
        log.info("Sleeping for " + secondsToWait + " seconds");
        Thread.sleep(secondsToWait * 1000);
      } while (true);

      double minOfMins = 999999999999999999L;
      double maxOfMaxs = 0;
      double avgsSum = 0;

      for (double min : mins)
        if (min < minOfMins)
          minOfMins = min;

      for (double max : maxs)
        if (max > maxOfMaxs)
          maxOfMaxs = max;

      for (double avg : avgs)
        avgsSum += avg;

      log.info("\nMinimum of the minimums times: " + minOfMins);
      log.info("Maximum of the maximums times: " + maxOfMaxs);
      log.info("Average of the averages times: " + (avgsSum / iterations));

    } catch (InterruptedException e) {
      log.error("Error while working thread!", e);
    } catch (Exception e) {
      log.error("Generic error during program execution.", e);
    }
  }
}
