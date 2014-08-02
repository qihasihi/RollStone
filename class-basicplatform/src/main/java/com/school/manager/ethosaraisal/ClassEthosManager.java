package com.school.manager.ethosaraisal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ethosaraisal.IClassEthosDAO;
import com.school.entity.ethosaraisal.ClassEthosInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ethosaraisal.IClassEthosManager;
import com.school.util.PageResult;
/**
 * @author ‘¿¥∫—Ù
 * @date 2013-04-26
 * @description ∞‡º∂–£∑Ámanager 
 */
@Service
public class ClassEthosManager extends BaseManager<ClassEthosInfo> implements
		IClassEthosManager {
	
	private IClassEthosDAO classethosdao;
	
	@Autowired
	@Qualifier("classEthosDAO")
	public void setClassethosdao(IClassEthosDAO classethosdao) {
		this.classethosdao = classethosdao;
	}

	@Override
	public Boolean doDelete(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean doSave(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		return this.classethosdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ClassEthosInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return classethosdao;
	}

	@Override
	public List<ClassEthosInfo> getList(ClassEthosInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(ClassEthosInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.classethosdao.getDeleteSql(obj, sqlbuilder);
	}

	public ClassEthosInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(ClassEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.classethosdao.getSaveSql(obj, sqlbuilder);
	}

	public List<Object> getUpdateSql(ClassEthosInfo obj,
			StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ClassEthosInfo> loadClassEthos(ClassEthosInfo obj) {
		// TODO Auto-generated method stub
		return this.classethosdao.loadClassEthos(obj);
	}

	public List<List<String>> getClassEthosList(String termid, String grade,
			Integer weekid, Integer weekidend, Integer classid, String order) {
		// TODO Auto-generated method stub
		return this.classethosdao.getClassEthosList(termid, grade, weekid, weekidend, classid, order);
	}

	public List<List<String>> getEthosForClass(String termid, String grade,
			Integer weekid, Integer classid) {
		// TODO Auto-generated method stub
		return this.classethosdao.getEthosForClass(termid, grade, weekid, classid);
	}
}
