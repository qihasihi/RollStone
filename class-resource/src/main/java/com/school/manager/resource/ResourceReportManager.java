
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IResourceReportDAO;

import com.school.entity.resource.ResourceReport;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IResourceReportManager;
import com.school.util.PageResult;

@Service
public class  ResourceReportManager extends BaseManager<ResourceReport> implements IResourceReportManager  { 
	
	private IResourceReportDAO resourcereportdao;
	
	@Autowired
	// @Qualifier("resourcereportDAO")
	public void setResourcereportdao(IResourceReportDAO resourcereportdao) {
		this.resourcereportdao = resourcereportdao;
	}
	
	public Boolean doSave(ResourceReport resourcereport) {
		return resourcereportdao.doSave(resourcereport);
	}
	
	public Boolean doDelete(ResourceReport resourcereport) {
		return resourcereportdao.doDelete(resourcereport);
	}

	public Boolean doUpdate(ResourceReport resourcereport) {
		return resourcereportdao.doUpdate(resourcereport);
	}
	
	public List<ResourceReport> getList(ResourceReport resourcereport, PageResult presult) {
		return resourcereportdao.getList(resourcereport,presult);	
	}
	
	public List<Object> getSaveSql(ResourceReport resourcereport, StringBuilder sqlbuilder) {
		return resourcereportdao.getSaveSql(resourcereport,sqlbuilder);
	}

	public List<Object> getDeleteSql(ResourceReport resourcereport, StringBuilder sqlbuilder) {
		return resourcereportdao.getDeleteSql(resourcereport,sqlbuilder);
	}

	public List<Object> getUpdateSql(ResourceReport resourcereport, StringBuilder sqlbuilder) {
		return resourcereportdao.getUpdateSql(resourcereport,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return resourcereportdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ResourceReport getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ResourceReport> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

