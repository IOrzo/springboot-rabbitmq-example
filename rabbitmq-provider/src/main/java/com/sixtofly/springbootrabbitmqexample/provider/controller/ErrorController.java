package com.sixtofly.springbootrabbitmqexample.provider.controller;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqcommon.entity.RabbitMessage;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xie yuan bing
 * @date 2020-12-18 17:42
 * @description
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendNotExistsExchangeMessage")
    public String sendNotExistsExchangeMessage(){
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_NOT_EXISTS, RabbitConstants.ROUTING_KEY_DIRECT, new RabbitMessage("sendNotExistsExchangeMessage"));
        return "ok";
    }

    @RequestMapping("/sendNotExistsQueue")
    public String sendNotExistsQueue(){
        rabbitTemplate.convertAndSend(RabbitConstants.EXCHANGE_DIRECT, RabbitConstants.ROUTING_KEY_NOT_EXISTS, new RabbitMessage("sendNotExistsQueue"));
        return "ok";
    }
}
