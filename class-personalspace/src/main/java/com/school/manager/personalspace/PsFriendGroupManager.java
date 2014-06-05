
package com.school.manager.personalspace;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.personalspace.IPsFriendGroupDAO;
import com.school.entity.personalspace.PsFriendGroupInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.personalspace.IPsFriendGroupManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PsFriendGroupManager extends BaseManager<PsFriendGroupInfo> implements IPsFriendGroupManager {
	
	private IPsFriendGroupDAO psfriendgroupdao;
	
	@Autowired
	@Qualifier("psFriendGroupDAO")
	public void setPsfriendgroupdao(IPsFriendGroupDAO psfriendgroupdao) {
		this.psfriendgroupdao = psfriendgroupdao;
	}
	
	public Boolean doSave(PsFriendGroupInfo checkinfo) {
		return psfriendgroupdao.doSave(checkinfo);
	}
	
	public Boolean doDelete(PsFriendGroupInfo checkinfo) {
		return psfriendgroupdao.doDelete(checkinfo);
	}

	public Boolean doUpdate(PsFriendGroupInfo checkinfo) {
		return psfriendgroupdao.doUpdate(checkinfo);
	}
	
	public List<PsFriendGroupInfo> getList(PsFriendGroupInfo checkinfo, PageResult presult) {
		return psfriendgroupdao.getList(checkinfo,presult);	
	}
	
	public List<Object> getSaveSql(PsFriendGroupInfo checkinfo, StringBuilder sqlbuilder) {
		return psfriendgroupdao.getSaveSql(checkinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(PsFriendGroupInfo checkinfo, StringBuilder sqlbuilder) {
		return psfriendgroupdao.getDeleteSql(checkinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(PsFriendGroupInfo checkinfo, StringBuilder sqlbuilder) {
		return psfriendgroupdao.getUpdateSql(checkinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return psfriendgroupdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public PsFriendGroupInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<PsFriendGroupInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return psfriendgroupdao;
	}
	
	

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

