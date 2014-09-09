package com.school.im1_1.manager._interface;

import com.school.dao.base.ICommonDAO;
import com.school.im1_1.dao.inter._interface.IImInterfaceDAO;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager.inter._interface.IImInterfaceManager;
import com.school.manager.base.BaseManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-7-25.
 */
@Service
public class ImInterfaceManager extends BaseManager<ImInterfaceInfo> implements IImInterfaceManager {
    private IImInterfaceDAO imInterfaceDAO;
    @Autowired
    @Qualifier("imInterfaceDAO")
    public void setImInterfaceDAO(IImInterfaceDAO imInterfaceDAO) {
        this.imInterfaceDAO = imInterfaceDAO;
    }

    @Override
    protected ICommonDAO<ImInterfaceInfo> getBaseDAO() {
        return this.imInterfaceDAO;
    }

    @Override
    public Boolean doSave(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public Boolean doUpdate(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public List<ImInterfaceInfo> getList(ImInterfaceInfo obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getUpdateSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public ImInterfaceInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getStudyModule(ImInterfaceInfo obj) {
        return this.imInterfaceDAO.getStudyModule(obj);
    }

    @Override
    public List<Map<String, Object>> getClassTaskCourse(ImInterfaceInfo obj) {
        return this.imInterfaceDAO.getClassTaskCourse(obj);
    }

    @Override
    public List<Map<String, Object>> getClassTaskTask(Long courseid,Integer userid,Integer classid) {
        return this.imInterfaceDAO.getClassTaskTask(courseid,userid,classid);
    }

    @Override
    public List<Map<String, Object>> getTaskInfo(Long taskid, Integer classid) {
        return this.imInterfaceDAO.getTaskInfo(taskid,classid);
    }

    @Override
    public List<Map<String, Object>> getTaskUserRecord(Long taskid, Integer classid, Integer isvir,Integer userid) {
        return this.imInterfaceDAO.getTaskUserRecord(taskid,classid,isvir,userid);
    }

    @Override
    public List<Map<String, Object>> getTeacherCalendar(Integer userid, Integer schoolid, Integer year, Integer month) {
        return this.imInterfaceDAO.getTeacherCalendar(userid,schoolid,year,month);
    }

    @Override
    public List<Map<String, Object>> getStudentCalendar(Integer userid, Integer schoolid, Integer classid, Integer year, Integer month) {
        return this.imInterfaceDAO.getStudentCalendar(userid,schoolid,classid,year,month);
    }

    @Override
    public List<Map<String, Object>> getTeacherCalendarDetail(Integer userid, Integer usertype, Integer schoolid, String time) {
        return this.imInterfaceDAO.getTeacherCalendarDetail(userid,usertype,schoolid,time);
    }

    @Override
    public List<Map<String, Object>> getstudentCalendarDetail(Integer userid, Integer usertype, Integer classid, Integer schoolid, String time) {
        return this.imInterfaceDAO.getstudentCalendarDetail(userid,usertype,classid,schoolid,time);
    }

    @Override
    public List<Map<String, Object>> getTopicUserRecord(Long topicid, Integer classid, Integer isvir, Integer userid) {
        return this.imInterfaceDAO.getTopicUserRecord(topicid,classid,isvir,userid);
    }

    @Override
    public List<Map<String, Object>> getQryStatPerson(ImInterfaceInfo obj) {
        return this.imInterfaceDAO.getQryStatPerson(obj);
    }

    @Override
    public List<Map<String, Object>> getQryStatPersonStu(ImInterfaceInfo obj) {
        return this.imInterfaceDAO.getQryStatPersonStu(obj);
    }

    @Override
    public List<Map<String, Object>> getTaskWatch(Integer userid, Long videoid) {
        return this.imInterfaceDAO.getTaskWatch(userid,videoid);
    }

    @Override
    public List<Map<String, Object>> getUnCompleteStu(Long taskid, Integer flag, Integer classid,Integer userid) {
        return this.imInterfaceDAO.getUnCompleteStu(taskid,flag,classid,userid);
    }
}
