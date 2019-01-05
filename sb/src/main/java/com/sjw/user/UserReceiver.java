package com.sjw.user;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * /userTest 消费者
 */
@Component
@RabbitListener(queues = "sb.user")
public class UserReceiver {

    @RabbitHandler
    public void process(User user) {
        System.out.println("user receive  : " + user.getName()+"/"+user.getPass());
    }

}
