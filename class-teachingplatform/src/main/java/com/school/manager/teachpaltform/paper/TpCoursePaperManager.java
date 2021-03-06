
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.ITpCoursePaperDAO;

import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.ITpCoursePaperManager;
import com.school.util.PageResult;

@Service
public class  TpCoursePaperManager extends BaseManager<TpCoursePaper> implements ITpCoursePaperManager  { 
	
	private ITpCoursePaperDAO tpcoursepaperdao;
	
	@Autowired
	@Qualifier("tpCoursePaperDAO")
	public void setTpcoursepaperdao(ITpCoursePaperDAO tpcoursepaperdao) {
		this.tpcoursepaperdao = tpcoursepaperdao;
	}
	
	public Boolean doSave(TpCoursePaper tpcoursepaper) {
		return this.tpcoursepaperdao.doSave(tpcoursepaper);
	}
	
	public Boolean doDelete(TpCoursePaper tpcoursepaper) {
		return this.tpcoursepaperdao.doDelete(tpcoursepaper);
	}

	public Boolean doUpdate(TpCoursePaper tpcoursepaper) {
		return this.tpcoursepaperdao.doUpdate(tpcoursepaper);
	}
	
	public List<TpCoursePaper> getList(TpCoursePaper tpcoursepaper, PageResult presult) {
		return this.tpcoursepaperdao.getList(tpcoursepaper,presult);	
	}
	
	public List<Object> getSaveSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		return this.tpcoursepaperdao.getSaveSql(tpcoursepaper,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		return this.tpcoursepaperdao.getDeleteSql(tpcoursepaper,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpCoursePaper tpcoursepaper, StringBuilder sqlbuilder) {
		return this.tpcoursepaperdao.getUpdateSql(tpcoursepaper,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpcoursepaperdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpCoursePaper getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 查询是否存在AB卷
     * @param tpCoursePaper
     * @param presult
     * @return
     */
    public List<TpCoursePaper> getABSynchroList(TpCoursePaper tpCoursePaper,PageResult presult){
        return this.tpcoursepaperdao.getABSynchroList(tpCoursePaper,presult);
    }

    /**
     * 获取添加视频关联试卷
     * 过滤掉试题组
     * @param tpcoursepaper
     * @param presult
     * @return
     */
    @Override
    public List<TpCoursePaper> getSelRelatePaPerList(TpCoursePaper tpcoursepaper, PageResult presult) {
        return this.tpcoursepaperdao.getSelRelatePaPerList(tpcoursepaper,presult);
    }

    @Override
	protected ICommonDAO<TpCoursePaper> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpcoursepaperdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public List<TpCoursePaper> getRelateCoursePaPerList(TpCoursePaper tpcoursepaper, PageResult presult) {
        return this.tpcoursepaperdao.getRelateCoursePaPerList(tpcoursepaper,presult);
    }
}

