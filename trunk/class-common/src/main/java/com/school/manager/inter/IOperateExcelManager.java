package com.school.manager.inter;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.school.manager.base.IBaseManager;




public interface IOperateExcelManager {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bhsf.oa.manager.impl.IOperateExcelManager#ExplortExcel(java.util.
	 * List, java.util.List, java.lang.String, java.lang.Class)
	 */
	public  String ExplortExcel(HttpServletResponse response,
			String filename, List<String> columns, List list, String title,
			Class clz, String explortObject) throws Exception;

	/**
	 * �������� *
	 * 
	 * @param filename
	 *            �����ļ�������
	 * @param sheetNames
	 *            ÿ��Sheet������
	 * @param columns
	 *            ÿ��Sheet�������
	 * @param list
	 *            ����ֵ
	 * @param title
	 *            ����
	 * @param clz
	 *            ����������
	 * @param explortObject
	 *            �����ĳɼ�����
	 * @throws IOException
	 */
	public  String ExplortExcel(HttpServletResponse response,
			String filename, List<String> sheetNames,
			List<List<String>> columns, List list, List<String> title,
			List<Class<? extends Object>> clz, List<String> explortObject)
			throws Exception;

	/******************************** ����Excel �漰���� ************************************/
	public List<Object> includeExcel(String fileName,IBaseManager basemanager)throws Exception;


}