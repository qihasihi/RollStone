package com.school.dao.resource;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.resource.OperateRecord;
import com.school.dao.inter.resource.IOperateRecordDAO;
import com.school.util.PageResult;

@Component  
public class OperateRecordDAO extends CommonDAO<OperateRecord> implements IOperateRecordDAO {

	public Boolean doSave(OperateRecord operaterecord) {
		if (operaterecord == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(operaterecord, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(OperateRecord operaterecord) {
		if(operaterecord==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(operaterecord, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(OperateRecord operaterecord) {
		if (operaterecord == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(operaterecord, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<OperateRecord> getList(OperateRecord operaterecord, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL rs_operate_record_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (operaterecord.getOperatetype() != null) {
			sqlbuilder.append("?,");
			objList.add(operaterecord.getOperatetype());
		} else
			sqlbuilder.append("null,");
		if (operaterecord.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(operaterecord.getUserid());
		} else
			sqlbuilder.append("null,");
		if (operaterecord.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(operaterecord.getResid());
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
		List<OperateRecord> operaterecordList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, OperateRecord.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return operaterecordList;	
	}
	
	public List<Object> getSaveSql(OperateRecord operaterecord, StringBuilder sqlbuilder) {
		if(operaterecord==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_operate_record_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (operaterecord.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (operaterecord.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getUserid());
			} else
				sqlbuilder.append("null,");
			if (operaterecord.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(OperateRecord operaterecord, StringBuilder sqlbuilder) {
		if(operaterecord==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_operate_record_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (operaterecord.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (operaterecord.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getUserid());
			} else
				sqlbuilder.append("null,");
			if (operaterecord.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(OperateRecord operaterecord, StringBuilder sqlbuilder) {
		if(operaterecord==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL rs_operate_record_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (operaterecord.getOperatetype() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getOperatetype());
			} else
				sqlbuilder.append("null,");
			if (operaterecord.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getUserid());
			} else
				sqlbuilder.append("null,");
			if (operaterecord.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(operaterecord.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

}
