package com.school.dao.inter.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.entity.UserModelTotalScoreInfo;
import com.school.entity.resource.score.UserModelTotalScore;

import java.util.*;

/**
 * Created by yuechunyang on 14-2-22.
 */
public interface IUserModelTotalScoreDAO extends ICommonDAO<UserModelTotalScoreInfo> {
    public List<Map<String,Object>> getUserScoreInfo(Integer userid);

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSQL(UserModelTotalScore entity,StringBuilder sqlbuilder);
}
