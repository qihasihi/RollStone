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
     * ���
     *
     * @param obj
     * @return
     */
    public abstract Boolean doSave(T obj);

    /**
     * �޸�
     *
     * @param obj
     * @return
     */
    public abstract Boolean doUpdate(T obj);

    /**
     * ɾ��
     *
     * @param obj
     * @return
     */
    public abstract Boolean doDelete(T obj);

    /**
     * ��ѯ
     *
     * @param obj
     * @param presult
     * @return
     */
    public abstract List<T> getList(T obj, PageResult presult);



    /**
     * �õ���һ������ֵ
     * @return
     */
    public abstract String getNextId();


    /**
     *
     * @param bo true:��ȡ��ֵ
     * @return
     */
    public Long getNextId(boolean bo);

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
     * ִ�ж���洢���̡�
     *
     * @param sqlArrayList
     * @param objArrayList
     * @return
     */
    public Boolean doExcetueArrayNoTranProc(List<String> sqlArrayList,List<List<Object>> objArrayList);

    public abstract T getOfExcel(Sheet rs, int cols, int d,String type);

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
}