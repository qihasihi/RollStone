package com.school.dao.inter.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.entity.resource.score.UserScoreRank;

import java.util.List;

public interface IUserScoreRankDAO extends ICommonDAO<UserScoreRank>{
    /**
     * ����UserId�õ���ʵ����
     * @param userid
     * @return
     */
    public String getRealNameByUserId(String userid);

    /**
     * �õ�ͬ����SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(UserScoreRank entity,StringBuilder sqlbuilder);
}
