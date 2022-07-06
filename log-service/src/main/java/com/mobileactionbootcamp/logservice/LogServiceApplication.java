package com.mobileactionbootcamp.logservice;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LogServiceApplication {

	@Value("${rabbit.queue.name}")
	public String QUEUE;

	@Value("${rabbit.exchange.name}")
	public String EXCHANGE;

	@Value("${rabbit.routingkey.name}")
	public String ROUTING_KEY;

	@Bean
	public Queue queue(){
		return new Queue(QUEUE);
	}

	@Bean
	public DirectExchange exchange(){
		return new DirectExchange(EXCHANGE);
	}

	@Bean
	public Binding binding(Queue queue, DirectExchange exchange){
		return BindingBuilder
				.bind(queue)
				.to(exchange)
				.with(ROUTING_KEY);
	}

	@Bean
	public MessageConverter messageConverter(){
		return new Jackson2JsonMessageConverter();
	}

	@Bean
	public AmqpTemplate template(ConnectionFactory connectionFactory){
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}

	public static void main(String[] args) {
		SpringApplication.run(LogServiceApplication.class, args);
	}

}
