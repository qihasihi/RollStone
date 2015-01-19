package com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ISchoolDAO;
import com.school.entity.SchoolInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzhou on 14-4-7.
 */
@Component
public class SchoolDAO extends CommonDAO<SchoolInfo> implements ISchoolDAO{
    @Override
    public Boolean doSave(SchoolInfo obj) {
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
    public Boolean doUpdate(SchoolInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(SchoolInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }return false;
    }

    @Override
    public List<SchoolInfo> getList(SchoolInfo obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL school_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null){
            sqlbuilder.append("NULL,NULL,NULL,");
        }else{
            if(obj.getSchoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSchoolid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getName()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getName());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getIp()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getIp());
            }else
                sqlbuilder.append("NULL,");
        }
        if (presult != null && presult.getPageNo() > 0
                && presult.getPageSize() > 0) {
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        } else {
            sqlbuilder.append("NULL,NULL,");
        }
        if (presult != null && presult.getOrderBy() != null
                && presult.getOrderBy().trim().length() > 0) {
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        } else {
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<SchoolInfo> schoolList=this.executeResult_PROC(sqlbuilder.toString(),objList,types,SchoolInfo.class,objArray);
        if (presult != null && objArray[0] != null&& objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return schoolList;
    }

    @Override
    public List<Object> getSaveSql(SchoolInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||obj.getSchoolid()==null||obj.getName()==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL school_info_proc_synchro(?,?,?,?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(obj.getSchoolid());
        objList.add(obj.getName());
        objList.add(obj.getIp()==null?"":obj.getIp());
        objList.add(obj.getCountry()==null?"":obj.getCountry());
        objList.add(obj.getProvince()==null?"":obj.getProvince());
        objList.add(obj.getCity()==null?"":obj.getCity());
        objList.add(obj.getIsactive()==null?0:obj.getIsactive());
        return objList;
    }

    @Override
    public List<Object> getUpdateSql(SchoolInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(SchoolInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL school_info_proc_delete(");
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
