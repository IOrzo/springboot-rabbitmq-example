package com.sixtofly.springbootrabbitmqexample.provider.controller;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqcommon.entity.RabbitMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xie yuan bing
 * @date 2020-12-18 09:52
 * @description
 */
@RestController
@RequestMapping("/topic")
public class TopicController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendTopicMessage")
    public String sendDirectMessage(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_TOPIC, RabbitConstants.ROUTING_KEY_TOPIC, msg);
        return "ok";
    }

    @GetMapping("/sendTopicMessageA")
    public String sendDirectMessageA(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 发送匹配消息
        // * 匹配单个单词
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_TOPIC, "ROUTING_KEY_TOPIC.A", msg);
        return "ok";
    }

    @GetMapping("/sendTopicMessageAB")
    public String sendTopicMessageAB(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 发送匹配消息
        // * 匹配多个单词
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_TOPIC, "ROUTING_KEY_TOPIC.A.A", msg);
        return "ok";
    }

    @GetMapping("/sendTopicMessageWithKey")
    public String sendTopicMessageWithKey(String message, String routingKey){
        RabbitMessage msg = new RabbitMessage(message);
        // 发送匹配消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_TOPIC, routingKey, msg);
        return "ok";
    }
}
