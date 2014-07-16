package com.school.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IFnClickDAO;
import com.school.entity.FnClickInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IFnClickManager;
import com.school.util.PageResult;

@Service
public class FnClickManager extends BaseManager<FnClickInfo> implements
		IFnClickManager {

	private IFnClickDAO fnclickDAO;
	
	@Autowired
	@Qualifier("fnClickDAO")
	
	public void setFnclickDAO(IFnClickDAO fnclickDAO) {
		this.fnclickDAO = fnclickDAO;
	}

	
	@Override
	public Boolean doDelete(FnClickInfo obj) {
		// TODO Auto-generated method stub
		return this.fnclickDAO.doDelete(obj);
	}

	
	@Override
	public Boolean doSave(FnClickInfo obj) {
		// TODO Auto-generated method stub
		return this.fnclickDAO.doSave(obj);
	}

	@Override
	public Boolean doUpdate(FnClickInfo obj) {
		// TODO Auto-generated method stub
		return this.fnclickDAO.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<FnClickInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return this.fnclickDAO;
	}

	@Override
	public List<FnClickInfo> getList(FnClickInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.fnclickDAO.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(FnClickInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public FnClickInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(FnClickInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(FnClickInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
