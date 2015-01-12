
package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.GradeInfo;

import java.util.List;

public interface  IGradeDAO extends ICommonDAO<GradeInfo>{
    public List<GradeInfo> getTchGradeList(Integer userid,String year);

    /**
     * 管理员查询教师教务统计，获取年级
     * */
    public List<GradeInfo> getAdminPerformanceTeaGrade(Integer schoolid);

    /**
     * 管理员查询学生教务统计，获取年级
     * */
    public List<GradeInfo> getAdminPerformanceStuGrade(Integer schoolid);
}
