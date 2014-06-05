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
	 * 创建正文 *
	 * 
	 * @param filename
	 *            导出文件的名称
	 * @param sheetNames
	 *            每个Sheet的名称
	 * @param columns
	 *            每个Sheet里的列名
	 * @param list
	 *            数据值
	 * @param title
	 *            标题
	 * @param clz
	 *            导出的类型
	 * @param explortObject
	 *            导出的成绩类型
	 * @throws IOException
	 */
	public  String ExplortExcel(HttpServletResponse response,
			String filename, List<String> sheetNames,
			List<List<String>> columns, List list, List<String> title,
			List<Class<? extends Object>> clz, List<String> explortObject)
			throws Exception;

	/******************************** 导入Excel 涉及方法 ************************************/
	public List<Object> includeExcel(String fileName,IBaseManager basemanager)throws Exception;


}