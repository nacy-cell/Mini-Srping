package test.org.springframework.bean.factory;

import org.junit.Test;
import org.springframework.bean.PropertyValue;
import org.springframework.bean.PropertyValues;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.support.DefaultListableBeanFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class BeanFactoryTest {

    @Test
    public void testGetBean() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues pvs = new PropertyValues();
        pvs.addPropertyValue(new PropertyValue("foo", "hello"));
        pvs.addPropertyValue(new PropertyValue("bar", "world"));
        BeanDefinition beanDefinition = new BeanDefinition(HelloService.class,pvs);
        beanFactory.registerBeanDefinition("helloService", beanDefinition);

        HelloService helloService = (HelloService) beanFactory.getBean("helloService");
        assertThat(helloService).isNotNull();
        System.out.println(helloService);
        helloService.sayHello();
    }

    @Test
    public void testPopulateBeanWithPropertyValues() throws Exception {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("name", "derek"));
        propertyValues.addPropertyValue(new PropertyValue("age", 18));
        BeanDefinition beanDefinition = new BeanDefinition(Person.class, propertyValues);
        beanFactory.registerBeanDefinition("person", beanDefinition);

        Person person = (Person) beanFactory.getBean("person");
        System.out.println(person);
        assertThat(person.getName()).isEqualTo("derek");
        assertThat(person.getAge()).isEqualTo(18);
    }
}

