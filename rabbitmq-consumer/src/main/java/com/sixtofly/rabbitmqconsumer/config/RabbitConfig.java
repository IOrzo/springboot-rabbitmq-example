package com.sixtofly.rabbitmqconsumer.config;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqconsumer.listener.DlxListener;
import com.sixtofly.rabbitmqconsumer.listener.ManualListener;
import com.sixtofly.rabbitmqconsumer.listener.ManualRetryListener;
import com.sixtofly.rabbitmqconsumer.listener.SimpleManualListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.retry.ImmediateRequeueMessageRecoverer;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

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

    /**
     *  手动应答重试示例 - 消费监听
     */
    @Bean
    public SimpleMessageListenerContainer manualRetryListenerContainer(ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        // 消费者数量
        container.setConcurrentConsumers(1);
        // 消费者最大数量
        container.setMaxConcurrentConsumers(1);
        // 获取消息数量
        container.setPrefetchCount(1);
        container.setTxSize(1);
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        container.addQueueNames(RabbitConstants.QUEUE_MANUAL_RETRY);
        container.setMessageListener(new ManualRetryListener());
        return container;
    }


    /**
     * 重试机制次数达到最大时, 对消息的执行策略
     * RejectAndDontRequeueRecoverer 默认实现了, 打印异常信息
     * @see org.springframework.amqp.rabbit.retry.RejectAndDontRequeueRecoverer
     * ImmediateRequeueMessageRecoverer 立即重新返回队列
     * @see org.springframework.amqp.rabbit.retry.ImmediateRequeueMessageRecoverer
     * RepublishMessageRecoverer 重新发布消息
     * @see org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer
     * @return
     */
    @Bean
    public MessageRecoverer messageRecoverer() {
        // 注意重试过后还是会抛异常, 再重回队列, 可能会导致死循环
//        return new ImmediateRequeueMessageRecoverer();
        // 根据参数重新将消息发布到指定交换机中
        return new RepublishMessageRecoverer(rabbitTemplate, RabbitConstants.EXCHANGE_DLX, RabbitConstants.ROUTING_KEY_DLX_CONTAINER);
//        return new RejectAndDontRequeueRecoverer();
    }



}
