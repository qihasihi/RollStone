package com.school.dao.inter.activity;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.activity.SiteInfo;

public interface ISiteDAO extends ICommonDAO<SiteInfo> {
	public List getListForSelect();
	public List<SiteInfo> getListByActivity(String activityId);
}
