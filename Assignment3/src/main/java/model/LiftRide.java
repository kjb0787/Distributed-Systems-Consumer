package model;

public class LiftRide {
  private final int skierId;
  private final int resortId;
  private final int seasonId;
  private final int dayId;
  private final int time;
  private final int liftId;

  public LiftRide(int resortId, int seasonId, int dayId, int skierId, int time, int liftId) {
    this.resortId = resortId;
    this.seasonId = seasonId;
    this.dayId = dayId;
    this.skierId = skierId;
    this.time = time;
    this.liftId = liftId;
  }

  public int getSkierId() {
    return skierId;
  }

  public int getResortId() {
    return resortId;
  }

  public int getSeasonId() {
    return seasonId;
  }

  public int getDayId() {
    return dayId;
  }

  public int getTime() {
    return time;
  }

  public int getLiftId() {
    return liftId;
  }
}
