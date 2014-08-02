package com.school.manager.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpGroupScoreDAO;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.award.ITpGroupScoreManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Service
public class TpGroupScoreManager extends BaseManager<TpGroupScore> implements ITpGroupScoreManager {
    private ITpGroupScoreDAO tpClsPerformanceDAO;
    @Autowired
    @Qualifier("tpGroupScoreDAO")
    public void setTpClsPerformanceDAO(ITpGroupScoreDAO tpClsPerformanceDAO) {
        this.tpClsPerformanceDAO = tpClsPerformanceDAO;
    }

    @Override
    protected ICommonDAO<TpGroupScore> getBaseDAO() {
        return tpClsPerformanceDAO;
    }

    @Override
    public Boolean doSave(final TpGroupScore obj) {
        return tpClsPerformanceDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(final TpGroupScore obj) {
        return tpClsPerformanceDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete( final TpGroupScore obj) {
        return tpClsPerformanceDAO.doDelete(obj);
    }

    @Override
    public List<TpGroupScore> getList( final TpGroupScore obj, PageResult presult) {
        return tpClsPerformanceDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(final TpGroupScore obj, StringBuilder sqlbuilder) {
        return tpClsPerformanceDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(final TpGroupScore obj, StringBuilder sqlbuilder) {
        return tpClsPerformanceDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(final TpGroupScore obj, StringBuilder sqlbuilder) {
        return tpClsPerformanceDAO.getDeleteSql(obj,sqlbuilder);
    }
    /**
     * 得到页面上的查询
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype,final Integer subjectid){
        return tpClsPerformanceDAO.getPageDataList(courseid,classid,classtype,subjectid);
    }

    @Override
    public TpGroupScore getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
    /**
     * 添加或更新
     * @param entity
     * @return
     */
    public boolean AddOrUpdate(final TpGroupScore entity){
        return tpClsPerformanceDAO.AddOrUpdate(entity);
    }
}
