package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory {

    private InstantiationStrategy instantiationStrategy=new SimpleInstantiationStrategy();

    @Override
    protected Object createBean(String beanName,BeanDefinition beanDefinition) throws BeansException {
        return doCreat(beanName,beanDefinition);
    }


    protected Object doCreat(String beanName,BeanDefinition beanDefinition) throws BeansException {
        Object bean = null;
        Class beanClass = beanDefinition.getBeanClass();
        try {
            bean=creatBeanInstance(beanDefinition);
        }catch (Exception e) {
            throw new BeansException("Instantiation of bean failed",e);
        }

        addSingleton(beanName,bean);

        return bean;
    }

    private Object creatBeanInstance(BeanDefinition beanDefinition) {
        return instantiationStrategy.instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


}
