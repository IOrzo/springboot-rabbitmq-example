package com.sixtofly.rabbitmqcommon.entity;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

/**
 * 基础事件类
 * @author xie yuan bing
 * @date 2020-12-30 10:07
 * @description
 */
@Data
public class BaseEvent {

    private String eventId;

    private Class clazz;

    private Date createTime;

    public BaseEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.createTime = new Date();
        this.clazz = this.getClass();
    }

}
