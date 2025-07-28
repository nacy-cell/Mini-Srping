package org.springframework.bean.factory.support;

import org.springframework.bean.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注入BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

    /**
     * 查找Bean
     * @param beanName
     * @return
     */
    BeanDefinition getBeanDefinition(String beanName);



    /**
     * 是否包含指定名称的BeanDefinition
     *
     * @param beanName
     * @return
     */
    boolean containsBeanDefinition(String beanName);


    /**
     * 返回定义的所有bean的名称
     * @return
     */
    String[] getBeanDefinitionNames();
}
