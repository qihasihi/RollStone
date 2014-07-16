package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TpResourceView;
import com.school.dao.inter.teachpaltform.ITpResourceViewDAO;
import com.school.util.PageResult;

@Component  
public class TpResourceViewDAO extends CommonDAO<TpResourceView> implements ITpResourceViewDAO {

	public Boolean doSave(TpResourceView tpresourceview) {
		if (tpresourceview == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tpresourceview, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TpResourceView tpresourceview) {
		if(tpresourceview==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tpresourceview, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TpResourceView tpresourceview) {
		if (tpresourceview == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tpresourceview, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TpResourceView> getList(TpResourceView tpresourceview, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_resource_view_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tpresourceview.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourceview.getRef());
		} else
			sqlbuilder.append("null,");
		if (tpresourceview.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourceview.getUserid());
		} else
			sqlbuilder.append("null,");
		if (tpresourceview.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourceview.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (tpresourceview.getResid() != null) {
			sqlbuilder.append("?,");
			objList.add(tpresourceview.getResid());
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
		List<TpResourceView> tpresourceviewList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TpResourceView.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tpresourceviewList;	
	}
	
	public List<Object> getSaveSql(TpResourceView tpresourceview, StringBuilder sqlbuilder) {
		if(tpresourceview==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_resource_view_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			
			if (tpresourceview.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TpResourceView tpresourceview, StringBuilder sqlbuilder) {
		if(tpresourceview==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_resource_view_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (tpresourceview.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getResid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TpResourceView tpresourceview, StringBuilder sqlbuilder) {
		if(tpresourceview==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_resource_view_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tpresourceview.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getRef());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tpresourceview.getResid() != null) {
				sqlbuilder.append("?,");
				objList.add(tpresourceview.getResid());
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
