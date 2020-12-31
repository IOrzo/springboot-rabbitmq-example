package com.sixtofly.rabbitmqcommon.listener.config;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sixtofly.rabbitmqcommon.entity.BaseEvent;
import com.sixtofly.rabbitmqcommon.listener.ConsumerInfo;
import com.sixtofly.rabbitmqcommon.listener.config.ApplicationEventListenerBeanPostProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 统一消息处理, 统一上层处理流程
 * @author xie yuan bing
 * @date 2020-12-30 10:44
 */
@Slf4j
public class EventListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            process(message, channel);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

    /**
     * 处理事件
     * @param message
     * @param channel
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    private void process(Message message, Channel channel) throws InvocationTargetException, IllegalAccessException, IOException {
        BaseEvent event = JSONObject.parseObject(message.getBody(), BaseEvent.class);
        // 检查事件必要信息
        if (event == null || event.getClazz() == null) {
            log.error("事件信息不全, 内容: {}", new String(message.getBody()));
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        ConsumerInfo consumerInfo = ApplicationEventListenerBeanPostProcessor.LISTENERS.get(event.getClazz());
        // 缺少对应事件处理器
        if (consumerInfo == null) {
            log.error("缺少{}事件处理器, 消息: {}", event.getClazz().getName(), new String(message.getBody()));
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }
        Object param = JSONObject.parseObject(message.getBody(), consumerInfo.getEvent());
        consumerInfo.getMethod().invoke(consumerInfo.getBean(), param);
    }
}
