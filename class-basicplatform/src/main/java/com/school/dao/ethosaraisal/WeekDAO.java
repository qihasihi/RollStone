package com.school.dao.ethosaraisal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ethosaraisal.IWeekDAO;
import com.school.entity.activity.ActivityInfo;
import com.school.entity.ethosaraisal.WeekInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 校风周次数据访问类 
 */
@Component 
public class WeekDAO extends CommonDAO<WeekInfo> implements IWeekDAO {

	public Boolean doDelete(WeekInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doSave(WeekInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doUpdate(WeekInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List<Object> getDeleteSql(WeekInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<WeekInfo> getList(WeekInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call week_proc_search_split(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.gettermid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.gettermid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
			sqlbuilder.append("?,?,");
			objlist.add(presult.getPageNo());
			objlist.add(presult.getPageSize());
		}else{
			sqlbuilder.append("NULL,NULL,");
		}
		if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
			sqlbuilder.append("?,");
			objlist.add(presult.getOrderBy());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];
		List<WeekInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, WeekInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}

	public List<Object> getSaveSql(WeekInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(WeekInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
