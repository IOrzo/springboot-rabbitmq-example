package com.sixtofly.rabbitmqconsumer.listener;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author xie yuan bing
 * @date 2020-12-18 15:17
 * @description
 * @RabbitListener 和 @RabbitHandler 搭配使用
 * @RabbitListener 可以标注在类上面，需配合 @RabbitHandler 注解一起使用
 * @RabbitListener 标注在类上面表示当有收到消息的时候，就交给 @RabbitHandler 的方法处理，具体使用哪个方法处理，根据 MessageConverter 转换后的参数类型
 */
@Component
@RabbitListener(queues = "QUEUE_DIRECT")
public class DirectListener {

    /**
     * 默认消息内容是二进制数组进行传输
     * @param body
     */
    @RabbitHandler
    public void process(byte[] body) {
        System.out.println("DirectReceiver消费者收到消息  : " + new String(body));
    }


    /**
     * 可以用Object对象来接收, 接收的类型就是消息本身
     * @param message
     */
//    @RabbitHandler
    public void process(Object message) {
        /**
         * @see org.springframework.amqp.core.Message
         */
        System.out.println(message.getClass().getName());
        System.out.println("DirectReceiver消费者收到消息  : " + JSON.toJSONString(message));
    }


}
