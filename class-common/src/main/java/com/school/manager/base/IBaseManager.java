package com.school.manager.base;

import java.util.List;

import jxl.Sheet;

import com.school.manager.base.BaseManager;
import com.school.util.PageResult;
/**
 *
 * @author zhengzhou
 *
 */
public interface IBaseManager<T> {


    /**
     * 添加
     *
     * @param obj
     * @return
     */
    public abstract Boolean doSave(T obj);

    /**
     * 修改
     *
     * @param obj
     * @return
     */
    public abstract Boolean doUpdate(T obj);

    /**
     * 删除
     *
     * @param obj
     * @return
     */
    public abstract Boolean doDelete(T obj);

    /**
     * 查询
     *
     * @param obj
     * @param presult
     * @return
     */
    public abstract List<T> getList(T obj, PageResult presult);



    /**
     * 得到下一个序列值
     * @return
     */
    public abstract String getNextId();


    /**
     *
     * @param bo true:获取负值
     * @return
     */
    public Long getNextId(boolean bo);

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
     * 执行多个存储过程。
     *
     * @param sqlArrayList
     * @param objArrayList
     * @return
     */
    public Boolean doExcetueArrayNoTranProc(List<String> sqlArrayList,List<List<Object>> objArrayList);

    public abstract T getOfExcel(Sheet rs, int cols, int d,String type);

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
}