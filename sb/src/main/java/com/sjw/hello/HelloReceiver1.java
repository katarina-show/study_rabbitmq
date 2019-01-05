package com.sjw.hello;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * /hello 消费者1号
 * @ RabbitListener注解：加在消费者类上，表示消费者监听的队列名
 * @ RabbitHandler注解：消息的处理方法
 * 基本每个消费者都有这2个注解
 */
@Component
@RabbitListener(queues = "sb.hello")
public class HelloReceiver1 {

    @RabbitHandler
    public void process(String hello) {
        System.out.println("Receiver1  : " + hello);
    }

}