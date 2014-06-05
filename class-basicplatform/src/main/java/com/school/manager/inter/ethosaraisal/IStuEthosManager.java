package com.school.manager.inter.ethosaraisal;

import java.util.List;

import com.school.entity.ethosaraisal.StuEthosInfo;
import com.school.manager.base.IBaseManager;

public interface IStuEthosManager extends IBaseManager<StuEthosInfo> {
	public List<List<String>> getEthosKQ(String termid, Integer classid,
			String stuno, String ordercolumn, String dict);
	public List<List<String>> getEthosWJ(String termid, Integer classid,
			String stuno, String ordercolumn, String dict);
	public List<List<String>> getEthosZH(String termid, Integer classid,
			String stuno,Integer isshowrank);
}
