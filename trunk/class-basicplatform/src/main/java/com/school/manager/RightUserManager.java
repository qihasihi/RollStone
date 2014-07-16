
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IRightUserDAO;
import com.school.util.PageResult;
import com.school.entity.RightUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IRightUserManager;

@Service
public class  RightUserManager extends BaseManager<RightUser> implements IRightUserManager  {
	private IRightUserDAO rightuserdao ;
	 
	@Autowired
	@Qualifier("rightUserDAO")
	
	
	public void setrightuserdao(IRightUserDAO rightuserdao) {
		this.rightuserdao = rightuserdao;
	}

	@Override
	public Boolean doDelete(RightUser obj) {
		return this.rightuserdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(RightUser obj) {
		return this.rightuserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(RightUser obj) {
		return this.rightuserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<RightUser> getBaseDAO() {
		return rightuserdao;
	}

	@Override
	public List<RightUser> getList(RightUser obj, PageResult presult) {
		return this.rightuserdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.rightuserdao.getNextId();
	}

	public List<Object> getDeleteSql(RightUser obj, StringBuilder sqlbuilder) {
		return this.rightuserdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(RightUser obj, StringBuilder sqlbuilder) { 
		return this.rightuserdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(RightUser obj, StringBuilder sqlbuilder) {
		return this.rightuserdao.getUpdateSql(obj, sqlbuilder);  
	}

	public RightUser getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	
	
}

