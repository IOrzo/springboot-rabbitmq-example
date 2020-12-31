package com.sixtofly.rabbitmqcommon.listener;

import com.sixtofly.rabbitmqcommon.entity.BaseEvent;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 消费者相关信息
 * @author xie yuan bing
 * @date 2020-12-30 14:42
 */
@Data
public class ConsumerInfo {

    /**
     * 执行bean
     */
    private Object bean;

    /**
     * 执行方法
     */
    private Method method;

    /**
     * 执行参数
     */
    private Class<? extends BaseEvent> event;
}
