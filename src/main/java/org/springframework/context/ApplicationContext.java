package org.springframework.context;

import org.springframework.bean.factory.HierarchicalBeanFactory;
import org.springframework.bean.factory.ListableBeanFactory;
import org.springframework.core.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory, ResourceLoader {

}
