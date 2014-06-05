package com.school.dao.resource.score;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.resource.score.IUserModelTotalScoreDAO;
import com.school.entity.UserModelTotalScoreInfo;
import com.school.entity.resource.score.UserModelTotalScore;
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
public class UserModelTotalScoreDAO extends CommonDAO<UserModelTotalScoreInfo> implements IUserModelTotalScoreDAO {
    public Boolean doSave(UserModelTotalScoreInfo obj) {
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

    public Boolean doUpdate(UserModelTotalScoreInfo obj) {
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

    public Boolean doDelete(UserModelTotalScoreInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }
        return false;
    }

    public List<UserModelTotalScoreInfo> getList(UserModelTotalScoreInfo obj, PageResult presult) {
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL user_model_total_score_proc_split(");
        if(obj==null)
            sqlbuilder.append("NULL,NULL,NULL,NULL,");
        else{
            if(obj.getUserId()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getUserId());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getModelId()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getModelId());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getRef()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getRef());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getTotalScore()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getTotalScore());
            }else
                sqlbuilder.append("NULL,");
        }
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else
            sqlbuilder.append("NULL,NULL");
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        List<Integer> types = new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray = new Object[1];
        List<UserModelTotalScoreInfo> userList = this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserModelTotalScoreInfo.class,
                objArray);
        if (presult != null && objArray[0] != null
                && objArray[0].toString().trim().length() > 0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return userList;
    }

    public List<Object> getSaveSql(UserModelTotalScoreInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getUpdateSql(UserModelTotalScoreInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getDeleteSql(UserModelTotalScoreInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)return null;
        sqlbuilder.append("{call user_model_total_score_proc_delete(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getUserId()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUserId());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getModelId()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getModelId());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Map<String, Object>> getUserScoreInfo(Integer userid) {
        if(userid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder("{call user_model_total_score_proc_list(");
        List<Object> objlist = new ArrayList<Object>();
        if(userid!=null&&userid>0){
            sqlbuilder.append("?");
            objlist.add(userid);
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objlist);
        return list;
    }

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSQL(UserModelTotalScore entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("{CALL user_model_total_score_proc_synchro(");
        if(entity==null)
            sqlbuilder.append("NULL,NULL,NULL,");
        else{
            if(entity.getUserid()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getUserid());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getModelid()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getModelid());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getTotalscore()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getTotalscore());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        return objList;
    }
}
