
package com.school.manager.teachpaltform;

import java.util.List;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITaskSuggestDAO;

import com.school.entity.teachpaltform.TaskSuggestInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITaskSuggestManager;
import com.school.util.PageResult;

@Service
public class  TaskSuggestManager extends BaseManager<TaskSuggestInfo> implements ITaskSuggestManager  { 
	
	private ITaskSuggestDAO tasksuggestdao;
	
	@Autowired
	@Qualifier("taskSuggestDAO")
	public void setTasksuggestdao(ITaskSuggestDAO tasksuggestdao) {
		this.tasksuggestdao = tasksuggestdao;
	}
	
	public Boolean doSave(TaskSuggestInfo tasksuggestinfo) {
		return this.tasksuggestdao.doSave(tasksuggestinfo);
	}
	
	public Boolean doDelete(TaskSuggestInfo tasksuggestinfo) {
		return this.tasksuggestdao.doDelete(tasksuggestinfo);
	}

	public Boolean doUpdate(TaskSuggestInfo tasksuggestinfo) {
		return this.tasksuggestdao.doUpdate(tasksuggestinfo);
	}
	
	public List<TaskSuggestInfo> getList(TaskSuggestInfo tasksuggestinfo, PageResult presult) {
		return this.tasksuggestdao.getList(tasksuggestinfo,presult);	
	}
	
	public List<Object> getSaveSql(TaskSuggestInfo tasksuggestinfo, StringBuilder sqlbuilder) {
		return this.tasksuggestdao.getSaveSql(tasksuggestinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TaskSuggestInfo tasksuggestinfo, StringBuilder sqlbuilder) {
		return this.tasksuggestdao.getDeleteSql(tasksuggestinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TaskSuggestInfo tasksuggestinfo, StringBuilder sqlbuilder) {
		return this.tasksuggestdao.getUpdateSql(tasksuggestinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tasksuggestdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TaskSuggestInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TaskSuggestInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tasksuggestdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

