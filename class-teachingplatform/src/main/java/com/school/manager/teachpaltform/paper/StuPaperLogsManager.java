
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IStuPaperLogsDAO;

import com.school.entity.teachpaltform.paper.StuPaperLogs;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IStuPaperLogsManager;
import com.school.util.PageResult;

@Service
public class  StuPaperLogsManager extends BaseManager<StuPaperLogs> implements IStuPaperLogsManager  {
	
	private IStuPaperLogsDAO stupaperlogsdao;
	
	@Autowired
	@Qualifier("stuPaperLogsDAO")
	public void setStupaperlogsdao(IStuPaperLogsDAO stupaperlogsdao) {
		this.stupaperlogsdao = stupaperlogsdao;
	}
	
	public Boolean doSave(StuPaperLogs stupaperlogs) {
		return this.stupaperlogsdao.doSave(stupaperlogs);
	}
	
	public Boolean doDelete(StuPaperLogs stupaperlogs) {
		return this.stupaperlogsdao.doDelete(stupaperlogs);
	}

	public Boolean doUpdate(StuPaperLogs stupaperlogs) {
		return this.stupaperlogsdao.doUpdate(stupaperlogs);
	}
	
	public List<StuPaperLogs> getList(StuPaperLogs stupaperlogs, PageResult presult) {
		return this.stupaperlogsdao.getList(stupaperlogs,presult);	
	}
	
	public List<Object> getSaveSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
		return this.stupaperlogsdao.getSaveSql(stupaperlogs,sqlbuilder);
	}

	public List<Object> getDeleteSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
		return this.stupaperlogsdao.getDeleteSql(stupaperlogs,sqlbuilder);
	}

	public List<Object> getUpdateSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
		return this.stupaperlogsdao.getUpdateSql(stupaperlogs,sqlbuilder);
	}

//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		return this.stupaperlogsdao.doExcetueArrayProc(sqlArrayList,objArrayList);
//	}
//
	public StuPaperLogs getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<StuPaperLogs> getBaseDAO() {
		// TODO Auto-generated method stub
		return stupaperlogsdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<StuPaperLogs> getMarkingLogs(Long paperid, Long quesid) {
        return this.stupaperlogsdao.getMarkingLogs(paperid,quesid);
    }

    @Override
    public List<Map<String, Object>> getMarkingDetail(Long paperid, Long questionid,Long quesid) {
        return this.stupaperlogsdao.getMarkingDetail(paperid,questionid,quesid);
    }

    @Override
    public List<Map<String, Object>> getMarkingNum(Long paperid, Long quesid) {
        return this.stupaperlogsdao.getMarkingNum(paperid,quesid);
    }

    @Override
    public List<Object> getUpdateScoreSql(StuPaperLogs stupaperlogs, StringBuilder sqlbuilder) {
        return this.stupaperlogsdao.getUpdateScoreSql(stupaperlogs,sqlbuilder);
    }

    @Override
    public Boolean doUpdateScore(StuPaperLogs stupaperlogs) {
        return this.stupaperlogsdao.doUpdate(stupaperlogs);
    }
    /**
     * 根据学生ID,试卷ID,得到学生得分
     * @param userid
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getStuPaperSumScore(Integer userid,Long paperid){
        return this.stupaperlogsdao.getStuPaperSumScore(userid,paperid);
    }
}


