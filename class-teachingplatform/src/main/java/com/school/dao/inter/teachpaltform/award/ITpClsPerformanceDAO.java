package com.school.dao.inter.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.award.TpClsPerformanceInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpClsPerformanceDAO extends ICommonDAO<TpClsPerformanceInfo> {
    /**
     * 得到页面上的查询
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype);

    /**
     * 添加或更新
     * @param entity
     * @return
     */
    public boolean AddOrUpdate(final TpClsPerformanceInfo entity);
}
