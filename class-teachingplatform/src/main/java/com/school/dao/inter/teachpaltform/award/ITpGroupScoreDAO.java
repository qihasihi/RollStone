package com.school.dao.inter.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;

import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpGroupScoreDAO extends ICommonDAO<TpGroupScore> {

    /**
     * 添加或更新
     * @param entity
     * @return
     */
    public boolean AddOrUpdate(final TpGroupScore entity);
}
