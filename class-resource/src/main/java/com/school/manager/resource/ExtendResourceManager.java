
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IExtendResourceDAO;

import com.school.entity.resource.ExtendResource;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IExtendResourceManager;
import com.school.util.PageResult;

@Service
public class  ExtendResourceManager extends BaseManager<ExtendResource> implements IExtendResourceManager  { 
	
	private IExtendResourceDAO extendresourcedao;
	
	@Autowired
	@Qualifier("extendResourceDAO")
	public void setExtendresourcedao(IExtendResourceDAO extendresourcedao) {
		this.extendresourcedao = extendresourcedao;
	}
	
	public Boolean doSave(ExtendResource extendresource) {
		return extendresourcedao.doSave(extendresource);
	}
	
	public Boolean doDelete(ExtendResource extendresource) {
		return extendresourcedao.doDelete(extendresource);
	}

	public Boolean doUpdate(ExtendResource extendresource) {
		return extendresourcedao.doUpdate(extendresource);
	}
	
	public List<ExtendResource> getList(ExtendResource extendresource, PageResult presult) {
		return extendresourcedao.getList(extendresource,presult);	
	}
	
	public List<Object> getSaveSql(ExtendResource extendresource, StringBuilder sqlbuilder) {
		return extendresourcedao.getSaveSql(extendresource,sqlbuilder);
	}

	public List<Object> getDeleteSql(ExtendResource extendresource, StringBuilder sqlbuilder) {
		return extendresourcedao.getDeleteSql(extendresource,sqlbuilder);
	}

	public List<Object> getUpdateSql(ExtendResource extendresource, StringBuilder sqlbuilder) {
		return extendresourcedao.getUpdateSql(extendresource,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return extendresourcedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ExtendResource getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ExtendResource> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

