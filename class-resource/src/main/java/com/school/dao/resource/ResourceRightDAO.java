package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ResourceRightInfo;
import com.school.dao.inter.resource.IResourceRightDAO;
import com.school.util.PageResult;

@Component  
public class ResourceRightDAO extends CommonDAO<ResourceRightInfo> implements IResourceRightDAO {

	public Boolean doSave(ResourceRightInfo resourcerightinfo) {
		if (resourcerightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(resourcerightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ResourceRightInfo resourcerightinfo) {
		if(resourcerightinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(resourcerightinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ResourceRightInfo resourcerightinfo) {
		if (resourcerightinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(resourcerightinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ResourceRightInfo> getList(ResourceRightInfo resourcerightinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_resource_right_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if(resourcerightinfo==null){
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,");
		}else{
			if (resourcerightinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getRighttype() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRighttype()); 
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getRightroletype() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRightroletype());
			} else
				sqlbuilder.append("null,");
			
			if (resourcerightinfo.getRightsubject() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRightsubject());
			} else
				sqlbuilder.append("null,");		
			if (resourcerightinfo.getRightuserref() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRightuserref());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getResid());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getSubjectid());
			} else
				sqlbuilder.append("null,");
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
		List<ResourceRightInfo> resourcerightinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceRightInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return resourcerightinfoList;	
	}
	
	public List<Object> getSaveSql(ResourceRightInfo resourcerightinfo, StringBuilder sqlbuilder) {
		if(resourcerightinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_right_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcerightinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getRighttype() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRighttype());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getRightroletype() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRightroletype());
			} else
				sqlbuilder.append("null,");
			
			if (resourcerightinfo.getRightsubject() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRightsubject());
			} else
				sqlbuilder.append("null,");		
			if (resourcerightinfo.getRightuserref() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getRightuserref());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getResid());
			} else
				sqlbuilder.append("null,");
			if (resourcerightinfo.getSubjectid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcerightinfo.getSubjectid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ResourceRightInfo resourcerightinfo, StringBuilder sqlbuilder) {
		if(resourcerightinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_right_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if (resourcerightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getRighttype() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRighttype());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getRightroletype() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRightroletype());
		} else
			sqlbuilder.append("null,");
		
		if (resourcerightinfo.getRightsubject() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRightsubject());
		} else
			sqlbuilder.append("null,");		
		if (resourcerightinfo.getRightuserref() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRightuserref());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getSubjectid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getSubjectid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ResourceRightInfo resourcerightinfo, StringBuilder sqlbuilder) {
		if(resourcerightinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_right_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (resourcerightinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getRighttype() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRighttype());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getRightroletype() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRightroletype());
		} else
			sqlbuilder.append("null,");
		
		if (resourcerightinfo.getRightsubject() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRightsubject());
		} else
			sqlbuilder.append("null,");		
		if (resourcerightinfo.getRightuserref() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getRightuserref());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (resourcerightinfo.getSubjectid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcerightinfo.getSubjectid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}
}
