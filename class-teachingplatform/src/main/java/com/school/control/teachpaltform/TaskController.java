package com.school.control.teachpaltform;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.*;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.*;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.im1_1.manager.inter._interface.IImInterfaceManager;
import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.manager.inter.teachpaltform.paper.IStuPaperQuesLogsManager;
import com.school.manager.inter.teachpaltform.paper.ITpCoursePaperManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.award.TpStuScoreLogsManager;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.PaperManager;
import com.school.manager.teachpaltform.paper.PaperQuestionManager;
import com.school.manager.teachpaltform.paper.StuPaperQuesLogsManager;
import com.school.manager.teachpaltform.paper.TpCoursePaperManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.control.base.BaseController;

import static com.school.share.TaskLoopRemindUtil.sendTaskRemindObj;

@Controller
@RequestMapping(value="/task")
public class TaskController extends BaseController{
    @Autowired
    private ITpTaskManager tpTaskManager;
    @Autowired
    private ITpTaskAllotManager tpTaskAllotManager;
    @Autowired
    private IQuestionOptionManager questionOptionManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private ITpTopicThemeManager  tpTopicThemeManager;
    @Autowired
    private IQuestionAnswerManager questionAnswerManager;
    @Autowired
    private IQuestionManager questionManager;
    @Autowired
    private ITaskPerformanceManager taskPerformanceManager;
    @Autowired
    private IUserManager userManager;
    @Autowired
    private ITpOperateManager tpOperateManager;
    @Autowired
    private ITpCourseClassManager tpCourseClassManager;
    @Autowired
    private ITpGroupManager tpGroupManager;
    @Autowired
    private ITpGroupStudentManager tpGroupStudentManager;
    @Autowired
    private ITpTopicManager tpTopicManager;
    @Autowired
    private ITpCourseQuestionManager tpCourseQuestionManager;
    @Autowired
    private ITpCourseResourceManager tpCourseResourceManager;
    @Autowired
    private IClassManager classManager;
    @Autowired
    private ITpVirtualClassManager tpVirtualClassManager;
    @Autowired
    private ITaskSuggestManager taskSuggestManager;
    @Autowired
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    @Autowired
    private ISmsManager smsManager;
    @Autowired
    private ITpCoursePaperManager tpCoursePaperManager;
    @Autowired
    private IPaperManager paperManager;
    @Autowired
    private IStuPaperQuesLogsManager stuPaperQuesLogsManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private IResourceManager resourceManager;
    @Autowired
    private ITpStuScoreLogsManager tpStuScoreLogsManager;
    @Autowired
    private IPaperQuestionManager paperQuestionManager;
    @Autowired
    private IImInterfaceManager imInterfaceManager;
    @Autowired
    private ITermManager termManager;
    @Autowired
    private IClassUserManager classUserManager;


    /**
     * ���ݿ���ID�����������б�
     * @return
     */
    @RequestMapping(params="toTaskList",method=RequestMethod.GET)
    public ModelAndView toTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //�õ��ÿ������������������������
        JsonEntity je= new JsonEntity();
        String courseid=request.getParameter("courseid");
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");
        String materialid=request.getParameter("material_id");
        String addresstype = request.getParameter("addresstype");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        System.out.println("==========================="+TpCourseInfo.class.getResource(""));
        TpCourseInfo tc=new TpCourseInfo();
        if(addresstype!=null&&addresstype!=""){
            tc.setUserid(null);
            tc.setCourseid(null);
            tc.setLocalstatus(null);//����
        }else{
            tc.setUserid(this.logined(request).getUserid());
            tc.setCourseid(Long.parseLong(courseid));
            tc.setLocalstatus(1);//����
        }
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("�Ҳ���ָ������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid().toString();

        if(gradeid!=null&&!gradeid.toString().equals("0"))
            request.getSession().setAttribute("session_grade",gradeid);
        if(subjectid!=null&&!subjectid.toString().equals("0"))
            request.getSession().setAttribute("session_subject",subjectid);
        //������ʽ
        request.setAttribute("coursename", teacherCourseList.get(0).getCoursename());
        TpCourseInfo tcs= new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        tcs.setTermid(teacherCourseList.get(0).getTermid());
        tcs.setLocalstatus(1);
        if(subjectid!=null)
            tcs.setSubjectid(Integer.parseInt(subjectid));
        if(gradeid!=null&&gradeid.trim().length()>0)
            tcs.setGradeid(Integer.parseInt(gradeid));
        else
            tcs.setGradeid(Integer.parseInt(request.getSession().getAttribute("session_grade").toString()));


        List<TpCourseInfo>courseList=this.tpCourseManager.getCourseList(tcs, null);
        request.setAttribute("courseList", courseList);

        //��ǰѧ��
        request.setAttribute("currentTerm",this.termManager.getMaxIdTerm(false));
        String termid=teacherCourseList.get(0).getTermid();
        request.setAttribute("courseid", courseid);
        request.setAttribute("termid", termid);
        request.setAttribute("subjectid", subjectid);

        //if(materialid!=null&&!materialid.toString().equals("0"))
        //    request.getSession().setAttribute("session_material",materialid);

        //�����
        List<DictionaryInfo>courselevel=this.dictionaryManager.getDictionaryByType("COURSE_LEVEL");
        request.setAttribute("courselevel",courselevel);
        //SESSION���ר��״̬
        request.getSession().setAttribute("coursestate", teacherCourseList.get(0).getLocalstatus());
        request.setAttribute("course_level",teacherCourseList.get(0).getCourselevel());
        request.setAttribute("share_type",teacherCourseList.get(0).getSharetype());


        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��,δ��������
        t.setSelecttype(2);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);
        List<TpTaskInfo>bankList=this.tpTaskManager.getList(t,null);
        request.setAttribute("quoteList",bankList);
        return new ModelAndView("/teachpaltform/task/teacher/task-list");
    }

    /**
     * ��������Ԫ������ҳ��
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTaskElementDetial",method=RequestMethod.GET)
    public ModelAndView toTaskElementDetailPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String type = request.getParameter("tasktype");
        //��Դ����
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid",courseid);
        request.setAttribute("resType", resourceTypeList);
        request.setAttribute("fileSystemIpPort", request.getSession().getAttribute("FILE_SYSTEM_IP_PORT").toString());
        //       request.setAttribute("fileSystemIpPort", "http://localhost:8080");
        request.setAttribute("nextid", this.resourceManager.getNextId(true));
        if(type.equals("1")){
            //��Ŀ
            IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
            EttColumnInfo ettColumnInfo = new EttColumnInfo();
            ettColumnInfo.setRoletype(2);
            ettColumnInfo.setSchoolid(this.logined(request).getDcschoolid());
            List<EttColumnInfo> columnList =columnManager.getEttColumnSplit(ettColumnInfo,null);
            if(columnList!=null&&columnList.size()>0){
                Boolean b= false;
                for(EttColumnInfo obj:columnList){
                    if(obj.getEttcolumnid()==7||obj.getEttcolumnid()==320){
                        b=true;
                        break;
                    }
                }
                request.setAttribute("sign",b);
            }else{
                request.setAttribute("sign",false);
            }
            List<GradeInfo> gList = this.gradeManager.getList(null, null);
            request.setAttribute("gradeList",gList );
            return new ModelAndView("/teachpaltform/task/teacher/dialog/select-resource");
        }else if(type.equals("3")){
            return new ModelAndView("/teachpaltform/task/teacher/dialog/select-ques");
        }else if(type.equals("2")){
            return new ModelAndView("/teachpaltform/task/teacher/dialog/childPage/element-detail");
        }else
            return new ModelAndView("/teachpaltform/task/teacher/dialog/select-ques");
    }



    /**
     * ��ȡ��ʦ�����б�
     * @throws Exception
     */
     @RequestMapping(params="m=ajaxTaskList",method=RequestMethod.POST)
     public void ajaxTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
         JsonEntity je = new JsonEntity();
         String courseid=request.getParameter("courseid");
         if(courseid==null||courseid.trim().length()<1){
             je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
             response.getWriter().print(je.toJSON());
             return;
         }
         PageResult p=this.getPageResultParameter(request);
         p.setOrderBy("u.order_idx,u.c_time ");
         TpTaskInfo t=new TpTaskInfo();
         t.setCourseid(Long.parseLong(courseid));
         //��ѯû����ɾ��������
         t.setSelecttype(1);
         t.setLoginuserid(this.logined(request).getUserid());
         t.setStatus(1);
        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, p);
         if(taskList!=null&&taskList.size()>0){
             for(TpTaskInfo task:taskList){
                 if(task.getTasktype().toString().equals("10")&&(task.getTaskstatus()!="3" && task.getTaskstatus()!="1")){
                     String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                     String lessionid=task.getTaskid().toString().replace("-","");
                     HashMap<String,String> signMap = new HashMap();
                     signMap.put("courseName",task.getCoursename().toString());
                     signMap.put("courseId",lessionid);
                     signMap.put("userId",this.logined(request).getUserid().toString());
                     signMap.put("userName",this.logined(request).getRealname());
                     signMap.put("rec","2");
                     signMap.put("srcId","90");
                     signMap.put("timestamp",System.currentTimeMillis()+"");
                     String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                     signMap.put("sign",signture);
                     JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                     int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                     if(type==1){
                         String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                         if(liveurl!=null&&liveurl.trim().length()>0)
                             task.setLiveaddress(java.net.URLDecoder.decode(liveurl,"UTF-8"));
                     }
                 }
             }
         }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��У��̨����ֱ�����б�ҳ��
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toLiveTaskList",method=RequestMethod.GET)
    public ModelAndView toLiveTaskPage(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String sign=request.getParameter("sign");
        String time=request.getParameter("time");
        if(sign==null||sign.trim().length()<1||time==null||time.length()<1){
            je.setMsg("Param Error!");
            response.getWriter().print(je.toJSON());
        }
        HashMap<String,String> map = new HashMap();
        map.put("time",time);
        Boolean b = UrlSigUtil.verifySigSimple("LiveTask",map,sign);
        if(!b){
            je.setMsg("Md5 Error!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        return new ModelAndView("/teachepaltform/teacher/live-task-list");
    }
    /**
     * ��ȡݼӢ��ֱ���������б�
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxLiveTaskList",method=RequestMethod.POST)
    public void ajaxLiveTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String eliteSchoolId=UtilTool.utilproperty.get("ELITE_SCHOOL_ID").toString();
        if(eliteSchoolId==null||eliteSchoolId.trim().length()<1){
            je.setMsg("ϵͳδ��ȡݼӢѧУ��ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy(" ta.b_time desc ");
        TpTaskInfo t=new TpTaskInfo();
        // t.setSchoolid(Integer.parseInt(eliteSchoolId));
        t.setTasktype(10);
        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskDetailList(t, p);
        if(taskList!=null&&taskList.size()>0){
//            for(TpTaskInfo task:taskList){
//                if(task.getTasktype().toString().equals("10")&&(task.getTaskstatus()!="3" && task.getTaskstatus()!="1")){
//                    String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
//                    String lessionid=task.getTaskid().toString().replace("-","");
//                    HashMap<String,String> signMap = new HashMap();
//                    signMap.put("courseName",task.getCoursename().toString());
//                    signMap.put("courseId",lessionid);
//                    signMap.put("userId","100");
//                    signMap.put("userName","luzhi");
//                    signMap.put("rec","2");
//                    signMap.put("srcId","90");
//                    signMap.put("timestamp",System.currentTimeMillis()+"");
//                    String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
//                    signMap.put("sign",signture);
//                    JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
//                    int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
//                    if(type==1){
//                        String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
//                        if(liveurl!=null&&liveurl.trim().length()>0)
//                            task.setLiveaddress(java.net.URLDecoder.decode(liveurl,"UTF-8"));
//                    }
//                }
//            }
        }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����ֱ��������
     * @throws Exception
     */
    @RequestMapping(params="m=GenerateLiveTaskUrl",method=RequestMethod.POST)
    public void getLiveTaskAddress(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid=request.getParameter("taskid");
        String name=request.getParameter("name");
        if(taskid==null||taskid.trim().length()<1||name==null||name.trim().length()<1){
            je.setMsg("�����쳣!");
            response.getWriter().print(je.toJSON());
            return;
        }

        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setTasktype(10);
        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskDetailList(t, null);
        if(taskList!=null&&taskList.size()>0){
            for(TpTaskInfo task:taskList){
                if(task.getTasktype().toString().equals("10")){
                    String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                    String lessionid=task.getTaskid().toString().replace("-","");
                    HashMap<String,String> signMap = new HashMap();
                    signMap.put("courseName",task.getCoursename().toString());
                    signMap.put("courseId",lessionid);
                    signMap.put("userId","100");
                    signMap.put("userName",name);
                    signMap.put("rec","1");
                    signMap.put("srcId","90");
                    signMap.put("timestamp",System.currentTimeMillis()+"");
                    String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                    signMap.put("sign",signture);
                    JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                    int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                    if(type==1){
                        String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                        if(liveurl!=null&&liveurl.trim().length()>0)
                            task.setLiveaddress(java.net.URLDecoder.decode(liveurl,"UTF-8"));
                    }
                }
            }
        }
        je.setObjList(taskList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��ȡ�����б�
     * @throws Exception
     */
    @RequestMapping(params="toQueryLiveList",method=RequestMethod.POST)
    public void toQueryLiveList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setTaskid(Long.parseLong(taskid));
        //��ѯû����ɾ��������
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, null);
        je.setObjList(taskList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * �޸���������
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdOrderIdx",method=RequestMethod.POST)
    public void doUpdOrderIdx(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid=request.getParameter("taskid");
        String courseid=request.getParameter("courseid");
        String orderix=request.getParameter("orderidx");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(orderix==null||orderix.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo tmpTask=new TpTaskInfo();
        tmpTask.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��������
        tmpTask.setSelecttype(1);
        tmpTask.setLoginuserid(this.logined(request).getUserid());
        tmpTask.setStatus(1);
        tmpTask.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>tmpTaskList=this.tpTaskManager.getTaskReleaseList(tmpTask,null);
        if(tmpTaskList==null||tmpTaskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        tmpTask=tmpTaskList.get(0);

        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��������
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);
        PageResult pageResult=new PageResult();
        pageResult.setOrderBy("u.order_idx desc");
        pageResult.setPageNo(0);
        pageResult.setPageSize(0);
        //1 2 3 4 5 6 7 8 9 10
        //�ѷ���������
        Integer descIdx=Integer.parseInt(orderix);
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t,pageResult);

        Integer objIdx=descIdx;
        //0�ǵ�������ȡ�������
        if(descIdx==0)
            descIdx=taskList.get(0).getOrderidx()+1;
        //descIdx=descIdx-1<0?1:descIdx-1;

        if(taskList!=null&&taskList.size()>0){
            if(tmpTask.getOrderidx()>descIdx){  //��7��3��
                for(TpTaskInfo task:taskList){
                    if(task.getOrderidx()>=descIdx&&task.getOrderidx()<tmpTask.getOrderidx()){
                        System.out.println("orderidx:" + task.getOrderidx());
                        TpTaskInfo upd=new TpTaskInfo();
                        upd.setTaskid(task.getTaskid());
                        upd.setOrderidx(task.getOrderidx()+1);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getUpdateSql(upd,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }

                    }
                }
            }else if(tmpTask.getOrderidx()<descIdx){ //��1��9��
                objIdx=objIdx-1<=0?1:objIdx-1;
                for(TpTaskInfo task:taskList){
                    if(task.getOrderidx()>tmpTask.getOrderidx()&&task.getOrderidx()<descIdx){
                        TpTaskInfo upd=new TpTaskInfo();
                        upd.setTaskid(task.getTaskid());
                        upd.setOrderidx(task.getOrderidx()-1);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getUpdateSql(upd,sql);
                        if(sql!=null&&objList!=null){
                            sqlListArray.add(sql.toString());
                            objListArray.add(objList);
                        }
                    }
                }
            }
        }

        TpTaskInfo upd=new TpTaskInfo();
        upd.setTaskid(tmpTask.getTaskid());
        if(orderix.equals("0"))
            upd.setOrderidx(descIdx-1<=0?1:descIdx-1);
        else
            upd.setOrderidx(objIdx);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getUpdateSql(upd,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        if(sqlListArray.size()>0&&objListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray,objListArray);
            if(flag){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ȡѧ�������б�
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxStuTaskList",method=RequestMethod.POST)
    public void ajaxStuTaskList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy(" unionflag desc,order_idx ");
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setUserid(this.logined(request).getUserid());
        // ѧ������
        List<TpTaskInfo>taskList=this.tpTaskManager.getUnionListbyStu(t, p);
        if(taskList!=null&&taskList.size()>0){
            for(TpTaskInfo task:taskList){
                if(task.getTasktype()==3){
                    QuestionOption questionOptions=new QuestionOption();
                    questionOptions.setQuestionid(task.getTaskvalueid());
                    PageResult pp = new PageResult();
                    pp.setOrderBy("u.option_type");
                    pp.setPageNo(0);
                    pp.setPageSize(0);
                    List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOptions,pp);
                    task.setQuestionOptionList(questionOptionList);

                    QuestionInfo q=new QuestionInfo();
                    q.setQuestionid(task.getTaskvalueid());
                    List<QuestionInfo>qList=this.questionManager.getList(q,null);
                    if(qList==null||qList.size()<1){
                        je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    task.setRightAnswerList(qList);

                    /*ѡ�����*/
                    if(qList.get(0).getQuestiontype()==3||qList.get(0).getQuestiontype()==4){
                        QuestionOption qo=new QuestionOption();
                        qo.setIsright(1);
                        qo.setQuestionid(q.getQuestionid());
                        List<QuestionOption>questionOptionList1=this.questionOptionManager.getList(qo,null);
                        task.setRightOptionAnswerList(questionOptionList1);
                    }
                }else if(task.getTasktype()==2){
                    TpTopicThemeInfo tt=new TpTopicThemeInfo();
                    tt.setTopicid(task.getTaskvalueid());
                    tt.setLoginuserref(this.logined(request).getRef());
                    tt.setCourseid(task.getCourseid());
                    List<TpTopicThemeInfo>tpTopicThemeInfoList=this.tpTopicThemeManager.getList(tt,null);
                    task.setTpTopicThemeInfoList(tpTopicThemeInfoList);
                }else if(task.getTasktype()==10){
                    if(task.getTaskstatus()!="3" && task.getTaskstatus()!="1"){
                        String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
                        String lessionid=task.getTaskid().toString().replace("-","");
                        HashMap<String,String> signMap = new HashMap();
                        signMap.put("courseName",task.getCoursename().toString());
                        signMap.put("courseId",lessionid);
                        signMap.put("userId",this.logined(request).getUserid().toString());
                        signMap.put("userName",this.logined(request).getRealname());
                        signMap.put("rec","3");
                        signMap.put("srcId","90");
                        signMap.put("timestamp",System.currentTimeMillis()+"");
                        String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
                        signMap.put("sign",signture);
                        JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
                        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
                        if(type==1){
                            String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
                            if(liveurl!=null&&liveurl.trim().length()>0)
                                task.setLiveaddress(liveurl);
                        }
                    }
                }
                //��ȡ�����¼���ĵ�
                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(task.getTaskid());
                qa.setUserid(this.logined(request).getRef());
                List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
                task.setQuestionAnswerList(qaList);

                //ѧϰʱ��
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(task.getTaskid());
                tp.setUserid(this.logined(request).getRef());
                tp.setCourseid(task.getCourseid());
                List<TaskPerformanceInfo>taskPerformanceInfoList=this.taskPerformanceManager.getList(tp,null);
                task.setTaskPerformanceList(taskPerformanceInfoList);
            }
        }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ȡδ�������ѧ���б�
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxNoCompleteTaskUser",method=RequestMethod.POST)
    public void ajaxNoCompleteTaskStuList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||
                taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setTaskid(Long.parseLong(taskid));
        t.setUserid(this.logined(request).getUserid());
        List<UserInfo>stuList=this.userManager.getUserNotCompleteTask(t.getTaskid(),this.logined(request).getUserid(),null,"1");
        je.setType("success");//
        je.setObjList(stuList);//
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ȡ�����б�
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxTaskBankList",method=RequestMethod.POST)
    public void ajaxTaskBankList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String cloudtype=request.getParameter("cloudtype");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("u.cloud_status desc,u.c_time desc");
        if(p.getPageNo()==0)
            p.setPageNo(1);
        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��,δ��������
        t.setSelecttype(2);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        if(tasktype!=null&&tasktype.length()>0){
            if(tasktype.indexOf("ques_")!=-1)
                t.setQuestiontype(tasktype.substring(tasktype.lastIndexOf("_") + 1));
            else{
                t.setTasktype(Integer.parseInt(tasktype));
            }
        }
        if(cloudtype!=null&&cloudtype.length()>0)
            t.setCloudtype(Integer.parseInt(cloudtype));


        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t, p);
        //�����µ�����
        if(taskList!=null&&taskList.size()>0){
            for(TpTaskInfo task :taskList){
                if(task.getTasktype()==3){
                    QuestionOption questionOptions=new QuestionOption();
                    questionOptions.setQuestionid(task.getTaskvalueid());
                    PageResult pp = new PageResult();
                    pp.setOrderBy("u.option_type");
                    pp.setPageNo(0);
                    pp.setPageSize(0);
                    List<QuestionOption>questionOptionList=this.questionOptionManager.getList(questionOptions,pp);
                    task.setQuestionOptionList(questionOptionList);
                }
            }
        }
        p.setList(taskList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * ɾ������
     * @throws Exception
     */
    @RequestMapping(params="m=doDelTask",method=RequestMethod.POST)
    public void doDelTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid=request.getParameter("taskid");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t, null);
        if(taskList==null||taskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;

        TpTaskInfo tmpTask=taskList.get(0);

        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(tmpTask.getCourseid());
        //��ѯû����ɾ��������
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);

        //1 2 3 4 5
        //�ѷ���������
        List<TpTaskInfo>taskReleaseList=this.tpTaskManager.getTaskReleaseList(sel, null);
        Integer orderIdx=tmpTask.getOrderidx();
        if(taskReleaseList!=null&&taskReleaseList.size()>0){
            for(TpTaskInfo task:taskReleaseList){
                if(task.getOrderidx()!=null){
                    if(task.getOrderidx()>orderIdx){
                        TpTaskInfo upd=new TpTaskInfo();
                        upd.setTaskid(task.getTaskid());
                        upd.setOrderidx(task.getOrderidx()-1);
                        sql=new StringBuilder();
                        objList=this.tpTaskManager.getUpdateSql(upd,sql);
                        if(sql!=null&&sql.toString().trim().length()>0){
                            objListArray.add(objList);
                            sqlStrList.add(sql.toString());
                        }
                    }
                }
            }
        }

        TpTaskAllotInfo ta=new TpTaskAllotInfo();
        ta.setTaskid(tmpTask.getTaskid());
        ta.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.tpTaskAllotManager.getDeleteSql(ta,sql);
        if(objList!=null&&sql!=null){
            sqlStrList.add(sql.toString());
            objListArray.add(objList);
        }
        QuestionAnswer qa=new QuestionAnswer();
        qa.setTaskid(tmpTask.getTaskid());
        qa.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.questionAnswerManager.getDeleteSql(qa,sql);
        if(objList!=null&&sql!=null){
            sqlStrList.add(sql.toString());
            objListArray.add(objList);
        }

        //���������֣�ɾ��������ɼ�¼
        sql=new StringBuilder();
        objList=this.tpTaskManager.getDelTpStuTaskScore(tmpTask.getTaskid(),sql);
        if(objList!=null&&sql!=null){
            sqlStrList.add(sql.toString());
            objListArray.add(objList);
        }

        if(tmpTask.getTaskid()>0){
            /*TpOperateInfo to=new TpOperateInfo();
            to.setRef(this.tpOperateManager.getNextId(true));
            to.setCuserid(this.logined(request).getUserid());
            to.setCourseid(tmpTask.getCourseid());
            to.setTargetid(tmpTask.getTaskid());             //�½�����ID
            to.setOperatetype(1);              //ɾ��
            to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
            sql=new StringBuilder();
            objList=this.tpOperateManager.getSaveSql(to,sql);
            if(objList!=null&&sql!=null){
                sqlStrList.add(sql.toString());
                objListArray.add(objList);
            }

            //������־
            sql=new StringBuilder();
            objList=this.tpOperateManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO", to.getTargetid().toString(),null,null,"DELETE"
                    ,"���Ӳ�����¼",sql);
            if(sql!=null&&sql.toString().trim().length()>0){
                objListArray.add(objList);
                sqlStrList.add(sql.toString());
            }*/
        }else{
            TpTaskInfo del=new TpTaskInfo();
            del.setTaskid(tmpTask.getTaskid());
            //t.setStatus(2); //�޸ı���״̬Ϊ��ɾ��
            //t.setOrderidx(-1);
            sql=new StringBuilder();
            objList=this.tpTaskManager.getDeleteSql(del, sql);
            if(sql!=null&&sql.toString().trim().length()>0){
                objListArray.add(objList);
                sqlStrList.add(sql.toString());
            }
        }
        //������־
       /* sql=new StringBuilder();
        objList=this.tpOperateManager.getAddOperateLog(this.logined(request).getRef(),"TP_TASK_INFO", t.getTaskid().toString(),null,null,"UPDATE"
                ,"�޸ı���״̬",sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sql.toString());
        })*/

        if(sqlStrList.size()>0&&objListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlStrList,objListArray);
            if(flag){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * �������
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toAddTask",method=RequestMethod.GET)
    public ModelAndView toAddTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("����,ϵͳδ��ȡ�������ʶ!");
            je.getAlertMsgAndBack();
            return null;
        }
        //��������
        List<DictionaryInfo>tasktypeList=this.dictionaryManager.getDictionaryByType("TP_TASK_TYPE");
        //��ÿ�������İ༶
        TpCourseClass c=new TpCourseClass();
        c.setCourseid(Long.parseLong(courseid));
        List<TpCourseClass>courseclassList=this.tpCourseClassManager.getList(c, null);
        if(courseclassList==null||courseclassList.size()<1){
            je.setMsg("����δ��ȡ���ÿ���İ༶��Ϣ!�����ú��������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }else {
            if(this.logined(request).getDcschoolid()!=1){
                for(TpCourseClass cc:courseclassList){
                    if(cc.getDctype()!=null&&cc.getDctype()==2){
                        //request.setAttribute("isWXCLS",1);
                        je.setMsg("��ǰ�༶����Ϊ��У�༶����֧���ڵ������������!");
                        response.getWriter().print(je.getAlertMsgAndBack());
                        return null;
                    }
                }
            }
        }

        String subjectid=null,gradeid=null;

        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            subjectid=materialList.get(0).getSubjectid().toString();
            //gradeid=materialList.get(0).getGradeid().toString();
        }

        //΢��Ƶ
        PageResult pageResult=new PageResult();
        pageResult.setPageSize(0);
        pageResult.setPageNo(0);
        pageResult.setOrderBy(" aa.diff_type desc ");
        TpCourseResource tr=new TpCourseResource();
        tr.setCourseid(Long.parseLong(courseid));
        //tr.setTaskflag(1);
        //tr.setDifftype(1);
        //tr.setHaspaper(1);
        tr.setFilesuffixname(".mp4");
        List<TpCourseResource>micList=this.tpCourseResourceManager.getList(tr,pageResult);
        if(micList!=null&&micList.size()>0)
            mp.put("hasVideo",1);
        //�Ծ�
        TpCoursePaper cp=new TpCoursePaper();
        cp.setCourseid(Long.parseLong(courseid));
        List<TpCoursePaper>paperList=this.tpCoursePaperManager.getList(cp,null);
        if(paperList!=null&&paperList.size()>0)
            mp.put("hasPaper",1);
        //����
        TpCourseQuestion cq=new TpCourseQuestion();
        cq.setCourseid(Long.parseLong(courseid));
        List<TpCourseQuestion>questionList=this.tpCourseQuestionManager.getList(cq,null);
        if(questionList!=null&&questionList.size()>0)
            mp.put("hasQuestion",1);

		TpGroupInfo g=new TpGroupInfo();
		//g.setCuserid(this.logined(request).getUserid());
        g.setTermid(courseclassList.get(0).getTermid());
        g.setSubjectid(Integer.parseInt(subjectid));
        List<TpGroupInfo>groupList=this.tpGroupManager.getList(g, null);
        mp.put("tasktypeList", tasktypeList);
        mp.put("courseclassList",courseclassList);
        mp.put("groupList", groupList);
        mp.put("termid", termid);
        mp.put("gradeid", request.getSession().getAttribute("session_grade"));
        mp.put("subjectid",request.getSession().getAttribute("session_subject"));
        mp.put("materialid",request.getSession().getAttribute("session_material"));
        TpCourseQuestion tcq=new TpCourseQuestion();
        tcq.setCourseid(Long.parseLong(courseid));
        Integer objectiveQuesCount=this.tpCourseQuestionManager.getObjectiveQuesCount(tcq);
        mp.put("objectiveQuesCount", objectiveQuesCount);   //ר���¿͹�������
        return new ModelAndView("/teachpaltform/task/teacher/task-add",mp);
    }

    /**
     * ���񣬷�����������ʱ��ѡ��
     *      author :zhengzhou
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toSelTopicForTask",method=RequestMethod.GET)
    public ModelAndView toSelTopicForTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
        return  new ModelAndView("/teachpaltform/task/teacher/dialog/select-topic");
    }
    /**
     * ���񣬷�����������ʱ��ѡ��
     *      author :zhengzhou
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toSelMicForTask",method=RequestMethod.GET)
    public ModelAndView toSelMicForTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
        return  new ModelAndView("/teachpaltform/task/teacher/dialog/select-mic");
    }

    /**
     * ��ȡ��������
     * @throws Exception
     */
    @RequestMapping(params="toQryQuestionType",method=RequestMethod.POST)
    public void toQryQuestionType(HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        List<DictionaryInfo>questiontypeList=this.dictionaryManager.getDictionaryByType("TP_QUESTION_TYPE");
        je.setObjList(questiontypeList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * �������
     * @throws Exception
     */
    @RequestMapping(params="doSubAddTask",method=RequestMethod.POST)
    public void doSubAddTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String tasktype=request.getParameter("tasktype");
        String taskname=request.getParameter("taskname");
        String taskvalueid=request.getParameter("taskvalueid");
        String taskremark=request.getParameter("taskremark");
        String quesnum=request.getParameter("quesnum");
        String clstype=request.getParameter("clstype");
        String micpaperid=request.getParameter("paperid");
        if(tasktype==null||tasktype.trim().length()<1
            //||StringUtils.isBlank(taskname
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String courseid=request.getParameter("courseid");
        String questype=request.getParameter("questype");
        String[]criteriaArray=request.getParameterValues("taskcri");
        //С��
        String[]groupArray=request.getParameterValues("groupArray");
        //�༶
        String[]clsArray=request.getParameterValues("clsArray");
        String[]bClsArray=request.getParameterValues("btimeClsArray");
        String[]eClsArray=request.getParameterValues("etimeClsArray");

        if(bClsArray==null||eClsArray==null){
            je.setMsg("δ��ȡ������ʱ��!");
            response.getWriter().print(je.toJSON());
            return;
        }

        if(StringUtils.isBlank(courseid)){
            je.setMsg("δ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if((groupArray==null||groupArray.length<1)
                &&(clsArray==null||clsArray.length<1)){
            je.setMsg("δ��ȡ���������!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(!(StringUtils.isNotBlank(questype)&&questype.equals("5"))&&criteriaArray.length<1){
            je.setMsg("δ��ȡ��������ɱ�׼!");
            response.getWriter().print(je.toJSON());
            return;
        }
        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        TpTaskInfo ta=new TpTaskInfo();


        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseInfo tmpCourse=courseList.get(0);


        //�κ���ҵ 3
        if(tasktype.toString().equals("3")){
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ�������ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseQuestion q=new TpCourseQuestion();
            q.setQuestionid(Long.parseLong(taskvalueid));
            List<TpCourseQuestion>quesList=this.tpCourseQuestionManager.getList(q,null);
            if(quesList==null||quesList.size()<1){
                je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ��!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("2")){//����
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ�������ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpTopicInfo i=new TpTopicInfo();
            i.setTopicid(Long.parseLong(taskvalueid));
            List<TpTopicInfo>iList=this.tpTopicManager.getList(i, null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("1")){//��Դ
            String resourcetype=request.getParameter("resourcetype");
            resourcetype=resourcetype==null||resourcetype.length()<1?"1":resourcetype;
            if(resourcetype.equals("1")){
                if(taskvalueid==null||taskvalueid.trim().length()<1){
                    je.setMsg("����ϵͳδ��ȡ����Դ��ʶ!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                TpCourseResource t=new TpCourseResource();
                t.setResid(Long.parseLong(taskvalueid));
                List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, null);
                if(resList==null||resList.size()<1){
                    je.setMsg("��ʾ����ǰ��Դ�Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(1);


                TpCourseResource tt=new TpCourseResource();
                tt.setCourseid(Long.parseLong(courseid));
                tt.setResid(Long.parseLong(taskvalueid));
                List<TpCourseResource>tpCourseResourceList=this.tpCourseResourceManager.getList(tt,null);
                if(tpCourseResourceList==null||tpCourseResourceList.size()<1){
                    je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                    response.getWriter().print(je.toJSON());
                    return;
                }
                if(tpCourseResourceList.get(0).getResourcetype().equals(2)){
                    tt.setRef(tpCourseResourceList.get(0).getRef());
                    tt.setResourcetype(1);
                    sql=new StringBuilder();
                    objList=this.tpCourseResourceManager.getUpdateSql(tt,sql);
                    if(objList!=null&&sql!=null&&sql.length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }
            }else{
                String remotetype=request.getParameter("remotetype");
                String resourcename=request.getParameter("resourcename");
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(2);
                ta.setRemotetype(Integer.parseInt(remotetype));
                ta.setResourcename(resourcename);
            }
        }else if(tasktype.toString().equals("4")){//�ɾ����
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ���Ծ��ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCoursePaper tpCoursePaper=new TpCoursePaper();
            tpCoursePaper.setPaperid(Long.parseLong(taskvalueid));
            List<TpCoursePaper>iList=this.tpCoursePaperManager.getList(tpCoursePaper,null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ�Ծ��Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("5")){//��������
            //����Ծ�
            TpCourseQuestion cq=new TpCourseQuestion();
            cq.setCourseid(Long.parseLong(courseid));
            List<TpCourseQuestion>courseQuestionList=this.tpCourseQuestionManager.getList(cq,null);
            if(courseQuestionList==null||courseQuestionList.size()<1){
                je.setMsg("��ǰר���������⣬�޷����!������������Ӹ�����!");
                response.getWriter().print(je.toJSON());
                return;
            }

            Long paperid=this.paperManager.getNextId(true);
            PaperInfo p=new PaperInfo();
            p.setPaperid(paperid);
            p.setPapername("��������");
            p.setCuserid(this.logined(request).getUserid());
            p.setPapertype(PaperInfo.PAPER_TYPE.FREE.getValue());
            sql=new StringBuilder();
            objList=this.paperManager.getSaveSql(p,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }

            //���������ר�����
            TpCoursePaper tpCoursePaper=new TpCoursePaper();
            tpCoursePaper.setPaperid(paperid);
            tpCoursePaper.setCourseid(Long.parseLong(courseid));
            sql=new StringBuilder();
            objList=this.tpCoursePaperManager.getSaveSql(tpCoursePaper,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
            ta.setTaskvalueid(paperid);
        }else if(tasktype.toString().equals("6")){
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("ϵͳδ��ȡ��΢��Ƶ��ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            if(micpaperid==null||micpaperid.trim().length()<1){
                je.setMsg("ϵͳδ��ȡ��΢��Ƶ�����Ծ�!");
                response.getWriter().print(je.toJSON());
                return;
            }

            TpCourseResource tpCourseResource=new TpCourseResource();
            tpCourseResource.setResid(Long.parseLong(taskvalueid));
            tpCourseResource.setCourseid(Long.parseLong(courseid));
            List<TpCourseResource>iList=this.tpCourseResourceManager.getList(tpCourseResource,null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ��Դ�Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            PaperInfo paperInfo=new PaperInfo();
            paperInfo.setPaperid(Long.parseLong(micpaperid));
            List<PaperInfo>paperList=this.paperManager.getList(paperInfo,null);
            if(paperList==null||paperList.size()<1){
                je.setMsg("��ʾ����ǰ�Ծ��Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
            ta.setPaperid(Long.parseLong(micpaperid));
        }else if(tasktype.toString().equals("10")){//ֱ����
            if(taskname==null||taskname.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ��ֱ������!");
                response.getWriter().print(je.toJSON());
                return;
            }


            for(int i=0;i<bClsArray.length;i++){
                TpTaskAllotInfo liveAllot=new TpTaskAllotInfo();
                liveAllot.setCourseid(Long.parseLong(courseid));
                liveAllot.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                liveAllot.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                liveAllot.setTasktype(10);
                List<TpTaskAllotInfo>allotInfoList=this.tpTaskAllotManager.getTaskRemindObjList(liveAllot,null);
                if(allotInfoList!=null&&allotInfoList.size()>0){
                    je.setMsg("��ʾ�������õ�ʱ��������ֱ�������������ʱ��! \n\nʱ�䣺"+bClsArray[i]+"~"+eClsArray[i]+"");
                    response.getWriter().print(je.toJSON());
                    return;
                }
            }

            ta.setTaskvalueid(Long.valueOf(1));
            ta.setTaskname(taskname);
        }
        /**
         *��ѯ����ǰר����Ч�����������������
         */

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��������
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, null);
        Integer orderIdx=1;
        if(taskList!=null&&taskList.size()>0)
            orderIdx+=taskList.size();

        //�������
        Long tasknextid=this.tpTaskManager.getNextId(true);
        ta.setTaskid(tasknextid);
        //ta.setTaskname(taskname);
        ta.setTasktype(Integer.parseInt(tasktype));
        ta.setCourseid(Long.parseLong(courseid));
        ta.setCuserid(this.logined(request).getRef());
        ta.setCriteria(Integer.parseInt(criteriaArray[0]));
        ta.setOrderidx(orderIdx);
        if(taskremark!=null)
            ta.setTaskremark(taskremark);
        if(quesnum!=null&&quesnum.trim().length()>0)
            ta.setQuesnum(Integer.parseInt(quesnum));
        else
            ta.setQuesnum(10);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getSaveSql(ta, sql);
        if(objList!=null&&sql!=null&&sql.length()>0){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }

        //���������� С��
        if(groupArray!=null&&groupArray.length>0){
            for (int i=0;i<groupArray.length;i++) {
                /**
                 * * ����Ľ���ʱ�� Ҫ���� ר��Ŀ�ʼʱ�䡣��Ҳ����˵����Ŀ�ʼ�ͽ���ʱ�䶼Ҫ����ר��Ŀ�ʼʱ�䣩
                 * ����Ŀ�ʼʱ��Ĭ��Ϊ��ǰʱ�䣬����Ľ���ʱ��Ĭ��Ϊר��Ŀ�ʼʱ�䡣
                 */
                TpGroupInfo groupInfo=new TpGroupInfo();
                groupInfo.setGroupid(Long.parseLong(groupArray[i]));
                List<TpGroupInfo>groupInfos=this.tpGroupManager.getList(groupInfo,null);
                if(groupInfos!=null&&groupInfos.size()>0){
                    //ר��ʱ��
                    TpCourseClass tpCourseClass=new TpCourseClass();
                    tpCourseClass.setClassid(groupInfos.get(0).getClassid());
                    tpCourseClass.setCourseid(Long.parseLong(courseid));
                    tpCourseClass.setSubjectid(groupInfos.get(0).getSubjectid());
                    List<TpCourseClass>courseClsList=this.tpCourseClassManager.getList(tpCourseClass,null);
                    if(courseClsList==null||courseClsList.size()<1){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    TpCourseClass tmpCourseClass=courseClsList.get(0);

                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(groupInfos.get(0).getClassid());
                    List<ClassInfo>clsList=this.classManager.getList(cls,null);
                    if(clsList!=null&&clsList.size()>0){
                        ClassInfo tmpCls=clsList.get(0);
                        if(tmpCls!=null&&tmpCls.getDctype()==3){
                            if(UtilTool.StringConvertToDate(eClsArray[i]).after(tmpCourseClass.getBegintime())){
                                je.setMsg("��ѧ���õ�����Ľ���ʱ��������ר��Ŀ�ʼʱ��!\n\n��ʾ������������"+groupInfos.get(0).getClassname()+groupInfos.get(0).getGroupname()+"����ʱ��!");
                                response.getWriter().print(je.toJSON());
                                return;
                            }
                        }
                    }
                }


                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                tal.setTaskid(tasknextid);
                tal.setUsertype(2);
                tal.setUsertypeid(Long.parseLong(groupArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }
        //���������� �༶
        if(clsArray!=null&&clsArray.length>0){
            if(clstype==null||clstype.trim().length()<1){
                je.setMsg("����!ϵͳδ��ȡ���༶����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]classTypeArray=clstype.split(",");
            for (int i=0;i<clsArray.length;i++) {
                /**
                 * ����Ľ���ʱ�� Ҫ���� ר��Ŀ�ʼʱ�䡣��Ҳ����˵����Ŀ�ʼ�ͽ���ʱ�䶼Ҫ����ר��Ŀ�ʼʱ�䣩
                 * ����Ŀ�ʼʱ��Ĭ��Ϊ��ǰʱ�䣬����Ľ���ʱ��Ĭ��Ϊר��Ŀ�ʼʱ�䡣
                 */
                ClassUser classUser=new ClassUser();
                classUser.setClassid(Integer.parseInt(clsArray[i]));
                classUser.setRelationtype("�ο���ʦ");
                classUser.setUserid(this.logined(request).getRef());
                Object subjectid=request.getSession().getAttribute("session_subject");
                if(subjectid!=null&&subjectid.toString().length()>0)
                    classUser.setSubjectid(Integer.parseInt(subjectid.toString()));
                List<ClassUser>clsUserList=this.classUserManager.getList(classUser,null);
                if(clsUserList!=null&&clsUserList.size()>0){
                    //ר��ʱ��
                    TpCourseClass tpCourseClass=new TpCourseClass();
                    tpCourseClass.setClassid(Integer.parseInt(clsArray[i]));
                    tpCourseClass.setCourseid(Long.parseLong(courseid));
                    tpCourseClass.setSubjectid(clsUserList.get(0).getSubjectid());
                    List<TpCourseClass>courseClsList=this.tpCourseClassManager.getList(tpCourseClass,null);
                    if(courseClsList==null||courseClsList.size()<1){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    TpCourseClass tmpCourseClass=courseClsList.get(0);

                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(cls,null);
                    if(clsList!=null&&clsList.size()>0){
                        ClassInfo tmpCls=clsList.get(0);
                        if(tmpCls!=null&&tmpCls.getDctype()==3){
                            if(UtilTool.StringConvertToDate(eClsArray[i]).after(tmpCourseClass.getBegintime())){
                                je.setMsg("��ѧ���õ�����Ľ���ʱ��������ר��Ŀ�ʼʱ��!\n\n��ʾ������������"+tmpCls.getClassgrade()+tmpCls.getClassname()+"����ʱ��!");
                                response.getWriter().print(je.toJSON());
                                return;
                            }
                        }
                    }
                }




                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //��֤�༶����
                if(classTypeArray[i].equals("1")){
                    /*ClassInfo c=new ClassInfo();
                    c.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(c,null);*/
                    tal.setUsertype(0);
                }else if(classTypeArray[i].equals("2")){
                    /*TpVirtualClassInfo tv=new TpVirtualClassInfo();
                    tv.setVirtualclassid(Integer.parseInt(clsArray[i]));
                    List<TpVirtualClassInfo>vclsList=this.tpVirtualClassManager.getList(tv,null);*/
                    tal.setUsertype(1);
                }else{
                    je.setMsg("����!����༶��Ч!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                tal.setTaskid(tasknextid);
                tal.setUsertypeid(Long.parseLong(clsArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }

        if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(tasknextid);
                to.setOperatetype(2);                                                              //���
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }





        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                objListArray=new ArrayList<List<Object>>();
                sqlListArray=new ArrayList<String>();
                //���������Ϣ����
              /*  List<UserInfo>taskUserList=this.userManager.getUserNotCompleteTask(ta,null);
                if(taskUserList!=null&&taskUserList.size()>0){
                    for (UserInfo u:taskUserList){
                        TpTaskInfo dynamicTask=new TpTaskInfo();
                        dynamicTask.setCourseid(ta.getCourseid());
                        dynamicTask.setTaskid(ta.getTaskid());
                        dynamicTask.setCuserid(u.getRef());
                        if(this.tpTaskManager.doSaveTaskMsg(dynamicTask))
                            System.out.println("doSaveTaskMsg SUCCESS!");
                        else
                            System.out.println("doSaveTaskMsg ERROR!");
                    }
                }else{
                    System.out.println("�������̬ʧ��!ԭ��δ��ȡ��ѧ���б�!");
                }*/
                if(!sendRemind(tasknextid))
                    System.out.println(" doSubAddTask ���taskRemindʧ��! Taskid"+tasknextid);
                else
                    System.out.println(" doSubAddTask ���taskRemind�ɹ�! Taskid"+tasknextid);

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }

    protected boolean sendRemind(Long tasknextid){
        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setSelecttype(TpTaskInfo.QUERY_TYPE.������ʼ.getValue());
        taskInfo.setTaskid(tasknextid);
        List<TpTaskInfo>taskRemindList=tpTaskManager.getTaskRemindList(taskInfo,null);
        if(taskRemindList!=null&&taskRemindList.size()>0){
            //��ϢMap
            List<Map<String,Object>>mapList=new ArrayList<Map<String, Object>>();

            //��ȡ�������
            for(TpTaskInfo task:taskRemindList){
                TpTaskAllotInfo allotInfo=new TpTaskAllotInfo();
                allotInfo.setTaskid(task.getTaskid());
                List<TpTaskAllotInfo>taskAllotList=tpTaskAllotManager.getList(allotInfo,null);

                if(taskAllotList!=null&&taskAllotList.size()>0){
                    for(TpTaskAllotInfo tt:taskAllotList){
                        Map<String,Object>map=new HashMap<String, Object>();
                        map.put("taskId",task.getTaskid());
                        Object taskObjName=task.getTaskobjnameremind()==null?"":task.getTaskobjnameremind();
                        map.put("content",UtilTool.ecode(task.getRealname()+"��ʦ������ȥ��ɡ����� "+task.getOrderidx()+" "+task.getTaskTypeName()+" "+taskObjName+"��"));
                        map.put("classId",tt.getAllotid());
                        map.put("taskType",task.getTasktype());
                        map.put("isVirtual","0");
                        map.put("classType",tt.getClasstype());
                        if(tt.getDcschoolid()==null||task.getEttuserid()==null||tt.getDcschoolid().toString().length()<1)
                            continue;
                        map.put("cUserId",task.getEttuserid());
                        map.put("schoolId",tt.getDcschoolid());
                        map.put("userType","3");
                        List<Integer> userId=new ArrayList<Integer>();
                        if(tt.getUsertype()==0){
                            ClassUser cu=new ClassUser();
                            cu.setClassid(Integer.parseInt(tt.getUsertypeid()+""));
                            cu.setRelationtype("ѧ��");
                            List<ClassUser>userList=classUserManager.getList(cu,null);
                            if(userList!=null&&userList.size()>0){
                                for(ClassUser classUser:userList){
                                    if(classUser.getEttuserid()!=null&&classUser.getEttuserid()>0)
                                        userId.add(classUser.getEttuserid());
                                }
                            }
                        }else if(tt.getUsertype()==2){
                            TpGroupStudent gs=new TpGroupStudent();
                            gs.setGroupid(tt.getUsertypeid());
                            List<TpGroupStudent>gsList=tpGroupStudentManager.getList(gs,null);
                            if(gsList!=null&&gsList.size()>0){
                                for(TpGroupStudent groupStudent:gsList){
                                    if(groupStudent.getEttuserid()!=null&&groupStudent.getEttuserid()>0)
                                        userId.add(groupStudent.getEttuserid());
                                }
                            }
                        }
                        if(userId==null||userId.size()<1)
                            continue;
                        String tmpIds="";
                        for(Integer uid:userId){
                            if(tmpIds.length()>0)
                                tmpIds+=",";
                            tmpIds+=uid.toString();
                        }
                        map.put("userIds",tmpIds);
                        mapList.add(map);


                        TpTaskAllotInfo upd=new TpTaskAllotInfo();
                        upd.setRef(tt.getRef());
                        upd.setRemindstatus(1);
                        sql=new StringBuilder();
                        objList=tpTaskAllotManager.getUpdateSql(upd,sql);
                        if(objList!=null&&sql!=null){
                            objListArray.add(objList);
                            sqlListArray.add(sql.toString());
                        }
                    }

                }
            }

            //����У��������
            if(!sendTaskRemindObj(mapList)){
                System.out.println("TaskController sendTaskRemind: error!");
                return false;
            }else{
                if(objListArray.size()>0&&sqlListArray.size()>0){
                    if(tpTaskManager.doExcetueArrayProc(sqlListArray,objListArray)){
                        System.out.println("TaskController sendTaskRemind�޸���������״̬�ɹ�!");
                        return true;
                    }else
                        System.out.println("TaskController sendTaskRemind�޸���������״̬ʧ��!");
                }
            }
        }else{
            System.out.println("TaskController sendTaskRemind TaskList.size():"+taskRemindList.size());
        }
        return false;
    }

    /**
     * ͨ����Դϵͳ
     * �����Դ����
     * @throws Exception
     */
    @RequestMapping(params="addCourseResTask",method=RequestMethod.POST)
    public void addCourseResTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=this.doAddCourseResTask(request,response);
        response.getWriter().print(je.toJSON());
    }




    protected JsonEntity doAddCourseResTask(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity je=new JsonEntity();
        String tasktype="1";
        String courseid=request.getParameter("courseid");
        String taskvalueid=request.getParameter("resid");
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return je;
        }
        if(StringUtils.isBlank(courseid)){
            je.setMsg("δ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return je;
        }
        //��ÿ�������İ༶
        TpCourseClass c=new TpCourseClass();
        c.setCourseid(Long.parseLong(courseid));
        List<TpCourseClass>courseclassList=this.tpCourseClassManager.getList(c, null);
        if(courseclassList==null||courseclassList.size()<1){
            je.setMsg("δ��ȡ���ÿ���İ༶��Ϣ!�����ú��������!");
            response.getWriter().print(je.toJSON());
            return je;
        }else {
            if(this.logined(request).getDcschoolid()!=1){
                for(TpCourseClass cc:courseclassList){
                    if(cc.getDctype()!=null&&cc.getDctype()==2){
                        je.setMsg("��ǰר���°�����У�༶����֧���ڵ������������!");
                        response.getWriter().print(je.toJSON());
                        return je;
                    }
                }
            }
        }

        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        TpTaskInfo ta=new TpTaskInfo();


        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>courseList=this.tpCourseManager.getList(courseInfo,null);
        if(courseList==null||courseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return je;
        }
        TpCourseInfo tmpCourse=courseList.get(0);


        if(tasktype.toString().equals("1")){//��Դ
            String resourcetype=request.getParameter("resourcetype");
            resourcetype=resourcetype==null||resourcetype.length()<1?"1":resourcetype;
            if(resourcetype.equals("1")){
                if(taskvalueid==null||taskvalueid.trim().length()<1){
                    je.setMsg("ϵͳδ��ȡ����Դ��ʶ!");
                    response.getWriter().print(je.toJSON());
                    return je;
                }
                //���ר����Դ
                TpCourseResource t=new TpCourseResource();
                t.setResid(Long.parseLong(taskvalueid));
                t.setCourseid(Long.parseLong(courseid));
                List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, null);
                if(resList==null||resList.size()<1){
                    TpCourseResource cr=new TpCourseResource();
                    cr.setCourseid(Long.parseLong(courseid));
                    cr.setResid(Long.parseLong(taskvalueid));
                    cr.setResourcetype(1);
                    cr.setRef(this.tpCourseResourceManager.getNextId(true));
                    sql=new StringBuilder();
                    objList=this.tpCourseResourceManager.getSaveSql(cr,sql);
                    if(objList!=null&&sql!=null&&sql.length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }else{
                    je.setMsg("��ǰר������Ӹ���Դ!");
                    response.getWriter().print(je.toJSON());
                    return je;
                }
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(1);
            }else{
                String remotetype=request.getParameter("remotetype");
                String resourcename=request.getParameter("resourcename");
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(2);
                ta.setRemotetype(Integer.parseInt(remotetype));
                ta.setResourcename(resourcename);
            }
        }
        /**
         *��ѯ����ǰר����Ч�����������������
         */

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��������
        t.setSelecttype(1);
        t.setLoginuserid(this.logined(request).getUserid());
        t.setStatus(1);

        //�ѷ���������
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(t, null);
        Integer orderIdx=1;
        if(taskList!=null&&taskList.size()>0)
            orderIdx+=taskList.size();

        //�������
        Long tasknextid=this.tpTaskManager.getNextId(true);
        ta.setTaskid(tasknextid);
        ta.setTasktype(Integer.parseInt(tasktype));
        ta.setCourseid(Long.parseLong(courseid));
        ta.setCuserid(this.logined(request).getRef());
        ta.setCriteria(2);
        ta.setOrderidx(orderIdx);

        sql=new StringBuilder();
        objList=this.tpTaskManager.getSaveSql(ta, sql);
        if(objList!=null&&sql!=null&&sql.length()>0){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }

        //���������� �༶
        if(courseclassList!=null&&courseclassList.size()>0){

            for (TpCourseClass courseClass:courseclassList) {
                /**
                 * ����Ľ���ʱ�� Ҫ���� ר��Ŀ�ʼʱ�䡣��Ҳ����˵����Ŀ�ʼ�ͽ���ʱ�䶼Ҫ����ר��Ŀ�ʼʱ�䣩
                 * ����Ŀ�ʼʱ��Ĭ��Ϊ��ǰʱ�䣬����Ľ���ʱ��Ĭ��Ϊר��Ŀ�ʼʱ�䡣
                 */
                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //��֤�༶����
                if(courseClass.getClasstype()==1){
                    tal.setUsertype(0);
                }else if(courseClass.getClasstype()==2){
                    tal.setUsertype(1);
                }else{
                    je.setMsg("����!����༶��Ч!");
                    response.getWriter().print(je.toJSON());
                    return je;
                }
                tal.setTaskid(tasknextid);
                tal.setUsertypeid(Long.parseLong(courseClass.getClassid().toString()));
                tal.setBtime(new Date());
                if(courseClass.getDctype()!=null&&courseClass.getDctype().toString().equals("3"))
                    tal.setEtime(courseClass.getBegintime());
                else
                    tal.setEtime(courseClass.getEndtime());
                tal.setCuserid(this.logined(request).getRef());
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }

        if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(tasknextid);
                to.setOperatetype(2);                                                              //���
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }





        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                objListArray=new ArrayList<List<Object>>();
                sqlListArray=new ArrayList<String>();
                //���������Ϣ����
              /*  List<UserInfo>taskUserList=this.userManager.getUserNotCompleteTask(ta,null);
                if(taskUserList!=null&&taskUserList.size()>0){
                    for (UserInfo u:taskUserList){
                        TpTaskInfo dynamicTask=new TpTaskInfo();
                        dynamicTask.setCourseid(ta.getCourseid());
                        dynamicTask.setTaskid(ta.getTaskid());
                        dynamicTask.setCuserid(u.getRef());
                        if(this.tpTaskManager.doSaveTaskMsg(dynamicTask))
                            System.out.println("doSaveTaskMsg SUCCESS!");
                        else
                            System.out.println("doSaveTaskMsg ERROR!");
                    }
                }else{
                    System.out.println("�������̬ʧ��!ԭ��δ��ȡ��ѧ���б�!");
                }*/
                if(!sendRemind(tasknextid))
                    System.out.println(" addCourseResTask ���taskRemindʧ��!");
                else
                    System.out.println(" addCourseResTask ���taskRemind�ɹ�!");

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("���Ĳ���û��ִ��!");
        }
        return je;
    }



    /**
     * ���������޸�ҳ
     * @return
     * @throws Exception
     */
    @RequestMapping(params="doUpdTask",method=RequestMethod.GET)
    public ModelAndView doUpdTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();//
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("����,ϵͳδ��ȡ�������ʶ!");
            je.getAlertMsgAndBack();
            return null;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg("����,ϵͳδ��ȡ�������ʶ!");
            je.getAlertMsgAndBack();
            return null;
        }
        //��������
        List<DictionaryInfo>tasktypeList=this.dictionaryManager.getDictionaryByType("TP_TASK_TYPE");
        //��ÿ�������İ༶
        TpCourseClass c=new TpCourseClass();
        c.setCourseid(Long.parseLong(courseid));
        List<TpCourseClass>courseclassList=this.tpCourseClassManager.getList(c, null);
        if(courseclassList==null||courseclassList.size()<1){
            je.setMsg("����δ��ȡ���ÿ���İ༶��Ϣ!�����ú��������!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        //��ȡ��ǰר��̲�
        String subjectid=null;
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0){
            subjectid=materialList.get(0).getSubjectid().toString();
            //gradeid=materialList.get(0).getGradeid().toString();
        }

        //С��
        TpGroupInfo g=new TpGroupInfo();
        //g.setCuserid(this.logined(request).getUserid());
        g.setSubjectid(Integer.parseInt(subjectid));
        g.setTermid(courseclassList.get(0).getTermid());
        g.setTaskid(Long.parseLong(taskid));
        List<TpGroupInfo>groupList=this.tpGroupManager.getList(g, null);
		request.setAttribute("tasktypeList", tasktypeList);
		request.setAttribute("courseclassList", courseclassList);
		request.setAttribute("groupList", groupList);


        //�����������õ������Ϣ
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCourseid(Long.parseLong(courseid));
        List<TpTaskInfo> tpList=this.tpTaskManager.getList(t, null);
        if(tpList==null||tpList.size()<1){
            je.setMsg("��Ǹ�������Ѳ�����!");
            je.getAlertMsgAndBack();
            return null;
        }
        //����
        TpTaskInfo taskinfo=tpList.get(0);
        //�������
        TpTaskAllotInfo tg=new TpTaskAllotInfo();
        tg.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo>taskgroupList=this.tpTaskAllotManager.getList(tg, null);


        request.setAttribute("taskInfo", taskinfo);
        request.setAttribute("taskgroupList", taskgroupList);
        request.setAttribute("courseid", courseid);
        request.setAttribute("taskid", taskid);
        request.setAttribute("gradeid", request.getSession().getAttribute("session_grade"));
        request.setAttribute("subjectid",request.getSession().getAttribute("session_subject"));
        request.setAttribute("materialid",request.getSession().getAttribute("session_material"));
        TpCourseQuestion cq=new TpCourseQuestion();
        cq.setCourseid(Long.parseLong(courseid));
        Integer objectiveQuesCount=this.tpCourseQuestionManager.getObjectiveQuesCount(cq);
        request.setAttribute("objectiveQuesCount", objectiveQuesCount);   //ר���¿͹�������
        return new ModelAndView("/teachpaltform/task/teacher/task-update");
    }


    /**
     * �����޸�
     * @throws Exception
     */
    @RequestMapping(params="doSubUpdTask",method=RequestMethod.POST)
    public void doSubUpdTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        String tasktype=request.getParameter("tasktype");
        String taskname=request.getParameter("taskname");
        String taskvalueid=request.getParameter("taskvalueid");
        String taskremark=request.getParameter("taskremark");
        String quesnum=request.getParameter("quesnum");
        String clstype=request.getParameter("clstype");
        String micpaperid=request.getParameter("paperid");
        if(tasktype==null||tasktype.trim().length()<1
                ||taskid==null||taskvalueid==null
            // ||taskname==null||taskname.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String courseid=request.getParameter("courseid");
        String questype=request.getParameter("questype");
        String[]criteriaArray=request.getParameterValues("taskcri");
        //С��
        String[]groupArray=request.getParameterValues("groupArray");
        //�༶
        String[]clsArray=request.getParameterValues("clsArray");
        String[]bClsArray=request.getParameterValues("btimeClsArray");
        String[]eClsArray=request.getParameterValues("etimeClsArray");

        if(bClsArray==null||eClsArray==null){
            je.setMsg("����,δ��ȡ������ʱ��!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(StringUtils.isBlank(courseid)){
            je.setMsg("����,δ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if((groupArray==null||groupArray.length<1)
                &&(clsArray==null||clsArray.length<1)){
            je.setMsg("����,δ��ȡ���������!");
            response.getWriter().print(je.toJSON());
            return;
        }
        if(!(StringUtils.isNotBlank(questype)&&questype.equals("5"))&&criteriaArray.length<1){
            je.setMsg("����,δ��ȡ��������ɱ�׼!");
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(Long.parseLong(courseid));
        //��ѯû����ɾ��������
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);
        //�ѷ���������
        List<TpTaskInfo>taskReleaseList=this.tpTaskManager.getTaskReleaseList(sel, null);
        Integer orderIdx=1;
        if(taskReleaseList!=null&&taskReleaseList.size()>0)
            orderIdx+=taskReleaseList.size();


        StringBuilder sql=null;
        List<Object>objList=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        Long tasknextid;
        TpTaskInfo ta=new TpTaskInfo();
       // ta.setOrderidx(orderIdx);

        //�κ���ҵ 3
        if(tasktype.toString().equals("3")){
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ�������ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseQuestion q=new TpCourseQuestion();
            q.setQuestionid(Long.parseLong(taskvalueid));
            List<TpCourseQuestion>quesList=this.tpCourseQuestionManager.getList(q,null);
            if(quesList==null||quesList.size()<1){
                je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ��!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("2")){//����
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ�������ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpTopicInfo i=new TpTopicInfo();
            i.setTopicid(Long.parseLong(taskvalueid));
            List<TpTopicInfo>iList=this.tpTopicManager.getList(i, null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("1")){//��Դ
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ����Դ��ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String resourcetype=request.getParameter("resourcetype");
            resourcetype=resourcetype==null||resourcetype.length()<1?"1":resourcetype;
            if(resourcetype.equals("1")){
                TpCourseResource t=new TpCourseResource();
                t.setResid(Long.parseLong(taskvalueid));
                List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, null);
                if(resList==null||resList.size()<1){
                    je.setMsg("��ʾ����ǰ��Դ�Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(1);

                TpCourseResource tt=new TpCourseResource();
                tt.setCourseid(Long.parseLong(courseid));
                tt.setResid(Long.parseLong(taskvalueid));
                List<TpCourseResource>tpCourseResourceList=this.tpCourseResourceManager.getList(tt,null);
                if(tpCourseResourceList==null||tpCourseResourceList.size()<1){
                    je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                    response.getWriter().print(je.toJSON());
                    return;
                }
                if(tpCourseResourceList.get(0).getResourcetype().equals(2)){
                    tt.setRef(tpCourseResourceList.get(0).getRef());
                    tt.setResourcetype(1);
                    sql=new StringBuilder();
                    objList=this.tpCourseResourceManager.getUpdateSql(tt,sql);
                    if(objList!=null&&sql!=null&&sql.length()>0){
                        objListArray.add(objList);
                        sqlListArray.add(sql.toString());
                    }
                }
            }else{
                String remotetype=request.getParameter("remotetype");
                String resourcename=request.getParameter("resourcename");
                ta.setTaskvalueid(Long.parseLong(taskvalueid));
                ta.setResourcetype(2);
                ta.setRemotetype(Integer.parseInt(remotetype));
                ta.setResourcename(resourcename);
            }

        }else if(tasktype.toString().equals("4")){//�ɾ����
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ���Ծ��ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCoursePaper tpCoursePaper=new TpCoursePaper();
            tpCoursePaper.setPaperid(Long.parseLong(taskvalueid));
            List<TpCoursePaper>iList=this.tpCoursePaperManager.getList(tpCoursePaper,null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ�Ծ��Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
        }else if(tasktype.toString().equals("5")){//��������
            //����Ծ�
            TpCourseQuestion cq=new TpCourseQuestion();
            cq.setCourseid(Long.parseLong(courseid));
            List<TpCourseQuestion>courseQuestionList=this.tpCourseQuestionManager.getList(cq,null);
            if(courseQuestionList==null||courseQuestionList.size()<1){
                je.setMsg("��ǰר���������⣬�޷����!������������Ӹ�����!");
                response.getWriter().print(je.toJSON());
                return;
            }
        }else if(tasktype.toString().equals("6")){//΢��Ƶ����
            if(taskvalueid==null||taskvalueid.trim().length()<1){
                je.setMsg("����ϵͳδ��ȡ��΢��Ƶ��ʶ!");
                response.getWriter().print(je.toJSON());
                return;
            }
            TpCourseResource tpCourseResource=new TpCourseResource();
            tpCourseResource.setResid(Long.parseLong(taskvalueid));
            tpCourseResource.setCourseid(Long.parseLong(courseid));
            List<TpCourseResource>iList=this.tpCourseResourceManager.getList(tpCourseResource,null);
            if(iList==null||iList.size()<1){
                je.setMsg("��ʾ����ǰ��Դ�Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            PaperInfo paperInfo=new PaperInfo();
            paperInfo.setPaperid(Long.parseLong(micpaperid));
            List<PaperInfo>paperList=this.paperManager.getList(paperInfo,null);
            if(paperList==null||paperList.size()<1){
                je.setMsg("��ʾ����ǰ�Ծ��Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
                response.getWriter().print(je.toJSON());
                return;
            }
            ta.setTaskvalueid(Long.parseLong(taskvalueid));
            ta.setPaperid(Long.parseLong(micpaperid));
        }else if(tasktype.toString().equals("10")){//ֱ����
            if(taskname==null||taskname.trim().length()<1){
                je.setMsg("ϵͳδ��ȡ��ֱ������!");
                response.getWriter().print(je.toJSON());
                return;
            }

            for(int i=0;i<bClsArray.length;i++){
                TpTaskAllotInfo liveAllot=new TpTaskAllotInfo();
                liveAllot.setCourseid(Long.parseLong(courseid));
                liveAllot.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                liveAllot.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                liveAllot.setTasktype(10);
                List<TpTaskAllotInfo>allotInfoList=this.tpTaskAllotManager.getTaskRemindObjList(liveAllot,null);
                if(allotInfoList!=null&&allotInfoList.size()>0){
                    boolean isHas=false;
                    for(TpTaskAllotInfo allot:allotInfoList){
                        if(allot.getTaskid().toString().equals(taskid.toString())){
                            isHas=true;
                        }else
                            isHas=false;
                    }
                    if(!isHas){
                        je.setMsg("��ʾ�������õ�ʱ��������ֱ�������������ʱ��! \n\nʱ�䣺"+bClsArray[i]+"~"+eClsArray[i]+"");
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                }
            }

            ta.setTaskname(taskname);
            ta.setTaskvalueid(Long.valueOf(1));
        }
        //��ȡ����
        TpTaskInfo selT=new TpTaskInfo();
        selT.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(selT,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg("��ʾ����ǰ�����Ѳ����ڻ�ɾ������ˢ��ҳ�������!");
            response.getWriter().print(je.toJSON());
            return;
        }

        TpTaskInfo tmpTask=taskList.get(0);
        /**
         * �ο�����
         * 1����Ԫ���޸ģ�����������
         * 2�½����񣬲��������ݵ�Ԫ�ز������С�
         * 3ɾ������������ݡ��������������Ϣ��
         */
        if(tmpTask.getCloudstatus().intValue()==3){
            //��������Ƿ��޸ģ��ǣ���������������ֱ��ʹ��
            ta.setTaskid(tmpTask.getTaskid());
            ta.setTasktype(Integer.parseInt(tasktype));
            ta.setCourseid(Long.parseLong(courseid));
            ta.setCriteria(Integer.parseInt(criteriaArray[0]));
            if(taskremark!=null)
                ta.setTaskremark(taskremark);
            if(taskname!=null)
                ta.setTaskname(taskname);
            if(quesnum!=null&&quesnum.trim().length()>0)
                ta.setQuesnum(Integer.parseInt(quesnum));
            else
                ta.setQuesnum(10);
            tasknextid=this.tpTaskManager.getNextId(true);
            ta.setCuserid(this.logined(request).getRef());
            if(micpaperid!=null&&micpaperid.trim().length()>0)
                ta.setPaperid(Long.parseLong(micpaperid));
            sql=new StringBuilder();
            objList=this.tpTaskManager.getSaveSql(ta, sql);
            if(objList!=null&&sql!=null&&sql.length()>0){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
           /* if(tmpTask.getQuoteid()!=null){
                //����ר���������
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpTask.getQuoteid());
                to.setTargetid(tasknextid);             //�½�����ID
                to.setReferenceid(tmpTask.getTaskid()); //����������ID
                to.setOperatetype(3);              //�޸�
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TASK.getValue());                 //ר������
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }*/
        }else{
            /**
             *�Խ������޸�
             */
            tasknextid=tmpTask.getTaskid();
            ta.setTaskid(tasknextid);
            if(taskname!=null)
                ta.setTaskname(taskname);
            ta.setTasktype(Integer.parseInt(tasktype));
            ta.setCourseid(Long.parseLong(courseid));
            ta.setCuserid(this.logined(request).getRef());
            ta.setCriteria(Integer.parseInt(criteriaArray[0]));
            if(taskremark!=null)
                ta.setTaskremark(taskremark);
            if(quesnum!=null&&quesnum.trim().length()>0)
                ta.setQuesnum(Integer.parseInt(quesnum));
            else
                ta.setQuesnum(10);
            if(micpaperid!=null&&micpaperid.trim().length()>0)
                ta.setPaperid(Long.parseLong(micpaperid));
            sql=new StringBuilder();
            objList=this.tpTaskManager.getUpdateSql(ta, sql);
            if(objList!=null&&sql!=null&&sql.length()>0){
                objListArray.add(objList);
                sqlListArray.add(sql.toString());
            }
        }


        //ɾ���������
        TpTaskAllotInfo tdelete=new TpTaskAllotInfo();
        tdelete.setTaskid(tmpTask.getTaskid());
        tdelete.setCourseid(tmpTask.getCourseid());
        sql=new StringBuilder();
        objList=this.tpTaskAllotManager.getDeleteSql(tdelete,sql);
        if(sql!=null&&objList!=null){
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        //���������� С��
        if(groupArray!=null&&groupArray.length>0){
            for (int i=0;i<groupArray.length;i++) {
                /**
                 * * ����Ľ���ʱ�� Ҫ���� ר��Ŀ�ʼʱ�䡣��Ҳ����˵����Ŀ�ʼ�ͽ���ʱ�䶼Ҫ����ר��Ŀ�ʼʱ�䣩
                 * ����Ŀ�ʼʱ��Ĭ��Ϊ��ǰʱ�䣬����Ľ���ʱ��Ĭ��Ϊר��Ŀ�ʼʱ�䡣
                 */
                TpGroupInfo groupInfo=new TpGroupInfo();
                groupInfo.setGroupid(Long.parseLong(groupArray[i]));
                List<TpGroupInfo>groupInfos=this.tpGroupManager.getList(groupInfo,null);
                if(groupInfos!=null&&groupInfos.size()>0){
                    //ר��ʱ��
                    TpCourseClass tpCourseClass=new TpCourseClass();
                    tpCourseClass.setClassid(groupInfos.get(0).getClassid());
                    tpCourseClass.setCourseid(Long.parseLong(courseid));
                    tpCourseClass.setSubjectid(groupInfos.get(0).getSubjectid());
                    List<TpCourseClass>courseClsList=this.tpCourseClassManager.getList(tpCourseClass,null);
                    if(courseClsList==null||courseClsList.size()<1){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    TpCourseClass tmpCourseClass=courseClsList.get(0);

                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(groupInfos.get(0).getClassid());
                    List<ClassInfo>clsList=this.classManager.getList(cls,null);
                    if(clsList!=null&&clsList.size()>0){
                        ClassInfo tmpCls=clsList.get(0);
                        if(tmpCls!=null&&tmpCls.getDctype()==3){
                            if(UtilTool.StringConvertToDate(eClsArray[i]).after(tmpCourseClass.getBegintime())){
                                je.setMsg("��ѧ���õ�����Ľ���ʱ��������ר��Ŀ�ʼʱ��!\n\n��ʾ������������"+groupInfos.get(0).getClassname()+groupInfos.get(0).getGroupname()+"����ʱ��!");
                                response.getWriter().print(je.toJSON());
                                return;
                            }
                        }
                    }
                }




                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                tal.setTaskid(tasknextid);
                tal.setUsertype(2);
                tal.setUsertypeid(Long.parseLong(groupArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }
        //���������� �༶
        if(clsArray!=null&&clsArray.length>0){
            if(clstype==null||clstype.trim().length()<1){
                je.setMsg("����!ϵͳδ��ȡ���༶����!");
                response.getWriter().print(je.toJSON());
                return;
            }
            String[]classTypeArray=clstype.split(",");
            for (int i=0;i<clsArray.length;i++) {
                /**
                 * ����Ľ���ʱ�� Ҫ���� ר��Ŀ�ʼʱ�䡣��Ҳ����˵����Ŀ�ʼ�ͽ���ʱ�䶼Ҫ����ר��Ŀ�ʼʱ�䣩
                 * ����Ŀ�ʼʱ��Ĭ��Ϊ��ǰʱ�䣬����Ľ���ʱ��Ĭ��Ϊר��Ŀ�ʼʱ�䡣
                 */
                ClassUser classUser=new ClassUser();
                classUser.setClassid(Integer.parseInt(clsArray[i]));
                classUser.setRelationtype("�ο���ʦ");
                classUser.setUserid(this.logined(request).getRef());
                Object subjectid=request.getSession().getAttribute("session_subject");
                if(subjectid!=null&&subjectid.toString().length()>0)
                    classUser.setSubjectid(Integer.parseInt(subjectid.toString()));
                List<ClassUser>clsUserList=this.classUserManager.getList(classUser,null);
                if(clsUserList!=null&&clsUserList.size()>0){
                    //ר��ʱ��
                    TpCourseClass tpCourseClass=new TpCourseClass();
                    tpCourseClass.setClassid(Integer.parseInt(clsArray[i]));
                    tpCourseClass.setCourseid(Long.parseLong(courseid));
                    tpCourseClass.setSubjectid(clsUserList.get(0).getSubjectid());
                    List<TpCourseClass>courseClsList=this.tpCourseClassManager.getList(tpCourseClass,null);
                    if(courseClsList==null||courseClsList.size()<1){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    TpCourseClass tmpCourseClass=courseClsList.get(0);

                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(cls,null);
                    if(clsList!=null&&clsList.size()>0){
                        ClassInfo tmpCls=clsList.get(0);
                        if(tmpCls!=null&&tmpCls.getDctype()==3){
                            if(UtilTool.StringConvertToDate(eClsArray[i]).after(tmpCourseClass.getBegintime())){
                                je.setMsg("��ѧ���õ�����Ľ���ʱ��������ר��Ŀ�ʼʱ��!\n\n��ʾ������������"+tmpCls.getClassgrade()+tmpCls.getClassname()+"����ʱ��!");
                                response.getWriter().print(je.toJSON());
                                return;
                            }
                        }
                    }
                }


                TpTaskAllotInfo tal=new TpTaskAllotInfo();
                //��֤��ʲô���͵İ༶
                if(classTypeArray[i].equals("1")){
                    /*ClassInfo c=new ClassInfo();
                    c.setClassid(Integer.parseInt(clsArray[i]));
                    List<ClassInfo>clsList=this.classManager.getList(c,null);*/
                    tal.setUsertype(0);
                }else if(classTypeArray[i].equals("2")){
                    /*TpVirtualClassInfo tv=new TpVirtualClassInfo();
                    tv.setVirtualclassid(Integer.parseInt(clsArray[i]));
                    List<TpVirtualClassInfo>vclsList=this.tpVirtualClassManager.getList(tv,null);*/
                    tal.setUsertype(1);
                }else{
                    je.setMsg("����!����༶��Ч!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                tal.setTaskid(tasknextid);
                tal.setUsertypeid(Long.parseLong(clsArray[i]));
                tal.setBtime(UtilTool.StringConvertToDate(bClsArray[i]));
                tal.setEtime(UtilTool.StringConvertToDate(eClsArray[i]));
                tal.setCuserid(this.logined(request).getRef());
                //tal.setCriteria(criteriaArray[0]);
                tal.setCourseid(Long.parseLong(courseid));
                sql=new StringBuilder();
                objList=this.tpTaskAllotManager.getSaveSql(tal, sql);
                if(objList!=null&&sql!=null&&sql.length()>0){
                    objListArray.add(objList);
                    sqlListArray.add(sql.toString());
                }
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                //���������Ϣ����
                //this.tpTaskManager.doSaveTaskMsg(ta);

                if(!sendRemind(tasknextid))
                    System.out.println("doSubUpdTask ���taskRemindʧ��!");
                else
                    System.out.println("doSubUpdTask ���taskRemind�ɹ�!");

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ת��ѧ��������ҳ
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toStuTaskIndex",method=RequestMethod.GET)
    public ModelAndView toStuTaskIndex(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
//		if(!this.validateRole(new BigDecimal(UtilTool._STUDENT_ROLE_ID))){
//			je.setMsg("��Ǹ����ǰҳ��ֻ����ѧ���û��鿴!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;  
//		}
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String classid=request.getParameter("classid");
        if(classid==null||classid.length()<1)
            classid=request.getSession().getAttribute("session_class").toString();
        //���ݿ���ID��ѧ��ID�������
        if(courseid==null||courseid.trim().length()<1||classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        ClassInfo classInfo=new ClassInfo();
        classInfo.setClassid(Integer.parseInt(classid));
        List<ClassInfo>clsList=this.classManager.getList(classInfo,null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        String gradeid=null;
        GradeInfo g=new GradeInfo();
        g.setGradevalue(clsList.get(0).getClassgrade());
        List<GradeInfo>gradeList=this.gradeManager.getList(g,null);
        if(gradeList!=null&&gradeList.size()>0)
            gradeid=gradeList.get(0).getGradeid().toString();

        TpTaskInfo t=new TpTaskInfo();
        t.setCourseid(Long.parseLong(courseid));
        t.setUserid(this.logined(request).getUserid());
//		List<TpTaskInfo> taskList=this.tpTaskManager.getListbyStu(t,null);
//		if(taskList==null||taskList.size()<1){
//			je.setMsg("��ʾ��û�з���������Ϣ!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;
//		}
        //��ѯѧ�������б�
        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid().toString();

        //�����б�
        TpCourseInfo tt=new TpCourseInfo();
        tt.setCourseid(Long.parseLong(courseid));
        tt.setLocalstatus(1);   //1������ 2��ɾ��
        tt.setUserid(this.logined(request).getUserid());
        List<TpCourseInfo>courseList=this.tpCourseManager.getStuCourseList(tt, null);
        if(courseList==null||courseList.size()<1){
            je.setMsg("����û�з��ֵ�ǰר��!��ˢ�º�����!");
            response.getWriter().print(je.getAlertMsgAndBack());return null;
        }else{
            if(this.logined(request).getCardstatus()!=null&&this.logined(request).getCardstatus()==0){
                TpCourseInfo tmpCourse=courseList.get(0);
                if(tmpCourse.getClassendtimes()!=null&&
                        new Date().after(UtilTool.StringConvertToDate(tmpCourse.getClassendtimes().toString())))
                    je.setMsg("���Ŀ��ѹ��ڣ�������!");
                response.getWriter().print(je.getAlertMsgAndCloseWin());return null;
            }

            request.setAttribute("coursename", courseList.get(0).getCoursename());
            //������ʽ
            TpCourseInfo tcs1= new TpCourseInfo();
            tcs1.setUserid(this.logined(request).getUserid());
            tcs1.setTermid(courseList.get(0).getTermid());
            tcs1.setSubjectid(Integer.parseInt(subjectid));
            if(gradeid!=null&&gradeid.trim().length()>0&&!gradeid.equals("0"))
                tcs1.setGradeid(Integer.parseInt(gradeid));
            if(termid==null||termid.trim().length()<1)
                termid=courseList.get(0).getTermid();
            List<TpCourseInfo>courseList1=this.tpCourseManager.getStuCourseList(tcs1, null);
            request.setAttribute("courseList",courseList1);
        }


        if(gradeid!=null&&!gradeid.toString().equals("0")&&gradeid.trim().length()>0)
            request.getSession().setAttribute("session_grade",gradeid);
        if(subjectid!=null&&!subjectid.toString().equals("0")&&subjectid.trim().length()>0)
            request.getSession().setAttribute("session_subject",subjectid);
        request.getSession().setAttribute("session_class",classid);

        request.setAttribute("courseid", courseid);
        request.setAttribute("termid", termid);

        return new ModelAndView("/teachpaltform/task/student/task-index");
    }


    /**
     * ѧ���ύ����
     * @throws Exception
     */
    @RequestMapping(params="doStuSubmitTask",method=RequestMethod.POST)
    public void doStuSubmitTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String tasktype=request.getParameter("tasktype");
        String hasannex=request.getParameter("hasannex");
        //String groupid=request.getParameter("groupid");





        //��Դ�����⡢�κ���ҵID
        String quesid=request.getParameter("quesid");
        String questype=request.getParameter("questype");
        if(courseid==null||courseid.trim().length()<1||
                taskid==null||taskid.trim().length()<1||
                tasktype==null||tasktype.trim().length()<1||
                //groupid==null||groupid.trim().length()<1||
                quesid==null||quesid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }


        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlListArray=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //�κ���ҵ
        String quesanswer=request.getParameter("quesanswer");
        String[]fbanswerArray=request.getParameterValues("fbanswerArray");
        String[]optionArray=request.getParameterValues("optionArray");

        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCourseid(Long.parseLong(courseid));
        t.setTasktype(Integer.parseInt(tasktype));
        //t.setGroupid(groupid);
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo tmpTask=taskList.get(0);

        if(Integer.parseInt(tasktype)==3){
            if(questype==null||!UtilTool.isNumber(questype)){
                je.setMsg("����ϵͳδ��ȡ����������!");
                response.getWriter().print(je.toJSON());
                return;
            }


            if(questype.equals("1")){//�ʴ�
                if(quesanswer==null||quesanswer.trim().length()<1){
                    je.setMsg("����δ��ȡ���ʴ����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }



                String annexName=null;
                boolean isfileOk=true;
                if(hasannex!=null&&hasannex.equals("1")){
                    MultipartFile[] muFile=this.getUpload(request);
                    if(muFile!=null&&muFile.length>0){//�ļ��ϴ�
                        //this.setFname(annexName=this.getUploadFileName()[0]);
                        this.setFname(annexName=new Date().getTime()+""
                                +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                        );
                        if(!fileupLoad(request)){
                            isfileOk=!isfileOk;
                        }
                    }else
                        isfileOk=false;
                }

                if(!isfileOk){
                    je.setMsg("�ļ��ϴ�ʧ��!��ˢ��ҳ�������!");
                    response.getWriter().println(je.toJSON());return ;
                }

                //ѹ��ԭͼ��w160 h 90
                if(annexName!=null&&annexName.length()>0){
                    quesanswer=new String(quesanswer.getBytes("iso-8859-1"),"UTF-8");
                    String suffix=annexName.substring(annexName.lastIndexOf("."));
                    if(UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR,suffix.toLowerCase())){
                        String filename=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName;
                        File file=new File(filename);
                        if(!file.exists()){
                            je.setMsg("δ�����ļ�!");
                            response.getWriter().println(je.toJSON());return ;
                        }
                        //��ͼ
                        String descFile=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName.substring(0,annexName.lastIndexOf("."))+"_1"+suffix;
                        UtilTool.copyFile(file,new File(descFile));
                        //����ͼ
                        UtilTool.ReduceImg(file, filename, 160, 90);
                    }
                }



                //¼������¼
                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(t.getTaskid());
                qa.setCourseid(t.getCourseid());
                qa.setTasktype(t.getTasktype());
                qa.setAnswercontent(quesanswer);
                qa.setQuesparentid(Long.parseLong(quesid));
                qa.setUserid(this.logined(request).getRef());
                qa.setRightanswer(1);
                if(annexName!=null&&annexName.length()>0)
                    qa.setReplyattach(annexName);
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                //¼�����״̬
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(t.getTaskid());
                tp.setTasktype(t.getTasktype());
                tp.setCourseid(t.getCourseid());
                tp.setCriteria(tmpTask.getCriteria());
                tp.setUserid(this.logined(request).getRef());
                tp.setIsright(1);
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }else if(questype.equals("2")){//���
                if(fbanswerArray==null||fbanswerArray.length<1){
                    je.setMsg("����δ��ȡ���ʴ����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                //�õ������ʦ���õĴ�
                QuestionInfo qb=new QuestionInfo();
                qb.setQuestionid(tmpTask.getTaskvalueid());
                List<QuestionInfo>qbList=this.questionManager.getList(qb, null);
                if(qbList==null||qbList.size()<1){
                    je.setMsg("����!��ǰ�����Ѳ�����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                boolean fbflag=false;
                String fbanswer=qbList.get(0).getCorrectanswer();
                String[]answerArray=null;
                if(fbanswer!=null&&fbanswer.length()>0){
                    answerArray=fbanswer.split("\\|");
                    if(answerArray.length<1){
                        je.setMsg("����!δ��ȡ��������ʦ���õĴ�!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }else if(answerArray.length!=fbanswerArray.length){
                        je.setMsg("����!�������Ŀ�������!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }
                    for (int i = 0; i < answerArray.length; i++) {
                        if(answerArray[i].equals(fbanswerArray[i])){
                            fbflag=true;
                        }else{
                            fbflag=false;
                        }
                    }
                }else{
                    je.setMsg("����!ϵͳδ��ȡ��������!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                //¼������¼
                String fbstr="";
                for (String s : fbanswerArray) {
                    if(fbstr.length()>0)
                        fbstr+="|";
                    fbstr+=s;
                }
                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(t.getTaskid());
                qa.setCourseid(t.getCourseid());
                qa.setTasktype(t.getTasktype());
                qa.setQuesparentid(Long.parseLong(quesid));
                qa.setAnswercontent(fbstr);
                qa.setUserid(this.logined(request).getRef());
                qa.setRightanswer(fbflag==true?1:0);
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                //¼�����״̬
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(t.getTaskid());
                tp.setTasktype(t.getTasktype());
                tp.setCourseid(t.getCourseid());
                tp.setUserid(this.logined(request).getRef());
                sql=new StringBuilder();
                tp.setCriteria(tmpTask.getCriteria());
                tp.setIsright(fbflag==true?1:0);
                objList=this.taskPerformanceManager.getSaveSql(tp, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }else if(questype.equals("3")){//��ѡ
                if(optionArray==null||optionArray.length<1){
                    je.setMsg("����δ��ȡ��ѡ�����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                //�õ������ʦ���õĴ�
                QuestionOption qo=new QuestionOption();
                qo.setQuestionid(tmpTask.getTaskvalueid());
                qo.setIsright(1);
                List<QuestionOption>qbList=this.questionOptionManager.getList(qo, null);
                if(qbList==null||qbList.size()<1){
                    je.setMsg("����!δ��ȡ����ʦ���õ�ѡ�����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                boolean selflag=false;
                for (QuestionOption questionBank : qbList) {
                    if(questionBank!=null&&questionBank.getRef().toString().equals(optionArray[0])){
                        selflag=true;
                        break;
                    }

                }
                //�õ���ǰѡ������
                QuestionOption qstu=new QuestionOption();
                qstu.setRef(Integer.parseInt(optionArray[0]));
                List<QuestionOption>qbstuList=this.questionOptionManager.getList(qstu, null);
                if(qbstuList==null||qbstuList.size()<1){
                    je.setMsg("����!��ǰѡ���Ѳ�����!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                QuestionAnswer qa=new QuestionAnswer();
                qa.setTaskid(t.getTaskid());
                qa.setCourseid(t.getCourseid());
                qa.setTasktype(t.getTasktype());
                qa.setUserid(this.logined(request).getRef());
                qa.setQuesparentid(Long.parseLong(quesid));
                qa.setQuesid(Long.parseLong(optionArray[0]));
                qa.setAnswercontent(qbstuList.get(0).getOptiontype());//ѡ�����
                qa.setRightanswer(selflag==true?1:0);
                sql=new StringBuilder();
                objList=this.questionAnswerManager.getSaveSql(qa, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

                //¼�����״̬
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(t.getTaskid());
                tp.setTasktype(t.getTasktype());
                tp.setCourseid(t.getCourseid());
                tp.setUserid(this.logined(request).getRef());
                sql=new StringBuilder();
                tp.setCriteria(tmpTask.getCriteria());
                tp.setIsright(selflag==true?1:0);
                objList=this.taskPerformanceManager.getSaveSql(tp, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }

            }else if(questype.equals("4")){//��ѡ
                if(optionArray==null||optionArray.length<1){
                    je.setMsg("����δ��ȡ����ѡ���!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                //�õ������ʦ���õĴ�
                QuestionOption qb=new QuestionOption();
                qb.setQuestionid(tmpTask.getTaskvalueid());
                qb.setIsright(1);
                List<QuestionOption>qbList=this.questionOptionManager.getList(qb, null);
                if(qbList==null||qbList.size()<1){
                    je.setMsg("����!δ��ȡ����ʦ���õĸ�ѡ���!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
                boolean selflag=false;
                int flag=0;
                for(String opnid : optionArray){
                    selflag=false;
                    for (QuestionOption questionBank : qbList) {
                        if(questionBank!=null&&
                                questionBank.getRef().toString().equals(opnid)){
                            selflag=true;
                            break;
                        }
                    }
                    if(!selflag)
                        ++flag;

                    //�õ���ǰѡ������
                    QuestionOption qstu=new QuestionOption();
                    qstu.setRef(Integer.parseInt(opnid));
                    List<QuestionOption>qbstuList=this.questionOptionManager.getList(qstu, null);
                    if(qbstuList==null||qbstuList.size()<1){
                        je.setMsg("����!��ǰѡ���Ѳ�����!");
                        response.getWriter().print(je.toJSON());
                        return;
                    }

                    QuestionAnswer qa=new QuestionAnswer();
                    qa.setTaskid(t.getTaskid());
                    qa.setCourseid(t.getCourseid());
                    qa.setTasktype(t.getTasktype());
                    //qa.setGroupid(groupid);
                    qa.setUserid(this.logined(request).getRef());
                    qa.setQuesparentid(Long.parseLong(quesid));
                    qa.setQuesid(Long.parseLong(opnid));
                    qa.setAnswercontent(qbstuList.get(0).getOptiontype()); //ѡ�����
                    qa.setRightanswer(selflag==true?1:0);
                    sql=new StringBuilder();
                    objList=this.questionAnswerManager.getSaveSql(qa, sql);
                    if(objList!=null&&sql!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }

                //¼�����״̬
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(t.getTaskid());
                tp.setTasktype(t.getTasktype());
                tp.setCourseid(t.getCourseid());
                tp.setUserid(this.logined(request).getRef());
                tp.setCriteria(tmpTask.getCriteria());
                tp.setIsright(flag<1?1:0);
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp, sql);
                if(objList!=null&&sql!=null){
                    sqlListArray.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }else if(Integer.parseInt(tasktype)==1){
            if(quesanswer==null||quesanswer.trim().length()<1){
                je.setMsg("����,δ��ȡ����Դѧϰ�ĵ�!");
                response.getWriter().print(je.toJSON());
                return;
            }


            String annexName=null;
            boolean isfileOk=true;
            if(hasannex!=null&&hasannex.equals("1")){
                MultipartFile[] muFile=this.getUpload(request);
                if(muFile!=null&&muFile.length>0){//�ļ��ϴ�
                    //this.setFname(annexName=this.getUploadFileName()[0]);
                    this.setFname(annexName=new Date().getTime()+""
                            +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                    );
                    if(!fileupLoad(request)){
                        isfileOk=!isfileOk;
                    }
                }else
                    isfileOk=false;
            }

            if(!isfileOk){
                je.setMsg("�ļ��ϴ�ʧ��!��ˢ��ҳ�������!");
                response.getWriter().println(je.toJSON());return ;
            }

            //ѹ��ԭͼ��w160 h 90
            if(annexName!=null&&annexName.length()>0){
                quesanswer=new String(quesanswer.getBytes("iso-8859-1"),"UTF-8");
                String suffix=annexName.substring(annexName.lastIndexOf("."));
                if(UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR,suffix.toLowerCase())){
                    String filename=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName;
                    File file=new File(filename);
                    if(!file.exists()){
                        je.setMsg("δ�����ļ�!");
                        response.getWriter().println(je.toJSON());return ;
                    }
                    //��ͼ
                    String descFile=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName.substring(0,annexName.lastIndexOf("."))+"_1"+suffix;
                    UtilTool.copyFile(file,new File(descFile));
                    //����ͼ
                    UtilTool.ReduceImg(file, filename, 160, 90);
                }
            }



            QuestionAnswer qa=new QuestionAnswer();
            qa.setCourseid(Long.parseLong(courseid));
            qa.setQuesparentid(Long.parseLong(quesid));
            qa.setQuesid(Long.parseLong("0"));
            qa.setUserid(this.logined(request).getRef());
            qa.setAnswercontent(quesanswer);
            qa.setRightanswer(1);
            qa.setTasktype(tmpTask.getTasktype());
            qa.setTaskid(tmpTask.getTaskid());
            if(annexName!=null&&annexName.trim().length()>0)
                qa.setReplyattach(annexName);
            sql=new StringBuilder();
            objList=this.questionAnswerManager.getSaveSql(qa,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }


            if(tmpTask.getCriteria()!=null&&tmpTask.getCriteria()==2){
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(taskList.get(0).getTaskid());
                tp.setTasktype(taskList.get(0).getTasktype());
                tp.setCourseid(taskList.get(0).getCourseid());
                tp.setCriteria(2);//�ύ�ĵ�
                tp.setUserid(this.logined(request).getRef());
                tp.setIsright(1);
                List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                if(tpList==null||tpList.size()<1){
                    sql=new StringBuilder();
                    objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                    if(sql!=null&&objList!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }else{
                je.setMsg("��ǰ��Դû�з����ύѧϰ�ĵ�����!");
                response.getWriter().print(je.toJSON());
                return;
            }
        }

        if(objListArray.size()>0&&sqlListArray.size()>0){
            boolean flag=this.tpTaskManager.doExcetueArrayProc(sqlListArray, objListArray);
            if(flag){
                //�õ��༶ID
                TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                tallot.setTaskid(taskList.get(0).getTaskid());
                tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                        ||clsMapList.get(0).get("CLASS_ID")==null){
                    je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(je.toJSON());return ;
                }

                //taskinfo:   4:�ɾ����  5����������   6:΢��Ƶ
                //����ת��:    6             7         8
                Integer type=0;
                switch(taskList.get(0).getTasktype()){
                    case 3:     //����
                        type=1;break;
                    case 1:     //��Դѧϰ
                        type=2;break;
                    case 2:
                        type=4;
                        break;
                }
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();
                /*�����ӷ�ͨ��*/
                if(this.tpStuScoreLogsManager.awardStuScore(Long.parseLong(courseid.trim())
                        ,Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                        ,Long.parseLong(taskid.trim())
                        ,Long.parseLong(this.logined(request).getUserid()+""),jid,type,this.logined(request).getDcschoolid())){
                    je.setMsg("��ϲ��,�����1���ֺ�1����ʯ(û�е��ýӿ�)");
                }else
                    System.out.println("awardScore error");
                je.setType("success");
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setMsg("���Ĳ���û��ִ��!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��ȡ�����µ�����
     * @throws Exception
     */
    @RequestMapping(params="toQryTopicList",method=RequestMethod.POST)
    public void toQryTopicList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String clsid=request.getParameter("clsid");
        String topicid=request.getParameter("topicid");
        String taskflag=request.getParameter("taskflag");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg("����ϵͳδ��ȡ�������ʶ!");
            response.getWriter().print(je.toJSON());
            return;
        }
//		if(clsid==null||clsid.trim().length()<1){
//			je.setMsg("����ϵͳδ��ȡ���༶��ʶ!");
//			response.getWriter().print(je.toJSON());
//			return;
//		}
        PageResult p=this.getPageResultParameter(request);
        TpTopicInfo t=new TpTopicInfo();
        t.setCuserid(this.logined(request).getUserid());
        t.setCourseid(Long.parseLong(courseid));
        t.setStatus(1);
        if(topicid!=null&&topicid.trim().length()>0)
            t.setTopicid(Long.parseLong(topicid));
        //��ѯδ�������������
        if(taskflag!=null&&taskflag.trim().length()>0)
            t.setFlag(1);
        List<TpTopicInfo>topList=this.tpTopicManager.getList(t,p);
        p.setList(topList);
        je.setPresult(p);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * �ύѧϰ�ĵ�
     * @throws Exception
     */
    @RequestMapping(params="m=doSubStudyNotes",method=RequestMethod.POST)
    public void doSubStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resourceid=request.getParameter("resid");
        String answercontent=request.getParameter("answercontent");
        String hasannex=request.getParameter("hasannex");

        if(courseid==null||courseid.trim().length()<1||
                resourceid==null||resourceid.trim().length()<1||
                answercontent==null||answercontent.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setCourseid(Long.parseLong(courseid));
        taskInfo.setTaskvalueid(Long.parseLong(resourceid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(taskInfo,null);
        if(taskList==null||taskList.size()<1){
            je.setMsg("��ǰ��Դû�з���������Ϣ!");
            response.getWriter().print(je.toJSON());
            return;
        }
        taskInfo=taskList.get(0);
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlList=new ArrayList<String>();

        if(taskInfo.getCriteria()!=null&&taskInfo.getCriteria()==2){
            TaskPerformanceInfo tp=new TaskPerformanceInfo();
            tp.setTaskid(taskList.get(0).getTaskid());
            tp.setTasktype(taskList.get(0).getTasktype());
            tp.setCourseid(taskList.get(0).getCourseid());
            tp.setCriteria(2);//�ύ�ĵ�
            tp.setUserid(this.logined(request).getRef());
            tp.setIsright(1);
            List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
            if(tpList==null||tpList.size()<1){
                sql=new StringBuilder();
                objList=this.taskPerformanceManager.getSaveSql(tp,sql);
                if(sql!=null&&objList!=null){
                    sqlList.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }else{
            je.setMsg("��ǰ��Դû�з����ύѧϰ�ĵ�����!");
            response.getWriter().print(je.toJSON());
            return;
        }




        String annexName=null;
        boolean isfileOk=true;
        if(hasannex!=null&&hasannex.equals("1")){
            MultipartFile[] muFile=this.getUpload(request);
            if(muFile!=null&&muFile.length>0){//�ļ��ϴ�
                //this.setFname(annexName=this.getUploadFileName()[0]);
                this.setFname(annexName=new Date().getTime()+""
                        +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                );
                if(!fileupLoad(request)){
                    isfileOk=!isfileOk;
                }
            }else
                isfileOk=false;
            answercontent=new String(answercontent.getBytes("iso-8859-1"),"UTF-8");
        }

        if(!isfileOk){
            je.setMsg("�ļ��ϴ�ʧ��!��ˢ��ҳ�������!");
            response.getWriter().println(je.toJSON());return ;
        }

        //ѹ��ԭͼ��w160 h 90
        if(annexName!=null&&annexName.length()>0){
            String suffix=annexName.substring(annexName.lastIndexOf("."));
            if(UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR,suffix.toLowerCase())){
                String filename=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName;
                File file=new File(filename);
                if(!file.exists()){
                    je.setMsg("δ�����ļ�!");
                    response.getWriter().println(je.toJSON());return ;
                }
                //��ͼ
                String descFile=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName.substring(0,annexName.lastIndexOf("."))+"_1"+suffix;
                UtilTool.copyFile(file,new File(descFile));
                //����ͼ
                UtilTool.ReduceImg(file,filename,160,90);

            }
        }




        QuestionAnswer qa=new QuestionAnswer();
        qa.setCourseid(Long.parseLong(courseid));
        qa.setQuesparentid(Long.parseLong(resourceid));
        qa.setQuesid(Long.parseLong("0"));
        qa.setUserid(this.logined(request).getRef());
        qa.setAnswercontent(answercontent);
        qa.setRightanswer(1);
        qa.setTasktype(taskInfo.getTasktype());
        qa.setTaskid(taskInfo.getTaskid());
        if(annexName!=null&&annexName.length()>0)
            qa.setReplyattach(annexName);
        sql=new StringBuilder();
        objList=this.questionAnswerManager.getSaveSql(qa,sql);
        if(sql!=null&&objList!=null){
            sqlList.add(sql.toString());
            objListArray.add(objList);
        }

        if(this.tpTaskManager.doExcetueArrayProc(sqlList,objListArray)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));


            //�õ��༶ID
            TpTaskAllotInfo tallot=new TpTaskAllotInfo();
            tallot.setTaskid(taskList.get(0).getTaskid());
            tallot.getUserinfo().setUserid(this.logined(request).getUserid());
            List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
            if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                    ||clsMapList.get(0).get("CLASS_ID")==null){
                je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(je.toJSON());return ;
            }

            //taskinfo:   4:�ɾ����  5����������   6:΢��Ƶ
            //����ת��:    6             7         8
            Integer type=0;
            switch(taskList.get(0).getTasktype()){
                case 3:     //����
                    type=1;break;
                case 1:     //��Դѧϰ
                    type=2;break;
                case 2:
                    type=4;
                    break;
            }
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();
                /*�����ӷ�ͨ��*/
            if(this.tpStuScoreLogsManager.awardStuScore(Long.parseLong(courseid.trim())
                    ,Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                    ,taskList.get(0).getTaskid()
                    ,Long.parseLong(this.logined(request).getUserid()+""),jid,type,this.logined(request).getDcschoolid())){
                je.setMsg("��ϲ��,�����1���ֺ�1����ʯ(û�е��ýӿ�)");
            }else
                System.out.println("awardScore error");



        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * �����Դ�Ƿ�����
     * @throws Exception
     */
    @RequestMapping(params="checkStudyNotes",method=RequestMethod.POST)
    public void checkStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resourceid=request.getParameter("resid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resourceid==null||resourceid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        /*
        * ��ѯ��ǰ��Դ�Ƿ���ѧϰ�ĵ�����
        */
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setCourseid(Long.parseLong(courseid));
        taskInfo.setTaskvalueid(Long.parseLong(resourceid));
        taskInfo.setCriteria(2);
        List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(taskInfo, null);



        je.setObjList(taskList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����ѧϰ�ĵ�
     * @throws Exception
     */
    @RequestMapping(params="loadStudyNotes",method=RequestMethod.POST)
    public void loadStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String type=request.getParameter("usertype");
        String courseid=request.getParameter("courseid");
        String resourceid=request.getParameter("resourceid");

        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resourceid==null||resourceid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }


        QuestionAnswer qa=new QuestionAnswer();
        qa.setCourseid(Long.parseLong(courseid));
        qa.setQuesparentid(Long.parseLong(resourceid));
        //�����ѧ�������ж��Ƿ��Ѿ�����


        //��ѧ���ĵ�
        if(type!=null&&type.equals("1")){
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){   //�����ѧ����ѯ�����ж��Ƿ�
                qa.setUserid(this.logined(request).getRef());
            }
        }
        List<QuestionAnswer>qaList=this.questionAnswerManager.getList(qa, null);
        je.getObjList().add(qaList!=null&&qaList.size()>0?qaList.get(0):null);

        /*
        * ��ѯ��ǰ��Դ�Ƿ�����
        */
        if(type!=null&&type.equals("1")){
            PageResult p=new PageResult();
            p.setPageNo(1);
            p.setPageSize(1);
            TpTaskInfo taskInfo=new TpTaskInfo();
            taskInfo.setCourseid(Long.parseLong(courseid));
            taskInfo.setTaskvalueid(Long.parseLong(resourceid));
            List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(taskInfo,p);
            if(taskList!=null&&taskList.size()>0&&taskList.get(0).getTaskstatus()!=null){
                /**
                 * ��ѯ��ǰѧ���Ƿ񱻷��������
                 * */
                boolean isAllot=false;
                TpTaskInfo t=new TpTaskInfo();
                t.setCourseid(taskList.get(0).getCourseid());
                t.setUserid(this.logined(request).getUserid());
                List<TpTaskInfo> taskStuList=this.tpTaskManager.getListbyStu(t,null);
                if(taskStuList!=null&&taskStuList.size()>0){
                    for(TpTaskInfo tmpTask:taskStuList){
                        if(tmpTask.getTaskid().equals(taskList.get(0).getTaskid())
                                &&taskList.get(0).getCriteria()!=null&&taskList.get(0).getCriteria().intValue()==2){
                            isAllot=true;
                        }
                    }
                }
                if(!taskList.get(0).getTaskstatus().equals("1")&&!taskList.get(0).getTaskstatus().equals("3")&&isAllot){
                    je.getObjList().add("on");
                }else if(taskList.get(0).getTaskstatus().equals("3")&&isAllot){
                    je.getObjList().add("off");
                }

            }

        }



        je.setType("success");
        response.getWriter().print(je.toJSON());
    }



    /**
     * �޸�ѧϰ�ĵ�
     * @throws Exception
     */
    @RequestMapping(params="doUpdStudyNotes",method=RequestMethod.POST)
    public void doUpdStudyNotes(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String ref=request.getParameter("ref");
        String content=request.getParameter("content");
        String hasannex=request.getParameter("hasannex");


        if(ref==null||ref.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(content==null||content.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        content=new String(content.getBytes("iso-8859-1"),"UTF-8");

        String annexName=null;
        boolean isfileOk=true;
        if(hasannex!=null&&hasannex.equals("1")){
            MultipartFile[] muFile=this.getUpload(request);
            if(muFile!=null&&muFile.length>0){//�ļ��ϴ�
                //this.setFname(annexName=this.getUploadFileName()[0]);
                this.setFname(annexName=new Date().getTime()+""
                        +this.getUploadFileName()[0].substring(this.getUploadFileName()[0].lastIndexOf("."))
                );
                if(!fileupLoad(request)){
                    isfileOk=!isfileOk;
                }
            }else
                isfileOk=false;
        }

        if(!isfileOk){
            je.setMsg("�ļ��ϴ�ʧ��!��ˢ��ҳ�������!");
            response.getWriter().println(je.toJSON());return ;
        }

        //ѹ��ԭͼ��w160 h 90
        if(annexName!=null&&annexName.length()>0){
            String suffix=annexName.substring(annexName.lastIndexOf("."));
            if(UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR,suffix.toLowerCase())){
                String filename=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName;
                File file=new File(filename);
                if(!file.exists()){
                    je.setMsg("δ�����ļ�!");
                    response.getWriter().println(je.toJSON());return ;
                }
                //��ͼ
                String descFile=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ annexName.substring(0,annexName.lastIndexOf("."))+"_1"+suffix;
                UtilTool.copyFile(file,new File(descFile));
                //����ͼ
                UtilTool.ReduceImg(file,filename,160,90);

            }
        }



        QuestionAnswer qa=new QuestionAnswer();
        qa.setRef(Integer.parseInt(ref));
        qa.setAnswercontent(content);
        if(annexName!=null&&annexName.trim().length()>0)
            qa.setReplyattach(annexName);
        if(this.questionAnswerManager.doUpdate(qa)){
            je.setType("success");
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * ѧ���Ὠ��
     * @throws Exception
     */
    @RequestMapping(params="doSubmitSuggest",method=RequestMethod.POST)
    public void doSubmitSuggest(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();

        String suggestcontent=request.getParameter("suggestcontent");
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        String isanonmous=request.getParameter("isanonmous");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(suggestcontent==null||suggestcontent.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(isanonmous==null||isanonmous.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        TaskSuggestInfo ts=new TaskSuggestInfo();
        ts.setTaskid(Long.parseLong(taskid));
        ts.setCourseid(Long.parseLong(courseid));
        ts.setIsanonymous(Integer.parseInt(isanonmous));
        ts.setUserid(this.logined(request).getRef());
        ts.setSuggestcontent(suggestcontent);

        if(this.taskSuggestManager.doSave(ts)){
            je.setMsg("�����ύ�ɹ�!");
            je.setType("success");
        }else{
            je.setMsg("�����ύʧ��!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��ӻ�����������鿴��¼
     * @throws Exception
     */
    @RequestMapping(params="doAddViewRecord",method=RequestMethod.POST)
    public void doAddViewRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String themeid=request.getParameter("themeid");
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String groupid=request.getParameter("groupid");
        String taskid=request.getParameter("taskid");


        if(themeid==null||themeid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
//		if(groupid==null||groupid.trim().length()<1){
//			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(je.toJSON());return;
//		}
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        //����Ƿ��в鿴��׼
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //¼�����״̬
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setTasktype(task.getTasktype());
        tp.setCourseid(task.getCourseid());
        tp.setCriteria(1);//�鿴
        tp.setUserid(this.logined(request).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
            if(tList!=null&&tList.size()>0){
                response.sendRedirect(baseUrl+"tptopic?m=viewTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
            }else{
                if(this.taskPerformanceManager.doSave(tp)){
                       /*�����ӷ�*/
                    //�õ��༶ID
                    TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                    tallot.setTaskid(Long.parseLong(taskid));
                    tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                    List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                    if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                            ||clsMapList.get(0).get("CLASS_ID")==null){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(je.toJSON());return ;
                    }

                    //taskinfo:   4:�ɾ����  5����������   6:΢��Ƶ
                    //����ת��:    6             7         8
                    Integer type=0;
                    switch(taskCriList.get(0).getTasktype()){
                        case 3:     //����
                            type=1;break;
                        case 1:     //��Դѧϰ
                            type=2;break;
                        case 2:
                            type=4;
                            break;
                    }
                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();


                    String msg=null;
                        /*�����ӷ�ͨ��*/
                    if(this.tpStuScoreLogsManager.awardStuScore(taskCriList.get(0).getCourseid()
                            , Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                            , taskCriList.get(0).getTaskid()
                            , Long.parseLong(this.logined(request).getUserid() + ""),jid, type,this.logined(request).getDcschoolid())){
                        msg="�鿴���ύ�ĵ�:��ϲ��,�����1���ֺ�1����ʯ(û�е��ýӿ�)";
//                        request.getSession().setAttribute("msg",msg);
                    }else
                        System.out.println("awardScore err ");


                    response.sendRedirect(baseUrl+"tptopic?m=viewTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
                }else{
                    je.setMsg("����!��Ӳ鿴��¼ʧ��!������!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect(baseUrl+"tptopic?m=viewTopic&topicid="+themeid+"&taskid="+taskid+"&courseid="+courseid);
        }
    }


    /**
     * ���ֱ���μ�¼
     * @throws Exception
     */
    @RequestMapping(params="doAddLiveLessionRecord",method=RequestMethod.POST)
    public void doAddLiveLessionRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String taskid=request.getParameter("taskid");
        String liveaddress=request.getParameter("liveaddress");

        if(liveaddress==null||liveaddress.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        //����Ƿ��в鿴��׼
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //¼�����״̬
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setTasktype(task.getTasktype());
        tp.setCourseid(task.getCourseid());
        tp.setCriteria(1);//�鿴
        tp.setUserid(this.logined(request).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
        if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
            if(tList!=null&&tList.size()>0){
                response.sendRedirect(liveaddress);
            }else{
                if(this.taskPerformanceManager.doSave(tp)){
                       /*�����ӷ�*/
                    //�õ��༶ID
                    TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                    tallot.setTaskid(Long.parseLong(taskid));

                    tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                    List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                    if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                            ||clsMapList.get(0).get("CLASS_ID")==null){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(je.toJSON());return ;
                    }

                    //taskinfo:   4:�ɾ����  5����������   6:΢��Ƶ   10ֱ����
                    //����ת��:    6             7         8         9
                    Integer type=0;
                    switch(taskCriList.get(0).getTasktype()){
                        case 3:     //����
                            type=1;break;
                        case 1:     //��Դѧϰ
                            type=2;break;
                        case 2:
                            type=4;
                            break;
                        case 10:
                            type=9;
                            break;
                    }
                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();

                    String msg=null;
                    if(this.tpStuScoreLogsManager.awardStuScore(taskCriList.get(0).getCourseid()
                            , Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                            , taskCriList.get(0).getTaskid()
                            , Long.parseLong(this.logined(request).getUserid() + ""),jid, type,this.logined(request).getDcschoolid())){
                        System.out.println("����ֱ����.��ϲ��,�����1���ֺ�1����ʯ");
//                        request.getSession().setAttribute("msg",msg);
                    }else
                        System.out.println("awardScore err ");

                    response.sendRedirect(liveaddress);
                }else{
                    je.setMsg("����!��Ӳ鿴��¼ʧ��!������!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect(liveaddress);
        }
    }




    /**
     * �����Դѧϰ�鿴��¼
     * @throws Exception
     */
    @RequestMapping(params="doAddResViewRecord",method=RequestMethod.POST)
    public void doAddResViewRecord(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String tpresdetailid=request.getParameter("tpresdetailid");
        String courseid=request.getParameter("courseid");
        String tasktype=request.getParameter("tasktype");
        String groupid=request.getParameter("groupid");
        String taskid=request.getParameter("taskid");


        if(tpresdetailid==null||tpresdetailid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        if(tasktype==null||tasktype.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
//		if(groupid==null||groupid.trim().length()<1){
//			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(je.toJSON());return;
//		}
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        //����Ƿ��в鿴��׼
        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        t.setCriteria(1);
        List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);

        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        task.setCourseid(Long.parseLong(courseid));
        task.setTasktype(Integer.parseInt(tasktype));
        //task.setGroupid(groupid);

        //¼�����״̬
        TaskPerformanceInfo tp=new TaskPerformanceInfo();
        tp.setTaskid(task.getTaskid());
        tp.setCourseid(task.getCourseid());
        tp.setTasktype(task.getTasktype());
        tp.setCriteria(1);
        tp.setUserid(this.logined(request).getRef());
        tp.setIsright(1);
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
        String baseUrl=request.getSession().getAttribute("IP_PROC_NAME")==null?"":request.getSession().getAttribute("IP_PROC_NAME").toString();
        if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
            if(tList!=null&&tList.size()>0){
                response.sendRedirect(baseUrl+"tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
            }else{
                if(this.taskPerformanceManager.doSave(tp)){
                    /*�����ӷ�*/
                    //�õ��༶ID
                    TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                    tallot.setTaskid(Long.parseLong(taskid));

                    tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                    List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                    if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                            ||clsMapList.get(0).get("CLASS_ID")==null){
                        je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                        response.getWriter().println(je.toJSON());return ;
                    }

                    //taskinfo:   4:�ɾ����  5����������   6:΢��Ƶ
                    //����ת��:    6             7         8
                    Integer type=0;
                    switch(taskCriList.get(0).getTasktype()){
                        case 3:     //����
                            type=1;break;
                        case 1:     //��Դѧϰ
                            type=2;break;
                        case 2:
                            type=4;
                            break;
                    }
                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();


                    String msg=null;
                        /*�����ӷ�ͨ��*/
                    if(this.tpStuScoreLogsManager.awardStuScore(taskCriList.get(0).getCourseid()
                            , Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                            , taskCriList.get(0).getTaskid()
                            , Long.parseLong(this.logined(request).getUserid() + ""),jid, type,this.logined(request).getDcschoolid())){
                        msg="�鿴���ύ�ĵ�:��ϲ��,�����1���ֺ�1����ʯ(û�е��ýӿ�)";
                    //    request.getSession().setAttribute("msg",msg);
                    }else
                        System.out.println("awardScore err ");
                    response.sendRedirect(baseUrl+"tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
                }else{
                    je.setMsg("����!��Ӳ鿴��¼ʧ��!������!");
                    response.getWriter().print(je.toJSON());
                }
            }
        }else{
            response.sendRedirect(baseUrl+"tpres?toStudentIdx&courseid="+courseid+"&tpresdetailid="+tpresdetailid+"&taskid="+taskid+"&groupid="+groupid);
        }
    }


    /**
     * ��ѯ������
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxTaskSuggest",method=RequestMethod.POST)
    public void loadTaskSuggest(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||taskid==null||taskid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }
        PageResult pageResult=this.getPageResultParameter(request);
        TaskSuggestInfo suggestInfo=new TaskSuggestInfo();
        suggestInfo.setCourseid(Long.parseLong(courseid));
        suggestInfo.setTaskid(Long.parseLong(taskid));
        List<TaskSuggestInfo>suggestList=this.taskSuggestManager.getList(suggestInfo,pageResult);
        pageResult.setList(suggestList);
        je.setPresult(pageResult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����������ҳ��
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTaskSuggestList",method=RequestMethod.GET)
    public ModelAndView toTaskSuggest(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1||taskid==null||taskid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid",courseid);
        request.setAttribute("taskid",taskid);
        return new ModelAndView("/teachpaltform/task/teacher/task-suggest");
    }

    /**
     * ����������ͳ��
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toTaskPerformance",method=RequestMethod.GET)
    public ModelAndView toTaskPerformanceInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ�������
        TpTaskInfo ti = new TpTaskInfo();
        ti.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> tiList = this.tpTaskManager.getList(ti,null);
        request.setAttribute("taskInfo",tiList.get(0));
        //��ȡ����
        if(tiList.get(0).getTasktype()==3||tiList.get(0).getTasktype()==4){
            QuestionOption qo = new QuestionOption();
            qo.setQuestionid(tiList.get(0).getTaskvalueid());
            PageResult pr = new PageResult();
            pr.setPageNo(0);
            pr.setPageSize(0);
            pr.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
            for(QuestionOption o : optionList){
                if(o.getIsright()==1){
                    request.setAttribute("rightid",o.getRef());
                }
            }
        }
        //��ȡ������صİ༶
        TpTaskAllotInfo ta = new TpTaskAllotInfo();
        ta.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo> taList = this.tpTaskAllotManager.getList(ta,null);

        List<Map> classList = new ArrayList<Map>();
        for(TpTaskAllotInfo o:taList){
            if(o.getUsertype()==0){
                ClassInfo ci = new ClassInfo();
                ci.setClassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<ClassInfo> ciList = this.classManager.getList(ci,null);
                Map map = new HashMap();
                map.put("classid",ciList.get(0).getClassid());
                map.put("classname",ciList.get(0).getClassname());
                map.put("classtype",1);
                classList.add(map);
            }else if(o.getUsertype()==1){
                TpVirtualClassInfo vci = new TpVirtualClassInfo();
                vci.setVirtualclassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<TpVirtualClassInfo> vciList = this.tpVirtualClassManager.getList(vci,null);
                Map map = new HashMap();
                map.put("classid",vciList.get(0).getVirtualclassid());
                map.put("classname",vciList.get(0).getVirtualclassname());
                map.put("classtype",2);
                classList.add(map);
            }else if(o.getUsertype()==2){
                TpGroupInfo tg = new TpGroupInfo();
                tg.setGroupid(o.getUsertypeid());
                List<TpGroupInfo> tgList = this.tpGroupManager.getList(tg,null);
                Integer classid=tgList.get(0).getClassid();
                if(tgList.get(0).getClasstype()==1){
                    ClassInfo ci = new ClassInfo();
                    ci.setClassid(classid);
                    List<ClassInfo> objList = this.classManager.getList(ci,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getClassid());
                    map.put("classname",objList.get(0).getClassname());
                    map.put("classtype",8);
                    if(classList.size()>0){
                        Boolean b = false;
                        for(Map m:classList){
                            if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                b=true;
                            }
                        }
                        if(b==false)
                            classList.add(map);
                    }else{
                        classList.add(map);
                    }
                }else{
                    TpVirtualClassInfo vc = new TpVirtualClassInfo();
                    vc.setVirtualclassid(classid);
                    List<TpVirtualClassInfo> objList = this.tpVirtualClassManager.getList(vc,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getVirtualclassid());
                    map.put("classname",objList.get(0).getVirtualclassname());
                    map.put("classtype",9);
                    if(classList.size()>0){
                        Boolean b = false;
                        for(Map m:classList){
                            if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                b=true;
                            }
                        }
                        if(b==false)
                            classList.add(map);
                    }else{
                        classList.add(map);
                    }
                }


            }
        }
        request.setAttribute("courseid",taList.get(0).getCourseid());
        request.setAttribute("taskid",taskid);
        request.setAttribute("questype",questype);
        request.setAttribute("classList",classList);
        if(questype.equals("3")||questype.equals("4")){
            return new ModelAndView("/teachpaltform/task/teacher/task-performance-xz");
        }else if(questype.equals("-1")){//�Ծ�
            return new ModelAndView("/teachpaltform/task/teacher/paper-task-performance");
        }else if(questype.equals("-2")){//ֱ����
            return new ModelAndView("/teachpaltform/task/teacher/live-task-performance");
        }else{
            return new ModelAndView("/teachpaltform/task/teacher/task-performance-zg");
        }
    }

    /**
     * �õ�������ͼƬ
     * @param classid
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value="/img/{taskid}/{classid}/{classtype}/op",method = {RequestMethod.GET,RequestMethod.GET})
    public void getOptTJImg(@PathVariable("taskid") Long taskid,
                            @PathVariable("classid") Long classid,
                            @PathVariable("classtype") Long classtype,
                            HttpServletRequest request,
                            HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        if(taskid==null||classid==null||classtype==null)return;
        //�õ����ͳ��ֵ
        //����ͳ��
        List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
        if(classtype==8||classtype==9){
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum2(taskid,classid);
        }else{
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum(taskid, classid);
        }

        //��̬ƴ����Ҫ��ѡ���ֲ�����
        DecimalFormat di = new DecimalFormat("#.00");
        //����������ͳ��ͼ
        DefaultPieDataset dataset = new DefaultPieDataset();
        if(optionnumList==null||optionnumList.size()<1){
            dataset.setValue("û������",0);
        }else{
            for(Map<String,Object> tmpMap:optionnumList){
                if(tmpMap.containsKey("OPTION_TYPE")&&tmpMap.containsKey("NUM"))
                    dataset.setValue(tmpMap.get("OPTION_TYPE").toString(),Integer.parseInt(tmpMap.get("NUM").toString()));
            }
        }
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
        FileOutputStream fos = null;
        String imgRealPath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/optionPie/op"+taskid+classid+classtype+".png";
        try{
            fos = new FileOutputStream(imgRealPath);
            ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);

        }finally{
            fos.close();
        }
        //����
        System.gc();
        //��ȡͼƬ��
        UtilTool.writeImage(response,imgRealPath);
    }
    /**
     * ��ѯѧ������������
     * @throws Exception
     */
    @RequestMapping(params="xzloadStuPerformance",method=RequestMethod.POST)
    public void xzloadStuPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        String questionid=request.getParameter("questionid");
        String subjectid = request.getParameter("subjectid");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        String type=request.getParameter("classtype");
        if(classid!=null&&classid.length()>0){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }

        //�����¼
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t, clsid,Integer.parseInt(type));
        //����ͳ��
        List<Map<String,Object>> optionnumList = new ArrayList<Map<String, Object>>();
        //����ͳ��
        List<Map<String,Object>> numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        List<TpGroupInfo>tmpGroupList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            tgsinfo.setSubjectid(Integer.parseInt(subjectid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);
            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            ti.setSubjectid(Integer.parseInt(subjectid));
            tiList = this.tpGroupManager.getList(ti,null);
            //����û�õ�С��
            TpTaskAllotInfo ta=new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            ta.setUsertype(2);
            List<TpTaskAllotInfo> taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
            if(taskAllotInfoList!=null&&taskAllotInfoList.size()>0&&tiList!=null){
                for(TpTaskAllotInfo allotInfo:taskAllotInfoList){
                    for(TpGroupInfo groupInfo:tiList){
                        if(allotInfo.getUsertypeid().equals(groupInfo.getGroupid()))
                            tmpGroupList.add(groupInfo);
                    }
                }
            }
            //���С����Ա��С����
            for(int i = 0;i<tmpGroupList.size();i++){
                TpGroupStudent ts = new TpGroupStudent();
                for(int j = 0;j<tgsList.size();j++){
                    if(tmpGroupList.get(i).getGroupid().toString().equals(tgsList.get(j).getGroupid().toString())){
                        tmpGroupList.get(i).setTpgroupstudent2(tgsList.get(j));
                    }
                }
            }
        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
            optionnumList = this.taskPerformanceManager.getPerformanceOptionNum(Long.parseLong(taskid),clsid);
        }
        QuestionOption qo = new QuestionOption();
        qo.setQuestionid(Long.parseLong(questionid));
        PageResult pr = new PageResult();
        pr.setPageNo(0);
        pr.setPageSize(0);
        pr.setOrderBy("u.option_type");
        List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
        int totalNum = 0;
        for(int i =0;i<optionnumList.size();i++){
            totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
        }
        List<Map<String,Object>> option = new ArrayList<Map<String, Object>>();
        //��̬ƴ����Ҫ��ѡ���ֲ�����
        DecimalFormat di = new DecimalFormat("#.00");
        for(QuestionOption o:optionList){
            Map m = new HashMap();
            if(optionnumList.size()>0){
                for(int i =0;i<optionnumList.size();i++){
                    if(o.getOptiontype().equals(optionnumList.get(i).get("OPTION_TYPE"))){
                        m.put("OPTION_TYPE",o.getOptiontype());
                        m.put("NUM",di.format((double)Integer.parseInt(optionnumList.get(i).get("NUM").toString())/totalNum*100));
                        break;
                    }else{
                        m.put("OPTION_TYPE",o.getOptiontype());
                        m.put("NUM",0);
                    }
                }
            }else{
                m.put("OPTION_TYPE",o.getOptiontype());
                m.put("NUM",0);
            }
            option.add(m);
        }
        //����������ͳ��ͼ
        DefaultPieDataset dataset = new DefaultPieDataset();
        for(Map o:optionnumList){
            dataset.setValue(o.get("OPTION_TYPE").toString(),Integer.parseInt(o.get("NUM").toString()));
        }
        JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
        FileOutputStream fos = null;
        Date d = new Date();
        try{
            File tmpf=new File(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/optionPie/");
            if(!tmpf.exists())
                tmpf.mkdirs();
            fos = new FileOutputStream(UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/optionPie/"+d.getTime()+"taskPie.png");
            ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
        }finally{
            fos.close();
        }
        //δ�������
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        je.getObjList().add(numList);
        je.getObjList().add(option);
        je.getObjList().add(tList);
        je.getObjList().add(tmpGroupList);
        je.getObjList().add(notCompleteList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }
    /**
     * ��ѯѧ������������
     * @throws Exception
     */
    @RequestMapping(params="zgloadStuPerformance",method=RequestMethod.POST)
    public void zgloadStuPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        String type=request.getParameter("classtype");
        String subjectid = request.getParameter("subjectid");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        if(classid!=null&&classid.length()>0&&!classid.equals("null")){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }
        //�����¼
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t,clsid,Integer.parseInt(type));
        //����ͳ��
        List<Map<String,Object>> numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        List<TpGroupInfo>tmpGroupList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            tgsinfo.setSubjectid(Integer.parseInt(subjectid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);
            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            ti.setSubjectid(Integer.parseInt(subjectid));
            tiList = this.tpGroupManager.getList(ti,null);
            //����û�õ�С��
            TpTaskAllotInfo ta=new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            ta.setUsertype(2);
            List<TpTaskAllotInfo> taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
            if(taskAllotInfoList!=null&&taskAllotInfoList.size()>0&&tiList!=null){
                for(TpTaskAllotInfo allotInfo:taskAllotInfoList){
                    for(TpGroupInfo groupInfo:tiList){
                        if(allotInfo.getUsertypeid().equals(groupInfo.getGroupid()))
                            tmpGroupList.add(groupInfo);
                    }
                }
            }
            //���С����Ա��С����
            for(int i = 0;i<tmpGroupList.size();i++){
                TpGroupStudent ts = new TpGroupStudent();
                for(int j = 0;j<tgsList.size();j++){
                    if(tmpGroupList.get(i).getGroupid().toString().equals(tgsList.get(j).getGroupid().toString())){
                        tmpGroupList.get(i).setTpgroupstudent2(tgsList.get(j));
                    }
                }
            }
        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
        }
        //δ�������
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        je.getObjList().add(numList);
        je.getObjList().add(tList);
        je.getObjList().add(tmpGroupList);
        je.getObjList().add(notCompleteList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����������ͳ��
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toCjTaskPerformance",method=RequestMethod.GET)
    public ModelAndView toCjTaskPerformanceInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //��ȡ�������
        TpTaskInfo ti = new TpTaskInfo();
        ti.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> tiList = this.tpTaskManager.getList(ti,null);
        request.setAttribute("taskInfo",tiList.get(0));
        //��ȡ������صİ༶
        TpTaskAllotInfo ta = new TpTaskAllotInfo();
        ta.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo> taList = this.tpTaskAllotManager.getList(ta,null);

        List<Map> classList = new ArrayList<Map>();
        for(TpTaskAllotInfo o:taList){
            if(o.getUsertype()==0){
                ClassInfo ci = new ClassInfo();
                ci.setClassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<ClassInfo> ciList = this.classManager.getList(ci,null);
                Map map = new HashMap();
                map.put("classid",ciList.get(0).getClassid());
                map.put("classname",ciList.get(0).getClassname());
                map.put("classtype",1);
                classList.add(map);
            }else if(o.getUsertype()==1){
                TpVirtualClassInfo vci = new TpVirtualClassInfo();
                vci.setVirtualclassid(Integer.parseInt(o.getUsertypeid().toString()));
                List<TpVirtualClassInfo> vciList = this.tpVirtualClassManager.getList(vci,null);
                Map map = new HashMap();
                map.put("classid",vciList.get(0).getVirtualclassid());
                map.put("classname",vciList.get(0).getVirtualclassname());
                map.put("classtype",2);
                classList.add(map);
            }else if(o.getUsertype()==2){
                TpGroupInfo tg = new TpGroupInfo();
                tg.setGroupid(o.getUsertypeid());
                List<TpGroupInfo> tgList = this.tpGroupManager.getList(tg,null);
                Integer classid=tgList.get(0).getClassid();
                if(tgList.get(0).getClasstype()==1){
                    ClassInfo ci = new ClassInfo();
                    ci.setClassid(classid);
                    List<ClassInfo> objList = this.classManager.getList(ci,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getClassid());
                    map.put("classname",objList.get(0).getClassname());
                    map.put("classtype",8);
                    if(classList.size()>0){
                        Boolean b = false;
                        for(Map m:classList){
                            if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                b=true;
                            }
                        }
                        if(b==false)
                            classList.add(map);
                    }else{
                        classList.add(map);
                    }
                }else{
                    TpVirtualClassInfo vc = new TpVirtualClassInfo();
                    vc.setVirtualclassid(classid);
                    List<TpVirtualClassInfo> objList = this.tpVirtualClassManager.getList(vc,null);
                    Map map = new HashMap();
                    map.put("classid",objList.get(0).getVirtualclassid());
                    map.put("classname",objList.get(0).getVirtualclassname());
                    map.put("classtype",9);
                    if(classList.size()>0){
                        Boolean b = false;
                        for(Map m:classList){
                            if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                b=true;
                            }
                        }
                        if(b==false)
                            classList.add(map);
                    }else{
                        classList.add(map);
                    }
                }


            }
        }
        request.setAttribute("courseid",taList.get(0).getCourseid());
        request.setAttribute("taskid",taskid);
        request.setAttribute("classList",classList);
        return new ModelAndView("/teachpaltform/task/teacher/task-performance-cj");
    }

    /**
     * ��ѯѧ������������
     * @throws Exception
     */
    @Transactional
    @RequestMapping(params="cjloadStuPerformance",method=RequestMethod.POST)
    public void cjloadStuPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String questype = request.getParameter("questype");
        String type=request.getParameter("classtype");
        String subjectid = request.getParameter("subjectid");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        if(classid!=null&&classid.length()>0&&!classid.equals("null")){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }
        //���ȴ�����������û�����µ�0��
        Boolean transign = this.taskPerformanceManager.getCjTaskPerformanceBefor(Long.parseLong(taskid));
        if(!transign){
            transactionRollback();
            return;
        }
        //�����¼
        List<List<String>>tList=this.taskPerformanceManager.getCjTaskPerformance(Long.parseLong(taskid),Integer.parseInt(classid),Integer.parseInt(type),Integer.parseInt(subjectid));
        //����ͳ��
        List<Map<String,Object>> numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        List<TpGroupInfo>tmpGroupList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            tgsinfo.setSubjectid(Integer.parseInt(subjectid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);
            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            ti.setSubjectid(Integer.parseInt(subjectid));
            tiList = this.tpGroupManager.getList(ti,null);
            //����û�õ�С��
            TpTaskAllotInfo ta=new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            ta.setUsertype(2);
            List<TpTaskAllotInfo> taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
            if(taskAllotInfoList!=null&&taskAllotInfoList.size()>0&&tiList!=null){
                for(TpTaskAllotInfo allotInfo:taskAllotInfoList){
                    for(TpGroupInfo groupInfo:tiList){
                        if(allotInfo.getUsertypeid().equals(groupInfo.getGroupid()))
                            tmpGroupList.add(groupInfo);
                    }
                }
            }
            //���С����Ա��С����
            for(int i = 0;i<tmpGroupList.size();i++){
                TpGroupStudent ts = new TpGroupStudent();
                for(int j = 0;j<tgsList.size();j++){
                    if(tmpGroupList.get(i).getGroupid().toString().equals(tgsList.get(j).getGroupid().toString())){
                        tmpGroupList.get(i).setTpgroupstudent2(tgsList.get(j));
                    }
                }
            }
        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
        }
        //δ�������
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        //��ѯ�Ծ���ȫ��������id
        //��ȡ�������
        TpTaskInfo ti = new TpTaskInfo();
        ti.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> taskList = this.tpTaskManager.getList(ti,null);
        List<Map<String,Object>> allquesid = this.paperQuestionManager.getPaperQuesAllId(taskList.get(0).getTaskvalueid());
        String[] quesids = allquesid.get(0).get("ALLQUESID").toString().split(",");
        List quesidList = new ArrayList();
        for(int i = 0;i<quesids.length;i++){
            quesidList.add(quesids[i]);
        }
        je.getObjList().add(numList);
        je.getObjList().add(tList);
        je.getObjList().add(null);
        je.getObjList().add(notCompleteList);
        je.getObjList().add(quesidList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ΢��Ƶ�Ծ� �鿴�ش�
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="loadStuMicQuesPerformance",method=RequestMethod.GET)
    public ModelAndView loadStuMicQuesPerformance(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String questionid=request.getParameter("questionid");
        String paperid=request.getParameter("paperid");
        String taskid=request.getParameter("taskid");
        String type=request.getParameter("type"); //1:���� 2:�͹�
        if(courseid==null||courseid.trim().length()<1
                ||questionid==null||questionid.trim().length()<1
                //||type==null||type.trim().length()<1
                ||paperid==null||paperid.trim().length()<1
                ||taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }

        QuestionInfo q=new QuestionInfo();
        q.setQuestionid(Long.parseLong(questionid));
        List<QuestionInfo>quesList=this.questionManager.getList(q,null);
        if(quesList==null||quesList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        QuestionInfo ques=quesList.get(0);
        if(ques.getQuestiontype()==3||ques.getQuestiontype()==4)
            type="2";
        else if(ques.getQuestiontype()==1||ques.getQuestiontype()==2)
            type="1";

        List<Map<String,Object>>allquesidList=paperQuestionManager.getPaperQuesAllId(Long.parseLong(paperid));
        if(allquesidList==null||allquesidList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        mp.put("allquesId",allquesidList);


        mp.put("allquesId",allquesidList.get(0).get("ALLQUESID"));
        mp.put("currentQuesId",questionid);
        mp.put("courseid",courseid);
        mp.put("paperid",paperid);
        mp.put("taskid",taskid);

        StuPaperQuesLogs logs=new StuPaperQuesLogs();
        logs.setPaperid(Long.parseLong(paperid));
        logs.setQuesid(Long.parseLong(questionid));
        logs.setTaskid(Long.parseLong(taskid));
        List<StuPaperQuesLogs>logsList=this.stuPaperQuesLogsManager.getList(logs,null);
        mp.put("logList",logsList);
        if(type.equals("1")){
            return new ModelAndView("/teachpaltform/task/teacher/mic-paper-zg", mp);
        }else{
            List<Map<String,Object>> numList =this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),null,null);
            List<Map<String,Object>> optionnumList =this.taskPerformanceManager.getMicPerformanceOptionNum(Long.parseLong(taskid),Long.parseLong(questionid));
            QuestionOption qo = new QuestionOption();
            qo.setQuestionid(Long.parseLong(questionid));
            PageResult pr = new PageResult();
            pr.setPageNo(0);
            pr.setPageSize(0);
            pr.setOrderBy("u.option_type");
            List<QuestionOption> optionList = this.questionOptionManager.getList(qo,pr);
            int totalNum = 0;
            for(int i =0;i<optionnumList.size();i++){
                totalNum+=Integer.parseInt(optionnumList.get(i).get("NUM").toString());
            }
            List<Map<String,Object>> option = new ArrayList<Map<String, Object>>();
            //��̬ƴ����Ҫ��ѡ���ֲ�����
            DecimalFormat di = new DecimalFormat("#.00");
            for(QuestionOption o:optionList){
                Map m = new HashMap();
                if(optionnumList.size()>0){
                    for(int i =0;i<optionnumList.size();i++){
                        if(o.getOptiontype().equals(optionnumList.get(i).get("OPTION_TYPE"))){
                            m.put("OPTION_TYPE",o.getOptiontype());
                            m.put("NUM",di.format((double)Integer.parseInt(optionnumList.get(i).get("NUM").toString())/totalNum*100));
                            break;
                        }else{
                            m.put("OPTION_TYPE",o.getOptiontype());
                            m.put("NUM",0);
                        }
                    }
                }else{
                    m.put("OPTION_TYPE",o.getOptiontype());
                    m.put("NUM",0);
                }
                option.add(m);
            }
            //����������ͳ��ͼ
            DefaultPieDataset dataset = new DefaultPieDataset();
            for(Map o:optionnumList){
                dataset.setValue(o.get("OPTION_TYPE").toString(),Integer.parseInt(o.get("NUM").toString()));
            }
            JFreeChart chart = ChartFactory.createPieChart("", dataset, true, false, false);
            FileOutputStream fos = null;

            try{
                fos = new FileOutputStream(request.getRealPath("/")+"images/taskMicPie.png");
                ChartUtilities.writeChartAsPNG(fos, chart, 193, 140);
            }finally{
                fos.close();
            }

            mp.put("numList",numList);
            mp.put("optionList",option);

            Integer right=0;
            if(option!=null)
            {
                for(StuPaperQuesLogs p:logsList){
                    if(p.getIsright()!=null&&p.getIsright().toString().equals("1"))
                        right+=1;
                }
            }
            mp.put("right",right);
            return new ModelAndView("/teachpaltform/task/teacher/mic-paper-kg", mp);
        }
    }
    /**
     * ��ѯѧ���Ծ�����������
     * @throws Exception
     */
    @RequestMapping(params="loadPaperPerformance",method=RequestMethod.POST)
    public void loadPaperPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        String type=request.getParameter("classtype");
        String orderstr=request.getParameter("orderstr");
        if(classid==null||classid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TaskPerformanceInfo t=new TaskPerformanceInfo();
        t.setTaskid(Long.parseLong(taskid));
        Long clsid=null;
        if(classid!=null&&classid.length()>0&&!classid.equals("null")){
            clsid=Long.parseLong(classid);
        }else{
            clsid=Long.parseLong("0");
        }
        if(orderstr!=null&&orderstr.trim().length()>0)
            t.setOrderstr(orderstr);
        else
            t.setOrderstr("t.c_time desc");
        //δ�������
        TpTaskInfo task=new TpTaskInfo();
        task.setTaskid(Long.parseLong(taskid));
        Integer cid=null;
        if(classid!=null&&!classid.equals("0"))
            cid=Integer.parseInt(classid);

        List<UserInfo>notCompleteList=this.userManager.getUserNotCompleteTask(task.getTaskid(),null,cid,"1");
        //�����¼
        List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getPerformListByTaskid(t,clsid,Integer.parseInt(type));
        //����ͳ��
        List<Map<String,Object>> numList;
        numList = new ArrayList<Map<String, Object>>();
        List<TpGroupInfo> tiList=new ArrayList<TpGroupInfo>();
        List<TpGroupInfo>tmpGroupList=new ArrayList<TpGroupInfo>();
        if(Integer.parseInt(type)==8||Integer.parseInt(type)==9){
            numList=this.taskPerformanceManager.getPerformanceNum2(Long.parseLong(taskid),clsid);
            TpGroupStudent tgsinfo = new TpGroupStudent();
            tgsinfo.setClassid(Integer.parseInt(classid));
            List<TpGroupStudent> tgsList = this.tpGroupStudentManager.getGroupStudentByClass(tgsinfo,null);


            TpGroupInfo ti = new TpGroupInfo();
            ti.setClassid(Integer.parseInt(classid));
            tiList = this.tpGroupManager.getList(ti,null);


            TpTaskAllotInfo ta=new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            ta.setUsertype(2);
            List<TpTaskAllotInfo> taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
            if(taskAllotInfoList!=null&&taskAllotInfoList.size()>0&&tiList!=null){
                for(TpTaskAllotInfo allotInfo:taskAllotInfoList){
                    for(TpGroupInfo groupInfo:tiList){
                        if(allotInfo.getUsertypeid().equals(groupInfo.getGroupid()))
                            tmpGroupList.add(groupInfo);
                    }
                }
            }

            if(tmpGroupList.size()>0){
                for(int i = 0;i<tmpGroupList.size();i++){
                    for(int j = 0;j<tgsList.size();j++){
                        if(tmpGroupList.get(i).getGroupid().toString().equals(tgsList.get(j).getGroupid().toString())){
                            tmpGroupList.get(i).setTpgroupstudent2(tgsList.get(j));
                        }
                    }
                }
            }

        }else{
            numList=this.taskPerformanceManager.getPerformanceNum(Long.parseLong(taskid),clsid,Integer.parseInt(type));
        }
        je.getObjList().add(numList);
        je.getObjList().add(tList);
        je.getObjList().add(tmpGroupList);
        je.getObjList().add(notCompleteList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����ѧ���ˣ��鿴��������������ҳ��
     * */
    @RequestMapping(params="m=tostuSelfPerformance",method=RequestMethod.GET)
    public ModelAndView toloadStuSelfPerformance(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String uid=request.getParameter("userid");
        String classid = request.getParameter("classid");
        Integer userid=this.logined(request).getUserid();
        if(uid!=null&&uid.trim().length()>0)
            userid=Integer.parseInt(uid.trim());
        List<Map<String,Object>> performanceList = this.taskPerformanceManager.getStuSelfPerformance(userid,Long.parseLong("0"),0,termid,Integer.parseInt(subjectid));
        List<Map<String,Object>> courseObj = new ArrayList<Map<String, Object>>();
        for(Map o:performanceList){
            Map m = new HashMap();
            m.put("courseid",o.get("COURSE_ID"));
            m.put("coursename",o.get("COURSE_NAME"));
            courseObj.add(m);
        }
        mp.put("course",courseObj);
        mp.put("userid",userid);
        UserInfo ui = new UserInfo();
        ui.setUserid(userid);
        List<UserInfo> uiList = this.userManager.getList(ui,null);
        if(uiList!=null){
            String username = uiList.get(0).getRealname();
            mp.put("username",username);
        }
        //��ѯ�༶����
        ClassUser cu = new ClassUser();
        cu.setClassid(Integer.parseInt(classid));
        cu.setRelationtype("ѧ��");
        cu.setSubjectid(Integer.parseInt(subjectid));
        cu.setCompletenum(1);//��ѯ���������
        List<ClassUser> stuList = this.classUserManager.getList(cu, null);
        mp.put("students", stuList);
        //��ѯС������
        List<TpGroupInfo> groupList = this.tpGroupManager.getMyGroupList(
                Integer.parseInt(classid), 1, termid, null, userid,Integer.parseInt(subjectid));
        if(groupList!=null && groupList.size()>0){
            for(TpGroupInfo g:groupList){
                TpGroupStudent gs = new TpGroupStudent();
                gs.setGroupid(g.getGroupid());
                List<TpGroupStudent> groupStudentList = this.tpGroupStudentManager.getList(gs, null);
                g.setTpgroupstudent(groupStudentList);
            }
        }
        mp.put("gsList",groupList);
        return new ModelAndView("/teachpaltform/task/student/task-stu-performance", mp);
    }

    /**
     * ��ʦ�ˣ��鿴ѧ����������������ҳ��
     * */
    @RequestMapping(params="m=showStuSelfPerformance",method=RequestMethod.GET)
    public ModelAndView teacherToStuselfPerformance(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        Integer userid=Integer.parseInt(request.getParameter("userid"));
        List<Map<String,Object>> performanceList = this.taskPerformanceManager.getStuSelfPerformance(userid,Long.parseLong("0"),0,termid,Integer.parseInt(subjectid));
        List<Map<String,Object>> courseObj = new ArrayList<Map<String, Object>>();
        for(Map o:performanceList){
            Map m = new HashMap();
            m.put("courseid",o.get("COURSE_ID"));
            m.put("coursename",o.get("COURSE_NAME"));
            courseObj.add(m);
        }
        mp.put("course",courseObj);
        return new ModelAndView("/teachpaltform/task/teacher/stu-task-performance", mp);
    }

    /**
     * ѧ���ˣ��鿴��������������
     * */
    @RequestMapping(params="m=stuSelfPerformance",method=RequestMethod.POST)
    public void loadStuSelfPerformance(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid = request.getParameter("courseid");
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String userid=request.getParameter("userid");
        Integer uid=this.logined(request).getUserid();
        if(userid!=null&&userid.trim().length()>0)
            uid=Integer.parseInt(userid);
        if(courseid==null)
            courseid="0";
        List<Map<String,Object>> performanceList = this.taskPerformanceManager.getStuSelfPerformance(uid,Long.parseLong(courseid),1,termid,Integer.parseInt(subjectid));
        Integer totalNum=performanceList.size();
        Integer completeNum=0;
        Integer unBeginNum=0;
        Integer endUnCompleteNum=0;
        for(Map o :performanceList){
            String bdatestr=o.get("B_TIME").toString();
            String edatestr=o.get("E_TIME").toString();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currTime = df.parse(df.format(new Date()));
            Date bDate = df.parse(bdatestr);
            Date eDate=df.parse(edatestr);
            if(bDate.after(currTime)){  //before
                unBeginNum++;
            }
            if(Integer.parseInt(o.get("STATUS").toString())>0){
                completeNum++;
            }
            if(Integer.parseInt(o.get("STATUS").toString())==0&&eDate.before(currTime)){
                endUnCompleteNum++;
            }
        }
        Integer unCompleteNum=totalNum-completeNum-unBeginNum-endUnCompleteNum;
        List<Map<String,Object>> performanceListNum=new ArrayList<Map<String, Object>>();
        Map o = new HashMap();
        o.put("totalNum",totalNum);
        o.put("completeNum",completeNum);
        o.put("unBeginNum",unBeginNum);
        o.put("endUnCompleteNum",endUnCompleteNum);
        o.put("unCompleteNum",unCompleteNum);
        performanceListNum.add(o);
        //List<Map<String,Object>> performanceListNum=this.taskPerformanceManager.getStuSelfPerformanceNum(this.logined(request).getUserid(),Long.parseLong(courseid),1,termid,Integer.parseInt(subjectid));
        je.getObjList().add(performanceList);
        je.getObjList().add(performanceListNum);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * �ָ�����վ�е�����
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doRestoreTask",method=RequestMethod.POST)
    public void doRestoreTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String taskid=request.getParameter("taskid");
        if(taskid==null||taskid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        TpTaskInfo t=new TpTaskInfo();
        t.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>taskList=this.tpTaskManager.getList(t, null);
        if(taskList==null||taskList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;




        TpTaskInfo tmpTask=taskList.get(0);

        TpTaskInfo sel=new TpTaskInfo();
        sel.setCourseid(tmpTask.getCourseid());
        //��ѯû����ɾ��������
        sel.setSelecttype(1);
        sel.setLoginuserid(this.logined(request).getUserid());
        sel.setStatus(1);

        //�ѷ���������
        List<TpTaskInfo>taskReleaseList=this.tpTaskManager.getTaskReleaseList(sel, null);
        Integer orderIdx=1;
        if(taskReleaseList!=null&&taskReleaseList.size()>0)
            orderIdx+=taskReleaseList.size();


        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setStatus(1);
        taskInfo.setTaskid(Long.parseLong(taskid));
        taskInfo.setOrderidx(orderIdx);
        sql=new StringBuilder();
        objList=this.tpTaskManager.getUpdateSql(taskInfo,sql);
        if(sql!=null&&sql.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sql.toString());
        }
        if(this.tpTaskManager.doExcetueArrayProc(sqlStrList,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }


    /**
     * ��δ��������ѧ��������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doSendTaskMsg",method=RequestMethod.POST)
    public void doSendTaskMsg(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String uidArray=request.getParameter("uidArray");
        String taskid=request.getParameter("taskid");
        String usertypeid=request.getParameter("usertypeid");
        if(uidArray==null||uidArray.trim().length()<1
                ||taskid==null||taskid.trim().length()<1
            //||usertypeid==null||usertypeid.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        String[] useridArray=uidArray.split(",");
        String userName="";
        for(String uid:useridArray){
            UserInfo u=new UserInfo();
            u.setUserid(Integer.parseInt(uid));
            List<UserInfo>userInfoList=this.userManager.getList(u,null);
            if(userInfoList!=null&&userInfoList.size()>0){
                if(userName.length()>0)
                    userName+=";";
                userName+=userInfoList.get(0).getUsername();
            }
        }

        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo>tpTaskInfoList=this.tpTaskManager.getList(taskInfo,null);
        if(tpTaskInfoList==null||tpTaskInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo tmpTask=tpTaskInfoList.get(0);

        TpTaskAllotInfo ta=new TpTaskAllotInfo();
        ta.setTaskid(tmpTask.getTaskid());
        if(usertypeid!=null&&!usertypeid.equals("null")&&!"0".equals(usertypeid))
            ta.setUsertypeid(Long.parseLong(usertypeid));
        List<TpTaskAllotInfo>taskAllotInfoList=this.tpTaskAllotManager.getList(ta,null);
        if(tpTaskInfoList==null||tpTaskInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        for (TpTaskAllotInfo tmpTaskAllot:taskAllotInfoList){
            //����1����Դѧϰ Ұ������.wmv ��ֹʱ����XXX���˴����嵽���Ӽ��ɣ����������㾡���������

            String content="";
            try {
                content="����"+tmpTask.getTaskTypeName()+" "+tmpTask.getTaskobjname()+" ��ֹʱ����"+tmpTaskAllot.getEtimeString()+"���뾡���������!";
            }catch (Exception e){
                content="";
            }
            SmsInfo smsInfo=new SmsInfo();
            smsInfo.setReceiverlist(userName);
            smsInfo.setSmstitle("����δ�������");
            smsInfo.setSenderid(this.logined(request).getUserid());
            smsInfo.setSmscontent(content);
            smsInfo.setSmsstatus(1);
            sql=new StringBuilder();
            objList=this.smsManager.getSaveSql(smsInfo,sql);
            if(sql!=null&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }
        if(this.smsManager.doExcetueArrayProc(sqlListArray,objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * ��ȡС������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadGroupStudent",method=RequestMethod.POST)
    public void loadGroupStudent(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String groupid=request.getParameter("groupid");
        if(groupid==null||groupid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpGroupStudent gs=new TpGroupStudent();
        gs.setGroupid(Long.parseLong(groupid));
        List<TpGroupStudent>groupStudentList=this.tpGroupStudentManager.getList(gs,null);
        je.setObjList(groupStudentList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * ����im������ͳ������ҳ��
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=toImTaskInfo",method={RequestMethod.POST,RequestMethod.GET})
    public ModelAndView toImTaskInfo(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid = request.getParameter("taskid");
        String classid = request.getParameter("classid");
        String type = request.getParameter("type");//1����ʦ 2��ѧ�� �����жϰ༶�Ƿ���ʾ
        //
        if(taskid==null||classid==null||type==null){
            je.setMsg("ȱ�ٲ���");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //���Ȳ�ѯ��������
        TpTaskInfo taskInfo = new TpTaskInfo();
        taskInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskInfo> taskInfoList = this.tpTaskManager.getList(taskInfo,null);
        if(taskInfoList==null||taskInfoList.size()==0){
            je.setMsg("��ǰ���񲻴��ڣ���ˢ��ҳ������");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("taskinfo",taskInfoList.get(0));
        //��������ѯ����Ļش��б�
        List<Map<String,Object>> taskUserRecord = new ArrayList<Map<String, Object>>();
        List<Map<String,Object>> returnUserRecord = new ArrayList<Map<String, Object>>();
        Map returnUserMap = null;
        if(type.equals("1")){
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(Long.parseLong(taskid),Integer.parseInt(classid),Integer.parseInt("0"),null);
        }else{
            taskUserRecord = this.imInterfaceManager.getTaskUserRecord(Long.parseLong(taskid),Integer.parseInt(classid),Integer.parseInt("0"),this.logined(request).getUserid());
        }
        if(taskUserRecord!=null&&taskUserRecord.size()>0){
            StringBuilder jids = new StringBuilder();
            jids.append("[");
            for(int i = 0;i<taskUserRecord.size();i++){
                returnUserMap = new HashMap();
                String replyDate = UtilTool.convertTimeForTask(Integer.parseInt(taskUserRecord.get(i).get("REPLYDATE").toString()),taskUserRecord.get(i).get("C_TIME").toString());
                returnUserMap.put("replyDate",replyDate);
                returnUserMap.put("jid",taskUserRecord.get(i).get("JID"));
                returnUserMap.put("replyDetail",taskUserRecord.get(i).get("REPLYDETAIL"));
                returnUserMap.put("replyAttach",taskUserRecord.get(i).get("REPLYATTACH"));
                returnUserMap.put("replyAttachType",taskUserRecord.get(i).get("REPLYATTACHTYPE"));
                if(taskUserRecord.get(i).get("JID")!=null){
                    jids.append("{\"jid\":"+Integer.parseInt(taskUserRecord.get(i).get("JID").toString())+"},");
                }else{
                    returnUserMap.put("uPhoto","http://attach.etiantian.com/ett20/study/common/upload/unknown.jpg");
                    returnUserMap.put("uName",taskUserRecord.get(i).get("realname"));
                }
                returnUserRecord.add(returnUserMap);
            }
            String jidstr = jids.toString().substring(0,jids.toString().lastIndexOf(","))+"]";
            String url="http://192.168.10.26:8008/study-im-service-1.0/queryPhotoAndRealName.do";
            //String url = "http://wangjie.etiantian.com:8080/queryPhotoAndRealName.do";
            HashMap<String,String> signMap = new HashMap();
            signMap.put("userList",jidstr);
            signMap.put("schoolId",this.logined(request).getDcschoolid()+"");
            signMap.put("srcJid",this.logined(request).getEttuserid()+"");
            signMap.put("userType","3");
            signMap.put("timestamp",""+System.currentTimeMillis());
            String signture = UrlSigUtil.makeSigSimple("queryPhotoAndRealName.do", signMap, "*ETT#HONER#2014*");
            signMap.put("sign",signture);
            JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
            int t = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
            if(t==1){
                Object obj = jsonObject.containsKey("data")?jsonObject.get("data"):null;
                JSONArray jr = JSONArray.fromObject(obj);
                if(jr!=null&&jr.size()>0){
                    for(int i = 0;i<jr.size();i++){
                        JSONObject jo = jr.getJSONObject(i);
                        for(int j = 0;j<returnUserRecord.size();j++){
                            returnUserMap = new HashMap();
                            if(jo.getInt("jid")==Integer.parseInt(returnUserRecord.get(j).get("jid").toString())){
                                returnUserRecord.get(j).put("uPhoto", jo.getString("headUrl"));
                                returnUserRecord.get(j).put("uName", jo.getString("realName"));
                            }
                        }
                    }
                }
            }
        }
        request.setAttribute("replyList",returnUserRecord);
        if(type.equals("1")){
            //��ȡ������صİ༶
            TpTaskAllotInfo ta = new TpTaskAllotInfo();
            ta.setTaskid(Long.parseLong(taskid));
            List<TpTaskAllotInfo> taList = this.tpTaskAllotManager.getList(ta,null);

            List<Map> classList = new ArrayList<Map>();
            for(TpTaskAllotInfo o:taList){
                if(o.getUsertype()==0){
                    ClassInfo ci = new ClassInfo();
                    ci.setClassid(Integer.parseInt(o.getUsertypeid().toString()));
                    List<ClassInfo> ciList = this.classManager.getList(ci,null);
                    Map map = new HashMap();
                    map.put("classid",ciList.get(0).getClassid());
                    map.put("classname",ciList.get(0).getClassname());
                    map.put("classtype",1);
                    classList.add(map);
                }else if(o.getUsertype()==2){//С�飬Ҫ��ȡС�����ڵİ༶
                    TpGroupInfo tg = new TpGroupInfo();
                    tg.setGroupid(o.getUsertypeid());
                    List<TpGroupInfo> tgList = this.tpGroupManager.getList(tg,null);
                    Integer clsid=tgList.get(0).getClassid();
                    if(tgList.get(0).getClasstype()==1){
                        ClassInfo ci = new ClassInfo();
                        ci.setClassid(clsid);
                        List<ClassInfo> objList = this.classManager.getList(ci,null);
                        Map map = new HashMap();
                        map.put("classid",objList.get(0).getClassid());
                        map.put("classname",objList.get(0).getClassname());
                        map.put("classtype",2);
                        if(classList.size()>0){
                            Boolean b = false;
                            for(Map m:classList){
                                if(Integer.parseInt(m.get("classid").toString())==Integer.parseInt(map.get("classid").toString())){
                                    b=true;
                                }
                            }
                            if(b==false)
                                classList.add(map);
                        }else{
                            classList.add(map);
                        }
                    }
                }
            }
            request.setAttribute("classlist",classList);
        }
        request.setAttribute("type",type);
        return new ModelAndView("/teachpaltform/task/student/im-task-detail");
    }
    @RequestMapping(params="m=stuQuesAnserList")
    public ModelAndView stuToQuesAnswerList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        //��֤����
        if(taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //���ҵ�ǰ�༶
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        tk=tkList.get(0);
        Integer questype=Integer.parseInt(tk.getTasktype().toString());
        if(questype!=7&&questype!=8&&questype!=9){
            jsonEntity.setMsg("���󣬸��������ƶ�������ֻ���ƶ���������ܽ���!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //��ѯ�༶
        List<Map<String,Object>> objMapList=this.tpTaskAllotManager.getTaskAllotBLClassId(Long.parseLong(taskid.trim()),this.logined(request).getUserid());
        String classid=null;
        if(objMapList!=null&&objMapList.get(0)!=null&&objMapList.get(0).containsKey("CLASS_ID")){
            classid=objMapList.get(0).get("CLASS_ID").toString();
        }
        if(classid==null||classid.trim().length()<1){
            jsonEntity.setMsg("���󣬰༶��ȡʧ��!!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        mp.put("classid",classid);
        return new ModelAndView("/teachpaltform/task/im_stu_task",mp);
    }

    @RequestMapping(params="m=teaQuesAnserList")
    public ModelAndView teaToQuesAnswerList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        //��֤����
        if(taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //���ҵ�ǰ�༶
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        tk=tkList.get(0);
        Integer questype=Integer.parseInt(tk.getTasktype().toString());
        if(questype!=7&&questype!=8&&questype!=9){
            jsonEntity.setMsg("���󣬸��������ƶ�������ֻ���ƶ���������ܽ���!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //��ѯ�༶
        List<ClassInfo> clsList=new ArrayList<ClassInfo>();
        //��ѯ��ǰ�����͵Ķ���
        TpTaskAllotInfo tpTaskAllotInfo = new TpTaskAllotInfo();
        tpTaskAllotInfo.setTaskid(Long.parseLong(taskid));
        List<TpTaskAllotInfo> tpTaskAllotInfoList = this.tpTaskAllotManager.getList(tpTaskAllotInfo,null);
        for(TpTaskAllotInfo obj:tpTaskAllotInfoList){
            if(obj.getUsertype()==0){
                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(obj.getUsertypeid().intValue());
                    List<ClassInfo> clstmpList=this.classManager.getList(cls,null);
                    if(clstmpList!=null&&clstmpList.size()>0)
                            clsList.add(clstmpList.get(0));

            }else if(obj.getUsertype()==2){
                //���Ȳ�ѯ��ǰ����İ༶�µ�С��
                TpGroupInfo tpGroupInfo = new TpGroupInfo();
                tpGroupInfo.setGroupid(obj.getUsertypeid());
                List<TpGroupInfo> tpGroupInfoList=this.tpGroupManager.getList(tpGroupInfo,null);
                if(tpGroupInfoList!=null&&tpGroupInfoList.size()>0){
                    ClassInfo cls=new ClassInfo();
                    cls.setClassid(tpGroupInfoList.get(0).getClassid());
                    List<ClassInfo> clstmpList=this.classManager.getList(cls,null);
                    if(clstmpList!=null&&clstmpList.size()>0&&!clstmpList.contains(clstmpList.get(0)))
                        clsList.add(clstmpList.get(0));
                }
            }
        }
        mp.put("clsList",clsList);
        return new ModelAndView("/teachpaltform/task/im_tea_task",mp);
    }

    /**
     * IM�����񣬽���ѧ���ش��б�
     * @return
     */
    @RequestMapping(params = "m=toAnswerList")
    public ModelAndView toQuesAserList(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String clsid=request.getParameter("classid");
        String taskid=request.getParameter("taskid");
        JsonEntity jsonEntity=new JsonEntity();
        //��֤����
        if(taskid==null||taskid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        if(clsid==null){
            TpTaskAllotInfo tpallot=new TpTaskAllotInfo();
            tpallot.setTaskid(Long.parseLong(taskid));
            List<TpTaskAllotInfo> tpallotList=this.tpTaskAllotManager.getList(tpallot,null);
            if(tpallotList==null||tpallotList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            tpallot=tpallotList.get(0);
            if(tpallot.getUsertype()==0){
                clsid=tpallot.getUsertypeid()+"";
            }else if(tpallot.getUsertype()==2){
                TpGroupInfo tg=new TpGroupInfo();
                tg.setGroupid(tpallot.getUsertypeid());
                List<TpGroupInfo> tgList=this.tpGroupManager.getList(tg,null);
                if(tgList==null||tgList.size()>0){
                    jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                    response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
                }
                clsid=tgList.get(0).getClassid()+"";
            }
        }
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid.trim()));
        List<TpTaskInfo> tkList=this.tpTaskManager.getList(tk,null);
        if(tkList==null||tkList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        tk=tkList.get(0);
        Integer questype=Integer.parseInt(tk.getTasktype().toString());
        if(questype!=7&&questype!=8&&questype!=9){
            jsonEntity.setMsg("���󣬸��������ƶ�������ֻ���ƶ���������ܽ���!");
            response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        QuestionAnswer qa=new QuestionAnswer();
        qa.setClassid(Integer.parseInt(clsid));
        qa.setTaskid(Long.parseLong(taskid.trim()));
        List<QuestionAnswer> qaList=this.questionAnswerManager.getList(qa,null);
        mp.put("qaList",qaList);
        mp.put("tk",tk);
        return new ModelAndView("/teachpaltform/task/im_task_detail",mp);
    }

    /**
     * ��ת��ֱ���β���ҳ��
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toZhiBoKe",method= {RequestMethod.GET,RequestMethod.POST})
    public void toZhiboke(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid = request.getParameter("taskid");
        String courseid = request.getParameter("courseid");
        //��֤ר��
        TpCourseInfo tc = new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
        //
        TpTaskInfo tk=new TpTaskInfo();
        tk.setTaskid(Long.parseLong(taskid));
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<TpTaskInfo> tpTaskList=this.tpTaskManager.getList(tk,presult);
        String url=UtilTool.utilproperty.getProperty("GET_ETT_LIVE_ADDRESS");
        HashMap<String,String> signMap = new HashMap();
        signMap.put("courseName",tcList.get(0).getCoursename());
        signMap.put("courseId",tpTaskList.get(0).getTaskid().toString().replace("-",""));
        signMap.put("userId",this.logined(request).getUserid()+"");
        signMap.put("userName",this.logined(request).getRealname()!=null?this.logined(request).getRealname():this.logined(request).getUsername());
        signMap.put("rec","3");
        signMap.put("srcId","90");
        signMap.put("timestamp",System.currentTimeMillis()+"");
        String signture = UrlSigUtil.makeSigSimple("getTutorUrl.do",signMap,"*ETT#HONER#2014*");
        signMap.put("sign",signture);
        JSONObject jsonObject = UtilTool.sendPostUrl(url,signMap,"utf-8");
        int type = jsonObject.containsKey("result")?jsonObject.getInt("result"):0;
        if(type==1){
            String liveurl= jsonObject.containsKey("data")?jsonObject.getString("data"):"";
            je.setType("success");
            je.getObjList().add(liveurl);
        }else{
            je.setType("error");
        }
        response.getWriter().print(je.toJSON());
    }
}
