package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IRoleDAO;
import com.school.entity.RoleInfo;
import com.school.util.PageResult;

@Component  
public class RoleDAO extends CommonDAO<RoleInfo> implements IRoleDAO {

	public Boolean doDelete(RoleInfo obj) {
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}

	public Boolean doSave(RoleInfo obj) {
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

	public Boolean doUpdate(RoleInfo obj) {
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

	public List<Object> getDeleteSql(RoleInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call role_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRoleid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRoleid());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<RoleInfo> getList(RoleInfo obj, PageResult presult) {
		StringBuilder sqlbuilder=new StringBuilder("{CALL role_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,");
		else{ 
			if(obj.getRoleid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRoleid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getRolename()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getRolename());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getFlag()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getFlag());
			}else
				sqlbuilder.append("NULL,");
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
		List<RoleInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, RoleInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return roleList;	
	}
	public List<Object> getSaveSql(RoleInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call role_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRolename()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRolename());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getFlag()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getFlag());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getIsadmin()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getIsadmin());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRemark()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRemark());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(RoleInfo obj, StringBuilder sqlbuilder) {
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call role_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getRoleid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRoleid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getRolename()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getRolename());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getFlag()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getFlag());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getIsadmin()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getIsadmin());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");
		return objList; 
	} 

}
