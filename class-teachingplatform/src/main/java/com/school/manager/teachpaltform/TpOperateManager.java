
package  com.school.manager.teachpaltform;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpOperateDAO;

import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpOperateManager;
import com.school.util.PageResult;

@Service
public class  TpOperateManager extends BaseManager<TpOperateInfo> implements ITpOperateManager  { 
	
	private ITpOperateDAO tpoperatedao;
	
	@Autowired
	@Qualifier("tpOperateDAO")
	public void setTpoperatedao(ITpOperateDAO tpoperatedao) {
		this.tpoperatedao = tpoperatedao;
	}
	
	public Boolean doSave(TpOperateInfo tpoperateinfo) {
		return this.tpoperatedao.doSave(tpoperateinfo);
	}
	
	public Boolean doDelete(TpOperateInfo tpoperateinfo) {
		return this.tpoperatedao.doDelete(tpoperateinfo);
	}

	public Boolean doUpdate(TpOperateInfo tpoperateinfo) {
		return this.tpoperatedao.doUpdate(tpoperateinfo);
	}
	
	public List<TpOperateInfo> getList(TpOperateInfo tpoperateinfo, PageResult presult) {
		return this.tpoperatedao.getList(tpoperateinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpOperateInfo tpoperateinfo, StringBuilder sqlbuilder) {
		return this.tpoperatedao.getSaveSql(tpoperateinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpOperateInfo tpoperateinfo, StringBuilder sqlbuilder) {
		return this.tpoperatedao.getDeleteSql(tpoperateinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpOperateInfo tpoperateinfo, StringBuilder sqlbuilder) {
		return this.tpoperatedao.getUpdateSql(tpoperateinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpoperatedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpOperateInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 得到本次需要更新的操作记录
     * @param ftime  上次更新的时间
     * @param presult 分页
     * @return
     */
    public List<TpOperateInfo> getSynchroList(String ftime,PageResult presult){
        return this.tpoperatedao.getSynchroList(ftime,presult);
    }

	@Override
	protected ICommonDAO<TpOperateInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpoperatedao;
	}

}

