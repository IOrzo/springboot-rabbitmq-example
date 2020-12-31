package com.sixtofly.rabbitmqcommon.listener;

import com.sixtofly.rabbitmqcommon.entity.BaseEvent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件处理监听器, 处理MQ事件
 * @author xie yuan bing
 * @date 2020-12-30 10:20
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ApplicationEventListener {

    Class<? extends BaseEvent> value();

}
