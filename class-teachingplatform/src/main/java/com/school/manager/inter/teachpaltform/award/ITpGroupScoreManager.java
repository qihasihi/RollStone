package com.school.manager.inter.teachpaltform.award;

import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpGroupScoreManager extends IBaseManager<TpGroupScore> {

    /**
     * 添加或更新
     * @param entity
     * @return
     */
    public boolean AddOrUpdate(final TpGroupScore entity);
}
