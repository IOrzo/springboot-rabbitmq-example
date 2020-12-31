package com.sixtofly.rabbitmqcommon.listener.config;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqcommon.entity.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 配置一个统一的事件发送者
 * @author xie yuan bing
 * @date 2020-12-30 10:25
 */
//@Component
@Slf4j
public class EventPublisher {


    @Autowired
    @Qualifier("businessTemplate")
    private RabbitTemplate businessTemplate;

    /**
     * 发送事件
     * @param event
     */
    public void publish(BaseEvent event) {
        businessTemplate.convertAndSend(RabbitConstants.EXCHANGE_BUSINESS, RabbitConstants.ROUTING_KEY_BUSINESS, event);
    }

    /**
     * 忽略异常
     * @param event
     * @return
     */
    public boolean tryPublish(BaseEvent event) {
        try {
            publish(event);
        } catch (Exception e) {
            log.error("", e);
            return false;
        }
        return true;
    }
}
