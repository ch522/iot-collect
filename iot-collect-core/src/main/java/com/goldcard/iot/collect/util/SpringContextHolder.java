package com.goldcard.iot.collect.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }

    public static <T> T getBean(String beanName, Class<T> tClass) {

        return applicationContext.getBean(beanName, tClass);

    }

    public static Boolean containsBean(String beanName) {
        return applicationContext.containsBean(beanName);
    }

    public static <T> T getBean(Class<?> clazz) {
        return (T) applicationContext.getBean(clazz);
    }

    public static <T> List<T> getBeanWithAnnotation(Class<? extends Annotation> clazz) {
        List<T> list = new ArrayList<>();
        Map<String, Object> map = applicationContext.getBeansWithAnnotation(clazz);
        for (Object serviceBean : map.values()) {
            list.add((T) serviceBean);
        }
        return list;
    }

    public static <T> List<T> getBeansWithType(Class<T> tClass) {
        List<T> list = new ArrayList<>();

        Map<String, T> map = applicationContext.getBeansOfType(tClass);
        list.addAll(map.values());
        return list;

    }
}
