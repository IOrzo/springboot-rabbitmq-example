package com.sixtofly.springbootrabbitmqexample.provider.config;

import com.sixtofly.springbootrabbitmqexample.provider.constants.RabbitConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xie yuan bing
 * @date 2020-12-18 11:15
 * @description
 */
@Configuration
public class TopicRabbitConfig {


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitConstants.EXCHANGE_TOPIC);
    }

    @Bean
    public Queue topicQueue() {
        return new Queue(RabbitConstants.QUEUE_TOPIC);
    }

    @Bean
    public Queue topicQueueA() {
        return new Queue(RabbitConstants.QUEUE_TOPIC_A);
    }

    @Bean
    public Queue topicQueueB() {
        return new Queue(RabbitConstants.QUEUE_TOPIC_B);
    }

    @Bean
    public Queue topicQueueAA() {
        return new Queue(RabbitConstants.QUEUE_TOPIC_A_A);
    }

    @Bean
    public Queue topicQueueAB() {
        return new Queue(RabbitConstants.QUEUE_TOPIC_A_B);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(RabbitConstants.ROUTING_KEY_TOPIC);
    }

    @Bean
    public Binding topicBindingA() {
        return BindingBuilder.bind(topicQueueA()).to(topicExchange()).with(RabbitConstants.ROUTING_KEY_TOPIC_A);
    }

    @Bean
    public Binding topicBindingB() {
        return BindingBuilder.bind(topicQueueB()).to(topicExchange()).with(RabbitConstants.ROUTING_KEY_TOPIC_A);
    }

    @Bean
    public Binding topicBindingAA() {
        return BindingBuilder.bind(topicQueueAA()).to(topicExchange()).with(RabbitConstants.ROUTING_KEY_TOPIC_A_A);
    }

    @Bean
    public Binding topicBindingAB() {
        return BindingBuilder.bind(topicQueueAB()).to(topicExchange()).with(RabbitConstants.ROUTING_KEY_TOPIC_A_A);
    }
}
