package com.sjw.callback;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 配置类
 * @ Value注解从配置文件中读取出属性
 */
@Configuration
public class RabbitConfig {

    @Value("${spring.rabbitmq.host}")
    private String addresses;

    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtual-host}")
    private String virtualHost;

    @Value("${spring.rabbitmq.publisher-confirms}")
    private boolean publisherConfirms;

    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        //如果要进行发送方确认模式，则这里必须要设置为true
        connectionFactory.setPublisherConfirms(publisherConfirms);

        //mandatory 属性为true，当找不到队列时的，会将消息返还给生产者
        //如果mandatory 属性配置为true，且有如上需求时，需将下面设置为true
        //connectionFactory.setPublisherReturns(true);
        return connectionFactory;
    }

    @Bean
    //因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置
    @Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    @Qualifier("callback")
    public RabbitTemplate rabbitTemplatenew() {
        //把连接工厂放入RabbitTemplate的构造器，这和当时在配置文件中的设置同理
        return new RabbitTemplate(connectionFactory());
    }

}
