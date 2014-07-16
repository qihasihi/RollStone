package com.school.manager.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.resource.score.IUserModelTotalScoreDAO;
import com.school.entity.UserModelTotalScoreInfo;
import com.school.entity.resource.score.UserModelTotalScore;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.score.IUserModelTotalScoreManager;
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
public class UserModelTotalScoreManager extends BaseManager<UserModelTotalScoreInfo> implements IUserModelTotalScoreManager {
    @Autowired
    @Qualifier("userModelTotalScoreDAO")
    private IUserModelTotalScoreDAO userModelTotalScoreDAO;
    public void setUserModelTotalScoreDAO(IUserModelTotalScoreDAO userModelTotalScoreDAO) {
        this.userModelTotalScoreDAO = userModelTotalScoreDAO;
    }

    @Override
    protected ICommonDAO<UserModelTotalScoreInfo> getBaseDAO() {
        return this.userModelTotalScoreDAO;
    }

    @Override
    public Boolean doSave(UserModelTotalScoreInfo obj) {
        return this.userModelTotalScoreDAO.doSave(obj);
    }

    @Override
    public Boolean doUpdate(UserModelTotalScoreInfo obj) {
        return this.userModelTotalScoreDAO.doUpdate(obj);
    }

    @Override
    public Boolean doDelete(UserModelTotalScoreInfo obj) {
        return this.userModelTotalScoreDAO.doDelete(obj);
    }

    @Override
    public List<UserModelTotalScoreInfo> getList(UserModelTotalScoreInfo obj, PageResult presult) {
        return this.userModelTotalScoreDAO.getList(obj,presult);
    }

    @Override
    public String getNextId() {
        return null;
    }

    public List<Object> getSaveSql(UserModelTotalScoreInfo obj, StringBuilder sqlbuilder) {
        return this.userModelTotalScoreDAO.getSaveSql(obj,sqlbuilder);
    }

    public List<Object> getUpdateSql(UserModelTotalScoreInfo obj, StringBuilder sqlbuilder) {
        return this.userModelTotalScoreDAO.getUpdateSql(obj,sqlbuilder);
    }

    public List<Object> getDeleteSql(UserModelTotalScoreInfo obj, StringBuilder sqlbuilder) {
        return this.userModelTotalScoreDAO.getDeleteSql(obj,sqlbuilder);
    }

    public UserModelTotalScoreInfo getOfExcel(Sheet rs, int cols, int d, String type) {
        return null;
    }

    public List<Map<String, Object>> getUserScoreInfo(Integer userid) {
        return this.userModelTotalScoreDAO.getUserScoreInfo(userid);
    }
    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSQL(UserModelTotalScore entity,StringBuilder sqlbuilder){
        return this.userModelTotalScoreDAO.getSynchroSQL(entity,sqlbuilder);
    }
}
