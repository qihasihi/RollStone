
package  com.school.manager.teachpaltform.interactive;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.interactive.ITpThemeReplyDAO;
import com.school.entity.teachpaltform.interactive.TpThemeReplyInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.interactive.ITpThemeReplyManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TpThemeReplyManager extends BaseManager<TpThemeReplyInfo> implements ITpThemeReplyManager  { 
	
	private ITpThemeReplyDAO tpThemeReplyDAO;
	
	@Autowired
	@Qualifier("tpThemeReplyDAO")
	public void setTpthemereplydao(ITpThemeReplyDAO tpthemereplydao) {
		this.tpThemeReplyDAO = tpthemereplydao;
	}
	
	public Boolean doSave(TpThemeReplyInfo tpthemereplyinfo) {
		return tpThemeReplyDAO.doSave(tpthemereplyinfo);
	}
	
	public Boolean doDelete(TpThemeReplyInfo tpthemereplyinfo) {
		return tpThemeReplyDAO.doDelete(tpthemereplyinfo);
	}

	public Boolean doUpdate(TpThemeReplyInfo tpthemereplyinfo) {
		return tpThemeReplyDAO.doUpdate(tpthemereplyinfo);
	}
	
	public List<TpThemeReplyInfo> getList(TpThemeReplyInfo tpthemereplyinfo, PageResult presult) {
		return tpThemeReplyDAO.getList(tpthemereplyinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpThemeReplyInfo tpthemereplyinfo, StringBuilder sqlbuilder) {
		return tpThemeReplyDAO.getSaveSql(tpthemereplyinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpThemeReplyInfo tpthemereplyinfo, StringBuilder sqlbuilder) {
		return tpThemeReplyDAO.getDeleteSql(tpthemereplyinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpThemeReplyInfo tpthemereplyinfo, StringBuilder sqlbuilder) {
		return tpThemeReplyDAO.getUpdateSql(tpthemereplyinfo,sqlbuilder);
	}
    public List<TpThemeReplyInfo> getListByThemeIdStr(final String themeidStr,final Integer searchType, PageResult presult){
        return tpThemeReplyDAO.getListByThemeIdStr(themeidStr,searchType,presult);
    }
	public TpThemeReplyInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpThemeReplyInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpThemeReplyDAO;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return tpThemeReplyDAO.getNextId();
	}
}

