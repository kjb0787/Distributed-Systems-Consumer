package dal;

import java.sql.ResultSet;

import model.LiftRide;

public class LiftRideDao {

  private static String paramParse(String... args) {
    return "(" + String.join(",", args) + ")";
  }

  public static boolean createLiftRide(LiftRide newLiftRide) {
    String vertical = "100";
    String query = "INSERT INTO LiftRides(SkierId, ResortId, SeasonId, DayId, Time, LiftId, Vertical) VALUES";
    query += paramParse(Integer.toString(newLiftRide.getSkierId()),
            Integer.toString(newLiftRide.getResortId()),
            Integer.toString(newLiftRide.getSeasonId()),
            Integer.toString(newLiftRide.getDayId()),
            Integer.toString(newLiftRide.getTime()),
            Integer.toString(newLiftRide.getLiftId()),
            vertical);
    query += " ON DUPLICATE KEY UPDATE Vertical = Vertical";
    // System.out.println(query);
    try (
            DBConnectionManager manager = new DBConnectionManager();
            ResultSet rs = manager.execWriteOnlyQuery(query);
    ) {
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }
}
