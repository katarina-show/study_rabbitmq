package sjw.producerconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送方确认模式  的消费者
 */
public class ProducerConfirmConsumer {

    private static final String EXCHANGE_NAME = "producer_confirm";

    public static void main(String[] argv) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = "producer_confirm";
        channel.queueDeclare(queueName,false,false,
                false,null);

        String severity="error";
        channel.queueBind(queueName, EXCHANGE_NAME, severity);

        System.out.println(" [*] Waiting for messages......");

        // 创建消费者
        final Consumer consumerB = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println( "Received ["+ envelope.getRoutingKey() + "] "+message);
            }
        };
        channel.basicConsume(queueName, true, consumerB);
    }

}
