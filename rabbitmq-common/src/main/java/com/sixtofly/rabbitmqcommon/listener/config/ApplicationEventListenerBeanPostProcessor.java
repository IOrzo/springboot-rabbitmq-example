package com.sixtofly.rabbitmqcommon.listener.config;

import com.sixtofly.rabbitmqcommon.entity.BaseEvent;
import com.sixtofly.rabbitmqcommon.listener.ApplicationEventListener;
import com.sixtofly.rabbitmqcommon.listener.ConsumerInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xie yuan bing
 * @date 2020-12-30 14:37
 * @description
 */
@Slf4j
public class ApplicationEventListenerBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    public static Map<Class<? extends BaseEvent>, ConsumerInfo> LISTENERS = new HashMap<>();

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 获取BaseEvent事件处理器
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<Method> methods = getMethodsListWithAnnotation(bean.getClass(), ApplicationEventListener.class);
        for (Method method : methods) {
            ApplicationEventListener listener = method.getAnnotation(ApplicationEventListener.class);
            if (listener == null) {
                continue;
            }
            if (method.getGenericParameterTypes().length == 1) {
                if (LISTENERS.containsKey(listener.value())) {
                    throw new RuntimeException("同一事件不允许存在多个监听器, 错误:" + listener.value().getName());
                }

                // 记录当前处理方法
                ConsumerInfo consumerInfo = new ConsumerInfo();
                consumerInfo.setBean(bean);
                consumerInfo.setEvent(listener.value());
                consumerInfo.setMethod(method);
                LISTENERS.put(listener.value(), consumerInfo);
            } else {
                log.error("{}类带有ApplicationEventListener注解方法只能有一个参数, 否则会导致消息处理失败", bean.getClass().getName());
                throw new RuntimeException("事件处理方法配置错误, 注解方法只能有一个参数");
            }
        }

        return bean;
    }

    /**
     * 获取带有指定注解的方法
     *
     * @param clazz      类名称
     * @param annotation 注解
     * @return 方法列表
     */
    private List<Method> getMethodsListWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();
        if (clazz != null && annotation != null) {
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.getAnnotation(annotation) != null) {
                    method.setAccessible(true);
                    methods.add(method);
                }
            }
        }
        return methods;
    }
}
