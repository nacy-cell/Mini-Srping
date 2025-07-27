package test.org.springframework.bean.factory;

import org.junit.Test;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.support.DefaultListableBeanFactory;

public class BeanFactoryTest {

    @Test
    public void testBeanFactory() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        helloService.sayHello();
    }
}