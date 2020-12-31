package com.sixtofly.springbootrabbitmqexample.provider.controller;

import com.sixtofly.rabbitmqcommon.entity.*;
import com.sixtofly.rabbitmqcommon.listener.config.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 业务相关示例接口
 * @author xie yuan bing
 * @date 2020-12-30 10:23
 * @description
 */
@RestController
@RequestMapping("/business")
public class BusinessController {

    @Autowired
    private EventPublisher publisher;

    @RequestMapping("/sendBusinessEvent")
    private String sendBusinessEvent(String message) {
        BusinessEvent event = new BusinessEvent();
        event.setDesc(message);
        publisher.publish(event);
        return "ok";
    }

    @RequestMapping("/sendOrderEvent")
    private String sendOrderEvent(String message) {
        OrderEvent event = new OrderEvent();
        event.setDesc(message);
        publisher.publish(event);
        return "ok";
    }

    @RequestMapping("/sendPayEvent")
    private String sendPayEvent(String message) {
        PayEvent event = new PayEvent();
        event.setDesc(message);
        publisher.publish(event);
        return "ok";
    }

    @RequestMapping("/sendEmptyEvent")
    private String sendEmptyEvent(String message) {
        EmptyEvent event = new EmptyEvent();
        event.setDesc(message);
        publisher.publish(event);
        return "ok";
    }
}
