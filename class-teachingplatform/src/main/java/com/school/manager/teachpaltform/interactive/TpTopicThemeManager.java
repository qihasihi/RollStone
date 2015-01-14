
package com.school.manager.teachpaltform.interactive;

import java.util.List;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.inter.teachpaltform.interactive.ITpTopicThemeDAO;

import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.util.PageResult;

@Service
public class  TpTopicThemeManager extends BaseManager<TpTopicThemeInfo> implements ITpTopicThemeManager  { 
	
	private ITpTopicThemeDAO tptopicthemedao;
	
	@Autowired
	@Qualifier("tpTopicThemeDAO")
	public void setTptopicthemedao(ITpTopicThemeDAO tptopicthemedao) {
		this.tptopicthemedao = tptopicthemedao;
	}
	
	public Boolean doSave(TpTopicThemeInfo tptopicthemeinfo) {
		return this.tptopicthemedao.doSave(tptopicthemeinfo);
	}
	
	public Boolean doDelete(TpTopicThemeInfo tptopicthemeinfo) {
		return this.tptopicthemedao.doDelete(tptopicthemeinfo);
	}

	public Boolean doUpdate(TpTopicThemeInfo tptopicthemeinfo) {
		return this.tptopicthemedao.doUpdate(tptopicthemeinfo);
	}
	
	public List<TpTopicThemeInfo> getList(TpTopicThemeInfo tptopicthemeinfo, PageResult presult) {
		return this.tptopicthemedao.getList(tptopicthemeinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpTopicThemeInfo tptopicthemeinfo, StringBuilder sqlbuilder) {
		return this.tptopicthemedao.getSaveSql(tptopicthemeinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpTopicThemeInfo tptopicthemeinfo, StringBuilder sqlbuilder) {
		return this.tptopicthemedao.getDeleteSql(tptopicthemeinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpTopicThemeInfo tptopicthemeinfo, StringBuilder sqlbuilder) {
		return this.tptopicthemedao.getUpdateSql(tptopicthemeinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tptopicthemedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpTopicThemeInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @return
     */
    public void getSynchroSql(TpTopicThemeInfo entity,List<String> sqlArrayList,List<List<Object>> objArrayList){
        this.tptopicthemedao.getSynchroSql(entity,sqlArrayList,objArrayList);
    }

    /**
     * 得到该论题下，某个班级的评论数
     * @param topicid
     * @param clsid
     * @return
     */
    public Integer getPingLunShu(final Long topicid,final Integer clsid){
        return this.tptopicthemedao.getPingLunShu(topicid,clsid);
    }

	@Override
	protected ICommonDAO<TpTopicThemeInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tptopicthemedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

