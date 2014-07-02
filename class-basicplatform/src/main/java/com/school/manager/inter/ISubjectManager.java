package com.school.manager.inter;

import com.school.entity.SubjectInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ISubjectManager extends IBaseManager<SubjectInfo>{
    public List<SubjectInfo> getHavaCourseSubject(String termid,String userref,int userid);
}
