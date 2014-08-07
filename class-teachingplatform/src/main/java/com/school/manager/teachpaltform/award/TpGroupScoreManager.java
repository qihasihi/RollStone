package com.school.manager.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpGroupScoreDAO;
import com.school.entity.teachpaltform.award.TpGroupScore;
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
    private ITpGroupScoreDAO tpGroupScoreDAO;
    @Autowired
    @Qualifier("tpGroupScoreDAO")
    public void setTpGroupScoreDAO(ITpGroupScoreDAO tpGroupScoreDAO) {
        this.tpGroupScoreDAO = tpGroupScoreDAO;
    }

    @Override
    protected ICommonDAO<TpGroupScore> getBaseDAO() {
        return tpGroupScoreDAO;
    }

    @Override
    public Boolean doSave(final TpGroupScore obj) {
        return tpGroupScoreDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(final TpGroupScore obj) {
        return tpGroupScoreDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete( final TpGroupScore obj) {
        return tpGroupScoreDAO.doDelete(obj);
    }

    @Override
    public List<TpGroupScore> getList( final TpGroupScore obj, PageResult presult) {
        return tpGroupScoreDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(final TpGroupScore obj, StringBuilder sqlbuilder) {
        return tpGroupScoreDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(final TpGroupScore obj, StringBuilder sqlbuilder) {
        return tpGroupScoreDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(final TpGroupScore obj, StringBuilder sqlbuilder) {
        return tpGroupScoreDAO.getDeleteSql(obj,sqlbuilder);
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
        return tpGroupScoreDAO.AddOrUpdate(entity);
    }
}
