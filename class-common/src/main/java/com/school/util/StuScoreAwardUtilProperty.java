package com.school.util;

import java.io.IOException;

/**
 * ����msg.properties�����ļ�
 * @author zhengzhou
 *
 */
public class StuScoreAwardUtilProperty {
	private volatile static StuScoreAwardUtilProperty instance;

	public java.util.Properties prop = null;

	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * ˫�ؼ�����������˵���ʹ��synchronized������
	 * 
	 * @return
	 */

	public static StuScoreAwardUtilProperty getInstance() {
		if (instance == null) {
			synchronized (StuScoreAwardUtilProperty.class) {
				if (instance == null) {
					instance = new StuScoreAwardUtilProperty();
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
			prop.load(StuScoreAwardUtilProperty.class
					.getResourceAsStream("/properties/stuScoreAwardUtil.properties")); // ���������ļ�
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
