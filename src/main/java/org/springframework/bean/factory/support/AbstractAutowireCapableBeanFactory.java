package org.springframework.bean.factory.support;

import cn.hutool.core.bean.BeanUtil;
import org.springframework.bean.PropertyValue;
import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

import java.lang.reflect.Method;

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

            applyPropertyValue(beanName,bean,beanDefinition);
        }catch (Exception e) {
            throw new BeansException("Instantiation of bean failed",e);
        }

        addSingleton(beanName,bean);

        return bean;
    }

    protected void applyPropertyValue(String beanName, Object bean, BeanDefinition beanDefinition) {

        try {
            Class<?> beanClass = beanDefinition.getBeanClass();

            for(PropertyValue propertyValue:beanDefinition.getPropertyValues().getPropertyValues()) {

                String name = propertyValue.getName();
                Object value = propertyValue.getValue();

//                //通过反射获取该属性在类中的字段，并获取其数据类型
//                Class type=beanClass.getDeclaredField(name).getType();
//                //生成setter
//                String methodName="set"+name.substring(0,1).toUpperCase()+name.substring(1);
//                //反射获取对应的setter方法
//                Method method=beanClass.getDeclaredMethod(methodName,new Class[]{type});
//                //注入属性值
//                method.invoke(bean,new Object[]{value});

                BeanUtil.setFieldValue(bean,name,value);

            }
        }catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }

    }

    protected Object creatBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


}
