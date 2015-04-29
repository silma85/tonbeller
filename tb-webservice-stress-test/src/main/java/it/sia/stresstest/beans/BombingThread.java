package it.sia.stresstest.beans;

import org.apache.axis.utils.StringUtils;
import org.perf4j.StopWatch;
import org.perf4j.slf4j.Slf4JStopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BombingThread extends Thread {

  private final static Logger log = LoggerFactory.getLogger(BombingThread.class);

  private int threadID;

  private BombingBean bb;

  public BombingThread(final int threadID, final BombingBean bb) {
    this.threadID = threadID;
    this.bb = bb;
  }

  @Override
  public void run() {

    String result = null;
    final StopWatch stopWatch = new Slf4JStopWatch(bb.getTag());

    try {

      result = bb.getServiceToTest().start();
      stopWatch.stop();

      if (!StringUtils.isEmpty(result)) {
        bb.worked();
        bb.timeAvg(stopWatch.getElapsedTime());
      }

    } catch (Exception e) {
      log.error("Error in Bombing Thread!", e);
    } finally {
      log.info("ThreadID " + threadID + ", USER " + bb.getWsUser() + ", " +
          result + ". " + stopWatch.getElapsedTime() / 1000 + " seconds");
    }
  }
}
