package com.sjw.service.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * 找不到队列的回调类
 * 即 原生的 addReturnListener方法
 * 需实现RabbitTemplate.ReturnCallback接口
 */
@Service
public class SendReturnCallback implements RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(SendReturnCallback.class);

    public void returnedMessage(Message message, int replyCode,
                                String replyText, String exchange,
                                String routingKey) {
        logger.info("Returned replyText："+replyText);
        logger.info("Returned exchange："+exchange);
        logger.info("Returned routingKey："+routingKey);
        String msgJson  = new String(message.getBody());
        logger.info("Returned Message："+msgJson);
    }
}
