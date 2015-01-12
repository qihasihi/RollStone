package com.school.dao.inter;

import com.school.entity.SubjectInfo;

import java.util.List;

public interface ISubjectDAO extends com.school.dao.base.ICommonDAO<SubjectInfo>{
      public List<SubjectInfo> getHavaCourseSubject(String termid,String userref,int userid);

      /**
       * ����Ա����ͳ�ƽ�ʦ��ȡѧ��
       * */
      public List<SubjectInfo> getAdminPerformanceTeaSubject(Integer schoolid,Integer gradeid);

    /**
     * ����Ա����ͳ��ѧ����ȡѧ��
     * */
    public List<SubjectInfo> getAdminPerformanceStuSubject(Integer schoolid,Integer gradeid);
 }
