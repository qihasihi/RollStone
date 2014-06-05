
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IOperateRecordDAO;

import com.school.entity.resource.OperateRecord;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IOperateRecordManager;
import com.school.util.PageResult;

@Service
public class  OperateRecordManager extends BaseManager<OperateRecord> implements IOperateRecordManager  { 
	
	private IOperateRecordDAO operaterecorddao;
	
	@Autowired
	// @Qualifier("operaterecordDAO")
	public void setOperaterecorddao(IOperateRecordDAO operaterecorddao) {
		this.operaterecorddao = operaterecorddao;
	}
	
	public Boolean doSave(OperateRecord operaterecord) {
		return operaterecorddao.doSave(operaterecord);
	}
	
	public Boolean doDelete(OperateRecord operaterecord) {
		return operaterecorddao.doDelete(operaterecord);
	}

	public Boolean doUpdate(OperateRecord operaterecord) {
		return operaterecorddao.doUpdate(operaterecord);
	}
	
	public List<OperateRecord> getList(OperateRecord operaterecord, PageResult presult) {
		return operaterecorddao.getList(operaterecord,presult);	
	}
	
	public List<Object> getSaveSql(OperateRecord operaterecord, StringBuilder sqlbuilder) {
		return operaterecorddao.getSaveSql(operaterecord,sqlbuilder);
	}

	public List<Object> getDeleteSql(OperateRecord operaterecord, StringBuilder sqlbuilder) {
		return operaterecorddao.getDeleteSql(operaterecord,sqlbuilder);
	}

	public List<Object> getUpdateSql(OperateRecord operaterecord, StringBuilder sqlbuilder) {
		return operaterecorddao.getUpdateSql(operaterecord,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return operaterecorddao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public OperateRecord getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<OperateRecord> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

