package com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ISchoolLogoDAO;
import com.school.entity.SchoolInfo;
import com.school.entity.SchoolLogoInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuechunyang on 14-9-2.
 */
@Component
public class SchoolLogoDAO extends CommonDAO<SchoolLogoInfo> implements ISchoolLogoDAO {
    @Override
    public Boolean doSave(SchoolLogoInfo obj) {
        if (obj == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getSaveSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0)
            return true;
        return false;
    }

    @Override
    public Boolean doUpdate(SchoolLogoInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(SchoolLogoInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }return false;
    }

    @Override
    public List<SchoolLogoInfo> getList(SchoolLogoInfo obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL school_logo_info_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null){
            sqlbuilder.append("NULL");
        }else{
            if(obj.getSchoolid()!=null){
                sqlbuilder.append("?");
                objList.add(obj.getSchoolid());
            }else
                sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<SchoolLogoInfo> schoolList=this.executeResult_PROC(sqlbuilder.toString(),objList,null,SchoolLogoInfo.class,null);
        return schoolList;
    }

    @Override
    public List<Object> getSaveSql(SchoolLogoInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||obj.getSchoolid()==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL school_logo_info_proc_add(?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(obj.getSchoolid());
        objList.add(obj.getLogosrc());
        return objList;
    }

    @Override
    public List<Object> getUpdateSql(SchoolLogoInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(SchoolLogoInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL school_logo_info_proc_delete(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getSchoolid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getSchoolid());
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(",?)}");
        return objList;
    }
}
