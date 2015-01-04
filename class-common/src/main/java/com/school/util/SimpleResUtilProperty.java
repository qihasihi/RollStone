package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析msg.properties属性文件
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
	 * 双重检查加锁，提高了单纯使用synchronized的性能
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
     * 加载配置文件
     */
    public void getProperties() {
        prop = new java.util.Properties(); // 创建一个Properties 类的引用
        try {

            InputStream is = SimpleResUtilProperty.class.getResourceAsStream("/properties/SimpleResUtil.properties"); // 加载配置文件
            if(is==null)  //测试
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(2));
            prop.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
