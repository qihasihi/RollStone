package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析msg.properties属性文件
 * @author 岳春阳
 *@date 2013.04.28
 */
public class EthosScoreRuleProperty {
	private volatile static EthosScoreRuleProperty instance;

	public java.util.Properties prop = null;
	
	/**
	 * 
	 * <b>Summary: </b>
	 * 
	 * 双重检查加锁，提高了单纯使用synchronized的性能
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
     * 加载配置文件
     */
    public void getProperties() {
        prop = new java.util.Properties(); // 创建一个Properties 类的引用
        try {
            InputStream is =EthosScoreRuleProperty.class
                    .getResourceAsStream("/properties/ethosStudentScoreRule.properties");
            if(is==null)  //测试
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(0));
            prop.load(is); // 加载配置文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
