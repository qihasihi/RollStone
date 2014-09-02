package com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ISchoolLogoDAO;
import com.school.entity.SchoolLogoInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ISchoolLogoManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yuechunyang on 14-9-2.
 */
@Service
public class SchoolLogoManager extends BaseManager<SchoolLogoInfo> implements ISchoolLogoManager {
    private ISchoolLogoDAO schoolLogoDAO;

    @Autowired
    @Qualifier("schoolLogoDAO")
    public void setSchoolLogoDAO(ISchoolLogoDAO schoolLogoDAO) {
        this.schoolLogoDAO = schoolLogoDAO;
    }

    @Override
    protected ICommonDAO<SchoolLogoInfo> getBaseDAO() {
        return this.schoolLogoDAO;
    }

    @Override
    public Boolean doSave(SchoolLogoInfo obj) {
        return this.schoolLogoDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(SchoolLogoInfo obj) {
        return this.schoolLogoDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(SchoolLogoInfo obj) {
        return schoolLogoDAO.doDelete(obj);
    }

    @Override
    public List<SchoolLogoInfo> getList(SchoolLogoInfo obj, PageResult presult) {
        return this.schoolLogoDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(SchoolLogoInfo obj, StringBuilder sqlbuilder) {
        return this.schoolLogoDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(SchoolLogoInfo obj, StringBuilder sqlbuilder) {
        return this.schoolLogoDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(SchoolLogoInfo obj, StringBuilder sqlbuilder) {
        return this.schoolLogoDAO.getDeleteSql(obj,sqlbuilder);
    }

    @Override
    public SchoolLogoInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
