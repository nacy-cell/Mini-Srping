package test.org.springframework;

import org.junit.Test;
import org.springframework.bean.PropertyValue;
import org.springframework.bean.PropertyValues;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.support.DefaultListableBeanFactory;
import test.org.springframework.service.HelloService;

public class BeanDefinitionAndBeanDefinitionRegistryTest {

	@Test
	public void testBeanFactory() throws Exception {
		DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
		PropertyValues pvs = new PropertyValues();
		pvs.addPropertyValue(new PropertyValue("foo", "hello"));
		pvs.addPropertyValue(new PropertyValue("bar", "world"));
		BeanDefinition beanDefinition = new BeanDefinition(HelloService.class,pvs);

		beanFactory.registerBeanDefinition("helloService", beanDefinition);

		HelloService helloService = (HelloService) beanFactory.getBean("helloService");
		helloService.sayHello();
	}
}
