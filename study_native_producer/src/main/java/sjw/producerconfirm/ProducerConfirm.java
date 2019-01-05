package sjw.producerconfirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送方确认模式 --同步模式
 */
public class ProducerConfirm {

    private final static String EXCHANGE_NAME = "producer_confirm";
    private final static String ROUTE_KEY = "error";

    public static void main(String[] args) throws IOException, TimeoutException,
            InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();
        // 设置MabbitMQ所在主机ip或者主机名
        factory.setHost("127.0.0.1");
        // 创建一个连接
        Connection connection = factory.newConnection();
        // 创建一个信道
        Channel channel = connection.createChannel();
        //关键：将信道设置为发送方确认
        channel.confirmSelect();

        //1.写法1 waitForConfirms方法
        for(int i=0;i<2;i++){
            String msg = "Hello "+(i+1);
            channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY,null,msg.getBytes());
            //只有当rabbitmq确认收到1条消息后，channel.waitForConfirms()才会变成true
            //返回false或者超时(可以参数设置超时时间)，消息将会重发
            //waitForConfirms是一种同步模式，意思就是比较慢
            if (channel.waitForConfirms()){
                System.out.println(ROUTE_KEY+":"+msg);
            }
        }

        //2.写法2 waitForConfirmsOrDie方法，和waitForConfirms最大的区别就是批处理
        //因为waitForConfirms只能一条一条的处理，速度比1要快

        /*for(int i=0;i<2;i++){
            String msg = "Hello "+(i+1);
            channel.basicPublish(EXCHANGE_NAME,ROUTE_KEY,null,msg.getBytes());
        }
        channel.waitForConfirmsOrDie();
        System.out.println("全部OK");*/

        //第3个方法在ProducerConfirmAsync类里：addConfirmListener

        // 关闭频道和连接
        channel.close();
        connection.close();
    }

}
