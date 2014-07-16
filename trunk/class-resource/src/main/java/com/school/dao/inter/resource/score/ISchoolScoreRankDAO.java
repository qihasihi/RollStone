
package com.school.dao.inter.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.score.SchoolScoreRank;

import java.util.List;

public interface ISchoolScoreRankDAO extends ICommonDAO<SchoolScoreRank>{
    /**
     * �õ�ͬ����SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(SchoolScoreRank entity,StringBuilder sqlbuilder);
}
