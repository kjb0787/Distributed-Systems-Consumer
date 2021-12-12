package dal;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionPoolFactory extends BasePooledObjectFactory<Connection> {

  private static final String HOST_NAME = "database-1-instance-1.c4awa04yo8tz.us-east-1.rds.amazonaws.com";
  private static final String PORT = "3306";
  private static final String DATABASE = "LiftRides";
  private static final String USERNAME = "admin";
  private static final String PASSWORD = "6650password";

  @Override
  public Connection create() throws Exception {
    Connection connection;
    try {
      Properties connectionProperties = new Properties();
      try {
        Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException(e);
      }
      connection = DriverManager.getConnection("jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + DATABASE, USERNAME, PASSWORD);
    } catch (SQLException e) {
      e.printStackTrace();
      throw e;
    }
    return connection;
  }

  @Override
  public PooledObject<Connection> wrap(Connection connection) {
    return new DefaultPooledObject<>(connection);
  }
}
