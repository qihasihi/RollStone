
package  com.school.manager;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.IMyInfoTemplateDAO;

import com.school.entity.MyInfoTemplateInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IMyInfoTemplateManager;
import com.school.util.PageResult;

@Service
public class  MyInfoTemplateManager extends BaseManager<MyInfoTemplateInfo> implements IMyInfoTemplateManager  { 
	
	private IMyInfoTemplateDAO myinfotemplatedao;
	
	@Autowired
	@Qualifier("myInfoTemplateDAO")
	public void setMyinfotemplatedao(IMyInfoTemplateDAO myinfotemplatedao) {
		this.myinfotemplatedao = myinfotemplatedao;
	}
	
	public Boolean doSave(MyInfoTemplateInfo myinfotemplateinfo) {
		return myinfotemplatedao.doSave(myinfotemplateinfo);
	}
	
	public Boolean doDelete(MyInfoTemplateInfo myinfotemplateinfo) {
		return myinfotemplatedao.doDelete(myinfotemplateinfo);
	}

	public Boolean doUpdate(MyInfoTemplateInfo myinfotemplateinfo) {
		return myinfotemplatedao.doUpdate(myinfotemplateinfo);
	}
	
	public List<MyInfoTemplateInfo> getList(MyInfoTemplateInfo myinfotemplateinfo, PageResult presult) {
		return myinfotemplatedao.getList(myinfotemplateinfo,presult);	
	}
	
	public List<Object> getSaveSql(MyInfoTemplateInfo myinfotemplateinfo, StringBuilder sqlbuilder) {
		return myinfotemplatedao.getSaveSql(myinfotemplateinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(MyInfoTemplateInfo myinfotemplateinfo, StringBuilder sqlbuilder) {
		return myinfotemplatedao.getDeleteSql(myinfotemplateinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(MyInfoTemplateInfo myinfotemplateinfo, StringBuilder sqlbuilder) {
		return myinfotemplatedao.getUpdateSql(myinfotemplateinfo,sqlbuilder);
	}
	

	@Override
	protected ICommonDAO<MyInfoTemplateInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return myinfotemplatedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return myinfotemplatedao.getNextId();
	}

	public MyInfoTemplateInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	
}

