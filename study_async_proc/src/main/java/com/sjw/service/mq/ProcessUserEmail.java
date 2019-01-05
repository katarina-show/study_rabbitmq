package com.sjw.service.mq;

import com.sjw.service.busi.SendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 消息消费端监听邮件消息类
 * 即消费者 实现MessageListener接口
 */
@Component
public class ProcessUserEmail implements MessageListener {

    private Logger logger = LoggerFactory.getLogger(ProcessUserEmail.class);

    @Autowired
    private SendEmail sendEmail;

    public void onMessage(Message message) {
        logger.info("accept message,ready process......");
        sendEmail.sendEmail(new String(message.getBody()));

    }
}
