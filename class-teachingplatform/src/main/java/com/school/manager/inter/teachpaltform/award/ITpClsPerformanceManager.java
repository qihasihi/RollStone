package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpClsPerformanceInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpClsPerformanceManager extends IBaseManager<TpClsPerformanceInfo> {
    /**
     * �õ�ҳ���ϵĲ�ѯ
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Integer courseid,final Integer classid,final Integer classtype);
}
