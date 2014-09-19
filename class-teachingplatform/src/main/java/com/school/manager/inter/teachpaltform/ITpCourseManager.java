
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
     * 查询专题列表使用
     * @param tpcourseinfo
     * @param presult
     * @return
     */
    public List<TpCourseInfo> getCourseList(TpCourseInfo tpcourseinfo, PageResult presult);

    /**
     * 查询关联专题
     * @return
     */
    public List<Map<String,Object>> getRelatedCourseList(Integer materialid,Integer userid,String coursename);

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseInfo entity,StringBuilder sqlbuilder);

    /**
     * 得到云端共享的集合
     * @param tpcourseinfo
     * @param presult
     * @return
     */
    public List<TpCourseInfo> getShareList(TpCourseInfo tpcourseinfo,PageResult presult);

    public boolean doUpdateShareCourse();

    List<Map<String,Object>>getCourseCalendar(Integer usertype,Integer userid,Integer dcschoolid,String year,String month,String gradeid,String subjectid,Integer classid,String termid);

    /**
     * 得到专题积分是否录取完毕
     * @param clsid
     * @param subjectid
     * @param carrayid
     * @param garrayid
     * @param roletype  NULL OR 1:老师  2：学生
     * @return
     * @author zhengzhou
     */
    public List<Map<String,Object>> getCourseScoreIsOver(final Integer clsid,final Integer subjectid,final String carrayid,String garrayid, Integer roletype);
}
