package com.school.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
/**
 * ����spring.properties�����ļ�
 * @author zhengzhou
 *
 */
public class SpringProperty {
	private volatile static ApplicationContext instance;

	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * ˫�ؼ�����������˵���ʹ��synchronized������
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
