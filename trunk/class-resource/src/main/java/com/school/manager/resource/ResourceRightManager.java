
package  com.school.manager.resource;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.inter.resource.IResourceRightDAO;

import com.school.entity.resource.ResourceRightInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IResourceRightManager;
import com.school.util.PageResult;

@Service
public class  ResourceRightManager extends BaseManager<ResourceRightInfo> implements IResourceRightManager  { 
	
	private IResourceRightDAO resourceRightDAO;
	
	@Autowired
	@Qualifier("resourceRightDAO")
	public void setResourcerightdao(IResourceRightDAO resourcerightdao) {
		this.resourceRightDAO = resourcerightdao;
	}
	
	public Boolean doSave(ResourceRightInfo resourcerightinfo) {
		return resourceRightDAO.doSave(resourcerightinfo);
	}
	
	public Boolean doDelete(ResourceRightInfo resourcerightinfo) {
		return resourceRightDAO.doDelete(resourcerightinfo);
	}

	public Boolean doUpdate(ResourceRightInfo resourcerightinfo) {
		return resourceRightDAO.doUpdate(resourcerightinfo);
	}
	
	public List<ResourceRightInfo> getList(ResourceRightInfo resourcerightinfo, PageResult presult) {
		return resourceRightDAO.getList(resourcerightinfo,presult);	
	}
	
	public List<Object> getSaveSql(ResourceRightInfo resourcerightinfo, StringBuilder sqlbuilder) {
		return resourceRightDAO.getSaveSql(resourcerightinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ResourceRightInfo resourcerightinfo, StringBuilder sqlbuilder) {
		return resourceRightDAO.getDeleteSql(resourcerightinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ResourceRightInfo resourcerightinfo, StringBuilder sqlbuilder) {
		return resourceRightDAO.getUpdateSql(resourcerightinfo,sqlbuilder);
	}
	public ResourceRightInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ResourceRightInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return resourceRightDAO;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return resourceRightDAO.getNextId();
	}
}

