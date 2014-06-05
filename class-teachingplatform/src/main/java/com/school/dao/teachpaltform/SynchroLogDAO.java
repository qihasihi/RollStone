package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.SynchroLogInfo;
import com.school.dao.inter.teachpaltform.ISynchroLogDAO;
import com.school.util.PageResult;

@Component  
public class SynchroLogDAO extends CommonDAO<SynchroLogInfo> implements ISynchroLogDAO {

	public Boolean doSave(SynchroLogInfo synchrologinfo) {
		if (synchrologinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(synchrologinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(SynchroLogInfo synchrologinfo) {
		if(synchrologinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(synchrologinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(SynchroLogInfo synchrologinfo) {
		if (synchrologinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(synchrologinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<SynchroLogInfo> getList(SynchroLogInfo synchrologinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL synchro_log_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (synchrologinfo.getErrortype() != null) {
			sqlbuilder.append("?,");
			objList.add(synchrologinfo.getErrortype());
		} else
			sqlbuilder.append("null,");
		if (synchrologinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(synchrologinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (synchrologinfo.getCtime() != null) {
			sqlbuilder.append("?,");
			objList.add(synchrologinfo.getCtime());
		} else
			sqlbuilder.append("null,");
		if (synchrologinfo.getErrormsg() != null) {
			sqlbuilder.append("?,");
			objList.add(synchrologinfo.getErrormsg());
		} else
			sqlbuilder.append("null,");
		if (synchrologinfo.getCuserid() != null) {
			sqlbuilder.append("?,");
			objList.add(synchrologinfo.getCuserid());
		} else
			sqlbuilder.append("null,");
		if (synchrologinfo.getIntertype() != null) {
			sqlbuilder.append("?,");
			objList.add(synchrologinfo.getIntertype());
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
		List<SynchroLogInfo> synchrologinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, SynchroLogInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return synchrologinfoList;	
	}
	
	public List<Object> getSaveSql(SynchroLogInfo synchrologinfo, StringBuilder sqlbuilder) {
		if(synchrologinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL synchro_log_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (synchrologinfo.getErrortype() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getErrortype());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getErrormsg() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getErrormsg());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getIntertype() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getIntertype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(SynchroLogInfo synchrologinfo, StringBuilder sqlbuilder) {
		if(synchrologinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL synchro_log_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (synchrologinfo.getErrortype() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getErrortype());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getErrormsg() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getErrormsg());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getIntertype() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getIntertype());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(SynchroLogInfo synchrologinfo, StringBuilder sqlbuilder) {
		if(synchrologinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL synchro_log_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (synchrologinfo.getErrortype() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getErrortype());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getErrormsg() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getErrormsg());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getCuserid() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getCuserid());
			} else
				sqlbuilder.append("null,");
			if (synchrologinfo.getIntertype() != null) {
				sqlbuilder.append("?,");
				objList.add(synchrologinfo.getIntertype());
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
