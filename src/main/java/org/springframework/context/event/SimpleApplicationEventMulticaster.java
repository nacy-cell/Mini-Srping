package org.springframework.context.event;

import org.springframework.bean.factory.BeanFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class SimpleApplicationEventMulticaster extends  AbstractApplicationEventMulticaster {

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for(ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportsEvent(listener,event)) {
                listener.onApplicationEvent(event);
            }
        }
    }

    /**
     * 检查事件是否支持
     * @param event 事件
     * @return 是否支持
     */
    protected boolean supportsEvent(ApplicationListener listener,ApplicationEvent event) {
        //的第一个泛型接口中的第一个泛型参数的类名
        Type type = listener.getClass().getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) type).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();
        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("wrong event class name: " + className);
        }
        return eventClassName.isAssignableFrom(event.getClass());
    }

}
