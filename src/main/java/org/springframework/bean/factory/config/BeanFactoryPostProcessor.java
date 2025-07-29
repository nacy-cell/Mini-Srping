package org.springframework.bean.factory.config;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;

}
