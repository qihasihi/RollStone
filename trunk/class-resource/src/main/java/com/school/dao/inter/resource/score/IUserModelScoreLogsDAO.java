package com.school.dao.inter.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.entity.UserModelScoreLogsInfo;
import com.school.util.PageResult;

import java.util.*;

/**
 * Created by yuechunyang on 14-2-22.
 */
public interface IUserModelScoreLogsDAO extends ICommonDAO<UserModelScoreLogsInfo> {
    public List<Map<String,Object>> getUserScoreDetails(Integer userid);
    public List<UserModelScoreLogsInfo> getUserResourceScoreList(UserModelScoreLogsInfo obj, PageResult presult) ;
}
