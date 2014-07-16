package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ResourceFileInfo;
import com.school.dao.inter.resource.IResourceFileDAO;
import com.school.util.PageResult;

@Component  
public class ResourceFileDAO extends CommonDAO<ResourceFileInfo> implements IResourceFileDAO {

	public Boolean doSave(ResourceFileInfo resourcefileinfo) {
		if (resourcefileinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(resourcefileinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ResourceFileInfo resourcefileinfo) {
		if(resourcefileinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(resourcefileinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ResourceFileInfo resourcefileinfo) {
		if (resourcefileinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(resourcefileinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ResourceFileInfo> getList(ResourceFileInfo resourcefileinfo, PageResult presult) {
		if(resourcefileinfo==null)
			resourcefileinfo=new ResourceFileInfo();
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_resource_file_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (resourcefileinfo.getFilesize() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getFilesize());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getFilename() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getFilename());
		} else
			sqlbuilder.append("null,");			
	
		if (resourcefileinfo.getSchoolid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getSchoolid());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getResid());
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
		List<ResourceFileInfo> resourcefileinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceFileInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return resourcefileinfoList;	
	}
	
	public List<Object> getSaveSql(ResourceFileInfo resourcefileinfo, StringBuilder sqlbuilder) {
		if(resourcefileinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_file_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcefileinfo.getFilesize() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcefileinfo.getFilesize());
			} else
				sqlbuilder.append("null,");
			if (resourcefileinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcefileinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcefileinfo.getFilename() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcefileinfo.getFilename());
			} else
				sqlbuilder.append("null,");			

		
//			if (resourcefileinfo.getSchoolid() != null) {
//				sqlbuilder.append("?,");
//				objList.add(resourcefileinfo.getSchoolid());
//			} else
//				sqlbuilder.append("null,");

			if (resourcefileinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcefileinfo.getResid());
			} else
				sqlbuilder.append("null,");
			if (resourcefileinfo.getPath() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcefileinfo.getPath().replace("\\", "\\\\"));
			} else
				sqlbuilder.append("null,");			
		sqlbuilder.append("?)}");
		System.out.println(sqlbuilder.toString());
		for(Object o:objList)
			System.out.println(o.toString());
		return objList;
	}

	public List<Object> getDeleteSql(ResourceFileInfo resourcefileinfo, StringBuilder sqlbuilder) {
		if(resourcefileinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_file_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
		if (resourcefileinfo.getFilesize() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getFilesize());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getFilename() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getFilename());
		} else
			sqlbuilder.append("null,");			
	
		if (resourcefileinfo.getSchoolid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getSchoolid());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ResourceFileInfo resourcefileinfo, StringBuilder sqlbuilder) {
		if(resourcefileinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_file_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (resourcefileinfo.getFilesize() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getFilesize());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getFilename() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getFilename());
		} else
			sqlbuilder.append("null,");			
	
		if (resourcefileinfo.getSchoolid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getSchoolid());
		} else
			sqlbuilder.append("null,");
		if (resourcefileinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcefileinfo.getResid());
		} else
			sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

}
