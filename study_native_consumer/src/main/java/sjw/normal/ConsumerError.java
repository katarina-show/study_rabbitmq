package sjw.normal;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 只处理error级别的日志
 * 和ConsumerAll一起启动
 */
public class ConsumerError {

    //private static final String EXCHANGE_NAME = "direct_logs";
    private static final String EXCHANGE_NAME = "fanout_logs";

    public static void main(String[] argv) throws IOException, TimeoutException {

        //这边和生产者一样的
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);


        //创建1个随机队列，不用手动取名字
        String queueName = channel.queueDeclare().getQueue();

        String server = "error";

        //如果采用fanout，会忽略路由键参数，即第三个参数
        //队列和交换器的绑定
        channel.queueBind(queueName,EXCHANGE_NAME,server);

        System.out.println("Waiting message.......");

        //创建1个消费者
        Consumer consumerB = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("Accept路由键为:"+envelope.getRoutingKey()+"的信息:"+message);
            }
        };

        //自动确认
        channel.basicConsume(queueName,true,consumerB);
    }

}
