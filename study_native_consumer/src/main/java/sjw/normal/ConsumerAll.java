package sjw.normal;

import com.rabbitmq.client.*;
import java.io.IOException;

/**
 * 消费所有类型的日志
 * 和ConsumerError一起启动
 */
public class ConsumerAll {

    //private static final String EXCHANGE_NAME = "direct_logs";
    private static final String EXCHANGE_NAME = "fanout_logs";

    public static void main(String[] argv) throws Exception {

        //这边和生产者一样的
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);


        //创建1个随机队列，不用手动取名字
        String queueName = channel.queueDeclare().getQueue();

        String[] serverities = {"error","info","warning"};

        for(String server:serverities){
            //队列和交换器的绑定
            //如果采用fanout，会忽略路由键参数，即第三个参数
            channel.queueBind(queueName,EXCHANGE_NAME,server);
            //System.out.println("已将队列名为：" + queueName + "  的队列和交换机：" + EXCHANGE_NAME + "  绑定");
        }


        System.out.println("Waiting message.......");

        //创建1个消费者
        Consumer consumerA = new DefaultConsumer(channel){
            //处理消息的逻辑
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                //消息实体转换为字符串
                String message = new String(body,"UTF-8");
                System.out.println("Accept路由键为:"+envelope.getRoutingKey()+"的信息:"+message);
            }
        };

        //第二个参数设置true表示自动应答
        channel.basicConsume(queueName,true,consumerA);

    }
}
