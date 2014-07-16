package com.school.manager.inter.resource.score;

import com.school.entity.UserModelScoreLogsInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-2-22.
 */
public interface IUserModelScoreLogsManager extends IBaseManager<UserModelScoreLogsInfo> {
    public List<Map<String,Object>> getUserScoreDetails(Integer userid);
    public List<UserModelScoreLogsInfo> getUserResourceScoreList(UserModelScoreLogsInfo obj, PageResult presult) ;
}
