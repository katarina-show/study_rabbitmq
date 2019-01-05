package com.sjw.service.impl;

import com.sjw.service.IUserReg;
import com.sjw.service.busi.SaveUser;
import com.sjw.vo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * 异步模式（使用消息中间件rabbitmq）
 */
@Service
@Qualifier("async")
public class AsyncProcess implements IUserReg{

    private Logger logger = LoggerFactory.getLogger(AsyncProcess.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SaveUser saveUser;


    public boolean userRegister(User user) {
        try {
            //先存入数据库，接下来2步可以用消息中间件实现
            saveUser.saveUser(user);
            //分别发送到队列
            rabbitTemplate.send("user-reg-exchange","email",
                    new Message(user.getEmail().getBytes(),new MessageProperties()));
            rabbitTemplate.send("user-reg-exchange","sms",
                    new Message(user.getEmail().getBytes(),new MessageProperties()));
        } catch (AmqpException e) {
            logger.error(e.toString());
            return  false;
        }

        return true;
    }
}
