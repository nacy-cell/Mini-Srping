package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.BeanFactory;
import org.springframework.bean.factory.config.BeanDefinition;

/**
 * @author saka
 *
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegister implements BeanFactory {

    @Override
    public Object getBean(String beanName) throws BeansException {

        Object bean = getSingleton(beanName);

        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition=getBeanDefinition(beanName);

        return createBean(beanName,beanDefinition);

    }

    protected abstract Object createBean(String beanName,BeanDefinition beanDefinition)throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
