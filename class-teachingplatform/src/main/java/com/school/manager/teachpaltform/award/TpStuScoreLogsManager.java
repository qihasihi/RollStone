
package  com.school.manager.teachpaltform.award;

import java.util.ArrayList;
import java.util.List;

import com.school.entity.ClassInfo;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.ClassManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.award.ITpStuScoreLogsDAO;

import com.school.entity.teachpaltform.award.TpStuScoreLogs;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.util.PageResult;

@Service("tpStuScoreLogsManager")
public class  TpStuScoreLogsManager extends BaseManager<TpStuScoreLogs> implements ITpStuScoreLogsManager  {
	
	private ITpStuScoreLogsDAO tpStuScoreLogsDAO;
	
	@Autowired
	@Qualifier("tpStuScoreLogsDAO")
	public void setTpStuScoreLogsDAO(ITpStuScoreLogsDAO tpStuScoreLogsDAO) {
		this.tpStuScoreLogsDAO = tpStuScoreLogsDAO;
	}

	public Boolean doSave(TpStuScoreLogs tpstuscorelogs) {
		return this.tpStuScoreLogsDAO.doSave(tpstuscorelogs);
	}
	
	public Boolean doDelete(TpStuScoreLogs tpstuscorelogs) {
		return this.tpStuScoreLogsDAO.doDelete(tpstuscorelogs);
	}

	public Boolean doUpdate(TpStuScoreLogs tpstuscorelogs) {
		return this.tpStuScoreLogsDAO.doUpdate(tpstuscorelogs);
	}
	
	public List<TpStuScoreLogs> getList(TpStuScoreLogs tpstuscorelogs, PageResult presult) {
		return this.tpStuScoreLogsDAO.getList(tpstuscorelogs,presult);
	}
	
	public List<Object> getSaveSql(TpStuScoreLogs tpstuscorelogs, StringBuilder sqlbuilder) {
		return this.tpStuScoreLogsDAO.getSaveSql(tpstuscorelogs,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpStuScoreLogs tpstuscorelogs, StringBuilder sqlbuilder) {
		return this.tpStuScoreLogsDAO.getDeleteSql(tpstuscorelogs,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpStuScoreLogs tpstuscorelogs, StringBuilder sqlbuilder) {
		return this.tpStuScoreLogsDAO.getUpdateSql(tpstuscorelogs,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpStuScoreLogsDAO.doExcetueArrayProc(sqlArrayList,objArrayList);
	}

    //奖励加分
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,Integer type){
        if(courseid==null||classid==null||taskid==null||userid==null||type==null)
            return false;
        Boolean returnVal=false;
        TpStuScoreLogs entity=new TpStuScoreLogs();
        entity.setCourseid(courseid);
        entity.setClassid(classid);
        entity.setTaskid(taskid);
        entity.setUserid(userid);
        List<TpStuScoreLogs> tsScoreList=this.getList(entity,null);
        //如果不存在，则需要添加，否则直接返回true
        String awardSettings=getPropertiesAwardScore(type);
        String[] awardArray=awardSettings.split(",");

        //连接四中，添加蓝宝石,在UtilTool中进行调用 返回true
        boolean isAddEttBlue=true;

       List<String> sqlArrayList=new ArrayList<String>();
       List<List<Object>> objArrayList=new ArrayList<List<Object>>();


        if(tsScoreList==null||tsScoreList.size()<1){
            if(isAddEttBlue)
                entity.setJewel(Integer.parseInt(awardArray[1]));
            else
                entity.setJewel(0);
            entity.setScore(Integer.parseInt(awardArray[0]));
            //添加
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.tpStuScoreLogsDAO.getSaveSql(entity, sqlbuilder);
            if(sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }else{//如果存在，则进行修改
            return true;
//            entity=tsScoreList.get(0);
//            if(isAddEttBlue)
//                entity.setJewel(Integer.parseInt(awardArray[1]));
//            else
//                entity.setJewel(0);
//            entity.setScore(Integer.parseInt(awardArray[0]));
//            StringBuilder sqlbuilder=new StringBuilder();
//            List<Object> objList=this.tpStuScoreLogsDAO.getUpdateSql(entity, sqlbuilder);
//            if(sqlbuilder.toString().trim().length()>0){
//                sqlArrayList.add(sqlbuilder.toString());
//                objArrayList.add(objList);
//            }
        }
        //查询班级的dc_school_id
        ClassInfo cls=new ClassInfo();
        cls.setClassid(entity.getClassid().intValue());
        IClassManager classManager=SpringBeanUtil.getBean(ClassManager.class);
        List<ClassInfo> clsList=classManager.getList(cls,null);
        if(clsList==null||clsList.size()<1){
          return returnVal;
        }

        //修改或添加StuScore
        TpStuScore tss=new TpStuScore();
        tss.setDcschoolid(clsList.get(0).getDcschoolid().longValue());
        tss.setClassid(entity.getClassid());
        tss.setClasstype(1);
        tss.setCourseid(entity.getCourseid());
        tss.setTaskscore(Integer.parseInt(UtilTool.stuScoreAwardUtilProperty.getProperty("TASK_SCORE").toString()));
        tss.setUserid(entity.getUserid());
//        tss.setGroupid(groupid);   //在数据库里自动查询
//        tss.setSubjectid(subjectid);
        StringBuilder sqlbuilder=new StringBuilder();
        ITpStuScoreManager stuScoreManager=SpringBeanUtil.getBean(TpStuScoreManager.class);
        List<Object> objList= stuScoreManager.getAddOrUpdateColScore(tss,sqlbuilder);
        if(sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.doExcetueArrayProc(sqlArrayList,objArrayList)){
                returnVal=true;
            }
        }
        return returnVal;

    }

    /**
     * 根据类型得到积分情况
     * @param type
     * @return
     */
    private String getPropertiesAwardScore(Integer type){
        String awardSettings=null;
        switch(type){
            case 1:   //#1. 试题类：学习任务首页，提交时，给出提醒。
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("QUESTION_SUCCESS_SCORE");
                break;
            case 2://#2. 资源学习：① 查看，不给提醒，但是系统后台统计积分；
            case 3:// #3  资源学习 提交心得，在首页或者专题资源页首次输入完心得提交时提醒（修改心得不再提醒）。
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("RESOURCE_SUCCESS_SCORE");
                break;
            case 4:  // #4. 互动交流：①查看，不给提醒，但是系统后台统计积分；
            case 5: // #5. 互动交流 ②发主帖，在首次发完主帖时提醒（修改主帖不再提醒）。
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("INTERACTIVE_SUCCESS_SCORE");
                break;
            case 6: //#6. 成卷测试 在交卷时给出提醒。
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("PAPER_TEA_SUCCESS_SCORE");
                break;
            case 7: // #7. 自主测试
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("PAPER_ZIZHU_SUCCESS_SCORE");
                break;
            case 8:// #8、微课程学习：
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("PAPER_VIDEO_SUCCESS_SCORE");
                break;
            case 9:// #9. 直播课：在学生进入进入直播视频系统时给出提示。
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("LIVE_COURSE_SUCCESS_SCORE");
                break;
        }
       return awardSettings;
    }




	
	public TpStuScoreLogs getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpStuScoreLogs> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpStuScoreLogsDAO;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}
}

