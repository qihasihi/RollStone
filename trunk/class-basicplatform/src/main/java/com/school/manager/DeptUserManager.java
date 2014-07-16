
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IDeptUserDAO;
import com.school.util.PageResult;
import com.school.entity.DeptUser;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IDeptUserManager;

@Service
public class  DeptUserManager extends BaseManager<DeptUser> implements IDeptUserManager  {
	private IDeptUserDAO deptuserdao ;
	 
	@Autowired
	@Qualifier("deptUserDAO")
	public void setdeptuserdao(IDeptUserDAO deptuserdao) {
		this.deptuserdao = deptuserdao;
	}

	@Override
	public Boolean doDelete(DeptUser obj) {
		return this.deptuserdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(DeptUser obj) {
		return this.deptuserdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(DeptUser obj) {
		return this.deptuserdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<DeptUser> getBaseDAO() {
		return deptuserdao;
	}

	@Override
	public List<DeptUser> getList(DeptUser obj, PageResult presult) {
		return this.deptuserdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.deptuserdao.getNextId();
	}

	public List<Object> getDeleteSql(DeptUser obj, StringBuilder sqlbuilder) {
		return this.deptuserdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(DeptUser obj, StringBuilder sqlbuilder) { 
		return this.deptuserdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(DeptUser obj, StringBuilder sqlbuilder) {
		return this.deptuserdao.getUpdateSql(obj, sqlbuilder);  
	}

	public DeptUser getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}


    @Override
    public List<Object> getUpdateSqlLoyal(DeptUser obj, StringBuilder sqlbuilder) {
        return this.deptuserdao.getUpdateSqlLoyal(obj,sqlbuilder);
    }
}

