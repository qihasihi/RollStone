
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IColumnRightPageRightDAO;

import com.school.entity.ColumnRightPageRightInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IColumnRightPageRightManager;
import com.school.util.PageResult;

@Service
public class  ColumnRightPageRightManager extends BaseManager<ColumnRightPageRightInfo> implements IColumnRightPageRightManager  { 
	
	private IColumnRightPageRightDAO columnrightpagerightdao;
	
	@Autowired
	@Qualifier("columnRightPageRightDAO")
	public void setColumnrightpagerightdao(IColumnRightPageRightDAO columnrightpagerightdao) {
		this.columnrightpagerightdao = columnrightpagerightdao;
	}
	
	public Boolean doSave(ColumnRightPageRightInfo columnrightpagerightinfo) {
		return columnrightpagerightdao.doSave(columnrightpagerightinfo);
	}
	
	public Boolean doDelete(ColumnRightPageRightInfo columnrightpagerightinfo) {
		return columnrightpagerightdao.doDelete(columnrightpagerightinfo);
	}

	public Boolean doUpdate(ColumnRightPageRightInfo columnrightpagerightinfo) {
		return columnrightpagerightdao.doUpdate(columnrightpagerightinfo);
	}
	
	public List<ColumnRightPageRightInfo> getList(ColumnRightPageRightInfo columnrightpagerightinfo, PageResult presult) {
		return columnrightpagerightdao.getList(columnrightpagerightinfo,presult);	
	}
	
	public List<Object> getSaveSql(ColumnRightPageRightInfo columnrightpagerightinfo, StringBuilder sqlbuilder) {
		return columnrightpagerightdao.getSaveSql(columnrightpagerightinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ColumnRightPageRightInfo columnrightpagerightinfo, StringBuilder sqlbuilder) {
		return columnrightpagerightdao.getDeleteSql(columnrightpagerightinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ColumnRightPageRightInfo columnrightpagerightinfo, StringBuilder sqlbuilder) {
		return columnrightpagerightdao.getUpdateSql(columnrightpagerightinfo,sqlbuilder);
	}
	public ColumnRightPageRightInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ColumnRightPageRightInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return columnrightpagerightdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return columnrightpagerightdao.getNextId();
	}
}

