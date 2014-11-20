
package  com.school.manager.teachpaltform.award;

import java.util.*;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.ClassInfo;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpCourseTeachingMaterial;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.ClassManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseTeachingMaterialManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.manager.teachpaltform.TpCourseTeachingMaterialManager;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import jxl.Sheet;

import net.sf.json.JSONObject;
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

    //任务奖励加分

    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,Integer type,Integer dcschool ){
        return awardStuScore(courseid, classid, taskid, userid, jid, type, dcschool,1);
    }

    /**
     *奖励加分
     * @param courseid
     * @param classid
     * @param taskid
     * @param userid
     * @param jid
     * @param type
     * @param dcschool
     * @param awardType    奖励加分类型  1:任务得分  2：评论加分
     * @return
     */
    public Boolean awardStuScore(final Long courseid,final Long classid,final Long taskid,final Long userid,final String jid,Integer type,Integer dcschool,Integer awardType){
        System.out.println("award_stu_score------------------------------begin");
        if(courseid==null||classid==null||userid==null||type==null||dcschool==null)
            return false;
        System.out.println("courseid--"+courseid);
        System.out.println("classid--"+classid);
        System.out.println("taskid--"+taskid);
        System.out.println("userid--"+userid);
        System.out.println("jid--"+jid);
        System.out.println("type--"+type);
        System.out.println("dcschool--"+dcschool);
        //查询班级的dc_school_id
        ClassInfo cls=new ClassInfo();
        cls.setClassid(classid.intValue());
        cls.setDcschoolid(dcschool);
        IClassManager classManager=SpringBeanUtil.getBean(ClassManager.class);
        List<ClassInfo> clsList=classManager.getList(cls,null);
        if(clsList==null||clsList.size()<1){
            return false;
        }
        //验证是否该专题已经录入过成绩
        ITpStuScoreManager stuScoreManager=SpringBeanUtil.getBean(TpStuScoreManager.class);
        //爱学班级，已老师录入时为准，如果老师已经录入完毕，则代表不能再奖励加分
        if(clsList.get(0).getDctype()!=null&&clsList.get(0).getDctype()==3){
            Integer luruCount=stuScoreManager.getTpScoreCourseIsInput(userid.intValue(),courseid);
            if(luruCount==null||luruCount>0){//表示录入过数据
                System.out.println("error:The teacher or team leader was input "+userid+"'s score in this course('"+courseid+"')!");
                return false;
            }
        }

        Boolean returnVal=false;
        List<TpStuScoreLogs> tsScoreList=null;
        TpStuScoreLogs entity=new TpStuScoreLogs();
        entity.setCourseid(courseid);
        entity.setClassid(classid);
        if(taskid==null||taskid.toString().length()<1)
            entity.setTaskid(-1L);  //在tpstuscorelogs表中，taskid  -1表示是评价加分记录
        else
            entity.setTaskid(taskid);
        entity.setUserid(userid);
        entity.setDcschoolid(dcschool);
        System.out.println("award_stu_score------------------------------1");
        tsScoreList=this.getList(entity,null);
        System.out.println("award_stu_score------------------------------2");
        //如果不存在，则需要添加，否则直接返回true
        String awardSettings=getPropertiesAwardScore(type);
        String[] awardArray=awardSettings.split(",");
        System.out.println("award_stu_score------------------------------3");

        boolean isAddEttBlue=true;

        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();


        if(tsScoreList==null||tsScoreList.size()<1){
            if(isAddEttBlue){
                if(awardArray.length>1){
                    entity.setJewel(Integer.parseInt(awardArray[1]));
                }else{
                    isAddEttBlue=false;
                    entity.setJewel(0);
                }
            }else
                entity.setJewel(0);
            entity.setScore(Integer.parseInt(awardArray[0]));
            //添加
            StringBuilder sqlbuilder=new StringBuilder();
            System.out.println("award_stu_score------------------------------4");
            List<Object> objList=this.tpStuScoreLogsDAO.getSaveSql(entity, sqlbuilder);
            if(sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }else{//如果存在，则进行修改
            return false;
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
        System.out.println("award_stu_score------------------------------5");

        //添加课程积分。
        TpStuScore tss=new TpStuScore();
        tss.setDcschoolid(Long.parseLong(dcschool+""));
        tss.setClassid(entity.getClassid());
        tss.setClasstype(1);
        tss.setCourseid(entity.getCourseid());
        if(awardType==1){
            tss.setTaskscore(Integer.parseInt(UtilTool.stuScoreAwardUtilProperty.getProperty("TASK_SCORE").toString()));
        }else if(awardType==2){
            tss.setCommentscore(Integer.parseInt(awardArray[0]));
        }
        // 如果不是爱学课堂 总分则加一
        if(clsList.get(0).getDctype()!=null&&clsList.get(0).getDctype()!=3)
            tss.setCoursetotalscore(1L);
        tss.setUserid(entity.getUserid());
//        tss.setGroupid(groupid);   //在数据库里自动查询
//        tss.setSubjectid(subjectid);
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList= stuScoreManager.getAddOrUpdateColScore(tss,sqlbuilder);
        if(sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //如果不是爱学课堂，则进入，进行
        if(clsList.get(0).getDctype()!=null&&clsList.get(0).getDctype()!=3){
            sqlbuilder=new StringBuilder();
            objList=stuScoreManager.getUpdateStaticesGroupScore(taskid,classid.intValue(),userid.intValue(),courseid,dcschool,sqlbuilder);
            if(sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }


        System.out.println("award_stu_score------------------------------7");


        if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            System.out.println("award_stu_score------------------------------8");
            if(this.doExcetueArrayProc(sqlArrayList,objArrayList)){
                returnVal=true;
                System.out.println("award_stu_score------------------------------9");
                if(awardType==1&&isAddEttBlue&&jid!=null&&jid.trim().length()>0){
                    System.out.println("award_stu_score------------------------------10");
                    //连接四中，添加蓝宝石,在UtilTool中进行调用 返回true
                    String u=UtilTool.utilproperty.getProperty("TO_ETT_ADD_SAPPHIRE").toString();
                    HashMap<String,String> paramMap=new HashMap<String,String>();
                    paramMap.put("eventId","84");
                    paramMap.put("taskId",taskid.toString());
                    paramMap.put("jid",jid.toString());
                    paramMap.put("sapphireCount","1");
                    paramMap.put("timestamp",new Date().getTime()+"");
                    String val = UrlSigUtil.makeSigSimple("addSapphire.do",paramMap);
                    paramMap.put("sign",val);
                    JSONObject jo=UtilTool.sendPostUrl(u,paramMap,"UTF-8");
                    if(jo==null||!jo.containsKey("result")||!jo.get("result").toString().trim().equals("1")){
                        if(jo!=null)
                            System.out.println(jo.get("msg"));
                        else
                            System.out.println("异常！");
                        returnVal=false;
                    }
//                    System.out.println(jo.get("result"));
                }
            }

        }
        System.out.println("award_stu_score------------------------------end");
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
            case 10:// #9. 直播课：在学生进入进入直播视频系统时给出提示。
                awardSettings=UtilTool.stuScoreAwardUtilProperty.getProperty("COMMENT_COURSE_SUCCESS_SCORE");
                break;
        }
       return awardSettings;
    }

    /**
     * 老师，班主任查看学生积分统计页面
     * @param termid
     * @param classid
     * @param subjectid
     * @param orderby
     * @return
     */
    public List<Map<String,Object>> getStuScoreTeachStatices(final String termid,final Integer classid,Integer subjectid,Integer orderby){
        return this.tpStuScoreLogsDAO.getStuScoreTeachStatices(termid, classid, subjectid, orderby);
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

