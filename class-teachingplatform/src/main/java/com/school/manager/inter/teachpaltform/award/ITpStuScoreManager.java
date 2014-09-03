package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpStuScoreManager extends IBaseManager<TpStuScore>{
    boolean AddOrUpdate(final TpStuScore entity);
    /**
     * 得到页面上的查询
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,
                                                    final Integer classtype,final Integer subjectid,
                                                    final String groupidStr,final Integer uid);

    /**
     * 教师组长提交时，初始化相关数据
     * @param obj
     * @return
     */
    public boolean stuScoreLastInit(final TpStuScore obj,final String groupidArr);


    /**
     * 添加或修改积分
     * @param obj
     * @return
     */
    public List<Object> getAddOrUpdateColScore(final TpStuScore obj,StringBuilder sqlbuilder);

    /**
     * 积分统计
     * @param subjectid
     * @param classid
     * @return
     */
    public List<Map<String,Object>> getScoreStatices(final Integer subjectid,final Integer classid);
}
