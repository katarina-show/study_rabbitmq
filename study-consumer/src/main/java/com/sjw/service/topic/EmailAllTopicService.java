package com.sjw.service.topic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

/**
 * 所有邮件日志
 * 属于topic交换机
 * 需要实现MessageListener接口
 */
@Component
public class EmailAllTopicService implements MessageListener{

    private Logger logger = LoggerFactory.getLogger(EmailAllTopicService.class);

    public void onMessage(Message message) {
        logger.info("Get message:"+new String(message.getBody()));
    }
}
