package com.school.manager.evalteacher;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.manager.base.BaseManager;
import com.school.manager.inter.evalteacher.ITimeStepManager;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.evalteacher.ITimeStepInfoDAO;
import com.school.entity.evalteacher.TimeStepInfo;
import com.school.util.PageResult;

import jxl.Sheet;

@Service
public class TimeStepManager extends BaseManager<TimeStepInfo> implements ITimeStepManager  {

	private ITimeStepInfoDAO<TimeStepInfo> timestepinfodao;
	
	public ITimeStepInfoDAO<TimeStepInfo> getTimestepinfodao() {
		return timestepinfodao;
	}
	
	@Autowired
	@Qualifier("timeStepInfoDAO")
	public void setTimestepinfodao(ITimeStepInfoDAO<TimeStepInfo> timestepinfodao) {
		this.timestepinfodao = timestepinfodao;
	}

	public BaseManager<TimeStepInfo> getBaseManager() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getCount(TimeStepInfo model, boolean islike) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object[] getListWithPage(TimeStepInfo model, StringBuilder sql,
			PageResult pageSut) {
		// TODO Auto-generated method stub
		return null;
	}

	public TimeStepInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer delete(TimeStepInfo t) {
		// TODO Auto-generated method stub
		return this.timestepinfodao.delete(t);
	}

	public List<TimeStepInfo> getPageList(TimeStepInfo t,
			PageResult result) {
		// TODO Auto-generated method stub
		return this.timestepinfodao.getPageList(t, result);
	}

	public Integer save(TimeStepInfo t) {
		// TODO Auto-generated method stub
		return this.timestepinfodao.save(t);
	}

	public Integer update(TimeStepInfo t) {
		// TODO Auto-generated method stub
		return this.timestepinfodao.update(t);
	}

	public int getCount(TimeStepInfo t) {
		// TODO Auto-generated method stub
		return this.timestepinfodao.getCount(t);
	}

	public List<TimeStepInfo> getListByYear(TimeStepInfo t) {
		// TODO Auto-generated method stub
		return this.timestepinfodao.getListByYear(t);
	}

	public List<Object> getSaveSql(TimeStepInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(TimeStepInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(TimeStepInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TimeStepInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doSave(TimeStepInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doUpdate(TimeStepInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doDelete(TimeStepInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TimeStepInfo> getList(TimeStepInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.timestepinfodao.getNextId();
	}

}
