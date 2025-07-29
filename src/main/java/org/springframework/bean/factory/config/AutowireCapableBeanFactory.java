package org.springframework.bean.factory.config;

import org.springframework.bean.factory.BeanFactory;
import org.springframework.bean.factory.BeansException;

public interface AutowireCapableBeanFactory extends BeanFactory {

    /**
     *执行BeanPostProcessors的postProcessBeforeInitialization方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 执行BeanPostProcessors的postProcessAfterInitialization方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeansException;
}
