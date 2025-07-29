package org.springframework.context.support;

import org.springframework.bean.factory.support.DefaultListableBeanFactory;
import org.springframework.bean.factory.xml.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {

    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations = getConfigLocations();
        if (configLocations != null) {
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
