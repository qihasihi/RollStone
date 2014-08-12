package com.school.util;

import java.io.IOException;

/**
 * 解析msg.properties属性文件
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
	 * 双重检查加锁，提高了单纯使用synchronized的性能
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
	 * 加载配置文件
	 */
	public void getProperties() {
		prop = new java.util.Properties(); // 创建一个Properties 类的引用
		try {
			prop.load(StuScoreAwardUtilProperty.class
					.getResourceAsStream("/properties/stuScoreAwardUtil.properties")); // 加载配置文件
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
