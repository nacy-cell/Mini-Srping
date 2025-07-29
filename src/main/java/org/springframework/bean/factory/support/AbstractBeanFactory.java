package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.config.BeanPostProcessor;
import org.springframework.bean.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author saka
 *
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegister implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor>  beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String beanName) throws BeansException {

        Object bean = getSingleton(beanName);

        if (bean != null) {
            return bean;
        }

        BeanDefinition beanDefinition=getBeanDefinition(beanName);

        return createBean(beanName,beanDefinition);

    }

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        beanPostProcessors.remove(beanPostProcessor);
        beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }
    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition)throws BeansException;

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;
}
