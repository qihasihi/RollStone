package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.QuesTeamRela;
import com.school.dao.inter.teachpaltform.IQuesTeamRelaDAO;
import com.school.util.PageResult;

@Component  
public class QuesTeamRelaDAO extends CommonDAO<QuesTeamRela> implements IQuesTeamRelaDAO {

	public Boolean doSave(final QuesTeamRela questeamrela) {
		if (questeamrela == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(questeamrela, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(final QuesTeamRela questeamrela) {
		if(questeamrela==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(questeamrela, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(final QuesTeamRela questeamrela) {
		if (questeamrela == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(questeamrela, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<QuesTeamRela> getList(final QuesTeamRela questeamrela, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_ques_team_rela_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if(questeamrela==null){
            sqlbuilder.append("NULL,NULL,NULL,NULL,");
        }else{
            if (questeamrela.getOrderid() != null) {
                sqlbuilder.append("?,");
                objList.add(questeamrela.getOrderid());
            } else
                sqlbuilder.append("null,");
            if (questeamrela.getQuesid() != null) {
                sqlbuilder.append("?,");
                objList.add(questeamrela.getQuesid());
            } else
                sqlbuilder.append("null,");
            if (questeamrela.getTeamid() != null) {
                sqlbuilder.append("?,");
                objList.add(questeamrela.getTeamid());
            } else
                sqlbuilder.append("null,");
            if (questeamrela.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(questeamrela.getRef());
            } else
                sqlbuilder.append("null,");
        }
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objList.add(presult.getPageNo());
			objList.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objList.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<QuesTeamRela> questeamrelaList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, QuesTeamRela.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return questeamrelaList;	
	}
	
	public List<Object> getSaveSql(final QuesTeamRela questeamrela, StringBuilder sqlbuilder) {
		if(questeamrela==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_ques_team_rela_proc_add(");
		List<Object>objList = new ArrayList<Object>();

			if (questeamrela.getOrderid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getOrderid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getQuesid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getQuesid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getTeamid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getTeamid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getRef());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(final QuesTeamRela questeamrela, StringBuilder sqlbuilder) {
		if(questeamrela==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_ques_team_rela_proc_delete(");
		List<Object>objList = new ArrayList<Object>();

			if (questeamrela.getOrderid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getOrderid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getQuesid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getQuesid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getTeamid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getTeamid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getRef());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(final QuesTeamRela questeamrela, StringBuilder sqlbuilder) {
		if(questeamrela==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_ques_team_rela_proc_update(");
		List<Object>objList = new ArrayList<Object>();

			if (questeamrela.getOrderid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getOrderid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getQuesid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getQuesid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getTeamid() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getTeamid());
			} else
				sqlbuilder.append("null,");
			if (questeamrela.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(questeamrela.getRef());
			} else
				sqlbuilder.append("null,");
            if (questeamrela.getScore() != null) {
                sqlbuilder.append("?,");
                objList.add(questeamrela.getScore());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
}
