package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.UserColumnRightInfo;
import com.school.dao.inter.IUserColumnRightDAO;
import com.school.util.PageResult;

@Component  
public class UserColumnRightDAO extends CommonDAO<UserColumnRightInfo> implements IUserColumnRightDAO {

	public Boolean doSave(UserColumnRightInfo usercolumnrightinfo) {
		if (usercolumnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(usercolumnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(UserColumnRightInfo usercolumnrightinfo) {
		if(usercolumnrightinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(usercolumnrightinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(UserColumnRightInfo usercolumnrightinfo) {
		if (usercolumnrightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(usercolumnrightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<UserColumnRightInfo> getList(UserColumnRightInfo usercolumnrightinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL j_user_columnright_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (usercolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		
		if (usercolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getUserid());
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
		List<UserColumnRightInfo> usercolumnrightinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, UserColumnRightInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return usercolumnrightinfoList;	
	}
	
	public List<Object> getSaveSql(UserColumnRightInfo usercolumnrightinfo, StringBuilder sqlbuilder) {
		if(usercolumnrightinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_user_columnright_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if (usercolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		
		if (usercolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(UserColumnRightInfo usercolumnrightinfo, StringBuilder sqlbuilder) {
		if(usercolumnrightinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_user_columnright_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if (usercolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		
		if (usercolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(UserColumnRightInfo usercolumnrightinfo, StringBuilder sqlbuilder) {
		if(usercolumnrightinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL j_user_columnright_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (usercolumnrightinfo.getMuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getMuserid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getColumnid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		
		if (usercolumnrightinfo.getColumnrightid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getColumnrightid());
		} else
			sqlbuilder.append("null,");
		if (usercolumnrightinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(usercolumnrightinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
}
