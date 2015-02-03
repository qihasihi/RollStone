package com.school.manager.teachpaltform.paper;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.paper.IStuPaperTimesDAO;
import com.school.entity.teachpaltform.paper.StuPaperTimesInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IStuPaperTimesManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by zhengzhou on 15-2-2.
 */
@Service
public class StuPaperTimesManager extends BaseManager<StuPaperTimesInfo> implements IStuPaperTimesManager {
    @Autowired
    private IStuPaperTimesDAO stuPaperTimesDAO;

    @Override
    protected ICommonDAO<StuPaperTimesInfo> getBaseDAO() {
        return stuPaperTimesDAO;
    }

    @Override
    public Boolean doSave(StuPaperTimesInfo obj) {
        return stuPaperTimesDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(StuPaperTimesInfo obj) {
        return stuPaperTimesDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(StuPaperTimesInfo obj) {
        return stuPaperTimesDAO.doDelete(obj);
    }

    @Override
    public List<StuPaperTimesInfo> getList(StuPaperTimesInfo obj, PageResult presult) {
        return stuPaperTimesDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(StuPaperTimesInfo obj, StringBuilder sqlbuilder) {
        return stuPaperTimesDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(StuPaperTimesInfo obj, StringBuilder sqlbuilder) {
        return stuPaperTimesDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(StuPaperTimesInfo obj, StringBuilder sqlbuilder) {
        return stuPaperTimesDAO.getDeleteSql(obj,sqlbuilder);
    }

    @Override
    public StuPaperTimesInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
    /**
     * 得到学生做试卷已经开始，但未提交试卷的记录
     * @param entity
     * @return
     */
    public List<StuPaperTimesInfo> getStuPaperNoCommitUser(final StuPaperTimesInfo entity){
        return stuPaperTimesDAO.getStuPaperNoCommitUser(entity);
    }
}
