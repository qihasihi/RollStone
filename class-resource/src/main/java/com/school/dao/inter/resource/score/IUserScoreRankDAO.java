package com.school.dao.inter.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.score.UserScoreRank;

import java.util.List;

public interface IUserScoreRankDAO extends ICommonDAO<UserScoreRank>{
    /**
     * 根据UserId得到真实姓名
     * @param userid
     * @return
     */
    public String getRealNameByUserId(String userid);

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(UserScoreRank entity,StringBuilder sqlbuilder);
}
