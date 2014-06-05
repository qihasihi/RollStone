package com.school.dao.impl.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.activity.IActivityUserDAO;
import com.school.entity.activity.ActivitySiteInfo;
import com.school.entity.activity.ActivityUserInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-03-27
 * @description 活动数据访问类 
 */
@Component
public class ActivityUserDAO extends CommonDAO<ActivityUserInfo> implements IActivityUserDAO {

	public Boolean doDelete(ActivityUserInfo obj) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doSave(ActivityUserInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ActivityUserInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = this.getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(ActivityUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call activity_user_proc_delete(");
		if(obj.getActivityref()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getActivityref());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<ActivityUserInfo> getList(ActivityUserInfo obj,
			PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{ call activity_user_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null)
			return null;
		if(obj.getActivityinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getActivityinfo().getRef());
			if(obj.getAttitude()!=null){
				sqlbuilder.append("?");
				objlist.add(obj.getAttitude());
			}else{
				sqlbuilder.append("NULL");
			}
			sqlbuilder.append(")}");
			List<ActivityUserInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, ActivityUserInfo.class, null);
			return list;
		}else{
			return null;
		}
	}


	public List<Object> getSaveSql(ActivityUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call activity_user_proc_add(");
		if(obj.getUserids()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserids());
		}
		else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getActivityref()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getActivityref());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getCuserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getCuserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<Object> getUpdateSql(ActivityUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call activity_user_proc_update(");
		
		if(obj.getActivityref()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getActivityref());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAttitude()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAttitude());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	
}
