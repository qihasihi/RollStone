
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IDeptDAO;
import com.school.util.PageResult;
import com.school.entity.DeptInfo;
import com.school.entity.DeptUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IDeptManager;

@Service
public class  DeptManager extends BaseManager<DeptInfo> implements IDeptManager  {
	private IDeptDAO deptdao ;
	 
	@Autowired
	@Qualifier("deptDAO")
	
	
	public void setDeptdao(IDeptDAO deptdao) {
		this.deptdao = deptdao;
	}

	@Override
	public Boolean doDelete(DeptInfo obj) {
		return this.deptdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(DeptInfo obj) {
		return this.deptdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(DeptInfo obj) {
		return this.deptdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<DeptInfo> getBaseDAO() {
		return deptdao;
	}

	@Override
	public List<DeptInfo> getList(DeptInfo obj, PageResult presult) {
		return this.deptdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.deptdao.getNextId();
	}

	public List<Object> getDeleteSql(DeptInfo obj, StringBuilder sqlbuilder) {
		return this.deptdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(DeptInfo obj, StringBuilder sqlbuilder) { 
		return this.deptdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(DeptInfo obj, StringBuilder sqlbuilder) {
		return this.deptdao.getUpdateSql(obj, sqlbuilder);  
	}

	public DeptInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null; 
	}

	public List<DeptUser> getNotInDeptUser(Integer roleid,String typeid,String name) {
		// TODO Auto-generated method stub
		return this.deptdao.getNotInDeptUser(roleid,typeid,name);
	} 
	
	
	
}

