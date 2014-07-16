package com.school.dao.inter.activity;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.UserInfo;
import com.school.entity.activity.ActivityInfo;
import com.school.util.PageResult;



public interface IActivityDAO extends ICommonDAO<ActivityInfo>{
	public List<ActivityInfo> getActivityListBySite(int siteId);
	public List<ActivityInfo> getActivityByRef(String ref);
	public List<ActivityInfo> getAdminList(ActivityInfo obj,PageResult presult);
}
