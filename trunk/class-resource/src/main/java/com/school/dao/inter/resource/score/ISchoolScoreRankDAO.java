
package com.school.dao.inter.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.score.SchoolScoreRank;

import java.util.List;

public interface ISchoolScoreRankDAO extends ICommonDAO<SchoolScoreRank>{
    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(SchoolScoreRank entity,StringBuilder sqlbuilder);
}
