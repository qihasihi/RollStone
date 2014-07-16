
package  com.school.manager.resource;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IResourceKeywordDAO;

import com.school.entity.resource.ResourceKeyword;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IResourceKeywordManager;
import com.school.util.PageResult;

@Service
public class  ResourceKeywordManager extends BaseManager<ResourceKeyword> implements IResourceKeywordManager  { 
	
	private IResourceKeywordDAO resourcekeyworddao;
	
	@Autowired
	@Qualifier("resourceKeywordDAO")
	public void setResourcekeyworddao(IResourceKeywordDAO resourcekeyworddao) {
		this.resourcekeyworddao = resourcekeyworddao;
	}
	
	public Boolean doSave(ResourceKeyword resourcekeyword) {
		return this.resourcekeyworddao.doSave(resourcekeyword);
	}
	
	public Boolean doDelete(ResourceKeyword resourcekeyword) {
		return this.resourcekeyworddao.doDelete(resourcekeyword);
	}

	public Boolean doUpdate(ResourceKeyword resourcekeyword) {
		return this.resourcekeyworddao.doUpdate(resourcekeyword);
	}
	
	public List<ResourceKeyword> getList(ResourceKeyword resourcekeyword, PageResult presult) {
		return this.resourcekeyworddao.getList(resourcekeyword,presult);	
	}
	
	public List<Object> getSaveSql(ResourceKeyword resourcekeyword, StringBuilder sqlbuilder) {
		return this.resourcekeyworddao.getSaveSql(resourcekeyword,sqlbuilder);
	}

	public List<Object> getDeleteSql(ResourceKeyword resourcekeyword, StringBuilder sqlbuilder) {
		return this.resourcekeyworddao.getDeleteSql(resourcekeyword,sqlbuilder);
	}

	public List<Object> getUpdateSql(ResourceKeyword resourcekeyword, StringBuilder sqlbuilder) {
		return this.resourcekeyworddao.getUpdateSql(resourcekeyword,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.resourcekeyworddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ResourceKeyword getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ResourceKeyword> getBaseDAO() {
		// TODO Auto-generated method stub
		return resourcekeyworddao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

