
package com.school.manager.teachpaltform;

import java.util.List;
import java.util.UUID;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpResourceCollectDAO;

import com.school.entity.teachpaltform.TpResourceCollect;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpResourceCollectManager;
import com.school.util.PageResult;

@Service
public class  TpResourceCollectManager extends BaseManager<TpResourceCollect> implements ITpResourceCollectManager  { 
	
	private ITpResourceCollectDAO tpresourcecollectdao;
	
	@Autowired
	@Qualifier("tpResourceCollectDAO")
	public void setTpresourcecollectdao(ITpResourceCollectDAO tpresourcecollectdao) {
		this.tpresourcecollectdao = tpresourcecollectdao;
	}
	
	public Boolean doSave(TpResourceCollect tpresourcecollect) {
		return this.tpresourcecollectdao.doSave(tpresourcecollect);
	}
	
	public Boolean doDelete(TpResourceCollect tpresourcecollect) {
		return this.tpresourcecollectdao.doDelete(tpresourcecollect);
	}

	public Boolean doUpdate(TpResourceCollect tpresourcecollect) {
		return this.tpresourcecollectdao.doUpdate(tpresourcecollect);
	}
	
	public List<TpResourceCollect> getList(TpResourceCollect tpresourcecollect, PageResult presult) {
		return this.tpresourcecollectdao.getList(tpresourcecollect,presult);	
	}
	
	public List<Object> getSaveSql(TpResourceCollect tpresourcecollect, StringBuilder sqlbuilder) {
		return this.tpresourcecollectdao.getSaveSql(tpresourcecollect,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpResourceCollect tpresourcecollect, StringBuilder sqlbuilder) {
		return this.tpresourcecollectdao.getDeleteSql(tpresourcecollect,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpResourceCollect tpresourcecollect, StringBuilder sqlbuilder) {
		return this.tpresourcecollectdao.getUpdateSql(tpresourcecollect,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpresourcecollectdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpResourceCollect getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpResourceCollect> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpresourcecollectdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

