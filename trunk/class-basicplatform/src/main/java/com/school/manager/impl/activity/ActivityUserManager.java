package com.school.manager.impl.activity;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.activity.IActivityUserDAO;
import com.school.entity.activity.ActivityUserInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.activity.IActivityUserManager;
import com.school.util.PageResult;
/**
 * @author ‘¿¥∫—Ù
 * @date 2013-03-27
 * @description ªÓ∂ØManger 
 */
@Service
public class ActivityUserManager extends BaseManager<ActivityUserInfo>
		implements IActivityUserManager {
	private IActivityUserDAO activityuserdao;
	@Autowired
	@Qualifier("activityUserDAO")
	public void setActivityuserdao(IActivityUserDAO activityuserdao) {
		this.activityuserdao = activityuserdao;
	}
	@Override
	public Boolean doDelete(ActivityUserInfo obj) {
		// TODO Auto-generated method stub
		return this.activityuserdao.doDelete(obj);
	}
	@Override
	public Boolean doSave(ActivityUserInfo obj) {
		// TODO Auto-generated method stub
		return this.activityuserdao.doSave(obj);
	}
	@Override
	public Boolean doUpdate(ActivityUserInfo obj) {
		// TODO Auto-generated method stub
		return this.activityuserdao.doUpdate(obj);
	}
	@Override
	protected ICommonDAO<ActivityUserInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<ActivityUserInfo> getList(ActivityUserInfo obj,
			PageResult presult) {
		// TODO Auto-generated method stub
		return this.activityuserdao.getList(obj, presult);
	}
	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.activityuserdao.getNextId();
	}
	public List<Object> getDeleteSql(ActivityUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}
	public ActivityUserInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Object> getSaveSql(ActivityUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.activityuserdao.getSaveSql(obj, sqlbuilder);
	}
	public List<Object> getUpdateSql(ActivityUserInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

}
