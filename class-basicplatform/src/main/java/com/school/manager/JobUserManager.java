
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IJobUserDAO;
import com.school.util.PageResult;
import com.school.entity.JobUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IJobUserManager;

@Service
public class  JobUserManager extends BaseManager<JobUser> implements IJobUserManager  {
	private IJobUserDAO jobuserdao ;
	 
	@Autowired
	@Qualifier("jobUserDAO")
	public void setjobuserdao(IJobUserDAO jobuserdao) {
		this.jobuserdao = jobuserdao;
	}

	@Override
	public Boolean doDelete(JobUser obj) {
		return this.jobuserdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(JobUser obj) {
		return this.jobuserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(JobUser obj) {
		return this.jobuserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<JobUser> getBaseDAO() {
		return jobuserdao;
	}

	@Override
	public List<JobUser> getList(JobUser obj, PageResult presult) {
		return this.jobuserdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.jobuserdao.getNextId();
	}

	public List<Object> getDeleteSql(JobUser obj, StringBuilder sqlbuilder) {
		return this.jobuserdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(JobUser obj, StringBuilder sqlbuilder) { 
		return this.jobuserdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(JobUser obj, StringBuilder sqlbuilder) {
		return this.jobuserdao.getUpdateSql(obj, sqlbuilder);  
	}

	public JobUser getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	
	
}

