package com.school.manager.peer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.peer.IPjPeerUserDAO;
import com.school.entity.peer.PjPeerUserInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.peer.IPjPeerUserManager;
import com.school.util.PageResult;
@Service
public class PjPeerUserManager extends BaseManager<PjPeerUserInfo> implements
		IPjPeerUserManager {
	private IPjPeerUserDAO pjpeeruserdao;
	@Autowired
	@Qualifier("pjPeerUserDAO")	
	public void setPjpeeruserdao(IPjPeerUserDAO pjpeeruserdao) {
		this.pjpeeruserdao = pjpeeruserdao;
	}
	@Override
	public Boolean doDelete(PjPeerUserInfo obj) {
		// TODO Auto-generated method stub
		return pjpeeruserdao.doDelete(obj);
	}
	@Override
	public Boolean doSave(PjPeerUserInfo obj) {
		// TODO Auto-generated method stub
		return pjpeeruserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(PjPeerUserInfo obj) {
		// TODO Auto-generated method stub
		return pjpeeruserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<PjPeerUserInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return pjpeeruserdao;
	}

	@Override
	public List<PjPeerUserInfo> getList(PjPeerUserInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return pjpeeruserdao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return "";
	}

	public List<Object> getDeleteSql(PjPeerUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return pjpeeruserdao.getDeleteSql(obj, sqlbuilder);
	}

	public PjPeerUserInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(PjPeerUserInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return pjpeeruserdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(PjPeerUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
