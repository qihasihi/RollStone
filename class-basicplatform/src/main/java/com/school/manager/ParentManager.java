
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IParentDAO;
import com.school.util.PageResult;
import com.school.entity.ParentInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IParentManager;

@Service
public class  ParentManager extends BaseManager<ParentInfo> implements IParentManager  {
	private IParentDAO parentdao ;
	 
	@Autowired
	@Qualifier("parentDAO")
	
	
	public void setparentdao(IParentDAO parentdao) {
		this.parentdao = parentdao;
	}

	@Override
	public Boolean doDelete(ParentInfo obj) {
		return this.parentdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(ParentInfo obj) {
		return this.parentdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(ParentInfo obj) {
		return this.parentdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<ParentInfo> getBaseDAO() {
		return parentdao;
	}

	@Override
	public List<ParentInfo> getList(ParentInfo obj, PageResult presult) {
		return this.parentdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.parentdao.getNextId();
	}

	public List<Object> getDeleteSql(ParentInfo obj, StringBuilder sqlbuilder) {
		return this.parentdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(ParentInfo obj, StringBuilder sqlbuilder) { 
		return this.parentdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(ParentInfo obj, StringBuilder sqlbuilder) {
		return this.parentdao.getUpdateSql(obj, sqlbuilder);  
	}

	public ParentInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	} 
	 
	
	
}

