package sjw.consumerconfirm;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * 用于测试 消费者确认模式（手动、自动、拒绝等）的生产者
 */
public class ConsumerConfirmProducer {

    private final static String EXCHANGE_NAME = "direct_cc_confirm";
    private final static String ROUTE_KEY = "error";

    public static void main(String[] args) throws Exception {

        //创建工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        /*factory.setUsername(..);
        factory.setPort();
        factory.setVirtualHost();*/

        //创建连接
        Connection connection = factory.newConnection();

        //创建信道
        Channel channel = connection.createChannel();

        //声明交换器，参数分别是  交换器名和交换器类型，交换器类型可用BuiltinExchangeType.xxx也可直接写字符串
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        for(int i = 0; i < 10; i++){
            String message = "Hello world_"+(i+1);

            channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY,null,message.getBytes());
            System.out.println("Sent "+ROUTE_KEY+":"+message);

        }

        channel.close();
        connection.close();

    }

}
