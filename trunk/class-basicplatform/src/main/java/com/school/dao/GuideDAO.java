package com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IGuideDAO;
import com.school.entity.GuideInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * 向导数据访问层
 * Created by zhengzhou on 14-5-5.
 */
@Component
public class GuideDAO extends CommonDAO<GuideInfo> implements IGuideDAO{

    @Override
    public Boolean doSave(GuideInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getSaveSql(obj,sqlbuilder);
        Object returnObj=this.executeSacle_PROC(sqlbuilder.toString(),objList.toArray());
        if(returnObj!=null&&returnObj.toString().trim().length()>0&&Integer.parseInt(returnObj.toString().trim())>0)
            return true;
        return false;
    }

    @Override
    public Boolean doUpdate(GuideInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(GuideInfo obj) {
        return null;
    }

    @Override
    public List<GuideInfo> getList(GuideInfo obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL guide_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null)
            sqlbuilder.append("?,?,?,?,");
        else{
            if(obj.getRef()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getRef());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getOptable()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getOptable());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getOpuser()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getOpuser());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getOptype()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getOptype());
            }else
                sqlbuilder.append("NULL,");
        }
        if(presult==null){
            sqlbuilder.append("null,null,null,");
        }else{
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
            if(presult.getOrderBy()!=null&&presult.getOrderBy().length()>0){
                sqlbuilder.append("?,");
                objList.add(presult.getOrderBy());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<GuideInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, GuideInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return roleList;
    }

    @Override
    public List<Object> getSaveSql(GuideInfo obj, StringBuilder sqlbuilder) {
        if(obj==null)
            return null;
        List<Object> returnObj=new ArrayList<Object>();
        sqlbuilder.append("{CALL guide_proc_add(");
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            returnObj.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getOptable()!=null){
            sqlbuilder.append("?,");
            returnObj.add(obj.getOptable());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getOpuser()!=null){
            sqlbuilder.append("?,");
            returnObj.add(obj.getOpuser());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getOptype()!=null){
            sqlbuilder.append("?,");
            returnObj.add(obj.getOptype());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return returnObj;
    }

    @Override
    public List<Object> getUpdateSql(GuideInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(GuideInfo obj, StringBuilder sqlbuilder) {
        return null;
    }
}
