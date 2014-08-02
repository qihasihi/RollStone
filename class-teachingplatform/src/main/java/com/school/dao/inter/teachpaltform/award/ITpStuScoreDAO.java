package com.school.dao.inter.teachpaltform.award;

import com.school.dao.base.ICommonDAO;
import com.school.entity.teachpaltform.award.TpStuScore;

/**
 * Created by zhengzhou on 14-6-24.
 */
public interface ITpStuScoreDAO extends ICommonDAO<TpStuScore> {
    boolean AddOrUpdate(final TpStuScore entity);
}
