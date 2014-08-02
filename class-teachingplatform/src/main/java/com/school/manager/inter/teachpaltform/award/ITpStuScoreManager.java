package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.base.IBaseManager;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpStuScoreManager extends IBaseManager<TpStuScore>{
    boolean AddOrUpdate(final TpStuScore entity);
}
