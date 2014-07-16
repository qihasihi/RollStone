package com.school.dao.impl.activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.activity.ISiteDAO;
import com.school.entity.activity.SiteInfo;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-03-28
 * @description 活动场地数据访问类 
 */
@Component
public class SiteDAO extends CommonDAO<SiteInfo> implements ISiteDAO {
	
	
	public Boolean doDelete(SiteInfo obj) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @author 岳春阳
	 * @date 2013-03-28
	 * @description 添加活动场地
	 */
	public Boolean doSave(SiteInfo obj) {
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

	public List getListForSelect(){
		StringBuilder sqlbuilder = new StringBuilder("{call activitysite_proc_select_list(");
		//List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("?)}");	
		List<Integer> types=new ArrayList<Integer>();
		types.add(Types.INTEGER);
		Object[] objArray=new Object[1];		
		List list = this.executeResult_PROC(sqlbuilder.toString(), null, types, null, objArray);
		return list;
	}
	
	public List<SiteInfo> getList(SiteInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call activitysite_proc_search_split(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj==null){
			sqlbuilder.append("NULL,NULL,NULL,NULL,");
		}else{
			if(obj.getRef()>0){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getSitename()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getSitename());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getSitecontain()>0){
				sqlbuilder.append("?,");
				objlist.add(obj.getSitecontain());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getSitecontain2()>0){
				sqlbuilder.append("?,");
				objlist.add(obj.getSitecontain2());
			}else{
				sqlbuilder.append("NULL,");
			}
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
		List<SiteInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, SiteInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}

	


	
	/** 
	 * @author 岳春阳
	 * @return objlist：存放值得数组
	 * @param obj：数据实体，sqlbuilder：sql语句字符串
	 * @date 2013-03-28
	 * @descritpion 获取添加sql
	 */
	public List<Object> getSaveSql(SiteInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{ call activitysite_proc_add(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getSitename()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSitename());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSiteaddress()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSiteaddress());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSitecontain()>0){
			sqlbuilder.append("?,");
			objlist.add(obj.getSitecontain());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBaseinfo()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBaseinfo());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public Boolean doUpdate(SiteInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getUpdateSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}

	public List<Object> getDeleteSql(SiteInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		List<Object> objlist = new ArrayList<Object>();
		sqlbuilder.append("{call activitysite_proc_delete(");
		if(obj.getRef()>0){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}


	public List<Object> getUpdateSql(SiteInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{ call activitysite_proc_update(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()>0){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSitename()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSitename());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSiteaddress()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getSiteaddress());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getSitecontain()>0){
			sqlbuilder.append("?,");
			objlist.add(obj.getSitecontain());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBaseinfo()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getBaseinfo());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getState()==0){
			sqlbuilder.append("?,");
			objlist.add(obj.getState());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<SiteInfo> getListByActivity(String activityId) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{ call activity_site_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(activityId==null||activityId==""){
			return null;
		}else{
			sqlbuilder.append("?");
			objlist.add(activityId);
		}
		sqlbuilder.append(")}");	
		List<SiteInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, SiteInfo.class, null);
		return list;
	}
	
}
