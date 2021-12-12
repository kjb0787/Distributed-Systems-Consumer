package dal;

import org.apache.commons.pool2.impl.GenericObjectPool;

import java.sql.Connection;

public class DBPool {
  private static DBPool instance = null;
  private GenericObjectPool<Connection> pool = null;

  private static int MAX_OBJECTS = 30;
  private static boolean BLOCKED = true;
  private static int MAX_WAIT = 10;

  private DBPool(){
    pool = new GenericObjectPool<>(new DatabaseConnectionPoolFactory());
    pool.setMaxTotal(MAX_OBJECTS);
    pool.setBlockWhenExhausted(BLOCKED);
    pool.setMaxTotal(MAX_WAIT);
  }

  public static DBPool getInstance(){
    if(instance == null){
      instance = new DBPool();
    }
    return instance;
  }

  public Connection borrowObject() throws Exception {
    return pool.borrowObject();
  }

  public void returnObject(Connection connection) {
    pool.returnObject(connection);
  }
}
