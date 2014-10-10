
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;

public interface ITpTaskManager  extends IBaseManager<TpTaskInfo> {
    /**
     * ��ѯ�ѷ���������
     * @param t
     * @param presult
     * @return
     */
    List<TpTaskInfo> getTaskReleaseList(TpTaskInfo t,PageResult presult);

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

    public List<TpTaskInfo> getDoTaskResourceId(TpTaskInfo obj);
    /**
     * ɾ������ʱ�����������֣�ɾ����ɼ�¼
     * @param taskid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getDelTpStuTaskScore(final Long taskid, StringBuilder sqlbuilder);

} 
