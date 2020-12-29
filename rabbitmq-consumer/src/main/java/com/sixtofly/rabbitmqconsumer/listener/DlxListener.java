package com.sixtofly.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @author xie yuan bing
 * @date 2020-12-24 16:45
 * @description
 */
@Slf4j
public class DlxListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        log.info("----------------------死信队列拒绝消息-----------------------");
        log.info("deliverTage: {}", message.getMessageProperties().getDeliveryTag());
        log.info("message: {}", JSONObject.toJSONString(message));
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        log.info("----------------------死信队列拒绝结束-----------------------");
    }
}
