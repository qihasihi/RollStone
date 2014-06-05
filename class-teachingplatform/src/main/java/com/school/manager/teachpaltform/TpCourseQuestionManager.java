
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpCourseQuestionDAO;

import com.school.entity.teachpaltform.TpCourseQuestion;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpCourseQuestionManager;
import com.school.util.PageResult;

@Service
public class  TpCourseQuestionManager extends BaseManager<TpCourseQuestion> implements ITpCourseQuestionManager  { 
	
	private ITpCourseQuestionDAO tpcoursequestiondao;
	
	@Autowired
	@Qualifier("tpCourseQuestionDAO")
	public void setTpcoursequestiondao(ITpCourseQuestionDAO tpcoursequestiondao) {
		this.tpcoursequestiondao = tpcoursequestiondao;
	}
	
	public Boolean doSave(TpCourseQuestion tpcoursequestion) {
		return this.tpcoursequestiondao.doSave(tpcoursequestion);
	}
	
	public Boolean doDelete(TpCourseQuestion tpcoursequestion) {
		return this.tpcoursequestiondao.doDelete(tpcoursequestion);
	}

	public Boolean doUpdate(TpCourseQuestion tpcoursequestion) {
		return this.tpcoursequestiondao.doUpdate(tpcoursequestion);
	}
	
	public List<TpCourseQuestion> getList(TpCourseQuestion tpcoursequestion, PageResult presult) {
		return this.tpcoursequestiondao.getList(tpcoursequestion,presult);	
	}
	
	public List<Object> getSaveSql(TpCourseQuestion tpcoursequestion, StringBuilder sqlbuilder) {
		return this.tpcoursequestiondao.getSaveSql(tpcoursequestion,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpCourseQuestion tpcoursequestion, StringBuilder sqlbuilder) {
		return this.tpcoursequestiondao.getDeleteSql(tpcoursequestion,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpCourseQuestion tpcoursequestion, StringBuilder sqlbuilder) {
		return this.tpcoursequestiondao.getUpdateSql(tpcoursequestion,sqlbuilder);
	}
	public TpCourseQuestion getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}


    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseQuestion entity,StringBuilder sqlbuilder){
        return this.tpcoursequestiondao.getSynchroSql(entity,sqlbuilder);
    }

	@Override
	protected ICommonDAO<TpCourseQuestion> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpcoursequestiondao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

