package com.sixtofly.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqcommon.entity.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 手动应答重试示例
 * @author xie yuan bing
 * @date 2020-12-29 16:32
 * @description
 */
@Slf4j
public class ManualRetryListener implements ChannelAwareMessageListener {

    Map<String, Integer> retry = new HashMap<>();


    /**
     * 手动应答重试, 不要catch异常, 最后一定要消息确认, 不然会导致消息一直等待
     * 应答, 阻塞消息队列。
     * 经测试, 手动应答模式不能使用重试机制
     */
    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            RabbitMessage msg = JSONObject.parseObject(message.getBody(), RabbitMessage.class);
            log.info("手动应答重试示例接受消息: {}", JSONObject.toJSONString(message));
            Integer retryCount = this.retry.computeIfPresent(msg.getId(), (key, oldValue) -> oldValue + 1);
            if (retryCount == null) {
                retry.put(msg.getId(), 0);
                log.info("手动应答重试首次记录消息: {}", msg.getId());
            } else {
                log.info("手动应答重试: {}, 重试次数: {}, 时间: {}", msg.getId(), retryCount, new Date());
            }
            int exception = 10/0;
            log.info("手动应答重试示例结束: {}", msg.getId());
        } finally {
            // 经测试, 手动应答模式不能使用重试机制
            // 若不手动应答, 则消息会处于未应答状态, 阻塞消息队列
            // 若手动应答, 则认为消息已经正常处理, 不会再次调用
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
//            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
//            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}
