package sjw.normal;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * Fanout交换机
 * 会忽略Routing key
 */
public class FanoutProducer {

    private final static String EXCHANGE_NAME = "fanout_logs";

    public static void main(String[] args) throws Exception{

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
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

        //定义日志输出级别
        String[] serverities = {"error","info","warning"};

        for(int i=0;i<3;i++){
            String server = serverities[i];
            String message = "Hello world_"+(i+1);

            channel.basicPublish(EXCHANGE_NAME,server,null,message.getBytes());
            System.out.println("Sent "+server+":"+message);

        }

        channel.close();
        connection.close();
    }

}
