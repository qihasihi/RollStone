package com.school.dao.inter;

import com.school.dao.base.ICommonDAO;
import com.school.entity.SmsInfo;

public interface ISmsDAO extends ICommonDAO<SmsInfo>{
	public Integer doSaveGetId(SmsInfo obj) ;
}