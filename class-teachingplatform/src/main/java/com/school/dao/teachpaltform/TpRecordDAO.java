package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.ITpRecordDAO;
import com.school.entity.teachpaltform.TpRecordInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzhou on 14-8-19.
 */
@Component("tpRecordDAO")
public class TpRecordDAO  extends CommonDAO<TpRecordInfo> implements ITpRecordDAO {

    @Override
    public Boolean doSave(TpRecordInfo obj) {
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
    public Boolean doUpdate(TpRecordInfo obj) {
        if (obj == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean doDelete(TpRecordInfo obj) {
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
    public List<TpRecordInfo> getList(TpRecordInfo obj, PageResult presult) {


        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_tp_record_list(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null)
            sqlbuilder.append("NULL,NULL,NULL,NULL,");
        if (obj.getUserId() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getUserId());
        } else
            sqlbuilder.append("null,");

        if (obj.getCourseId() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getCourseId());
        } else
            sqlbuilder.append("null,");

        if (obj.getClassId() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getClassId());
        } else
            sqlbuilder.append("null,");
        if (obj.getDcSchoolId() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getDcSchoolId());
        } else
            sqlbuilder.append("null,");
        if (obj.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(obj.getRef());
        } else
            sqlbuilder.append("null,");

//        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
//            sqlbuilder.append("?,?,");
//            objList.add(presult.getPageNo());
//            objList.add(presult.getPageSize());
//        }else{
//            sqlbuilder.append("NULL,NULL,");
//        }
//        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
//            sqlbuilder.append("?,");
//            objList.add(presult.getOrderBy());
//        }else{
//            sqlbuilder.append("NULL,");
//        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpRecordInfo> questioninfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpRecordInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return questioninfoList;
    }

    @Override
    public List<Object> getSaveSql(TpRecordInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("{CALL imapi_tp_record_add(");
        if(obj.getCourseId()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCourseId());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getUserId()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUserId());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getContent()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getContent());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getImgUrl()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getImgUrl());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassId()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassId());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcSchoolId()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDcSchoolId());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");

         return objList;
    }

    @Override
    public List<Object> getUpdateSql(TpRecordInfo obj, StringBuilder sqlbuilder) {

        return null;
    }

    @Override
    public List<Object> getDeleteSql(TpRecordInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("{CALL imapi_tp_record_del(");
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");

        sqlbuilder.append("?)}");
        return objList;
    }
}
