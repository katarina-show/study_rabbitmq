package com.sjw;

import org.springframework.amqp.core.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootRmApplication {

    //如下两个队列未绑定交换器，那么会绑定到rabbitmq默认内置的direct交换机上，路由键和队列名相同
	//定义1个名为 sb.hello 的队列
	@Bean
	public Queue helloQueue() {
		return new Queue(RmConst.QUEUE_HELLO);
	}

	//定义1个名为 sb.user 的队列
	@Bean
	public Queue userQueue() {
		return new Queue(RmConst.QUEUE_USER);
	}

	//===============以下是验证topic Exchange的队列==========
	//定义1个名为 sb.info.email 的队列
	@Bean
	public Queue queueEmailMessage() {
		return new Queue(RmConst.QUEUE_TOPIC_EMAIL);
	}
	//定义1个名为 sb.info.user 的队列
	@Bean
	public Queue queueUserMessages() {
		return new Queue(RmConst.QUEUE_TOPIC_USER);
	}
	//===============以上是验证topic Exchange的队列==========


	//定义1个名为 sb.exchange 的交换机
	@Bean
	TopicExchange exchange() {
		return new TopicExchange(RmConst.EXCHANGE_TOPIC);
	}

	/**
	 * bind方法指定队列，to方法指定交换机，with方法指定路由键
	 */
	@Bean
	Binding bindingEmailExchangeMessage() {
		return BindingBuilder
                .bind(queueEmailMessage())
                .to(exchange())
                .with("sb.*.email");
	}

	/**
	 * bind方法指定队列，to方法指定交换机，with方法指定路由键
	 */
	@Bean
	Binding bindingUserExchangeMessages() {
		return BindingBuilder
                .bind(queueUserMessages())
                .to(exchange())
                .with("sb.*.user");
	}

	/*
	//===============以下是验证Fanout Exchange的队列==========
	@Bean
	public Queue AMessage() {
		return new Queue(FANOUT_QUEUE+".A");
	}

	@Bean
	public Queue BMessage() {
		return new Queue(FANOUT_QUEUE+".B");
	}

	@Bean
	public Queue CMessage() {
		return new Queue(FANOUT_QUEUE+".C");
	}
	//===============以上是验证Fanout Exchange的队列==========

	FanoutExchange fanoutExchange() {
		return new FanoutExchange(FANOUT_EXCHANGE);
	}

	@Bean
	Binding bindingExchangeA(Queue AMessage,FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(AMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeB(Queue BMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(BMessage).to(fanoutExchange);
	}

	@Bean
	Binding bindingExchangeC(Queue CMessage, FanoutExchange fanoutExchange) {
		return BindingBuilder.bind(CMessage).to(fanoutExchange);
	}
	*/

	public static void main(String[] args) {
		SpringApplication.run(SpringbootRmApplication.class, args);
	}
}
