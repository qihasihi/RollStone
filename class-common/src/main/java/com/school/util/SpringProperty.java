package com.school.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * 解析spring.properties属性文件
 * @author zhengzhou
 *
 */
public class SpringProperty {
	private volatile static ApplicationContext instance;

	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * 双重检查加锁，提高了单纯使用synchronized的性能
	 * 
	 * @return
	 */

	public static ApplicationContext getInstance() {
		if (instance == null) {
			synchronized (SpringProperty.class) {
				if (instance == null) {
					 String path ="classpath:applicationContext.xml";
					 instance =new FileSystemXmlApplicationContext(path);;
				}
			}
		}
		return instance;
	}

	
}
