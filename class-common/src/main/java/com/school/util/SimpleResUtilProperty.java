package com.school.util;

import java.io.IOException;

/**
 * ����msg.properties�����ļ�
 * @author zhengzhou
 *
 */
public class SimpleResUtilProperty {
	private volatile static SimpleResUtilProperty instance;

	public java.util.Properties prop = null;

	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * ˫�ؼ�����������˵���ʹ��synchronized������
	 * 
	 * @return
	 */

	public static SimpleResUtilProperty getInstance() {
		if (instance == null) {
			synchronized (SimpleResUtilProperty.class) {
				if (instance == null) {
					instance = new SimpleResUtilProperty();
					instance.getProperties();
				}
			}
		}
		return instance;
	}

	/**
	 * ���������ļ�
	 */
	public void getProperties() {
		prop = new java.util.Properties(); // ����һ��Properties �������
		try {
			prop.load(SimpleResUtilProperty.class
					.getResourceAsStream("/properties/SimpleResUtil.properties")); // ���������ļ�
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
