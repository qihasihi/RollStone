
package  com.school.manager;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.ITermDAO;

import com.school.entity.TermInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ITermManager;
import com.school.util.PageResult;

@Service
public class  TermManager extends BaseManager<TermInfo> implements ITermManager  { 
	
	private ITermDAO termdao;
	
	@Autowired
	@Qualifier("termDAO")
	public void setTermdao(ITermDAO termdao) {
		this.termdao = termdao;
	}
	
	public Boolean doSave(TermInfo terminfo) {
		return this.termdao.doSave(terminfo);
	}
	
	public Boolean doDelete(TermInfo terminfo) {
		return this.termdao.doDelete(terminfo);
	}

	public Boolean doUpdate(TermInfo terminfo) {
		return this.termdao.doUpdate(terminfo);
	}
	
	public List<TermInfo> getList(TermInfo terminfo, PageResult presult) {
		return this.termdao.getList(terminfo,presult);	
	}
	
	public List<Object> getSaveSql(TermInfo terminfo, StringBuilder sqlbuilder) {
		return this.termdao.getSaveSql(terminfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TermInfo terminfo, StringBuilder sqlbuilder) {
		return this.termdao.getDeleteSql(terminfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TermInfo terminfo, StringBuilder sqlbuilder) {
		return this.termdao.getUpdateSql(terminfo,sqlbuilder);
	}

	
	
	public TermInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TermInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return termdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TermInfo> getAvailableTermList() {
		// TODO Auto-generated method stub
		return this.termdao.getAvailableTermList();
	}

	public TermInfo getMaxIdTerm() {
		// TODO Auto-generated method stub
		return this.getMaxIdTerm(false);
	}

	public TermInfo getMaxIdTerm(Boolean flag) {		
		return this.termdao.getMaxIdTerm(flag);
	}
	public TermInfo getAutoTerm(){
		TermInfo termInfo=this.termdao.getMaxIdTerm(false);
		if(termInfo==null)
			termInfo=this.termdao.getMaxIdTerm(true);
		return termInfo;
	}

	public List<Map<String, Object>> getYearTerm() {
		// TODO Auto-generated method stub
		return this.termdao.getYearTerm();
	}

    @Override
    public Boolean InitTerm() {
        return this.termdao.InitTerm();
    }
}

