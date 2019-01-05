package com.sjw.topic;

import com.sjw.RmConst;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/**
 * topic交换机生产者
 */
@Component
public class TopicSender {


    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        //邮件和用户都发
        String msg1 = "I am email mesaage msg======";
        System.out.println("email_sender1 : " + msg1);
        //发送到交换机sb.exchange上，路由键是sb.info.email
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC, RmConst.RK_EMAIL, msg1);

        String msg2 = "I am user mesaages msg########";
        System.out.println("user_sender2 : " + msg2);
        this.rabbitTemplate.convertAndSend(RmConst.EXCHANGE_TOPIC, RmConst.RK_USER, msg2);
    }

}
