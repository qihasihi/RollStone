package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
            InputStream is =StuScoreAwardUtilProperty.class
                    .getResourceAsStream("/properties/stuScoreAwardUtil.properties");
            if(is==null)  //����
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(3));
            prop.load(is); // ���������ļ�
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
