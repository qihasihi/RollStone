package com.school.dao.teachpaltform.award;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpGroupScoreDAO;
import com.school.dao.inter.teachpaltform.award.ITpGroupScoreDAO;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Component
public class TpGroupScoreDAO extends CommonDAO<TpGroupScore> implements ITpGroupScoreDAO {
    @Override
    public Boolean doSave(TpGroupScore obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getSaveSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doUpdate(TpGroupScore obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getUpdateSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doDelete(TpGroupScore obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getDeleteSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public List<TpGroupScore> getList(final TpGroupScore obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{call tp_group_score_list_split(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null)
            sqlbuilder.append("?,?,?,?,?,?,");
        else{
            if(obj.getCourseid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getCourseid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getClassid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getClasstype()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClasstype());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getSubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getGroupid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getGroupid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getDcschoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getDcschoolid());
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
            }
        }
        sqlbuilder.append("?)}");

        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<TpGroupScore> papertypeinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpGroupScore.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return papertypeinfoList;
    }

    @Override
    public List<Object> getSaveSql(TpGroupScore obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> rturnVal=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_group_score_add(");
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
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubmitflag()!=null){
            sqlbuilder.append("?,");
            rturnVal.add(obj.getSubmitflag());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return rturnVal;
    }

    @Override
    public List<Object> getUpdateSql(TpGroupScore obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> returnVal=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_group_score_update(");
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
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubmitflag()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getSubmitflag());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return returnVal;
    }

    /**
     * Ìí¼Ó»òÐÞ¸Ä
     * @param obj
     * @return
     */
    public boolean AddOrUpdate(final TpGroupScore obj){

        List<Object> returnVal=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL tp_group_score_addOrUpdate(");

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
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            returnVal.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return this.executeQuery_PROC(sqlbuilder.toString(),returnVal.toArray());
    }

    @Override
    public List<Object> getDeleteSql(TpGroupScore obj, StringBuilder sqlbuilder) {
        return null;
    }
}
