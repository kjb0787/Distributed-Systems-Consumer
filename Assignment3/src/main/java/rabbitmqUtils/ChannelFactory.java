package rabbitmqUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ChannelFactory extends BasePooledObjectFactory<Channel> {

  @Override
  public Channel create() throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("3.233.66.173");
    factory.setUsername("6650user");
    factory.setPassword("6650password");
    Connection connection = factory.newConnection();
    return connection.createChannel();
  }

  @Override
  public PooledObject<Channel> wrap(Channel channel) {
    return new DefaultPooledObject<>(channel);
  }
}
