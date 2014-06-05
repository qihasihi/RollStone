package com.school.dao.resource.score;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.resource.score.*;
import com.school.entity.resource.score.SchoolScoreRank;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component  
public class SchoolScoreRankDAO extends CommonDAO<SchoolScoreRank> implements ISchoolScoreRankDAO {

	public Boolean doSave(SchoolScoreRank schoolscorerank) {
		if (schoolscorerank == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(schoolscorerank, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(SchoolScoreRank schoolscorerank) {
		if(schoolscorerank==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(schoolscorerank, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(SchoolScoreRank schoolscorerank) {
		if (schoolscorerank == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(schoolscorerank, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<SchoolScoreRank> getList(SchoolScoreRank schoolscorerank, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL school_score_rank_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (schoolscorerank.getSchoolname() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getSchoolname());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getModelid() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getModelid());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getSchoolid() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getSchoolid());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getScore() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getScore());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getTypeid() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getTypeid());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getOperatetype() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getOperatetype());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getRank() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getRank());
		} else
			sqlbuilder.append("null,");
		if (schoolscorerank.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(schoolscorerank.getRef());
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
		List<SchoolScoreRank> schoolscorerankList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, SchoolScoreRank.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return schoolscorerankList;	
	}
	
	public List<Object> getSaveSql(SchoolScoreRank schoolscorerank, StringBuilder sqlbuilder) {
		if(schoolscorerank==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL school_score_rank_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (schoolscorerank.getSchoolname() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getSchoolname());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getModelid() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getModelid());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getSchoolid() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getSchoolid());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getScore());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getTypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getTypeid());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getRank() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getRank());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getRef());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getCtime());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(SchoolScoreRank schoolscorerank, StringBuilder sqlbuilder) {
		if(schoolscorerank==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL school_score_rank_proc_delete(");
		List<Object>objList = new ArrayList<Object>();

            if (schoolscorerank.getTypeid() != null) {
                sqlbuilder.append("?,");
                objList.add(schoolscorerank.getTypeid());
            } else
                sqlbuilder.append("null,");
            if (schoolscorerank.getSchoolid() != null) {
                sqlbuilder.append("?,");
                objList.add(schoolscorerank.getSchoolid());
            } else
                sqlbuilder.append("null,");

            if (schoolscorerank.getRef() != null) {
                sqlbuilder.append("?,");
                objList.add(schoolscorerank.getRef());
            } else
                sqlbuilder.append("null,");


			if (schoolscorerank.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getOperatetype());
			} else
				sqlbuilder.append("null,");
            if (schoolscorerank.getModelid() != null) {
                sqlbuilder.append("?,");
                objList.add(schoolscorerank.getModelid());
            } else
                sqlbuilder.append("null,");

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(SchoolScoreRank schoolscorerank, StringBuilder sqlbuilder) {
		if(schoolscorerank==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL school_score_rank_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (schoolscorerank.getSchoolname() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getSchoolname());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getModelid() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getModelid());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getSchoolid() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getSchoolid());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getScore());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getTypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getTypeid());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getRank() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getRank());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getRef());
			} else
				sqlbuilder.append("null,");
			if (schoolscorerank.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(schoolscorerank.getCtime());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(SchoolScoreRank entity,StringBuilder sqlbuilder){
        if(entity==null||sqlbuilder==null)return null;
        List<Object> objList=new ArrayList<Object>();
        sqlbuilder.append("{CALL school_score_rank_proc_synchro(");
        if(entity==null)
            sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
        else{
            if(entity.getSchoolname()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getSchoolname());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getTypeid()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getTypeid());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getSchoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getSchoolid());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getRank()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getRank());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getOperatetype()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getOperatetype());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getScore()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getScore());
            }else
                sqlbuilder.append("NULL,");
            if(entity.getModelid()!=null){
                sqlbuilder.append("?,");
                objList.add(entity.getModelid());
            }else
                sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        return objList;
    }
}
