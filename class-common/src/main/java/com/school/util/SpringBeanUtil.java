package com.school.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by zhengzhou on 13-12-31.
 */
public class SpringBeanUtil implements ApplicationContextAware {


    private static ApplicationContext applicationContext;
    public static Object getBean(String beanName) {
        if(applicationContext!=null&&applicationContext.containsBean(beanName)){
            return applicationContext.getBean(beanName);
        }
        return null;
    }

    public static <T> T getBean(Class<T> clazz){
        if(applicationContext==null)return null;
        String[] beanNameArray=applicationContext.getBeanNamesForType(clazz);
        if(beanNameArray!=null&&beanNameArray.length>0){
           return clazz.cast(getBean(beanNameArray[0]));
        }
        return null;
    }

    /**
     * 根据BeanName,Class得到实例
     * @param beanName
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBean(String beanName, Class<T> clazz) {
        if(applicationContext==null)return null;
        String springBeanName=null;
        Object obj=null;
        String[] beanNameArray=applicationContext.getBeanNamesForType(clazz);
        if(beanNameArray!=null&&beanNameArray.length>0){
            for (String bname:beanNameArray){
                if(bname!=null&&bname.trim().length()>0){
                    if(bname.trim().toLowerCase().equals(beanName.trim().toLowerCase())){
                        springBeanName=bname;
                        break;
                    }
                }
            }
            obj=getBean(springBeanName);
        }
        if(obj!=null)
            return clazz.cast(obj);
        return null;
    }
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringBeanUtil.applicationContext = applicationContext;
    }
}


