package com.school.dao.inter;

import com.school.entity.SubjectInfo;

import java.util.List;

public interface ISubjectDAO extends com.school.dao.base.ICommonDAO<SubjectInfo>{
      public List<SubjectInfo> getHavaCourseSubject(String termid,String userref,int userid);
}
