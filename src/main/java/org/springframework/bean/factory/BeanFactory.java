package org.springframework.bean.factory;

import cn.hutool.core.bean.BeanException;

import java.util.HashMap;
import java.util.Map;

public interface BeanFactory {

    /**
     *
     * @param beanName
     * @return bean实例
     * @throws BeanException bean不存在时
     */
    Object getBean(String beanName) throws BeanException;

}
