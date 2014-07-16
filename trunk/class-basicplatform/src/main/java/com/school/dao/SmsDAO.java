package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ISmsDAO;
import com.school.entity.SmsInfo;
import com.school.util.PageResult;

@Component
public class SmsDAO extends CommonDAO<SmsInfo> implements ISmsDAO {

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#doDelete(com.school.entity.SmsInfo)
	 */
	public Boolean doDelete(SmsInfo obj) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(obj, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#doExcetueArrayProc(java.util.List, java.util.List)
	 */
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
	//	this.executeArray_SQL(sqlArrayList, objArrayList);
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#doSave(com.school.entity.SmsInfo)
	 */
	public Boolean doSave(SmsInfo obj) {
		// TODO Auto-generated method stub
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

	
	public Integer doSaveGetId(SmsInfo obj) {
		// TODO Auto-generated method stub
		if (obj == null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null) 
			return Integer.parseInt(afficeObj.toString());
		else
			return null;
	}
	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#doUpdate(com.school.entity.SmsInfo)
	 */
	public Boolean doUpdate(SmsInfo obj) {
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

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#getDeleteSql(com.school.entity.SmsInfo, java.lang.StringBuilder)
	 */
	public List<Object> getDeleteSql(SmsInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if (sqlbuilder == null)
			return null;
		sqlbuilder.append("{CALL sms_info_proc_delete(");
		List<Object> objList = new ArrayList<Object>();
		if (obj == null) {
			sqlbuilder.append("NULL,");
		} else {
			if (obj.getSmsid()!= null) {
				sqlbuilder.append("?,");
				objList.add(obj.getSmsid());
			} else
				sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#getList(com.school.entity.SmsInfo, com.school.util.PageResult)
	 */
	public List<SmsInfo> getList(SmsInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder("{CALL sms_proc_search_split(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL,NULL,NULL,NULL,NULL"); 
		else{
			if(obj.getSmsid()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getSmsid()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSmsstatus()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getSmsstatus()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSmstitle()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSmstitle());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSenderid()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getSenderid()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getReceiverlist()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getReceiverlist());
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
		List<SmsInfo> smsInfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, SmsInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return smsInfoList;	
	}

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#getSaveSql(com.school.entity.SmsInfo, java.lang.StringBuilder)
	 */
	public List<Object> getSaveSql(SmsInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call sms_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		if(obj==null)
			return null;
		else{
			if(obj.getReceiverlist()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getReceiverlist());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSenderid()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSenderid());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSmstitle()!=null){
				sqlbuilder.append("?,");
				objList.add(obj.getSmstitle());
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSmscontent()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getSmscontent()); 
			}else
				sqlbuilder.append("NULL,");
			if(obj.getSmsstatus()!=null){ 
				sqlbuilder.append("?,");
				objList.add(obj.getSmsstatus()); 
			}else
				sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;			
	}

	/* (non-Javadoc)
	 * @see com.school.dao.ISmsDAO#getUpdateSql(com.school.entity.SmsInfo, java.lang.StringBuilder)
	 */
	public List<Object> getUpdateSql(SmsInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub

		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call sms_draft_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if(obj.getSmsid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSmsid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getReceiverlist()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getReceiverlist());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSenderid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSenderid());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSmstitle()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSmstitle());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSmscontent()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSmscontent());
		}else
			sqlbuilder.append("NULL,");
		if(obj.getSmsstatus()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getSmsstatus());
		}else
			sqlbuilder.append("NULL,");
		
		sqlbuilder.append("?)}");
		return objList; 
	
	}

}
