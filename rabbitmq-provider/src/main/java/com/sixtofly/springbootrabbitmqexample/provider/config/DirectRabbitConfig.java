package com.sixtofly.springbootrabbitmqexample.provider.config;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 直连交换机配置
 * @author xie yuan bing
 * @date 2020-12-18 09:42
 * @description
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 声明交换机
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_DIRECT);
    }

    /**
     * 声明队列
     */
    @Bean
    public Queue directQueue() {
        return new Queue(RabbitConstants.QUEUE_DIRECT);
    }

    @Bean
    public Queue independentListenerQueue() {
        return new Queue(RabbitConstants.QUEUE_INDEPENDENT_LISTENER);
    }

    /**
     * 自动应答重试
     */
    @Bean
    public Queue autoRetryQueue() {
        return new Queue(RabbitConstants.QUEUE_AUTO_RETRY);
    }

    /**
     * 手动应答重试
     */
    @Bean
    public Queue manualRetryQueue() {
        return new Queue(RabbitConstants.QUEUE_MANUAL_RETRY);
    }


    /**
     * 声明绑定关系
     */
    @Bean
    public Binding directBinding () {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(RabbitConstants.ROUTING_KEY_DIRECT);
    }

    @Bean
    public Binding independentListenerBinding() {
        return BindingBuilder.bind(independentListenerQueue()).to(directExchange()).with(RabbitConstants.ROUTING_KEY_INDEPENDENT_LISTENER);
    }

    @Bean
    public Binding autoRetryBinding() {
        return BindingBuilder.bind(autoRetryQueue()).to(directExchange()).with(RabbitConstants.ROUTING_KEY_AUTO_RETRY);
    }

    @Bean
    public Binding manualRetryBinding() {
        return BindingBuilder.bind(manualRetryQueue()).to(directExchange()).with(RabbitConstants.ROUTING_KEY_MANUAL_RETRY);
    }


}
