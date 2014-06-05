
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ISubjectUserDAO;
import com.school.util.PageResult;
import com.school.entity.SubjectUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ISubjectUserManager;

@Service
public class  SubjectUserManager extends BaseManager<SubjectUser> implements ISubjectUserManager  {
	private ISubjectUserDAO subjectuserdao ;
	 
	@Autowired
	@Qualifier("subjectUserDAO") 
	
	
	public void setsubjectuserdao(ISubjectUserDAO subjectuserdao) {
		this.subjectuserdao = subjectuserdao;
	}

	@Override
	public Boolean doDelete(SubjectUser obj) {
		return this.subjectuserdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(SubjectUser obj) {
		return this.subjectuserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(SubjectUser obj) {
		return this.subjectuserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<SubjectUser> getBaseDAO() {
		return subjectuserdao;
	}

	@Override
	public List<SubjectUser> getList(SubjectUser obj, PageResult presult) {
		return this.subjectuserdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.subjectuserdao.getNextId();
	}

	public List<Object> getDeleteSql(SubjectUser obj, StringBuilder sqlbuilder) {
		return this.subjectuserdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(SubjectUser obj, StringBuilder sqlbuilder) { 
		return this.subjectuserdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(SubjectUser obj, StringBuilder sqlbuilder) {
		return this.subjectuserdao.getUpdateSql(obj, sqlbuilder);  
	}

	public SubjectUser getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	
	
}

