
package com.school.manager.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.resource.score.IUserScoreRankDAO;
import com.school.entity.resource.score.UserScoreRank;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.score.IUserScoreRankManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserScoreRankManager extends BaseManager<UserScoreRank> implements IUserScoreRankManager  {
	private IUserScoreRankDAO userscorerankdao;
	
	@Autowired
	@Qualifier("userScoreRankDAO")
	public void setUserscorerankdao(IUserScoreRankDAO userscorerankdao) {
		this.userscorerankdao = userscorerankdao;
	}
	
	public Boolean doSave(UserScoreRank userscorerank) {
		return userscorerankdao.doSave(userscorerank);
	}
	
	public Boolean doDelete(UserScoreRank userscorerank) {
		return userscorerankdao.doDelete(userscorerank);
	}

	public Boolean doUpdate(UserScoreRank userscorerank) {
		return userscorerankdao.doUpdate(userscorerank);
	}
	
	public List<UserScoreRank> getList(UserScoreRank userscorerank, PageResult presult) {
		return userscorerankdao.getList(userscorerank,presult);	
	}
	
	public List<Object> getSaveSql(UserScoreRank userscorerank, StringBuilder sqlbuilder) {
		return userscorerankdao.getSaveSql(userscorerank,sqlbuilder);
	}

	public List<Object> getDeleteSql(UserScoreRank userscorerank, StringBuilder sqlbuilder) {
		return userscorerankdao.getDeleteSql(userscorerank,sqlbuilder);
	}

	public List<Object> getUpdateSql(UserScoreRank userscorerank, StringBuilder sqlbuilder) {
		return userscorerankdao.getUpdateSql(userscorerank,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return userscorerankdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}

    public String getRealNameByUserId(String userid){
        return userscorerankdao.getRealNameByUserId(userid);
    }
    /**
     * 得到同步的SQL
     * @param entity
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(UserScoreRank entity,StringBuilder sqlbuilder){
        return userscorerankdao.getSynchroSql(entity,sqlbuilder);
    }

	public UserScoreRank getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<UserScoreRank> getBaseDAO() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

