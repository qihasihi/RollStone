package com.school.manager.impl.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.activity.IActivityDAO;
import com.school.entity.activity.ActivityInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.activity.IActivityManager;
import com.school.util.PageResult;
/**
 * @author ������
 * @date 2013-03-27
 * @description �Manger 
 */
@Service
public class ActivityManager extends BaseManager<ActivityInfo> implements IActivityManager {
	private IActivityDAO activitydao;
	
	@Autowired
	@Qualifier("activityDAO")
	public void setActivitydao(IActivityDAO activitydao) {
		this.activitydao = activitydao; 
	}

	
	@Override
	public Boolean doDelete(ActivityInfo obj) {
		// TODO Auto-generated method stub
		return this.activitydao.doDelete(obj);
	}
	/**
	 * @author ������
	 * @date 2013-03-27
	 * @description ��ӻ
	 */
	@Override
	public Boolean doSave(ActivityInfo obj) {
		// TODO Auto-generated method stub
		Boolean b = this.activitydao.doSave(obj);
		return b;
	}

	@Override
	public Boolean doUpdate(ActivityInfo obj) {
		// TODO Auto-generated method stub
		return this.activitydao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<ActivityInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return activitydao;
	}

	@Override
	public List<ActivityInfo> getList(ActivityInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.activitydao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.activitydao.getNextId();
	}

	public List<Object> getDeleteSql(ActivityInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public ActivityInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	/** 
	 * @author ������
	 * @return objlist�����ֵ������
	 * @param obj������ʵ�壬sqlbuilder��sql����ַ���
	 * @date 2013-03-27 14:01
	 * @descritpion ��ȡ���sql
	 */
	public List<Object> getSaveSql(ActivityInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.activitydao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(ActivityInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.activitydao.getUpdateSql(obj, sqlbuilder);
	}


	public List<ActivityInfo> getActivityListBySite(int siteId) {
		// TODO Auto-generated method stub
		return this.activitydao.getActivityListBySite(siteId);
	}


	public List<ActivityInfo> getActivityByRef(String ref) {
		// TODO Auto-generated method stub
		return this.activitydao.getActivityByRef(ref);
	}


	public List<ActivityInfo> getAdminList(ActivityInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.activitydao.getAdminList(obj, presult);
	}
	
}
