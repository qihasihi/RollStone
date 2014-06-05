
package  com.school.manager.inter.teachpaltform.interactive;

import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * �����ռ�����
 */
public interface ITpTopicManager  extends IBaseManager<TpTopicInfo> {
    /**
     * �õ������ռ���ҳ��ʾ��ר����Ŀ
     * @param userref ��ѯ�û���ref
     * @param termid  ѧ��id
     * @return
     */
    public List<Map<String,Object>> getListTopicIndex(String userref,String termid);

    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpTopicInfo entity,StringBuilder sqlbuilder);
    
    /**
     * ����CourseId�õ������ռ��ͳ��
     * @param courseid
     * @return
     */    
    public List<Map<String,Object>> getTopicStaticesPageIdx(Long courseid,String loginref,Integer roleInt);
    /**
     *�õ�����ͳ��ҳ������
     * @param topicid
     * @param clsid
     * @return
     */
    public List<Map<String,Object>> getTopicStatices(Long topicid,Integer clsid,Integer clstype);
    /**
     *�õ������ռ�༶��Ϣ
     * @param courseid
     * @param userid
     * @param roletype
     * @return
     */
    public List<Map<String,Object>> getInteractiveClass(Long courseid,Integer userid,Integer roletype);


} 
