package com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.IInitWizardDAO;
import com.school.entity.InitWizardInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.IInitWizardManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuechunyang on 14-3-6.
 */
@Service
public class InitWizardManager extends BaseManager<InitWizardInfo> implements IInitWizardManager{
    private IInitWizardDAO initWizardDAO;
    @Autowired
    @Qualifier("initWizardDAO")
    public void setInitWizardDAO(IInitWizardDAO initWizardDAO) {
        this.initWizardDAO = initWizardDAO;
    }

    @Override
    protected ICommonDAO<InitWizardInfo> getBaseDAO() {
        return this.initWizardDAO;
    }

    @Override
    public Boolean doSave(InitWizardInfo obj) {
        return this.initWizardDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(InitWizardInfo obj) {
        return this.initWizardDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(InitWizardInfo obj) {
        return this.initWizardDAO.doDelete(obj);
    }

    @Override
    public List<InitWizardInfo> getList(InitWizardInfo obj, PageResult presult) {
        return this.initWizardDAO.getList(obj,presult);
    }

    @Override
    public String getNextId() {
        return null;
    }

    public List<Object> getSaveSql(InitWizardInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getUpdateSql(InitWizardInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public List<Object> getDeleteSql(InitWizardInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    public InitWizardInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
