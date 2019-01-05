package sjw.consumerconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 手动相应消息
 */
public class ClientConsumerAck {

    private static final String EXCHANGE_NAME = "direct_cc_confirm";

    public static void main(String[] argv) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();//连接
        Channel channel = connection.createChannel();//信道
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);//交换器

        //声明队列，指定队列具体名
        String queueName = "consumer_confirm";

        //参数2 队列是否持久化（默认false，和spring整合时，默认是true）
        //参数3 是否独占
        //参数4 是否自动删除（最后一个消费者取消订阅时，队列是否会自动删除）
        //参数5 其他参数
        channel.queueDeclare(queueName,false,false,
                false,null);

        String server = "error";
        channel.queueBind(queueName,EXCHANGE_NAME,server);
        System.out.println("Waiting message.......");

        Consumer consumerB = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body,"UTF-8");
                System.out.println("Accept:"+envelope.getRoutingKey()+":"+message);
                //自行确认的方法basicAck，basicConsume第二个参数必须设置为false
                //参数1 标志符，一般都是从envelope中取得
                //参数2 是否批量回复
                this.getChannel().basicAck(envelope.getDeliveryTag(),false);
            }
        };

        //手动响应
        channel.basicConsume(queueName,false,consumerB);
    }

}
