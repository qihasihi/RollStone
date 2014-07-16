package com.school.manager.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpClassPerformanceAwardDAO;
import com.school.entity.teachpaltform.award.TpClassPerformanceAwardInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.award.ITpClassPerformanceAwardManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Service
public class TpClassPerformanceAwardManager extends BaseManager<TpClassPerformanceAwardInfo> implements ITpClassPerformanceAwardManager{
    private ITpClassPerformanceAwardDAO tpClassPerformanceAwardDAO;
    @Autowired
    @Qualifier("tpClassPerformanceAwardDAO")
    public void setTpClassPerformanceAwardDAO(final ITpClassPerformanceAwardDAO tpClassPerformanceAwardDAO) {
        this.tpClassPerformanceAwardDAO = tpClassPerformanceAwardDAO;
    }

    @Override
    protected ICommonDAO<TpClassPerformanceAwardInfo> getBaseDAO() {
        return tpClassPerformanceAwardDAO;
    }

    @Override
    public Boolean doSave(final TpClassPerformanceAwardInfo obj) {
        return tpClassPerformanceAwardDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(final TpClassPerformanceAwardInfo obj) {
        return tpClassPerformanceAwardDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(final TpClassPerformanceAwardInfo obj) {
        return tpClassPerformanceAwardDAO.doDelete(obj);
    }

    @Override
    public List<TpClassPerformanceAwardInfo> getList(final TpClassPerformanceAwardInfo obj, PageResult presult) {
        return tpClassPerformanceAwardDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(final TpClassPerformanceAwardInfo obj, StringBuilder sqlbuilder) {
        return tpClassPerformanceAwardDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(final TpClassPerformanceAwardInfo obj, StringBuilder sqlbuilder) {
        return tpClassPerformanceAwardDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(final TpClassPerformanceAwardInfo obj, StringBuilder sqlbuilder) {
        return tpClassPerformanceAwardDAO.getDeleteSql(obj,sqlbuilder);
    }
    public boolean AddOrUpdate(final TpClassPerformanceAwardInfo entity){
        return tpClassPerformanceAwardDAO.AddOrUpdate(entity);
    }
    @Override
    public TpClassPerformanceAwardInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
