package org.springframework.bean.factory.xml;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import org.springframework.bean.PropertyValue;
import org.springframework.bean.factory.BeansException;
import org.springframework.bean.factory.config.BeanDefinition;
import org.springframework.bean.factory.config.BeanReference;
import org.springframework.bean.factory.support.AbstractBeanDefinitionReader;
import org.springframework.bean.factory.support.BeanDefinitionRegistry;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

public class XmlBeanDefinitionReader extends AbstractBeanDefinitionReader {

    public static final String BEAN_ELEMENT="bean";
    public static final String PROPERTY_ELEMENT="property";
    public static final String ID_ATTRIBUTE="id";
    public static final String NAME_ATTRIBUTE="name";
    public static final String CLASS_ATTRIBUTE="class";
    public static final String VALUE_ATTRIBUTE="value";
    public static final String REF_ATTRIBUTE="ref";
    public static final String INIT_METHOD_ATTRIBUTE = "init-method";
    public static final String DESTROY_METHOD_ATTRIBUTE = "destroy-method";



    public XmlBeanDefinitionReader(BeanDefinitionRegistry beanDefinitionRegistry) {
        super(beanDefinitionRegistry);
    }

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        super(registry, resourceLoader);
    }

    @Override
    public void loadBeanDefinitions(Resource resource) throws BeansException {
        try {
            InputStream inputStream =resource.getInputStream();
            try {
                doLoadBeanDefinitions(inputStream);
            }finally {
                inputStream.close();
            }
        }catch (Exception e){
            throw new BeansException("IOException parsing XML document from " + resource, e);
        }
    }

    private void doLoadBeanDefinitions(InputStream inputStream){

        Document document = XmlUtil.readXML(inputStream);
        Element rootElement = document.getDocumentElement();
        NodeList childNodes = rootElement.getChildNodes();
        /**示例
         <bean id="dataSource" class="com.example.db.MyDataSource" name="mainDS,primaryDataSource">
             <property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
             <property name="url" value="jdbc:mysql://localhost:3306/db?useSSL=false"/>
             <property name="username" value="admin"/>
             <property name="password" value="securePass123"/>
             <property name="maxPoolSize" value="20"/>
             <property name="minIdle" value="5"/>
         </bean>

         <bean id="userDao" class="com.example.dao.UserDaoImpl">
             <property name="dataSource" ref="dataSource"/>
             <property name="cacheEnabled" value="true"/>
             <property name="queryTimeout" value="30"/>
         </bean>
         */
        for (int i = 0; i < childNodes.getLength(); i++) {
            if(childNodes.item(i) instanceof Element){
                if(childNodes.item(i).getNodeName().equals(BEAN_ELEMENT)){
                    Element beanElement = (Element) childNodes.item(i);
                    String id = beanElement.getAttribute(ID_ATTRIBUTE);
                    String name = beanElement.getAttribute(NAME_ATTRIBUTE);
                    String className = beanElement.getAttribute(CLASS_ATTRIBUTE);
                    String initMethodName = beanElement.getAttribute(INIT_METHOD_ATTRIBUTE);
                    String destroyMethodName = beanElement.getAttribute(DESTROY_METHOD_ATTRIBUTE);

                    // 根据类名反射获取对应的Class对象
                    Class<?> clazz = null;
                    try {
                        clazz = Class.forName(className);
                    } catch (ClassNotFoundException e) {
                        throw new BeansException("Cannot find class [" + className + "]");
                    }
                    //id优先于name
                    String beanName = StrUtil.isNotEmpty(id)?id:name;
                    if(StrUtil.isEmpty(beanName)){
                        //如果id和name都为空，将类名的第一个字母转为小写后作为bean的名称
                        beanName=StrUtil.lowerFirst(clazz.getSimpleName());
                    }

                    BeanDefinition beanDefinition = new BeanDefinition(clazz);

                    beanDefinition.setInitMethodName(initMethodName);
                    beanDefinition.setDestroyMethodName(destroyMethodName);

                    for(int j=0;j<beanElement.getChildNodes().getLength();j++){
                        if(beanElement.getChildNodes().item(j) instanceof Element){
                            if (PROPERTY_ELEMENT.equals(((Element) beanElement.getChildNodes().item(j)).getNodeName())) {
                                Element property = (Element) beanElement.getChildNodes().item(j);
                                String nameAttribute = property.getAttribute(NAME_ATTRIBUTE);
                                String valueAttribute = property.getAttribute(VALUE_ATTRIBUTE);
                                String refAttribute = property.getAttribute(REF_ATTRIBUTE);

                                if (StrUtil.isEmpty(nameAttribute)) {
                                    throw new BeansException("The name attribute cannot be null or empty");
                                }

                                Object value = valueAttribute;
                                if (StrUtil.isNotEmpty(refAttribute)) {
                                    value = new BeanReference(refAttribute);
                                }

                                PropertyValue propertyValue = new PropertyValue(nameAttribute, value);
                                beanDefinition.getPropertyValues().addPropertyValue(propertyValue);

                            }
                        }
                    }

                    if (getRegistry().containsBeanDefinition(beanName)) {
                        //beanName不能重名
                        throw new BeansException("Duplicate beanName[" + beanName + "] is not allowed");
                    }
                    getRegistry().registerBeanDefinition(beanName, beanDefinition);

                }
            }
        }
    }

    @Override
    public void loadBeanDefinitions(String location) throws BeansException {
        ResourceLoader resourceLoader = getResourceLoader();
        Resource resource = resourceLoader.getResource(location);
        loadBeanDefinitions(resource);
    }
}
