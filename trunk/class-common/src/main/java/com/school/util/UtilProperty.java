package com.school.util;

import java.io.IOException;
/**
 * ����msg.properties�����ļ�
 * @author zhengzhou
 *
 */
public class UtilProperty {
	private static UtilProperty instance;

	public java.util.Properties prop = null;
	public UtilProperty(){}
	public UtilProperty(boolean chongxinjiazai){
		if(chongxinjiazai){
			instance=null;
		//	prop=null;			
		}
	}
	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * ˫�ؼ�����������˵���ʹ��synchronized������
	 * 
	 * @return
	 */

	public static UtilProperty getInstance() {
		if (instance == null) {
			synchronized (UtilProperty.class) {
				if (instance == null) {
					instance = new UtilProperty();
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
			prop.load(UtilProperty.class
					.getResourceAsStream("/properties/util.properties")); // ���������ļ�
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
