package sjw.consumerconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 拒绝消息
 */
public class ClientConsumerReject {

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

        Consumer consumerB = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                //basicReject是拒绝消息的方法
                //参数1 标志符，一般都是从envelope中取得
                //参数2 是否消息重新入队，如果true，就会发给别的消费者，如果false，消息作废，从此消失
                //和ClientConsumerAck一起启动，然后发送，以查看结果
                this.getChannel().basicReject(envelope.getDeliveryTag(),true);
                System.out.println("拒绝了路由键为：:"+envelope.getRoutingKey()
                        +"的消息:"+new String(body,"UTF-8"));
            }
        };

        //要拒绝，也要设置手动回复
        channel.basicConsume(queueName,false,consumerB);
    }

}
