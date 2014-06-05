package com.school.manager.ethosaraisal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ethosaraisal.IWeekDAO;
import com.school.entity.ethosaraisal.WeekInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ethosaraisal.IWeekManager;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 校风周次manager
 */
@Service
public class WeekManager extends BaseManager<WeekInfo> implements IWeekManager {
	
	private IWeekDAO weekdao;
	
	@Autowired
	@Qualifier("weekDAO")
	public void setWeekdao(IWeekDAO weekdao) {
		this.weekdao = weekdao;
	}

	@Override
	public Boolean doDelete(WeekInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doSave(WeekInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doUpdate(WeekInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<WeekInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return this.weekdao;
	}

	@Override
	public List<WeekInfo> getList(WeekInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.weekdao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(WeekInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public WeekInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(WeekInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(WeekInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
