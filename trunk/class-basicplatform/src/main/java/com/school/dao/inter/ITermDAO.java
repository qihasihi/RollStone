
package com.school.dao.inter;

import java.util.List;
import java.util.Map;

import com.school.dao.base.ICommonDAO;
import com.school.entity.TermInfo;

public interface ITermDAO extends ICommonDAO<TermInfo>{
	List<TermInfo>getAvailableTermList();

	TermInfo getMaxIdTerm(Boolean flag);
	
	List<Map<String,Object>>  getYearTerm();
    public Boolean InitTerm();
}
