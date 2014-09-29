package com.school.control.teachpaltform.award;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.entity.SubjectInfo;
import com.school.entity.teachpaltform.TpCourseClass;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.entity.teachpaltform.TpGroupStudent;
import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.entity.teachpaltform.award.TpGroupScore;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.manager.ClassManager;
import com.school.manager.ClassUserManager;
import com.school.manager.SubjectManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.ISubjectManager;
import com.school.manager.inter.teachpaltform.ITpCourseClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.ITpGroupStudentManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassManager;
import com.school.manager.inter.teachpaltform.award.ITpGroupScoreManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreManager;
import com.school.manager.teachpaltform.TpCourseClassManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.TpGroupStudentManager;
import com.school.manager.teachpaltform.TpVirtualClassManager;
import com.school.manager.teachpaltform.award.TpStuScoreLogsManager;
import com.school.manager.teachpaltform.award.TpStuScoreManager;
import com.school.manager.teachpaltform.award.TpGroupScoreManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ���ñ��ֿ�����
 * Created by zhengzhou on 14-6-27.
 */
@Controller
@RequestMapping(value="/clsperformance")
public class TpGroupScoreController extends BaseController<TpStuScore>{

    private ITpCourseManager courseManager;
    private ITpGroupScoreManager tpGroupScoreManager;
    private ITpStuScoreManager tpStuScoreManager;
    private ITpStuScoreLogsManager tpStuScoreLogsManager;
    private ITpCourseClassManager tpCourseClassManager;
    private ITpGroupStudentManager tpGroupStudentManager;
    private IClassManager classManage;
    private ITpVirtualClassManager virtualClassManager;
    private ISubjectManager subjectManager;
    private IClassUserManager classUserManager;
    public TpGroupScoreController(){
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
        this.tpGroupScoreManager=this.getManager(TpGroupScoreManager.class);
        this.courseManager=this.getManager(TpCourseManager.class);
        this.tpStuScoreManager=this.getManager(TpStuScoreManager.class);
        this.tpCourseClassManager=this.getManager(TpCourseClassManager.class);
        this.tpGroupStudentManager=this.getManager(TpGroupStudentManager.class);
        this.classManage=this.getManager(ClassManager.class);
        this.virtualClassManager=this.getManager(TpVirtualClassManager.class);
        tpStuScoreLogsManager=this.getManager(TpStuScoreLogsManager.class);
    }
    /**
     * ������ñ���ҳ�����ҳ(�ֽ�ɫ)
     * @param request
     * @param mp
     * @return
     */
    @RequestMapping(params="m=toIndex",method = RequestMethod.GET)
    public ModelAndView toIndex(
            HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception {
        String courseid=request.getParameter("courseid"); //ר��ID
        String clsid=request.getParameter("classid");     //�༶ID
        String typeid=request.getParameter("classtype");  //�༶����
        String subjectid=request.getParameter("subjectid");
        String termid=request.getParameter("termid");
        JsonEntity jsonEntity=new JsonEntity();
        //������֤��
        if(courseid==null||courseid.trim().length()<1
                ||subjectid==null||subjectid.trim().length()<1
//                ||typeid==null||typeid.trim().length()<1
                ){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        Integer clsDcType=2;//Ĭ������У�༶

        //��������ڣ���Ĭ�ϵ�һ��
        TpCourseClass tcc=new TpCourseClass();
        tcc.setCourseid(Long.parseLong(courseid.trim()));
        List<TpCourseClass> tccList=this.tpCourseClassManager.getList(tcc,null);
        if(tccList==null||tccList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        mp.put("tccClsList",tccList);
        if(clsid==null||clsid.trim().length()<1){
            clsid=tccList.get(0).getClassid().toString();
        }

        //�༶����(ʵ��༶)����֤�༶
        if(clsid!=null&&clsid.trim().length()>0){
            ClassInfo clsEntity=new ClassInfo();
            clsEntity.setClassid(Integer.parseInt(clsid.trim()));
            List<ClassInfo> clsList=this.classManage.getList(clsEntity,null);
            if(clsList==null||clsList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            clsDcType=clsList.get(0).getDctype();
        }
        mp.put("clsDcType",clsDcType);
        //�õ�ר������
        TpCourseInfo courseInfo=new TpCourseInfo();
        courseInfo.setCourseid(Long.parseLong(courseid.trim()));
        List<TpCourseInfo> courseList=this.courseManager.getList(courseInfo,null);
        if(courseList!=null&&courseList.size()>0){
            mp.put("coursename",courseList.get(0).getCoursename());
        }
        String groupid=",";

        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            //������֤��
            if(termid==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            //��ѯ��ѧ����С�����
            TpGroupStudent gs=new TpGroupStudent();
            gs.setUserid(this.logined(request).getUserid());
            gs.getTpgroupinfo().setSubjectid(Integer.parseInt(subjectid));
            //gs.setIsleader(1);
            List<TpGroupStudent> tgList=this.tpGroupStudentManager.getList(gs,null);

            if(tgList!=null&&tgList.size()>0&&tgList.get(0)!=null){
                for(TpGroupStudent tgs:tgList){
                    groupid+=tgs.getGroupid()+",";
                }
            }

            //�õ�ѧ�����ڵİ༶��classtype
            List<Map<String,Object>> tpccList=this.tpCourseClassManager.getTpClassCourse(Long.parseLong(courseid.trim()),this.logined(request).getUserid(),termid);
            if(tpccList==null||tpccList.size()<1){
                jsonEntity.setMsg("�쳣�����⵱ǰѧ���б�ר��û�����İ༶��Ϣ!������!");
                response.getWriter().println(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            clsid=tpccList.get(0).get("CLASS_ID").toString();
            typeid=tpccList.get(0).get("CLASS_TYPE").toString();
            if(groupid.trim().length()>1){
                mp.put("ctUid",this.logined(request).getUserid());
            }
        }else{
            if(groupid.length()<2){
                groupid="";
            }
        }
        if(groupid.trim().length()<=1)
            groupid=null;
        else{
            groupid=groupid.substring(1,groupid.lastIndexOf(","));
        }
        //���ݲ����õ�ֵ ��
        List<Map<String,Object>> dataListMap=null;
        if(this.validateRole(request,UtilTool._ROLE_TEACHER_ID)){
            dataListMap=tpStuScoreManager.getPageDataList(Long.parseLong(courseid),Long.parseLong(clsid.trim())
                    ,Integer.parseInt(typeid.trim()),Integer.parseInt(subjectid),null,null);
        }else if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            if(groupid!=null&&groupid.trim().length()>0){
                dataListMap=tpStuScoreManager.getPageDataList(Long.parseLong(courseid),Long.parseLong(clsid.trim())
                        ,Integer.parseInt(typeid.trim()),Integer.parseInt(subjectid),groupid,null);
            }
        }
        mp.put("dataListMap",dataListMap);
        if(groupid!=null){
            groupid=new StringBuilder(",").append(groupid).append(",").toString();
        }
        mp.put("stuGroupId",groupid);
        groupid=",";
        //�����ѧ�������ѯ��ѧԱ�ķ���
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            // ��ѯ����
            List<Map<String,Object>> maplist=tpStuScoreManager.getPageDataList(Long.parseLong(courseid),Long.parseLong(clsid.trim())
                    ,2,Integer.parseInt(subjectid),null,this.logined(request).getUserid());
            if(maplist!=null&&maplist.get(0)!=null)
                mp.put("stuMap",maplist.get(0));
            //������֤��
            if(termid==null){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
            }
            //��ѯ��ѧ����С�����(�Ƿ���С���鳤)
            TpGroupStudent gs=new TpGroupStudent();
            gs.setUserid(this.logined(request).getUserid());
            gs.getTpgroupinfo().setSubjectid(Integer.parseInt(subjectid));
            gs.setIsleader(1);
            List<TpGroupStudent> tgList=this.tpGroupStudentManager.getList(gs,null);

            if(tgList!=null&&tgList.size()>0&&tgList.get(0)!=null){
                for(TpGroupStudent tgs:tgList){
                    groupid+=tgs.getGroupid()+",";
                }
            }
            if(groupid.trim().length()<=1)
                groupid=null;
            if(groupid!=null)
                mp.put("leanderGrpid",groupid);
        }
        mp.put("classid",clsid);
        mp.put("classtype",typeid);
        mp.put("courseid",courseid);
        mp.put("subjectid",subjectid);
        return new ModelAndView("/teachpaltform/classPerformanceAward/clsPerformanceAwardIndex",mp);
    }


//    public ModelAndView toStuIndex(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
//
//    }

    /**
     * ��ӻ����޸ļ�¼
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=doAddOrUpdate",method = RequestMethod.POST)
    public void doAddOrUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpStuScore entity=this.getParameter(request,TpStuScore.class);
        String clstype=request.getParameter("clstype");
        if(entity.getUserid()==null||entity.getCourseid()==null||entity.getSubjectid()==null||entity.getClassid()==null||clstype==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        //���classid<>null ����schoolid  ��ѯʵ��༶
        if(Integer.parseInt(clstype.trim())==1){
            //��ѯ�༶��dc_school_id
            ClassInfo cls=new ClassInfo();
            cls.setClassid(entity.getClassid().intValue());
            List<ClassInfo> clsList=classManage.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(clsList.get(0).getDcschoolid().longValue());
        }else if(Integer.parseInt(clstype.trim())==2){//����༶
            TpVirtualClassInfo vc=new TpVirtualClassInfo();
            vc.setVirtualclassid(entity.getClassid().intValue());
            List<TpVirtualClassInfo> vtclassList=this.virtualClassManager.getList(vc,null);
            if(vtclassList==null||vtclassList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(vtclassList.get(0).getDcschoolid().longValue());
        }
        entity.setClasstype(Integer.parseInt(clstype));
        if(this.tpStuScoreManager.AddOrUpdate(entity)){
            jsonEntity.setType("success");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(jsonEntity.toJSON());
    }
    /**
     * ��ӻ����޸ļ�¼
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=doAddOrUpdateGroup",method = RequestMethod.POST)
    public void doAddOrUpdateGroupAward(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String groupid=request.getParameter("groupid");
        String subjectid=request.getParameter("subjectid");
        String awardnumber=request.getParameter("awardNumber");
        String classid=request.getParameter("classid");
        String clstype=request.getParameter("clstype");
        if(courseid==null||groupid==null||awardnumber==null||!UtilTool.isNumber(awardnumber.trim())||subjectid==null||classid==null||clstype==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        TpGroupScore entity=new TpGroupScore();
        entity.setClassid(Long.parseLong(classid));
        //���classid<>null ����schoolid  ��ѯʵ��༶
        if(Integer.parseInt(clstype.trim())==1){
            //��ѯ�༶��dc_school_id
            ClassInfo cls=new ClassInfo();
            cls.setClassid(entity.getClassid().intValue());
            List<ClassInfo> clsList=classManage.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(clsList.get(0).getDcschoolid().longValue());
        }else if(Integer.parseInt(clstype.trim())==2){//����༶
            TpVirtualClassInfo vc=new TpVirtualClassInfo();
            vc.setVirtualclassid(entity.getClassid().intValue());
            List<TpVirtualClassInfo> vtclassList=this.virtualClassManager.getList(vc,null);
            if(vtclassList==null||vtclassList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(vtclassList.get(0).getDcschoolid().longValue());
        }
        entity.setClasstype(Integer.parseInt(clstype));
        entity.setAwardnumber(Integer.parseInt(awardnumber.trim()));
        entity.setCourseid(Long.parseLong(courseid.trim()));
        entity.setGroupid(Long.parseLong(groupid.trim()));
        entity.setSubjectid(Integer.parseInt(subjectid.trim()));
        if(this.tpGroupScoreManager.AddOrUpdate(entity)){
            jsonEntity.setType("success");
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().println(jsonEntity.toJSON());
    }
    /**
     * ��ӻ����޸ļ�¼
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/roleSub/{courseid}/{classid}/{clstype}/{subjectid}/sub",method = RequestMethod.POST)
    public void doRoleSubmit( @PathVariable("courseid") Long courseid,
                              @PathVariable("classid") Long classid,
                              @PathVariable("clstype") Integer clstype,
                              @PathVariable("subjectid") Integer subjectid,
                              HttpServletRequest request,HttpServletResponse response) throws Exception{
        String groupJson=request.getParameter("groupJson");
        JsonEntity jsonEntity=new JsonEntity();
        if(courseid==null||classid==null||clstype==null||subjectid==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        TpStuScore entity=new TpStuScore();
        entity.setCourseid(courseid);
        entity.setClassid(classid);
        entity.setClasstype(clstype);
        entity.setSubjectid(subjectid);

        //���classid<>null ����schoolid  ��ѯʵ��༶
        if(clstype==1){
            //��ѯ�༶��dc_school_id
            ClassInfo cls=new ClassInfo();
            cls.setClassid(entity.getClassid().intValue());
            List<ClassInfo> clsList=classManage.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(clsList.get(0).getDcschoolid().longValue());


        }else if(clstype==2){//����༶
            TpVirtualClassInfo vc=new TpVirtualClassInfo();
            vc.setVirtualclassid(entity.getClassid().intValue());
            List<TpVirtualClassInfo> vtclassList=this.virtualClassManager.getList(vc,null);
            if(vtclassList==null||vtclassList.size()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return;
            }
            entity.setDcschoolid(vtclassList.get(0).getDcschoolid().longValue());
        }
        //groupidArray
        String groupidArray=null;
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();
        //�����ѧ����˵����С���鳤����õ�groupidArray
        if(validateRole(request,UtilTool._ROLE_STU_ID)){
                //��֤ѧ����С��
               if(groupJson==null||groupJson.trim().length()<1){
                   jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                   response.getWriter().print(jsonEntity.toJSON());return;
               }
            //�õ�ѧ����С��ID
            JSONObject jsonObject=JSONObject.fromObject(groupJson);
            Iterator<Object> keyIte=jsonObject.keySet().iterator();
            while(keyIte.hasNext()){
                Object key=keyIte.next();
                if(key!=null&&key.toString().trim().length()>0){
                    //groupidArray
                    if(groupidArray!=null)
                        groupidArray+=",";
                    else
                        groupidArray="";
                    groupidArray+=key.toString();
                    //��֯�õ�Sql���
                    TpGroupScore gs=new TpGroupScore();
                    gs.setGroupid(Long.parseLong(key.toString()));
                 //   gs.setSubmitflag(1);
                    gs.setCourseid(courseid);
                    gs.setClassid(classid);
                    gs.setSubjectid(subjectid);
                    gs.setClasstype(clstype);
                    gs.setDcschoolid(entity.getDcschoolid());
                    List<TpGroupScore> tpGsList=this.tpGroupScoreManager.getList(gs,null);
                    if(tpGsList==null||tpGsList.size()<1){
                        //���
                        gs.setSubmitflag(1);
                        gs.setAwardnumber(0);
                        StringBuilder sqlbuilder=new StringBuilder();
                        List<Object> objList=this.tpGroupScoreManager.getSaveSql(gs,sqlbuilder);
                        if(sqlbuilder.toString().trim().length()>0){
                            objArrayList.add(objList);
                            sqlArrayList.add(sqlbuilder.toString());
                        }
                    }else{
                        //�޸�
                        if(tpGsList.get(0).getSubmitflag()==null||tpGsList.get(0).getSubmitflag()==0){
                            tpGsList.get(0).setSubmitflag(1);
                            StringBuilder sqlbuilder=new StringBuilder();
                            List<Object> objList=this.tpGroupScoreManager.getUpdateSql(tpGsList.get(0), sqlbuilder);
                            if(sqlbuilder.toString().trim().length()>0){
                                objArrayList.add(objList);
                                sqlArrayList.add(sqlbuilder.toString());
                            }
                        }
                    }
                }
            }
        }


        //��ʼ��ѧ����Ϣ�������޸�
        if(this.tpStuScoreManager.stuScoreLastInit(entity,groupidArray)){
            //group��ʼ�� ���������޸�
          if(objArrayList.size()>0&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
              if(this.tpStuScoreManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                  jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                  //������е����ݶ��ύ����������ܷ�
                  if(!this.tpStuScoreManager.tpStuScoreCkAllComplateInput(courseid,classid.intValue(),subjectid,entity.getDcschoolid().intValue())){
                     jsonEntity.setMsg("��Ϣ¼��ɹ�����ִ��ѧ����Ϣͳ��ʧ��!");
                  }else
                      jsonEntity.setType("success");

              }else
                  jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
          }else{
              jsonEntity.setType("success");
              jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));

              //������е����ݶ��ύ����������ܷ�
              if(!this.tpStuScoreManager.tpStuScoreCkAllComplateInput(courseid,classid.intValue(),subjectid,entity.getDcschoolid().intValue())){
                  jsonEntity.setMsg("��Ϣ¼��ɹ�����ִ��ѧ����Ϣͳ��ʧ��!");
                  jsonEntity.setType("error");
              }else
                  jsonEntity.setType("success");


          }
        }else{
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(jsonEntity.toJSON());

    }

    /**
     * �������ͳ��ҳ��
     * @param request
     * @param response
     * @param mp
     * @return
     */
    @RequestMapping(params="m=toAwardStaticesScore")
    public ModelAndView toAwardStatices(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        String subjectid=request.getParameter("subjectid");
        String classid=request.getParameter("classid");
        String sort=request.getParameter("sort");
        JsonEntity jsonEntity=new JsonEntity();
        if(subjectid==null||subjectid.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());return null;
        }
        if(sort==null)
            sort="asc";
        //��֤classid
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(classid.trim()));
        List<ClassInfo> clsList=this.classManage.getList(cls,null);
        if(clsList==null||clsList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
            response.getWriter().print(jsonEntity.getAlertMsgAndBack());return null;
        }
        List<Map<String,Object>> listMap=this.tpStuScoreManager.getScoreStatices(Integer.parseInt(subjectid),Integer.parseInt(classid),sort);
        mp.put("dataList",listMap);
        mp.put("currentLoginUID",this.logined(request).getUserid());
        mp.put("clsObj",clsList.get(0));
        return new ModelAndView("/teachpaltform/classPerformanceAward/awardStatices",mp);
    }

    /**
     * �����ʦ���û��ֹ���
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTeaCourseScore")
    public ModelAndView toTeaCourseScore(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String classid=request.getParameter("classid");
        String termid=request.getParameter("termid");
        String subjectid=request.getParameter("subjectid");
        String sort=request.getParameter("sort");
        JsonEntity jsonEntity=new JsonEntity();
        if(classid==null||classid.trim().length()<1||termid==null||termid.trim().length()<1||subjectid==null||subjectid.trim().length()<1){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
                response.getWriter().print(jsonEntity.toJSON());return null;
        }
        //��֤�༶
        ClassInfo cls=new ClassInfo();
        cls.setClassid(Integer.parseInt(classid));
        List<ClassInfo> clsList=this.classManage.getList(cls,null);
        if(clsList==null||clsList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
            response.getWriter().print(jsonEntity.toJSON());return null;
        }

        //��֤ѧ��
        SubjectInfo sb=new SubjectInfo();
        sb.setSubjectid(Integer.parseInt(subjectid));
        List<SubjectInfo> subList=this.subjectManager.getList(sb,null);
        if(subList==null||subList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
            response.getWriter().print(jsonEntity.toJSON());return null;
        }
        Integer stype=1;
        if(sort!=null&&sort.trim().length()>0){
            stype=Integer.parseInt(sort);
        }
        //�õ�ͳ����Ϣ
        List<Map<String,Object>> mapList=this.tpStuScoreLogsManager.getStuScoreTeachStatices(termid,Integer.parseInt(classid),Integer.parseInt(subjectid),stype);
        //ѧ��
        mp.put("subObj",subList.get(0));
        //�༶
        mp.put("clsObj",clsList.get(0));
        //ҳ��ͳ������
        mp.put("dataMapList",mapList);
        //����
        mp.put("sort",stype);
        return new ModelAndView("/teachpaltform/classPerformanceAward/forCourseScore",mp);
    }

}
