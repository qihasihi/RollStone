package com.school.control.teachpaltform.interactive;

import com.school.control.base.BaseController;
import com.school.entity.DictionaryInfo;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpCourseTeachingMaterial;
import com.school.entity.teachpaltform.TpOperateInfo;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.ITpCourseTeachingMaterialManager;
import com.school.manager.inter.teachpaltform.ITpOperateManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-1-13.
 */
@Controller
@RequestMapping(value="/tptopic")
public class TpTopicController extends BaseController<TpTopicInfo>{
    @Autowired
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    @Autowired
    private ITpTopicThemeManager tpTopicThemeManager;
    @Autowired
    private ITpTopicManager tpTopicManager;
    @Autowired
    private IDictionaryManager dictionaryManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private ITpOperateManager tpOperateManager;
    /**
     * ������ҳ���ֽ�ɫ
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="m=index",method= RequestMethod.GET)
    public ModelAndView toTopicIndex(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        TpTopicInfo topicInfo=this.getParameter(request,TpTopicInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        //��֤courseid
        if(topicInfo.getCourseid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.get("PARAM_ERROR").toString());
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //��֤courseid
        TpCourseInfo tpCourseInfo=new TpCourseInfo();
        tpCourseInfo.setCourseid(topicInfo.getCourseid());
        PageResult presult=new PageResult();
        presult.setPageNo(1);presult.setPageSize(1);
        List<TpCourseInfo> courseList=this.tpCourseManager.getTchCourseList(tpCourseInfo,presult);//this.  //��ѯ���ݿ⣬�õ�
        if(courseList==null||courseList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.get("ENTITY_NOT_EXISTS").toString());
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }

        TpCourseInfo course=courseList.get(0);
        mp.put("locastatus", course.getLocalstatus());  //�õ�ר���״̬ ������

        mp.put("courseid", topicInfo.getCourseid());
        mp.put("coruseName",course.getCoursename());
        //�����֤
        String roleStr="TEACHER";
        Integer roleInt=1;
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            roleStr="STUDENT";
            roleInt=2;
        }
        mp.put("roleStr", roleStr);
        //��ҳ��ѯ����
//        presult=new PageResult();
//        presult.setPageNo(1);
//        presult.setPageSize(10);
//        //�õ���ʾ����������
//        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topicInfo,presult);
//        mp.put("topicList",topicList);
//        mp.put("presult", presult);





        //�õ���ҳͳ��
        List<Map<String,Object>> objMap=this.tpTopicManager.getTopicStaticesPageIdx(course.getCourseid(),this.logined(request).getRef(),roleInt);
        if(objMap!=null&&objMap.size()>0)
            mp.put("staticObj", objMap.get(0));

        Integer subjectid=null;
        //�õ�ѧ��
        //��ȡ��ǰר��̲�
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(topicInfo.getCourseid());
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid();

        //��ѯ��ͷ
        TpCourseInfo tcentity=new TpCourseInfo();
        Object gradeid=request.getSession().getAttribute("session_grade");
        if(gradeid!=null&&gradeid.toString().length()>0)
                tcentity.setGradeid(Integer.parseInt(gradeid.toString()));
        Object subject=request.getSession().getAttribute("session_subject");
        if(gradeid!=null&&gradeid.toString().length()>0)
            tcentity.setSubjectid(Integer.parseInt(subject.toString()));
        tcentity.setUserid(this.logined(request).getUserid());
        tcentity.setTermid(courseList.get(0).getTermid());
        tcentity.setLocalstatus(1);

        //��ݴ���
        if(roleStr.equals("TEACHER")){  //��ʦ���
            mp.put("courseList",this.tpCourseManager.getCourseList(tcentity, null));
        }else{                          //ѧ�����
            mp.put("courseList",this.tpCourseManager.getStuCourseList(tcentity,null));
        }
        return new ModelAndView("/interactive/topic/index",mp);
    }
    /**
     * AJAX��ѯ
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxtopiclist",method=RequestMethod.POST)
    public void getAjaxTopicList(HttpServletRequest request,HttpServletResponse response) throws Exception{
        TpTopicInfo topicInfo=this.getParameter(request,TpTopicInfo.class);
        PageResult presult=this.getPageResultParameter(request);
        //�õ���ʾ����������
        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topicInfo,presult);

        JsonEntity jeEntity=new JsonEntity();
        jeEntity.setType("success");
        // jeEntity.setObjList(topicList);
        presult.setList(topicList);
        jeEntity.setPresult(presult);
        response.getWriter().print(jeEntity.toJSON());

    }

    /**
     * ��������޸�ҳ��
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="m=toAdmin",method= RequestMethod.GET)
    public ModelAndView toAddOrUpdate(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        TpTopicInfo topic=this.getParameter(request,TpTopicInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        if(topic.getCourseid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //��֤ר��
        TpCourseInfo tcourseinfo=new TpCourseInfo();
        tcourseinfo.setCourseid(topic.getCourseid());
        List<TpCourseInfo> tpcourseList=this.tpCourseManager.getTsList(tcourseinfo, null);
        if(tpcourseList==null||tpcourseList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //   mp.put("course",tpcourseList.get(0));
        // �洢��ǰר��
        mp.put("courseid",topic.getCourseid());
        //�޸�
        if(topic!=null&&topic.getTopicid()!=null){
            List<TpTopicInfo> topicList=this.tpTopicManager.getList(topic, null);
            if(topicList==null||topicList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            mp.put("tpc",topicList.get(0));
            //mp.put("courseList", this.tpTopicManager.getListTopicIndex(this.logined(request).getRef(),));
        }

        //�õ���Ҫ����
        List<DictionaryInfo> topicStateList=this.dictionaryManager.getDictionaryByType("ACTIVE_TOPIC_STATE");
        mp.put("topicState",topicStateList);
        return new ModelAndView("/interactive/topic/topic-admin",mp);
    }

    /**
     * ��������޸�ҳ��
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params="m=doSaveTopic",method= RequestMethod.POST)
    public void doSaveTopic(HttpServletRequest request,HttpServletResponse response)throws Exception{
        TpTopicInfo topic=this.getParameter(request,TpTopicInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        //ר������ж�
        if(topic.getCourseid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());return;
        }
        //�����ж�
        if(!UtilTool.nullOrEmpty(topic.getTopictitle())
                ||topic.getStatus()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());return;
        }
        //�õ���ID
        Long nextTopicid=this.tpTopicManager.getNextId(true);

        topic.setTopicid(nextTopicid);
        topic.setCuserid(this.logined(request).getUserid());
        //��֯��Ӳ�����־
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> paramList=this.tpTopicManager.getSaveSql(topic,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paramList);
        }
        //��Ӳ�����־
        sqlbuilder=new StringBuilder();
        paramList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef()
                ,"TP_TOPIC_INFO",nextTopicid+"",null,null,"ADD","���",sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paramList);
        }
        //�õ�ר���״̬�Ƿ������õ�
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(topic.getCourseid());
        List<TpCourseInfo> tcList=this.tpCourseManager.getList(tc,null);
        if(tcList==null||tcList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ACTIVE_COLUMNS_NO_DATA"));
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());return;
        }
        Long quoteid=tcList.get(0).getQuoteid();
        if(quoteid!=null&&quoteid!=0&&quoteid>0){//���<>NULL��˵��������ר��
            //�������¼��Ӽ�¼
            TpOperateInfo operate=new TpOperateInfo();
            operate.setCourseid(quoteid);
            operate.setDatatype(4);  //�������� 1��ר��(����) 2��ר������ 3��ר����Դ 4��ר������ 5��ר������ 6��ר������
            operate.setTargetid(nextTopicid);
            operate.setOperatetype(2);
            operate.setCuserid(this.logined(request).getUserid());
            operate.setRef(this.tpOperateManager.getNextId(true));

            sqlbuilder=new StringBuilder();
            paramList=this.tpOperateManager.getSaveSql(operate, sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlList.add(sqlbuilder.toString());
                objArrayList.add(paramList);
            }
        }
        //����ִ��
        if(this.tpTopicManager.doExcetueArrayProc(sqlList,objArrayList)){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_SUCCESS"));
            jsonEntity.setType("success");
            jsonEntity.getObjList().add(nextTopicid);
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_ERROR"));
        response.getWriter().print(jsonEntity.toJSON());
    }


    /**
     * ִ��AJAX ��ѯ
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=topicListjson",method = RequestMethod.POST)
    public void topicListJSON(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicInfo topicInfo=this.getParameter(request,TpTopicInfo.class);
        if(topicInfo==null||topicInfo.getCourseid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //��ѯ
        PageResult presult=this.getPageResultParameter(request);
        topicInfo.setSelectType(1);
        topicInfo.setLoginuserid(this.logined(request).getUserid());
        List<TpTopicInfo> tpTopicList=this.tpTopicManager.getList(topicInfo, presult);
        presult.setList(tpTopicList);
        jsonEntity.setPresult(presult);
        jsonEntity.setType("success");
        //����
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ����ID�õ�����
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getTopicById",method=RequestMethod.POST)
    public void getTopicById(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();  //����JSONʵ��
        TpTopicInfo tpTopic=this.getParameter(request,TpTopicInfo.class);
        //��֤����
        if(tpTopic==null||tpTopic.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //�õ����� (ֻȡһ������)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        //��ѯ��û�б�ɾ����
        tpTopic.setSelectType(1);
        tpTopic.setLoginuserid(this.logined(request).getUserid());

        List<TpTopicInfo> tpTopicList=this.tpTopicManager.getList(tpTopic,presult);
        if(tpTopicList==null||tpTopicList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        tpTopic=tpTopicList.get(0);
        if(tpTopic.getCloudstatus()!=null&&(tpTopic.getCloudstatus()==3||tpTopic.getCloudstatus()==4)){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("INTERNET_RESURCE"));//�ƶ����ݣ��������޸�
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        jsonEntity.setObjList(tpTopicList);
        jsonEntity.setType("success");
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ִ���޸�����
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdateTopic",method=RequestMethod.POST)
    public void doUpdateTopic(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicInfo topicInfo=this.getParameter(request,TpTopicInfo.class);
        //������֤
        if(topicInfo==null||topicInfo.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
//        if(!UtilTool.nullOrEmpty(topicInfo.getTopictitle())
//                ||!UtilTool.nullOrEmpty(topicInfo.getTopiccontent())
//                ||topicInfo.getStatus()==null){
//            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//            response.getWriter().print(jsonEntity.toJSON());return;
//        }

        //�õ����� (ֻȡһ������)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        //����ID�õ�ԭ�е��������
        TpTopicInfo topictmp=new TpTopicInfo();
        topictmp.setTopicid(topicInfo.getTopicid());
        //���ݶԱ�
        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topictmp,presult);
        //�ƶ����ݣ��������޸�
//        if(topicList.get(0).getCloudstatus()!=null&&(topicList.get(0).getCloudstatus()==3||topicList.get(0).getCloudstatus()==4)){
//            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("INTERNET_RESURCE"));//�ƶ����ݣ��������޸�
//            response.getWriter().print(jsonEntity.toJSON());return;
//        }
        topictmp=topicList.get(0);

        //��¼�޸�ǰ������
        StringBuilder operateLogRemark= new StringBuilder("�޸�ǰ������: topictitle:");
        operateLogRemark.append(topictmp.getTopictitle());
        operateLogRemark.append("  status:");
        operateLogRemark.append(topictmp.getStatus());
        //��ֵ
        if(topicInfo.getTopictitle()!=null)
            topictmp.setTopictitle(topicInfo.getTopictitle());
        if(topicInfo.getTopiccontent()!=null)
            topictmp.setTopiccontent(topicInfo.getTopiccontent());
        if(topicInfo.getStatus()!=null)
            topictmp.setStatus(topicInfo.getStatus());
        //��¼�޸ĺ��ֵ
        if(topicInfo.getTopictitle()!=null){
            operateLogRemark.append("�޸ĺ��ֵ��topictitle:");
            operateLogRemark.append(topictmp.getTopictitle());
        }
        if(topicInfo.getStatus()!=null){
            operateLogRemark.append("  status:");
            operateLogRemark.append(topictmp.getStatus());
        }

        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.tpTopicManager.getUpdateSql(topictmp, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }
        //������־
        sqlbuilder=new StringBuilder();
        objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_TOPIC_INFO", topictmp.getTopicid().toString(),null,null,"UPDATE"
                ,operateLogRemark.toString(),sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }

        if(sqlStrList.size()>0&&sqlStrList.size()==objListArray.size()){
            if(this.tpTopicManager.doExcetueArrayProc(sqlStrList,objListArray)){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("MODIFY_SUCCESS"));
                jsonEntity.setType("success");
            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("MODIFY_ERROR"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));

        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ִ��ɾ������
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doDeleteTopic",method=RequestMethod.POST)
    public void doDeleteTopic(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicInfo topicInfo=this.getParameter(request,TpTopicInfo.class); //�õ�����
        if(topicInfo.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //��ѯ�����Ƿ����
        //ֻ�õ�һ������
        //�õ����� (ֻȡһ������)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topicInfo,presult);
        if(topicList==null||topicList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //��ʼɾ��
        //����������¼
        List<List<Object>> objListArray=new ArrayList<List<Object>>();
        List<String> sqlStrList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=null;
        //����Ǳ�׼��Դ������
        TpTopicInfo tmptopic=topicList.get(0);
        //�ƶ����ݣ���ֻ��д��¼
        //   if(tmptopic.getCloudstatus()!=null&&(tmptopic.getCloudstatus()==3||tmptopic.getCloudstatus()==4)){
        Long operateNextId=this.tpOperateManager.getNextId(true);
        //����tp_operate_info���м�һ������ ���й���      //�˲������ڻ���վ����ʾ
//            TpOperateInfo operateInfo=new TpOperateInfo();
//            operateInfo.setCourseid(tmptopic.getCourseid());
//            operateInfo.setTargetid(tmptopic.getTopicid());
//            operateInfo.setOperatetype(1);   //1:ɾ��
//            operateInfo.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_TOPIC.getValue()); //����
//            operateInfo.setCuserid(this.logined(request).getUserid());
//            operateInfo.setRef(operateNextId);
//            objList=this.tpOperateManager.getSaveSql(operateInfo, sqlbuilder);
//            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
//                objListArray.add(objList);
//                sqlStrList.add(sqlbuilder.toString());
//            }
        //������־
//            sqlbuilder=new StringBuilder();
//            objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_OPERATE_INFO"
//                    ,topicInfo.getTopicid().toString(),null,null,"ADD","��������IDɾ���������ǹ�����߱�׼���⣬���¼������Ԫ�ر���,��ʵ��ɾ��!",sqlbuilder);
//            //�˲������ڻ���վ����ʾ
//            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
//                objListArray.add(objList);
//                sqlStrList.add(sqlbuilder.toString());
//            }
        // }else{
        topicInfo.setStatus(3);//�߼�ɾ��        �˲������ڻ���վ����ʾ
        objList=this.tpTopicManager.getUpdateSql(topicInfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }
        sqlbuilder=new StringBuilder();
        objList=this.tpTopicManager.getAddOperateLog(this.logined(request).getRef(),"TP_TOPIC_INFo"
                ,topicInfo.getTopicid().toString(),null,null,"DELETE","��������ID�߼�ɾ��",sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            objListArray.add(objList);
            sqlStrList.add(sqlbuilder.toString());
        }
        // }
        if(sqlStrList.size()>0&&sqlStrList.size()==objListArray.size()){
            if(this.tpTopicManager.doExcetueArrayProc(sqlStrList,objListArray)){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                jsonEntity.setType("success");
            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * ����ר����������ҳ��
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toDetailTopic",method=RequestMethod.GET)
    public ModelAndView toDetailTopic(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();  //������֯
        TpTopicInfo topic=this.getParameter(request,TpTopicInfo.class); //�õ�����
        if(topic==null||topic.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        //��ѯ�����Ƿ����
        //ֻ�õ�һ������
        //�õ����� (ֻȡһ������)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);

        topic.setSelectType(2); /*��ѯ����  1:status<>3   2:�����ӱ�ɾ���� */
        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topic,presult);
        if(topicList==null||topicList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        topic=topicList.get(0);

        //�����֤
        String roleStr="TEACHER";
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            roleStr="STUDENT";
        }
        mp.put("roleStr", roleStr);



        if(roleStr.equals("STUDENT")&&topic.getStatus().intValue()==2){
            jsonEntity.setMsg("���󣬸������Ѿ��رգ��޷����в鿴!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }


        mp.put("topic",topic); //�洢��request��
        mp.put("newthemeid",this.tpTopicThemeManager.getNextId(true));

        //�õ�ר���״̬�Ƿ������õ�
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(topic.getCourseid());
//        tc.setDcschoolid(this.logined(request).getDcschoolid());
        List<TpCourseInfo> tcList=this.tpCourseManager.getList(tc,null);
        if(tcList==null||tcList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ACTIVE_COLUMNS_NO_DATA"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        Long quoteid=tcList.get(0).getQuoteid();
        if(quoteid!=null&&quoteid!=0){//���<>NULL��˵��������ר��
            mp.put("coursequoteid", quoteid);
        }
        //�����֤
        Integer role=1;
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            role=2;
        }
        List<Map<String,Object>> tccList=this.tpTopicManager.getInteractiveClass(topic.getCourseid(), this.logined(request).getUserid(), role);
        mp.put("tccList",tccList);


        return new ModelAndView("/interactive/topic/topic-detail",mp);
    }
    /**
     * ����ר����������ҳ��
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=viewTopic",method=RequestMethod.GET)
    public ModelAndView viewTopic(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();  //������֯
        TpTopicInfo topic=this.getParameter(request,TpTopicInfo.class); //�õ�����
        if(topic==null||topic.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        topic.setQuoteid(null);
        //��ѯ�����Ƿ����
        //ֻ�õ�һ������
        //�õ����� (ֻȡһ������)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);

        topic.setSelectType(2); /*��ѯ����  1:status<>3   2:�����ӱ�ɾ���� */
        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topic,presult);
        if(topicList==null||topicList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        topic=topicList.get(0);

        //�����֤
        String roleStr="TEACHER";
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            roleStr="STUDENT";
        }
        mp.put("roleStr", roleStr);



        if(roleStr.equals("STUDENT")&&topic.getStatus().intValue()==2){
            jsonEntity.setMsg("���󣬸������Ѿ��رգ��޷����в鿴!");
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }


        mp.put("topic",topic); //�洢��request��
        mp.put("newthemeid",this.tpTopicThemeManager.getNextId(true));

        //�õ�ר���״̬�Ƿ������õ�
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(topic.getCourseid());
//        tc.setDcschoolid(this.logined(request).getDcschoolid());
        List<TpCourseInfo> tcList=this.tpCourseManager.getList(tc,null);
        if(tcList==null||tcList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ACTIVE_COLUMNS_NO_DATA"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        Long quoteid=tcList.get(0).getQuoteid();
        if(quoteid!=null&&quoteid!=0){//���<>NULL��˵��������ר��
            mp.put("coursequoteid", quoteid);
        }
        //�����֤
        Integer role=1;
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            role=2;
        }
        List<Map<String,Object>> tccList=this.tpTopicManager.getInteractiveClass(topic.getCourseid(), this.logined(request).getUserid(), role);
        mp.put("tccList",tccList);
        Integer ishasCKZT=0;//�Ƿ��вο����� 0��û�� 1������
        //��ѯ�ο������Ƿ����
        if(quoteid!=null){
            TpTopicThemeInfo th=new TpTopicThemeInfo();
            th.setCourseid(quoteid);
            th.setTopicid(topic.getTopicid());
            th.setLoginuserref(this.logined(request).getRef());
            if(role==2){
                //ѧ����&selectType=-4&status=1
                th.setSelectType(-4);
                th.setStatus(1L);
                th.setSearchRoleType(2);
            }else{
                //��ʦ: cloudstatus=3&selectType=-4
                th.setSelectType(-4);
                th.setCloudstatus(3);
            }
            List<TpTopicThemeInfo> tpTopicThemeList=this.tpTopicThemeManager.getList(th,presult);
            if(tpTopicThemeList!=null&&tpTopicThemeList.size()>0)
                ishasCKZT=1;
        }
        mp.put("isHasCKZT",ishasCKZT);




        return new ModelAndView("/interactive/topic/topic-detail0111",mp);
    }



    /**
     * ��������ͳ��ҳ��ZT
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTopicStatices",method=RequestMethod.GET)
    public ModelAndView toTopicStatices(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        TpTopicInfo entity=this.getParameter(request,TpTopicInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        //�õ�����
        if(entity.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        String clsid=request.getParameter("clsid");
        List<TpTopicInfo> tpcList=this.tpTopicManager.getList(entity,null);
        if(tpcList==null||tpcList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        String type=request.getParameter("type");
        mp.put("tpc",tpcList.get(0));


        //��ѯ�õ��༶��Ϣ

        //�����֤
        Integer role=1;
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            role=2;
        }
        List<Map<String,Object>> tccList=this.tpTopicManager.getInteractiveClass(tpcList.get(0).getCourseid(), this.logined(request).getUserid(), role);
        mp.put("tccList",tccList);
        if(clsid==null||clsid.toString().trim().length()<1){
            if(tccList==null||tccList.size()<1){
                jsonEntity.setMsg("�����޷���ѯ�������ڵİ༶��Ϣ!");
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            clsid=tccList.get(0).get("CLASS_ID").toString();
            type=tccList.get(0).get("CLASSTYPE").toString();
            mp.put("clsid",clsid);
            mp.put("type",type);
        }
        mp.put("clsid",clsid);
        mp.put("type",type);
        //Ĭ���ǵ�һ���༶
        List<Map<String,Object>> objMapList=this.tpTopicManager.getTopicStatices(entity.getTopicid(),Integer.parseInt(clsid),Integer.parseInt(type));
        mp.put("dataMapList",objMapList);
        return new ModelAndView("/interactive/topic/state",mp);
    }

    /**
     * ����ר������ҳ�棬�����ռ� ��Ҫ����courseid��
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=courseDetailIdx",method=RequestMethod.GET)
    public ModelAndView courseDetailIdx(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicInfo entity=this.getParameter(request,TpTopicInfo.class);

        if(entity==null||entity.getCourseid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsg());return null;
        }
       //��֤�Ƿ���ڴ�ר��
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(entity.getCourseid());
        List<TpCourseInfo> tcList=this.tpCourseManager.getList(tc,null);
        if(tcList==null||tcList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsg());return null;
        }
        mp.put("courseid",entity.getCourseid());
        return new ModelAndView("/interactive/course/index",mp);
    }
}
