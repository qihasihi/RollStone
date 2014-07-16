package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IJobDAO;
import com.school.entity.JobInfo;
import com.school.util.PageResult;
@Component
public class JobDAO  extends CommonDAO<JobInfo> implements IJobDAO {

	public Boolean doSave(JobInfo obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public Boolean doUpdate(JobInfo obj) {
		// TODO Auto-generated method stub
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

	public Boolean doDelete(JobInfo obj) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public List<JobInfo> getList(JobInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		
		StringBuilder sqlbuilder=new StringBuilder("{CALL job_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,");
		else{
			if(obj.getJobname()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getJobname());
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
			objList.add(presult.getOrderBy().trim());
		}else
			sqlbuilder.append("NULL,");
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<JobInfo> jobInfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, JobInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return jobInfoList;
		
	}

	public List<Object> getSaveSql(JobInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if (sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL job_info_proc_add(");
		List<Object> objList = new ArrayList<Object>();
		if (obj != null) {
			if (obj.getJobname() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getJobname());
			} else {
				sqlbuilder.append("NULL,");
			}
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(JobInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		sqlbuilder.append("{CALL job_info_proc_update(");
		List<Object> objList = new ArrayList<Object>();
		if (obj != null) {
			if (obj.getJobid() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getJobid());
			} else
				sqlbuilder.append("NULL,");
			if (obj.getJobname() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getJobname());
			} else
				sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(JobInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if (sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL job_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (obj == null) {
			sqlbuilder.append("NULL,");
		} else {
			if (obj.getJobid() != null) {
				sqlbuilder.append("?,");
				objList.add(obj.getJobid());
			} else
				sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
