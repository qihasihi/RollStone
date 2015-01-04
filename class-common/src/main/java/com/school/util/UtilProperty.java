package com.school.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 解析msg.properties属性文件
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
	 * 双重检查加锁，提高了单纯使用synchronized的性能
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
     * 加载配置文件
     */
    public void getProperties() {
        prop = new java.util.Properties(); // 创建一个Properties 类的引用
        try {
            InputStream is =UtilProperty.class
                    .getResourceAsStream("/properties/util.properties");
            if(is==null)  //测试
                is=new FileInputStream(PropertiesFileConfig.getPropertiesConfigPath(4));
            prop.load(is); // 加载配置文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
