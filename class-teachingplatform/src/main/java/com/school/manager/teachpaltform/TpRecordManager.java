package com.school.manager.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.ITpRecordDAO;
import com.school.entity.teachpaltform.TpRecordInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpRecordManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhengzhou on 14-8-19.
 */
@Service("tpRecordManager")
public class TpRecordManager extends BaseManager<TpRecordInfo> implements ITpRecordManager {

    private ITpRecordDAO tpRecordDAO;
    @Autowired
    @Qualifier("tpRecordDAO")
    public void setTpRecordDAO(ITpRecordDAO tprdao){
        this.tpRecordDAO=tprdao;
    }
    @Override
    protected ICommonDAO<TpRecordInfo> getBaseDAO() {
        return tpRecordDAO;
    }

    @Override
    public Boolean doSave(TpRecordInfo obj) {
        return tpRecordDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(TpRecordInfo obj) {
        return tpRecordDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(TpRecordInfo obj) {
        return tpRecordDAO.doDelete(obj);
    }

    @Override
    public List<TpRecordInfo> getList(TpRecordInfo obj, PageResult presult) {
        return tpRecordDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(TpRecordInfo obj, StringBuilder sqlbuilder) {
        return tpRecordDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(TpRecordInfo obj, StringBuilder sqlbuilder) {
        return tpRecordDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(TpRecordInfo obj, StringBuilder sqlbuilder) {
        return tpRecordDAO.getDeleteSql(obj,sqlbuilder);
    }

    @Override
    public TpRecordInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
