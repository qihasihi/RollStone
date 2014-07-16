package com.school.dao.teachpaltform.award;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpClassPerformanceAwardDAO;
import com.school.entity.teachpaltform.award.TpClassPerformanceAwardInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Component
public class TpClassPerformanceAwardDAO extends CommonDAO<TpClassPerformanceAwardInfo> implements ITpClassPerformanceAwardDAO {
    @Override
    public Boolean doSave(TpClassPerformanceAwardInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getSaveSql(obj,sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doUpdate(TpClassPerformanceAwardInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getUpdateSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doDelete(TpClassPerformanceAwardInfo obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getDeleteSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public List<TpClassPerformanceAwardInfo> getList(TpClassPerformanceAwardInfo obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(TpClassPerformanceAwardInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> rturnVal=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_cls_performance_award_info_add(");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getCourseid());
        }else
            sqlbuilder.append("null,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getGroupid());
        }else
            sqlbuilder.append("null,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAwardnumber()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getAwardnumber());
        }else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return rturnVal;
    }

    @Override
    public List<Object> getUpdateSql(TpClassPerformanceAwardInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> returnVal=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_cls_performance_award_info_update(");
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAwardnumber()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getAwardnumber());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}}");
        return returnVal;
    }

    /**
     * Ìí¼Ó»òÐÞ¸Ä
     * @param obj
     * @return
     */
    public boolean AddOrUpdate(final TpClassPerformanceAwardInfo obj){

        List<Object> returnVal=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL tp_cls_performance_award_info_addOrUpdate(");

        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAwardnumber()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getAwardnumber());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return this.executeQuery_PROC(sqlbuilder.toString(),returnVal.toArray());
    }

    @Override
    public List<Object> getDeleteSql(TpClassPerformanceAwardInfo obj, StringBuilder sqlbuilder) {
        return null;
    }
}
