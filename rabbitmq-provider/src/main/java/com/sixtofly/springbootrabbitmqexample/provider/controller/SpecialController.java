package com.sixtofly.springbootrabbitmqexample.provider.controller;

import com.sixtofly.springbootrabbitmqexample.provider.constants.RabbitConstants;
import com.sixtofly.springbootrabbitmqexample.provider.entity.RabbitMessage;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 特殊使用, 如: 死信队列、 延时队列
 * @author xie yuan bing
 * @date 2020-12-24 15:06
 * @description
 */
@RestController
@RequestMapping("/special")
public class SpecialController {

    @Resource
    private RabbitTemplate rabbitTemplate;


    /**
     * 发送手动确认消息
     * @param message
     * @return
     */
    @GetMapping("/sendManualMessage")
    public String sendManualMessage(String message){
        String id = String.valueOf(UUID.randomUUID());
        RabbitMessage msg = new RabbitMessage(id, message, new Date());
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DLX, RabbitConstants.ROUTING_KEY_MANUAL, msg);
        return "ok";
    }


    /**
     * 发送死信消息
     * @param message
     * @return
     */
    @GetMapping("/sendDlxMessage")
    public String sendDlxMessage(String message){
        String id = String.valueOf(UUID.randomUUID());
        RabbitMessage msg = new RabbitMessage(id, message, new Date());
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DLX, RabbitConstants.ROUTING_KEY_DLX, msg);
        return "ok";
    }

    /**
     * 发送延时消息
     * @param message
     * @return
     */
    @GetMapping("/sendDelayMessage")
    public String sendDelayMessage(String message){
        String id = String.valueOf(UUID.randomUUID());
        RabbitMessage msg = new RabbitMessage(id, message, new Date());
        // 设置消息过期时间, 5s
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DLX, RabbitConstants.ROUTING_KEY_DELAY, msg, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setExpiration("5000");
                return message;
            }
        });
        return "ok";
    }
}
