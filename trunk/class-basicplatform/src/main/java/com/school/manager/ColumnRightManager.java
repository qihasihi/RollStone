
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IColumnRightDAO;

import com.school.entity.ColumnRightInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IColumnRightManager;
import com.school.util.PageResult;

@Service
public class  ColumnRightManager extends BaseManager<ColumnRightInfo> implements IColumnRightManager  { 
	
	private IColumnRightDAO columnrightdao;
	
	@Autowired
	@Qualifier("columnRightDAO")
	public void setColumnrightdao(IColumnRightDAO columnrightdao) {
		this.columnrightdao = columnrightdao;
	}
	
	public Boolean doSave(ColumnRightInfo columnrightinfo) {
		return columnrightdao.doSave(columnrightinfo);
	}
	
	public Boolean doDelete(ColumnRightInfo columnrightinfo) {
		return columnrightdao.doDelete(columnrightinfo);
	}

	public Boolean doUpdate(ColumnRightInfo columnrightinfo) {
		return columnrightdao.doUpdate(columnrightinfo);
	}
	
	public List<ColumnRightInfo> getList(ColumnRightInfo columnrightinfo, PageResult presult) {
		return columnrightdao.getList(columnrightinfo,presult);	
	}
	
	public List<Object> getSaveSql(ColumnRightInfo columnrightinfo, StringBuilder sqlbuilder) {
		return columnrightdao.getSaveSql(columnrightinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ColumnRightInfo columnrightinfo, StringBuilder sqlbuilder) {
		return columnrightdao.getDeleteSql(columnrightinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ColumnRightInfo columnrightinfo, StringBuilder sqlbuilder) {
		return columnrightdao.getUpdateSql(columnrightinfo,sqlbuilder);
	}
	public ColumnRightInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ColumnRightInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return columnrightdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return columnrightdao.getNextId();
	}
}

