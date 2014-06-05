
package com.school.manager.inter.resource.score;

import com.school.entity.resource.score.UserScoreRank;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface IUserScoreRankManager  extends IBaseManager<UserScoreRank> {

    public String getRealNameByUserId(String userid);
    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(UserScoreRank entity,StringBuilder sqlbuilder);
}
