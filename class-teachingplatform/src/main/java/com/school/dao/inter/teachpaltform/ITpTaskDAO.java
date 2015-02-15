
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.util.PageResult;

import java.util.List;

public interface ITpTaskDAO extends ICommonDAO<TpTaskInfo>{
    /**
     * ��ѯ�ѷ���������
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo> getTaskReleaseList(TpTaskInfo t,PageResult presult);

    /**
     * ��ѯ��������(ѧУ���ƣ�ѧ��)
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo> getTaskDetailList(TpTaskInfo t,PageResult presult);

    /**
     * ��ȡѧ������
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo>getListbyStu(TpTaskInfo t,PageResult presult);
    /**
     * ��ȡѧ�����񣬰���������������ɵ�����
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo>getUnionListbyStu(TpTaskInfo t,PageResult presult);
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpTaskInfo entity,StringBuilder sqlbuilder);

    public Boolean doSaveTaskMsg(TpTaskInfo t);

    /**
     * ��ѯ�ѷ����������Դid
     * */
    public List<TpTaskInfo> getDoTaskResourceId(TpTaskInfo obj);

    /**
     * ɾ������ʱ�����������֣�ɾ����ɼ�¼
     * @param taskid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getDelTpStuTaskScore(final Long taskid, StringBuilder sqlbuilder);


    /**
     * ��ȡ��Ҫ�����ѵ�����
     * @return
     */
    public List<TpTaskInfo> getTaskRemindList(TpTaskInfo t,PageResult presult);



    /**
     * ��ȡ�༶����ͳ��
     * @param t
     * @return
     */
    public List<TpTaskInfo> getTaskColumnByClass(TpTaskInfo t);

    /**
     * ��ȡ�༶����ͳ��
     * @param t
     * @return
     */
    public List<List<String>>  getTaskStatByClass(TpTaskInfo t);

    /**
     * ��ȡС������ͳ��
     * @param t
     * @return
     */
    public List<List<String>>  getTaskStatByGroup(TpTaskInfo t);

    /**
     * ��ȡδ����С������ͳ��
     * @param t
     * @return
     */
    public List<List<String>>  getTaskStatByNoGroup(TpTaskInfo t);

}
