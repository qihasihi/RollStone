package com.school.manager.inter.peer;

import java.util.List;
import java.util.Map;

import com.school.entity.DeptInfo;
import com.school.entity.peer.PjPeerLogInfo;
import com.school.manager.base.IBaseManager;

public interface IPjPeerLogManager extends IBaseManager<PjPeerLogInfo> {
	public List<List<String>> getLogStat(String peerbaseref,Integer ptype,String deptid);
	public List<DeptInfo> getDeptInfo(String deptref);
}
