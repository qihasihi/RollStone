
package com.school.manager.teachpaltform;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITaskPerformanceDAO;

import com.school.entity.teachpaltform.TaskPerformanceInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITaskPerformanceManager;
import com.school.util.PageResult;

@Service
public class  TaskPerformanceManager extends BaseManager<TaskPerformanceInfo> implements ITaskPerformanceManager  {
	
	private ITaskPerformanceDAO taskperformancedao;
	
	@Autowired
	@Qualifier("taskPerformanceDAO")
	public void setTaskperformancedao(ITaskPerformanceDAO taskperformancedao) {
		this.taskperformancedao = taskperformancedao;
	}
	
	public Boolean doSave(TaskPerformanceInfo taskperformanceinfo) {
		return this.taskperformancedao.doSave(taskperformanceinfo);
	}
	
	public Boolean doDelete(TaskPerformanceInfo taskperformanceinfo) {
		return this.taskperformancedao.doDelete(taskperformanceinfo);
	}

	public Boolean doUpdate(TaskPerformanceInfo taskperformanceinfo) {
		return this.taskperformancedao.doUpdate(taskperformanceinfo);
	}
	
	public List<TaskPerformanceInfo> getList(TaskPerformanceInfo taskperformanceinfo, PageResult presult) {
		return this.taskperformancedao.getList(taskperformanceinfo,presult);	
	}
	
	public List<Object> getSaveSql(TaskPerformanceInfo taskperformanceinfo, StringBuilder sqlbuilder) {
		return this.taskperformancedao.getSaveSql(taskperformanceinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TaskPerformanceInfo taskperformanceinfo, StringBuilder sqlbuilder) {
		return this.taskperformancedao.getDeleteSql(taskperformanceinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TaskPerformanceInfo taskperformanceinfo, StringBuilder sqlbuilder) {
		return this.taskperformancedao.getUpdateSql(taskperformanceinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.taskperformancedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TaskPerformanceInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TaskPerformanceInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return taskperformancedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	public List<TaskPerformanceInfo> getPerformListByTaskid(
			TaskPerformanceInfo t, Long classid,Integer classtype) {
		// TODO Auto-generated method stub
		return this.taskperformancedao.getPerformListByTaskid(t, classid,classtype);
	}

    public List<Map<String, Object>> getPerformanceNum(Long taskid, Long classid,Integer type) {
        return this.taskperformancedao.getPerformanceNum(taskid,classid,type);
    }

    @Override
    public List<Map<String, Object>> getPerformanceNum2(Long taskid, Long classid) {
        return this.taskperformancedao.getPerformanceNum2(taskid,classid);
    }

    public List<Map<String, Object>> getPerformanceOptionNum(Long taskid, Long classid) {
        return this.taskperformancedao.getPerformanceOptionNum(taskid,classid);
    }

    @Override
    public List<Map<String, Object>> getPerformanceOptionNum2(Long taskid, Long classid) {
        return this.taskperformancedao.getPerformanceOptionNum2(taskid,classid);
    }

    @Override
    public List<Map<String, Object>> getMicPerformanceOptionNum(Long taskid, Long questionid) {
        return this.taskperformancedao.getMicPerformanceOptionNum(taskid,questionid);
    }

    public List<TaskPerformanceInfo> getReplyColumsCount(TaskPerformanceInfo t) {
		// TODO Auto-generated method stub
		return this.taskperformancedao.getReplyColumsCount(t);
	}

	public List<TaskPerformanceInfo> getStuPerformanceStatus(
			TaskPerformanceInfo t) {
		// TODO Auto-generated method stub
		return this.taskperformancedao.getStuPerformanceStatus(t);
	}

    @Override
    public List<Map<String, Object>> getStuSelfPerformance(Integer userid, Long courseid,Integer group,String termid,Integer subjectid) {
        return this.taskperformancedao.getStuSelfPerformance(userid,courseid,group,termid,subjectid);
    }

    @Override
    public List<Map<String, Object>> getStuSelfPerformanceNum(Integer userid, String courseids, Integer group, String termid, Integer subjectid) {
        return this.taskperformancedao.getStuSelfPerformanceNum(userid,courseids,group,termid,subjectid);
    }
}

