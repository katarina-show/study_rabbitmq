package sjw.consumerconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 较慢响应消息
 * 可以把该类和ClientConsumerAck同时启动，然后发送消息，接着把该类停止
 * 会发现余下的消息会转发送给ClientConsumerAck
 */
public class ClientConsumerSlowAck {

    private static final String EXCHANGE_NAME = "direct_cc_confirm";

    public static void main(String[] argv) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();//连接
        Channel channel = connection.createChannel();//信道
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);//交换器

        //声明队列
        String queueName = "consumer_confirm";
        channel.queueDeclare(queueName,false,false,
                false,null);

        String server = "error";
        channel.queueBind(queueName,EXCHANGE_NAME,server);
        System.out.println("Waiting message.......");

        //消费者收到消息和消费者确认消息是2个不同的意思
        Consumer consumerB = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                try {
                    //模拟响应慢
                    Thread.sleep(25000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String message = new String(body,"UTF-8");
                System.out.println("Accept:"+envelope.getRoutingKey()+":"+message);
                //this.getChannel().basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //设为手动响应，但是没有basicAck，即无响应
        channel.basicConsume(queueName,false,consumerB);
    }

}
