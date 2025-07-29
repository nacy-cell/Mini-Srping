package test.org.springframework.common;


import org.springframework.bean.PropertyValue;
import org.springframework.bean.PropertyValues;
import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.ConfigurableListableBeanFactory;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.config.BeanFactoryPostProcessor;

/**
 * @author derekyi
 * @date 2020/11/28
 */
public class CustomBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("CustomBeanFactoryPostProcessor#postProcessBeanFactory");
		BeanDefinition personBeanDefiniton = beanFactory.getBeanDefinition("person");
		PropertyValues propertyValues = personBeanDefiniton.getPropertyValues();
		//将person的name属性改为ivy
		propertyValues.addPropertyValue(new PropertyValue("name", "ivy"));
	}

}
