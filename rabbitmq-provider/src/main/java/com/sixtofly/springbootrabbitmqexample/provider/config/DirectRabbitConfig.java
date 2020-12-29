package com.sixtofly.springbootrabbitmqexample.provider.config;

import com.sixtofly.springbootrabbitmqexample.provider.constants.RabbitConstants;
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
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_DIRECT);
    }

    /**
     * 声明队列
     * @return
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
     * 声明绑定关系
     * @return
     */
    @Bean
    public Binding directBinding () {
        return BindingBuilder.bind(directQueue()).to(directExchange()).with(RabbitConstants.ROUTING_KEY_DIRECT);
    }

    @Bean
    public Binding independentListenerBinding() {
        return BindingBuilder.bind(independentListenerQueue()).to(directExchange()).with(RabbitConstants.ROUTING_KEY_INDEPENDENT_LISTENER);
    }


}
