package com.school.manager.inter;


import com.school.entity.SmsInfo;
import com.school.manager.base.IBaseManager;

public interface ISmsManager extends IBaseManager<SmsInfo>{
	
	public Integer doSaveGetId(SmsInfo obj);
	
}