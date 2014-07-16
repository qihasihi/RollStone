package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ExtendResource;
import com.school.dao.inter.resource.IExtendResourceDAO;
import com.school.util.PageResult;

@Component  
public class ExtendResourceDAO extends CommonDAO<ExtendResource> implements IExtendResourceDAO {

	public Boolean doSave(ExtendResource extendresource) {
		if (extendresource == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(extendresource, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ExtendResource extendresource) {
		if(extendresource==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(extendresource, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ExtendResource extendresource) {
		if (extendresource == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(extendresource, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ExtendResource> getList(ExtendResource extendresource, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_extend_resource_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (extendresource.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getRef());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getValueid());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getResid());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getPosisions() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getPosisions());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getRootextendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getRootextendid());
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
		List<ExtendResource> extendresourceList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ExtendResource.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return extendresourceList;	
	}
	
	public List<Object> getSaveSql(ExtendResource extendresource, StringBuilder sqlbuilder) {
		if(extendresource==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_extend_resource_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if (extendresource.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getRef());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getValueid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getValueid());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getResid());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getPosisions() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getPosisions());
		} else
			sqlbuilder.append("null,");
		if (extendresource.getFullname() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getFullname());
		} else
			sqlbuilder.append("null,");	
		if (extendresource.getRootextendid() != null) {
			sqlbuilder.append("?,");
			objList.add(extendresource.getRootextendid());
		} else
			sqlbuilder.append("null,");	
			
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ExtendResource extendresource, StringBuilder sqlbuilder) {
		if(extendresource==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_extend_resource_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (extendresource.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getRef());
			} else
				sqlbuilder.append("null,");
			if (extendresource.getValueid() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getValueid());
			} else
				sqlbuilder.append("null,");
			if (extendresource.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getResid());
			} else
				sqlbuilder.append("null,");
		
		
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ExtendResource extendresource, StringBuilder sqlbuilder) {
		if(extendresource==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_extend_resource_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (extendresource.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getRef());
			} else
				sqlbuilder.append("null,");
			if (extendresource.getValueid() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getValueid());
			} else
				sqlbuilder.append("null,");
			if (extendresource.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getResid());
			} else
				sqlbuilder.append("null,");
			if (extendresource.getPosisions() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getPosisions());
			} else
				sqlbuilder.append("null,");	
			if (extendresource.getFullname() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getFullname());
			} else
				sqlbuilder.append("null,");		
			if (extendresource.getRootextendid() != null) {
				sqlbuilder.append("?,");
				objList.add(extendresource.getRootextendid());
			} else
				sqlbuilder.append("null,");	
		sqlbuilder.append("?)}");
		return objList; 
	}


}
