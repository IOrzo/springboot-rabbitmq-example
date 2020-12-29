package com.sixtofly.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sixtofly.rabbitmqconsumer.entity.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 普通手工消费监听器
 * @author xie yuan bing
 * @date 2020-12-25 16:56
 * @description
 */
@Slf4j
public class SimpleManualListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("----------------------SimpleManualListener开始-----------------------");
        MessageProperties properties = message.getMessageProperties();
        log.info("deliverTage: {}", properties.getDeliveryTag());
        log.info("message: {}", JSONObject.toJSONString(message));
        log.info("messageProperties: {}", JSONObject.toJSONString(properties));
        String queue = properties.getConsumerQueue();
        RabbitMessage msg = JSONObject.parseObject(message.getBody(), RabbitMessage.class);
        log.info("body: {}", new String(message.getBody()));
        if ("QUEUE_DLX_CONTAINER".equals(queue)) {
            log.info("接收到死信队列消息: {}", msg.getData());
        } else if ("QUEUE_DELAY_CONTAINER".equals(queue)) {
            log.info("接收到延时队列消息: {}", msg.getData());
            Date now = new Date();
            Date createTime = msg.getCreateTime();
            long seconds = TimeUnit.MILLISECONDS.toSeconds(now.getTime() - createTime.getTime());
            log.info("消息经过{}被秒处理", seconds);
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        log.info("----------------------SimpleManualListener结束-----------------------");
    }
}
