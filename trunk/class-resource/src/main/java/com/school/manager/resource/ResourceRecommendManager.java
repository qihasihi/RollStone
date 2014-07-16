
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IResourceRecommendDAO;

import com.school.entity.resource.ResourceRecommend;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IResourceRecommendManager;
import com.school.util.PageResult;

@Service
public class  ResourceRecommendManager extends BaseManager<ResourceRecommend> implements IResourceRecommendManager  { 
	
	private IResourceRecommendDAO resourcerecommenddao;
	
	@Autowired
	@Qualifier("resourceRecommendDAO")
	public void setResourcerecommenddao(IResourceRecommendDAO resourcerecommenddao) {
		this.resourcerecommenddao = resourcerecommenddao;
	}
	
	public Boolean doSave(ResourceRecommend resourcerecommend) {
		return resourcerecommenddao.doSave(resourcerecommend);
	}
	
	public Boolean doDelete(ResourceRecommend resourcerecommend) {
		return resourcerecommenddao.doDelete(resourcerecommend);
	}

	public Boolean doUpdate(ResourceRecommend resourcerecommend) {
		return resourcerecommenddao.doUpdate(resourcerecommend);
	}
	
	public List<ResourceRecommend> getList(ResourceRecommend resourcerecommend, PageResult presult) {
		return resourcerecommenddao.getList(resourcerecommend,presult);	
	}
	
	public List<Object> getSaveSql(ResourceRecommend resourcerecommend, StringBuilder sqlbuilder) {
		return resourcerecommenddao.getSaveSql(resourcerecommend,sqlbuilder);
	}

	public List<Object> getDeleteSql(ResourceRecommend resourcerecommend, StringBuilder sqlbuilder) {
		return resourcerecommenddao.getDeleteSql(resourcerecommend,sqlbuilder);
	}

	public List<Object> getUpdateSql(ResourceRecommend resourcerecommend, StringBuilder sqlbuilder) {
		return resourcerecommenddao.getUpdateSql(resourcerecommend,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return resourcerecommenddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public ResourceRecommend getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ResourceRecommend> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

