package com.school.manager.peer;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.peer.IPjPeerLogDAO;
import com.school.entity.DeptInfo;
import com.school.entity.peer.PjPeerLogInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.peer.IPjPeerLogManager;
import com.school.util.PageResult;
@Service
public class PjPeerLogManager extends BaseManager<PjPeerLogInfo> implements
		IPjPeerLogManager {
	private IPjPeerLogDAO pjpeerlogdao;	

	@Autowired
	@Qualifier("pjPeerLogDAO")
	
	public void setPjpeerlogDAO(IPjPeerLogDAO pjpeerlogDAO) {
		this.pjpeerlogdao = pjpeerlogDAO;
	}
	
	@Override
	public Boolean doDelete(PjPeerLogInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(PjPeerLogInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(PjPeerLogInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PjPeerLogInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return pjpeerlogdao;
	}

	@Override
	public List<PjPeerLogInfo> getList(PjPeerLogInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(PjPeerLogInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.getDeleteSql(obj, sqlbuilder);
	}

	public PjPeerLogInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(PjPeerLogInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(PjPeerLogInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<List<String>> getLogStat(String peerbaseref,
			Integer ptype, String deptid) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.getLogStat(peerbaseref, ptype, deptid);
	}

	public List<DeptInfo> getDeptInfo(String deptref) {
		// TODO Auto-generated method stub
		return this.pjpeerlogdao.getDeptInfo(deptref);
	}

	
}
