
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITeachVersionDAO;

import com.school.entity.teachpaltform.TeachVersionInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITeachVersionManager;
import com.school.util.PageResult;

@Service
public class  TeachVersionManager extends BaseManager<TeachVersionInfo> implements ITeachVersionManager  { 
	
	private ITeachVersionDAO teachversiondao;
	
	@Autowired
	@Qualifier("teachVersionDAO")
	public void setTeachversiondao(ITeachVersionDAO teachversiondao) {
		this.teachversiondao = teachversiondao;
	}
	
	public Boolean doSave(TeachVersionInfo teachversioninfo) {
		return this.teachversiondao.doSave(teachversioninfo);
	}
	
	public Boolean doDelete(TeachVersionInfo teachversioninfo) {
		return this.teachversiondao.doDelete(teachversioninfo);
	}

	public Boolean doUpdate(TeachVersionInfo teachversioninfo) {
		return this.teachversiondao.doUpdate(teachversioninfo);
	}
	
	public List<TeachVersionInfo> getList(TeachVersionInfo teachversioninfo, PageResult presult) {
		return this.teachversiondao.getList(teachversioninfo,presult);	
	}
	
	public List<Object> getSaveSql(TeachVersionInfo teachversioninfo, StringBuilder sqlbuilder) {
		return this.teachversiondao.getSaveSql(teachversioninfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TeachVersionInfo teachversioninfo, StringBuilder sqlbuilder) {
		return this.teachversiondao.getDeleteSql(teachversioninfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TeachVersionInfo teachversioninfo, StringBuilder sqlbuilder) {
		return this.teachversiondao.getUpdateSql(teachversioninfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.teachversiondao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TeachVersionInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TeachVersionInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return teachversiondao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<Object> getSynchroSql(TeachVersionInfo entity, StringBuilder sqlbuilder) {
        return teachversiondao.getSynchroSql(entity,sqlbuilder);
    }
}

