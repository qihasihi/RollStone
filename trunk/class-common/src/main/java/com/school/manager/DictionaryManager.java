
package  com.school.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.inter.IDictionaryDAO;

import com.school.entity.DictionaryInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.util.PageResult;

@Service
public class  DictionaryManager extends BaseManager<DictionaryInfo> implements IDictionaryManager  { 
	
	private IDictionaryDAO dictionarydao;
	
	@Autowired
	//@Qualifier("dictionaryinfoDAO")
	public void setDictionarydao(IDictionaryDAO dictionarydao) {
		this.dictionarydao = dictionarydao;
	}
	
	public Boolean doSave(DictionaryInfo dictionaryinfo) {
		return dictionarydao.doSave(dictionaryinfo);
	}
	
	public Boolean doDelete(DictionaryInfo dictionaryinfo) {
		return dictionarydao.doDelete(dictionaryinfo);
	}

	public Boolean doUpdate(DictionaryInfo dictionaryinfo) {
		return dictionarydao.doUpdate(dictionaryinfo);
	}
	
	public List<DictionaryInfo> getList(DictionaryInfo dictionaryinfo, PageResult presult) {
		return dictionarydao.getList(dictionaryinfo,presult);	
	}
	
	public List<Object> getSaveSql(DictionaryInfo dictionaryinfo, StringBuilder sqlbuilder) {
		return dictionarydao.getSaveSql(dictionaryinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(DictionaryInfo dictionaryinfo, StringBuilder sqlbuilder) {
		return dictionarydao.getDeleteSql(dictionaryinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(DictionaryInfo dictionaryinfo, StringBuilder sqlbuilder) {
		return dictionarydao.getUpdateSql(dictionaryinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return dictionarydao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public DictionaryInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
	/**
	 * 根据type,得到数据
	 * @param type
	 * @param dicenum DictionaryInfo.DictOrderEnum 排序字段
	 * @return
	 */
	public List<DictionaryInfo> getDictionaryByType(String type){
		DictionaryInfo dictionaryInfo=new DictionaryInfo();
		dictionaryInfo.setDictionarytype(type);
		return this.dictionarydao.getList(dictionaryInfo, null);
	}
	
	@Override
	protected ICommonDAO<DictionaryInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return dictionarydao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return dictionarydao.getNextId();
	}
	
	public Map<String, Object> getDicMapByType(String type) {
		DictionaryInfo dictionary = new DictionaryInfo();
		dictionary.setDictionarytype(type);
		List<DictionaryInfo> dicList = this.dictionarydao.getList(dictionary, null);
		Map<String, Object> dicMap = new HashMap<String, Object>();
        if(dicList!=null){
            for(DictionaryInfo dic:dicList){
                dicMap.put(dic.getDictionaryname(), dic.getDictionaryvalue());
            }
        }
		return dicMap;
	}
}

