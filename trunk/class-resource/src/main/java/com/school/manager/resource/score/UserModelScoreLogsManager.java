package com.school.manager.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.resource.score.IUserModelScoreLogsDAO;
import com.school.entity.UserModelScoreLogsInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.score.IUserModelScoreLogsManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-2-22.
 */
@Service
public class UserModelScoreLogsManager extends BaseManager<UserModelScoreLogsInfo> implements IUserModelScoreLogsManager{
    @Autowired
    @Qualifier("userModelScoreLogsDAO")
    private IUserModelScoreLogsDAO userModelScoreLogsDAO;
    public void setUserModelScoreLogsDAO(IUserModelScoreLogsDAO userModelScoreLogsDAO) {
        this.userModelScoreLogsDAO = userModelScoreLogsDAO;
    }


    @Override
    protected ICommonDAO<UserModelScoreLogsInfo> getBaseDAO() {
        return this.userModelScoreLogsDAO;
    }

    @Override
    public Boolean doSave(UserModelScoreLogsInfo obj) {
        return this.userModelScoreLogsDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(UserModelScoreLogsInfo obj) {
        return this.userModelScoreLogsDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(UserModelScoreLogsInfo obj) {
        return this.userModelScoreLogsDAO.doDelete(obj);
    }

    @Override
    public List<UserModelScoreLogsInfo> getList(UserModelScoreLogsInfo obj, PageResult presult) {
        return this.userModelScoreLogsDAO.getList(obj,presult);
    }

    @Override
    public String getNextId() {
        return null;
    }

    @Override
    public Long getNextId(boolean bo) {
        return null;
    }

   
    public List<Object> getSaveSql(UserModelScoreLogsInfo obj, StringBuilder sqlbuilder) {
        return this.userModelScoreLogsDAO.getSaveSql(obj,sqlbuilder);
    }

    
    public List<Object> getUpdateSql(UserModelScoreLogsInfo obj, StringBuilder sqlbuilder) {
        return this.userModelScoreLogsDAO.getUpdateSql(obj,sqlbuilder);
    }

  
    public List<Object> getDeleteSql(UserModelScoreLogsInfo obj, StringBuilder sqlbuilder) {
        return this.userModelScoreLogsDAO.getDeleteSql(obj,sqlbuilder);
    }

    public UserModelScoreLogsInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }


    public List<Map<String, Object>> getUserScoreDetails(Integer userid) {
        return this.userModelScoreLogsDAO.getUserScoreDetails(userid);
    }

    public List<UserModelScoreLogsInfo> getUserResourceScoreList(UserModelScoreLogsInfo obj, PageResult presult) {
        return this.userModelScoreLogsDAO.getUserResourceScoreList(obj,presult);
    }
}
