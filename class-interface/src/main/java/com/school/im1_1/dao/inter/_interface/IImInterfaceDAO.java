package com.school.im1_1.dao.inter._interface;

import com.school.dao.base.ICommonDAO;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import java.util.*;

/**
 * Created by yuechunyang on 14-7-25.
 */
public interface IImInterfaceDAO extends ICommonDAO<ImInterfaceInfo>{
    public List<Map<String ,Object>> getStudyModule(ImInterfaceInfo obj);
    public List<Map<String,Object>> getClassTaskCourse(ImInterfaceInfo obj);
    public List<Map<String,Object>> getClassTaskTask(String courseid,Integer userid,Integer classid);
    public List<Map<String,Object>> getClassTaskTaskOld(String courseid,Integer userid,Integer classid);
    public List<Map<String,Object>> getTaskInfo(Long taskid,Integer classid);
    public List<Map<String,Object>> getTaskUserRecord(Long taskid,Integer classid,Integer isvir,Integer userid);
    public List<Map<String,Object>> getTeacherCalendar(Integer userid,Integer schoolid,Integer year,Integer month);
    public List<Map<String,Object>> getStudentCalendar(Integer userid,Integer schoolid,Integer classid,Integer year,Integer month);
    public List<Map<String,Object>> getTeacherCalendarDetail(Integer userid,Integer usertype,Integer schoolid,String time);
    public List<Map<String,Object>> getstudentCalendarDetail(Integer userid,Integer usertype,Integer classid,Integer schoolid,String time);
    public List<Map<String,Object>> getTopicUserRecord(Long topicid,Integer classid,Integer isvir,Integer userid);

    public List<Map<String ,Object>> getQryStatPerson(ImInterfaceInfo obj);
    public List<Map<String ,Object>> getQryStatPersonStu(ImInterfaceInfo obj);

    public List<Map<String,Object>> getTaskWatch(Integer userid,Long videoid);

    //未完成任务人员
    public List<Map<String,Object>> getUnCompleteStu(Long taskid,Integer flag,Integer classid,Integer userid);
    //班级统计 学科列表
    public List<Map<String,Object>>getStuScoreSubjectList(Integer classid,String userid);
    //任务提醒详情
    public List<Map<String,Object>> getTaskRemind(Long taskid,Integer userid,Integer classid);

    //获取发专题前的信息1.1.5
    public List<Map<String,Object>> getClassInfoForCourse(String userid,Integer classid);

    //获取修改专题前的信息1.1.5
    public List<Map<String,Object>> getUpdateCourse(Long courseid,Integer classid);

    //验证学生年级
    public List<Map<String,Object>> validateGrade(String userid);
}
