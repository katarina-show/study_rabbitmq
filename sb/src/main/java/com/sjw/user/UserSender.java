package com.sjw.user;

import com.sjw.RmConst;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * /userTest 生产者
 */
@Component
public class UserSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void send() {
        User user=new User();
        user.setName("sjw");
        user.setPass("123456789");
        System.out.println("user send : " + user.getName() + "/" + user.getPass());
        //注意下convertAndSend有多个重载方法，下面的方法只指定了路由键和消息内容
        //它会被发送到rabbit默认的交换机上（direct），一般情况下我们是需要指定交换机名字的
        this.rabbitTemplate.convertAndSend(RmConst.QUEUE_USER, user);
    }

}
