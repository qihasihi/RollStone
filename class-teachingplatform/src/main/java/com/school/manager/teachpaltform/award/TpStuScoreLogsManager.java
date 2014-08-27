
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

    //�����ӷ�
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
        //��������ڣ�����Ҫ��ӣ�����ֱ�ӷ���true
        String awardSettings=getPropertiesAwardScore(type);
        String[] awardArray=awardSettings.split(",");

        //�������У��������ʯ,��UtilTool�н��е��� ����true
        boolean isAddEttBlue=true;

       List<String> sqlArrayList=new ArrayList<String>();
       List<List<Object>> objArrayList=new ArrayList<List<Object>>();


        if(tsScoreList==null||tsScoreList.size()<1){
            if(isAddEttBlue)
                entity.setJewel(Integer.parseInt(awardArray[1]));
            else
                entity.setJewel(0);
            entity.setScore(Integer.parseInt(awardArray[0]));
            //���
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.tpStuScoreLogsDAO.getSaveSql(entity, sqlbuilder);
            if(sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }else{//������ڣ�������޸�
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
        //��ѯ�༶��dc_school_id
        ClassInfo cls=new ClassInfo();
        cls.setClassid(entity.getClassid().intValue());
        IClassManager classManager=SpringBeanUtil.getBean(ClassManager.class);
        List<ClassInfo> clsList=classManager.getList(cls,null);
        if(clsList==null||clsList.size()<1){
          return returnVal;
        }

        //�޸Ļ����StuScore
        TpStuScore tss=new TpStuScore();
        tss.setDcschoolid(clsList.get(0).getDcschoolid().longValue());
        tss.setClassid(entity.getClassid());
        tss.setClasstype(1);
        tss.setCourseid(entity.getCourseid());
        tss.setTaskscore(Integer.parseInt(UtilTool.stuScoreAwardUtilProperty.getProperty("TASK_SCORE").toString()));
        tss.setUserid(entity.getUserid());
//        tss.setGroupid(groupid);   //�����ݿ����Զ���ѯ
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
     * �������͵õ��������
     * @param type
     * @return
     */
    private String getPropertiesAwardScore(Integer type){
        String awardSettings=null;
        switch(type){
            case 1:   //#1. �����ࣺѧϰ������ҳ���ύʱ���������ѡ�
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("QUESTION_SUCCESS_SCORE");
                break;
            case 2://#2. ��Դѧϰ���� �鿴���������ѣ�����ϵͳ��̨ͳ�ƻ��֣�
            case 3:// #3  ��Դѧϰ �ύ�ĵã�����ҳ����ר����Դҳ�״��������ĵ��ύʱ���ѣ��޸��ĵò������ѣ���
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("RESOURCE_SUCCESS_SCORE");
                break;
            case 4:  // #4. �����������ٲ鿴���������ѣ�����ϵͳ��̨ͳ�ƻ��֣�
            case 5: // #5. �������� �ڷ����������״η�������ʱ���ѣ��޸������������ѣ���
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("INTERACTIVE_SUCCESS_SCORE");
                break;
            case 6: //#6. �ɾ���� �ڽ���ʱ�������ѡ�
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("PAPER_TEA_SUCCESS_SCORE");
                break;
            case 7: // #7. ��������
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("PAPER_ZIZHU_SUCCESS_SCORE");
                break;
            case 8:// #8��΢�γ�ѧϰ��
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("PAPER_VIDEO_SUCCESS_SCORE");
                break;
            case 9:// #9. ֱ���Σ���ѧ���������ֱ����Ƶϵͳʱ������ʾ��
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

