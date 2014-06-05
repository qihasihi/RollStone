package com.school.manager.ethosaraisal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jxl.Sheet;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ethosaraisal.IStuEthosDAO;
import com.school.entity.ethosaraisal.StuEthosInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ethosaraisal.IStuEthosManager;
import com.school.util.PageResult;
/**
 * @author 岳春阳
 * @date 2013-04-26
 * @description 学员校风manager
 */
@Service
public class StuEthosManager extends BaseManager<StuEthosInfo> implements
		IStuEthosManager {

	private IStuEthosDAO stuehtosdao;
	
	@Autowired
	@Qualifier("stuEthosDAO")
	public void setStuehtosdao(IStuEthosDAO stuehtosdao) {
		this.stuehtosdao = stuehtosdao;
	}

	@Override
	public Boolean doDelete(StuEthosInfo obj) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(StuEthosInfo obj) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(StuEthosInfo obj) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<StuEthosInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return stuehtosdao;
	}

	@Override
	public List<StuEthosInfo> getList(StuEthosInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.getList(obj, presult);
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(StuEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.getDeleteSql(obj, sqlbuilder);
	}

	public StuEthosInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getSaveSql(StuEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(StuEthosInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<List<String>> getEthosKQ(String termid, Integer classid,
			String stuno, String ordercolumn, String dict) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.getEthosKQ(termid, classid, stuno, ordercolumn, dict);
	}

	public List<List<String>> getEthosWJ(String termid, Integer classid,
			String stuno, String ordercolumn, String dict) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.getEthosWJ(termid, classid, stuno, ordercolumn, dict);
	}

	public List<List<String>> getEthosZH(String termid, Integer classid,
			String stuno,Integer isshowrank) {
		// TODO Auto-generated method stub
		return this.stuehtosdao.getEthosZH(termid, classid, stuno,isshowrank);
	}

	
}
