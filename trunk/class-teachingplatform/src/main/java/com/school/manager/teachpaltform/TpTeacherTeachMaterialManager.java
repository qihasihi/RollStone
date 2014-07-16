package com.school.manager.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.ITpTeacherTeachMaterialDAO;
import com.school.entity.teachpaltform.TpTeacherTeachMaterial;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpTeacherTeachMaterialManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhengzhou on 14-3-29.
 */
@Service
public class TpTeacherTeachMaterialManager extends BaseManager<TpTeacherTeachMaterial> implements ITpTeacherTeachMaterialManager {

    private ITpTeacherTeachMaterialDAO tpTeacherTeachMaterialDAO;
    @Autowired
    @Qualifier("tpTeacherTeachMaterialDAO")
    public void setTpTeacherTeachMaterialDAO(ITpTeacherTeachMaterialDAO tpTeacherTeachMaterialDAO) {
        this.tpTeacherTeachMaterialDAO = tpTeacherTeachMaterialDAO;
    }

    @Override
    public List<Object> getSaveSql(TpTeacherTeachMaterial obj, StringBuilder sqlbuilder) {
        return tpTeacherTeachMaterialDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(TpTeacherTeachMaterial obj, StringBuilder sqlbuilder) {
        return tpTeacherTeachMaterialDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(TpTeacherTeachMaterial obj, StringBuilder sqlbuilder) {
        return tpTeacherTeachMaterialDAO.getDeleteSql(obj,sqlbuilder);
    }

    @Override
    public TpTeacherTeachMaterial getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }

    @Override
    protected ICommonDAO<TpTeacherTeachMaterial> getBaseDAO() {
        return tpTeacherTeachMaterialDAO;
    }

    @Override
    public Boolean doSave(TpTeacherTeachMaterial obj) {
        return tpTeacherTeachMaterialDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(TpTeacherTeachMaterial obj) {
        return tpTeacherTeachMaterialDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(TpTeacherTeachMaterial obj) {
        return tpTeacherTeachMaterialDAO.doDelete(obj);
    }

    @Override
    public List<TpTeacherTeachMaterial> getList(TpTeacherTeachMaterial obj, PageResult presult) {
        return tpTeacherTeachMaterialDAO.getList(obj,presult);
    }
}
