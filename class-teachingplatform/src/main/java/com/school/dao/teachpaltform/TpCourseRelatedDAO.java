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
        return null;
    }

    @Override
    public List<TpCourseRelatedInfo> getList(TpCourseRelatedInfo obj, PageResult presult) {
        return null;
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
        return null;
    }
}
