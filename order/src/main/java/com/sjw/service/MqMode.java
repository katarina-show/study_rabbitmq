package com.sjw.service;

import com.sjw.vo.GoodTransferVo;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 用mq实现库存减少
 */
@Service
@Qualifier("mq")
public class MqMode  implements IProDepot {

    //交换机名称，在配置文件中有定义
    private final static String DEPOT_EXCHANGE = "depot-amount-exchange";
    //路由键名称，在配置文件中有定义
    private final static String DEPOT_RK = "amount.depot";

    @Autowired
    RabbitTemplate rabbitTemplate;

    private static Gson gson = new Gson();

    public void processDepot(String goodsId, int amount) {
        GoodTransferVo goodTransferVo = new GoodTransferVo();
        goodTransferVo.setGoodsId(goodsId);
        goodTransferVo.setChangeAmount(amount);
        goodTransferVo.setInOrOut(false);
        String goods = gson.toJson(goodTransferVo);
        //用MessageProperties的setDeliveryMode方法来让消息持久化
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        rabbitTemplate.send(DEPOT_EXCHANGE, DEPOT_RK,
                new Message(goods.getBytes(), messageProperties));
    }
}
