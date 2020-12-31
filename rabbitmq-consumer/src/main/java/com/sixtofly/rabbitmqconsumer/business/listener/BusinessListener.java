package com.sixtofly.rabbitmqconsumer.business.listener;

import com.alibaba.fastjson.JSONObject;
import com.sixtofly.rabbitmqcommon.entity.BusinessEvent;
import com.sixtofly.rabbitmqcommon.entity.OrderEvent;
import com.sixtofly.rabbitmqcommon.listener.ApplicationEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author xie yuan bing
 * @date 2020-12-30 11:25
 * @description
 */
@Component
@Slf4j
public class BusinessListener  {

    @ApplicationEventListener(BusinessEvent.class)
    public void process(BusinessEvent event) {
        log.info("开始处理BusinessEvent事件");
        log.info("内容:{}", JSONObject.toJSONString(event));
        log.info("处理BusinessEvent事件结束");
    }

    /**
     * 同一个类中多个处理方法
     * @param event
     */
    @ApplicationEventListener(OrderEvent.class)
    public void process(OrderEvent event) {
        log.info("开始处理OrderEvent事件");
        log.info("内容:{}", JSONObject.toJSONString(event));
        log.info("处理OrderEvent事件结束");
    }

    /**
     * 错误示例: 同一事件不允许存在多个监听器
     */
//    @ApplicationEventListener(OrderEvent.class)
//    public void processOrderEvent(OrderEvent event) {
//        log.info("开始处理OrderEvent事件");
//        log.info("内容:{}", JSONObject.toJSONString(event));
//        log.info("处理OrderEvent事件结束");
//    }

    /**
     * 错误示例: 方法参数只能有一个, 且是事件本身
     */
//    @ApplicationEventListener(OrderEvent.class)
//    public void process(OrderEvent event, Object redundancy) {
//        log.info("开始处理OrderEvent事件");
//        log.info("内容:{}", JSONObject.toJSONString(event));
//        log.info("处理OrderEvent事件结束");
//    }
}
