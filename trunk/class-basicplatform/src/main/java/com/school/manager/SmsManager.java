package com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.base.CommonDAO;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ISmsDAO;
import com.school.entity.SmsInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ISmsManager;
import com.school.util.PageResult;

@Service
public class SmsManager extends BaseManager<SmsInfo> implements ISmsManager {

	private ISmsDAO smsdao;
	
	@Autowired
	@Qualifier("smsDAO") 
	
	public void setSmsdao(ISmsDAO smsdao) {
		this.smsdao = smsdao;
	} 
	public Boolean doDelete(SmsInfo obj) {
		// TODO Auto-generated method stub
		return this.smsdao.doDelete(obj);
	}

 
	public Boolean doSave(SmsInfo obj) {
		// TODO Auto-generated method stub
		return this.smsdao.doSave(obj);
	}

	public Integer doSaveGetId(SmsInfo obj) {
		return this.smsdao.doSaveGetId(obj);
	}
	
	public Boolean doUpdate(SmsInfo obj) {
		// TODO Auto-generated method stub
		return this.smsdao.doUpdate(obj);
	}

	public List<Object> getDeleteSql(SmsInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.smsdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<SmsInfo> getList(SmsInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.smsdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.smsdao.getNextId();
	}

	public List<Object> getSaveSql(SmsInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.smsdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(SmsInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.smsdao.getUpdateSql(obj, sqlbuilder);
	}

	public SmsInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<SmsInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return smsdao;
	}


}
