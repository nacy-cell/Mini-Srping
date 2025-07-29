package org.springframework.context.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.ConfigurableListableBeanFactory;
import org.springframework.bean.factory.config.BeanFactoryPostProcessor;
import org.springframework.bean.factory.config.BeanPostProcessor;
import org.springframework.bean.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        refreshBeanFactory();
        ConfigurableListableBeanFactory beanFactory=getBeanFactory();

        // 在bean实例化之前，执行BeanFactoryPostProcessor
        invokeBeanFactoryPostProcessors(beanFactory);

        // BeanPostProcessor需要提前与其他bean实例化之前注册
        registerBeanPostProcessors(beanFactory);

        beanFactory.preInstantiateSingletons();

    }

    protected abstract void refreshBeanFactory()throws BeansException;

    /**
     * 在bean实例化之前，执行BeanFactoryPostProcessor
     *
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = beanFactory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactoryPostProcessorMap.values()) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
        }
    }

    /**
     * 注册BeanPostProcessor
     *
     * @param beanFactory
     */
    protected void registerBeanPostProcessors(ConfigurableListableBeanFactory beanFactory) {
        Map<String, BeanPostProcessor> beanPostProcessorMap = beanFactory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor beanPostProcessor : beanPostProcessorMap.values()) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
        }
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    public abstract ConfigurableListableBeanFactory getBeanFactory();


}
