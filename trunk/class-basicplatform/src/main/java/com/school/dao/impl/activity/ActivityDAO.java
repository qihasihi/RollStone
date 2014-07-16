package com.school.dao.impl.activity;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.activity.IActivityDAO;
import com.school.entity.UserInfo;
import com.school.entity.activity.ActivityInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

/**
 * @author 岳春阳
 * @date 2013-03-27
 * @description 活动数据访问类 
 */
@Component
public class ActivityDAO extends CommonDAO<ActivityInfo> implements IActivityDAO {

	/**
	 * @author 岳春阳
	 * @date 2013-03-27
	 * @description 添加活动
	 */
	public Boolean doSave(ActivityInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null)
		return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getSaveSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj != null && afficeObj.toString().trim().length()>0 
				&& Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		
		return false;
	}
	
	/** 
	 * @author 岳春阳
	 * @return objlist：存放值得数组
	 * @param obj：数据实体，sqlbuilder：sql语句字符串
	 * @date 2013-03-27 14:01
	 * @descritpion 获取添加sql
	 */
	public List<Object> getSaveSql(ActivityInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null)
		return null;
		sqlbuilder.append("{call activity_proc_add(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAtname()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAtname());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getBegintime()!=null){
			sqlbuilder.append("?,");
            objlist.add(UtilTool.DateConvertToString(obj.getBegintime(), DateType.type1));
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getEndtime()!=null){
			sqlbuilder.append("?,");
			objlist.add(UtilTool.DateConvertToString(obj.getEndtime(), DateType.type1));
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getContent()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getContent());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getEstimationnum()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getEstimationnum());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAudiovisual()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAudiovisual());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getIssign()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getIssign());
		}else{
			sqlbuilder.append("NULL,");
		}
		sqlbuilder.append("?)}");
//		System.out.println(sqlbuilder);
//		System.out.println(objlist);
		return objlist;
	}
    
	
	/** 
	 * @author 岳春阳
	 * @return objlist：存放值得数组
	 * @param obj：数据实体，sqlbuilder：sql语句字符串
	 * @date 2013-03-27 14:01
	 * @descritpion 获取修改sql
	 */
	public List<Object> getUpdateSql(ActivityInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||sqlbuilder==null||obj.getRef()==null)
			return null;
			sqlbuilder.append("{call activity_proc_update(");
			List<Object> objlist = new ArrayList<Object>();
			if(obj.getRef()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getRef());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getAtname()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getAtname());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getBegintime()!=null){
				sqlbuilder.append("?,");
	            objlist.add(UtilTool.DateConvertToString(obj.getBegintime(), DateType.type1));
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getEndtime()!=null){
				sqlbuilder.append("?,");
				objlist.add(UtilTool.DateConvertToString(obj.getEndtime(), DateType.type1));
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getContent()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getContent());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getEstimationnum()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getEstimationnum());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getAudiovisual()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getAudiovisual());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getIssign()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getIssign());
			}else{
				sqlbuilder.append("NULL,");
			}
			if(obj.getState()!=null){
				sqlbuilder.append("?,");
				objlist.add(obj.getState());
			}else{
				sqlbuilder.append("NULL,");
			}
			sqlbuilder.append("?)}");
			return objlist;
	}

	public Boolean doDelete(ActivityInfo obj) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return false;
		}
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objlist = getDeleteSql(obj, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(), objlist.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}
		return false;
	}

	public Boolean doUpdate(ActivityInfo obj) {
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

	public List<Object> getDeleteSql(ActivityInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		if(obj==null||obj.getRef()==null){
			return null;
		}
		sqlbuilder.append("{call activity_proc_delete(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getRef()!=null&&obj.getRef()!=""){
			sqlbuilder.append("?,");
			objlist.add(obj.getRef());
		}else{
			sqlbuilder.append("NULL");
		}
		sqlbuilder.append("?)}");
		return objlist;
	}

	public List<ActivityInfo> getList(ActivityInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder("{call activity_proc_search_split(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
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
		List<ActivityInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, ActivityInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}
	
	public List<ActivityInfo> getAdminList(ActivityInfo obj,PageResult presult){
		StringBuilder sqlbuilder = new StringBuilder("{call activity_proc_adminsearch_split(");
		List<Object> objlist = new ArrayList<Object>();
		if(obj.getUserid()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getUserid());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getAtname()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getAtname());
		}else{
			sqlbuilder.append("NULL,");
		}
		if(obj.getState()!=null){
			sqlbuilder.append("?,");
			objlist.add(obj.getState());
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
		List<ActivityInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, types, ActivityInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));	
		return list;
	}



	public List<ActivityInfo> getActivityListBySite(int siteId) {
		// TODO Auto-generated method stub
		if(siteId<=0){
			return null;
		}else{
			StringBuilder sqlbuilder = new StringBuilder("{call activitysite_proc_ck(");
			List<Object> objlist = new ArrayList<Object>();
			sqlbuilder.append("?");
			objlist.add(siteId);
			sqlbuilder.append(")}");			
			List<ActivityInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, ActivityInfo.class, null);
			return list;
		}
		
	}

	public List<ActivityInfo> getActivityByRef(String ref) {
		// TODO Auto-generated method stub
		if(ref==null){
			return null;
		}else{
			StringBuilder sqlbuilder = new StringBuilder("{call activity_proc_toupd(");
			List<Object> objlist = new ArrayList<Object>();
			sqlbuilder.append("?");
			objlist.add(ref);
			sqlbuilder.append(")}");			
			List<ActivityInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, ActivityInfo.class, null);
			return list;
		}
	}

	public List<UserInfo> getActivityUserByRef(String ref) {
		// TODO Auto-generated method stub
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{ call activity_user_proc_list(");
		List<Object> objlist = new ArrayList<Object>();
		if(ref!=null||ref!=""){
			sqlbuilder.append("?");
			objlist.add(ref);
			sqlbuilder.append(")}");
			List<UserInfo> list = this.executeResult_PROC(sqlbuilder.toString(), objlist, null, UserInfo.class, null);
			return list;
		}else{
			return null;
		}
	}
}
