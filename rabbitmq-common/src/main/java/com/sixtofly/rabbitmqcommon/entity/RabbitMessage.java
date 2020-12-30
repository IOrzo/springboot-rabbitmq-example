package com.sixtofly.rabbitmqcommon.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * @author xie yuan bing
 * @date 2019-11-26 15:50
 * @description
 */
@Data
@AllArgsConstructor
public class RabbitMessage implements Serializable {

    private String id;

    private String data;

    private Date createTime;

    public RabbitMessage() {
        this.id = UUID.randomUUID().toString();
        this.createTime = new Date();
    }

    public RabbitMessage(String data) {
        this();
        this.data = data;
    }
}
