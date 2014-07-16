package com.school.manager.base;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.base.ICommonDAO;
import com.school.manager.OperateExcelManager;
import com.school.manager.inter.IOperateExcelManager;
import com.school.util.PageResult;
/**
 * 
 * @author zhengzhou
 *
 */
public abstract class  BaseManager<T> implements IBaseManager<T> {


	
	/******************* 抽象声明区 **************************/
	
	protected abstract ICommonDAO<T> getBaseDAO();
	/* (non-Javadoc)
	 * @see com.school.manager.base.IBaseManager#doSave(T)
	 */
	public abstract Boolean doSave(T obj);

	/* (non-Javadoc)
	 * @see com.school.manager.base.IBaseManager#doUpdate(T)
	 */
	public abstract Boolean doUpdate(T obj);

	/* (non-Javadoc)
	 * @see com.school.manager.base.IBaseManager#doDelete(T)
	 */
	public abstract Boolean doDelete(T obj);

	/* (non-Javadoc)
	 * @see com.school.manager.base.IBaseManager#getList(T, com.school.util.PageResult)
	 */
	public abstract List<T> getList(T obj, PageResult presult);
	
	public String getNextId(){
		 return this.getBaseDAO().getNextId();
	}

    public Long getNextId(boolean bo){
        return this.getBaseDAO().getNextId(bo);
    }
	
	/**
	 * 执行多个存储过程。
	 * 
	 * @param sqlArrayList
	 * @param objArrayList
	 * @return
	 */
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,List<List<Object>> objArrayList){
		if(this.getBaseDAO()==null)
			return null;
		return this.getBaseDAO().doExcetueArrayProc(sqlArrayList, objArrayList);
	}
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
			String operateType, String remark){
		return this.getBaseDAO().executeAddOperateLog(operateUserref, table, rowsid, freevalue, currentVal, operateType, remark);
	}
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
			String operateType, String remark,StringBuilder sqlbuilder){
		return this.getBaseDAO().getAddOperateLog(operateUserref, table, rowsid, freevalue, currentVal, operateType, remark, sqlbuilder);
	}
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
			String operateType,StringBuilder sqlbuilder){
		return this.getBaseDAO().getDelOperateLog(operateUserref, table, rowsid, operateType, sqlbuilder);
	}
	
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
                                       List<String> sqlList, List<List<Object>> objListArray){
        this.getBaseDAO().getArrayUpdateLongText(tblname,refName,columnsName,value,ref,sqlList,objListArray);
    }
    
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
			String columnName,String topicid,Integer type,StringBuilder sqlbuilder){
		return	this.getBaseDAO().getUpdateNumAdd(tblname, refname, columnName, topicid, type, sqlbuilder);
	}
	
	
}
