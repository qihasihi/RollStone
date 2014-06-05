package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ResourceKeyword;
import com.school.dao.inter.resource.IResourceKeywordDAO;
import com.school.util.PageResult;

@Component  
public class ResourceKeywordDAO extends CommonDAO<ResourceKeyword> implements IResourceKeywordDAO {

	public Boolean doSave(ResourceKeyword resourcekeyword) {
		if (resourcekeyword == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(resourcekeyword, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ResourceKeyword resourcekeyword) {
		if(resourcekeyword==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(resourcekeyword, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ResourceKeyword resourcekeyword) {
		if (resourcekeyword == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(resourcekeyword, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ResourceKeyword> getList(ResourceKeyword resourcekeyword, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_j_resource_keyword_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (resourcekeyword.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcekeyword.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcekeyword.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcekeyword.getCtime());
		} else
			sqlbuilder.append("null,");
		if (resourcekeyword.getKeyword() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcekeyword.getKeyword());
		} else
			sqlbuilder.append("null,");
		if (resourcekeyword.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcekeyword.getResid());
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
		List<ResourceKeyword> resourcekeywordList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceKeyword.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return resourcekeywordList;	
	}
	
	public List<Object> getSaveSql(ResourceKeyword resourcekeyword, StringBuilder sqlbuilder) {
		if(resourcekeyword==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_j_resource_keyword_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcekeyword.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getCtime());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getKeyword() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getKeyword());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ResourceKeyword resourcekeyword, StringBuilder sqlbuilder) {
		if(resourcekeyword==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_j_resource_keyword_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcekeyword.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getCtime());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getKeyword() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getKeyword());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ResourceKeyword resourcekeyword, StringBuilder sqlbuilder) {
		if(resourcekeyword==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_j_resource_keyword_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcekeyword.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getCtime());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getKeyword() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getKeyword());
			} else
				sqlbuilder.append("null,");
			if (resourcekeyword.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcekeyword.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArray_SQL(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
