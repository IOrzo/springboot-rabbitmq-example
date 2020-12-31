package com.sixtofly.rabbitmqcommon.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 无处理器示例
 * @author xie yuan bing
 * @date 2020-12-30 11:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class EmptyEvent extends BaseEvent{

    private String desc = "无处理器示例";

}
