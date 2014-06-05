package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ZTestUserInfo;

import java.util.List;

public interface IZTestUserInfoDAO extends ICommonDAO<ZTestUserInfo>{
	public List<ZTestUserInfo> getList();
}
