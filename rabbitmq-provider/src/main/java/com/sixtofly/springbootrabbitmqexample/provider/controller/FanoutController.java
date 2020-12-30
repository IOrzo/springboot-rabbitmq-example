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
@RequestMapping("/fanout")
public class FanoutController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendFanoutMessage")
    public String sendDirectMessage(String message){
        RabbitMessage msg = new RabbitMessage(message);
        // 方式消息
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_FANOUT, RabbitConstants.ROUTING_KEY_FANOUT, msg);
        return "ok";
    }
}
