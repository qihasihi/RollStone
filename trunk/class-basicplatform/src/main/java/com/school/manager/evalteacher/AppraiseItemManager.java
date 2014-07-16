package com.school.manager.evalteacher;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;


import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.evalteacher.IAppraiseItemDAO;
import com.school.entity.evalteacher.AppraiseItemInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.base.IBaseManager;
import com.school.manager.inter.evalteacher.IAppraiseItemManager;
import com.school.util.PageResult;

@Service
public class AppraiseItemManager extends BaseManager<AppraiseItemInfo> implements IAppraiseItemManager{
	
	
	private IAppraiseItemDAO<AppraiseItemInfo> appraiseitemdao;	
	
	@Autowired
	@Qualifier("appraiseItemDAO")
	public void setAppraiseitemdao(IAppraiseItemDAO<AppraiseItemInfo> appraiseitemdao) {
		this.appraiseitemdao = appraiseitemdao;
	}

	public BaseManager<AppraiseItemInfo> getBaseManager() {
		// TODO Auto-generated method stub
		return this;
	}

	public int getCount(AppraiseItemInfo model, boolean islike) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Object[] getListWithPage(AppraiseItemInfo model, StringBuilder sql,
			PageResult pageSut) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer delete(AppraiseItemInfo t) {
		// TODO Auto-generated method stub
		return appraiseitemdao.delete(t);
	}

	public List<AppraiseItemInfo> getList(AppraiseItemInfo t, PageResult presult) {
		// TODO Auto-generated method stub
		return appraiseitemdao.getList(t, presult);
	}

	public Integer save(AppraiseItemInfo t) {
		// TODO Auto-generated method stub
		return appraiseitemdao.save(t);
	}

	public Integer update(AppraiseItemInfo t) {
		// TODO Auto-generated method stub
		return appraiseitemdao.update(t);
	}

	public AppraiseItemInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(AppraiseItemInfo t, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.appraiseitemdao.getSaveSql(t, sqlbuilder);
	}

	public List<Object>  getUpdateSql(AppraiseItemInfo t, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.appraiseitemdao.getUpdateSql(t, sqlbuilder);
	}

	public BigDecimal getNextSeqId() {
		// TODO Auto-generated method stub
		return this.appraiseitemdao.getNextSeqId();
	}
	/**
	 * 批量执行存储过程。
	 * @param sqlList  
	 * @param objList
	 * @return
	 */
	public boolean doMoreProcedure(List<String> sqlList,List<List<Object>> objList){
		return this.appraiseitemdao.executeArray_SQL(sqlList, objList);
	}

	public List<Object> getDeleteSql(AppraiseItemInfo t,
			StringBuilder sqlbuilder) {
		return this.appraiseitemdao.getDeleteSql(t, sqlbuilder); 
	}
	
	/**
	 * 得到数量
	 * @param t
	 * @return
	 */
	public Integer getAppraiseTitleCount(AppraiseItemInfo t){
		return this.appraiseitemdao.getAppraiseTitleCount(t);		
	}
	
	public List<AppraiseItemInfo> getAppraiseTitle(AppraiseItemInfo t, BigDecimal targetid,Boolean order){
		return this.appraiseitemdao.getAppraiseTitle(t,targetid,order);		
	}

	@Override
	protected ICommonDAO<AppraiseItemInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doSave(AppraiseItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doUpdate(AppraiseItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doDelete(AppraiseItemInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.appraiseitemdao.getNextId();
	}
	
	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return appraiseitemdao.executeArrayQuery_PROC(sqlArrayList, objArrayList);
	}
}
