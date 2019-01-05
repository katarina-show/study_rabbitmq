package com.sjw.service.fanout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

/**
 * 第二个消费者
 * 属于fanout交换机
 * 需要实现MessageListener接口
 */
public class H2_Service implements MessageListener{

    private Logger logger = LoggerFactory.getLogger(H2_Service.class);

    public void onMessage(Message message) {
        logger.info("Get message:"+new String(message.getBody()));
    }
}
