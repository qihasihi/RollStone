package com.school.manager.peer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.peer.IPjPeerItemDAO;
import com.school.entity.peer.PjPeerItemInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.peer.IPjPeerItemManager;
import com.school.util.PageResult;
@Service
public class PjPeerItemManager extends BaseManager<PjPeerItemInfo> implements
		IPjPeerItemManager {
	private IPjPeerItemDAO pjpeeritemdao;	

	@Autowired
	@Qualifier("pjPeerItemDAO")
	
	public void setPjpeeritemDAO(IPjPeerItemDAO pjpeeritemDAO) {
		this.pjpeeritemdao = pjpeeritemDAO;
	}
	
	@Override
	public Boolean doDelete(PjPeerItemInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeeritemdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(PjPeerItemInfo obj) {
		// TODO Auto-generated method stub
		return this.pjpeeritemdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(PjPeerItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PjPeerItemInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return pjpeeritemdao;
	}

	@Override
	public List<PjPeerItemInfo> getList(PjPeerItemInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.pjpeeritemdao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.pjpeeritemdao.getNextId();
	}

	public List<Object> getDeleteSql(PjPeerItemInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(PjPeerItemInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.pjpeeritemdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(PjPeerItemInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public PjPeerItemInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
