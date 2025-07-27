package org.springframework.bean.factory.support;

import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;

public class CglibSubclassingInstantiationStrategy implements  InstantiationStrategy {

    @Override
    public Object instantiate(BeanDefinition beanDefinition) throws BeansException {
        //TODO 使用Cglib动态生成子类
        throw new UnsupportedOperationException("CGLIB instantiation strategy is not supported now");
    }
}
