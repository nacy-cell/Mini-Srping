package org.springframework.context;

import org.springframework.bean.factory.Aware;
import org.springframework.bean.factory.BeansException;

public interface ApplicationContextAware extends Aware {

    /**
     * 设置 ApplicationContext
     * @param applicationContext
     * @throws BeansException
     */
    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;
}
