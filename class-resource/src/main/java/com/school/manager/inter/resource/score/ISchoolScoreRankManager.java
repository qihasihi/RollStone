
package com.school.manager.inter.resource.score;

import com.school.entity.resource.score.SchoolScoreRank;
import com.school.manager.base.IBaseManager;

import java.util.List;

public interface ISchoolScoreRankManager  extends IBaseManager<SchoolScoreRank> {
    public List<Object> getSynchroSql(SchoolScoreRank entity,StringBuilder sqlbuilder);
} 
