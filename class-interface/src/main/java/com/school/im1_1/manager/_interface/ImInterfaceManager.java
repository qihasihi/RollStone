package com.school.im1_1.manager._interface;

import com.school.dao.base.ICommonDAO;
import com.school.im1_1.dao.inter._interface.IImInterfaceDAO;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager.inter._interface.IImInterfaceManager;
import com.school.manager.base.BaseManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-7-25.
 */
@Service
public class ImInterfaceManager extends BaseManager<ImInterfaceInfo> implements IImInterfaceManager {
    private IImInterfaceDAO imInterfaceDAO;
    @Autowired
    @Qualifier("imInterfaceDAO")
    public void setImInterfaceDAO(IImInterfaceDAO imInterfaceDAO) {
        this.imInterfaceDAO = imInterfaceDAO;
    }

    @Override
    protected ICommonDAO<ImInterfaceInfo> getBaseDAO() {
        return this.imInterfaceDAO;
    }

    @Override
    public Boolean doSave(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public Boolean doUpdate(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public List<ImInterfaceInfo> getList(ImInterfaceInfo obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getUpdateSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public ImInterfaceInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getStudyModule(ImInterfaceInfo obj) {
        return this.imInterfaceDAO.getStudyModule(obj);
    }
}
