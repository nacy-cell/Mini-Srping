package org.springframework.bean;

import java.util.ArrayList;
import java.util.List;

public class PropertyValues {

    private final List<PropertyValue> propertyValues=new ArrayList<>();

    public void addPropertyValue(PropertyValue propertyValue) {

        for (int i = 0; i < this.propertyValues.size(); i++) {
            PropertyValue currentPv = this.propertyValues.get(i);
            if (currentPv.getName().equals(propertyValue.getName())) {
                //覆盖原有的属性值
                this.propertyValues.set(i, propertyValue);
                return;
            }
        }

        this.propertyValues.add(propertyValue);
    }

    public PropertyValue[] getPropertyValues() {
        return propertyValues.toArray(new PropertyValue[0]);
    }

    public PropertyValue  getPropertyValue(String propertyName) {
        for (PropertyValue propertyValue : propertyValues) {
            if (propertyValue.getName().equals(propertyName)) {
                return propertyValue;
            }
        }
        return null;
    }


}
