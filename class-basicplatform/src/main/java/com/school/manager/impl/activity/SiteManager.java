package com.school.manager.impl.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.activity.ISiteDAO;
import com.school.entity.activity.SiteInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.activity.ISiteManager;
import com.school.util.PageResult;

/**
 * @author 岳春阳
 * @date 2013-03-27
 * @description 活动场地Manger 
 */
@Service
public class SiteManager extends BaseManager<SiteInfo> implements ISiteManager {
	private ISiteDAO sitedao;
	@Autowired
	@Qualifier("siteDAO")
	public void setSitedao(ISiteDAO sitedao) {
		this.sitedao = sitedao;
	}

	@Override
	public Boolean doDelete(SiteInfo obj) {
		// TODO Auto-generated method stub
		Boolean b = this.sitedao.doDelete(obj);
		return b;
	}

	@Override
	public Boolean doSave(SiteInfo obj) {
		// TODO Auto-generated method stub
		Boolean b = this.sitedao.doSave(obj);
		return b;
	}

	@Override
	public Boolean doUpdate(SiteInfo obj) {
		// TODO Auto-generated method stub
		Boolean b = this.sitedao.doUpdate(obj);	
		return b;
	}

	@Override
	protected ICommonDAO<SiteInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public List getListForSelect(){
		List list = this.sitedao.getListForSelect();
		return list;
	}
	
	@Override
	public List<SiteInfo> getList(SiteInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		List<SiteInfo> list = this.sitedao.getList(obj, presult);
		return list;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.sitedao.getNextId();
	}

	public List<Object> getDeleteSql(SiteInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public SiteInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(SiteInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(SiteInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<SiteInfo> getListByActivity(String activityId) {
		// TODO Auto-generated method stub
		return this.sitedao.getListByActivity(activityId);
	}

	

}
