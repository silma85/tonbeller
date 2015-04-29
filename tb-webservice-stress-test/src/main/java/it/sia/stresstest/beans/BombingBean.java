package it.sia.stresstest.beans;

import lombok.Data;

@Data
public class BombingBean {

  private String wsUser;

  private String wsPwd;

  private long minTime = 999999999999999999L;

  private long maxTime;

  private long avgTime;

  private int worked = 0;

  private TestWebService serviceToTest;

  private String tag;

  synchronized void worked() {
    worked++;
  }

  synchronized void timeAvg(long milliseconds) {
    if (milliseconds < minTime)
      minTime = milliseconds;
    if (milliseconds > maxTime)
      maxTime = milliseconds;

    avgTime = (minTime + maxTime) / 2;
  }
}
