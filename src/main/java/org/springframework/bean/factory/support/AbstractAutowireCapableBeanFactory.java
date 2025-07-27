package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition) throws BeansException {
        return doCreat(beanName,beanDefinition);
    }


    protected Object doCreat(String beanName,BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        Class beanClass = beanDefinition.getBeanClass();
        try {
            bean=beanClass.newInstance();
        }catch (Exception e) {
            throw new BeansException("Instantiation of bean failed",e);
        }

        addSingleton(beanName,bean);

        return bean;
    }


}
