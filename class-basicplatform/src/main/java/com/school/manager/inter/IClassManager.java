
package  com.school.manager.inter;

import com.school.entity.AdminPerformance;
import com.school.entity.ClassInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface IClassManager  extends IBaseManager<ClassInfo> {
    /**
     * 根据UserRef,YEAR,PAttern得到班级
     * @param userref
     * @param year
     * @param pattern
     * @return
     */
    public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern);
    /**
     * 升级操作
     * @param year
     * @return
     */
    public Boolean doClassLevelUp(String year,Integer dcschoolid);

    /**
     * 得到更新或添加的SQL
     * @param obj;
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder);

    /**
     * 得到已建立的班级数量
     * @param schoolId 分校id
     * @param year 学年的值
     * @return 已有的班级数量
     */
    public int getTotalClass(int schoolId, String year, int from) ;

    public List<ClassInfo> getIm113List(ClassInfo cls, PageResult presult);

    public List<ClassInfo> getClassByGradeTerm(int gradeId,int termId);

    public List<ClassInfo> getAllTerm();

    /**
     * 管理员查询班级任务统计
     * */
    public List<AdminPerformance> getAdminPerformance(ClassInfo obj,PageResult presult);

    /**
     * 管理员查询教师班级任务统计,获取班级
     * */
    public List<ClassInfo> getAdminPerformanceTeaClass(Integer schoolid,Integer gradeid,Integer subjectid);

    /**
     * 管理员查询学生班级任务统计,获取班级
     * */
    public List<ClassInfo> getAdminPerformanceStuClass(Integer schoolid,Integer gradeid,Integer subjectid);

    /**
     * 管理员查询班级学生任务统计
     * */
    public List<List<String>> getAdminPerformanceStu(ClassInfo obj,PageResult presult);

    /**
     * 管理员查询学生任务统计，获取列头
     * */
    public List<Map<String,Object>> getAdminPerformanceStuCol(ClassInfo obj);
}
