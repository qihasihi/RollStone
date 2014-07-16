package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.ScoreInfo;
import com.school.dao.inter.IScoreDAO;
import com.school.util.PageResult;

@Component  
public class ScoreDAO extends CommonDAO<ScoreInfo> implements IScoreDAO {

	public Boolean doSave(ScoreInfo scoreinfo) {
		if (scoreinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(scoreinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ScoreInfo scoreinfo) {
		if(scoreinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(scoreinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ScoreInfo scoreinfo) {
		if (scoreinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(scoreinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ScoreInfo> getList(ScoreInfo scoreinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL score_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (scoreinfo.getScoreid() != null) {
			sqlbuilder.append("?,");
			objList.add(scoreinfo.getScoreid());
		} else
			sqlbuilder.append("null,");
		if (scoreinfo.getCommentid() != null) {
			sqlbuilder.append("?,");
			objList.add(scoreinfo.getCommentid());
		} else
			sqlbuilder.append("null,");
		if (scoreinfo.getScoretype() != null) {
			sqlbuilder.append("?,");
			objList.add(scoreinfo.getScoretype());
		} else
			sqlbuilder.append("null,");
		if (scoreinfo.getScoreobjectid() != null) {
			sqlbuilder.append("?,");
			objList.add(scoreinfo.getScoreobjectid());
		} else
			sqlbuilder.append("null,");
		if (scoreinfo.getScoreuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(scoreinfo.getScoreuserid());
		} else
			sqlbuilder.append("null,");
		if (scoreinfo.getScore() != null) {
			sqlbuilder.append("?,");
			objList.add(scoreinfo.getScore());
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
		List<ScoreInfo> scoreinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ScoreInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return scoreinfoList;	
	}
	
	public List<Object> getSaveSql(ScoreInfo scoreinfo, StringBuilder sqlbuilder) {
		if(scoreinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL score_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (scoreinfo.getScoreid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoretype() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoretype());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreobjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreobjectid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScore());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getCommentid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getCommentid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ScoreInfo scoreinfo, StringBuilder sqlbuilder) {
		if(scoreinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL score_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (scoreinfo.getCommentid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getCommentid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoretype() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoretype());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScore());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreobjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreobjectid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ScoreInfo scoreinfo, StringBuilder sqlbuilder) {
		if(scoreinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL score_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (scoreinfo.getCommentid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getCommentid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoretype() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoretype());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScore());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreobjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreobjectid());
			} else
				sqlbuilder.append("null,");
			if (scoreinfo.getScoreuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(scoreinfo.getScoreuserid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

}
