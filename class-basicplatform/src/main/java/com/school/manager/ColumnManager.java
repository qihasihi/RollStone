
package  com.school.manager;

import java.util.List;

import com.school.entity.EttColumnInfo;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IColumnDAO;

import com.school.entity.ColumnInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IColumnManager;
import com.school.util.PageResult;

@Service
public class  ColumnManager extends BaseManager<ColumnInfo> implements IColumnManager  { 
	
	private IColumnDAO columndao;
	
	@Autowired
	@Qualifier("columnDAO")
	public void setColumndao(IColumnDAO columndao) {
		this.columndao = columndao;
	}
	
	public Boolean doSave(ColumnInfo columninfo) {
		return columndao.doSave(columninfo);
	}
	
	public Boolean doDelete(ColumnInfo columninfo) {
		return columndao.doDelete(columninfo);
	}

	public Boolean doUpdate(ColumnInfo columninfo) {
		return columndao.doUpdate(columninfo);
	}
	
	public List<ColumnInfo> getList(ColumnInfo columninfo, PageResult presult) {
		return columndao.getList(columninfo,presult);	
	}
	
	public List<Object> getSaveSql(ColumnInfo columninfo, StringBuilder sqlbuilder) {
		return columndao.getSaveSql(columninfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(ColumnInfo columninfo, StringBuilder sqlbuilder) {
		return columndao.getDeleteSql(columninfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(ColumnInfo columninfo, StringBuilder sqlbuilder) {
		return columndao.getUpdateSql(columninfo,sqlbuilder);
	}
	public ColumnInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<ColumnInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return columndao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return columndao.getNextId();
	}	
	/**
	 * 得到用户的栏目访问权限
	 * @param userref
	 * @return
	 */
	public List<ColumnInfo> getUserColumnList(String userref){
		return columndao.getUserColumnList(userref);
	}
    /**
     * 同步ETT栏目信息
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getEttColumnSynchro(final EttColumnInfo entity,StringBuilder sqlbuilder){
        return columndao.getEttColumnSynchro(entity,sqlbuilder);
    }
    /**
     * 查询ETT栏目信息
     * @param entity
     * @param presult
     * @return
     */
    public List<EttColumnInfo> getEttColumnSplit(final EttColumnInfo entity,PageResult presult){
        return columndao.getEttColumnSplit(entity,presult);
    }
    public List<Object> getEttDeleteSql(final EttColumnInfo entity, StringBuilder sqlbuilder){
        return columndao.getEttDeleteSql(entity,sqlbuilder);
    }
}

