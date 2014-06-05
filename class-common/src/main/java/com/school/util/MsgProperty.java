package com.school.util;

import java.io.IOException;
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
			prop.load(MsgProperty.class
					.getResourceAsStream("/properties/msg.properties")); // 加载配置文件
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
