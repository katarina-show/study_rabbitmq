package com.sjw.hello;

import com.sjw.RmConst;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * /hello 生产者1号
 */
@Component
public class HelloSender1 {

    //使用AmqpTemplate或RabbitTemplate都是一样的，RabbitTemplate是AmqpTemplate的实现类
    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send(String msg) {
        String sendMsg = msg +"---"+ System.currentTimeMillis();
        System.out.println("Sender : " + sendMsg);
        //注意下convertAndSend有多个重载方法，下面的方法只指定了路由键和消息内容
        //它会被发送到rabbit默认的交换机上（direct），一般情况下我们是需要指定交换机名字的
        this.rabbitTemplate.convertAndSend(RmConst.QUEUE_HELLO, sendMsg);
    }

}
