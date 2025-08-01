package org.springframework.bean.factory;

public interface BeanFactoryAware extends Aware{

    /**
     * 设置 BeanFactory
     * @param beanFactory
     */
    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
