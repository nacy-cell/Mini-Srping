package org.springframework.bean.factory;

public interface InitializingBean {

    void afterPropertiesSet() throws Exception;
}
