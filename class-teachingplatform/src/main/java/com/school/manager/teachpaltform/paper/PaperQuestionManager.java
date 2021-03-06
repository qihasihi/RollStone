
package  com.school.manager.teachpaltform.paper;

import java.util.List;
import java.util.Map;

import com.school.util.UtilTool;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.paper.IPaperQuestionDAO;

import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.util.PageResult;

@Service
public class  PaperQuestionManager extends BaseManager<PaperQuestion> implements IPaperQuestionManager  { 
	
	private IPaperQuestionDAO paperquestiondao;
	
	@Autowired
	@Qualifier("paperQuestionDAO")
	public void setPaperquestiondao(IPaperQuestionDAO paperquestiondao) {
		this.paperquestiondao = paperquestiondao;
	}
	
	public Boolean doSave(PaperQuestion paperquestion) {
		return this.paperquestiondao.doSave(paperquestion);
	}
	
	public Boolean doDelete(PaperQuestion paperquestion) {
		return this.paperquestiondao.doDelete(paperquestion);
	}

	public Boolean doUpdate(PaperQuestion paperquestion) {
		return this.paperquestiondao.doUpdate(paperquestion);
	}
	
	public List<PaperQuestion> getList(PaperQuestion paperquestion, PageResult presult) {
		return this.paperquestiondao.getList(paperquestion,presult);	
	}
	
	public List<Object> getSaveSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		return this.paperquestiondao.getSaveSql(paperquestion,sqlbuilder);
	}

	public List<Object> getDeleteSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		return this.paperquestiondao.getDeleteSql(paperquestion,sqlbuilder);
	}

	public List<Object> getUpdateSql(PaperQuestion paperquestion, StringBuilder sqlbuilder) {
		return this.paperquestiondao.getUpdateSql(paperquestion,sqlbuilder);
	}

	
	public PaperQuestion getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 得到同步SQL语句
     * @param paperquestion
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSynchroSql(PaperQuestion paperquestion, StringBuilder sqlbuilder){
        return this.paperquestiondao.getSynchroSql(paperquestion,sqlbuilder);
    }

	@Override
	protected ICommonDAO<PaperQuestion> getBaseDAO() {
		// TODO Auto-generated method stub
		return paperquestiondao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    @Override
    public Float getSumScore(PaperQuestion paperQuestion) {
        return this.paperquestiondao.getSumScore(paperQuestion);
    }

    @Override
    public Integer paperQuesCount(Long paperid) {
        return this.paperquestiondao.paperQuesCount(paperid);
    }

    @Override
    public Boolean updateQuesTeamScore(PaperQuestion paperQuestion) {
        return this.paperquestiondao.updateQuesTeamScore(paperQuestion);
    }

    @Override
    public List<PaperQuestion> getQuestionByPaper(Long paperid,Integer classid,Integer classtype,Long taskid) {
        return this.paperquestiondao.getQuestionByPaper(paperid,classid,classtype,taskid);
    }

    public List<Map<String,Object>> getPaperQuesAllId(Long paperid){
        return this.paperquestiondao.getPaperQuesAllId(paperid);
    }

    @Override
    public List<PaperQuestion> getPaperTeamQuestionList(PaperQuestion p, PageResult pageResult) {
        return this.paperquestiondao.getPaperTeamQuestionList(p,pageResult);
    }
    /**
     * 得到试卷下的所有分数或某题分数
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getPaperQuesAllScore(Long paperid,Long quesid,Long courseid){
        return this.paperquestiondao.getPaperQuesAllScore(paperid,quesid,courseid);
    }

    /**
     * 得到一个试卷下主观题的数量
     * @param paperid
     * @return
     */
    public List<Map<String,Object>> getZGTCount(final Long paperid){
        return this.paperquestiondao.getZGTCount(paperid);
    }

    /**
     * 得到当前班级下，当前试题，试卷下的正确率
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getClsPaperQuesZQLV(Long paperid,Long quesid,Integer classid,Long taskid){
        return this.paperquestiondao.getClsPaperQuesZQLV(paperid, quesid, classid,taskid);
      }

    /**
     * 得到当前班级下，当前试题，试卷下的正确率
     * @param paperid
     * @param quesid
     * @return
     */
    public List<Map<String,Object>> getClsPaperQuesOptTJ(Long paperid,Long quesid,Integer classid,Long taskid){
        return this.paperquestiondao.getClsPaperQuesOptTJ(paperid,quesid,classid,taskid);
    }

    /**
     * 得到用户总分
     * @param paperid
     * @param userid
     * @param taskid
     * @return
     */
    public List<Map<String,Object>> getPaperScoreByUser(final long paperid,final Integer userid,final long taskid){
        return this.paperquestiondao.getPaperScoreByUser(paperid,userid,taskid);
    }

}

