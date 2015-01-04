package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ����msg.properties�����ļ�
 * @author ������
 *@date 2013.04.28
 */
public class EthosScoreRuleProperty {
	private volatile static EthosScoreRuleProperty instance;

	public java.util.Properties prop = null;
	
	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * ˫�ؼ�����������˵���ʹ��synchronized������
	 * 
	 * @return
	 */
	
	public static EthosScoreRuleProperty getInstance() {
		if (instance == null) {
			synchronized (EthosScoreRuleProperty.class) {
				if (instance == null) {
					instance = new EthosScoreRuleProperty();
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
            InputStream is =EthosScoreRuleProperty.class
                    .getResourceAsStream("/properties/ethosStudentScoreRule.properties");
            if(is==null)  //����
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(0));
            prop.load(is); // ���������ļ�
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
