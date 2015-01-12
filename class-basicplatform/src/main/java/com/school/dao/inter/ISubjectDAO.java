package com.school.dao.inter;

import com.school.entity.SubjectInfo;

import java.util.List;

public interface ISubjectDAO extends com.school.dao.base.ICommonDAO<SubjectInfo>{
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
