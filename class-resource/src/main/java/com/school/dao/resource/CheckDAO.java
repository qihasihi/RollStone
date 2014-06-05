package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.CheckInfo;
import com.school.dao.inter.resource.ICheckDAO;
import com.school.util.PageResult;

@Component  
public class CheckDAO extends CommonDAO<CheckInfo> implements ICheckDAO {

	public Boolean doSave(CheckInfo checkinfo) {
		if (checkinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(checkinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(CheckInfo checkinfo) {
		if(checkinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(checkinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(CheckInfo checkinfo) {
		if (checkinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(checkinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<CheckInfo> getList(CheckInfo checkinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_check_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
	
		if (checkinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(checkinfo.getUserid());
		} else
			sqlbuilder.append("null,");
	
		if (checkinfo.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(checkinfo.getValueid());
		} else
			sqlbuilder.append("null,");

		if (checkinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(checkinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (checkinfo.getOperateuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(checkinfo.getOperateuserid());
		} else
			sqlbuilder.append("null,");
		if (checkinfo.getOperaterealname() != null) {
			sqlbuilder.append("?,");
			objList.add(checkinfo.getOperaterealname());
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
		List<CheckInfo> checkinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, CheckInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return checkinfoList;	
	}
	
	public List<Object> getSaveSql(CheckInfo checkinfo, StringBuilder sqlbuilder) {
		if(checkinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_check_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			
			if (checkinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getUserid());
			} else
				sqlbuilder.append("null,");
		
			
			if (checkinfo.getValueid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getValueid());
			} else
				sqlbuilder.append("null,");
			if (checkinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (checkinfo.getOperateuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getOperateuserid());
			} else
				sqlbuilder.append("null,");
			if (checkinfo.getOperaterealname() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getOperaterealname());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(CheckInfo checkinfo, StringBuilder sqlbuilder) {
		if(checkinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_check_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		
			if (checkinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			
			if (checkinfo.getValueid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getValueid());
			} else
				sqlbuilder.append("null,");
			if (checkinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getRef());
			} else
				sqlbuilder.append("null,");
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(CheckInfo checkinfo, StringBuilder sqlbuilder) {
		if(checkinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_check_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			
			if (checkinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getUserid());
			} else
				sqlbuilder.append("null,");
		
			if (checkinfo.getValueid() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getValueid());
			} else
				sqlbuilder.append("null,");
			if (checkinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(checkinfo.getRef());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
}
