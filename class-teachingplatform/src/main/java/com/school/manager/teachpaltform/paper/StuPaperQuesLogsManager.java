
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IStuPaperQuesLogsDAO;

import com.school.entity.teachpaltform.paper.StuPaperQuesLogs;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IStuPaperQuesLogsManager;
import com.school.util.PageResult;

@Service
public class  StuPaperQuesLogsManager extends BaseManager<StuPaperQuesLogs> implements IStuPaperQuesLogsManager  { 
	
	private IStuPaperQuesLogsDAO stupaperqueslogsdao;
	
	@Autowired
	@Qualifier("stuPaperQuesLogsDAO")
	public void setStupaperqueslogsdao(IStuPaperQuesLogsDAO stupaperqueslogsdao) {
		this.stupaperqueslogsdao = stupaperqueslogsdao;
	}
	
	public Boolean doSave(StuPaperQuesLogs stupaperqueslogs) {
		return this.stupaperqueslogsdao.doSave(stupaperqueslogs);
	}
	
	public Boolean doDelete(StuPaperQuesLogs stupaperqueslogs) {
		return this.stupaperqueslogsdao.doDelete(stupaperqueslogs);
	}

	public Boolean doUpdate(StuPaperQuesLogs stupaperqueslogs) {
		return this.stupaperqueslogsdao.doUpdate(stupaperqueslogs);
	}
	
	public List<StuPaperQuesLogs> getList(StuPaperQuesLogs stupaperqueslogs, PageResult presult) {
		return this.stupaperqueslogsdao.getList(stupaperqueslogs,presult);	
	}
	
	public List<Object> getSaveSql(StuPaperQuesLogs stupaperqueslogs, StringBuilder sqlbuilder) {
		return this.stupaperqueslogsdao.getSaveSql(stupaperqueslogs,sqlbuilder);
	}

	public List<Object> getDeleteSql(StuPaperQuesLogs stupaperqueslogs, StringBuilder sqlbuilder) {
		return this.stupaperqueslogsdao.getDeleteSql(stupaperqueslogs,sqlbuilder);
	}

	public List<Object> getUpdateSql(StuPaperQuesLogs stupaperqueslogs, StringBuilder sqlbuilder) {
		return this.stupaperqueslogsdao.getUpdateSql(stupaperqueslogs,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.stupaperqueslogsdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public StuPaperQuesLogs getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<StuPaperQuesLogs> getBaseDAO() {
		// TODO Auto-generated method stub
		return stupaperqueslogsdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

