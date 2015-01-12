package com.school.manager;

import java.util.List;

import com.school.dao.base.ICommonDAO;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.ISubjectDAO;
import com.school.entity.SubjectInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ISubjectManager;
import com.school.util.PageResult;


@Service
public class SubjectManager extends BaseManager<SubjectInfo> implements ISubjectManager{


	private ISubjectDAO subjectDAO;
	
	@Autowired
	@Qualifier("subjectDAO")
	public void setSubjectDAO(ISubjectDAO subjectDAO) {
		this.subjectDAO = subjectDAO;
	}
	

	public ISubjectDAO getSubjectDAO() {
		return subjectDAO;
	}


    @Override
    protected ICommonDAO<SubjectInfo> getBaseDAO() {
        return subjectDAO;
    }

    public Boolean doSave(SubjectInfo obj) {
		// TODO Auto-generated method stub
		return subjectDAO.doSave(obj);
	}


	public Boolean doUpdate(SubjectInfo obj) {
		// TODO Auto-generated method stub
		return subjectDAO.doUpdate(obj);
	}


	public Boolean doDelete(SubjectInfo obj) {
		// TODO Auto-generated method stub
		return subjectDAO.doDelete(obj);
	}


	public List<SubjectInfo> getList(SubjectInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return subjectDAO.getList(obj, presult);
	}



	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.subjectDAO.getNextId();
	}


	public List<Object> getSaveSql(SubjectInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return this.subjectDAO.getSaveSql(obj, sqlbuilder);
	}


	public List<Object> getUpdateSql(SubjectInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}


	public List<Object> getDeleteSql(SubjectInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}



	public SubjectInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public List<SubjectInfo> getHavaCourseSubject(String termid, String userref, int userid) {
        return this.subjectDAO.getHavaCourseSubject(termid,userref,userid);
    }

    @Override
    public List<SubjectInfo> getAdminPerformanceTeaSubject(Integer schoolid, Integer gradeid) {
        return this.subjectDAO.getAdminPerformanceTeaSubject(schoolid, gradeid);
    }

    @Override
    public List<SubjectInfo> getAdminPerformanceStuSubject(Integer schoolid, Integer gradeid) {
        return this.subjectDAO.getAdminPerformanceStuSubject(schoolid,gradeid);
    }
}
