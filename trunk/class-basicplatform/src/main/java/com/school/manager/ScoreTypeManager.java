package com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IScoreTypeDAO;
import com.school.entity.ScoreTypeInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IScoreTypeManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Service
public class ScoreTypeManager extends BaseManager<ScoreTypeInfo> implements IScoreTypeManager {
    @Autowired
    @Qualifier("scoreTypeDAO")
    private IScoreTypeDAO scoreTypeDAO;
    public IScoreTypeDAO getScoreTypeDAO() {
        return scoreTypeDAO;
    }

    @Override
    protected ICommonDAO<ScoreTypeInfo> getBaseDAO() {
        return this.scoreTypeDAO;
    }

    @Override
    public Boolean doSave(ScoreTypeInfo obj) {
        return this.scoreTypeDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(ScoreTypeInfo obj) {
        return this.scoreTypeDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(ScoreTypeInfo obj) {
        return this.scoreTypeDAO.doDelete(obj);
    }

    @Override
    public List<ScoreTypeInfo> getList(ScoreTypeInfo obj, PageResult presult) {
        return this.scoreTypeDAO.getList(obj,presult);
    }

    @Override
    public String getNextId() {
        return null;
    }

    public List<Object> getSaveSql(ScoreTypeInfo obj, StringBuilder sqlbuilder) {
        return this.scoreTypeDAO.getSaveSql(obj,sqlbuilder);
    }

    public List<Object> getUpdateSql(ScoreTypeInfo obj, StringBuilder sqlbuilder) {
        return this.scoreTypeDAO.getUpdateSql(obj,sqlbuilder);
    }

    public List<Object> getDeleteSql(ScoreTypeInfo obj, StringBuilder sqlbuilder) {
        return this.scoreTypeDAO.getDeleteSql(obj,sqlbuilder);
    }

    public ScoreTypeInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
