package com.school.im1_1.manager.inter._interface;

import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-7-25.
 */
public interface IImInterfaceManager extends IBaseManager<ImInterfaceInfo> {
    public List<Map<String, Object>> getStudyModule(ImInterfaceInfo obj);
    public List<Map<String,Object>> getClassTaskCourse(ImInterfaceInfo obj);
    public List<Map<String,Object>> getClassTaskTask(Long courseid,Integer userid,Integer classid);
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
}
