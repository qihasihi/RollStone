package com.school.manager.inter.activity;

import java.util.List;

import com.school.entity.activity.ActivityInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

public interface IActivityManager extends IBaseManager<ActivityInfo>{
	public List<ActivityInfo> getActivityListBySite(int siteId);
	public List<ActivityInfo> getActivityByRef(String ref);
	public List<ActivityInfo> getAdminList(ActivityInfo obj,PageResult presult);
}
