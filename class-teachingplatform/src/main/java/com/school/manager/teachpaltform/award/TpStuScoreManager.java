package com.school.manager.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpStuScoreDAO;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Service
public class TpStuScoreManager extends BaseManager<TpStuScore> implements ITpStuScoreManager {
    private ITpStuScoreDAO tpStuScoreDAO;
    @Autowired
    @Qualifier("tpStuScoreDAO")
    public void setTpStuScoreDAO(final ITpStuScoreDAO tpStuScoreDAO) {
        this.tpStuScoreDAO = tpStuScoreDAO;
    }

    @Override
    protected ICommonDAO<TpStuScore> getBaseDAO() {
        return tpStuScoreDAO;
    }

    @Override
    public Boolean doSave(final TpStuScore obj) {
        return tpStuScoreDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(final TpStuScore obj) {
        return tpStuScoreDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(final TpStuScore obj) {
        return tpStuScoreDAO.doDelete(obj);
    }

    @Override
    public List<TpStuScore> getList(final TpStuScore obj, PageResult presult) {
        return tpStuScoreDAO.getList(obj,presult);
    }

    @Override
    public List<Object> getSaveSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return tpStuScoreDAO.getSaveSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getUpdateSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return tpStuScoreDAO.getUpdateSql(obj,sqlbuilder);
    }

    @Override
    public List<Object> getDeleteSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return tpStuScoreDAO.getDeleteSql(obj,sqlbuilder);
    }
    public boolean AddOrUpdate(final TpStuScore entity){
        return tpStuScoreDAO.AddOrUpdate(entity);
    }

    /**
     * 添加或修改积分
     * @param obj
     * @return
     */

    public List<Object> getAddOrUpdateColScore(final TpStuScore obj,StringBuilder sqlbuilder){
        return tpStuScoreDAO.getAddOrUpdateColScore(obj, sqlbuilder);
    }
    /**
     * 得到页面上的查询
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype
            ,final Integer subjectid,final String groupidStr,final Integer uid){
        return tpStuScoreDAO.getPageDataList(courseid,classid,classtype,subjectid,groupidStr,uid);
    }
    /**
     * 教师组长提交时，初始化相关数据
     * @param obj
     * @return
     */
    public boolean stuScoreLastInit(final TpStuScore obj,final String groupidArr){
        return tpStuScoreDAO.stuScoreLastInit(obj,groupidArr);
    }
    /**
     * 积分统计
     * @param subjectid
     * @param classid
     * @return
     */
    public List<Map<String,Object>> getScoreStatices(final Integer subjectid,final Integer classid,final String sort){
        return tpStuScoreDAO.getScoreStatices(subjectid,classid,sort);
    }
    /**
     * 更新统计小组分数
     * @param taskid
     * @param classid
     * @param userid
     * @param courseid
     * @param dcschoolid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateStaticesGroupScore(final Long taskid,final Integer classid
            ,final Integer userid,final Long courseid,final Integer dcschoolid,StringBuilder sqlbuilder){
        return tpStuScoreDAO.getUpdateStaticesGroupScore(taskid, classid, userid, courseid, dcschoolid, sqlbuilder);
    }
    /**
     * 满足条件执行统计
     * （班级人数==已录入提交人数）
     * @return
     */
    public boolean tpStuScoreCkAllComplateInput(final Long courseid,final Integer classid,final Integer subjectid,final Integer dcschoolid){
        return tpStuScoreDAO.tpStuScoreCkAllComplateInput(courseid, classid, subjectid, dcschoolid);
    }
    @Override
    public TpStuScore getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }
}
