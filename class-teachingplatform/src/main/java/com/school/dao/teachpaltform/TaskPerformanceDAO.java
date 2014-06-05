package com.school.dao.teachpaltform;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.teachpaltform.TaskPerformanceInfo;
import com.school.dao.inter.teachpaltform.ITaskPerformanceDAO;
import com.school.util.PageResult;

@Component  
public class TaskPerformanceDAO extends CommonDAO<TaskPerformanceInfo> implements ITaskPerformanceDAO {

	public Boolean doSave(TaskPerformanceInfo taskperformanceinfo) {
		if (taskperformanceinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getSaveSql(taskperformanceinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public Boolean doDelete(TaskPerformanceInfo taskperformanceinfo) {
		if(taskperformanceinfo==null)		
			return false;
		StringBuilder sqlbuilder=new StringBuilder();
		List<Object> objList=getDeleteSql(taskperformanceinfo, sqlbuilder);
		Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
		if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
			return true;
		}return false;
	}

	public Boolean doUpdate(TaskPerformanceInfo taskperformanceinfo) {
		if (taskperformanceinfo == null)
			return false;
		StringBuilder sqlbuilder = new StringBuilder();
		List<Object> objList = this.getUpdateSql(taskperformanceinfo, sqlbuilder);
		Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
				objList.toArray());
		if (afficeObj != null && afficeObj.toString().trim().length() > 0
				&& Integer.parseInt(afficeObj.toString()) > 0) {
			return true;
		}
		return false;
	}
	
	public List<TaskPerformanceInfo> getList(TaskPerformanceInfo taskperformanceinfo, PageResult presult) {
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_task_performance_proc_split(");
		List<Object> objList=new ArrayList<Object>();
		if (taskperformanceinfo.getRef() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getTaskid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getTaskid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getTasktype() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getTasktype());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getGroupid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getGroupid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getIsright() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getIsright());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getCriteria() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getCriteria());
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
		List<TaskPerformanceInfo> taskperformanceinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TaskPerformanceInfo.class, objArray);
		if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
			presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));				
		return taskperformanceinfoList;	
	}
	
	public List<Object> getSaveSql(TaskPerformanceInfo taskperformanceinfo, StringBuilder sqlbuilder) {
		if(taskperformanceinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_performance_proc_add(");
		List<Object>objList = new ArrayList<Object>();
			if (taskperformanceinfo.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getTaskid());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getTasktype() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getTasktype());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getGroupid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getGroupid());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getIsright() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getIsright());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getCriteria() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getCriteria());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getStatus() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getStatus());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}");
		return objList;
	}

	public List<Object> getDeleteSql(TaskPerformanceInfo taskperformanceinfo, StringBuilder sqlbuilder) {
		if(taskperformanceinfo==null || sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_performance_proc_delete(");
		List<Object>objList = new ArrayList<Object>();
			
			if (taskperformanceinfo.getRef() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getRef());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getUserid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getUserid());
			} else
				sqlbuilder.append("null,");
			if (taskperformanceinfo.getTaskid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getTaskid());
			} else
				sqlbuilder.append("null,");
			
			if (taskperformanceinfo.getCourseid() != null) {
				sqlbuilder.append("?,");
				objList.add(taskperformanceinfo.getCourseid());
			} else
				sqlbuilder.append("null,");
		sqlbuilder.append("?)}"); 
		return objList;
	}

	public List<Object> getUpdateSql(TaskPerformanceInfo taskperformanceinfo, StringBuilder sqlbuilder) {
		if(taskperformanceinfo==null||sqlbuilder==null)
			return null;
		sqlbuilder.append("{CALL tp_task_performance_proc_update(");
		List<Object>objList = new ArrayList<Object>();
		if (taskperformanceinfo.getRef() != null) { 
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getRef());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getTaskid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getTaskid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getTasktype() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getTasktype());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getCourseid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getCourseid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getGroupid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getGroupid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getUserid() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getUserid());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getIsright() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getIsright());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getCriteria() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getCriteria());
		} else
			sqlbuilder.append("null,");
		if (taskperformanceinfo.getStatus() != null) {
			sqlbuilder.append("?,");
			objList.add(taskperformanceinfo.getStatus());
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

	public List<TaskPerformanceInfo> getPerformListByTaskid(
			TaskPerformanceInfo t, Long classid,Integer classtype) {
		if(t.getTaskid()==null)
			return null;
		StringBuilder sqlbuilder = new StringBuilder();
		sqlbuilder.append("{CALL tp_qry_stu_performance(");
		List<Object> objList=new ArrayList<Object>();
		objList.add(t.getTaskid());
		sqlbuilder.append("?,");
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(",?");
        objList.add(classtype);
        sqlbuilder.append(")}");
		List<Integer> types=new ArrayList<Integer>();
		List<TaskPerformanceInfo> taskperformanceinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TaskPerformanceInfo.class, null);
		return taskperformanceinfoList;	
	}

    public List<Map<String, Object>> getPerformanceOptionNum(Long taskid, Long classid) {
        if(taskid==null||classid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_stu_performance_option_num(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        sqlbuilder.append("?,");
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> numList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return numList;
    }

    public List<Map<String, Object>> getPerformanceOptionNum2(Long taskid, Long classid) {
        if(taskid==null||classid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_stu_performance_option_num2(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        sqlbuilder.append("?,");
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> numList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return numList;
    }

    public List<Map<String, Object>> getPerformanceNum(Long taskid, Long classid,Integer type) {
        if(taskid==null||classid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_stu_performance_num(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        sqlbuilder.append("?,");
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?");
        objList.add(type);
        sqlbuilder.append(")}");
        List<Map<String,Object>> numList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return numList;
    }

    public List<Map<String, Object>> getPerformanceNum2(Long taskid, Long classid) {
        if(taskid==null||classid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_stu_performance_num2(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        sqlbuilder.append("?,");
        if(classid!=null){
            sqlbuilder.append("?");
            objList.add(classid);
        }else{
            sqlbuilder.append("NULL");
        }

        sqlbuilder.append(")}");
        List<Map<String,Object>> numList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return numList;
    }

    public List<TaskPerformanceInfo> getReplyColumsCount(TaskPerformanceInfo t) {
		StringBuilder sqlbuilder = new StringBuilder();
		if(t.getTaskid()==null)
			return null;
		sqlbuilder.append("{CALL tp_qry_columns_count_performance(");
		List<Object> objList=new ArrayList<Object>();
		objList.add(t.getTaskid());
		sqlbuilder.append("?)}");
		List<Integer> types=new ArrayList<Integer>();
		List<TaskPerformanceInfo> taskperformanceinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TaskPerformanceInfo.class, null);
		return taskperformanceinfoList;	
	}

	public List<TaskPerformanceInfo> getStuPerformanceStatus(
			TaskPerformanceInfo t) {
		StringBuilder sqlbuilder = new StringBuilder();
		if(t==null||t.getTaskid()==null||t.getUserid()==null)
			return null;
		sqlbuilder.append("{CALL tp_qry_stu_performance_status(");
		List<Object> objList=new ArrayList<Object>();
		objList.add(t.getTaskid());
		objList.add(t.getUserid()); 
		sqlbuilder.append("?,?)}");
		List<Integer> types=new ArrayList<Integer>();
		List<TaskPerformanceInfo> taskperformanceinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, TaskPerformanceInfo.class, null);
		return taskperformanceinfoList;	
	}

    public List<Map<String, Object>> getStuSelfPerformance(Integer userid, Long courseid,Integer group,String termid,Integer subjectid) {
        if(userid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_stu_self_performance(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(userid);
        sqlbuilder.append("?,");
        if(courseid!=null){
            sqlbuilder.append("?,");
            objList.add(courseid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(group!=null&&group==0){
            sqlbuilder.append("?,");
            objList.add(group);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(termid!=null){
            sqlbuilder.append("?,");
            objList.add(termid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(subjectid!=null){
            sqlbuilder.append("?");
            objList.add(subjectid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> performanceList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return performanceList;
    }

    public List<Map<String, Object>> getStuSelfPerformanceNum(Integer userid, String courseids, Integer group, String termid, Integer subjectid) {
        if(userid==null)
            return null;
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL tp_qry_stu_self_performance_num(");
        List<Object> objList=new ArrayList<Object>();
        objList.add(userid);
        sqlbuilder.append("?,");
        if(courseids!=null){
            sqlbuilder.append("?,");
            objList.add(courseids);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(group!=null&&group==0){
            sqlbuilder.append("?,");
            objList.add(group);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(termid!=null){
            sqlbuilder.append("?,");
            objList.add(termid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(subjectid!=null){
            sqlbuilder.append("?");
            objList.add(subjectid);
        }else{
            sqlbuilder.append("NULL");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> performanceList = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return performanceList;
    }

}
