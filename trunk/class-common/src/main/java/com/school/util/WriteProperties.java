package com.school.util;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
public class WriteProperties {
	private static String fname="";	
	public WriteProperties(HttpServletRequest request,String pathname){		
		fname=request.getRealPath("/")+"/WEB-INF/classes/properties/"+pathname;
	}
	// ����key��ȡvalue
	public static String readValue( String key) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(fname));
			props.load(in);
			String value = props.getProperty(key);
			System.out.println(key +":"+ value);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ��ȡproperties��ȫ����Ϣ

	public static void readProperties(String filePath) {
		Map<String,Object> returnMap=new HashMap<String,Object>();
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				returnMap.put(key, Property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 

	// д��properties��Ϣ
	public static void writeProperties(String filePath, String parameterName,String parameterValue) {
		Properties prop = new Properties();
		try {
			InputStream fis = new FileInputStream(filePath);
			// ���������ж�ȡ�����б�(����Ԫ�ض�)
			prop.load(fis);
			// ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
			// ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
			// ���� Properties ���е������б�(����Ԫ�ض�)д�������
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			System.err.println("Visit "+filePath+" for updating "+parameterName+" value error");
		}
	}
    // д��properties��Ϣ
    public static void writeProperties(String filePath,Map<String,Object> map) {
        if(map==null||map.size()<1)return;
        Properties prop = new Properties();
        try {
            InputStream fis = new FileInputStream(filePath);
            // ���������ж�ȡ�����б�(����Ԫ�ض�)
            prop.load(fis);
            // ���� Hashtable �ķ��� put��ʹ�� getProperty �����ṩ�����ԡ�
            // ǿ��Ҫ��Ϊ���Եļ���ֵʹ���ַ���������ֵ�� Hashtable ���� put �Ľ����
            OutputStream fos = new FileOutputStream(filePath);
            Iterator<String> keySet=map.keySet().iterator();
            while(keySet.hasNext()){
                String key=keySet.next();
                prop.setProperty(key,map.get(key).toString());
            }
            // ���ʺ�ʹ�� load �������ص� Properties ���еĸ�ʽ��
            // ���� Properties ���е������б�(����Ԫ�ض�)д�������
            prop.store(fos,"Write Properties "+new Date().toString());
            fos.close();
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
