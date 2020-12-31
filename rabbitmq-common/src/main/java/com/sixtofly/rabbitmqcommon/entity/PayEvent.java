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
public class PayEvent extends BaseEvent{

    private String desc = "支付事件";

}
