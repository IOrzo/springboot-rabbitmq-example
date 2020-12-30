package com.sixtofly.springbootrabbitmqexample.provider.config;

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
 * @author xie yuan bing
 * @date 2020-12-18 09:35
 * @description
 */
@Configuration
@Slf4j
public class RabbitConfig {

//    @Bean
//    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
//        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
//        rabbitAdmin.setAutoStartup(true);
//        return rabbitAdmin;
//    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);

        // 配置消息转化器
        // RabbitMQ 的序列化是指 Message 的 body 属性，即我们真正需要传输的内容，RabbitMQ 抽象出一个 MessageConvert 接口处理消息的序列化，其实现有 SimpleMessageConverter（默认）、Jackson2JsonMessageConverter 等
        // 当调用了 convertAndSend 方法时会使用 MessageConvert 进行消息的序列化
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        // 设置开启Mandatory,才能出发回调函数，无论消息推送结果怎么样都强制回调函数
        rabbitTemplate.setMandatory(true);

        // 确认消息推送成功 -> 消息成功到达交换机
        /**
         * 成功示例:
         * correlationData(相关数据): null
         * ack(确认情况): true
         * cause(原因): null
         *
         * 失败示例:
         * correlationData(相关数据): null
         * ack(确认情况): false
         * cause(原因): channel error; protocol method: #method<channel.close>(reply-code=404, reply-text=NOT_FOUND - no exchange 'EXCHANGE_NOT_EXISTS' in vhost '/example', class-id=60, method-id=40)
         */
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.info("---------------------ConfirmCallback------------------------");
                log.info("correlationData(相关数据): {}", correlationData);
                log.info("ack(确认情况): {}", ack);
                log.info("cause(原因): {}", cause);
            }
        });

        // 消息没有正确到达队列时触发回调，如果正确到达队列不执行
        // 只有当消息成功到达交换机了, 但是没有到达队列才会执行
        /**
         * 成功示例:
         * 不会执行该回调函数
         *
         * 失败示例:
         * message(消息): {"body":"eyJpZCI6IjdiOWI4NGJjLTAzNzgtNGM1My1iM2Y1LWE5ZWRhNmU1YzY5ZSIsImRhdGEiOiJzZW5kTm90RXhpc3RzUXVldWUiLCJjcmVhdGVUaW1lIjoxNjA4NzE1MzM5OTgyfQ==","messageProperties":{"contentEncoding":"UTF-8","contentLength":0,"contentType":"application/json","deliveryTag":0,"finalRetryForMessageWithNoId":false,"headers":{"__TypeId__":"com.sixtofly.rabbitmqcommon.entity.RabbitMessage"},"priority":0,"publishSequenceNumber":0,"receivedDeliveryMode":"PERSISTENT"}}
         * replyCode(回应码): 312
         * replyText(回应信息): NO_ROUTE
         * exchange(交换机): EXCHANGE_DIRECT
         * routingKey(路由信息): ROUTING_NOT_EXISTS
         */
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                log.info("---------------------ReturnCallback------------------------");
                log.info("message(消息): {}", JSON.toJSONString(message));
                log.info("replyCode(回应码): {}", replyCode);
                log.info("replyText(回应信息): {}", replyText);
                log.info("exchange(交换机): {}", exchange);
                log.info("routingKey(路由信息): {}", routingKey);
            }
        });
        return rabbitTemplate;
    }

}
