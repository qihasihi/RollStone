package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.ResourceReport;
import com.school.dao.inter.resource.IResourceReportDAO;
import com.school.util.PageResult;

@Component  
public class ResourceReportDAO extends CommonDAO<ResourceReport> implements IResourceReportDAO {

	public Boolean doSave(ResourceReport resourcereport) {
		if (resourcereport == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(resourcereport, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(ResourceReport resourcereport) {
		if(resourcereport==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(resourcereport, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(ResourceReport resourcereport) {
		if (resourcereport == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(resourcereport, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<ResourceReport> getList(ResourceReport resourcereport, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_resource_report_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (resourcereport.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcereport.getRef());
		} else
			sqlbuilder.append("null,");
		if (resourcereport.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcereport.getUserid());
		} else
			sqlbuilder.append("null,");
		if (resourcereport.getContent() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcereport.getContent());
		} else
			sqlbuilder.append("null,");
		if (resourcereport.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(resourcereport.getResid());
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
		List<ResourceReport> resourcereportList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ResourceReport.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return resourcereportList;	
	}
	
	public List<Object> getSaveSql(ResourceReport resourcereport, StringBuilder sqlbuilder) {
		if(resourcereport==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_report_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcereport.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getUserid());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getContent() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getContent());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getResid());
			} else
				sqlbuilder.append("null,");
			
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(ResourceReport resourcereport, StringBuilder sqlbuilder) {
		if(resourcereport==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_report_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcereport.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getUserid());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getResid());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getContent() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getContent());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(ResourceReport resourcereport, StringBuilder sqlbuilder) {
		if(resourcereport==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_resource_report_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (resourcereport.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getUserid());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getRef());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getResid());
			} else
				sqlbuilder.append("null,");
			if (resourcereport.getContent() != null) {
				sqlbuilder.append("?,");
				objList.add(resourcereport.getContent());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

}
