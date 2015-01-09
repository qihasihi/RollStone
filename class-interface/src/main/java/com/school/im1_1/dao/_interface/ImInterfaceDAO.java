package com.school.im1_1.dao._interface;

import com.school.dao.base.CommonDAO;
import com.school.im1_1.dao.inter._interface.IImInterfaceDAO;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-7-25.
 */
@Component
public class ImInterfaceDAO extends CommonDAO<ImInterfaceInfo> implements IImInterfaceDAO {
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
    public List<Map<String, Object>> getStudyModule(ImInterfaceInfo obj) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_studymodule_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUserid());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getUsertype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUsertype());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getSchoolid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getSchoolid());
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getClassTaskCourse(ImInterfaceInfo obj) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_classtask_proc_getcourse(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else{
            sqlbuilder.append("null,");
        }
//        if(obj.getSchoolid()!=null){
//            sqlbuilder.append("?");
//            objList.add(obj.getSchoolid());
//        }else{
//            sqlbuilder.append("null");
//        }
        if(obj.getUserid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getUserid());
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getClassTaskTask(String courseid,Integer userid,Integer classid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_classtask_proc_task(");
        List<Object> objList=new ArrayList<Object>();
        if(courseid!=null){
            sqlbuilder.append("?,");
            objList.add(courseid);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getClassTaskTaskOld(String courseid, Integer userid, Integer classid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_classtask_proc_task_old(");
        List<Object> objList=new ArrayList<Object>();
        if(courseid!=null){
            sqlbuilder.append("?,");
            objList.add(courseid);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getTaskInfo(Long taskid, Integer classid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_taskinfo_proc_gettask(");
        List<Object> objList=new ArrayList<Object>();
        if(taskid!=null){
            sqlbuilder.append("?,");
            objList.add(taskid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getTaskUserRecord(Long taskid, Integer classid, Integer isvir,Integer userid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_taskinfo_proc_getuserrecord_list(");
        List<Object> objList=new ArrayList<Object>();
        if(taskid!=null){
            sqlbuilder.append("?,");
            objList.add(taskid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("null,");
        }
        if(isvir!=null){
            sqlbuilder.append("?,");
            objList.add(isvir);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?");
            objList.add(userid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getTeacherCalendar(Integer userid, Integer schoolid, Integer year, Integer month) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_getTeacherObtainCalendarProc(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        sqlbuilder.append("null,");
        if(schoolid!=null){
            sqlbuilder.append("?,");
            objList.add(schoolid);
        }else{
            sqlbuilder.append("null,");
        }
        if(year!=null){
            sqlbuilder.append("?,");
            objList.add(year);
        }else{
            sqlbuilder.append("null,");
        }
        if(month!=null){
            sqlbuilder.append("?");
            objList.add(month);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getStudentCalendar(Integer userid, Integer schoolid, Integer classid, Integer year, Integer month) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_getTeacherObtainCalendarProc(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("null,");
        }
        if(schoolid!=null){
            sqlbuilder.append("?,");
            objList.add(schoolid);
        }else{
            sqlbuilder.append("null,");
        }
        if(year!=null){
            sqlbuilder.append("?,");
            objList.add(year);
        }else{
            sqlbuilder.append("null,");
        }
        if(month!=null){
            sqlbuilder.append("?");
            objList.add(month);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getTeacherCalendarDetail(Integer userid, Integer usertype, Integer schoolid, String time) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_getClassCalendarByDayProc(");
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("null,");
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
//        if(classid!=null){
//            sqlbuilder.append("?,");
//            objList.add(classid);
//        }else{
//            sqlbuilder.append("null,");
//        }
        if(schoolid!=null){
            sqlbuilder.append("?,");
            objList.add(schoolid);
        }else{
            sqlbuilder.append("null,");
        }
        if(usertype!=null){
            sqlbuilder.append("?,");
            objList.add(usertype);
        }else{
            sqlbuilder.append("null,");
        }
        if(time!=null){
            sqlbuilder.append("?");
            objList.add(time);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getstudentCalendarDetail(Integer userid, Integer usertype, Integer classid, Integer schoolid, String time) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_getClassCalendarByDayProc(");
        List<Object> objList=new ArrayList<Object>();
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(schoolid!=null){
            sqlbuilder.append("?,");
            objList.add(schoolid);
        }else{
            sqlbuilder.append("null,");
        }
        if(usertype!=null){
            sqlbuilder.append("?,");
            objList.add(usertype);
        }else{
            sqlbuilder.append("null,");
        }
        if(time!=null){
            sqlbuilder.append("?");
            objList.add(time);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getTopicUserRecord(Long topicid, Integer classid, Integer isvir, Integer userid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_stu_post_detail(");
        List<Object> objList=new ArrayList<Object>();
        if(topicid!=null){
            sqlbuilder.append("?,");
            objList.add(topicid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("null,");
        }
        if(isvir!=null){
            sqlbuilder.append("?,");
            objList.add(isvir);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?");
            objList.add(userid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getQryStatPerson(ImInterfaceInfo obj) {

        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_query_stat_person(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else{
            sqlbuilder.append("null,");
        }

        sqlbuilder.append("?)}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getQryStatPersonStu(ImInterfaceInfo obj) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_query_stat_person_stu(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUserid());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else{
            sqlbuilder.append("null,");
        }

        sqlbuilder.append("?)}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getTaskWatch(Integer userid, Long videoid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_taskinfo_proc_getWatch(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(videoid!=null){
            sqlbuilder.append("?");
            objList.add(videoid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getUnCompleteStu(Long taskid, Integer flag, Integer classid,Integer userid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_task_notcomplete_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(taskid!=null){
            sqlbuilder.append("?,");
            objList.add(taskid);
        }else{
            sqlbuilder.append("null,");
        }
        if(flag!=null){
            sqlbuilder.append("?,");
            objList.add(flag);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        sqlbuilder.append("?)}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getStuScoreSubjectList(Integer classid,String userid) {
        if(classid==null||classid.toString().length()<1)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL getStuScoreSubjectListProc(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null&& userid.length()>0){
            objList.add(userid);
            sqlbuilder.append("?,");
        }else
            sqlbuilder.append("NULL,");
       objList.add(classid);
        sqlbuilder.append("?,?)}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getTaskRemind(Long taskid, Integer userid, Integer classid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_task_remind_proc_detail(");
        List<Object> objList=new ArrayList<Object>();
        if(taskid!=null){
            sqlbuilder.append("?,");
            objList.add(taskid);
        }else{
            sqlbuilder.append("null,");
        }
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getClassInfoForCourse(String userid, Integer classid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi115_proc_classinfo_for_course(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> getUpdateCourse(Long courseid, Integer classid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi115_proc_getupdatecourse_info(");
        List<Object> objList=new ArrayList<Object>();
        if(courseid!=null){
            sqlbuilder.append("?,");
            objList.add(courseid);
        }else{
            sqlbuilder.append("null,");
        }
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    @Override
    public List<Map<String, Object>> validateGrade(String userid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi115_proc_validate_grade(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?");
            objList.add(userid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }
}
