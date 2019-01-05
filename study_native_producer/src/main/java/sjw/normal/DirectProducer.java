package sjw.normal;

import com.rabbitmq.client.*;

/**
 * Direct交换机
 */
public class DirectProducer {

    private final static String EXCHANGE_NAME = "direct_logs";

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

        //声明交换器，参数分别是  交换器名、交换器类型、channel是否持久化（默认false）
        //需要注意的是和spring整合，spring配置文件中，channel和queue默认都是持久化的
        //交换器类型可用BuiltinExchangeType.xxx也可直接写字符串
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT,false);

        //队列由消费者创建

        //定义日志输出级别
        String[] serverities = {"error","info","warning"};

        for(int i = 0; i < 3; i++){
            String server = serverities[i];
            String message = "Hello world_"+(i+1);
            //发送消息的核心方法basicPublish
            //第三个参数可以进行很多额外配置，我们只关心消息的持久化配置，此处先不配置
            //channel.basicPublish(EXCHANGE_NAME,server,MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());

            channel.basicPublish(EXCHANGE_NAME,server,null,message.getBytes());
            System.out.println("Sent "+server+":"+message);

        }

        channel.close();
        connection.close();




    }

}
