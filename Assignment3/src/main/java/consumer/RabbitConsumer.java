package consumer;

import com.google.gson.Gson;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import dal.LiftRideDao;
import model.LiftRide;

public class RabbitConsumer {
  private final static String QUEUE_NAME = "threadExQ";
  private final static int NUM_THREADS = 20;
  private final static Gson gson = new Gson();
  private static AtomicInteger count = new AtomicInteger(0);

  public static void main(String[] argv) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("3.233.66.173");
    factory.setUsername("6650user");
    factory.setPassword("6650password");

    final Connection connection = factory.newConnection();
    Runnable runnable = () -> {
      try {
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        // max one message per receiver
        channel.basicQos(5);
        System.out.println(" [*] Thread waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
          String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
          channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
          // System.out.println( "Callback thread ID = " + Thread.currentThread().getId() + " Received '" + message + "'");
          LiftRide liftRide = gson.fromJson(message, LiftRide.class);
          LiftRideDao.createLiftRide(liftRide);

          if (count.incrementAndGet() % 1000 == 0) {
            System.out.println("count now: " + count.get());
          }
        };
        // process messages
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
      } catch (IOException ex) {
        Logger.getLogger(RabbitConsumer.class.getName()).log(Level.SEVERE, null, ex);
      }
    };
    // start threads and block to receive messages
    List<Thread> threads = new ArrayList<>();
    for (int i = 0; i < NUM_THREADS; ++i) {
      Thread receiveThread = new Thread(runnable);
      threads.add(receiveThread);
      receiveThread.start();
    }
    for (Thread t : threads) {
      t.join();
    }
  }
}
