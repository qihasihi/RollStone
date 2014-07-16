
package  com.school.manager;

import java.util.List;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IGradeDAO;
import com.school.util.PageResult;
import com.school.entity.GradeInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IGradeManager;

@Service
public class  GradeManager extends BaseManager<GradeInfo> implements IGradeManager  {
	private IGradeDAO gradedao ;
	 
	@Autowired
	@Qualifier("gradeDAO")
	
	
	public void setGradedao(IGradeDAO gradedao) {
		this.gradedao = gradedao;
	}

	@Override
	public Boolean doDelete(GradeInfo obj) {
		return this.gradedao.doDelete(obj);
	}

	@Override
	public Boolean doSave(GradeInfo obj) {
		return this.gradedao.doSave(obj);
	}

	@Override
	public Boolean doUpdate(GradeInfo obj) {
		return this.gradedao.doUpdate(obj);
	}

	@Override
	protected ICommonDAO<GradeInfo> getBaseDAO() {
		return gradedao;
	}

	@Override
	public List<GradeInfo> getList(GradeInfo obj, PageResult presult) {
		return this.gradedao.getList(obj, presult);
	}


	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.gradedao.getNextId();
	}

	public List<Object> getDeleteSql(GradeInfo obj, StringBuilder sqlbuilder) {
		return this.gradedao.getDeleteSql(obj, sqlbuilder);
	}

	public List<Object> getSaveSql(GradeInfo obj, StringBuilder sqlbuilder) { 
		return this.gradedao.getSaveSql(obj, sqlbuilder);
	} 

	public List<Object> getUpdateSql(GradeInfo obj, StringBuilder sqlbuilder) {
		return this.gradedao.getUpdateSql(obj, sqlbuilder);  
	}

	public GradeInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

    public List<GradeInfo> getTchGradeList(Integer userid, String year) {
        return this.gradedao.getTchGradeList(userid,year);
    }
}

