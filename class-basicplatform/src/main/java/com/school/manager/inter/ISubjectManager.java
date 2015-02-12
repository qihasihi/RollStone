package com.school.manager.inter;

import com.school.entity.SubjectInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ISubjectManager extends IBaseManager<SubjectInfo>{
    public List<SubjectInfo> getHavaCourseSubject(String termid,String userref,int userid);

    /**
     * ����Ա����ͳ�ƽ�ʦ��ȡѧ��
     * */
    public List<SubjectInfo> getAdminPerformanceTeaSubject(Integer schoolid,Integer gradeid);

    /**
     * ����Ա����ͳ��ѧ����ȡѧ��
     * */
    public List<SubjectInfo> getAdminPerformanceStuSubject(Integer schoolid,Integer gradeid);

    /**
     * �����û�ID,��ݵõ���ǰ���ڵ�ѧ��
     * @param userid
     * @param year
     * @return
     */
    List<SubjectInfo> getListByUserYear(Integer userid, String year);
}
