package com.school.dao.impl.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.activity.IActivitySiteDAO;
import com.school.entity.activity.ActivitySiteInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-03-27
 * @description 活动数据访问类 
 */
@Component
public class ActivitySiteDAO extends CommonDAO<ActivitySiteInfo> implements
		IActivitySiteDAO {

	public Boolean doDelete(ActivitySiteInfo obj) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doSave(ActivitySiteInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist =this.getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ActivitySiteInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(ActivitySiteInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call activity_site_proc_delete(");
		if(obj.getActivityid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getActivityid());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<ActivitySiteInfo> getList(ActivitySiteInfo obj,
			PageResult presult) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(ActivitySiteInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call activity_site_proc_add(");
		if(obj.getSiteIds()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSiteIds());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getActivityinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getActivityinfo().getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserinfo().getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserinfo().getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<Object> getUpdateSql(ActivitySiteInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
