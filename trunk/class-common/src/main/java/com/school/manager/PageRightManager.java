
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IPageRightDAO;
import com.school.util.PageResult;
import com.school.entity.PageRightInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IPageRightManager;

@Service
public class  PageRightManager extends BaseManager<PageRightInfo> implements IPageRightManager  {
	private IPageRightDAO pagerightdao ;
	 
	@Autowired
	@Qualifier("pageRightDAO")
	public void setRoledao(IPageRightDAO pagerightdao) {
		this.pagerightdao = pagerightdao;
	}

	@Override
	public Boolean doDelete(PageRightInfo obj) {
		return this.pagerightdao.doDelete(obj);
	}

	@Override
	public Boolean doSave(PageRightInfo obj) {
		return this.pagerightdao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(PageRightInfo obj) {
		return this.pagerightdao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<PageRightInfo> getBaseDAO() {
		return pagerightdao;
	}

	@Override
	public List<PageRightInfo> getList(PageRightInfo obj, PageResult presult) {
		return this.pagerightdao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.pagerightdao.getNextId();
	}

	public List<Object> getDeleteSql(PageRightInfo obj, StringBuilder sqlbuilder) {
		return this.pagerightdao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(PageRightInfo obj, StringBuilder sqlbuilder) { 
		return this.pagerightdao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(PageRightInfo obj, StringBuilder sqlbuilder) {
		return this.pagerightdao.getUpdateSql(obj, sqlbuilder);  
	}

	public PageRightInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	} 
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<PageRightInfo> getUserColumnList(String columnid,String userref){
		return this.pagerightdao.getUserColumnList(columnid, userref);
	}
	
	
	
}

