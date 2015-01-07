package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseDAO;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class TpCourseDAO extends CommonDAO<TpCourseInfo> implements ITpCourseDAO {

    public Boolean doSave(TpCourseInfo tpcourseinfo) {
        if (tpcourseinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getSaveSql(tpcourseinfo, sqlbuilder);

        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doDelete(TpCourseInfo tpcourseinfo) {
        if(tpcourseinfo==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(tpcourseinfo, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }return false;
    }

    public Boolean doUpdate(TpCourseInfo tpcourseinfo) {
        if (tpcourseinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getUpdateSql(tpcourseinfo, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    // 专题评论和打分，更新专题状态
    public Boolean doCommentAndScore(TpCourseInfo tpcourseinfo) {
        if (tpcourseinfo == null || tpcourseinfo.getCourseid()==null
                || tpcourseinfo.getAvgscore()==null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder("{CALL tp_course_info_proc_comment_and_score(?,?,?)}");
        List<Object> objList = new ArrayList<Object>();
        objList.add(tpcourseinfo.getCourseid());
        objList.add(tpcourseinfo.getAvgscore());
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<TpCourseInfo> getCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_split_list(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getGradeid());
        } else
            sqlbuilder.append("null,");

        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    public List<TpCourseInfo> checkQuoteCourse(TpCourseInfo tpcourseinfo) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_check(");
        List<Object> objList=new ArrayList<Object>();
        if(tpcourseinfo.getCuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        }else{
            objList.add("null,");
        }
        if(tpcourseinfo.getQuoteid()!=null){
            sqlbuilder.append("?");
            objList.add(tpcourseinfo.getQuoteid());
        }else{
            objList.add("null");
        }
        sqlbuilder.append(")}");
        List<TpCourseInfo> courseList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,TpCourseInfo.class,null);
        return courseList;
    }

    public List<Map<String,Object>> getRelatedCourseList(Integer materialid, Integer userid,String coursename) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_related_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(materialid!=null){
            sqlbuilder.append("?,");
            objList.add(materialid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(materialid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(coursename!=null){
            sqlbuilder.append("?");
            objList.add(coursename);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> courselist=this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return courselist;
    }

    /**
     * 得到云端共享的集合
     * @param tpcourseinfo
     * @param presult
     * @return
     */
    public List<TpCourseInfo> getShareList(TpCourseInfo tpcourseinfo,PageResult presult){
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_share_split(");
        List<Object> objList=new ArrayList<Object>();

        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if(tpcourseinfo.getCloudstatus()!=null){
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");

        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    @Override
    public List<Map<String, Object>> getCourseCalendar(Integer usertype,Integer userid, Integer dcschoolid, String year, String month,String gradeid,String subjectid,Integer classid,String termid) {
        if(usertype==null||dcschoolid==null||year==null||month==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL getCourseCalendar(");
        List<Object> objList=new ArrayList<Object>();
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(usertype!=null){
            sqlbuilder.append("?,");
            objList.add(usertype);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(dcschoolid!=null){
            sqlbuilder.append("?,");
            objList.add(dcschoolid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(year!=null){
            sqlbuilder.append("?,");
            objList.add(year);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(month!=null){
            sqlbuilder.append("?,");
            objList.add(month);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(gradeid!=null&&gradeid.trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(gradeid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(subjectid!=null&&subjectid.trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(subjectid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(termid!=null&&termid.trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(termid);
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Map<String,Object>> courselist=this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return courselist;
    }

    public List<TpCourseInfo> getList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getDcschoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getDcschoolid());
        }else{
            sqlbuilder.append("NULL,");
        }

        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if(tpcourseinfo.getCloudstatus()!=null){
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getQuoteid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getQuoteid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getMaterialidvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getMaterialidvalues());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getFilterquote() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getFilterquote());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectvalues());
        } else
            sqlbuilder.append("null,");
        if(tpcourseinfo.getVersionvalues()!=null){
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getVersionvalues());
        }else{
            sqlbuilder.append("null,");
        }
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    public List<TpCourseInfo> getTchCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_tch_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");

        if (tpcourseinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getMaterialidvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getMaterialidvalues());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourseclassref() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseclassref());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getResid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getResid());
        } else
            sqlbuilder.append("null,");

        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    @Override
    public List<TpCourseInfo> getCalanderCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_proc_tch_calander_split(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");

        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");

        if (tpcourseinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTermid());
        } else
            sqlbuilder.append("null,");

        if (tpcourseinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getUsertype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getUsertype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getDcschoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getDcschoolid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectid() != null&&!tpcourseinfo.getSubjectid().toString().equals("0")) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getGradeid() != null&&!tpcourseinfo.getGradeid().toString().equals("0")) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSeldate() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSeldate());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getClassid());
        } else
            sqlbuilder.append("null,");


        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    public List<TpCourseInfo> getStuCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_stu_list(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSelectType() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSelectType());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getClassid());
        } else
            sqlbuilder.append("null,");
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    // 获得教师受托管的课题列表
    public List<TpCourseInfo> getTsList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_info_proc_split_ts(");
        List<Object> objList=new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");

        if (tpcourseinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTermid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTermid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSubjectid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSubjectid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getGradeid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getGradeid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getMaterialidvalues() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getMaterialidvalues());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getUserid());
        } else
            sqlbuilder.append("null,");
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    public List<Object> getSaveSql(TpCourseInfo tpcourseinfo, StringBuilder sqlbuilder) {
        if(tpcourseinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_course_info_proc_add(");
        List<Object>objList = new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getIntroduction() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getIntroduction());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getQuoteid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getQuoteid());
        } else
            sqlbuilder.append("null,");

        sqlbuilder.append("?,");
        objList.add(tpcourseinfo.getDcschoolid());
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getDeleteSql(TpCourseInfo tpcourseinfo, StringBuilder sqlbuilder) {
        if(tpcourseinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_course_info_proc_delete(");
        List<Object>objList = new ArrayList<Object>();
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getUpdateSql(TpCourseInfo tpcourseinfo, StringBuilder sqlbuilder) {
        if(tpcourseinfo==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_course_info_proc_update(");
        List<Object>objList = new ArrayList<Object>();
        if (tpcourseinfo.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getIntroduction() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getIntroduction());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (tpcourseinfo.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(tpcourseinfo.getSchoolname());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public Boolean doExcetueArrayProc(List<String> sqlArrayList,
                                      List<List<Object>> objArrayList) {
        return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
    }

    public String getNextId() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseInfo entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        sqlbuilder.append("{CALL tp_course_info_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourseid());
        } else
            sqlbuilder.append("null,");
        if (entity.getCoursename() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCoursename());
        } else
            sqlbuilder.append("null,");
        if (entity.getIntroduction() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getIntroduction());
        } else
            sqlbuilder.append("null,");
        if (entity.getCuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCuserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getSharetype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSharetype());
        } else
            sqlbuilder.append("null,");
        if (entity.getCourselevel() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCourselevel());
        } else
            sqlbuilder.append("null,");
        if (entity.getCloudstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCloudstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getCoursestatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getCoursestatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getLocalstatus() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getLocalstatus());
        } else
            sqlbuilder.append("null,");
        if (entity.getTeachername() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTeachername());
        } else
            sqlbuilder.append("null,");
        if (entity.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (entity.getQuoteid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getQuoteid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }




    public List<TpCourseInfo> getCouresResourceList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_resource_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(tpcourseinfo==null){
            sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
        }else{
            if (tpcourseinfo.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCourseid());
            } else
                sqlbuilder.append("null,");

            if (tpcourseinfo.getCoursename() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCoursename());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getCourselevel() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCourselevel());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getMaterialid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getMaterialid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getGradeid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getGradeid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getSubjectid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getSubjectid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getCuserid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCuserid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getCurrentcourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCurrentcourseid());
            } else
                sqlbuilder.append("null,");
        }

        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    /**
     * @author 岳春阳
     * @decription 查询专题列表（关联试题）
     * @date 2014.2.14
     * */
    public List<TpCourseInfo> getCourseQuestionList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_question_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(tpcourseinfo==null){
            sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
        }else{
            if (tpcourseinfo.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCourseid());
            } else
                sqlbuilder.append("null,");

            if (tpcourseinfo.getCoursename() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCoursename());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getCourselevel() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCourselevel());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getGradeid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getGradeid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getMaterialid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getMaterialid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getCurrentcourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getCurrentcourseid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getSubjectid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getSubjectid());
            } else
                sqlbuilder.append("null,");
            if (tpcourseinfo.getQuestiontype() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getQuestiontype());
            } else
                sqlbuilder.append("null,");
        }

        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }

    /**
     * 更新现在已经上传的专题
     * @return
     */
    public boolean doUpdateShareCourse(){
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL up_tp_course_info_proc_share(?)}");
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                new ArrayList().toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 得到专题积分是否录取完毕
     * @param clsid
     * @param subjectid
     * @param carrayid
     * @param garrayid
     * @param roletype  NULL OR 1:老师  2：学生
     * @return
     */
    public List<Map<String,Object>> getCourseScoreIsOver(final Integer clsid,final Integer subjectid,final String carrayid,String garrayid, Integer roletype){
        if(clsid==null||subjectid==null||carrayid==null||carrayid.trim().length()<1){
            return null;
        }
        if(roletype==null)
            roletype=1;
        if(garrayid==null||garrayid.trim().length()<1)
            garrayid="0";
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_course_idx_grouper_coursescore_isover(?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(clsid);
        objList.add(subjectid);
        objList.add(carrayid);
        objList.add(garrayid);
        objList.add(roletype);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);

    }

    public List<TpCourseInfo> getCourseByGradeTermSubject(int gradeId,int termId,int subjectId){
        List<TpCourseInfo> list = null;

        StringBuilder sb = new StringBuilder("{call tp_course_info_proc_list_elite_course(");
        sb.append(gradeId);
        sb.append(",");
        sb.append(termId);
        sb.append(",");
        sb.append(subjectId);
        sb.append(")}");

        list=this.executeResult_PROC(sb.toString(), null, null, TpCourseInfo.class, null);
        return list;
    }

    @Override
    public List<TpCourseInfo> getCourseSubGradeList(TpCourseInfo tpcourseinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_course_subgrade_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(tpcourseinfo==null){
            return null;
        }else{
            if (tpcourseinfo.getTermid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getTermid());
            } else
                sqlbuilder.append("null,");

            if (tpcourseinfo.getUserid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpcourseinfo.getUserid());
            } else
                sqlbuilder.append("null,");
        }
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpCourseInfo> tpcourseinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpCourseInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return tpcourseinfoList;
    }


}
