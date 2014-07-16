
package  com.school.manager.resource;

import java.util.List;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.resource.IStoreDAO;

import com.school.entity.resource.StoreInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.IStoreManager;
import com.school.util.PageResult;

@Service
public class  StoreManager extends BaseManager<StoreInfo> implements IStoreManager  { 
	
	private IStoreDAO storedao;
	
	@Autowired
	@Qualifier("storeDAO")
	public void setStoredao(IStoreDAO storedao) {
		this.storedao = storedao;
	}
	
	public Boolean doSave(StoreInfo storeinfo) {
		return storedao.doSave(storeinfo);
	}
	
	public Boolean doDelete(StoreInfo storeinfo) {
		return storedao.doDelete(storeinfo);
	}

	public Boolean doUpdate(StoreInfo storeinfo) {
		return storedao.doUpdate(storeinfo);
	}
	
	public List<StoreInfo> getList(StoreInfo storeinfo, PageResult presult) {
		return storedao.getList(storeinfo,presult);	
	}
	
	public List<Object> getSaveSql(StoreInfo storeinfo, StringBuilder sqlbuilder) {
		return storedao.getSaveSql(storeinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(StoreInfo storeinfo, StringBuilder sqlbuilder) {
		return storedao.getDeleteSql(storeinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(StoreInfo storeinfo, StringBuilder sqlbuilder) {
		return storedao.getUpdateSql(storeinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return storedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public StoreInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<StoreInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return storedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

