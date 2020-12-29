package com.sixtofly.rabbitmqconsumer.config;

import com.sixtofly.rabbitmqconsumer.listener.DlxListener;
import com.sixtofly.rabbitmqconsumer.listener.ManualListener;
import com.sixtofly.rabbitmqconsumer.listener.SimpleManualListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 消费者配置
 * @author xie yuan bing
 * @date 2020-12-18 09:35
 */
@Configuration
@Slf4j
public class RabbitConfig {

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        // 配置消息转化器
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer manualListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 消费者数量
        container.setConcurrentConsumers(1);
        // 消费者最大数量
        container.setMaxConcurrentConsumers(1);
        // 获取消息数量
        container.setPrefetchCount(1);
        container.setTxSize(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.addQueueNames("QUEUE_MANUAL");
        container.setMessageListener(new ManualListener());
        return container;
    }

    @Bean
    public SimpleMessageListenerContainer dlxListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 消费者数量
        container.setConcurrentConsumers(1);
        // 消费者最大数量
        container.setMaxConcurrentConsumers(1);
        // 获取消息数量
        container.setPrefetchCount(1);
        container.setTxSize(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.addQueueNames("QUEUE_DLX");
        container.setMessageListener(new DlxListener());
        return container;
    }

    /**
     * 普通消息手动确认监听器
     * @param connectionFactory
     * @return
     */
    @Bean
    public SimpleMessageListenerContainer simpleManualListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 消费者数量
        container.setConcurrentConsumers(1);
        // 消费者最大数量
        container.setMaxConcurrentConsumers(1);
        // 获取消息数量
        container.setPrefetchCount(1);
        container.setTxSize(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.addQueueNames("QUEUE_DLX_CONTAINER", "QUEUE_DELAY_CONTAINER");
        container.setMessageListener(new SimpleManualListener());
        return container;
    }



}
