package com.sjw.callback;

import java.util.UUID;

import com.sjw.RmConst;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
/**
 * 发送方确认模式
 * 依旧是实现 RabbitTemplate.ConfirmCallback接口
 */
@Component
public class CallBackSender  implements  RabbitTemplate.ConfirmCallback{

    @Autowired
    @Qualifier("callback")
    private RabbitTemplate rabbitTemplateNew;

    public void send() {

        rabbitTemplateNew.setConfirmCallback(this);
        String msg="callbackSender : i am callback sender";
        System.out.println(msg);
        //用CorrelationData类指定消息唯一ID，rabbitmqServer可以从中取得这个ID，见下面的confirm方法
        CorrelationData correlationData =
                new CorrelationData(UUID.randomUUID().toString());
        System.out.println("callbackSender UUID: " + correlationData.getId());
        //发送路由键是sb.info.email
        this.rabbitTemplateNew.convertAndSend(RmConst.EXCHANGE_TOPIC,
                RmConst.RK_EMAIL, msg, correlationData);
    }

    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        System.out.println("callbakck confirm: " + correlationData.getId());
    }
}
