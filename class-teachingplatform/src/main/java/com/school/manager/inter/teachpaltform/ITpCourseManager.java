
package  com.school.manager.inter.teachpaltform;

import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ITpCourseManager  extends IBaseManager<TpCourseInfo> {
    public List<TpCourseInfo> getTchCourseList(TpCourseInfo tpcourseinfo, PageResult presult);
    public List<TpCourseInfo> getCalanderCourseList(TpCourseInfo tpcourseinfo, PageResult presult);
    public List<TpCourseInfo> getStuCourseList(TpCourseInfo tpcourseinfo, PageResult presult);
    public List<TpCourseInfo> getTsList(TpCourseInfo tpcourseinfo, PageResult presult);
    List<TpCourseInfo> getCouresResourceList(TpCourseInfo tpcourseinfo, PageResult presult);
    public List<TpCourseInfo> getCourseQuestionList(TpCourseInfo tpcourseinfo,PageResult presult);
    public Boolean doCommentAndScore(TpCourseInfo tpcourseinfo);

    /**
     * ��ѯר���б�ʹ��
     * @param tpcourseinfo
     * @param presult
     * @return
     */
    public List<TpCourseInfo> getCourseList(TpCourseInfo tpcourseinfo, PageResult presult);

    /**
     * ��ѯ����ר��
     * @return
     */
    public List<Map<String,Object>> getRelatedCourseList(Integer materialid,Integer userid,String coursename);

    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpCourseInfo entity,StringBuilder sqlbuilder);

    /**
     * �õ��ƶ˹���ļ���
     * @param tpcourseinfo
     * @param presult
     * @return
     */
    public List<TpCourseInfo> getShareList(TpCourseInfo tpcourseinfo,PageResult presult);

    public boolean doUpdateShareCourse();

    List<Map<String,Object>>getCourseCalendar(Integer usertype,Integer userid,Integer dcschoolid,String year,String month,String gradeid,String subjectid,Integer classid,String termid);

    /**
     * �õ�ר������Ƿ�¼ȡ���
     * @param clsid
     * @param subjectid
     * @param carrayid
     * @param garrayid
     * @param roletype  NULL OR 1:��ʦ  2��ѧ��
     * @return
     * @author zhengzhou
     */
    public List<Map<String,Object>> getCourseScoreIsOver(final Integer clsid,final Integer subjectid,final String carrayid,String garrayid, Integer roletype);
}
