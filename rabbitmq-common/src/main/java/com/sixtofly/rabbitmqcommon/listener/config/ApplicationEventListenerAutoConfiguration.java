package com.sixtofly.rabbitmqcommon.listener.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 统一监听器处理配置
 * @author xie yuan bing
 * @date 2020-12-30 15:11
 */
@Configuration
@Slf4j
public class ApplicationEventListenerAutoConfiguration {

    @Bean
    public ApplicationEventListenerBeanPostProcessor applicationEventListenerBeanPostProcessor() {
        return new ApplicationEventListenerBeanPostProcessor();
    }

    /**
     * 统一消息发布
     */
    @Bean
    public EventPublisher publisher() {
        return new EventPublisher();
    }

    /**
     * 统一消息处理
     */
    @Bean
    public EventListener eventListener() {
        return new EventListener();
    }

    /**
     * 业务统一处理配置
     */
    @Bean
    public RabbitTemplate businessTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        // 配置消息转化器
        // RabbitMQ 的序列化是指 Message 的 body 属性，即我们真正需要传输的内容，RabbitMQ 抽象出一个 MessageConvert 接口处理消息的序列化，其实现有 SimpleMessageConverter（默认）、Jackson2JsonMessageConverter 等
        // 当调用了 convertAndSend 方法时会使用 MessageConvert 进行消息的序列化
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置开启Mandatory,才能出发回调函数，无论消息推送结果怎么样都强制回调函数
        rabbitTemplate.setMandatory(true);

        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("---------------------BusinessTemplate ConfirmCallback------------------------");
                log.info("correlationData(相关数据): {}", correlationData);
                log.info("ack(确认情况): {}", ack);
                log.info("cause(原因): {}", cause);
            }
        });


        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("---------------------BusinessTemplate ReturnCallback------------------------");
                log.info("message(消息): {}", JSON.toJSONString(message));
                log.info("replyCode(回应码): {}", replyCode);
                log.info("replyText(回应信息): {}", replyText);
                log.info("exchange(交换机): {}", exchange);
                log.info("routingKey(路由信息): {}", routingKey);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public BusinessRabbitConfig businessRabbitConfig() {
        return new BusinessRabbitConfig();
    }
}
