package com.school.manager.inter.resource.score;

import com.school.entity.UserModelTotalScoreInfo;
import com.school.entity.resource.score.UserModelTotalScore;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-2-22.
 */
public interface IUserModelTotalScoreManager extends IBaseManager<UserModelTotalScoreInfo> {
    public List<Map<String,Object>> getUserScoreInfo(Integer userid);

    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSQL(UserModelTotalScore entity,StringBuilder sqlbuilder);
}
