package rabbitmqUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

public class Sender {
  private final static String QUEUE_NAME = "threadExQ";

    public static void send(String messageToSend) throws Exception {
      ChannelPool channelPool = ChannelPool.getInstance();
      Channel channel = null;
      try {
        channel = channelPool.borrowObject();
      } catch (NoSuchElementException e) {
        System.out.println("the pool is exhausted and cannot or will not return another instance.");
        e.printStackTrace();
      } catch (IllegalStateException e) {
        System.out.println("close has been called on pool.");
        e.printStackTrace();
      } catch (Exception e) {
        System.out.println("make object threw an exception");
        e.printStackTrace();
      }
      try {
        assert channel != null;
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicPublish("", QUEUE_NAME, null, messageToSend.getBytes(StandardCharsets.UTF_8));
      } catch (Exception e) {
        channelPool.invalidateObject(channel);
        channel = null;
      }
      if (channel != null) {
        channelPool.returnObject(channel);
      }
  }

  private static ConnectionFactory factory = null;

  private static ConnectionFactory getFactory() {
    if (factory == null) {
      factory = new ConnectionFactory();
      factory.setHost("3.233.66.173");
      factory.setUsername("6650user");
      factory.setPassword("6650password");
    }
    return factory;
  }
}
