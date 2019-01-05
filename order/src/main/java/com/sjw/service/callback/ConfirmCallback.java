package com.sjw.service.callback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

/**
 * 发送方确认模式的回调类
 * 即 原生实现的addConfirmListener方法
 * 需实现RabbitTemplate.ConfirmCallback接口
 */
@Service
public class ConfirmCallback implements RabbitTemplate.ConfirmCallback {
    private Logger logger = LoggerFactory.getLogger(ConfirmCallback.class);

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        //ack为true说明mq告诉发送方它已经收到了
        if (ack) {
            logger.info("消息确认发送给mq成功");
        } else {
            //处理失败的消息
            logger.info("消息发送给mq失败,考虑重发:" + cause);
        }
    }
}
