package org.springframework.context;

import org.springframework.bean.factory.BeansException;

public interface ConfigurableApplicationContext extends ApplicationContext{


    /**
     * 刷新上下文，重新加载Bean定义和Bean实例
     * @throws BeansException
     */
    void refresh()throws BeansException;

}
