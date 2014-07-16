package com.school.dao.inter.peer;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.DeptUser;
import com.school.entity.peer.PjPeerBaseInfo;

public interface IPjPeerBaseDAO extends ICommonDAO<PjPeerBaseInfo> {
	public List checkUserList(String deptref);
	public List<DeptUser> getUserListByDeptref(String deptref);
}
