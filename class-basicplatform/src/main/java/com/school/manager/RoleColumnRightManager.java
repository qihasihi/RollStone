
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IRoleColumnRightDAO;

import com.school.entity.RoleColumnRightInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IRoleColumnRightManager;
import com.school.util.PageResult;

@Service
public class  RoleColumnRightManager extends BaseManager<RoleColumnRightInfo> implements IRoleColumnRightManager  { 
	
	private IRoleColumnRightDAO rolecolumnrightdao;
	
	@Autowired
	@Qualifier("roleColumnRightDAO")
	public void setRolecolumnrightdao(IRoleColumnRightDAO rolecolumnrightdao) {
		this.rolecolumnrightdao = rolecolumnrightdao;
	}
	
	public Boolean doSave(RoleColumnRightInfo rolecolumnrightinfo) {
		return rolecolumnrightdao.doSave(rolecolumnrightinfo);
	}
	
	public Boolean doDelete(RoleColumnRightInfo rolecolumnrightinfo) {
		return rolecolumnrightdao.doDelete(rolecolumnrightinfo);
	}

	public Boolean doUpdate(RoleColumnRightInfo rolecolumnrightinfo) {
		return rolecolumnrightdao.doUpdate(rolecolumnrightinfo);
	}
	
	public List<RoleColumnRightInfo> getList(RoleColumnRightInfo rolecolumnrightinfo, PageResult presult) {
		return rolecolumnrightdao.getList(rolecolumnrightinfo,presult);	
	}
	
	public List<Object> getSaveSql(RoleColumnRightInfo rolecolumnrightinfo, StringBuilder sqlbuilder) {
		return rolecolumnrightdao.getSaveSql(rolecolumnrightinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(RoleColumnRightInfo rolecolumnrightinfo, StringBuilder sqlbuilder) {
		return rolecolumnrightdao.getDeleteSql(rolecolumnrightinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(RoleColumnRightInfo rolecolumnrightinfo, StringBuilder sqlbuilder) {
		return rolecolumnrightdao.getUpdateSql(rolecolumnrightinfo,sqlbuilder);
	}
	public RoleColumnRightInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<RoleColumnRightInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return rolecolumnrightdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return rolecolumnrightdao.getNextId();
	}
}

