
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITrusteeShipClassDAO;

import com.school.entity.teachpaltform.TrusteeShipClass;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITrusteeShipClassManager;
import com.school.util.PageResult;

@Service
public class  TrusteeShipClassManager extends BaseManager<TrusteeShipClass> implements ITrusteeShipClassManager  { 
	
	private ITrusteeShipClassDAO trusteeshipclassdao;
	
	@Autowired
	@Qualifier("trusteeShipClassDAO")
	public void setTrusteeshipclassdao(ITrusteeShipClassDAO trusteeshipclassdao) {
		this.trusteeshipclassdao = trusteeshipclassdao;
	}
	
	public Boolean doSave(TrusteeShipClass trusteeshipclass) {
		return this.trusteeshipclassdao.doSave(trusteeshipclass);
	}
	
	public Boolean doDelete(TrusteeShipClass trusteeshipclass) {
		return this.trusteeshipclassdao.doDelete(trusteeshipclass);
	}

	public Boolean doUpdate(TrusteeShipClass trusteeshipclass) {
		return this.trusteeshipclassdao.doUpdate(trusteeshipclass);
	}
	
	public List<TrusteeShipClass> getList(TrusteeShipClass trusteeshipclass, PageResult presult) {
		return this.trusteeshipclassdao.getList(trusteeshipclass,presult);	
	}
	
	public List<Object> getSaveSql(TrusteeShipClass trusteeshipclass, StringBuilder sqlbuilder) {
		return this.trusteeshipclassdao.getSaveSql(trusteeshipclass,sqlbuilder);
	}

	public List<Object> getDeleteSql(TrusteeShipClass trusteeshipclass, StringBuilder sqlbuilder) {
		return this.trusteeshipclassdao.getDeleteSql(trusteeshipclass,sqlbuilder);
	}

	public List<Object> getUpdateSql(TrusteeShipClass trusteeshipclass, StringBuilder sqlbuilder) {
		return this.trusteeshipclassdao.getUpdateSql(trusteeshipclass,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.trusteeshipclassdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TrusteeShipClass getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TrusteeShipClass> getBaseDAO() {
		// TODO Auto-generated method stub
		return trusteeshipclassdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<Map<String, Object>> getTrusteeShipTchs(String subject, String grade, String year, String tchname) {
        return trusteeshipclassdao.getTrusteeShipTchs( subject, grade, year, tchname);
    }

    public List<Map<String, Object>> getTsClassList(TrusteeShipClass trusteeshipclass) {
        return trusteeshipclassdao.getTsClassList(trusteeshipclass);
    }
}

