package com.sixtofly.springbootrabbitmqexample.provider.controller;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqcommon.entity.RabbitMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author xie yuan bing
 * @date 2020-12-18 09:52
 * @description
 */
@RestController
@RequestMapping("/direct")
public class DirectController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendDirectMessage")
    public String sendDirectMessage(String message){
        String id = String.valueOf(UUID.randomUUID());
        RabbitMessage msg = new RabbitMessage(id, message, new Date());
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DIRECT, RabbitConstants.ROUTING_KEY_DIRECT, msg);
        return "ok";
    }

    @GetMapping("/sendDirectIndependentListenerMessage")
    public String sendDirectIndependentListenerMessage(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DIRECT, RabbitConstants.ROUTING_KEY_INDEPENDENT_LISTENER, msg);
        return "ok";
    }

    /**
     * 发送自动应答重试消息
     * @param message
     * @return
     */
    @GetMapping("/sendAutoRetryMessage")
    public String sendAutoRetryMessage(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DIRECT, RabbitConstants.ROUTING_KEY_AUTO_RETRY, msg);
        return "ok";
    }

    /**
     * 发送手动应答重试消息
     * @param message
     * @return
     */
    @GetMapping("/sendManualRetryMessage")
    public String sendManualRetryMessage(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DIRECT, RabbitConstants.ROUTING_KEY_MANUAL_RETRY, msg);
        return "ok";
    }
}
