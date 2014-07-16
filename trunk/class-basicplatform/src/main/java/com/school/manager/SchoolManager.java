package com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ISchoolDAO;
import com.school.entity.SchoolInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ISchoolManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ·ÖÐ£
 * Created by zhengzhou on 14-4-7.
 */
@Service
public class SchoolManager extends BaseManager<SchoolInfo> implements ISchoolManager {

    private ISchoolDAO schoolDAO;
    @Autowired
    @Qualifier("schoolDAO")
    public void setSchoolDAO(ISchoolDAO schoolDAO){
        this.schoolDAO=schoolDAO;
    }

    @Override
    protected ICommonDAO<SchoolInfo> getBaseDAO() {
        return schoolDAO;
    }

    @Override
    public Boolean doSave(SchoolInfo obj) {
        return schoolDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(SchoolInfo obj) {
        return schoolDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(SchoolInfo obj) {
        return schoolDAO.doUpdate(obj);
    }

    @Override
    public List<SchoolInfo> getList(SchoolInfo obj, PageResult presult) {
        return schoolDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(SchoolInfo obj, StringBuilder sqlbuilder) {
        return schoolDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(SchoolInfo obj, StringBuilder sqlbuilder) {
        return schoolDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(SchoolInfo obj, StringBuilder sqlbuilder) {
        return schoolDAO.getDeleteSql(obj,sqlbuilder);
    }

    @Override
    public SchoolInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
