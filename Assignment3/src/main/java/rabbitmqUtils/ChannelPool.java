package rabbitmqUtils;

import com.rabbitmq.client.Channel;

import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ChannelPool {
  private static ChannelPool instance = null;
  private final GenericObjectPool<Channel> pool;

  private static int MAX_OBJS = 100;
  private static boolean BLOCKED = true;
  private static int WAIT_IN_S = 10;

  private ChannelPool() {
    pool = new GenericObjectPool<>(new ChannelFactory());
    pool.setMaxTotal(MAX_OBJS);
    pool.setBlockWhenExhausted(BLOCKED);
    pool.setMaxTotal(WAIT_IN_S * 1000);
  }

  public static ChannelPool getInstance() throws IOException, TimeoutException {
    if (instance == null) instance = new ChannelPool();
    return instance;
  }

  public Channel borrowObject() throws Exception {
    return pool.borrowObject();
  }

  public void returnObject(Channel channel) {
    pool.returnObject(channel);
  }

  public void invalidateObject(Channel channel) throws Exception {
    pool.invalidateObject(channel);
  }
}
