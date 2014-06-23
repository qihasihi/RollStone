package com.school.manager.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseRelatedDAO;
import com.school.entity.teachpaltform.TpCourseRelatedInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpCourseRelatedManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuechunyang on 14-6-19.
 */
@Service
public class TpCourseRelatedManager extends BaseManager<TpCourseRelatedInfo> implements ITpCourseRelatedManager {

    private ITpCourseRelatedDAO tpCourseRelatedDAO;

    @Autowired
    @Qualifier("tpCourseRelatedDAO")

    public void setTpCourseRelatedDAO(ITpCourseRelatedDAO tpCourseRelatedDAO) {
        this.tpCourseRelatedDAO = tpCourseRelatedDAO;
    }

    @Override
    protected ICommonDAO<TpCourseRelatedInfo> getBaseDAO() {
        return this.tpCourseRelatedDAO;
    }

    @Override
    public Boolean doSave(TpCourseRelatedInfo obj) {
        return null;
    }

    @Override
    public Boolean doUpdate(TpCourseRelatedInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(TpCourseRelatedInfo obj) {
        return null;
    }

    @Override
    public List<TpCourseRelatedInfo> getList(TpCourseRelatedInfo obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(TpCourseRelatedInfo obj, StringBuilder sqlbuilder) {
        return this.tpCourseRelatedDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(TpCourseRelatedInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(TpCourseRelatedInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public TpCourseRelatedInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
