package com.school.dao.teachpaltform;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.IQuestionDAO;
import com.school.dao.inter.teachpaltform.ITaskRemindDAO;
import com.school.entity.teachpaltform.TaskRemindInfo;
import com.school.entity.teachpaltform.TaskRemindInfo;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

@Component  
public class TaskRemindDAO extends CommonDAO<TaskRemindInfo> implements ITaskRemindDAO {

	public Boolean doSave(TaskRemindInfo taskremindinfo) {
		if (taskremindinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(taskremindinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TaskRemindInfo taskremindinfo) {
		if(taskremindinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(taskremindinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TaskRemindInfo taskremindinfo) {
		if (taskremindinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(taskremindinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TaskRemindInfo> getList(TaskRemindInfo taskremindinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL task_remind_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		
        if (taskremindinfo.getCtime() != null) {
            sqlbuilder.append("?,");
            objList.add(UtilTool.DateConvertToString(taskremindinfo.getCtime(), UtilTool.DateType.type1));
        } else
            sqlbuilder.append("null,");
        if (taskremindinfo.getSeltype() != null) {
            sqlbuilder.append("?,");
            objList.add(taskremindinfo.getSeltype());
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
		List<TaskRemindInfo> taskremindinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TaskRemindInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return taskremindinfoList;	
	}
	
	public List<Object> getSaveSql(TaskRemindInfo taskremindinfo, StringBuilder sqlbuilder) {
		if(taskremindinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL task_remind_proc_add(");
		List<Object>objList = new ArrayList<Object>();
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TaskRemindInfo taskremindinfo, StringBuilder sqlbuilder) {
		if(taskremindinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL question_info_proc_delete(");
		List<Object>objList = new ArrayList<Object>();

		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getUpdateSql(TaskRemindInfo taskremindinfo, StringBuilder sqlbuilder) {
		if(taskremindinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL question_info_proc_update(");
		List<Object>objList = new ArrayList<Object>();

		sqlbuilder.append("?)}");
		return objList; 
	}

    public String getNextId(){
		// TODO Auto-generated method stub
		return null;
	}


}
