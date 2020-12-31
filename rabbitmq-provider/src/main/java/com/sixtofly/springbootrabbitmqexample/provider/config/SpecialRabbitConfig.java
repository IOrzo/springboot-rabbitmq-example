package com.sixtofly.springbootrabbitmqexample.provider.config;

import com.sixtofly.rabbitmqcommon.constants.RabbitConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 特殊交换机配置
 *
 * @author xie yuan bing
 * @date 2020-12-23 17:41
 * @description
 */
@Configuration
public class SpecialRabbitConfig {

    /**
     * 声明死信交换机
     */
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange(RabbitConstants.EXCHANGE_DLX, true, false);
    }

    /**
     * 手动确认队列
     */
    @Bean
    public Queue manualQueue() {
        return new Queue(RabbitConstants.QUEUE_MANUAL);
    }

    /**
     * 队列设置参数:
     * x-message-ttl 消息生存期, 毫秒
     * x-expires  队列多长时间(毫秒)没有被使用(访问)就会被删除
     * x-max-length  队列可以容纳的消息的最大条数,超过这个条数,队列头部的消息将会被丢弃
     * x-max-length-bytes 队列可以容纳的消息的最大字节数,超过这个字节数,队列头部的消息将会被丢弃
     * x-overflow 队列中的消息溢出时,如何处理这些消息.要么丢弃队列头部的消息,要么拒绝接收后面生产者发送过来的所有消息.（值可以是 drop-head 和 reject-publish）
     * x-dead-letter-exchange 该参数值为一个(死信)交换机的名称,当队列中的消息的生存期到了,或者因长度限制被丢弃时,消息会被推送到(绑定到)这台交换机(的队列中),而不是直接丢掉
     * x-dead-letter-routing-key 相对于死信交换机的路由信息
     * x-max-priority 设置该队列中的消息的优先级最大值.发布消息的时候,可以指定消息的优先级,优先级高的先被消费.如果没有设置该参数,那么该队列不支持消息优先级功能.也就是说,就算发布消息的时候传入了优先级的值,也不会起什么作用.(消息的优先级最好不要超过这个值)
     * x-queue-mode 设置队列为懒人模式.该模式下的队列会先将交换机推送过来的消息(尽可能多的)保存在磁盘上,以减少内存的占用.当消费者开始消费的时候才加载到内存中;如果没有设置懒人模式,队列则会直接利用内存缓存,以最快的速度传递消息. (值: lazy)
     * x-queue-master-locator 集群相关设置
     * 注: 队列一旦声明,参数将无法更改
     *
     */
    /**
     * 声明死信队列(死信队列需配合手动确认使用)
     * 若当前队列中的消息被丢弃, 则会把消息推送到指定交换机
     *
     * 消息变成死信的情况:
     * 消息被拒绝(basic.reject / basic.nack)，并且requeue = false
     * 消息TTL过期
     * 队列达到最大长度
     */
    @Bean
    public Queue dlxQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", RabbitConstants.EXCHANGE_DLX);
        args.put("x-dead-letter-routing-key", RabbitConstants.ROUTING_KEY_DLX_CONTAINER);
        return new Queue(RabbitConstants.QUEUE_DLX, true, false, false, args);
    }

    /**
     * 延时队列
     * 利用死信队列的特性, 在推送消息时加入消息过期时间, 以达到延时消费的情况
     */
    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>(3);
        args.put("x-dead-letter-exchange", RabbitConstants.EXCHANGE_DLX);
        args.put("x-dead-letter-routing-key", RabbitConstants.ROUTING_KEY_DELAY_CONTAINER);
        return new Queue(RabbitConstants.QUEUE_DELAY, true, false, false, args);
    }

    /**
     * 死信队列容器
     */
    @Bean
    public Queue dlxContainerQueue() {
        return new Queue(RabbitConstants.QUEUE_DLX_CONTAINER, true, false, false);
    }

    /**
     * 延时队列容器
     */
    @Bean
    public Queue delayContainerQueue() {
        return new Queue(RabbitConstants.QUEUE_DELAY_CONTAINER, true, false, false);
    }

    @Bean
    public Binding manualBinding () {
        return BindingBuilder.bind(manualQueue()).to(dlxExchange()).with(RabbitConstants.ROUTING_KEY_MANUAL);
    }


    @Bean
    public Binding dlxBinding() {
        return BindingBuilder.bind(dlxQueue()).to(dlxExchange()).with(RabbitConstants.ROUTING_KEY_DLX);
    }

    @Bean
    public Binding delayBinding() {
        return BindingBuilder.bind(delayQueue()).to(dlxExchange()).with(RabbitConstants.ROUTING_KEY_DELAY);
    }

    @Bean
    public Binding dlxContainerBinding() {
        return BindingBuilder.bind(dlxContainerQueue()).to(dlxExchange()).with(RabbitConstants.ROUTING_KEY_DLX_CONTAINER);
    }

    @Bean
    public Binding delayContainerBinding() {
        return BindingBuilder.bind(delayContainerQueue()).to(dlxExchange()).with(RabbitConstants.ROUTING_KEY_DELAY_CONTAINER);
    }
}
