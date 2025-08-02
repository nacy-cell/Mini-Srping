package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.FactoryBean;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.config.BeanPostProcessor;
import org.springframework.bean.factory.config.ConfigurableBeanFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author saka
 *
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegister implements ConfigurableBeanFactory {

    private final List<BeanPostProcessor>  beanPostProcessors = new ArrayList<>();
    private final Map<String,Object> factoryBeansObjectCache=new HashMap<>();

    @Override
    public Object getBean(String beanName) throws BeansException {

        Object sharedInstance = getSingleton(beanName);

        if (sharedInstance != null) {
            //如果是FactoryBean，从FactoryBean#getObject中创建bean
            return getObjectForBeanInstance(sharedInstance, beanName);
        }

        BeanDefinition beanDefinition=getBeanDefinition(beanName);
        Object bean = createBean(beanName, beanDefinition);

        return getObjectForBeanInstance(bean, beanName);

    }

    protected Object getObjectForBeanInstance(Object beanInstance, String beanName) {
        Object bean = beanInstance;
        if (beanInstance instanceof FactoryBean<?> factoryBean) {
            try {
                if(factoryBean.isSingleton()) {
                    //singleton作用域bean，从缓存中获取
                    bean = factoryBeansObjectCache.get(beanName);
                    if (bean == null) {
                        bean = factoryBean.getObject();
                        factoryBeansObjectCache.put(beanName, bean);
                    }
                } else {
                    //prototype作用域bean，新创建bean
                    bean = factoryBean.getObject();
                }
            }catch (Exception e) {
                throw new BeansException("FactoryBean threw exception on object[" + beanName + "] creation", e);
            }
        }
        return  bean;
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return ((T) getBean(name));
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
