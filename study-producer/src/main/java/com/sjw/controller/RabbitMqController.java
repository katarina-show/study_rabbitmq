package com.sjw.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 使用Spring提供的RabbitTemplate发送消息
 */
@Controller
@RequestMapping("/rabbitmq")
public class RabbitMqController {

    private Logger logger = LoggerFactory.getLogger(RabbitMqController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    /*
     * fanout交换机发送
     * 没有routing key
     */
    @ResponseBody
    @RequestMapping("/fanoutSender")
    public String fanoutSender(@RequestParam("message")String message){
        String opt;
        try {
            for(int i = 0; i < 3; i++){
                String str = "Fanout,the message_"+i+" is : "+message;
                logger.info("**************************Send Message:["+str+"]");
                //核心方法send，参数分别是：交换机名称、路由键、信息实体
                //MessageProperties可配置一些属性，暂不配置
                rabbitTemplate.send("fanout-exchange","",
                        new Message(str.getBytes(),new MessageProperties()));

            }
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

    /*
     * topic交换机发送
     * 以  日志级别.系统名称 来做不同的topic转发
     */
    @ResponseBody
    @RequestMapping("/topicSender")
    public String topicSender(@RequestParam("message")String message){
        String opt;
        try {
            String[] severities={"error","info","warning"};
            String[] modules={"email","order","user"};
            for(int i = 0; i < severities.length; i++){
                for(int j = 0; j < modules.length; j++){
                    String routeKey = severities[i]+"."+modules[j];
                    String str = "the message is [rk:"+routeKey+"]["+message+"]";
                    //核心方法send，参数分别是：交换机名称、路由键、信息实体
                    //MessageProperties可配置一些属性，暂不配置
                    rabbitTemplate.send("topic-exchange",routeKey,
                            new Message(str.getBytes(),new MessageProperties()));

                }
            }
            opt = "suc";
        } catch (Exception e) {
            opt = e.getCause().toString();
        }
        return opt;
    }

}
