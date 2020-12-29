package com.sixtofly.rabbitmqconsumer.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 单独使用@RabbitListener注解进行消费
 *
 * @author xie yuan bing
 * @date 2020-12-18 17:10
 * @description
 */
@Component
public class IndependentListener {

    @RabbitListener(queues = "QUEUE_INDEPENDENT_LISTENER")
    public void process(@Payload byte[] body, @Headers Map<String, Object> headers, @Header String contentType) {
        System.out.println("body：" + new String(body));
        System.out.println("Headers：" + headers);
        System.out.println("contentType：" + contentType);
    }
}
