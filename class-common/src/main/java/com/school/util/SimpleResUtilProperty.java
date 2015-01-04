package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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

            InputStream is = SimpleResUtilProperty.class.getResourceAsStream("/properties/SimpleResUtil.properties"); // ���������ļ�
            if(is==null)  //����
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(2));
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
