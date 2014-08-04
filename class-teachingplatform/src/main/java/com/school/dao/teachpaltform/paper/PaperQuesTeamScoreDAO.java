package com.school.dao.teachpaltform.paper;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.paper.PaperQuesTeamScore;
import com.school.dao.inter.teachpaltform.paper.IPaperQuesTeamScoreDAO;
import com.school.util.PageResult;

@Component  
public class PaperQuesTeamScoreDAO extends CommonDAO<PaperQuesTeamScore> implements IPaperQuesTeamScoreDAO {

	public Boolean doSave(PaperQuesTeamScore paperquesteamscore) {
		if (paperquesteamscore == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(paperquesteamscore, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(PaperQuesTeamScore paperquesteamscore) {
		if(paperquesteamscore==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(paperquesteamscore, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(PaperQuesTeamScore paperquesteamscore) {
		if (paperquesteamscore == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(paperquesteamscore, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<PaperQuesTeamScore> getList(PaperQuesTeamScore paperquesteamscore, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_paper_ques_team_score_proc_split(");
		List<Object> objList=new ArrayList<Object>();
        if (paperquesteamscore.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(paperquesteamscore.getRef());
        } else
            sqlbuilder.append("null,");
		if (paperquesteamscore.getPaperid() != null) {
			sqlbuilder.append("?,");
			objList.add(paperquesteamscore.getPaperid());
		} else
			sqlbuilder.append("null,");
        if (paperquesteamscore.getQuesref() != null) {
            sqlbuilder.append("?,");
            objList.add(paperquesteamscore.getQuesref());
        } else
            sqlbuilder.append("null,");

		if (paperquesteamscore.getScore() != null) {
			sqlbuilder.append("?,");
			objList.add(paperquesteamscore.getScore());
		} else
			sqlbuilder.append("null,");
        if (paperquesteamscore.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(paperquesteamscore.getCourseid());
        } else
            sqlbuilder.append("null,");

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
		List<PaperQuesTeamScore> paperquesteamscoreList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, PaperQuesTeamScore.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return paperquesteamscoreList;	
	}
	
	public List<Object> getSaveSql(PaperQuesTeamScore paperquesteamscore, StringBuilder sqlbuilder) {
		if(paperquesteamscore==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_paper_ques_team_score_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (paperquesteamscore.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquesteamscore.getPaperid());
			} else
				sqlbuilder.append("null,");
            if (paperquesteamscore.getQuesref() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getQuesref());
            } else
                sqlbuilder.append("null,");
			if (paperquesteamscore.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquesteamscore.getScore());
			} else
				sqlbuilder.append("null,");
            if (paperquesteamscore.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getCourseid());
            } else
                sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(PaperQuesTeamScore paperquesteamscore, StringBuilder sqlbuilder) {
		if(paperquesteamscore==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_paper_ques_team_score_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
            if (paperquesteamscore.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getRef());
            } else
                sqlbuilder.append("null,");
			if (paperquesteamscore.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquesteamscore.getPaperid());
			} else
				sqlbuilder.append("null,");
            if (paperquesteamscore.getQuesref() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getQuesref());
            } else
                sqlbuilder.append("null,");

			if (paperquesteamscore.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquesteamscore.getScore());
			} else
				sqlbuilder.append("null,");
            if (paperquesteamscore.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getCourseid());
            } else
                sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(PaperQuesTeamScore paperquesteamscore, StringBuilder sqlbuilder) {
		if(paperquesteamscore==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_paper_ques_team_score_proc_update(");
		List<Object>objList = new ArrayList<Object>();
            if (paperquesteamscore.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getRef());
            } else
                sqlbuilder.append("null,");

            if (paperquesteamscore.getPaperid() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquesteamscore.getPaperid());
			} else
				sqlbuilder.append("null,");
			if (paperquesteamscore.getQuesref() != null) {
				sqlbuilder.append("?,");
				objList.add(paperquesteamscore.getQuesref());
			} else
				sqlbuilder.append("null,");
            if (paperquesteamscore.getScore() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getScore());
            } else
                sqlbuilder.append("null,");
            if (paperquesteamscore.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(paperquesteamscore.getCourseid());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
