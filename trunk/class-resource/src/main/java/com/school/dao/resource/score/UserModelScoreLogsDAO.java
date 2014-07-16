package com.school.dao.resource.score;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.resource.score.IUserModelScoreLogsDAO;
import com.school.entity.UserModelScoreLogsInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Component
public class UserModelScoreLogsDAO extends CommonDAO<UserModelScoreLogsInfo> implements IUserModelScoreLogsDAO {
    public Boolean doSave(UserModelScoreLogsInfo obj) {
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

    public Boolean doUpdate(UserModelScoreLogsInfo obj) {
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

    public Boolean doDelete(UserModelScoreLogsInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }
        return false;
    }

    public List<UserModelScoreLogsInfo> getList(UserModelScoreLogsInfo obj, PageResult presult) {
        return null;
    }

    public String getNextId() {
        return null;
    }

    public Long getNextId(boolean bo) {
        return null;
    }

    public List<Object> getSaveSql(UserModelScoreLogsInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getUpdateSql(UserModelScoreLogsInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getDeleteSql(UserModelScoreLogsInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Map<String,Object>> getUserScoreDetails(Integer userid) {
        if(userid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder("{call user_model_score_sum_proc_list(");
        List<Object> objlist = new ArrayList<Object>();
        if(userid!=null&&userid>0){
            sqlbuilder.append("?");
            objlist.add(userid);
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objlist);
        return list;
    }

    public List<UserModelScoreLogsInfo> getUserResourceScoreList(UserModelScoreLogsInfo obj, PageResult presult) {
        if(obj==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder("{call user_model_score_log_proc_list_bymonth(");
        List<Object> objlist = new ArrayList<Object>();
        if(obj.getUserId()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserId());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getSelmonth()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSelmonth());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getScoreTypeId()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getScoreTypeId());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objlist.add(presult.getPageNo());
            objlist.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objlist.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<UserModelScoreLogsInfo> list = this.executeResult_PROC(sqlbuilder.toString(),objlist,types,UserModelScoreLogsInfo.class,objArray);
        return list;
    }
}
