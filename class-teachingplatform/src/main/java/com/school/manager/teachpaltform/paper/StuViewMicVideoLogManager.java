
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IStuViewMicVideoLogDAO;

import com.school.entity.teachpaltform.paper.StuViewMicVideoLog;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IStuViewMicVideoLogManager;
import com.school.util.PageResult;

@Service
public class  StuViewMicVideoLogManager extends BaseManager<StuViewMicVideoLog> implements IStuViewMicVideoLogManager  { 
	
	private IStuViewMicVideoLogDAO stuviewmicvideologdao;
	
	@Autowired
	@Qualifier("stuViewMicVideoLogDAO")
	public void setStuviewmicvideologdao(IStuViewMicVideoLogDAO stuviewmicvideologdao) {
		this.stuviewmicvideologdao = stuviewmicvideologdao;
	}
	
	public Boolean doSave(StuViewMicVideoLog stuviewmicvideolog) {
		return this.stuviewmicvideologdao.doSave(stuviewmicvideolog);
	}
	
	public Boolean doDelete(StuViewMicVideoLog stuviewmicvideolog) {
		return this.stuviewmicvideologdao.doDelete(stuviewmicvideolog);
	}

	public Boolean doUpdate(StuViewMicVideoLog stuviewmicvideolog) {
		return this.stuviewmicvideologdao.doUpdate(stuviewmicvideolog);
	}
	
	public List<StuViewMicVideoLog> getList(StuViewMicVideoLog stuviewmicvideolog, PageResult presult) {
		return this.stuviewmicvideologdao.getList(stuviewmicvideolog,presult);	
	}
	
	public List<Object> getSaveSql(StuViewMicVideoLog stuviewmicvideolog, StringBuilder sqlbuilder) {
		return this.stuviewmicvideologdao.getSaveSql(stuviewmicvideolog,sqlbuilder);
	}

	public List<Object> getDeleteSql(StuViewMicVideoLog stuviewmicvideolog, StringBuilder sqlbuilder) {
		return this.stuviewmicvideologdao.getDeleteSql(stuviewmicvideolog,sqlbuilder);
	}

	public List<Object> getUpdateSql(StuViewMicVideoLog stuviewmicvideolog, StringBuilder sqlbuilder) {
		return this.stuviewmicvideologdao.getUpdateSql(stuviewmicvideolog,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.stuviewmicvideologdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public StuViewMicVideoLog getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<StuViewMicVideoLog> getBaseDAO() {
		// TODO Auto-generated method stub
		return stuviewmicvideologdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

