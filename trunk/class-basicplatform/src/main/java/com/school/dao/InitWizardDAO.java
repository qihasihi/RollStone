package com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IInitWizardDAO;
import com.school.entity.InitWizardInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuechunyang on 14-3-6.
 */
@Component
public class InitWizardDAO extends CommonDAO<InitWizardInfo> implements IInitWizardDAO {

    public Boolean doSave(InitWizardInfo obj) {
        // TODO Auto-generated method stub
        if(obj==null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objlist = getSaveSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
        if(afficeObj != null && afficeObj.toString().trim().length()>0
                && Integer.parseInt(afficeObj.toString())>0){
            return true;
        }
        return false;
    }

    public Boolean doUpdate(InitWizardInfo obj) {
        // TODO Auto-generated method stub
        if(obj==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objlist = getUpdateSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doDelete(InitWizardInfo obj) {
        return null;
    }

    public List<InitWizardInfo> getList(InitWizardInfo obj, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder("{call init_wizard_proc_search(");
        sqlbuilder.append(")}");
        List<InitWizardInfo> list = this.executeResult_PROC(sqlbuilder.toString(),null,null,InitWizardInfo.class,null);
        return list;
    }

    public List<Object> getSaveSql(InitWizardInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call init_wizard_proc_add(");
        List<Object>objList = new ArrayList<Object>();
        if(obj.getCurrentStep()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCurrentStep());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSuccess()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSuccess());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getUpdateSql(InitWizardInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call init_wizard_proc_update(");
        List<Object>objList = new ArrayList<Object>();
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCurrentStep()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCurrentStep());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSuccess()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSuccess());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getDeleteSql(InitWizardInfo obj, StringBuilder sqlbuilder) {
        return null;
    }
}
