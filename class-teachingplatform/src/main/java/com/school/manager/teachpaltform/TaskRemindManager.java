
package com.school.manager.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.ITaskRemindDAO;
import com.school.entity.teachpaltform.TaskRemindInfo;
import com.school.entity.teachpaltform.TaskRemindInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITaskRemindManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskRemindManager extends BaseManager<TaskRemindInfo> implements ITaskRemindManager { 
	
	private ITaskRemindDAO taskreminddao;
	
	@Autowired
	@Qualifier("taskRemindDAO")
	public void setTaskreminddao(ITaskRemindDAO taskreminddao) {
		this.taskreminddao = taskreminddao;
	}
	
	public Boolean doSave(TaskRemindInfo taskremindinfo) {
		return this.taskreminddao.doSave(taskremindinfo);
	}
	
	public Boolean doDelete(TaskRemindInfo taskremindinfo) {
		return this.taskreminddao.doDelete(taskremindinfo);
	}

	public Boolean doUpdate(TaskRemindInfo taskremindinfo) {
		return this.taskreminddao.doUpdate(taskremindinfo);
	}
	
	public List<TaskRemindInfo> getList(TaskRemindInfo taskremindinfo, PageResult presult) {
		return this.taskreminddao.getList(taskremindinfo,presult);	
	}
	
	public List<Object> getSaveSql(TaskRemindInfo taskremindinfo, StringBuilder sqlbuilder) {
		return this.taskreminddao.getSaveSql(taskremindinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TaskRemindInfo taskremindinfo, StringBuilder sqlbuilder) {
		return this.taskreminddao.getDeleteSql(taskremindinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TaskRemindInfo taskremindinfo, StringBuilder sqlbuilder) {
		return this.taskreminddao.getUpdateSql(taskremindinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.taskreminddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TaskRemindInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TaskRemindInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return taskreminddao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

