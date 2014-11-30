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
	 * 执行SQL语句  返回一个参数
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeSalar(java.lang.String,
	 * java.lang.Object[])
	 */
	public abstract Object executeSalar_SQL(String sql, Object[] paraValue);

	/*
	 * (non-Javadoc)
	 *执行返回一个参数的存储过程
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeProcedure(java.lang.Boolean,
	 * java.lang.String, java.lang.Object)
	 */
	public abstract Object executeSacle_PROC(String sql, Object... paraValue);

	/*
	 * (non-Javadoc)
	 * 执行不带参数返回的存储过程
	 * @see com.bhsf.oa.dao.impl.ICommonDAO#executeProcedure(java.lang.Boolean,
	 * java.lang.String, java.lang.Object)
	 */
	public abstract boolean executeQuery_PROC(String sql, Object... paraValue);

	/*
	 * (non-Javadoc) 
	 * 执行多个存储过程。
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#executeArratProcedure(java.util.List,
	 * java.util.List)
	 */
	public abstract boolean executeArrayQuery_PROC(List sqlbuilder, List paraV);

	/**
	 * 执行MySql返回List<map<String,Object>>
	 * @param sql
	 * @param objPara
	 * @return
	 */
	public abstract List<Map<String, Object>> queryListMap_SQL(String sql,
			List<Object> objPara);

	/**
	 * 执行MySql返回List<map<String,Object>>
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
	 * 执行存储过程，并返回结果集List<Entity> 查询推荐使用
	 * 
	 * @param sql
	 *            SQL语句
	 * @param paraV
	 *            执行的语句参数
	 * @param oracleType
	 *            返回值的类型，没有则 NULL
	 * @param clazz
	 *            映射的实体 没有时，NULL
	 * @param reObj
	 *            实体的总数量 返回参数.如果是NULL 则如需要。
	 */
	public abstract List executeResult_PROC(String sql, List paraV,
			List<Integer> oracleType, Class<? extends Object> clazz,
			Object[] reObj);

	/*
	 * (non-Javadoc) AUTHOR:郑舟
	 * 
	 * @see com.bhsf.oa.dao.impl.IStatisticsDAO#executeList2(java.lang.String,
	 * java.lang.Object[])
	 */
	public abstract List<List<String>> executeList_SQL(String sql,
			Object[] paraValue);

	/*
	 * (non-Javadoc) 执行存储过程，并返回结果集List<List<String>>
	 * 
	 * @see
	 * com.bhsf.oa.dao.impl.ICommonDAO#executeArratProcedure(java.util.List,
	 * java.util.List)
	 */
	public List<List<String>> executeResultProcedure(String sql, List paraV) ;
	/**
	 * 实体转换
	 *
	 * @param rs
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public abstract List entityConvertor(ResultSet rs,
			Class<? extends Object> clazz) throws Exception;
	
	// //////////////////////////////所有实现数要实现的方法

	/**
	 * 添加
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean doSave(T obj);

	/**
	 * 修改
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean doUpdate(T obj);

	/**
	 * 删除
	 * 
	 * @param obj
	 * @return
	 */
	public Boolean doDelete(T obj);

	/**
	 * 查询
	 * 
	 * @param obj
	 * @param presult
	 * @return
	 */
	public List<T> getList(T obj, PageResult presult);
	/**
	 * 得到下一个序列值
	 * @return
	 */
	public String getNextId();

    public Long getNextId(boolean bo);
	/**
	 * 执行得到相对应的返回值 
	 * @param returnType
	 * @param sql
	 * @param paraValue
	 * @return
	 */
	public Object executeSacle_PROC(Integer returnType,String sql, Object... paraValue);

    public Object executeSeq_PROC(String sql, Object... paraValue);
	
	/**
	 * 得到添加的SQL
	 * @param obj
	 * @param sqlbuilder
	 * @return
	 */
	public abstract List<Object> getSaveSql(T obj,StringBuilder sqlbuilder);
	/**
	 * 得到修改的SQL
	 * @param obj
	 * @param sqlbuilder
	 * @return
	 */
	public abstract List<Object> getUpdateSql(T obj,StringBuilder sqlbuilder);
	/**
	 * 得到删除的SQL
	 * @param obj
	 * @param sqlbuilder
	 * @return
	 */
	public abstract List<Object> getDeleteSql(T obj,StringBuilder sqlbuilder);

	/**
	 * 执行多个存储过程。
	 * 
	 * @param sqlArrayList
	 * @param objArrayList
	 * @return
	 */
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,List<List<Object>> objArrayList);
	
	
	/**
	 * 执行添加Log
	 * 
	 * @param operateUserref
	 *            操作人
	 * @param table
	 *            操作的表
	 * @param rowsid
	 *            操作的主键ID
	 * @param freevalue
	 *            操作之前的值是什么
	 * @param currentVal
	 *            操作之后的值是什么
	 * @param operateType
	 *            操作类型： add:添加 update:修改 delete:删除
	 * @param remark
	 *            备注
	 * @return
	 */
	public Boolean executeAddOperateLog(String operateUserref, String table,
			String rowsid, String freevalue, String currentVal,
			String operateType, String remark);
	
	
	
	/**
	 * 得到添加Log
	 * 
	 * @param operateUserref
	 *            操作人
	 * @param table
	 *            操作的表
	 * @param rowsid
	 *            操作的主键ID
	 * @param freevalue
	 *            操作之前的值是什么
	 * @param currentVal
	 *            操作之后的值是什么
	 * @param operateType
	 *            操作类型： add:添加 update:修改 delete:删除
	 * @param remark
	 *            备注
	 * @return
	 */
	public List<Object> getAddOperateLog(String operateUserref, String table,
			String rowsid, String freevalue, String currentVal,
			String operateType, String remark,StringBuilder sqlbuilder);

	/**
	 * 得到添加Log
	 * 
	 * @param operateUserref
	 *            操作人
	 * @param table
	 *            操作的表
	 * @param rowsid
	 *            操作的主键ID	
	 * @param operateType
	 *            操作类型： add:添加 update:修改 delete:删除	
	 * @return
	 */
	public List<Object> getDelOperateLog(String operateUserref, String table,
			String rowsid,
			String operateType,StringBuilder sqlbuilder);
	
	
	/**
     * 更新长字段(clob,text,blob等)
     * @param tblname 表名
     * @param refName  主键列称
     * @param columnsName 更新的列名
     * @param value      对应的值
     * @param ref   主键值
     * @param sqlList
     * @param objListArray
     */
    public void getArrayUpdateLongText(String tblname, String refName,
                                       String columnsName, String value, String ref,
                                       List<String> sqlList, List<List<Object>> objListArray);
    /**
	 * 数量累计
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