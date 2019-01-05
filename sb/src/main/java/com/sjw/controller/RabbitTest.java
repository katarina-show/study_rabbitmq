package com.sjw.controller;

import com.sjw.callback.CallBackSender;
import com.sjw.hello.HelloSender1;
import com.sjw.topic.TopicSender;
import com.sjw.user.UserSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试接口
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitTest {

    @Autowired
    private HelloSender1 helloSender1;
    @Autowired
    private UserSender userSender;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private CallBackSender callBackSender;


    /**
     * 单生产者-多消费者
     */
    @GetMapping("/hello")
    public void hello() {
        //发送10条消息，轮询发送给定义的2个消费者，每个消费者拿到5条消息
        for(int i = 0; i < 10; i++){
            helloSender1.send("hellomsg:"+i);
        }

    }

    /**
     * 传输实体类
     */
    @GetMapping("/userTest")
    public void userTest() {
        //传输一个user对象，给定义的1个消费者
        userSender.send();
    }

    /**
     * topic exchange类型rabbitmq测试
     */
    @GetMapping("/topicTest")
    public void topicTest() {
        topicSender.send();
    }

    /**
     * 发送方确认模式
     */
    @GetMapping("/callback")
    public void callback() {
        callBackSender.send();
    }

}
