package com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IRoleDAO;
import com.school.entity.RoleInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IRoleManager;
import com.school.util.PageResult;

@Service
public class RoleManager extends BaseManager<RoleInfo> implements IRoleManager{
	private IRoleDAO roledao;
	
	@Autowired
	@Qualifier("roleDAO")
	public void setRoledao(IRoleDAO roledao) {
		this.roledao = roledao;
	}

	@Override
	public Boolean doDelete(RoleInfo obj) {
		// TODO Auto-generated method stub
		return this.roledao.doDelete(obj);
	}

	@Override
	public Boolean doSave(RoleInfo obj) {
		// TODO Auto-generated method stub
		return this.roledao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(RoleInfo obj) {
		// TODO Auto-generated method stub
		return this.roledao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<RoleInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return roledao;
	}

	@Override
	public List<RoleInfo> getList(RoleInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.roledao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.roledao.getNextId();
	}

	public List<Object> getDeleteSql(RoleInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.roledao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(RoleInfo obj, StringBuilder sqlbuilder) { 
		// TODO Auto-generated method stub
		return this.roledao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(RoleInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.roledao.getUpdateSql(obj, sqlbuilder);  
	}

	public RoleInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
