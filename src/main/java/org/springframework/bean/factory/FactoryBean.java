package org.springframework.bean.factory;

public interface FactoryBean<T> {

     T getObject() throws Exception;

    boolean isSingleton();

}
