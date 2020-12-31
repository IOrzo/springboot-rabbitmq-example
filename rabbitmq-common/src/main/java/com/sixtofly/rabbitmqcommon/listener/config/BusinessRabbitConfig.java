package com.sixtofly.rabbitmqcommon.listener.config;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xie yuan bing
 * @date 2020-12-30 10:31
 * @description
 */
//@Configuration
public class BusinessRabbitConfig {

    /**
     * 业务交换机
     */
    @Bean
    public DirectExchange businessExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_BUSINESS, true, false);
    }

    @Bean
    public Queue businessQueue() {
        return new Queue(RabbitConstants.QUEUE_BUSINESS);
    }

    @Bean
    public Binding businessBinding() {
        return BindingBuilder.bind(businessQueue()).to(businessExchange()).with(RabbitConstants.ROUTING_KEY_BUSINESS);
    }
}
