package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseRelatedDAO;
import com.school.entity.teachpaltform.TpCourseRelatedInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuechunyang on 14-6-19.
 */
@Component
public class TpCourseRelatedDAO extends CommonDAO<TpCourseRelatedInfo> implements ITpCourseRelatedDAO {
    @Override
    public Boolean doSave(TpCourseRelatedInfo obj) {
        return null;
    }

    @Override
    public Boolean doUpdate(TpCourseRelatedInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(TpCourseRelatedInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }return false;
    }

    @Override
    public List<TpCourseRelatedInfo> getList(TpCourseRelatedInfo obj, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_j_course_related_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getCourseid()==null)
            return null;
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getCourseid());
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<TpCourseRelatedInfo> tpCourseRelatedInfoList = this.executeResult_PROC(sqlbuilder.toString(),objList,null,TpCourseRelatedInfo.class,null);
        return tpCourseRelatedInfoList;
    }

    @Override
    public List<Object> getSaveSql(TpCourseRelatedInfo obj, StringBuilder sqlbuilder) {
        if(obj==null)
            return null;
        sqlbuilder.append("{CALL tp_j_course_related_proc_add(");
        List<Object>objList = new ArrayList<Object>();
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCourseid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getRelatedcourseid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getRelatedcourseid());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        return objList;
    }

    @Override
    public List<Object> getUpdateSql(TpCourseRelatedInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(TpCourseRelatedInfo obj, StringBuilder sqlbuilder) {
        if(obj==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL tp_j_course_related_proc_del(");
        List<Object>objList = new ArrayList<Object>();
        if (obj.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getCourseid());
        } else
            sqlbuilder.append("NULL,");

        sqlbuilder.append("?)}");
        return objList;
    }
}
