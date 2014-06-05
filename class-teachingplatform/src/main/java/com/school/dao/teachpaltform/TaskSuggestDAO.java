package com.school.dao.teachpaltform;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TaskSuggestInfo;
import com.school.dao.inter.teachpaltform.ITaskSuggestDAO;
import com.school.util.PageResult;

@Component  
public class TaskSuggestDAO extends CommonDAO<TaskSuggestInfo> implements ITaskSuggestDAO {

	public Boolean doSave(TaskSuggestInfo tasksuggestinfo) {
		if (tasksuggestinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(tasksuggestinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TaskSuggestInfo tasksuggestinfo) {
		if(tasksuggestinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(tasksuggestinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TaskSuggestInfo tasksuggestinfo) {
		if (tasksuggestinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(tasksuggestinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TaskSuggestInfo> getList(TaskSuggestInfo tasksuggestinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_task_suggest_info_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (tasksuggestinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(tasksuggestinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (tasksuggestinfo.getTaskid() != null) {
			sqlbuilder.append("?,");
			objList.add(tasksuggestinfo.getTaskid());
		} else
			sqlbuilder.append("null,");
		if (tasksuggestinfo.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(tasksuggestinfo.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (tasksuggestinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(tasksuggestinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (tasksuggestinfo.getIsanonymous() != null) {
			sqlbuilder.append("?,");
			objList.add(tasksuggestinfo.getIsanonymous());
		} else
			sqlbuilder.append("null,");
        if (tasksuggestinfo.getLoginuserid() != null) {
            sqlbuilder.append("?,");
            objList.add(tasksuggestinfo.getLoginuserid());
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
		List<TaskSuggestInfo> tasksuggestinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TaskSuggestInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return tasksuggestinfoList;	
	}
	
	public List<Object> getSaveSql(TaskSuggestInfo tasksuggestinfo, StringBuilder sqlbuilder) {
		if(tasksuggestinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_suggest_info_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (tasksuggestinfo.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getTaskid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getIsanonymous() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getIsanonymous());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getSuggestcontent() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getSuggestcontent());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TaskSuggestInfo tasksuggestinfo, StringBuilder sqlbuilder) {
		if(tasksuggestinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_suggest_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			if (tasksuggestinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getTaskid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			
			if (tasksuggestinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getIsanonymous() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getIsanonymous());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TaskSuggestInfo tasksuggestinfo, StringBuilder sqlbuilder) {
		if(tasksuggestinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_suggest_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();
			if (tasksuggestinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getCtime() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getCtime());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getIsanonymous() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getIsanonymous());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getTaskid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (tasksuggestinfo.getSuggestcontent() != null) {
				sqlbuilder.append("?,");
				objList.add(tasksuggestinfo.getSuggestcontent());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList; 
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
	
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

}
