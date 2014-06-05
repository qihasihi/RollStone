package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.UserIdentityInfo;
import com.school.dao.inter.IUserIdentityDAO;
import com.school.util.PageResult;

@Component  
public class UserIdentityDAO extends CommonDAO<UserIdentityInfo> implements IUserIdentityDAO {

	public Boolean doSave(UserIdentityInfo useridentityinfo) {
		if (useridentityinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(useridentityinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(UserIdentityInfo useridentityinfo) {
		if(useridentityinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(useridentityinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(UserIdentityInfo useridentityinfo) {
		if (useridentityinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(useridentityinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<UserIdentityInfo> getList(UserIdentityInfo useridentityinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_user_identity_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (useridentityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getUserid());
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
		List<UserIdentityInfo> useridentityinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserIdentityInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return useridentityinfoList;	
	}
	
	public List<Object> getSaveSql(UserIdentityInfo useridentityinfo, StringBuilder sqlbuilder) {
		if(useridentityinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_user_identity_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if (useridentityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getUserid());
		} else
			sqlbuilder.append("null,");
			
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(UserIdentityInfo useridentityinfo, StringBuilder sqlbuilder) {
		if(useridentityinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_user_identity_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if (useridentityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(UserIdentityInfo useridentityinfo, StringBuilder sqlbuilder) {
		if(useridentityinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_user_identity_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (useridentityinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getIdentityname() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getIdentityname());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (useridentityinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(useridentityinfo.getUserid());
		} else
			sqlbuilder.append("null,");	
		sqlbuilder.append("?)}");
		return objList; 
	}
}
