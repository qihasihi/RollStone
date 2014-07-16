package com.school.manager.inter.ethosaraisal;

import java.util.List;

import com.school.entity.ethosaraisal.ClassEthosInfo;
import com.school.manager.base.IBaseManager;

public interface IClassEthosManager extends IBaseManager<ClassEthosInfo> {
	public List<ClassEthosInfo> loadClassEthos(ClassEthosInfo obj);
	public List<List<String>> getClassEthosList(String termid,String grade,Integer weekid,Integer weekidend,Integer classid,String order);
	public List<List<String>> getEthosForClass(String termid, String grade,Integer weekid, Integer classid);
}
