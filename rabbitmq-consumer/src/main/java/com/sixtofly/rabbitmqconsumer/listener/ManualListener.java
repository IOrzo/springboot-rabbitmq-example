package com.sixtofly.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.sixtofly.rabbitmqcommon.entity.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

import java.util.HashSet;
import java.util.Set;

/**
 * @author xie yuan bing
 * @date 2020-12-23 17:50
 * @description
 */
@Slf4j
public class ManualListener implements ChannelAwareMessageListener {

    private Set<String> used = new HashSet<>();


    @Override
    public void onMessage(Message message, Channel channel) throws Exception {

        log.info("----------------------ManualListener-----------------------");
        log.info("----------------------Manual队列手动确认消息开始-----------------------");
        long deliverTage = message.getMessageProperties().getDeliveryTag();
        log.info("deliverTage: {}", deliverTage);
        log.info("message: {}", JSONObject.toJSONString(message));
        RabbitMessage msg = JSONObject.parseObject(message.getBody(), RabbitMessage.class);
        String body = msg.getData();
        log.info("body: {}", body);
        if ("ack".equals(body)) {
            log.info("手动确认消息: 确认");
            channel.basicAck(deliverTage, false);
        } else if ("reject".equals(body)) {
            // requeue为true会重新放回队列
            if (used.add(msg.getId())) {
                log.info("手动确认消息: 第一次拒绝, 消息重回队列, {}", msg.getId());
                channel.basicReject(deliverTage, true);
            } else {
                log.info("手动确认消息: 第二次拒绝, 丢弃消息, {}", msg.getId());
                channel.basicReject(deliverTage, false);
            }
        } else if ("nack".equals(body)) {
            log.info("手动确认消息: nack, 丢弃消息, {}", msg.getId());
            channel.basicNack(deliverTage, false, false);
        } else {
            // 消息没有做确认操作, 则会阻塞消息队列, 消息会处于unacked状态, 造成消息积压
            log.info("无效操作， 不做任何操作");
        }

        /**
         * deliverTage: 消息的下标
         * multiple: 是否批量处理, true将处理小于deliverTage的所有消息
         * requeue: 被拒绝的消息是否重新入队列
         */
        // 确认消息
//        channel.basicAck(deliverTage, false);
        // 拒绝消息
//        channel.basicReject(deliverTage, false);
        // 拒绝消息, 可以批量处理
//        channel.basicNack(deliverTage, false, false);

        log.info("----------------------Manual队列手动确认消息结束-----------------------");
    }
}
