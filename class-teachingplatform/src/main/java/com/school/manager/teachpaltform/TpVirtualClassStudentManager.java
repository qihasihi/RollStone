
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpVirtualClassStudentDAO;

import com.school.entity.teachpaltform.TpVirtualClassStudent;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassStudentManager;
import com.school.util.PageResult;

@Service
public class  TpVirtualClassStudentManager extends BaseManager<TpVirtualClassStudent> implements ITpVirtualClassStudentManager  { 
	
	private ITpVirtualClassStudentDAO tpvirtualclassstudentdao;
	
	@Autowired
	@Qualifier("tpVirtualClassStudentDAO")
	public void setTpvirtualclassstudentdao(ITpVirtualClassStudentDAO tpvirtualclassstudentdao) {
		this.tpvirtualclassstudentdao = tpvirtualclassstudentdao;
	}
	
	public Boolean doSave(TpVirtualClassStudent tpvirtualclassstudent) {
		return this.tpvirtualclassstudentdao.doSave(tpvirtualclassstudent);
	}
	
	public Boolean doDelete(TpVirtualClassStudent tpvirtualclassstudent) {
		return this.tpvirtualclassstudentdao.doDelete(tpvirtualclassstudent);
	}

	public Boolean doUpdate(TpVirtualClassStudent tpvirtualclassstudent) {
		return this.tpvirtualclassstudentdao.doUpdate(tpvirtualclassstudent);
	}
	
	public List<TpVirtualClassStudent> getList(TpVirtualClassStudent tpvirtualclassstudent, PageResult presult) {
		return this.tpvirtualclassstudentdao.getList(tpvirtualclassstudent,presult);	
	}
	
	public List<Object> getSaveSql(TpVirtualClassStudent tpvirtualclassstudent, StringBuilder sqlbuilder) {
		return this.tpvirtualclassstudentdao.getSaveSql(tpvirtualclassstudent,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpVirtualClassStudent tpvirtualclassstudent, StringBuilder sqlbuilder) {
		return this.tpvirtualclassstudentdao.getDeleteSql(tpvirtualclassstudent,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpVirtualClassStudent tpvirtualclassstudent, StringBuilder sqlbuilder) {
		return this.tpvirtualclassstudentdao.getUpdateSql(tpvirtualclassstudent,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpvirtualclassstudentdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpVirtualClassStudent getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpVirtualClassStudent> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpvirtualclassstudentdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<Map<String, Object>> getStudentList(String grade, Integer classid, String stuname,String year,Integer virclassid) {
        return tpvirtualclassstudentdao.getStudentList(grade,classid,stuname,year,virclassid);
    }
}

