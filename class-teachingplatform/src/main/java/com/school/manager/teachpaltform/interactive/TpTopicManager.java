
package com.school.manager.teachpaltform.interactive;

import java.util.List;
import java.util.Map;

import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.school.dao.inter.teachpaltform.interactive.ITpTopicDAO;

import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.util.PageResult;

@Service
public class  TpTopicManager extends BaseManager<TpTopicInfo> implements ITpTopicManager  { 
	
	private ITpTopicDAO tptopicdao;
	
	@Autowired
	@Qualifier("tpTopicDAO")
	public void setTptopicdao(ITpTopicDAO tptopicdao) {
		this.tptopicdao = tptopicdao;
	}
	
	public Boolean doSave(TpTopicInfo tptopicinfo) {
		return this.tptopicdao.doSave(tptopicinfo);
	}
	
	public Boolean doDelete(TpTopicInfo tptopicinfo) {
		return this.tptopicdao.doDelete(tptopicinfo);
	}

	public Boolean doUpdate(TpTopicInfo tptopicinfo) {
		return this.tptopicdao.doUpdate(tptopicinfo);
	}
	
	public List<TpTopicInfo> getList(TpTopicInfo tptopicinfo, PageResult presult) {
		return this.tptopicdao.getList(tptopicinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpTopicInfo tptopicinfo, StringBuilder sqlbuilder) {
		return this.tptopicdao.getSaveSql(tptopicinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpTopicInfo tptopicinfo, StringBuilder sqlbuilder) {
		return this.tptopicdao.getDeleteSql(tptopicinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpTopicInfo tptopicinfo, StringBuilder sqlbuilder) {
		return this.tptopicdao.getUpdateSql(tptopicinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tptopicdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public TpTopicInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpTopicInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tptopicdao;
	}

    /**
     * �õ������ռ���ҳ��ʾ��ר����Ŀ
     * @param userref ��ѯ�û���ref
     * @param termid  ѧ��id
     * @return
     */
    public List<Map<String,Object>> getListTopicIndex(String userref,String termid){
        return this.tptopicdao.getListTopicIndex(userref,termid);
    }
    /**
     * �õ�ͬ����SQL
     * @param entity  ����ʵ��
     * @param sqlbuilder  ������SQL��䣬����ʵ������
     * @return
     */
    public List<Object> getSynchroSql(TpTopicInfo entity,StringBuilder sqlbuilder){
        return this.tptopicdao.getSynchroSql(entity,sqlbuilder);
    }

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return this.tptopicdao.getNextId();
	}
	
	  /**
     * ����CourseId�õ������ռ��ͳ��
     * @param courseid
     * @return
     */    
    public List<Map<String,Object>> getTopicStaticesPageIdx(Long courseid,String loginref,Integer roleInt){
    	return this.tptopicdao.getTopicStaticesPageIdx(courseid,loginref,roleInt);
    }

    /**
     *�õ�����ͳ��ҳ������
     * @param topicid
     * @param clsid
     * @return
     */
    public List<Map<String,Object>> getTopicStatices(Long topicid,Integer clsid,Integer clstype){
        return this.tptopicdao.getTopicStatices(topicid,clsid,clstype);
    }
    /**
     *�õ������ռ�༶��Ϣ
     * @param courseid
     * @param userid
     * @param roletype
     * @return
     */
    public List<Map<String,Object>> getInteractiveClass(Long courseid,Integer userid,Integer roletype){
        return this.tptopicdao.getInteractiveClass(courseid,userid,roletype);
    }
}

