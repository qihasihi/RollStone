package com.school.dao.inter.ethosaraisal;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import com.school.entity.ethosaraisal.ClassEthosInfo;

public interface IClassEthosDAO extends ICommonDAO<ClassEthosInfo> {
	public List<ClassEthosInfo> loadClassEthos(ClassEthosInfo obj);
	public List<List<String>> getClassEthosList(String termid,String grade,Integer weekid,Integer weekidend,Integer classid,String order);
	public List<List<String>> getEthosForClass(String termid,String grade,Integer weekid,Integer classid);
}
