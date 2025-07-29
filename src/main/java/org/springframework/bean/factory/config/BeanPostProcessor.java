package org.springframework.bean.factory.config;

import org.springframework.bean.factory.BeansException;

/**
 * 用于修改实例化后的bean的核心扩展点
 */
public interface BeanPostProcessor {

    /**
     * bean初始化前执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * bean初始化后执行
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}

