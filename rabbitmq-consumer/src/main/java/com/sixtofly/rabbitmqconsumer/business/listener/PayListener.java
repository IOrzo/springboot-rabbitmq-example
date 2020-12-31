package com.sixtofly.rabbitmqconsumer.business.listener;

import com.alibaba.fastjson.JSONObject;
import com.sixtofly.rabbitmqcommon.entity.BusinessEvent;
import com.sixtofly.rabbitmqcommon.entity.OrderEvent;
import com.sixtofly.rabbitmqcommon.entity.PayEvent;
import com.sixtofly.rabbitmqcommon.listener.ApplicationEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 同一消费处理, 测试多个Listener类
 * @author xie yuan bing
 * @date 2020-12-30 11:25
 */
@Component
@Slf4j
public class PayListener {

    @ApplicationEventListener(PayEvent.class)
    public void process(PayEvent event) {
        log.info("开始处理PayEvent事件");
        log.info("内容:{}", JSONObject.toJSONString(event));
        log.info("处理PayEvent事件结束");
    }

}
