package com.sjw.service.mq;

import com.sjw.service.busi.SendSms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息消费端监听短信消息类
 * 即消费者 实现MessageListener接口
 */
@Component
public class ProcessUserSms implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(ProcessUserSms.class);

    @Autowired
    private SendSms sendSms;

    public void onMessage(Message message) {
        logger.info("accept message,ready process......");
        sendSms.sendSms(new String(message.getBody()));

    }
}
