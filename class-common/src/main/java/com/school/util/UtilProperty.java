package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

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
            InputStream is =UtilProperty.class
                    .getResourceAsStream("/properties/util.properties");
            if(is==null)  //����
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(4));
            prop.load(is); // ���������ļ�
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
