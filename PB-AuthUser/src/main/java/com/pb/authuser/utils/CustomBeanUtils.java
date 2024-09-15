package com.pb.authuser.utils;

import java.beans.PropertyDescriptor;
import org.springframework.beans.BeanWrapperImpl;

public class CustomBeanUtils {

    public static void copyNonNullProperties(Object src, Object target) {
        var source = new BeanWrapperImpl(src);
        var targetWrapper = new BeanWrapperImpl(target);

        for (PropertyDescriptor sourcePd : source.getPropertyDescriptors()) {
            var srcValue = source.getPropertyValue(sourcePd.getName());

            if (srcValue != null && targetWrapper.isWritableProperty(sourcePd.getName())) {
                targetWrapper.setPropertyValue(sourcePd.getName(), srcValue);
            }
        }
    }

}
