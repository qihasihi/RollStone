
package  com.school.manager.inter;

import com.school.entity.GradeInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IGradeManager  extends IBaseManager<GradeInfo> {

    public List<GradeInfo> getTchGradeList(Integer userid,String year);

    /**
     * 管理员查询教师教务统计，获取年级
     * */
    public List<GradeInfo> getAdminPerformanceTeaGrade(Integer schoolid);

    /**
     * 管理员查询学生教务统计，获取年级
     * */
    public List<GradeInfo> getAdminPerformanceStuGrade(Integer schoolid);
    /**
     * 根据用户ID,年份得到当前所在的年级
     * @param userid
     * @param year
     * @return
     */
    List<GradeInfo> getListByUserYear(Integer userid, String year);
}
