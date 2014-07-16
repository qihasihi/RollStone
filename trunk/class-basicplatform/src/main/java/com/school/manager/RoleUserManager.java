
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IRoleUserDAO;
import com.school.util.PageResult;
import com.school.entity.RoleUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IRoleUserManager;

@Service
public class  RoleUserManager extends BaseManager<RoleUser> implements IRoleUserManager  {
	private IRoleUserDAO roleuserdao ;
	 
	@Autowired
	@Qualifier("roleUserDAO")
	
	
	public void setRoleuserdao(IRoleUserDAO roleuserdao) {
		this.roleuserdao = roleuserdao;
	}

	@Override
	public Boolean doDelete(RoleUser obj) {
		return this.roleuserdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(RoleUser obj) {
		return this.roleuserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(RoleUser obj) {
		return this.roleuserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<RoleUser> getBaseDAO() {
		return roleuserdao;
	}

	@Override
	public List<RoleUser> getList(RoleUser obj, PageResult presult) {
		return this.roleuserdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.roleuserdao.getNextId();
	}

	public List<Object> getDeleteSql(RoleUser obj, StringBuilder sqlbuilder) {
		return this.roleuserdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(RoleUser obj, StringBuilder sqlbuilder) { 
		return this.roleuserdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(RoleUser obj, StringBuilder sqlbuilder) {
		return this.roleuserdao.getUpdateSql(obj, sqlbuilder);  
	}

	public RoleUser getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	} 
	
	
	
}

