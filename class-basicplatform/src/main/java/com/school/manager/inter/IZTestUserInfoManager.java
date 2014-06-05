
package com.school.manager.inter;

import com.school.entity.ZTestUserInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IZTestUserInfoManager extends IBaseManager<ZTestUserInfo> {
	public List<ZTestUserInfo> getList();
} 
