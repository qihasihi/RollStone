package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析msg.properties属性文件
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
	 * 双重检查加锁，提高了单纯使用synchronized的性能
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
     * 加载配置文件
     */
    public void getProperties() {
        prop = new java.util.Properties(); // 创建一个Properties 类的引用
        try {

            InputStream is =MsgProperty.class
                    .getResourceAsStream("/properties/msg.properties");
            if(is==null)  //测试
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(1));
            prop.load(is); // 加载配置文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
