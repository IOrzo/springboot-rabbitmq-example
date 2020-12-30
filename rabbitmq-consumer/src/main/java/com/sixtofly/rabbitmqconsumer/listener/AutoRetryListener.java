package com.sixtofly.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSONObject;
import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import com.sixtofly.rabbitmqcommon.entity.RabbitMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 自动应答重试示例
 * @author xie yuan bing
 * @date 2020-12-29 16:32
 * @description
 */
@Slf4j
@Component
@RabbitListener(queues = RabbitConstants.QUEUE_AUTO_RETRY)
public class AutoRetryListener {

    Map<String, Integer> retry = new HashMap<>();


    /**
     * 自动应答模式中, 不能捕获异常, 否则系统会当作消息被正常处理了,
     * 系统则不会启动重试机制
     */
    @RabbitHandler
    public void process(byte[] body) {
        RabbitMessage message = JSONObject.parseObject(new String(body), RabbitMessage.class);
        log.info("自动应答重试示例接受消息: {}", JSONObject.toJSONString(message));
        Integer retryCount = this.retry.computeIfPresent(message.getId(), (key, oldValue) -> oldValue + 1);
        if (retryCount == null) {
            retry.put(message.getId(), 0);
            log.info("自动应答重试首次记录消息: {}", message.getId());
        } else {
            log.info("自动应答重试: {}, 重试次数: {}", message.getId(), retryCount);
        }
        int exception = 10/0;
        log.info("自动应答重试示例结束: {}", message.getId());

    }

//    @RabbitHandler
//    public void process(RabbitMessage message) {
//        log.info("自动应答重试示例接受消息: {}", JSONObject.toJSONString(message));
//        Integer count = retry.computeIfAbsent(message.getId(), (key) -> 0);
//        if (count != 0) {
//            Integer retryCount = this.retry.computeIfPresent(message.getId(), (key, oldValue) -> oldValue + 1);
//            log.info("{}, 重试次数: {}", message.getId(), retryCount);
//        }
//        int exception = 10/0;
//        log.info("自动应答重试示例结束: {}", message.getId());
//    }

}
