
package com.school.manager.teachpaltform;

import java.util.List;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpResourceViewDAO;

import com.school.entity.teachpaltform.TpResourceView;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpResourceViewManager;
import com.school.util.PageResult;

@Service
public class  TpResourceViewManager extends BaseManager<TpResourceView> implements ITpResourceViewManager  { 
	
	private ITpResourceViewDAO tpresourceviewdao;
	
	@Autowired
	@Qualifier("tpResourceViewDAO")
	public void setTpresourceviewdao(ITpResourceViewDAO tpresourceviewdao) {
		this.tpresourceviewdao = tpresourceviewdao;
	}
	
	public Boolean doSave(TpResourceView tpresourceview) {
		return this.tpresourceviewdao.doSave(tpresourceview);
	}
	
	public Boolean doDelete(TpResourceView tpresourceview) {
		return this.tpresourceviewdao.doDelete(tpresourceview);
	}

	public Boolean doUpdate(TpResourceView tpresourceview) {
		return this.tpresourceviewdao.doUpdate(tpresourceview);
	}
	
	public List<TpResourceView> getList(TpResourceView tpresourceview, PageResult presult) {
		return this.tpresourceviewdao.getList(tpresourceview,presult);	
	}
	
	public List<Object> getSaveSql(TpResourceView tpresourceview, StringBuilder sqlbuilder) {
		return this.tpresourceviewdao.getSaveSql(tpresourceview,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpResourceView tpresourceview, StringBuilder sqlbuilder) {
		return this.tpresourceviewdao.getDeleteSql(tpresourceview,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpResourceView tpresourceview, StringBuilder sqlbuilder) {
		return this.tpresourceviewdao.getUpdateSql(tpresourceview,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpresourceviewdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpResourceView getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpResourceView> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpresourceviewdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

