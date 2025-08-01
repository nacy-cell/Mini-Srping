package org.springframework.bean.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.bean.PropertyValue;
import org.springframework.bean.factory.BeanFactoryAware;
import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.DisposableBean;
import org.springframework.bean.factory.InitializingBean;
import org.springframework.bean.factory.config.AutowireCapableBeanFactory;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.config.BeanPostProcessor;
import org.springframework.bean.factory.config.BeanReference;

import java.lang.reflect.Method;

public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory implements AutowireCapableBeanFactory {

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
            //执行bean的初始化方法和BeanPostProcessor的前置和后置处理方法
            initializeBean(beanName,bean,beanDefinition);
        }catch (Exception e) {
            throw new BeansException("Instantiation of bean failed",e);
        }

        //注册有销毁方法的bean
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);

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

                // beanA依赖beanB，先实例化beanB
                if(value instanceof BeanReference) {

                    BeanReference beanReference = (BeanReference) value;
                    value=getBean(beanReference.getBeanName());
                }

                BeanUtil.setFieldValue(bean,name,value);

            }
        }catch (Exception e) {
            throw new BeansException("Error setting property values for bean: " + beanName, e);
        }

    }

    /**
     * 注册有销毁方法的bean，即bean继承自DisposableBean或有自定义的销毁方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     */
    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
    }


    protected Object creatBeanInstance(BeanDefinition beanDefinition) {
        return getInstantiationStrategy().instantiate(beanDefinition);
    }


    protected Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        if(bean instanceof BeanFactoryAware) {
            ((BeanFactoryAware) bean).setBeanFactory(this);
        }

        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);

        try {
            invokeInitMethods(beanName,wrappedBean,beanDefinition);
        }catch (Throwable ex) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", ex);
        }


        wrappedBean=applyBeanPostProcessorsAfterInitialization(bean, beanName);

        return wrappedBean;
    }


    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object bean, String beanName) throws BeansException {

        Object result=bean;
        for(BeanPostProcessor beanPostProcessor :getBeanPostProcessors()) {
            Object current= beanPostProcessor.postProcessBeforeInitialization(bean, beanName);
            if(current==null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object bean, String beanName) throws BeansException {
        Object result = bean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (current == null) {
                return result;
            }
            result = current;
        }
        return result;
    }

    /**
     * 执行bean的初始化方法
     *
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @throws Throwable
     */
    protected void invokeInitMethods(String beanName, Object bean, BeanDefinition beanDefinition) throws Throwable {

        if (bean instanceof InitializingBean) {
            ((InitializingBean) bean).afterPropertiesSet();
        }

        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = ClassUtil.getPublicMethod(beanDefinition.getBeanClass(), initMethodName);
            if (initMethod == null) {
                throw new BeansException("Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }


    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


}
