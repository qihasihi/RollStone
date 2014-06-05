
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IResourceFileDAO;

import com.school.entity.resource.ResourceFileInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IResourceFileManager;
import com.school.util.PageResult;

@Service
public class  ResourceFileManager extends BaseManager<ResourceFileInfo> implements IResourceFileManager  { 
	
	private IResourceFileDAO resourcefiledao;
	
	@Autowired
	@Qualifier("resourceFileDAO")
	public void setResourcefiledao(IResourceFileDAO resourcefiledao) {
		this.resourcefiledao = resourcefiledao;
	}
	
	public Boolean doSave(ResourceFileInfo resourcefileinfo) {
		return resourcefiledao.doSave(resourcefileinfo);
	}
	
	public Boolean doDelete(ResourceFileInfo resourcefileinfo) {
		return resourcefiledao.doDelete(resourcefileinfo);
	}

	public Boolean doUpdate(ResourceFileInfo resourcefileinfo) {
		return resourcefiledao.doUpdate(resourcefileinfo);
	}
	
	public List<ResourceFileInfo> getList(ResourceFileInfo resourcefileinfo, PageResult presult) {
		return resourcefiledao.getList(resourcefileinfo,presult);	
	}
	
	public List<Object> getSaveSql(ResourceFileInfo resourcefileinfo, StringBuilder sqlbuilder) {
		return resourcefiledao.getSaveSql(resourcefileinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ResourceFileInfo resourcefileinfo, StringBuilder sqlbuilder) {
		return resourcefiledao.getDeleteSql(resourcefileinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ResourceFileInfo resourcefileinfo, StringBuilder sqlbuilder) {
		return resourcefiledao.getUpdateSql(resourcefileinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return resourcefiledao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ResourceFileInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ResourceFileInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

