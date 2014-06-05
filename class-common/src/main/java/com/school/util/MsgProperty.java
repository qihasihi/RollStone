package com.school.util;

import java.io.IOException;
/**
 * ����msg.properties�����ļ�
 * @author zhengzhou
 *
 */
public class MsgProperty {
	private volatile static MsgProperty instance;

	public java.util.Properties prop = null;

	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * ˫�ؼ�����������˵���ʹ��synchronized������
	 * 
	 * @return
	 */

	public static MsgProperty getInstance() {
		if (instance == null) {
			synchronized (MsgProperty.class) {
				if (instance == null) {
					instance = new MsgProperty();
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
			prop.load(MsgProperty.class
					.getResourceAsStream("/properties/msg.properties")); // ���������ļ�
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
