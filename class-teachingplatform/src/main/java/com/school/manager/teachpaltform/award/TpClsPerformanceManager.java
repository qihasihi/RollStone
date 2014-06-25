package com.school.manager.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpClsPerformanceDAO;
import com.school.entity.teachpaltform.award.TpClsPerformanceInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.award.ITpClsPerformanceManager;
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
public class TpClsPerformanceManager extends BaseManager<TpClsPerformanceInfo> implements ITpClsPerformanceManager {
    private ITpClsPerformanceDAO tpClsPerformanceDAO;
    @Autowired
    @Qualifier("tpClsPerformanceDAO")
    public void setTpClsPerformanceDAO(ITpClsPerformanceDAO tpClsPerformanceDAO) {
        this.tpClsPerformanceDAO = tpClsPerformanceDAO;
    }

    @Override
    protected ICommonDAO<TpClsPerformanceInfo> getBaseDAO() {
        return tpClsPerformanceDAO;
    }

    @Override
    public Boolean doSave(final TpClsPerformanceInfo obj) {
        return tpClsPerformanceDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(final TpClsPerformanceInfo obj) {
        return tpClsPerformanceDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete( final TpClsPerformanceInfo obj) {
        return tpClsPerformanceDAO.doDelete(obj);
    }

    @Override
    public List<TpClsPerformanceInfo> getList( final TpClsPerformanceInfo obj, PageResult presult) {
        return tpClsPerformanceDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(final TpClsPerformanceInfo obj, StringBuilder sqlbuilder) {
        return tpClsPerformanceDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(final TpClsPerformanceInfo obj, StringBuilder sqlbuilder) {
        return tpClsPerformanceDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(final TpClsPerformanceInfo obj, StringBuilder sqlbuilder) {
        return tpClsPerformanceDAO.getDeleteSql(obj,sqlbuilder);
    }
    /**
     * 得到页面上的查询
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Integer courseid,final Integer classid,final Integer classtype){
        return tpClsPerformanceDAO.getPageDataList(courseid,classid,classtype);
    }

    @Override
    public TpClsPerformanceInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
