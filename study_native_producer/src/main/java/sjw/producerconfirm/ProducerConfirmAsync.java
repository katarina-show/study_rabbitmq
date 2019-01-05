package sjw.producerconfirm;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 发送方确认模式 --异步模式
 */
public class ProducerConfirmAsync {

    private final static String EXCHANGE_NAME = "producer_confirm";

    public static void main(String[] args) throws IOException, TimeoutException,
            InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();

        // 设置MabbitMQ所在主机ip或者主机名
        factory.setHost("127.0.0.1");
        // 创建一个连接
        Connection connection = factory.newConnection();
        //连接被关闭
        //connection.addShutdownListener();

        // 创建一个信道
        Channel channel = connection.createChannel();
        // 指定转发
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        //关键：将信道设置为发送方确认
        channel.confirmSelect();



        //关键：发送方确认模式--异步模式的核心方法addConfirmListener
        channel.addConfirmListener(new ConfirmListener() {
            //multiple默认是false表示是否批处理小于deliveryTag的信息，用处不大
            //deliveryTag代表了（channel）唯一的投递
            //如果rabbitmq接收到了发送方的消息，会执行以下逻辑
            public void handleAck(long deliveryTag, boolean multiple)
                    throws IOException {
                System.out.println(String.format("已确认消息，标识：%d，是否多个消息：%b", deliveryTag, multiple));
            }
            //如果rabbitmq内部错误，会执行这个逻辑，表示否定应答
            public void handleNack(long deliveryTag, boolean multiple)
                    throws IOException {
                System.out.println("未确认消息，标识：" + deliveryTag);
            }
        });


        //发送方确认模式的拓展监听器addReturnListener
        //当mandatory参数为true，投递消息时如果无法找到一个合适的队列，就会把消息返回给生产者
        //否则丢弃消息(也就是false，是默认值)
        //mandatory参数在basicPublish的一个重载方法中可传入
        //即该监听器只有在mandatory参数为true且无法找到合适队列时，才会触发内部逻辑
        channel.addReturnListener(new ReturnListener() {
            public void handleReturn(int replyCode, String replyText,
                                     String exchange, String routingKey,
                                     AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String msg = new String(body);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("msg:"+msg);
            }
        });

        //其他监听器如 信道被关闭的 监听器等等
        //channel.addShutdownListener(.......);

        String[] severities={"error","info","warning"};
        for(int i=0;i<3;i++){
            String severity = severities[i%3];
            // 发送的消息
            String message = "Hello World_"+(i+1)+("_"+System.currentTimeMillis());
            //注意下mandatory参数
            channel.basicPublish(EXCHANGE_NAME, severity, true,
                    null, message.getBytes());
            System.out.println("----------------------------------------------------");
            System.out.println(" Sent Message: [" + severity +"]:'"+ message + "'");
            Thread.sleep(200);
        }

        // 关闭频道和连接
        channel.close();
        connection.close();
    }


}
