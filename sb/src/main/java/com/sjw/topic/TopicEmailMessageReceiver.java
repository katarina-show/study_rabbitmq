package com.sjw.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
/**
 * topic交换机消费者（email）
 */
@Component
@RabbitListener(queues = "sb.info.email")
public class TopicEmailMessageReceiver {

    @RabbitHandler
    public void process(String msg) {
        System.out.println("TopicEmailMessageReceiver  : " +msg);
    }

}