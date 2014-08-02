package com.school.manager.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpStuScoreDAO;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
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
public class TpStuScoreManager extends BaseManager<TpStuScore> implements ITpStuScoreManager {
    private ITpStuScoreDAO tpClassPerformanceAwardDAO;
    @Autowired
    @Qualifier("tpStuScoreDAO")
    public void setTpClassPerformanceAwardDAO(final ITpStuScoreDAO tpClassPerformanceAwardDAO) {
        this.tpClassPerformanceAwardDAO = tpClassPerformanceAwardDAO;
    }

    @Override
    protected ICommonDAO<TpStuScore> getBaseDAO() {
        return tpClassPerformanceAwardDAO;
    }

    @Override
    public Boolean doSave(final TpStuScore obj) {
        return tpClassPerformanceAwardDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(final TpStuScore obj) {
        return tpClassPerformanceAwardDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(final TpStuScore obj) {
        return tpClassPerformanceAwardDAO.doDelete(obj);
    }

    @Override
    public List<TpStuScore> getList(final TpStuScore obj, PageResult presult) {
        return tpClassPerformanceAwardDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return tpClassPerformanceAwardDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return tpClassPerformanceAwardDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return tpClassPerformanceAwardDAO.getDeleteSql(obj,sqlbuilder);
    }
    public boolean AddOrUpdate(final TpStuScore entity){
        return tpClassPerformanceAwardDAO.AddOrUpdate(entity);
    }
    @Override
    public TpStuScore getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
