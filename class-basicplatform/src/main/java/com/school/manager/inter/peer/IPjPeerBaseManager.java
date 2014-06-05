package com.school.manager.inter.peer;

import java.util.List;

import com.school.entity.DeptUser;
import com.school.entity.peer.PjPeerBaseInfo;
import com.school.manager.base.IBaseManager;

public interface IPjPeerBaseManager extends IBaseManager<PjPeerBaseInfo> {
	public List checkUserList(String deptref);
	public List<DeptUser> getUserListByDeptref(String deptref);
}
