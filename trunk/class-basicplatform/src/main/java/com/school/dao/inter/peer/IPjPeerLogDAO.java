package com.school.dao.inter.peer;

import java.util.List;
import java.util.Map;

import com.school.dao.base.ICommonDAO;
import com.school.entity.DeptInfo;
import com.school.entity.peer.PjPeerLogInfo;

public interface IPjPeerLogDAO extends ICommonDAO<PjPeerLogInfo> {
	public List<List<String>> getLogStat(String peerbaseref,Integer ptype,String deptid);
	public List<DeptInfo> getDeptInfo(String deptref);
}
