package com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IGuideDAO;
import com.school.entity.GuideInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IGuideManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 向导，业务逻辑
 * Created by zhengzhou on 14-5-5.
 */
@Service
public class GuideManager extends BaseManager<GuideInfo> implements IGuideManager{
    private IGuideDAO guideDAO;
    @Autowired
    @Qualifier("guideDAO")
    public void setGuideDAO(IGuideDAO guideDAO) {
        this.guideDAO = guideDAO;
    }

    @Override
    protected ICommonDAO<GuideInfo> getBaseDAO() {
        return guideDAO;
    }

    @Override
    public Boolean doSave(GuideInfo obj) {
        return guideDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(GuideInfo obj) {
        return guideDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(GuideInfo obj) {
        return guideDAO.doDelete(obj);
    }

    @Override
    public List<GuideInfo> getList(GuideInfo obj, PageResult presult) {
        return guideDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(GuideInfo obj, StringBuilder sqlbuilder) {
        return guideDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(GuideInfo obj, StringBuilder sqlbuilder) {
        return guideDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(GuideInfo obj, StringBuilder sqlbuilder) {
        return guideDAO.getDeleteSql(obj,sqlbuilder);
    }

    @Override
    public GuideInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
