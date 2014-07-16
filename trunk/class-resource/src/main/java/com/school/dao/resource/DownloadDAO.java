package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.DownloadInfo;
import com.school.dao.inter.resource.IDownloadDAO;
import com.school.util.PageResult;

@Component  
public class DownloadDAO extends CommonDAO<DownloadInfo> implements IDownloadDAO {

	public Boolean doSave(DownloadInfo downloadinfo) {
		if (downloadinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(downloadinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(DownloadInfo downloadinfo) {
		if(downloadinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(downloadinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(DownloadInfo downloadinfo) {
		if (downloadinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(downloadinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<DownloadInfo> getList(DownloadInfo downloadinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_download_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
	
		if (downloadinfo.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(downloadinfo.getResid());
		} else
			sqlbuilder.append("null,");
		if (downloadinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(downloadinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (downloadinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(downloadinfo.getRef());
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
		List<DownloadInfo> downloadinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, DownloadInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return downloadinfoList;	
	}
	
	public List<Object> getSaveSql(DownloadInfo downloadinfo, StringBuilder sqlbuilder) {
		if(downloadinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_download_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();		
			if (downloadinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getResid());
			} else
				sqlbuilder.append("null,");
			if (downloadinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (downloadinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getRef());
			} else
				sqlbuilder.append(this.getNextId());
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(DownloadInfo downloadinfo, StringBuilder sqlbuilder) {
		if(downloadinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_download_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			
			if (downloadinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getResid());
			} else
				sqlbuilder.append("null,");
			if (downloadinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (downloadinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getRef());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(DownloadInfo downloadinfo, StringBuilder sqlbuilder) {
		if(downloadinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_download_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			
			if (downloadinfo.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getResid());
			} else
				sqlbuilder.append("null,");
			if (downloadinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (downloadinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(downloadinfo.getRef());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

}
