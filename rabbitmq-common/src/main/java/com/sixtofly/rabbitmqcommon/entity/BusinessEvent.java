package com.sixtofly.rabbitmqcommon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 业务时间示例
 * @author xie yuan bing
 * @date 2020-12-30 11:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessEvent extends BaseEvent{

    private String desc = "测试业务事件发送";

}
