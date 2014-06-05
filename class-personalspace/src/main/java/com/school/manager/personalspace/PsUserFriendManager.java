
package com.school.manager.personalspace;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.personalspace.IPsUserFriendDAO;
import com.school.entity.personalspace.PsUserFriend;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.personalspace.IPsUserFriendManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PsUserFriendManager extends BaseManager<PsUserFriend> implements IPsUserFriendManager {
	
	private IPsUserFriendDAO psuserfrienddao;
	
	@Autowired
	@Qualifier("psUserFriendDAO")
	public void setPsuserfrienddao(IPsUserFriendDAO psuserfrienddao) {
		this.psuserfrienddao = psuserfrienddao;
	}
	
	public Boolean doSave(PsUserFriend t) {
		return psuserfrienddao.doSave(t);
	}
	
	public Boolean doDelete(PsUserFriend t) {
		return psuserfrienddao.doDelete(t);
	}

	public Boolean doUpdate(PsUserFriend t) {
		return psuserfrienddao.doUpdate(t);
	}
	
	public List<PsUserFriend> getList(PsUserFriend t, PageResult presult) {
		return psuserfrienddao.getList(t,presult);	
	}
	
	public List<Object> getSaveSql(PsUserFriend t, StringBuilder sqlbuilder) {
		return psuserfrienddao.getSaveSql(t,sqlbuilder);
	}

	public List<Object> getDeleteSql(PsUserFriend t, StringBuilder sqlbuilder) {
		return psuserfrienddao.getDeleteSql(t,sqlbuilder);
	}

	public List<Object> getUpdateSql(PsUserFriend t, StringBuilder sqlbuilder) {
		return psuserfrienddao.getUpdateSql(t,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return psuserfrienddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public PsUserFriend getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PsUserFriend> getBaseDAO() {
		// TODO Auto-generated method stub
		return psuserfrienddao;
	}
	
	

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

    @Override
    public List<PsUserFriend> getMyFriendList(PsUserFriend t, PageResult pageResult) {
        return null;
    }
}

