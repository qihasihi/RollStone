
package com.school.manager.resource.score;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.resource.score.ISchoolScoreRankDAO;
import com.school.entity.resource.score.SchoolScoreRank;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.resource.score.ISchoolScoreRankManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class  SchoolScoreRankManager extends BaseManager<SchoolScoreRank> implements ISchoolScoreRankManager  { 
	
	private ISchoolScoreRankDAO schoolscorerankdao;
	
	@Autowired
    @Qualifier("schoolScoreRankDAO")
	public void setSchoolscorerankdao(ISchoolScoreRankDAO schoolscorerankdao) {
		this.schoolscorerankdao = schoolscorerankdao;
	}
	
	public Boolean doSave(SchoolScoreRank schoolscorerank) {
		return schoolscorerankdao.doSave(schoolscorerank);
	}
	
	public Boolean doDelete(SchoolScoreRank schoolscorerank) {
		return schoolscorerankdao.doDelete(schoolscorerank);
	}

	public Boolean doUpdate(SchoolScoreRank schoolscorerank) {
		return schoolscorerankdao.doUpdate(schoolscorerank);
	}
	
	public List<SchoolScoreRank> getList(SchoolScoreRank schoolscorerank, PageResult presult) {
		return schoolscorerankdao.getList(schoolscorerank,presult);	
	}
	
	public List<Object> getSaveSql(SchoolScoreRank schoolscorerank, StringBuilder sqlbuilder) {
		return schoolscorerankdao.getSaveSql(schoolscorerank,sqlbuilder);
	}

	public List<Object> getDeleteSql(SchoolScoreRank schoolscorerank, StringBuilder sqlbuilder) {
		return schoolscorerankdao.getDeleteSql(schoolscorerank,sqlbuilder);
	}

	public List<Object> getUpdateSql(SchoolScoreRank schoolscorerank, StringBuilder sqlbuilder) {
		return schoolscorerankdao.getUpdateSql(schoolscorerank,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return schoolscorerankdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public SchoolScoreRank getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    public List<Object> getSynchroSql(SchoolScoreRank entity,StringBuilder sqlbuilder){
        return schoolscorerankdao.getSynchroSql(entity,sqlbuilder);
    }
	@Override
	protected ICommonDAO<SchoolScoreRank> getBaseDAO() {
		// TODO Auto-generated method stub
		return schoolscorerankdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}
}

