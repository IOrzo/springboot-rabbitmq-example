package com.sixtofly.springbootrabbitmqexample.provider.config;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xie yuan bing
 * @date 2020-12-18 11:15
 * @description
 */
@Configuration
public class FanoutRabbitConfig {


    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(RabbitConstants.EXCHANGE_FANOUT);
    }

    @Bean
    public Queue fanoutQueue() {
        return new Queue(RabbitConstants.QUEUE_FANOUT);
    }

    @Bean
    public Queue fanoutQueueA() {
        return new Queue(RabbitConstants.QUEUE_FANOUT_A);
    }

    @Bean
    public Queue fanoutQueueB() {
        return new Queue(RabbitConstants.QUEUE_FANOUT_B);
    }

    @Bean
    public Binding fanoutBinding() {
        return BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingA() {
        return BindingBuilder.bind(fanoutQueueA()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBindingB() {
        return BindingBuilder.bind(fanoutQueueB()).to(fanoutExchange());
    }
}
