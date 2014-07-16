package com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IScoreTypeDAO;
import com.school.entity.ScoreTypeInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Component
public class ScoreTypeDAO extends CommonDAO<ScoreTypeInfo> implements IScoreTypeDAO{
    public Boolean doSave(ScoreTypeInfo obj) {
        if (obj == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getSaveSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doUpdate(ScoreTypeInfo obj) {
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

    public Boolean doDelete(ScoreTypeInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }
        return false;
    }

    public List<ScoreTypeInfo> getList(ScoreTypeInfo obj, PageResult presult) {
        return null;
    }

    public List<Object> getSaveSql(ScoreTypeInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getUpdateSql(ScoreTypeInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getDeleteSql(ScoreTypeInfo obj, StringBuilder sqlbuilder) {
        return null;
    }
}
