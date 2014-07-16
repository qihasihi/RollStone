package com.school.manager.impl.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.activity.IActivitySiteDAO;
import com.school.entity.activity.ActivitySiteInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.activity.IActivitySiteManager;
import com.school.util.PageResult;
/**
 * @author ‘¿¥∫—Ù
 * @date 2013-03-27
 * @description ªÓ∂ØManger 
 */
@Service
public class ActivitySiteManager extends BaseManager<ActivitySiteInfo>
		implements IActivitySiteManager {
	private IActivitySiteDAO activitysitedao;
	@Autowired
	@Qualifier("activitySiteDAO")
	public void setActivitysitedao(IActivitySiteDAO activitysitedao) {
		this.activitysitedao = activitysitedao;
	}

	@Override
	public Boolean doDelete(ActivitySiteInfo obj) {
		// TODO Auto-generated method stub
		return this.activitysitedao.doDelete(obj);
	}

	@Override
	public Boolean doSave(ActivitySiteInfo obj) {
		// TODO Auto-generated method stub
		Boolean b = this.activitysitedao.doSave(obj);
		return b;
	}

	@Override
	public Boolean doUpdate(ActivitySiteInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ActivitySiteInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivitySiteInfo> getList(ActivitySiteInfo obj,
			PageResult presult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.activitysitedao.getNextId();
	}

	public List<Object> getDeleteSql(ActivitySiteInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public ActivitySiteInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(ActivitySiteInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.activitysitedao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(ActivitySiteInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
