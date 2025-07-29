package org.springframework.bean.factory.config;

import org.springframework.bean.factory.BeanFactory;

public interface ConfigurableBeanFactory extends BeanFactory ,SingletonBeanRegistry{

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);

}
