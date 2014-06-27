package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpClassPerformanceAwardInfo;
import com.school.manager.base.IBaseManager;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpClassPerformanceAwardManager  extends IBaseManager<TpClassPerformanceAwardInfo>{
    boolean AddOrUpdate(final TpClassPerformanceAwardInfo entity);
}
