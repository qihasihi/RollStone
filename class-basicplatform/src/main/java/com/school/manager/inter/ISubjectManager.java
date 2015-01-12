package com.school.manager.inter;

import com.school.entity.SubjectInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ISubjectManager extends IBaseManager<SubjectInfo>{
    public List<SubjectInfo> getHavaCourseSubject(String termid,String userref,int userid);

    /**
     * 管理员教务统计教师获取学科
     * */
    public List<SubjectInfo> getAdminPerformanceTeaSubject(Integer schoolid,Integer gradeid);

    /**
     * 管理员教务统计学生获取学科
     * */
    public List<SubjectInfo> getAdminPerformanceStuSubject(Integer schoolid,Integer gradeid);
}
