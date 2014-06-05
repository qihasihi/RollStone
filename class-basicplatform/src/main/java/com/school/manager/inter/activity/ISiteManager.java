package com.school.manager.inter.activity;

import java.util.List;

import com.school.entity.activity.SiteInfo;
import com.school.manager.base.IBaseManager;

public interface ISiteManager extends IBaseManager<SiteInfo>{
	public List getListForSelect();
	public List<SiteInfo> getListByActivity(String activityId);
}
