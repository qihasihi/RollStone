package com.school.dao.base;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import com.school.util.PageResult;

public interface ICommonDAO<T> {


	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#getParedStatement(java.sql.Connection,
	 * java.sql.PreparedStatement, java.lang.String, java.lang.Object)
	 */
//	public abstract PreparedStatement getParedStatement(Connection conn,
//			PreparedStatement pstatement, String sql, Object... paraValue);

	/*
	 * (non-Javadoc)
	 * ִ��SQL���  ����һ������
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeSalar(java.lang.String,
	 * java.lang.Object[])
	 */
	public abstract Object executeSalar_SQL(String sql, Object[] paraValue);

	/*
	 * (non-Javadoc)
	 *ִ�з���һ�������Ĵ洢����
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeProcedure(java.lang.Boolean,
	 * java.lang.String, java.lang.Object)
	 */
	public abstract Object executeSacle_PROC(String sql, Object... paraValue);

	/*
	 * (non-Javadoc)
	 * ִ�в����������صĴ洢����
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeProcedure(java.lang.Boolean,
	 * java.lang.String, java.lang.Object)
	 */
	public abstract boolean executeQuery_PROC(String sql, Object... paraValue);

	/*
	 * (non-Javadoc) 
	 * ִ�ж���洢���̡�
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#executeArratProcedure(java.util.List,
	 * java.util.List)
	 */
	public abstract boolean executeArrayQuery_PROC(List sqlbuilder, List paraV);

	/**
	 * ִ��MySql����List<map<String,Object>>
	 * @param sql
	 * @param objPara
	 * @return
	 */
	public abstract List<Map<String, Object>> queryListMap_SQL(String sql,
			List<Object> objPara);

	/**
	 * ִ��MySql����List<map<String,Object>>
	 * @param sql
	 * @param objPara
	 * @return
	 */
	public abstract List queryEntity_SQL(String sql, List<Object> objPara,
			Class<? extends Object> cls);

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeArraySQL(java.util.List,
	 * java.util.List)
	 */
	public abstract boolean executeArray_SQL(List sqlbuilder, List paraV);

	public abstract List<Map<String, Object>> executeResultListMap_PROC(
			String sql, List<Object> paraV);

	/**
	 * ִ�д洢���̣������ؽ����List<Entity> ��ѯ�Ƽ�ʹ��
	 * 
	 * @param sql
	 *            SQL���
	 * @param paraV
	 *            ִ�е�������
	 * @param oracleType
	 *            ����ֵ�����ͣ�û���� NULL
	 * @param clazz
	 *            ӳ���ʵ�� û��ʱ��NULL
	 * @param reObj
	 *            ʵ��������� ���ز���.�����NULL ������Ҫ��
	 */
	public abstract List executeResult_PROC(String sql, List paraV,
			List<Integer> oracleType, Class<? extends Object> clazz,
			Object[] reObj);

	/*
	 * (non-Javadoc) AUTHOR:֣��
	 * 
	 * @see com.bhsf.oa.dao.impl.IStatisticsDAO#executeList2(java.lang.String,
	 * java.lang.Object[])
	 */
	public abstract List<List<String>> executeList_SQL(String sql,
			Object[] paraValue);

	/*
	 * (non-Javadoc) ִ�д洢���̣������ؽ����List<List<String>>
	 * 
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#executeArratProcedure(java.util.List,
	 * java.util.List)
	 */
	public List<List<String>> executeResultProcedure(String sql, List paraV) ;
	/**
	 * ʵ��ת��
	 *
	 * @param rs
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public abstract List entityConvertor(ResultSet rs,
			Class<? extends Object> clazz) throws Exception;
	
	// //////////////////////////////����ʵ����Ҫʵ�ֵķ���

	/**
	 * ���
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean doSave(T obj);

	/**
	 * �޸�
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean doUpdate(T obj);

	/**
	 * ɾ��
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean doDelete(T obj);

	/**
	 * ��ѯ
	 * 
	 * @param obj
	 * @param presult
	 * @return
	 */
	public List<T> getList(T obj, PageResult presult);
	/**
	 * �õ���һ������ֵ
	 * @return
	 */
	public String getNextId();

    public Long getNextId(boolean bo);
	/**
	 * ִ�еõ����Ӧ�ķ���ֵ 
	 * @param returnType
	 * @param sql
	 * @param paraValue
	 * @return
	 */
	public Object executeSacle_PROC(Integer returnType,String sql, Object... paraValue);

    public Object executeSeq_PROC(String sql, Object... paraValue);
	
	/**
	 * �õ���ӵ�SQL
	 * @param obj
	 * @param sqlbuilder
	 * @return
	 */
	public abstract List<Object> getSaveSql(T obj,StringBuilder sqlbuilder);
	/**
	 * �õ��޸ĵ�SQL
	 * @param obj
	 * @param sqlbuilder
	 * @return
	 */
	public abstract List<Object> getUpdateSql(T obj,StringBuilder sqlbuilder);
	/**
	 * �õ�ɾ����SQL
	 * @param obj
	 * @param sqlbuilder
	 * @return
	 */
	public abstract List<Object> getDeleteSql(T obj,StringBuilder sqlbuilder);

	/**
	 * ִ�ж���洢���̡�
	 * 
	 * @param sqlArrayList
	 * @param objArrayList
	 * @return
	 */
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,List<List<Object>> objArrayList);
	
	
	/**
	 * ִ�����Log
	 * 
	 * @param operateUserref
	 *            ������
	 * @param table
	 *            �����ı�
	 * @param rowsid
	 *            ����������ID
	 * @param freevalue
	 *            ����֮ǰ��ֵ��ʲô
	 * @param currentVal
	 *            ����֮���ֵ��ʲô
	 * @param operateType
	 *            �������ͣ� add:��� update:�޸� delete:ɾ��
	 * @param remark
	 *            ��ע
	 * @return
	 */
	public Boolean executeAddOperateLog(String operateUserref, String table,
			String rowsid, String freevalue, String currentVal,
			String operateType, String remark);
	
	
	
	/**
	 * �õ����Log
	 * 
	 * @param operateUserref
	 *            ������
	 * @param table
	 *            �����ı�
	 * @param rowsid
	 *            ����������ID
	 * @param freevalue
	 *            ����֮ǰ��ֵ��ʲô
	 * @param currentVal
	 *            ����֮���ֵ��ʲô
	 * @param operateType
	 *            �������ͣ� add:��� update:�޸� delete:ɾ��
	 * @param remark
	 *            ��ע
	 * @return
	 */
	public List<Object> getAddOperateLog(String operateUserref, String table,
			String rowsid, String freevalue, String currentVal,
			String operateType, String remark,StringBuilder sqlbuilder);

	/**
	 * �õ����Log
	 * 
	 * @param operateUserref
	 *            ������
	 * @param table
	 *            �����ı�
	 * @param rowsid
	 *            ����������ID	
	 * @param operateType
	 *            �������ͣ� add:��� update:�޸� delete:ɾ��	
	 * @return
	 */
	public List<Object> getDelOperateLog(String operateUserref, String table,
			String rowsid,
			String operateType,StringBuilder sqlbuilder);
	
	
	/**
     * ���³��ֶ�(clob,text,blob��)
     * @param tblname ����
     * @param refName  �����г�
     * @param columnsName ���µ�����
     * @param value      ��Ӧ��ֵ
     * @param ref   ����ֵ
     * @param sqlList
     * @param objListArray
     */
    public void getArrayUpdateLongText(String tblname, String refName,
                                       String columnsName, String value, String ref,
                                       List<String> sqlList, List<List<Object>> objListArray);
    /**
	 * �����ۼ�
	 * @param tblname
	 * @param refname
	 * @param columnName
	 * @param topicid
	 * @param type NULl OR 1:+1 2:-1
	 * @param sqlbuilder
	 * @return
	 */
	public List<Object> getUpdateNumAdd(String tblname,String refname,
			String columnName,String topicid,Integer type,StringBuilder sqlbuilder);
}