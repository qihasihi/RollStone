package com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IJobDAO;
import com.school.entity.JobInfo;
import com.school.manager.inter.IJobManager;
import com.school.util.PageResult;
@Service
public class JobManager extends CommonDAO<JobInfo> implements IJobManager{
	private IJobDAO jobdao;

	@Autowired
	@Qualifier("jobDAO")
	public void setJobdao(IJobDAO jobdao) {
		this.jobdao = jobdao;
	}
	
	public Boolean doSave(JobInfo obj) {
		// TODO Auto-generated method stub
		return jobdao.doSave(obj);
	}

	public Boolean doUpdate(JobInfo obj) {
		// TODO Auto-generated method stub
		return jobdao.doUpdate(obj);
	}

	public Boolean doDelete(JobInfo obj) {
		// TODO Auto-generated method stub
		return this.jobdao.doDelete(obj);
	}

	public List<JobInfo> getList(JobInfo obj, PageResult presult) {
		// TODO Auto-generated method stub
		return jobdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.jobdao.getNextId();
	}
	public List<Object> getSaveSql(JobInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getUpdateSql(JobInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Object> getDeleteSql(JobInfo obj, StringBuilder sqlbuilder) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public IJobDAO getJobdao() {
		return jobdao;
	}

	public JobInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
