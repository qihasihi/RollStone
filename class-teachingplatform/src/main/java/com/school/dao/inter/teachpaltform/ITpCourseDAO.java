
package com.school.dao.inter.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ITpCourseDAO extends ICommonDAO<TpCourseInfo>{
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

    List<Map<String,Object>>getCourseCalendar(Integer usertype,Integer userid,Integer dcschoolid,String year,String month,String gradeid,String subjectid);



    boolean doUpdateShareCourse();
}
