package com.sixtofly.springbootrabbitmqexample.provider.constants;

/**
 * 常用值
 * @author xie yuan bing
 * @date 2020-12-18 09:37
 * @description
 */
public interface RabbitConstants {

    // 交换机常量

    String EXCHANGE_DIRECT = "EXCHANGE_DIRECT";

    String EXCHANGE_FANOUT = "EXCHANGE_FANOUT";

    String EXCHANGE_TOPIC = "EXCHANGE_TOPIC";

    String EXCHANGE_BUSINESS = "EXCHANGE_BUSINESS";

    /**
     * 死信交换机
     */
    String EXCHANGE_DLX = "EXCHANGE_DLX";

    /**
     * 不存在的交换机
     */
    String EXCHANGE_NOT_EXISTS = "EXCHANGE_NOT_EXISTS";

    // 队列常量

    String QUEUE_DIRECT = "QUEUE_DIRECT";

    String QUEUE_FANOUT = "QUEUE_FANOUT";

    String QUEUE_FANOUT_A = "QUEUE_FANOUT_A";

    String QUEUE_FANOUT_B = "QUEUE_FANOUT_B";

    String QUEUE_TOPIC = "QUEUE_TOPIC";

    String QUEUE_TOPIC_A = "QUEUE_TOPIC_A";

    String QUEUE_TOPIC_B = "QUEUE_TOPIC_B";

    String QUEUE_TOPIC_A_A = "QUEUE_TOPIC_A_A";

    String QUEUE_TOPIC_A_B = "QUEUE_TOPIC_A_B";

    String QUEUE_NOT_EXISTS = "QUEUE_NOT_EXISTS";

    /**
     * 手动确认队列
     */
    String QUEUE_MANUAL = "QUEUE_MANUAL";

    /**
     * 死信队列
     */
    String QUEUE_DLX = "QUEUE_DLX";

    /**
     * 延时队列
     */
    String QUEUE_DELAY = "QUEUE_DELAY";

    /**
     * 死信队列容器
     */
    String QUEUE_DLX_CONTAINER = "QUEUE_DLX_CONTAINER";

    /**
     * 延时队列容器
     */
    String QUEUE_DELAY_CONTAINER = "QUEUE_DELAY_CONTAINER";

    /**
     * 单独使用@RabbitListener进行消费
     */
    String QUEUE_INDEPENDENT_LISTENER = "QUEUE_INDEPENDENT_LISTENER";

    // 路由常量

    String ROUTING_KEY_DIRECT = "ROUTING_KEY_DIRECT";

    String ROUTING_KEY_FANOUT = "ROUTING_KEY_FANOUT";

    String ROUTING_KEY_TOPIC = "ROUTING_KEY_TOPIC";

    /**
     * * 代替一个任意标识符, 需用.分隔标识符
     * 如: ROUTING_KEY_TOPIC.  ROUTING_KEY_TOPIC.A
     */
    String ROUTING_KEY_TOPIC_A = "ROUTING_KEY_TOPIC.*";

    /**
     * # 代替零个或多个标识符(包括一个)
     * 如: ROUTING_KEY_TOPIC  ROUTING_KEY_TOPIC.  ROUTING_KEY_TOPIC.A  ROUTING_KEY_TOPIC.A.A
     */
    String ROUTING_KEY_TOPIC_A_A = "ROUTING_KEY_TOPIC.#";

    String ROUTING_KEY_TOPIC_A_B = "ROUTING_KEY_TOPIC_A_B";

    String ROUTING_KEY_INDEPENDENT_LISTENER = "ROUTING_KEY_INDEPENDENT_LISTENER";

    String ROUTING_KEY_NOT_EXISTS = "ROUTING_KEY_NOT_EXISTS";

    String ROUTING_KEY_MANUAL = "ROUTING_KEY_MANUAL";

    String ROUTING_KEY_DLX_CONTAINER = "ROUTING_KEY_DLX_CONTAINER";

    String ROUTING_KEY_DELAY_CONTAINER = "ROUTING_KEY_DELAY_CONTAINER";

    /**
     * 死信路由
     */
    String ROUTING_KEY_DLX = "ROUTING_KEY_DLX";

    /**
     * 延时路由
     */
    String ROUTING_KEY_DELAY = "ROUTING_KEY_DELAY";

}
