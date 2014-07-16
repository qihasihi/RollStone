package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpResourceCollect;
import com.school.dao.inter.teachpaltform.ITpResourceCollectDAO;
import com.school.util.PageResult;

@Component  
public class TpResourceCollectDAO extends CommonDAO<TpResourceCollect> implements ITpResourceCollectDAO {

	public Boolean doSave(TpResourceCollect tpresourcecollect) {//
		if (tpresourcecollect == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpresourcecollect, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpResourceCollect tpresourcecollect) {
		if(tpresourcecollect==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpresourcecollect, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpResourceCollect tpresourcecollect) {
		if (tpresourcecollect == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpresourcecollect, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpResourceCollect> getList(TpResourceCollect tpresourcecollect, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_resource_collect_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpresourcecollect.getCollectid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourcecollect.getCollectid());
		} else
			sqlbuilder.append("null,");
		if (tpresourcecollect.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourcecollect.getUserid());
		} else
			sqlbuilder.append("null,");
		if (tpresourcecollect.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourcecollect.getResid());
		} else
			sqlbuilder.append("null,");
        if (tpresourcecollect.getCourseid() != null) {
            sqlbuilder.append("?,");
            objList.add(tpresourcecollect.getCourseid());
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
		List<TpResourceCollect> tpresourcecollectList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpResourceCollect.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpresourcecollectList;	
	}
	
	public List<Object> getSaveSql(TpResourceCollect tpresourcecollect, StringBuilder sqlbuilder) {
		if(tpresourcecollect==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_resource_collect_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			
			if (tpresourcecollect.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpresourcecollect.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getResid());
			} else
				sqlbuilder.append("null,");
            if (tpresourcecollect.getCourseid() != null) {
                sqlbuilder.append("?,");
                objList.add(tpresourcecollect.getCourseid());
            } else
                sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpResourceCollect tpresourcecollect, StringBuilder sqlbuilder) {
		if(tpresourcecollect==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_resource_collect_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			
			if (tpresourcecollect.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpresourcecollect.getCollectid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getCollectid());
			} else
				sqlbuilder.append("null,");
			if (tpresourcecollect.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpResourceCollect tpresourcecollect, StringBuilder sqlbuilder) {
		if(tpresourcecollect==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_resource_collect_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tpresourcecollect.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tpresourcecollect.getCollectid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getCollectid());
			} else
				sqlbuilder.append("null,");
			if (tpresourcecollect.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpresourcecollect.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourcecollect.getResid());
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
