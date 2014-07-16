package com.school.manager.peer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.peer.IPjPeerBaseDAO;
import com.school.entity.DeptUser;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.peer.IPjPeerBaseManager;
import com.school.util.PageResult;
@Service
public class PjPeerBaseManager extends BaseManager<PjPeerBaseInfo> implements
		IPjPeerBaseManager {
	private IPjPeerBaseDAO pjpeerbasedao;	
	
	@Autowired
	@Qualifier("pjPeerBaseDAO")
	
	public void setPjpeerbaseDAO(IPjPeerBaseDAO pjpeerbaseDAO) {
		this.pjpeerbasedao = pjpeerbaseDAO;
	}

	
	@Override
	public Boolean doDelete(PjPeerBaseInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.doDelete(obj);
	}

	@Override
	public Boolean doSave(PjPeerBaseInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(PjPeerBaseInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<PjPeerBaseInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return pjpeerbasedao;
	}

	@Override
	public List<PjPeerBaseInfo> getList(PjPeerBaseInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.getList(obj, presult);
	}

	

	public List<Object> getDeleteSql(PjPeerBaseInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.getDeleteSql(obj, sqlbuilder);
	}

	

	public List<Object> getSaveSql(PjPeerBaseInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(PjPeerBaseInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.getUpdateSql(obj, sqlbuilder);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}


	public PjPeerBaseInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}


	public List checkUserList(String deptref) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.checkUserList(deptref);
	}


	public List<DeptUser> getUserListByDeptref(String deptref) {
		// TODO Auto-generated method stub
		return this.pjpeerbasedao.getUserListByDeptref(deptref);
	}

	
}
