package com.school.dao.teachpaltform.award;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpClsPerformanceDAO;
import com.school.entity.teachpaltform.award.TpClsPerformanceInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Component
public class TpClsPerformanceDAO extends CommonDAO<TpClsPerformanceInfo> implements ITpClsPerformanceDAO {
    @Override
    public Boolean doSave(final TpClsPerformanceInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getSaveSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doUpdate(final TpClsPerformanceInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getUpdateSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doDelete(final TpClsPerformanceInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getDeleteSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public List<TpClsPerformanceInfo> getList(final TpClsPerformanceInfo obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(final TpClsPerformanceInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_class_performance_info_add(");
        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
         sqlbuilder.append("?,?,?,");
        objlist.add(obj.getAttendanceNum());
        objlist.add(obj.getSimilingNum());
        objlist.add(obj.getViolationDisNum());
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objlist;
    }

    @Override
    public List<Object> getUpdateSql(final TpClsPerformanceInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_class_performance_info_update(");
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAttendanceNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getAttendanceNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSimilingNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSimilingNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getViolationDisNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getViolationDisNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objlist;
    }

    /**
     * 得到页面上的查询
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype,final Integer subjectid){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_cls_performance_info_list(");
        if(courseid!=null){
            sqlbuilder.append("?,");
            objList.add(courseid);
        }else
            sqlbuilder.append("NULL,");
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else
            sqlbuilder.append("NULL,");
        if(classtype!=null){
            sqlbuilder.append("?,");
            objList.add(classtype);
        }else
            sqlbuilder.append("NULL,");
        if(subjectid!=null){
            sqlbuilder.append("?");
            objList.add(subjectid);
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(")}");

        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

    /**
     * 添加或修改
     * @param obj
     * @return
     */
    public boolean AddOrUpdate(final TpClsPerformanceInfo obj){
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_class_performance_info_addOrupdate(");

        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAttendanceNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getAttendanceNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSimilingNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSimilingNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getViolationDisNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getViolationDisNum());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return this.executeQuery_PROC(sqlbuilder.toString(),objlist.toArray());
    }

    @Override
    public List<Object> getDeleteSql(final TpClsPerformanceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }
}
