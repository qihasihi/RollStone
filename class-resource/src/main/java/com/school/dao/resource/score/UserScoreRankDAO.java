package com.school.dao.resource.score;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.score.UserScoreRank;
import com.school.dao.inter.resource.score.IUserScoreRankDAO;
import com.school.util.PageResult;

@Component  
public class UserScoreRankDAO extends CommonDAO<UserScoreRank> implements IUserScoreRankDAO {

	public Boolean doSave(UserScoreRank userscorerank) {
		if (userscorerank == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(userscorerank, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(UserScoreRank userscorerank) {
		if(userscorerank==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(userscorerank, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(UserScoreRank userscorerank) {
		if (userscorerank == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(userscorerank, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<UserScoreRank> getList(UserScoreRank userscorerank, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL user_score_rank_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (userscorerank.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getUserid());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getRef());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getRank() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getRank());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getSchoolid() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getSchoolid());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getTypeid() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getTypeid());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getOperatetype() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getOperatetype());
		} else
			sqlbuilder.append("null,");

		if (userscorerank.getScore() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getScore());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getUserrealname() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getUserrealname());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getSchoolname() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getSchoolname());
		} else
			sqlbuilder.append("null,");
		if (userscorerank.getModelid() != null) {
			sqlbuilder.append("?,");
			objList.add(userscorerank.getModelid());
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
		List<UserScoreRank> userscorerankList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserScoreRank.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return userscorerankList;	
	}
	
	public List<Object> getSaveSql(UserScoreRank userscorerank, StringBuilder sqlbuilder) {
		if(userscorerank==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL user_score_rank_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (userscorerank.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getUserid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getRef());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getRank() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getRank());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getSchoolid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getSchoolid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getTypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getTypeid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getCtime());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getScore());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getUserrealname() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getUserrealname());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getSchoolname() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getSchoolname());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getModelid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getModelid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(UserScoreRank userscorerank, StringBuilder sqlbuilder) {
		if(userscorerank==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL user_score_rank_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (userscorerank.getTypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getTypeid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getUserid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getRef());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getSchoolid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getSchoolid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getModelid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getModelid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(UserScoreRank userscorerank, StringBuilder sqlbuilder) {
		if(userscorerank==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL user_score_rank_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (userscorerank.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getUserid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getRef());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getRank() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getRank());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getSchoolid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getSchoolid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getTypeid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getTypeid());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getCtime());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getScore() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getScore());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getUserrealname() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getUserrealname());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getSchoolname() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getSchoolname());
			} else
				sqlbuilder.append("null,");
			if (userscorerank.getModelid() != null) {
				sqlbuilder.append("?,");
				objList.add(userscorerank.getModelid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

    /**
     * 根据UserId得到真实姓名
     * @param userid
     * @return
     */
    public String getRealNameByUserId(String userid){
        if(userid==null)
            return null;
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL getUserRealNameByUserId(?)}");
        List<Object>objList = new ArrayList<Object>();
        objList.add(userid);
        List<Map<String,Object>> mapList=this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(mapList!=null&&mapList.size()>0){
            Map<String,Object> objMap=mapList.get(0);
            if(objMap!=null&&objMap.size()>0){
                if(objMap.containsKey("REALNAME")&&objMap.get("REALNAME")!=null)
                return objMap.get("REALNAME").toString();
            }
        }
        return null;
    }

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(UserScoreRank entity,StringBuilder sqlbuilder){
        if(entity==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL user_score_rank_proc_synchro(");
        List<Object>objList = new ArrayList<Object>();
        if (entity.getRank() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getRank());
        } else
            sqlbuilder.append("null,");
        if (entity.getTypeid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getTypeid());
        } else
            sqlbuilder.append("null,");
        if (entity.getUserid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUserid());
        } else
            sqlbuilder.append("null,");
        if (entity.getOperatetype() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getOperatetype());
        } else
            sqlbuilder.append("null,");
        if (entity.getSchoolid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSchoolid());
        } else
            sqlbuilder.append("null,");
        if (entity.getUserrealname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getUserrealname());
        } else
            sqlbuilder.append("null,");
        if (entity.getModelid() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getModelid());
        } else
            sqlbuilder.append("null,");
        if (entity.getSchoolname() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getSchoolname());
        } else
            sqlbuilder.append("null,");
        if (entity.getScore() != null) {
            sqlbuilder.append("?,");
            objList.add(entity.getScore());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

}
