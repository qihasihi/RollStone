package com.school.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IFnClickDAO;
import com.school.entity.ClassInfo;
import com.school.entity.FnClickInfo;
import com.school.util.PageResult;
@Component
public class FnClickDAO extends CommonDAO<FnClickInfo> implements IFnClickDAO {

	public Boolean doDelete(FnClickInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doSave(FnClickInfo obj) {
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

	public Boolean doUpdate(FnClickInfo obj) {
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

	public List<Object> getDeleteSql(FnClickInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<FnClickInfo> getList(FnClickInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder=new StringBuilder("{CALL fn_click_search(");
		List<Object> objList=new ArrayList<Object>();
		if(obj==null)
			sqlbuilder.append("NULL");
		else{
			if(obj.getFnid()!=null){
				sqlbuilder.append("?");
				objList.add(obj.getFnid());
			}else{
				sqlbuilder.append("NULL");
			}
		}	
		sqlbuilder.append(")}");
		List<FnClickInfo> list=this.executeResult_PROC(sqlbuilder.toString(), objList, null, FnClickInfo.class, null);
						
		return list;	
	}


	public List<Object> getSaveSql(FnClickInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call fn_click_add(");
		List<Object> objList = new ArrayList<Object>();
		if(obj.getFnid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getFnid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getClickcount()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClickcount());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(FnClickInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{call fn_click_update(");
		List<Object> objList = new ArrayList<Object>();
		if(obj.getFnid()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getFnid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getClickcount()!=null){
			sqlbuilder.append("?,");
			objList.add(obj.getClickcount());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objList;
	}

}
