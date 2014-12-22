package com.school.control.teachpaltform;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.dao.inter.resource.IAccessDAO;
import com.school.entity.*;
import com.school.entity.resource.AccessInfo;
import com.school.entity.resource.StoreInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.paper.MicVideoPaperInfo;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.manager.inter.resource.IAccessManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.resource.IStoreManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.paper.IMicVideoPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.manager.resource.AccessManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.resource.StoreManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.paper.MicVideoPaperManager;
import com.school.manager.teachpaltform.paper.PaperManager;
import com.school.manager.teachpaltform.paper.PaperQuestionManager;
import com.school.util.MD5_NEW;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


import com.school.control.base.BaseController;
import com.school.entity.resource.ResourceInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/tpres")
public class TpResourceController extends BaseController<TpCourseResource>{
    private ITpCourseManager tpCourseManager;
    private ITpOperateManager tpOperateManager;
    private IResourceManager resourceManager;
    private IDictionaryManager dictionaryManager;
    private IGradeManager gradeManager;
    private ITeachingMaterialManager teachingMaterialManager;
    private ITpCourseTeachingMaterialManager tpCourseTeachingMaterialManager;
    private ITpCourseResourceManager tpCourseResourceManager;
    private IStudentManager studentManager;
    private ITpResourceCollectManager tpResourceCollectManager;
    private ITpResourceViewManager tpResourceViewManager;
    private IQuestionAnswerManager questionAnswerManager;
    private ITpTaskManager tpTaskManager;
    private IUserManager userManager;
    private ITpCourseQuestionManager tpCourseQuestionManager;
    private IAccessManager accessManager;
    private IStoreManager storeManager;
    private ITpCourseRelatedManager tpCourseRelatedManager;
    private IMicVideoPaperManager micVideoPaperManager;
    private IPaperQuestionManager paperQuestionManager;
    private IPaperManager paperManager;
    private IQuestionOptionManager questionOptionManager;
    private ITaskPerformanceManager taskPerformanceManager;
    public TpResourceController(){
        this.taskPerformanceManager=this.getManager(TaskPerformanceManager.class);
        this.tpCourseManager=this.getManager(TpCourseManager.class);
        this.tpOperateManager=this.getManager(TpOperateManager.class);
        this.resourceManager=this.getManager(ResourceManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.teachingMaterialManager=this.getManager(TeachingMaterialManager.class);
        this.tpCourseTeachingMaterialManager=this.getManager(TpCourseTeachingMaterialManager.class);
        this.tpCourseResourceManager=this.getManager(TpCourseResourceManager.class);
        this.studentManager=this.getManager(StudentManager.class);
        this.tpResourceCollectManager=this.getManager(TpResourceCollectManager.class);
        this.tpResourceViewManager=this.getManager(TpResourceViewManager.class);
        this.questionAnswerManager=this.getManager(QuestionAnswerManager.class);
        this.tpTaskManager=this.getManager(TpTaskManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.tpCourseQuestionManager=this.getManager(TpCourseQuestionManager.class);
        this.accessManager=this.getManager(AccessManager.class);
        this.storeManager=this.getManager(StoreManager.class);
        this.tpCourseRelatedManager=this.getManager(TpCourseRelatedManager.class);
        this.micVideoPaperManager=this.getManager(MicVideoPaperManager.class);
        this.paperManager=this.getManager(PaperManager.class);
        this.paperQuestionManager=this.getManager(PaperQuestionManager.class);
        this.questionOptionManager=this.getManager(QuestionOptionManager.class);
    }

    /**
     * 获取专题列表
     */
    @RequestMapping(params="m=ajaxCourseList",method=RequestMethod.POST)
    public void ajaxCourseList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String courselevel=request.getParameter("courselevel");
        String materialid=request.getParameter("materialid");
        String gradeid=request.getParameter("gradeid");
        String coursename=request.getParameter("coursename");
        String subjectid=request.getParameter("subjectid");
        String currentcourseid=request.getParameter("currentcourseid");

        PageResult pageResult=this.getPageResultParameter(request);
        TpCourseInfo tpCourseInfo=new TpCourseInfo();
        if(courselevel!=null&&courselevel.trim().length()>0){
            tpCourseInfo.setCourselevel(Integer.parseInt(courselevel));
            if(courselevel.equals("1"))
                tpCourseInfo.setCuserid(this.logined(request).getUserid());
        }
        if(materialid!=null&&materialid.trim().length()>0){
            tpCourseInfo.setMaterialid(Integer.parseInt(materialid));
        }
        if(gradeid!=null&&gradeid.trim().length()>0){
            tpCourseInfo.setGradeid(Integer.parseInt(gradeid));
        }
        if(subjectid!=null&&subjectid.trim().length()>0){
            tpCourseInfo.setSubjectid(Integer.parseInt(subjectid));
        }
        if(coursename!=null&&coursename.trim().length()>0){
            tpCourseInfo.setCoursename(coursename);
        }
        if(currentcourseid!=null&&currentcourseid.trim().length()>0){
            tpCourseInfo.setCurrentcourseid(Long.parseLong(currentcourseid));
        }



        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getCouresResourceList(tpCourseInfo, pageResult);
        pageResult.setList(teacherCourseList);
        je.setPresult(pageResult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     *获取专题资源列表
     */
    @RequestMapping(params="m=ajaxCourseResList",method=RequestMethod.POST)
    public void ajaxCourseResList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        PageResult pageResult=this.getPageResultParameter(request);
        //pageResult.setOrderBy(" aa.diff_type desc, aa.ctime desc,aa.operate_time desc ");
        pageResult.setOrderBy(" aa.diff_type desc,aa.is_mic_copiece,ifnull(aa.operate_time,aa.ctime) desc,aa.res_id");
        TpCourseResource tr=this.getParameter(request,TpCourseResource.class);
        String courseid=request.getParameter("courseid");
        String currentcourseid=request.getParameter("currentcourseid");
        if(courseid!=null&&courseid.trim().length()>0)
            tr.setCourseid(Long.parseLong(courseid));
        else
            tr.setCourseid(null);
        if(currentcourseid!=null&&currentcourseid.trim().length()>0){
            tr.setCurrentcourseid(Long.parseLong(currentcourseid));
            //pageResult.setOrderBy(" aa.diff_type desc,aa.ctime desc,aa.operate_time desc,aa.res_flag ");
            pageResult.setOrderBy(" aa.diff_type desc,aa.is_mic_copiece,ifnull(aa.operate_time,aa.ctime) desc,aa.res_id,aa.res_flag ");
        }



        List<TpCourseResource>resourceList=this.tpCourseResourceManager.getList(tr,pageResult);
        pageResult.setList(resourceList);
        je.setPresult(pageResult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     *从资源库添加专题资源
     */
    @RequestMapping(params="m=addResource",method=RequestMethod.POST)
    public void addResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String residArray=request.getParameter("resArray");
        String resourceType=request.getParameter("resourcetype");

        if(courseid==null||courseid.trim().length()<1
                ||residArray==null||residArray.length()<1
                ||resourceType==null||resourceType.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlList=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        String[]array=residArray.split(",");
        if(array.length>0){
            for(String resid:array){
                TpCourseResource tc=new TpCourseResource();
                tc.setCourseid(Long.parseLong(courseid));
                tc.setResid(Long.parseLong(resid));
                tc.setResourcetype(Integer.parseInt(resourceType));
                List<TpCourseResource>count=this.tpCourseResourceManager.getList(tc,null);
                //资源存在
                if(count!=null&&count.size()>0)
                    continue;
                tc.setRef(this.tpCourseResourceManager.getNextId(true));
                sql=new StringBuilder();
                objList=this.tpCourseResourceManager.getSaveSql(tc,sql);
                if(objList!=null&&sql!=null){
                    sqlList.add(sql.toString());
                    objListArray.add(objList);
                }
            }
        }

        boolean flag=this.tpCourseResourceManager.doExcetueArrayProc(sqlList,objListArray);
        if(objListArray.size()>0&&sqlList.size()>0){
            if(flag){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            je.setType("success");
            //je.setMsg("当前选择资源已存在!");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 添加到学习资源
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdResType",method=RequestMethod.POST)
    public void doUpdResType(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String ref=request.getParameter("ref");
        if(ref==null||ref.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseResource t=new TpCourseResource();
        t.setRef(Long.parseLong(ref));
        t.setResourcetype(1);
        if(this.tpCourseResourceManager.doUpdate(t)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 添加到学习资源
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdResTypeById",method=RequestMethod.POST)
    public void doUpdResTypeByCourseid(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String resid=request.getParameter("resid");
        String courseid=request.getParameter("courseid");
        if(resid==null||resid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(courseid==null||courseid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        TpCourseResource t=new TpCourseResource();
        t.setCourseid(Long.parseLong(courseid));
        t.setResid(Long.parseLong(resid));
        List<TpCourseResource>tpCourseResourceList=this.tpCourseResourceManager.getList(t,null);
        if(tpCourseResourceList==null||tpCourseResourceList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(tpCourseResourceList.get(0).getResourcetype().equals(2)){
            t.setRef(tpCourseResourceList.get(0).getRef());
            t.setResourcetype(1);
            if(this.tpCourseResourceManager.doUpdate(t)){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 删除专题资源
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doDelResource",method=RequestMethod.POST)
    public void doDelResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        if(courseid==null||courseid.length()<1
                ||resid==null||resid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseResource tr=new TpCourseResource();
        tr.setCourseid(Long.parseLong(courseid));
        tr.setResid(Long.parseLong(resid));
        List<TpCourseResource>teacherCourseList=this.tpCourseResourceManager.getList(tr, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlList=new ArrayList<String>();
        /**
         *1.引用专题,增加到操作元素表中
         *2.自建专题,修改本地状态
         */
        TpCourseResource tmpCourseResource=teacherCourseList.get(0);
        if(tmpCourseResource.getQuoteid()!=null&&tmpCourseResource.getQuoteid().intValue()!=0){
            TpCourseInfo quoteInfo=new TpCourseInfo();
            quoteInfo.setCourseid(tmpCourseResource.getQuoteid());
            List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
            if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                //增加专题操作数据
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourseResource.getQuoteid());
                to.setTargetid(tmpCourseResource.getResid());
                to.setOperatetype(1);              //删除
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_RESOURCE.getValue());                 //专题资源
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    objListArray.add(objList);
                    sqlList.add(sql.toString());
                }
            }

        }

        tmpCourseResource.setLocalstatus(2);//本地删除，修改状态
        sql=new StringBuilder();
        objList=this.tpCourseResourceManager.getUpdateSql(tmpCourseResource,sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqlList.add(sql.toString());
        }


        if(this.tpCourseResourceManager.doExcetueArrayProc(sqlList, objListArray)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 修改资源
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdResource",method=RequestMethod.POST)
    public void doUpdResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        String restype=request.getParameter("restype");
        String resname=request.getParameter("resname");
        String resintroduce=request.getParameter("resintroduce");
        String usertype=request.getParameter("usertype");
        if(courseid==null||courseid.length()<1
                ||resid==null||resid.length()<1
                ||resname==null||resname.length()<1
                ||usertype==null||usertype.length()<1
                ||restype==null){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseResource tr=new TpCourseResource();
        tr.setCourseid(Long.parseLong(courseid));
        tr.setResid(Long.parseLong(resid));
        List<TpCourseResource>teacherCourseList=this.tpCourseResourceManager.getList(tr, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        tr=teacherCourseList.get(0);
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlList=new ArrayList<String>();

        /**
         * 1：非引用专题、本地资源修改属性
         * 2：标准/自建专题 拷贝数据、文件。并插入到专题元素操作表中
         */
        if(tr.getResdegree()!=null&&tr.getResdegree().intValue()==3){
            ResourceInfo rs=new ResourceInfo();
            rs.setResname(resname);
            rs.setResid(Long.parseLong(resid));
            rs.setRestype(Integer.parseInt(restype));
            if(resintroduce!=null&&resintroduce.length()>0)
                rs.setResintroduce(resintroduce);
            if(this.resourceManager.doUpdate(rs)){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }else{
            ResourceInfo selres=new ResourceInfo();
            selres.setResid(Long.parseLong(resid));
            List<ResourceInfo>resourceList=this.resourceManager.getList(selres,null);
            if(resourceList==null||resourceList.size()<1){
                je.setMsg(UtilTool.msgproperty.getProperty("NOT_EXISTS"));
                response.getWriter().print(je.toJSON());
                return;
            }
            //添加资源
            Long nextResourceId=this.resourceManager.getNextId(true);
            ResourceInfo tmp=resourceList.get(0);
            tmp.setResname(resname);
            tmp.setResid(nextResourceId);
            tmp.setRestype(Integer.parseInt(restype));
            tmp.setUsertype(Integer.parseInt(usertype));
            if(resintroduce!=null&&resintroduce.length()>0)
                tmp.setResintroduce(resintroduce);
            sql=new StringBuilder();
            objList=this.resourceManager.getSaveSql(tmp,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlList.add(sql.toString());
            }
            //修改专题数据
            TpCourseResource trupdate=new TpCourseResource();
            trupdate.setRef(tr.getRef());
            trupdate.setResid(nextResourceId);
            sql=new StringBuilder();
            objList=this.tpCourseResourceManager.getUpdateSql(trupdate,sql);
            if(objList!=null&&sql!=null){
                objListArray.add(objList);
                sqlList.add(sql.toString());
            }


            //引用专题
            if(tr.getQuoteid()!=null&&tr.getQuoteid().intValue()!=0){
                TpCourseInfo quoteInfo=new TpCourseInfo();
                quoteInfo.setCourseid(tr.getQuoteid());
                List<TpCourseInfo>quoteList=this.tpCourseManager.getList(quoteInfo,null);
                if(quoteList!=null&&quoteList.size()>0&&quoteList.get(0).getCourselevel()!=3){
                    //增加专题操作数据
                    TpOperateInfo to=new TpOperateInfo();
                    to.setRef(this.tpOperateManager.getNextId(true));
                    to.setCuserid(this.logined(request).getUserid());
                    to.setCourseid(tr.getQuoteid());
                    to.setTargetid(nextResourceId);   //新建资源ID
                    to.setReferenceid(tr.getResid()); //要修改的资源ID
                    to.setOperatetype(3);              //修改
                    to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_RESOURCE.getValue());                 //专题资源
                    sql=new StringBuilder();
                    objList=this.tpOperateManager.getSaveSql(to,sql);
                    if(objList!=null&&sql!=null){
                        objListArray.add(objList);
                        sqlList.add(sql.toString());
                    }
                }

            }


            if(objListArray.size()>0&&sqlList.size()>0){
                if(this.tpCourseResourceManager.doExcetueArrayProc(sqlList,objListArray)){
                    je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                    je.setType("success");
                    String src=UtilTool.getResourceLocation(Long.parseLong(resid),2)+"/"+UtilTool.getResourceMd5Directory(resid);
                    String dest=UtilTool.getResourceLocation(nextResourceId,2)+"/"+UtilTool.getResourceMd5Directory(nextResourceId.toString());
                    UtilTool.copyDirectiory(src,dest);
                }else
                    je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        }

        response.getWriter().print(je.toJSON());
    }


    /**
     * 教师个人资源库
     * @return
     * @throws Exception

     @RequestMapping(params="toTeacherUpload",method=RequestMethod.GET)
     public ModelAndView toTeacherUpload(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je=new JsonEntity();
     //		String courseid=request.getParameter("courseid");
     //		if(courseid==null||courseid.trim().length()<1){
     //			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     //			response.getWriter().print(je.toJSON());
     //			return null;
     //		}
     TeacherCourseInfo t= new TeacherCourseInfo();
     t.getUserinfo().setRef((this.logined(request).getRef()));
     List<TeacherCourseInfo>teacherCourseList=this.teacherCourseManager.getList(t, null);
     TpResourceBaseInfo obj=new TpResourceBaseInfo();
     //obj.setCourseid(courseid);
     List<TpResourceBaseInfo>resList=this.tpResourceBaseManager.getList(obj, null);
     request.setAttribute("resList", resList);
     request.setAttribute("courseList", teacherCourseList);
     return new ModelAndView("/teachpaltform/resource/tea-upload");
     }
     */

    /**
     * 获取ajax专题列表

     @RequestMapping(params="m=ajaxCourseList",method=RequestMethod.POST)
     public void ajaxCourseList(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je =new JsonEntity();
     String courseid=request.getParameter("courseid");
     if(courseid==null||courseid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     TeacherCourseInfo t= new TeacherCourseInfo();
     t.getUserinfo().setRef((this.logined(request).getRef()));
     List<TeacherCourseInfo>teacherCourseList=this.teacherCourseManager.getList(t, null);
     if(teacherCourseList!=null&&teacherCourseList.size()>0){
     for (int i=0;i<teacherCourseList.size();i++) {
     if(teacherCourseList.get(i)!=null&&teacherCourseList.get(i).getCourseid().equals(courseid)){
     teacherCourseList.remove(i);
     }
     }
     }
     je.setObjList(teacherCourseList);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     }
     */

    /**
     * 获取资源信息

     @RequestMapping(params="loadResourceById",method=RequestMethod.POST)
     public void loadResourceById(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je =new JsonEntity();
     String resid=request.getParameter("resid");
     String courseid=request.getParameter("courseid");
     if(resid==null||resid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     //		if(courseid==null||courseid.trim().length()<1){
     //			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     //			response.getWriter().print(je.toJSON());return;
     //		}
     TpResourceBaseInfo tb=new TpResourceBaseInfo();
     //tb.setCourseid(courseid);
     tb.setResourceid(resid);
     List<TpResourceBaseInfo>resList=this.tpResourceBaseManager.getList(tb, null);
     je.setObjList(resList);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     }     */

    /**
     * 教师上传资源（在个人资源库）
     * @throws Exception

     @RequestMapping(params="doUploadTeacherRes",method=RequestMethod.POST)
     public void doUploadTeacherRes(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je =new JsonEntity();
     String resid=request.getParameter("resid");
     String courseid=request.getParameter("courseid");
     String resname=request.getParameter("resname");
     String reskeyword=request.getParameter("reskeyword");
     String resintroduce=request.getParameter("resintroduce");
     String resremark=request.getParameter("resremark");
     String usertype=request.getParameter("usertype");
     if(resid==null||resid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     if(courseid==null||courseid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     if(resname==null||resname.trim().length()<1
     ||reskeyword==null||reskeyword.trim().length()<1
     ||usertype==null||usertype.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());
     return;
     }
     //查询出资源ID
     TpResourceBaseInfo t=new TpResourceBaseInfo();
     t.setResourceid(resid);
     List<TpResourceBaseInfo>resbaseList=this.tpResourceBaseManager.getList(t, null);
     if(resbaseList==null||resbaseList.size()<1){
     je.setMsg("上传失败!选择的资源已不存在或删除!");
     response.getWriter().print(je.toJSON());return;
     }
     List<String>sqlListArray=new ArrayList<String>();
     List<List<Object>>objListArray=new ArrayList<List<Object>>();
     StringBuilder sql=null;
     List<Object>objList=null;

     //添加基本
     String baseref=this.tpResourceBaseManager.getNextId();
     String resourceid=resbaseList.get(0).getResourceid();
     t=new TpResourceBaseInfo();
     t.setRef(baseref);
     t.setCourseid(courseid);
     t.setResname(resname);
     t.setReskeyword(reskeyword);
     if(resintroduce!=null)
     t.setResintroduce(resintroduce);
     if(resremark!=null)
     t.setResremark(resremark);
     t.setUserid(this.logined(request).getRef());
     t.setResstate(2);
     t.setUsertype(Integer.parseInt(usertype));
     t.setResourceid(resourceid);

     sql=new StringBuilder();
     objList=this.tpResourceBaseManager.getSaveSql(t, sql);
     if(objList!=null&&sql!=null){
     sqlListArray.add(sql.toString());
     objListArray.add(objList);
     }

     //添加详情
     /*	TpResourceDetailInfo d=new TpResourceDetailInfo();
     d.setRef(this.getModel().getResdetailid());
     List<TpResourceDetailInfo>resdetailList=this.getTpresourcedetailmanager().getList(d, null);
     if(resdetailList==null||resdetailList.size()<1){
     je.setMsg("上传失败!选择的资源已不存在或删除!");
     response.getWriter().print(je.toJSON());return;
     }
     d=new TpResourceDetailInfo();
     d.setResbaseid(resourceid);
     d.setResdetailname(resdetailList.get(0).getResdetailname());
     d.setResbaseref(baseref);
     d.setResdetailsize(resdetailList.get(0).getResdetailsize());
     sql=new StringBuilder();
     objList=this.getTpresourcedetailmanager().getSaveSql(d, sql);
     if(objList!=null&&sql!=null){
     sqlListArray.add(sql);
     objListArray.add(objList);
     }

     if(objListArray.size()>0&&sqlListArray.size()>0){
     boolean flag=this.tpResourceBaseManager.doExcetueArrayProc(sqlListArray, objListArray);
     if(flag){
     je.setMsg("上传成功!");
     je.setType("succes");
     }else{
     je.setMsg("上传失败!");
     }
     }else{
     je.setMsg("您的操作没有执行!");
     }
     response.getWriter().print(je.toJSON());
     }  */

    /**
     * 进入教师资源首页
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toTeacherIdx",method=RequestMethod.GET)
    public ModelAndView toTeacherIdx(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            je.setMsg("找不到指定课题!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        String subjectid=null;
        if(courseid!=null&&courseid.length()>0){
            TpCourseTeachingMaterial tct=new TpCourseTeachingMaterial();
            tct.setCourseid(Long.parseLong(courseid));
            List<TpCourseTeachingMaterial>tctList=tpCourseTeachingMaterialManager.getList(tct,null);
            if(tctList!=null&&tctList.size()>0){
                request.setAttribute("subjectid",tctList.get(0).getSubjectid());
                subjectid=tctList.get(0).getSubjectid().toString();
            }
        }


        //课题样式
        request.setAttribute("coursename", teacherCourseList.get(0).getCoursename());
        TpCourseInfo tcs= new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        tcs.setTermid(teacherCourseList.get(0).getTermid());
        tcs.setLocalstatus(1);
        if(termid==null||termid.trim().length()<1)
            termid=teacherCourseList.get(0).getTermid();
        if(subjectid!=null)
            tcs.setSubjectid(Integer.parseInt(subjectid));
        Object gradeid=request.getSession().getAttribute("session_grade");
        if(gradeid!=null&&gradeid.toString().length()>0&&!gradeid.equals("0"))
            tcs.setGradeid(Integer.parseInt(gradeid.toString()));
        List<TpCourseInfo>courseList=this.tpCourseManager.getCourseList(tcs, null);
        //当前学期所有专题
        tcs=new TpCourseInfo();
        tcs.setTermid(termid);
        // List<TpCourseInfo>courseAllList=this.tpCourseManager.getTchCourseList(tcs, null);
        //学生资源待审核数
       /* TpCourseResource tcheck=new TpCourseResource();
        tcheck.setCourseid(Long.parseLong(courseid));
        tcheck.setResourcetype(1);
        List<TpCourseResource>countList=this.tpCourseResourceManager.getList(tcheck, null);*/

        //SESSION添加专题状态
        //request.getSession().setAttribute("coursestate", teacherCourseList.get(0).getLocalstatus());
        //专题教材查询条件
        GradeInfo grade=new GradeInfo();
        //grade.setGradename("高");
        List<GradeInfo>gradeList=this.gradeManager.getList(grade,null);
        //List<TeachingMaterialInfo>materialList=this.teachingMaterialManager.getList(null,null);
        List<DictionaryInfo>courseLevelList=this.dictionaryManager.getDictionaryByType("COURSE_LEVEL");
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");

        //页数定位
        Integer pageno=1;
        String tpresdetailid=request.getParameter("tpresdetailid");
        if(tpresdetailid!=null&&tpresdetailid.trim().length()>0){
            TpCourseResource tr=new TpCourseResource();
            tr.setCourseid(Long.parseLong(courseid));
            tr.setResstatus(1);
            tr.setResourcetype(1);
            PageResult pageResult=new PageResult();
            pageResult.setPageNo(0);
            pageResult.setPageSize(0);
            pageResult.setOrderBy(" aa.diff_type desc,aa.is_mic_copiece,ifnull(aa.operate_time,aa.ctime) desc,aa.res_id");
            List<TpCourseResource>resourceList=this.tpCourseResourceManager.getList(tr,pageResult);
            if(resourceList!=null&&resourceList.size()>0){
                for(int i=0;i<resourceList.size();i++){
                    if(tpresdetailid.equals(resourceList.get(i).getResid().toString())){
                        pageno=i<10?1:(i+1)/10<1?1:i/10+1;
                        break;
                    }
                }
            }
        }


        mp.put("pageno",pageno);
        //mp.put("checkCount", countList);
        mp.put("courseid", courseid);
        mp.put("termid", termid);
        mp.put("courseList", courseList);
        mp.put("nextid",this.tpCourseResourceManager.getNextId(true));
        mp.put("gradeList",gradeList);
        // mp.put("materialList",materialList);
        mp.put("courseLevel",courseLevelList);
        mp.put("resType",resourceTypeList);
        return new ModelAndView("/teachpaltform/resource/teacher-index",mp);
    }

    /**
     * 专题资源--资源上传
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=loadUpload",method=RequestMethod.GET)
    public ModelAndView loadUpload(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        tc.setLocalstatus(1);
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            return null;
        }
        //获取当前专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(teacherCourseList.get(0).getCourseid());
        Object gradeid=request.getSession().getAttribute("session_grade");
        Object materialid=request.getSession().getAttribute("session_material");
        if(gradeid!=null&&gradeid.toString().trim().length()>0)
            ttm.setGradeid(Integer.parseInt(gradeid.toString()));
        if(materialid!=null&&materialid.toString().trim().length()>0)
            ttm.setTeachingmaterialid(Integer.parseInt(materialid.toString()));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            mp.put("materialInfo",materialList.get(0));

        //专题教材查询条件
        GradeInfo grade=new GradeInfo();
        //grade.setGradename("高");
        List<GradeInfo>gradeList=this.gradeManager.getList(grade,null);
        TpCourseInfo tcs= new TpCourseInfo();
        tcs=new TpCourseInfo();
        tcs.setTermid(teacherCourseList.get(0).getTermid());
        //  List<TpCourseInfo>courseAllList=this.tpCourseManager.getTchCourseList(tcs, null);
        //资源类型
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        List<DictionaryInfo>courseLevelList=this.dictionaryManager.getDictionaryByType("COURSE_LEVEL");
        //关联专题
        TpCourseRelatedInfo r=new TpCourseRelatedInfo();
        r.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo>courseRelatedList=this.tpCourseRelatedManager.getList(r,null);
        if(courseRelatedList!=null&&courseRelatedList.size()>0)
            mp.put("relateCourse","1");
        mp.put("resType",resourceTypeList);
        mp.put("gradeList",gradeList);
        mp.put("courseLevel",courseLevelList);
        mp.put("fileSystemIpPort",request.getSession().getAttribute("FILE_SYSTEM_IP_PORT").toString());
        Long nextid=this.resourceManager.getNextId(true);
        mp.put("nextid",nextid);
        mp.put("nextMd5Id",UtilTool.getResourceMd5Directory(nextid+""));
        System.out.println("nextid:" + nextid);
        return new ModelAndView("/teachpaltform/resource/local-upload-new",mp);
    }


    /**
     * 发布任务--资源上传
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=dialogUpload",method=RequestMethod.GET)
    public ModelAndView DialogUpload(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            return null;
        }
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>teacherCourseList=this.tpCourseManager.getTchCourseList(tc, null);
        if(teacherCourseList==null||teacherCourseList.size()<1){
            return null;
        }
        //专题教材查询条件
        GradeInfo grade=new GradeInfo();
       // grade.setGradename("高");
        List<GradeInfo>gradeList=this.gradeManager.getList(grade,null);
        TpCourseInfo tcs= new TpCourseInfo();
        tcs=new TpCourseInfo();
        tcs.setTermid(teacherCourseList.get(0).getTermid());
        List<TpCourseInfo>courseAllList=this.tpCourseManager.getTchCourseList(tcs, null);
        //资源类型
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        List<DictionaryInfo>courseLevelList=this.dictionaryManager.getDictionaryByType("COURSE_LEVEL");

        if(courseid!=null&&courseid.length()>0){
            TpCourseTeachingMaterial tct=new TpCourseTeachingMaterial();
            tct.setCourseid(Long.parseLong(courseid));
            List<TpCourseTeachingMaterial>tctList=tpCourseTeachingMaterialManager.getList(tct,null);
            if(tctList!=null&&tctList.size()>0){
                request.setAttribute("subjectid",tctList.get(0).getSubjectid());
                request.setAttribute("materialInfo",tctList.get(0));
            }
        }

        //关联专题
        TpCourseRelatedInfo r=new TpCourseRelatedInfo();
        r.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo>courseRelatedList=this.tpCourseRelatedManager.getList(r,null);
        if(courseRelatedList!=null&&courseRelatedList.size()>0)
            mp.put("relateCourse","1");

        mp.put("courseAllList",courseAllList);
        mp.put("resType",resourceTypeList);
        mp.put("gradeList",gradeList);
        mp.put("courseLevel",courseLevelList);
        mp.put("fileSystemIpPort",request.getSession().getAttribute("FILE_SYSTEM_IP_PORT").toString());
        mp.put("nextid",this.resourceManager.getNextId(true));
        return new ModelAndView("/teachpaltform/resource/dialog-local-upload",mp);
    }

    /**
     * 教师上传资源
     * @throws Exception
     */
    @RequestMapping(params="doUploadResource",method=RequestMethod.POST)
    public void doUploadResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resid=request.getParameter("resid");
        String restype=request.getParameter("restype");
        String crestype=request.getParameter("resourcetype");
        String filename=request.getParameter("filename");
        String resname=request.getParameter("resname");
        String courseid=request.getParameter("courseid");
        String usertype=request.getParameter("usertype");
        String resintroduce=request.getParameter("resintroduce");

        if(resid==null||resid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("RESOURCE_NO_RESID"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(filename==null||filename.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("RESOURCE_NO_FILE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resname==null||resname.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("RESOURCE_NO_RESNAME"));
            response.getWriter().print(je.toJSON());return;
        }
        if(courseid==null||courseid.trim().length()<1
                ||restype==null||restype.trim().length()<1
                ||usertype==null||usertype.trim().length()<1
                ||crestype==null||crestype.length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        /**
         * 查询该专题是否引用了共享专题，增加元素操作记录
         */
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>tcList=this.tpCourseManager.getList(tc, null);
        if(tcList==null||tcList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }





        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> listArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();

        //添加resoursce_base默认值
        ResourceInfo resbase=new ResourceInfo();
        //资源共享等级使用专题共享等级
        resbase.setSharestatus(tcList.get(0).getSharetype());
        resbase.setUserid(this.logined(request).getUserid());
        resbase.setResid(Long.parseLong(resid));
        resbase.setResname(resname);
        resbase.setUsertype(Integer.parseInt(usertype));
        if("1".equals(usertype)){//学生
            resbase.setResstatus(0);
        }
        resbase.setResdegree(3);//本地
        resbase.setDcschoolid(this.logined(request).getDcschoolid());
        resbase.setRestype(Integer.parseInt(restype));
        resbase.setUsername(this.logined(request).getRealname());
        //resbase.setSchoolname(UtilTool.msgproperty.get("CURRENT_SCHOOL_NAME").toString());

        if(resintroduce!=null)
            resbase.setResintroduce(resintroduce);
        //添加ResourceFile
        if(filename.indexOf("*")!=-1){
            String suffix=filename.split("\\*")[0].substring(filename.split("\\*")[0].lastIndexOf("."));
            if(suffix!=null&&UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR,suffix.toLowerCase()))
                suffix=".mp4";
            resbase.setFilesuffixname(suffix);
            resbase.setFilesize(Long.parseLong(filename.split("\\*")[1]));
        }else{
            String suffix=filename.substring(filename.lastIndexOf("."));
            if(suffix!=null&&UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR,suffix.toLowerCase())){
                if(!suffix.toLowerCase().equals(".mp4")){
                    je.setMsg("仅限MP4格式的视频，请转换后再上传!");
                    response.getWriter().print(je.toJSON());
                    return;
                }
            }
            String fileurl=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+UtilTool.getResourceUrl(resid,suffix);
              System.out.println("fileurl:" + fileurl+"");
            File tmp=new File(fileurl);
         //   System.out.println("fileurl:" + fileurl + "   " + tmp.exists() + " " + request.getSession().getServletContext().getRealPath("/"));
            if(!tmp.exists()){  //
                je.setMsg("文件不存在!");
                response.getWriter().print(je.toJSON());
                return;
            }

            resbase.setFilesuffixname(suffix);
            resbase.setFilesize(tmp.length());
        }
        List<DictionaryInfo> dicList=this.dictionaryManager.getDictionaryByType("FILE_SUFFIX_TYPE");
        if(dicList!=null&&dicList.size()>0){
            for (DictionaryInfo dic:dicList){
                if(dic!=null){
                    if(dic.getDictionaryname()!=null){
                        String[] typeArr=dic.getDictionaryname().split(",");
                        if(typeArr.length>0){
                            for(String str:typeArr){
                                if((resbase.getFilesuffixname()!=null&&resbase.getFilesuffixname().toLowerCase().trim().equals(str.trim()))){
                                    resbase.setFiletype(Integer.parseInt(dic.getDictionaryvalue().trim()));
                                    break;
                                }
                            }
                            if(resbase.getFiletype()!=null)
                                break;
                        }
                    }
                }
            }
        }
        if(resbase.getFiletype()==null){  //如果都不匹配，则为文本类型
            resbase.setFiletype(1);
        }



        List<Object> objlist=this.resourceManager.getSaveSql(resbase, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            listArrayList.add(objlist);
        }
        //开始添加TpCourseResourceInfo
        TpCourseResource tb=new TpCourseResource();
        tb.setCourseid(Long.parseLong(courseid));
        tb.setResid(Long.parseLong(resid));
        tb.setResourcetype(Integer.parseInt(crestype));
        tb.setRef(this.tpCourseResourceManager.getNextId(true));
        StringBuilder sql=new StringBuilder();
        List<Object>objList=this.tpCourseResourceManager.getSaveSql(tb, sql);
        if(objList!=null&&sql!=null){
            sqlList.add(sql.toString());
            listArrayList.add(objList);
        }


        TpCourseInfo tmpCourse=tcList.get(0);
        if(tmpCourse.getQuoteid()!=null&&tmpCourse.getQuoteid().intValue()!=0){
            TpCourseInfo tsel=new TpCourseInfo();
            tsel.setCourseid(tmpCourse.getQuoteid());
            List<TpCourseInfo>tmpList=this.tpCourseManager.getList(tsel, null);
            if(tmpList!=null&&tmpList.size()>0
                    &&tmpList.get(0).getCourselevel()!=null
                    &&tmpList.get(0).getCourselevel().intValue()!=3){
                //增加专题操作数据
                TpOperateInfo to=new TpOperateInfo();
                to.setRef(this.tpOperateManager.getNextId(true));
                to.setCuserid(this.logined(request).getUserid());
                to.setCourseid(tmpCourse.getQuoteid());
                to.setTargetid(resbase.getResid());
                to.setOperatetype(2);              //添加
                to.setDatatype(TpOperateInfo.OPERATE_TYPE.COURSE_RESOURCE.getValue());                 //专题资源
                sql=new StringBuilder();
                objList=this.tpOperateManager.getSaveSql(to,sql);
                if(objList!=null&&sql!=null){
                    listArrayList.add(objList);
                    sqlList.add(sql.toString());
                }
            }
        }


        boolean flag=this.tpCourseResourceManager.doExcetueArrayProc(sqlList, listArrayList);
        if(flag){
            je.setType("success");
            String msg="资源上传成功!";
            //文件转换
            String fname=filename.split("\\*")[0];
            String suffix=fname.substring(fname.lastIndexOf("."));
            if(fname!=null){
                String filetype=UtilTool.getConvertResourseType(fname);
                if(filetype!=null&&filetype.equals("doc")){
                    boolean issuccess=UtilTool.Office2Swf(request,resid, suffix);
                    if(issuccess)msg="资源上传并转换成功!";
                }
            }
            je.setMsg(msg);

            //添加动态
            if(usertype.equals("1")){
                if(this.tpCourseResourceManager.doAddDynamic(tb))
                    System.out.println("insert tpres dynamic success!");
                else
                    System.out.println("insert tpres dynamic error!");
            }
            je.getObjList().add(this.tpCourseResourceManager.getNextId(true));


        }else{
            je.setMsg("操作失败!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 跳转到上传本地资源页（通用）
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toLocalUpload",method=RequestMethod.GET)
    public ModelAndView toLocalUpload(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String termid=request.getParameter("termid");
        if(termid==null||termid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
        }
        List<TpCourseInfo>courseList=null;
        TpCourseInfo t=new TpCourseInfo();
        t.setTermid(termid);
        Integer resourceType=-1;
        if(this.validateRole(request,UtilTool._ROLE_TEACHER_ID)){
            resourceType=2;
            t.setUserid(this.logined(request).getUserid());
            courseList=this.tpCourseManager.getList(t, null);
        }else if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            resourceType=1;
            courseList=this.tpCourseManager.getList(t, null);
        }


        String uuid=this.resourceManager.getNextId();
        request.setAttribute("courseid", courseid);
        request.setAttribute("userType", resourceType);
        request.setAttribute("courseList", courseList);
        request.setAttribute("UUID",uuid);
        return new ModelAndView("/teachpaltform/resource/local-upload");
    }


    /**
     * 进入学生资源首页
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toStudentIdx",method=RequestMethod.GET)
    public ModelAndView toStudentIdx(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String taskid=request.getParameter("taskid");
        String courseid=request.getParameter("courseid");
        String tpresdetailid=request.getParameter("tpresdetailid");
        String groupid=request.getParameter("groupid");
        String termid=request.getParameter("termid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        if(!this.validateRole(request, UtilTool._ROLE_STU_ID)){
            je.setMsg("抱歉，当前页面只允许学生用户查看!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        //验证学生是否在该课题下
//		TpResourceBaseInfo t=new TpResourceBaseInfo();
//		t.setUserid(this.logined(request).getRef());
//		t.setCourseid(courseid);
//		if(this.tpResourceBaseManager.CourseExistsStudent(t)<1){
//			je.setMsg("没有发现指定数据!");
//			response.getWriter().print(je.getAlertMsgAndBack());
//			return null;
//		}

        //查询学生课题列表
        Integer subjectid=null;
        //获取当前专题教材
        TpCourseTeachingMaterial ttm=new TpCourseTeachingMaterial();
        ttm.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial>materialList=this.tpCourseTeachingMaterialManager.getList(ttm,null);
        if(materialList!=null&&materialList.size()>0)
            subjectid=materialList.get(0).getSubjectid();

        //课题列表
        TpCourseInfo tt=new TpCourseInfo();
        tt.setCourseid(Long.parseLong(courseid));
        tt.setUserid(this.logined(request).getUserid());
        //tt.setSubjectid(subjectid);
        tt.setLocalstatus(1);
        List<TpCourseInfo>courseList=this.tpCourseManager.getStuCourseList(tt, null);
        if(courseList!=null&&courseList.size()>0)
            request.setAttribute("coursename", courseList.get(0).getCoursename());
        //课题样式
        TpCourseInfo tcs1= new TpCourseInfo();
        tcs1.setUserid(this.logined(request).getUserid());
        tcs1.setTermid(courseList.get(0).getTermid());
        tcs1.setSubjectid(subjectid);
        if(termid==null||termid.trim().length()<1)
            termid=courseList.get(0).getTermid();
        Object gradeid=request.getSession().getAttribute("session_grade");
        if(gradeid!=null&&gradeid.toString().length()>0&&!gradeid.equals("0"))
            tcs1.setGradeid(Integer.parseInt(gradeid.toString()));
        List<TpCourseInfo>courseList1=this.tpCourseManager.getStuCourseList(tcs1, null);



        TpCourseInfo tcs=new TpCourseInfo();
        tcs.setUserid(this.logined(request).getUserid());
        if(courseList.get(0)!=null){
            termid=courseList.get(0).getTermid();
            tcs.setTermid(termid);
        }

        StudentInfo stuInfo1=new StudentInfo();
        stuInfo1.setUserref(this.logined(request).getRef());
        List<StudentInfo> stuList=this.studentManager.getList(stuInfo1,null);
        if(stuList==null){
            je.setMsg("错误，加载学生失败!");
            response.getWriter().print(je.getAlertMsgAndBack());return null;
        }
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");


        //页数定位
        Integer pageno=1;
        if(tpresdetailid!=null&&tpresdetailid.trim().length()>0){
            TpCourseResource tr=new TpCourseResource();
            tr.setCourseid(Long.parseLong(courseid));
            tr.setResstatus(1);
            tr.setResourcetype(1);
            PageResult pageResult=new PageResult();
            pageResult.setPageNo(0);
            pageResult.setPageSize(0);
            pageResult.setOrderBy(" aa.diff_type desc,aa.is_mic_copiece,ifnull(aa.operate_time,aa.ctime) desc,aa.res_id");
            List<TpCourseResource>resourceList=this.tpCourseResourceManager.getList(tr,pageResult);
            if(resourceList!=null&&resourceList.size()>0){
                for(int i=0;i<resourceList.size();i++){
                    if(tpresdetailid.equals(resourceList.get(i).getResid().toString())){
                        pageno=i<10?1:(i+1)/10<1?1:i/10+1;
                        break;
                    }
                }
            }
        }
        request.setAttribute("pageno", pageno);
        request.setAttribute("taskid", taskid);
        request.setAttribute("courseid", courseid);
        request.setAttribute("tpresdetailid", tpresdetailid);
        request.setAttribute("courseList",courseList1);
        request.setAttribute("resType",resourceTypeList);
        return new ModelAndView("/teachpaltform/resource/student_index");
    }

    /**
     * 教师审核资源
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toResourceCheckList",method=RequestMethod.GET)
    public ModelAndView toResourceCheckList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
//		if(this.getModel().getCourseid()==null||!UtilTool.isNumber(this.getModel().getCourseid().toString())){
//			je.setMsg("错误!系统未获取到课题标识!");
//			je.getAlertMsgAndBack();
//			response.getWriter().print(je.toJSON());
//			return null;
//		}
        if(!this.validateRole(request,UtilTool._ROLE_TEACHER_ID)){
            je.setMsg("抱歉，当前页面只允许教师查看!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid", request.getParameter("courseid"));
        return new ModelAndView("/teachpaltform/resource/teacherResCheck");
    }
    /**
     * 教师管理学生资源
     * @throws Exception

     @RequestMapping(params="getResCheckList",method=RequestMethod.POST)
     public void getResCheckList(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     String courseid=request.getParameter("courseid");
     //		if(courseid==null||courseid.trim().length()<1){
     //			je.setMsg("错误!系统未获取到专题标识!");
     //			response.getWriter().print(je.toJSON());
     //			return;
     //		}
     PageResult presult=this.getPageResultParameter(request);
     String realname=request.getParameter("realname");
     String resstate=request.getParameter("resstate");
     TpResourceBaseInfo td=new TpResourceBaseInfo();
     if(courseid!=null&&courseid.length()>0)
     td.setCourseid(courseid);
     else{
     td.setIsunion(1);
     td.setUserid(this.logined(request).getRef());
     }
     if(resstate!=null&&UtilTool.isNumber(resstate))
     td.setResstate(Integer.parseInt(resstate));
     td.setUsertype(2);
     if(realname!=null)
     td.getUserinfo().setRealname(realname);
     //获取学生上传的资源进行审核
     List<TpResourceBaseInfo>resDetailList=this.tpResourceBaseManager.getList(td, presult);
     presult.setList(resDetailList);
     je.setPresult(presult);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     }
     /**
      * 我发布的 （学生）
      * @return
     * @throws Exception
     */
    @RequestMapping(params="toStuResourceList",method=RequestMethod.GET)
    public ModelAndView toStuResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        request.setAttribute("courseid", request.getParameter("courseid"));
        return new ModelAndView("/teachpaltform/resource/stu-resource");
    }
    /**
     * 获取学生发布的资源
     * @throws Exception
     */
    @RequestMapping(params="getStuResourceList",method=RequestMethod.POST)
    public void getStuResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
//		if(this.getModel().getCourseid()==null){
//			je.setMsg("错误,系统未获取到课题标识!");
//			response.getWriter().print(je.toJSON());
//			return;
//		}

        PageResult presult=this.getPageResultParameter(request);
        String orderby=request.getParameter("stuorder");
        if(orderby!=null&&orderby.trim().length()>0)
            presult.setOrderBy(orderby);
        //获取学生上传的资源

        String courseid=request.getParameter("courseid");
        TpCourseResource td=new TpCourseResource();
        td.setUserid(this.logined(request).getUserid());
        if(courseid!=null)
            td.setCourseid(Long.parseLong(courseid));
        td.setUsertype(1);
        List<TpCourseResource>resDetailList=this.tpCourseResourceManager.getList(td, presult);
        presult.setList(resDetailList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 我收藏的 （学生）
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toStuCollectList",method=RequestMethod.GET)
    public ModelAndView toStuCollectList(HttpServletRequest request)throws Exception{
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        request.setAttribute("resType",resourceTypeList);
        return new ModelAndView("/teachpaltform/resource/stu-res-collect");
    }

    /**
     * 获取学生收藏的
     * @throws Exception
     */
    @RequestMapping(params="getStuCollectList",method=RequestMethod.POST)
    public void getStuCollectList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        //获取学生收藏的资源
        PageResult presult=this.getPageResultParameter(request);
        String orderby=request.getParameter("collectorder");
        if(orderby!=null&&orderby.trim().length()>0)
            presult.setOrderBy(orderby);
        TpResourceCollect c=new TpResourceCollect();
        c.setUserid(this.logined(request).getRef());
        List<TpResourceCollect>resDetailList=this.tpResourceCollectManager.getList(c, presult);
        presult.setList(resDetailList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 取消收藏资源
     * @throws Exception
     */
    @RequestMapping(params="doCancelCollect",method=RequestMethod.POST)
    public void doCancelCollectResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        String resid=request.getParameter("resid");
        if(ref==null||ref.trim().length()<1
                ||resid==null||resid.trim().length()<1){
            je.setMsg("错误!系统未获取到资源收藏标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        //验证是否已经存在
        StoreInfo storetmp=new StoreInfo();
        storetmp.setResid(Long.parseLong(resid));
        storetmp.setUserid(this.logined(request).getUserid());
        List<StoreInfo> storeList=storeManager.getList(storetmp, null);
        if(storeList==null||storeList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("STORE_IS_NULL"));
            response.getWriter().print(je.toJSON());return;
        }

        //修改状态,同时写入操作记录
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.storeManager.getDeleteSql(storetmp,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //修改数量
        sqlbuilder=new StringBuilder();
        objList=this.storeManager.getUpdateNumAdd("rs_resource_info","res_id","STORENUM",storetmp.getResid().toString(),2,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }



        TpResourceCollect c=new TpResourceCollect();
        //c.setCollectid(Integer.parseInt(ref));
        c.setUserid(this.logined(request).getRef());
        c.setResid(Long.parseLong(resid));
        sqlbuilder=new StringBuilder();
        objList=this.tpResourceCollectManager.getDeleteSql(c,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        if(sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 教师管理资源
     * @return
     * @throws Exception
     */
    @RequestMapping(params="toTeacherResource",method=RequestMethod.GET)
    public ModelAndView toTeacherResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
//		if(this.getModel().getCourseid()==null||!UtilTool.isNumber(this.getModel().getCourseid().toString())){
//			je.setMsg("错误!系统未获取到课题标识!");
//			je.getAlertMsgAndBack();
//			response.getWriter().print(je.toJSON());
//			return null;
//		}
        if(!this.validateRole(request,UtilTool._ROLE_TEACHER_ID)){
            je.setMsg("抱歉，当前页面只允许教师用户查看!");
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        request.setAttribute("courseid", request.getParameter("courseid"));
        return new ModelAndView("/teachpaltform/resource/tea-resource");
    }

    /**
     * 教师的资源
     * @throws Exception

     @RequestMapping(params="getTeacherResourceList",method=RequestMethod.POST)
     public void getTeacherResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je=new JsonEntity();
     //		if(this.getModel().getCourseid()==null){
     //			je.setMsg("错误,系统未获取到课题标识!");
     //			response.getWriter().print(je.toJSON());
     //			return;
     //		}
     PageResult presult=this.getPageResultParameter(request);
     presult.setOrderBy("u.c_time desc");
     String courseid=request.getParameter("courseid");
     TpResourceBaseInfo td=new TpResourceBaseInfo();
     td.setUserid(this.logined(request).getRef());
     if(courseid!=null)
     td.setCourseid(courseid);
     List<TpResourceBaseInfo>resDetailList=this.tpResourceBaseManager.getList(td, presult);
     presult.setList(resDetailList);
     je.setPresult(presult);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     } */


    /**
     * 审核学生资源(批量)
     * @throws Exception

     @RequestMapping(params="doCheckResource",method=RequestMethod.POST)
     public void doCheckResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     String resstate=request.getParameter("resstate");
     String courseid=request.getParameter("courseid");
     if(courseid==null||resstate==null){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());
     return;
     }
     String baseidstr=request.getParameter("baseidstr");
     if(baseidstr==null||baseidstr.trim().length()<1){
     je.setMsg("错误!系统未获取到资源标识!");
     response.getWriter().print(je.toJSON());
     return;
     }
     List<String>sqlListArray=new ArrayList<String>();
     List<List<Object>>objListArray=new ArrayList<List<Object>>();


     String[]baseidArray=baseidstr.split(",");
     if(baseidArray.length>0){
     for (String baseid : baseidArray) {
     StringBuilder sql=new StringBuilder();
     TpResourceBaseInfo t=new TpResourceBaseInfo();
     t.setResstate(Integer.parseInt(resstate));
     t.setRef(baseid);
     List<Object>objList=this.tpResourceBaseManager.getUpdateSql(t, sql);
     if(objList!=null&&sql!=null){
     sqlListArray.add(sql.toString());
     objListArray.add(objList);
     }
     }
     }
     if(sqlListArray.size()>0&&objListArray.size()>0){
     boolean flag=this.tpResourceBaseManager.doExcetueArrayProc(sqlListArray, objListArray);
     if(flag){
     je.setMsg("操作成功!");
     je.setType("success");
     }else{
     je.setMsg("操作失败!");
     }
     }else{
     je.setMsg("您的操作没有执行!");
     }
     response.getWriter().print(je.toJSON());

     }
     */

    /**
     * 审核学生资源(单条)
     * @throws Exception
     */
    @RequestMapping(params="doCheckOneResource",method=RequestMethod.POST)
    public void doCheckOneResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resstate=request.getParameter("resstate");
        String ref=request.getParameter("ref");
        if(courseid==null||resstate==null||ref==null){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ResourceInfo t=new ResourceInfo();
        t.setResstatus(Integer.parseInt(resstate));
        t.setResid(Long.parseLong(ref));
        boolean flag=this.resourceManager.doUpdate(t);
        if(flag){
            je.setMsg("操作成功!");
            je.setType("success");
        }else{
            je.setMsg("操作失败!");
        }
        response.getWriter().print(je.toJSON());

    }

    /**
     * 删除学生资源（单条）
     * @throws Exception
     */
    @RequestMapping(params="doDelOneResource",method=RequestMethod.POST)
    public void doDelOneResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String ref=request.getParameter("ref");
        if(ref==null||ref.trim().length()<1){
            je.setMsg("错误,系统未获取到资源标识!");
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseResource t=new TpCourseResource();
        t.setRef(Long.parseLong(ref));
        boolean flag=this.tpCourseResourceManager.doDelete(t);
        if(flag){
            je.setMsg("操作成功!");
            je.setType("success");
        }else{
            je.setMsg("操作失败!");
        }
        response.getWriter().print(je.toJSON());
    }


    /**
     * 删除学生资源(批量)
     * @throws Exception

     @RequestMapping(params="doDelAllResource",method=RequestMethod.POST)
     public void doDelAllResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();

     String baseidstr=request.getParameter("baseidstr");
     if(baseidstr==null||baseidstr.trim().length()<1){
     je.setMsg("错误!系统未获取到资源标识!");
     response.getWriter().print(je.toJSON());
     return;
     }
     List<String>sqlListArray=new ArrayList<String>();
     List<List<Object>>objListArray=new ArrayList<List<Object>>();


     String[]baseidArray=baseidstr.split(",");
     if(baseidArray.length>0){
     for (String baseid : baseidArray) {
     StringBuilder sql=new StringBuilder();
     TpResourceBaseInfo t=new TpResourceBaseInfo();
     t.setRef(baseid);
     List<Object>objList=this.tpResourceBaseManager.getDeleteSql(t, sql);
     if(objList!=null&&sql!=null){
     sqlListArray.add(sql.toString());
     objListArray.add(objList);
     }
     }
     }
     if(sqlListArray.size()>0&&objListArray.size()>0){
     boolean flag=this.tpResourceBaseManager.doExcetueArrayProc(sqlListArray, objListArray);
     if(flag){
     je.setMsg("操作成功!");
     je.setType("success");
     }else{
     je.setMsg("操作失败!");
     }
     }else{
     je.setMsg("您的操作没有执行!");
     }
     response.getWriter().print(je.toJSON());

     } */

    /**
     * 增加点击量
     * @throws Exception
     */
    @RequestMapping(params="doAddResourceView",method=RequestMethod.POST)
    public void doAddResourceView(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resourceid=request.getParameter("resourceid");
        String courseid=request.getParameter("courseid");
        if(resourceid==null||resourceid.trim().length()<1
                ||courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        ResourceInfo entity=new ResourceInfo();
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        entity.setResstatus(-3);//rs_status<>3的条件判断
        entity.setResid(Long.parseLong(resourceid));
        List<ResourceInfo> resourceList=this.resourceManager.getList(entity,presult);
        if(resourceList.size()<1){  //资源已经不存在
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.getAlertMsgAndBack());return;
        }
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.resourceManager.getUpdateNumAdd("rs_resource_info", "res_id", "CLICKS", entity.getResid().toString(), 1, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //添加资源访问记录
        AccessInfo ai = new AccessInfo();
        ai.setResid(entity.getResid());
        ai.setRef(this.accessManager.getNextId());
        ai.setUserid(this.logined(request).getUserid());
        this.accessManager.doSaveOrUpdate(ai);
        /*TpResourceView v=new TpResourceView();
        v.setUserid(this.logined(request).getRef());
        v.setResid(Long.parseLong(resourceid));
        v.setCourseid(Long.parseLong(courseid));
        boolean flag=this.tpResourceViewManager.doSave(v);*/
        if(this.tpCourseResourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
            je.setMsg("操作成功!");
            je.setType("success");
        }else{
            je.setMsg("操作失败!");
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 查询专题资源点击量
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="loadResourceViewCount",method=RequestMethod.POST)
    public void loadResourceViewCount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resourceid=request.getParameter("resourceid");
        String courseid=request.getParameter("courseid");
        if(resourceid==null||resourceid.trim().length()<1
                ||courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ResourceInfo r=new ResourceInfo();
        r.setResid(Long.parseLong(resourceid));
        List<ResourceInfo>resList=this.resourceManager.getList(r,null);
        if(resList==null||resList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }

        /*TpResourceView v=new TpResourceView();
        v.setResid(Long.parseLong(resourceid));
        v.setCourseid(Long.parseLong(courseid));
        List<TpResourceView>viewList=this.tpResourceViewManager.getList(v,null);*/
        if(resList!=null&&resList.size()>0)
            je.getObjList().add(resList.get(0).getClicks());
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 查询专题资源收藏量
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="loadResourceCollectCount",method=RequestMethod.POST)
    public void loadResourceCollectCount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resourceid=request.getParameter("resourceid");
        String courseid=request.getParameter("courseid");
        if(resourceid==null||resourceid.trim().length()<1
                ||courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpResourceCollect c=new TpResourceCollect();
        c.setResid(Long.parseLong(resourceid));
        c.setCourseid(Long.parseLong(courseid));
        List<TpResourceCollect>collectList=this.tpResourceCollectManager.getList(c,null);
        je.getObjList().add(collectList==null||collectList.size()<1?0:collectList.size());
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 查询专题资源收藏量
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="loadResourceDownLoadCount",method=RequestMethod.POST)
    public void loadResourceDownLoadCount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resourceid=request.getParameter("resourceid");
        String courseid=request.getParameter("courseid");
        if(resourceid==null||resourceid.trim().length()<1
            //||courseid==null||courseid.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ResourceInfo r=new ResourceInfo();
        r.setResid(Long.parseLong(resourceid));
        List<ResourceInfo>rList=this.resourceManager.getList(r,null);
        if(rList!=null&&rList.size()>0)
            je.getObjList().add(rList.get(0).getDownloadnum());
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 添加收藏
     * @throws Exception
     */
    @RequestMapping(params="doCollectResource",method=RequestMethod.POST)
    public void doCollectResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resourceid=request.getParameter("resourceid");
        String courseid=request.getParameter("courseid");
        if(resourceid==null||resourceid.trim().length()<1||courseid==null){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //验证是否已经存在
        StoreInfo storetmp=new StoreInfo();
        storetmp.setResid(Long.parseLong(resourceid));
        storetmp.setUserid(this.logined(request).getUserid());
        List<StoreInfo> storeList=storeManager.getList(storetmp, null);
        if(storeList!=null&&storeList.size()>0){
            je.setMsg(UtilTool.msgproperty.getProperty("STORE_HAS_MORE"));
            response.getWriter().print(je.toJSON());return;
        }

        //开始添加
        storetmp.setRef(this.storeManager.getNextId());
        //修改状态,同时写入操作记录
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.storeManager.getSaveSql(storetmp, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //修改数量
        sqlbuilder=new StringBuilder();
        objList=this.storeManager.getUpdateNumAdd("rs_resource_info","res_id","STORENUM",storetmp.getResid().toString(),1,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }


        TpResourceCollect c=new TpResourceCollect();
        c.setUserid(this.logined(request).getRef());
        c.setResid(Long.parseLong(resourceid));
        c.setCourseid(Long.parseLong(courseid));
        sqlbuilder=new StringBuilder();
        objList=this.tpResourceCollectManager.getSaveSql(c,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

        if(sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                je.setType("success");
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        }
        response.getWriter().print(je.toJSON());
    }
    /**
     * 已收藏
     * @throws Exception
     */
    @RequestMapping(params="doCheckIsCollect",method=RequestMethod.POST)
    public void doCheckIsCollect(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String resourceid=request.getParameter("resourceid");
        String courseid=request.getParameter("courseid");
        if(resourceid==null||resourceid.trim().length()<1||courseid==null){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpResourceCollect c=new TpResourceCollect();
        c.setUserid(this.logined(request).getRef());
        c.setResid(Long.parseLong(resourceid));
        c.setCourseid(Long.parseLong(courseid));
        List<TpResourceCollect>list=this.tpResourceCollectManager.getList(c, null);
        je.setObjList(list);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 添加好评
     * @throws Exception

     @RequestMapping(params="doAddCommentScore",method=RequestMethod.POST)
     public void doAddCommentScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     String courseid=request.getParameter("courseid");
     String resourceid=request.getParameter("resourceid");
     String score=request.getParameter("scoreval");
     if(courseid==null||courseid.trim().length()<1
     ||resourceid==null||resourceid.trim().length()<1
     ||score==null||Integer.parseInt(score)<1){
     je.setMsg(UtilTool.msgproperty.getProperty(UtilTool.msgproperty.getProperty("PARAM_ERROR")));
     response.getWriter().print(je.toJSON());
     return;
     }

     TpResourceRank r=new TpResourceRank();
     r.setCourseid(courseid);
     r.setScore(Integer.parseInt(score));
     r.setResid(resourceid);
     r.setCuserid(this.logined(request).getRef());
     boolean flag=this.tpResourceRankManager.doSave(r);
     if(flag){
     je.setMsg("操作成功!");
     je.setType("success");
     }else{
     je.setMsg("操作失败!");
     }
     response.getWriter().print(je.toJSON());
     } */

    /**
     * 查询好评
     * @throws Exception

     @RequestMapping(params="qryCommentScore",method=RequestMethod.POST)
     public void qryCommentScore(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     String resourceid=request.getParameter("resourceid");
     if(resourceid==null||resourceid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());
     return;
     }

     TpResourceRank r=new TpResourceRank();
     r.setResid(resourceid);
     r.setCuserid(this.logined(request).getRef());
     List<TpResourceRank>list=this.tpResourceRankManager.getList(r, null);
     if(list!=null&&list.size()>0){
     je.getObjList().add(list.get(0).getScore());
     je.setType("success");
     }
     response.getWriter().print(je.toJSON());
     }


     /**
      * 好评排行

     @RequestMapping(params="loadRankScoreList",method=RequestMethod.POST)
     public void loadRankScoreList(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     PageResult presult=this.getPageResultParameter(request);
     String courseid=request.getParameter("courseid");
     if(courseid==null||courseid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     TpResourceBaseInfo td=new TpResourceBaseInfo();
     td.setCourseid(courseid);
     td.setResstate(2);
     presult.setPageSize(5);
     presult.setOrderIdx(3);
     presult.setOrderBy(" 2 desc ");
     List<TpResourceBaseInfo>resDetailList=this.tpResourceBaseManager.getList(td,presult);
     je.setObjList(resDetailList);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     }
     /**
      * 点击排行

     @RequestMapping(params="loadViewRankList",method=RequestMethod.POST)
     public void loadViewRankList(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     String courseid=request.getParameter("courseid");
     PageResult presult=this.getPageResultParameter(request);
     if(courseid==null||courseid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     TpResourceBaseInfo td=new TpResourceBaseInfo();
     td.setCourseid(courseid);
     td.setResstate(2);
     presult.setPageSize(5);
     presult.setOrderBy(" 1 desc ");
     List<TpResourceBaseInfo>resDetailList=this.tpResourceBaseManager.getList(td, presult);
     je.setObjList(resDetailList);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     }
     /**
      * 最新评论

     @RequestMapping(params="loadNewCommentList",method=RequestMethod.POST)
     public void loadNewCommentList(HttpServletRequest request,HttpServletResponse response)throws Exception{
     JsonEntity je = new JsonEntity();
     String courseid=request.getParameter("courseid");
     PageResult presult=this.getPageResultParameter(request);
     if(courseid==null||courseid.trim().length()<1){
     je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
     response.getWriter().print(je.toJSON());return;
     }
     TpResCommentInfo c=new TpResCommentInfo();
     c.setCourseid(courseid);
     c.setType(1);
     presult.setOrderBy(" t.c_time desc ");
     List<TpResCommentInfo>commentList=this.tpResCommentManager.getList(c, presult);
     presult.setList(commentList);
     je.setPresult(presult);
     je.setType("success");
     response.getWriter().print(je.toJSON());
     }

     /**
      * 任务添加页 获取资源
      * @throws Exception
     */
    @RequestMapping(params="toQueryResourceList",method=RequestMethod.POST)
    public void toQueryResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        String taskflag=request.getParameter("taskflag");
        String difftype=request.getParameter("difftype");//微视频
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("aa.resource_type,ifnull(aa.operate_time,aa.ctime) desc ");
        TpCourseResource t= new TpCourseResource();
        t.setCourseid(Long.parseLong(courseid));
        t.setResstatus(1);
        if(resid!=null&&resid.trim().length()>0)
            t.setResid(Long.parseLong(resid));
        if(difftype!=null&&difftype.trim().length()>0)
            t.setDifftype(Integer.parseInt(difftype));
        //学习参考
        //t.setResourcetype(1);
        //查询没有发任务的资源
        if(taskflag!=null&&taskflag.trim().length()>0)
            t.setTaskflag(1);
        List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, p);
        je.setPresult(p);
        je.setObjList(resList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 任务添加页 获取资源(new)
     * @throws Exception
     */
    @RequestMapping(params="toQueryLocalResourceList",method=RequestMethod.POST)
    public void toQueryLocalResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        String taskflag=request.getParameter("taskflag");
        String difftype=request.getParameter("difftype");//微视频
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy(" aa.resource_type ,ifnull(aa.operate_time,aa.ctime) desc");

        TpCourseResource t= new TpCourseResource();
        t.setCourseid(Long.parseLong(courseid));
        t.setResstatus(1);
        t.setHaspaper(0);//没有试卷的微视频
        if(resid!=null&&resid.trim().length()>0)
            t.setResid(Long.parseLong(resid));
        if(difftype!=null&&difftype.trim().length()>0)
            t.setDifftype(Integer.parseInt(difftype));
        //学习参考
        //t.setResourcetype(1);
        //查询没有发任务的资源
        if(taskflag!=null&&taskflag.trim().length()>0)
            t.setTaskflag(1);
        List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, p);
        je.setPresult(p);
        je.setObjList(resList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 任务添加页 获取资源(union)
     * @throws Exception
     */
    @RequestMapping(params="toQueryLocalResourceListUnion",method=RequestMethod.POST)
    public void toQueryLocalResourceListUnion(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String taskflag=request.getParameter("taskflag");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult p=this.getPageResultParameter(request);

        TpCourseResource t= new TpCourseResource();
        t.setCourseid(Long.parseLong(courseid));
        //查询没有发任务的资源
        t.setTaskflag(1);
        List<TpCourseResource>resList=this.tpCourseResourceManager.getResourceUnion(t, p);
        je.setPresult(p);
        je.setObjList(resList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 任务添加页 获取关联专题资源
     * @throws Exception
     */
    @RequestMapping(params="toQueryRelatedResourceList",method=RequestMethod.POST)
    public void toQueryRelatedResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        String taskflag=request.getParameter("taskflag");
        String difftype=request.getParameter("difftype");//微视频
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
        tr.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr,null);
        if(trList!=null&&trList.size()>0){
            StringBuilder sb = new StringBuilder();
            for(int i = 0;i<trList.size();i++){
                sb.append(trList.get(i).getRelatedcourseid());
                if(i<trList.size()-1){
                    sb.append("|");
                }
            }
            PageResult p=this.getPageResultParameter(request);
            p.setOrderBy("aa.diff_type desc,aa.is_mic_copiece,aa.resource_type,ifnull(aa.operate_time,aa.ctime) desc,aa.res_id ");
            TpCourseResource t= new TpCourseResource();
            t.setCourseids(sb.toString());
            t.setResstatus(1);
            t.setHaspaper(0);   //0：没试卷的微视频 1：带试卷的微视频
            if(resid!=null&&resid.trim().length()>0)
                t.setResid(Long.parseLong(resid));
            if(difftype!=null&&difftype.trim().length()>0)
                t.setDifftype(Integer.parseInt(difftype));

            //学习参考
            //t.setResourcetype(1);
            //查询没有发任务的资源
            if(taskflag!=null&&taskflag.trim().length()>0)
                t.setTaskflag(1);
            t.setTaskCourseid(Long.parseLong(courseid.trim()));
            List<TpCourseResource>resList=this.tpCourseResourceManager.getResourceForRelatedCourse(t, p);
            je.setPresult(p);
            je.setObjList(resList);
            je.setType("success");
        }else{
            PageResult p = new PageResult();
            je.setPresult(p);
            je.setObjList(null);
        }

        response.getWriter().print(je.toJSON());
    }

    /**
     * 任务添加页 获取资源
     * @throws Exception
     */
    @RequestMapping(params="toQueryLikeResourceList",method=RequestMethod.POST)
    public void toQueryLikeResourceList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        String subjectid=request.getParameter("subjectid");
        String name=request.getParameter("name");
        PageResult p = this.getPageResultParameter(request);
        List<TpCourseResource>resList=this.tpCourseResourceManager.getLikeResource(Integer.parseInt(gradeid),Integer.parseInt(subjectid),name,p);
        je.setPresult(p);
        je.setObjList(resList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 查询资源学习心得
     * @throws Exception
     */
    @RequestMapping(params="m=getStuNotetList",method=RequestMethod.POST)
    public void getCommentList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resdetailid");
        String usertype=request.getParameter("usertype");
        if(resid==null||courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

         /*
        * 查询当前资源是否发任务
        */
        boolean isTaskEnd=false,isAnswer=false;
        if(usertype!=null&&usertype.equals("1")){
            PageResult p=new PageResult();
            p.setPageNo(1);
            p.setPageSize(1);
            TpTaskInfo taskInfo=new TpTaskInfo();
            taskInfo.setCourseid(Long.parseLong(courseid));
            taskInfo.setTaskvalueid(Long.parseLong(resid));
            List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(taskInfo,p);
            if(taskList!=null&&taskList.size()>0&&taskList.get(0).getTaskstatus()!=null){
                /**
                 * 查询当前学生是否被分配该任务
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
                    isTaskEnd=true;
                }
                QuestionAnswer qa=new QuestionAnswer();
                qa.setUserid(this.logined(request).getRef());
                qa.setTaskid(taskList.get(0).getTaskid());
                List<QuestionAnswer> qaList=this.questionAnswerManager.getList(qa,null);
                if(qaList!=null&&qaList.size()>0)
                    isAnswer=true;
            }

        }



        PageResult presult=this.getPageResultParameter(request);
        presult.setOrderBy(" t.c_time ");
        QuestionAnswer t=new QuestionAnswer();
        //得到一级评论
        t.setQuesparentid(Long.parseLong(resid));
        t.setQuesid(Long.parseLong("0"));
        if(usertype!=null&&usertype.equals("1")&&(!isTaskEnd&&!isAnswer))
            t.setUserid(this.logined(request).getRef());
        List<QuestionAnswer>commentList=this.questionAnswerManager.getResouceStuNoteList(t, presult);

        //得到二级回复
        t=new QuestionAnswer();
        t.setQuesparentid(Long.parseLong(resid));
        t.setQuesid(Long.parseLong("0"));
        List<QuestionAnswer>commentReplyList=this.questionAnswerManager.getResouceStuNoteTreeList(t, presult);


        //if(usertype!=null&&usertype.equals("2")||isTaskEnd){
        if(usertype!=null){
            presult.getList().add(commentList);
            presult.getList().add(commentReplyList);
        }
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }


    /**
     * 专题资源心得回复
     * @throws Exception
     */
    @RequestMapping(params="m=doReplyResourceStuNote",method=RequestMethod.POST)
    public void doReplyResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je= new JsonEntity();
        String  content =request.getParameter("replycontent");
        String  courseid =request.getParameter("courseid");
        String  parentcommentid =request.getParameter("parentcommentid");

        String  touserid =request.getParameter("touserid");
        String  resid=request.getParameter("resdetailid");

        if(parentcommentid==null||parentcommentid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(touserid==null||touserid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resid==null||resid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(content==null||content.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpTaskInfo task=new TpTaskInfo();
        task.setCourseid(Long.parseLong(courseid));
        task.setTaskvalueid(Long.parseLong(resid));
        List<TpTaskInfo>taskInfoList=this.tpTaskManager.getList(task,null);
        if(taskInfoList==null||taskInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }

        // String nextid=this.commentManager.getNextId();
        QuestionAnswer q=new QuestionAnswer();
        q.setCourseid(Long.parseLong(courseid));
        q.setTaskid(taskInfoList.get(0).getTaskid());
        q.setTasktype(1);
        q.setUserid(this.logined(request).getRef());
        q.setReplyuserid(this.logined(request).getRef());
        q.setAnswercontent(content.trim());
        q.setQuesid(Long.parseLong(parentcommentid)); //父ID
        q.setQuesparentid(Long.parseLong(resid));   //评论对象
        //touserid
        if(touserid!=null){
            //得到真实姓名
            UserInfo touser=new UserInfo();
            touser.setRef(touserid);
            List<UserInfo> touserList=this.userManager.getList(touser, null);
            if(touserList!=null&&touserList.size()>0){
                q.setTorealname(touserList.get(0).getRealname());
            }
            q.setTouserid(touserid);
        }
        if(this.questionAnswerManager.doSave(q)){
            je.setType("success");
            je.setMsg("回复成功!");
            //得到最新的数据
//            commentinfo=new CommentInfo();
//            commentinfo.setCommentid(nextid);
//            List<CommentInfo> resList=this.commentManager.getResouceCommentList(commentinfo, null);
//            if(resList==null||resList.size()<1){
//                je.setMsg("未正常添加!请刷新确认!");
//                je.setType("error");
//            }
//            je.getObjList().add(resList.get(0));
        }else
            je.setMsg("回复失败!");
        response.getWriter().print(je.toJSON());

    }

    /**
     * 回收站
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toRecycleIdx",method=RequestMethod.GET)
    public ModelAndView toRecycleIdx(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        String type=request.getParameter("type");
        if(courseid==null||courseid.length()<1||type==null||type.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            je.getAlertMsgAndBack();
            return null;
        }
        TpCourseInfo tpCourseInfo=new TpCourseInfo();
        tpCourseInfo.setCourseid(Long.parseLong(courseid));
        List<TpCourseInfo>tpCourseInfoList=this.tpCourseManager.getTchCourseList(tpCourseInfo,null);
        if(tpCourseInfoList==null||tpCourseInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            je.getAlertMsgAndBack();
            return null;
        }
        mp.put("courseid",courseid);
        mp.put("type",type);
        mp.put("coursename",tpCourseInfoList.get(0).getCoursename());
        return new ModelAndView("/teachpaltform/recycle/index",mp);
    }

    /**
     * 加载回收站中的资源
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadRecycleResource",method=RequestMethod.POST)
    public void loadRecycleResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }
        TpCourseResource tpCourseResource=new TpCourseResource();
        tpCourseResource.setCourseid(Long.parseLong(courseid));
        tpCourseResource.setLocalstatus(2); //已删除
        tpCourseResource.setSeldatetype(1);//最近一个月的资源
        //tpCourseResource.setUserid(this.logined(request).getUserid());
        PageResult pageResult=this.getPageResultParameter(request);
        pageResult.setOrderBy(" aa.operate_time desc");
        List<TpCourseResource>tpCourseResourceList=this.tpCourseResourceManager.getList(tpCourseResource,pageResult);
        pageResult.setList(tpCourseResourceList);
        je.setPresult(pageResult);
        response.getWriter().print(je.toJSON());
    }


    /**
     * 加载回收站中的任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadRecycleTask",method=RequestMethod.POST)
    public void loadRecycleTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setCourseid(Long.parseLong(courseid));
        taskInfo.setStatus(2); //已删除
        taskInfo.setCuserid(this.logined(request).getRef());
        PageResult pageResult=this.getPageResultParameter(request);
        pageResult.setOrderBy(" u.m_time desc");
        List<TpTaskInfo>taskInfoList=this.tpTaskManager.getList(taskInfo,pageResult);
        pageResult.setList(taskInfoList);
        je.setPresult(pageResult);
        response.getWriter().print(je.toJSON());
    }


    /**
     * 加载回收站中的试题
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=loadRecycleQuestion",method=RequestMethod.POST)
    public void loadRecycleQuestion(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.getAlertMsgAndBack());
            return;
        }
        TpCourseQuestion tpCourseQuestion=new TpCourseQuestion();
        tpCourseQuestion.setCourseid(Long.parseLong(courseid));
        tpCourseQuestion.setLocalstatus(2); //已删除
        tpCourseQuestion.setSeldatetype(1);//最近1个月的
        PageResult pageResult=this.getPageResultParameter(request);
        pageResult.setOrderBy(" u.operate_time desc");
        List<TpCourseQuestion>tpCourseQuestionList=this.tpCourseQuestionManager.getList(tpCourseQuestion,pageResult);
        pageResult.setList(tpCourseQuestionList);
        je.setPresult(pageResult);
        response.getWriter().print(je.toJSON());
    }


    /**
     * 恢复回收站中的资源
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doRestoreResource",method=RequestMethod.POST)
    public void doRestoreTask(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String ref=request.getParameter("ref");
        if(ref==null||ref.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TpCourseResource t=new TpCourseResource();
        t.setRef(Long.parseLong(ref));
        t.setLocalstatus(1);
        if(this.tpCourseResourceManager.doUpdate(t)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     * 查询微视频列表 发任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=queryMicViewList",method=RequestMethod.GET)
    public ModelAndView queryMicViewList(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return null;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("aa.diff_type desc,aa.is_mic_copiece,aa.res_id,ifnull(aa.operate_time ,aa.ctime) desc ");
        TpCourseResource t= new TpCourseResource();
        t.setCourseid(Long.parseLong(courseid));
        t.setResstatus(1);
        t.setTaskflag(1);//查询没有发任务的资源
        t.setDifftype(1);//微视频类型
        t.setHaspaper(1);//有试卷的视频
        List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, p);
        mp.put("resList",resList);
        return new ModelAndView("/teachpaltform/resource/select-resource",mp);
    }

    /**
     * 查询微视频列表 发任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=queryMicViewListModel",method=RequestMethod.GET)
    public ModelAndView queryMicViewListModel(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return null;
        }
        PageResult p=this.getPageResultParameter(request);
        p.setOrderBy("aa.diff_type desc,aa.is_mic_copiece,aa.res_id,ifnull(aa.operate_time ,aa.ctime) desc ");
        TpCourseResource t= new TpCourseResource();
        t.setCourseid(Long.parseLong(courseid));
        t.setResstatus(1);
        t.setTaskflag(1);//查询没有发任务的资源
        t.setDifftype(1);//微视频类型
        t.setHaspaper(1);//有试卷的视频
        List<TpCourseResource>resList=this.tpCourseResourceManager.getList(t, p);
        mp.put("resList",resList);
        return new ModelAndView("/teachpaltform/task/teacher/dialog/childPage/select-miclist",mp);
    }

    /**
     * 查询微视频列表 发任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=previewMic",method=RequestMethod.GET)
    public ModelAndView previewMic(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1//||taskid==null||taskid.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return null;
        }
        ResourceInfo rtmp=new ResourceInfo();
        rtmp.setResid(Long.parseLong(resid));
        List<ResourceInfo> resList=this.resourceManager.getList(rtmp,null);
        //验证微视频是否存在相关数据
        if(resList==null||resList.size()<1||resList.get(0)==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndCloseWin());return null;
        }

        //得到相关的试卷ID
        MicVideoPaperInfo mvpaper=new MicVideoPaperInfo();
        mvpaper.setMicvideoid(resList.get(0).getResid());
        List<MicVideoPaperInfo> mvpaperList=this.micVideoPaperManager.getList(mvpaper,null);
        if(mvpaperList==null||mvpaperList.size()<1||mvpaperList.get(0)==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndCloseWin());return null;
        }

        PaperInfo pp=new PaperInfo();
        pp.setPaperid(mvpaperList.get(0).getPaperid());
        List<PaperInfo>tpCoursePaperList=this.paperManager.getList(pp, null);
        if(tpCoursePaperList==null||tpCoursePaperList.size()<1){
            je.setMsg("抱歉该试卷已不存在!");
            je.getAlertMsgAndBack();
            return null;
        }

        //获取提干
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(mvpaperList.get(0).getPaperid());
        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        p.setPageNo(0);
        p.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);

        //获取试题组下题目
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);

        //获取选项
        QuestionOption questionOption=new QuestionOption();
        questionOption.setPaperid(pq.getPaperid());
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        List<QuestionOption>questionOptionList=this.questionOptionManager.getPaperQuesOptionList(questionOption, pchild);

        //整合试题组与选项
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
        List<QuestionOption>tmpOptionList;
        List<PaperQuestion>questionTeam;
        if(pqList!=null&&pqList.size()>0){
            for(PaperQuestion paperQuestion:pqList){
                questionTeam=new ArrayList<PaperQuestion>();
                //试题组
                if(childList!=null&&childList.size()>0){
                    for (PaperQuestion childp :childList){
                        //试题组选项
                        if(paperQuestion.getRef().equals(childp.getRef())){
                            if(questionOptionList!=null&&questionOptionList.size()>0){
                                tmpOptionList=new ArrayList<QuestionOption>();
                                for(QuestionOption qo:questionOptionList){
                                    if(qo.getQuestionid().equals(childp.getQuestionid())){
                                        tmpOptionList.add(qo);
                                    }
                                }
                                childp.setQuestionOption(tmpOptionList);
                                questionTeam.add(childp);
                            }
                        }
                    }
                    paperQuestion.setQuestionTeam(questionTeam);
                }

                if(questionOptionList!=null&&questionOptionList.size()>0){
                    //普通试题选项
                    List<QuestionOption> tmp1OptionList=new ArrayList<QuestionOption>();
                    for(QuestionOption qo:questionOptionList){
                        if(qo.getQuestionid().equals(paperQuestion.getQuestionid())){
                            tmp1OptionList.add(qo);
                        }
                    }

                    paperQuestion.setQuestionOption(tmp1OptionList);
                }
                tmpList.add(paperQuestion);
            }
        }

        request.setAttribute("pqList", tmpList);
        request.setAttribute("paper", tpCoursePaperList.get(0));


        mp.put("resObj", resList.get(0));
        mp.put("paperid", mvpaperList.get(0).getPaperid().toString());
        mp.put("courseid",courseid);
        mp.put("taskid",taskid);

        return new ModelAndView("/teachpaltform/resource/mic-view-detail",mp);
    }
    /**
     * 查询微视频列表 发任务
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=previewMicModel",method=RequestMethod.GET)
    public ModelAndView previewMicModel(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resid");
        String taskid=request.getParameter("taskid");
        if(courseid==null||courseid.trim().length()<1//||taskid==null||taskid.trim().length()<1
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return null;
        }
        ResourceInfo rtmp=new ResourceInfo();
        rtmp.setResid(Long.parseLong(resid));
        List<ResourceInfo> resList=this.resourceManager.getList(rtmp,null);
        //验证微视频是否存在相关数据
        if(resList==null||resList.size()<1||resList.get(0)==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndCloseWin());return null;
        }

        //得到相关的试卷ID
        MicVideoPaperInfo mvpaper=new MicVideoPaperInfo();
        mvpaper.setMicvideoid(resList.get(0).getResid());
        List<MicVideoPaperInfo> mvpaperList=this.micVideoPaperManager.getList(mvpaper,null);
        if(mvpaperList==null||mvpaperList.size()<1||mvpaperList.get(0)==null){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().println(je.getAlertMsgAndCloseWin());return null;
        }

        PaperInfo pp=new PaperInfo();
        pp.setPaperid(mvpaperList.get(0).getPaperid());
        List<PaperInfo>tpCoursePaperList=this.paperManager.getList(pp, null);
        if(tpCoursePaperList==null||tpCoursePaperList.size()<1){
            je.setMsg("抱歉该试卷已不存在!");
            je.getAlertMsgAndBack();
            return null;
        }

        //获取提干
        PaperQuestion pq=new PaperQuestion();
        pq.setPaperid(mvpaperList.get(0).getPaperid());
        PageResult p=new PageResult();
        p.setOrderBy("u.order_idx");
        p.setPageNo(0);
        p.setPageSize(0);
        List<PaperQuestion>pqList=this.paperQuestionManager.getList(pq,p);

        //获取试题组下题目
        PaperQuestion child =new PaperQuestion();
        child.setPaperid(pq.getPaperid());
        List<PaperQuestion>childList=this.paperQuestionManager.getPaperTeamQuestionList(child,null);

        //获取选项
        QuestionOption questionOption=new QuestionOption();
        questionOption.setPaperid(pq.getPaperid());
        PageResult pchild = new PageResult();
        pchild.setPageNo(0);
        pchild.setPageSize(0);
        pchild.setOrderBy("option_type");
        List<QuestionOption>questionOptionList=this.questionOptionManager.getPaperQuesOptionList(questionOption, pchild);

        //整合试题组与选项
        List<PaperQuestion> tmpList=new ArrayList<PaperQuestion>();
        List<QuestionOption>tmpOptionList;
        List<PaperQuestion>questionTeam;
        if(pqList!=null&&pqList.size()>0){
            for(PaperQuestion paperQuestion:pqList){
                questionTeam=new ArrayList<PaperQuestion>();
                //试题组
                if(childList!=null&&childList.size()>0){
                    for (PaperQuestion childp :childList){
                        //试题组选项
                        if(paperQuestion.getRef().equals(childp.getRef())){
                            if(questionOptionList!=null&&questionOptionList.size()>0){
                                tmpOptionList=new ArrayList<QuestionOption>();
                                for(QuestionOption qo:questionOptionList){
                                    if(qo.getQuestionid().equals(childp.getQuestionid())){
                                        tmpOptionList.add(qo);
                                    }
                                }
                                childp.setQuestionOption(tmpOptionList);
                                questionTeam.add(childp);
                            }
                        }
                    }
                    paperQuestion.setQuestionTeam(questionTeam);
                }

                if(questionOptionList!=null&&questionOptionList.size()>0){
                    //普通试题选项
                    List<QuestionOption> tmp1OptionList=new ArrayList<QuestionOption>();
                    for(QuestionOption qo:questionOptionList){
                        if(qo.getQuestionid().equals(paperQuestion.getQuestionid())){
                            tmp1OptionList.add(qo);
                        }
                    }

                    paperQuestion.setQuestionOption(tmp1OptionList);
                }
                tmpList.add(paperQuestion);
            }
        }

        request.setAttribute("pqList", tmpList);
        request.setAttribute("paper", tpCoursePaperList.get(0));


        mp.put("resObj", resList.get(0));
        mp.put("paperid", mvpaperList.get(0).getPaperid().toString());
        mp.put("courseid",courseid);
        mp.put("taskid",taskid);

        return new ModelAndView("/teachpaltform/task/teacher/dialog/childPage/mic-view-detail",mp);
    }





    /**
     * ??????????
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="getResFilePath",method=RequestMethod.POST)
    public void getResFilePath(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity("success","");
        String resid=request.getParameter("resid");
        if(resid==null||resid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        je.getObjList().add(UtilTool.getResourceLocation(Long.parseLong(resid),1));
        response.getWriter().print(je.toJSON());
    }

    /**
     * ??????????????
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="checkConvertStatus",method=RequestMethod.POST)
    public void checkConvertStatus(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity("success","");
        String resid=request.getParameter("resid");
        if(resid==null||resid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ResourceInfo r=new ResourceInfo();
        r.setResid(Long.parseLong(resid));
        List<ResourceInfo>resourceInfoList=this.resourceManager.getList(r,null);
        if(resourceInfoList==null||resourceInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }else {
            r=resourceInfoList.get(0);
            String filetype=UtilTool.getConvertResourseType(r.getResname()+r.getFilesuffixname());
            if(filetype!=null&&filetype.equals("doc")){
                //String destPath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+UtilTool.getConvertPath(r.getResid().toString(),r.getFilesuffixname());
                String destPath=UtilTool.getResourceLocation(r.getResid(),2)+"/"+UtilTool.getConvertPath(r.getResid().toString(),r.getFilesuffixname());
                System.out.println("checkConvertStatus destpath:"+destPath);
                if(!new File(destPath).exists()){
                    je.setMsg("当前资源不可预览，请等待转换完成后预览!");
                    je.setObjList(resourceInfoList);
                }
            }
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 转SWF
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="convertDocResource",method=RequestMethod.POST)
    public void convertDocResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String resid=request.getParameter("resid");
        if(resid==null||resid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ResourceInfo r=new ResourceInfo();
        r.setResid(Long.parseLong(resid));
        List<ResourceInfo>resourceInfoList=this.resourceManager.getList(r,null);
        if(resourceInfoList==null||resourceInfoList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }else {
            r=resourceInfoList.get(0);
            String filetype=UtilTool.getConvertResourseType(r.getResname()+r.getFilesuffixname());
            if(filetype!=null&&filetype.equals("doc")){
                boolean flag=false;
                String destPath=UtilTool.getResourceLocation(r.getResid(),2)+"/"+UtilTool.getConvertPath(r.getResid().toString(),r.getFilesuffixname());
                System.out.println("convertDoc destPath:"+destPath);
                String file=UtilTool.getResourceLocation(r.getResid(),2)+"/"+UtilTool.getResourceFileUrl(r.getResid().toString(),r.getFilesuffixname());
                System.out.println("convertDoc srcPath:"+file);
                if(!new File(file).exists()){
                    System.out.println("RES_ID:"+r.getResid()+"path:"+file);
                    je.setMsg("文件不存在!");
                    response.getWriter().print(je.toJSON());
                    return;
                }

                if(!new File(destPath).exists())
                    flag=UtilTool.Office2Swf(request,r.getResid().toString(), r.getFilesuffixname());
                if(flag){
                    je.setMsg("转换成功!");
                    je.setType("success");
                    ResourceInfo update=new ResourceInfo();
                    update.setResid(r.getResid());
                    update.setConvertstatus(1);
                    this.resourceManager.doUpdate(update);
                    // Thread.sleep(TimeUnit.MINUTES.toMillis(1));
                }else
                    je.setMsg("转换失败!");
            }
            response.getWriter().print(je.toJSON());
        }
    }


    /**
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="convertResource",method={RequestMethod.POST,RequestMethod.GET})
    public void convertResource(HttpServletRequest request)throws Exception{
        JsonEntity je=new JsonEntity();
        int i=0;
        while (true){
            i=i+1;
            PageResult pageResult=new PageResult();
            pageResult.setPageSize(20);
            pageResult.setPageNo(i);
            ResourceInfo rs=new ResourceInfo();
            rs.setConvertstatus(0);
            List<ResourceInfo>resourceInfoList=this.resourceManager.getList(rs,pageResult);
            if(resourceInfoList==null||resourceInfoList.size()<1){
                System.out.println("No Files need convert...");
                break;
            }else{
                //??????
                for(ResourceInfo resinfo:resourceInfoList){
                    String filetype=UtilTool.getConvertResourseType(resinfo.getFilename());
                    if(filetype!=null&&filetype.equals("doc")){
                        boolean flag=false;
                        String destPath=UtilTool.getResourceLocation(resinfo.getResid(),2)+"/"+UtilTool.getConvertPath(resinfo.getResid().toString(),resinfo.getFilesuffixname());
                        String file=UtilTool.getResourceLocation(resinfo.getResid(),2)+"/"+UtilTool.getResourceFileUrl(resinfo.getResid().toString(),resinfo.getFilesuffixname());
                        if(!new File(file).exists()){
                            System.out.println("RES_ID:"+resinfo.getResid()+"PATH:"+file);
                            continue;
                        }

                        if(!new File(destPath).exists()){
                            System.out.println("CONVERT--------RES_ID:"+resinfo.getResid()+"PATH:"+file);
                            try{
                                flag=UtilTool.Office2Swf(request,resinfo.getResid().toString(), resinfo.getFilesuffixname());
                            }catch (Exception e){
                                flag=UtilTool.Office2Swf(request,resinfo.getResid().toString(), resinfo.getFilesuffixname());
                            }

                            if(flag){
                                ResourceInfo update=new ResourceInfo();
                                update.setResid(resinfo.getResid());
                                update.setConvertstatus(1);
                                if(this.resourceManager.doUpdate(update)){
                                    System.out.println("update convert status success!");
                                }else{
                                    System.out.println("update convert status error!");
                                }
                                // Thread.sleep(TimeUnit.MINUTES.toMillis(1));
                            }
                            System.out.print("File:"+resinfo.getFilename()+"convert "+(flag==true?"success":"err"));
                        }else{
                            ResourceInfo update=new ResourceInfo();
                            update.setResid(resinfo.getResid());
                            update.setConvertstatus(1);
                            if(this.resourceManager.doUpdate(update)){
                                System.out.println("update convert status success!");
                            }else{
                                System.out.println("update convert status error!");
                            }
                        }
                    }else{
                        ResourceInfo update=new ResourceInfo();
                        update.setResid(resinfo.getResid());
                        update.setConvertstatus(1);
                        if(this.resourceManager.doUpdate(update)){
                            System.out.println("update convert status success!");
                        }else{
                            System.out.println("update convert status error!");
                        }
                    }
                }

            }
        }
    }

    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=toRemoteResourcesDetail",method=RequestMethod.GET)
    public void toRemoteResourcesDetail(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String hd_res_id=request.getParameter("hd_res_id");
        String res_id = request.getParameter("res_id");
        String taskid=request.getParameter("taskid");
        //检测是否有查看标准
        if(taskid!=null&&taskid.trim().length()>0&&this.validateRole(request,UtilTool._ROLE_STU_ID)){
            TpTaskInfo t=new TpTaskInfo();
            t.setTaskid(Long.parseLong(taskid));
            t.setCriteria(1);
            List<TpTaskInfo>taskCriList=this.tpTaskManager.getList(t, null);
            if(taskCriList!=null&&taskCriList.size()>0){
                TpTaskInfo task=taskCriList.get(0);

                //录入完成状态
                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                tp.setTaskid(task.getTaskid());
                tp.setCourseid(task.getCourseid());
                tp.setTasktype(task.getTasktype());
                tp.setCriteria(1);
                tp.setUserid(this.logined(request).getRef());
                tp.setIsright(1);
                List<TaskPerformanceInfo>tList=this.taskPerformanceManager.getList(tp,null);
                if(taskCriList!=null&&taskCriList.size()>0&&taskCriList.get(0).getTaskstatus()!=null
                        &&!taskCriList.get(0).getTaskstatus().equals("1")&&!taskCriList.get(0).getTaskstatus().equals("3")){
                    if(tList==null||tList.size()<1){
                        if(this.taskPerformanceManager.doSave(tp)){
                            System.out.println("添加资源查看记录成功...");
                        }else{
                            je.setMsg("错误!添加查看记录失败!请重试!");
                            response.getWriter().print(je.toJSON());
                            return;
                        }
                    }
                }
            }
        }

        Date d = new Date();
        String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/openUrlById.jsp";
        if(hd_res_id!=null&&hd_res_id.length()>0){
            Long timestamp=d.getTime();
            String md5key = hd_res_id+timestamp.toString()+"ett_dc_20146305645645647";
            String signature= MD5_NEW.getMD5Result(md5key);
            url+="?hd_res_id="+hd_res_id+"&timestamp="+timestamp+"&signature="+signature;
        }else if(res_id!=null&&res_id.length()>0){
            Long timestamp=d.getTime();
            String md5key = res_id+timestamp.toString()+"ett_dc_20146305645645647";
            String signature= MD5_NEW.getMD5Result(md5key);
            url+="?res_id="+res_id+"&timestamp="+timestamp+"&signature="+signature;
        }
        response.sendRedirect(url);
    }

    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=toRemoteResources",method=RequestMethod.GET)
    public ModelAndView toRemoteResources(HttpServletRequest request,HttpServletResponse response) throws Exception {
        String courseid = request.getParameter("courseid");
        TpCourseTeachingMaterial obj= new TpCourseTeachingMaterial();
        obj.setCourseid(Long.parseLong(courseid));
        List<TpCourseTeachingMaterial> objList = tpCourseTeachingMaterialManager.getList(obj,null);
        request.setAttribute("versionid",objList.get(0).getTeachingmaterialid());
        request.setAttribute("courseid",courseid);
        //栏目
        IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
        EttColumnInfo ettColumnInfo = new EttColumnInfo();
        ettColumnInfo.setRoletype(2);
        List<EttColumnInfo> columnList =columnManager.getEttColumnSplit(ettColumnInfo,null);
        if(columnList!=null&&columnList.size()>0){
            Boolean b1= false;
            Boolean b2 = false;
            for(EttColumnInfo o:columnList){
                if(o.getEttcolumnid()==7){
                    b1=true;
                }
                if(o.getEttcolumnid()==320){
                    b2=true;
                }
            }
            request.setAttribute("sign1",b1);
            request.setAttribute("sign2",b2);
        }
        List<GradeInfo> gList = this.gradeManager.getList(null, null);
        request.setAttribute("gradeList",gList );
        //资源类型
        List<DictionaryInfo>resourceTypeList=this.dictionaryManager.getDictionaryByType("RES_TYPE");
        request.setAttribute("courseid",courseid);
        request.setAttribute("resType", resourceTypeList);
        request.setAttribute("fileSystemIpPort", request.getSession().getAttribute("FILE_SYSTEM_IP_PORT").toString());
        return new ModelAndView("/teachpaltform/resource/remote-resource-list");
    }

    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=getRemoteResources",method={RequestMethod.POST,RequestMethod.GET})
    public void getRemoteResources(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String pageNow = request.getParameter("pageNow");
        String pageSize = request.getParameter("pageSize");
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String versionid = request.getParameter("versionid");
        String schoolid = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
        String courseid = request.getParameter("courseid");
        String keyword = request.getParameter("keyword");
        if(keyword!=null&&keyword.length()>0){
            keyword= URLEncoder.encode(keyword,"GBK");
        }
        String courseIDList = "";
        //获取关联专题集合
        TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
        tr.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr, null);
        //先判断有没有    权限
        Boolean b1= false;
        Boolean b2= false;
        //栏目
        IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
        EttColumnInfo ettColumnInfo = new EttColumnInfo();
        ettColumnInfo.setRoletype(2);
        List<EttColumnInfo> columnList =columnManager.getEttColumnSplit(ettColumnInfo,null);
        if(columnList!=null&&columnList.size()>0){
            for(EttColumnInfo obj:columnList){
                if(obj.getEttcolumnid()==7){
                    b1=true;
                }
                if(obj.getEttcolumnid()==320){
                    b2=true;
                }
            }
        }
        Date d = new Date();
        if(b2){
            String pvgStr = "320";
            Long timestamp=d.getTime();
            //http://localhost:8080/sz_school/tpres?m=getRemoteResources&gradeid=3&subjectid=4&versionid=44&pageNow=1&pageSize=10
            String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
            String signature= MD5_NEW.getMD5Result(md5key);
            String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/queryForWebservice.jsp";
            //String url="http://wangjie.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
            String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
                    gradeid+"&subjectId="+subjectid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+pageNow+"&pageSize="+
                    pageSize;
            if(keyword!=null&&keyword.length()>0){
                param+="&tchVersionId="+versionid+"&keyword="+keyword;
            }else{
                StringBuilder sb = new StringBuilder();
                //首先得到本专题
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(courseid));
                List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
                if(tcList.get(0).getQuoteid()!=null)
                    sb.append("{course_id:\""+tcList.get(0).getQuoteid()+"\"}");
                if(trList!=null&&trList.size()>0){
                    for(int i = 0;i<trList.size();i++){
                        sb.append("{course_id:\""+trList.get(i).getRelatedcourseid()+"\"}");
                    }
                }
                String courseidlist = "["+sb.toString()+"]";
                param+="&courseIDList="+courseidlist;

            }
            String returnval=sendPostURL(url,param);
            //  System.out.println(new String(returnval.getBytes("8859_1"),"GBK"));
            if(returnval!=null&&returnval.length()>0){
                //转换成JSON
                JSONObject jb=JSONObject.fromObject(returnval);
                String type=jb.containsKey("type")?jb.getString("type"):"";
                String ispage=jb.containsKey("ispage")?jb.getString("ispage"):"";
                Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
                if(type!=null&&type.trim().toLowerCase().equals("error")){
                    je.setMsg("获取资源失败");
                    je.setType("error");
                }else{
                    je.setType("success");
                    JSONArray jr=JSONArray.fromObject(objList);
                    je.getObjList().add(jr);
                    je.getObjList().add(ispage);
                    TpTaskInfo task = new TpTaskInfo();
                    task.setCourseid(Long.parseLong(courseid));
                    task.setRemotetype(1);
                    List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
                    je.getObjList().add(taskList);

                }
            }else{
                je.getObjList().add(null);
                je.getObjList().add(1);
                je.getObjList().add(null);
            }
        }
        if(b1){
            String pvgStr="7";
            Long timestamp=d.getTime();
            //http://localhost:8080/sz_school/tpres?m=getRemoteResources&gradeid=3&subjectid=4&versionid=44&pageNow=1&pageSize=10
            String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
            String signature= MD5_NEW.getMD5Result(md5key);
            String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/queryForWebservice.jsp";
            //String url="http://wangjie.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
            String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
                    gradeid+"&subjectId="+subjectid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+pageNow+"&pageSize="+
                    pageSize;
            if(keyword!=null&&keyword.length()>0){
                param+="&tchVersionId="+versionid+"&keyword="+keyword;
            }else{
                StringBuilder sb = new StringBuilder();
                //首先得到本专题
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(courseid));
                List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
                if(tcList.get(0).getQuoteid()!=null)
                    sb.append("{course_id:\""+tcList.get(0).getQuoteid()+"\"}");
                if(trList!=null&&trList.size()>0){
                    for(int i = 0;i<trList.size();i++){
                        sb.append("{course_id:\""+trList.get(i).getRelatedcourseid()+"\"}");
                    }
                }
                String courseidlist = "["+sb.toString()+"]";
                param+="&courseIDList="+courseidlist;
            }
            String returnval2=sendPostURL(url,param);
            if(returnval2!=null&&returnval2.length()>0){
                //转换成JSON
                JSONObject jb=JSONObject.fromObject(returnval2);
                String type=jb.containsKey("type")?jb.getString("type"):"";
                String ispage=jb.containsKey("ispage")?jb.getString("ispage"):"";
                Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
                if(type!=null&&type.trim().toLowerCase().equals("error")){
                    je.setMsg("获取资源失败");
                    je.setType("error");
                }else{
                    je.setType("success");
                    JSONArray jr=JSONArray.fromObject(objList);
                    je.getObjList().add(jr);
                    je.getObjList().add(ispage);

                    TpTaskInfo task = new TpTaskInfo();
                    task.setCourseid(Long.parseLong(courseid));
                    task.setRemotetype(2);
                    List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
                    je.getObjList().add(taskList);
                }
            }
        }
        //  response.setCharacterEncoding("GBK");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=getRemoteResources1",method={RequestMethod.POST,RequestMethod.GET})
    public void getRemoteResources1(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult p = this.getPageResultParameter(request);
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String versionid = request.getParameter("versionid");
        String schoolid = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
        String courseid = request.getParameter("courseid");
        String keyword = request.getParameter("keyword");
        if(keyword!=null&&keyword.length()>0){
            keyword= URLEncoder.encode(keyword,"GBK");
        }
        String courseIDList = "";
        //获取关联专题集合
        TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
        tr.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr, null);
        //先判断有没有    权限
        Boolean b2= false;
        //栏目
        IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
        EttColumnInfo ettColumnInfo = new EttColumnInfo();
        ettColumnInfo.setRoletype(2);
        List<EttColumnInfo> columnList =columnManager.getEttColumnSplit(ettColumnInfo,null);
        if(columnList!=null&&columnList.size()>0){
            for(EttColumnInfo obj:columnList){
                if(obj.getEttcolumnid()==320){
                    b2=true;
                }
            }
        }
        Date d = new Date();
        if(b2){
            String pvgStr = "320";
            Long timestamp=d.getTime();
            //http://localhost:8080/sz_school/tpres?m=getRemoteResources&gradeid=3&subjectid=4&versionid=44&pageNow=1&pageSize=10
            String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
            String signature= MD5_NEW.getMD5Result(md5key);
            String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/queryForWebservice.jsp";
            //String url="http://wangjie.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
            String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
                    gradeid+"&subjectId="+subjectid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+p.getPageNo()+"&pageSize="+
                    p.getPageSize();
            if(keyword!=null&&keyword.length()>0){
                param+="&tchVersionId="+versionid+"&keyword="+keyword;
            }else{
                StringBuilder sb = new StringBuilder();
                //首先得到本专题
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(courseid));
                List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
                if(tcList.get(0).getQuoteid()!=null)
                    sb.append("{course_id:\""+tcList.get(0).getQuoteid()+"\"}");
                if(trList!=null&&trList.size()>0){
                    for(int i = 0;i<trList.size();i++){
                        sb.append("{course_id:\""+trList.get(i).getRelatedcourseid()+"\"}");
                    }
                }
                String courseidlist = "["+sb.toString()+"]";
                param+="&courseIDList="+courseidlist;

            }
            String returnval=sendPostURL(url,param);
            //  System.out.println(new String(returnval.getBytes("8859_1"),"GBK"));
            if(returnval!=null&&returnval.length()>0){
                //转换成JSON
                JSONObject jb=JSONObject.fromObject(returnval);
                String type=jb.containsKey("type")?jb.getString("type"):"";
                String ispage=jb.containsKey("ispage")?jb.getString("ispage"):"";
                Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
                int rectotal = jb.containsKey("totalRows")?jb.getInt("totalRows"):0;
                if(type!=null&&type.trim().toLowerCase().equals("error")){
                    je.setMsg("获取资源失败");
                    je.setType("error");
                }else{
                    je.setType("success");
                    JSONArray jr=JSONArray.fromObject(objList);
                    p.getList().add(jr);
                    TpTaskInfo task = new TpTaskInfo();
                    task.setCourseid(Long.parseLong(courseid));
                    task.setRemotetype(1);
                    List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
                    p.getList().add(taskList);
                    p.setRecTotal(rectotal);
                }
            }else{
                p.getList().add(null);
                p.getList().add(null);
            }
        }
        //  response.setCharacterEncoding("GBK");
        je.setPresult(p);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=getRemoteResources2",method={RequestMethod.POST,RequestMethod.GET})
    public void getRemoteResources2(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult p = this.getPageResultParameter(request);
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String versionid = request.getParameter("versionid");
        String schoolid = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
        String courseid = request.getParameter("courseid");
        String keyword = request.getParameter("keyword");
        if(keyword!=null&&keyword.length()>0){
            keyword= URLEncoder.encode(keyword,"GBK");
        }
        String courseIDList = "";
        //获取关联专题集合
        TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
        tr.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr, null);
        //先判断有没有    权限
        Boolean b1= false;
        //栏目
        IColumnManager columnManager=(ColumnManager)this.getManager(ColumnManager.class);
        EttColumnInfo ettColumnInfo = new EttColumnInfo();
        ettColumnInfo.setRoletype(2);
        List<EttColumnInfo> columnList =columnManager.getEttColumnSplit(ettColumnInfo,null);
        if(columnList!=null&&columnList.size()>0){
            for(EttColumnInfo obj:columnList){
                if(obj.getEttcolumnid()==7){
                    b1=true;
                }
            }
        }
        Date d = new Date();

        if(b1){
            String pvgStr="7";
            Long timestamp=d.getTime();
            //http://localhost:8080/sz_school/tpres?m=getRemoteResources&gradeid=3&subjectid=4&versionid=44&pageNow=1&pageSize=10
            String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
            String signature= MD5_NEW.getMD5Result(md5key);
            String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/queryForWebservice.jsp";
           // String url="http://wangjie.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
            String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
                    gradeid+"&subjectId="+subjectid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+p.getPageNo()+"&pageSize="+
                    p.getPageSize();
            if(keyword!=null&&keyword.length()>0){
                param+="&tchVersionId="+versionid+"&keyword="+keyword;
            }else{
                StringBuilder sb = new StringBuilder();
                //首先得到本专题
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(courseid));
                List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
                if(tcList.get(0).getQuoteid()!=null)
                    sb.append("{course_id:\""+tcList.get(0).getQuoteid()+"\"}");
                if(trList!=null&&trList.size()>0){
                    for(int i = 0;i<trList.size();i++){
                        sb.append("{course_id:\""+trList.get(i).getRelatedcourseid()+"\"}");
                    }
                }
                String courseidlist = "["+sb.toString()+"]";
                param+="&courseIDList="+courseidlist;
            }
            String returnval2=sendPostURL(url,param);
            if(returnval2!=null&&returnval2.length()>0){
                //转换成JSON
                JSONObject jb=JSONObject.fromObject(returnval2);
                String type=jb.containsKey("type")?jb.getString("type"):"";
                String ispage=jb.containsKey("ispage")?jb.getString("ispage"):"";
                Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
                int rectotal = jb.containsKey("totalRows")?jb.getInt("totalRows"):0;
                if(type!=null&&type.trim().toLowerCase().equals("error")){
                    je.setMsg("获取资源失败");
                    je.setType("error");
                }else{
                    je.setType("success");
                    JSONArray jr=JSONArray.fromObject(objList);
                    p.getList().add(jr);
                    TpTaskInfo task = new TpTaskInfo();
                    task.setCourseid(Long.parseLong(courseid));
                    task.setRemotetype(1);
                    List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
                    p.getList().add(taskList);
                    p.setRecTotal(rectotal);
                }
            }else{
                p.getList().add(null);
                p.getList().add(null);
            }
        }
        //  response.setCharacterEncoding("GBK");
        je.setPresult(p);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=getMoreResources",method={RequestMethod.POST,RequestMethod.GET})
    public void getMoreResources(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String pageNow = request.getParameter("pageNow");
        String pageSize = request.getParameter("pageSize");
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String versionid = request.getParameter("versionid");
        String schoolid = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
        String courseid = request.getParameter("courseid");
        String keyword = request.getParameter("keyword");
        String courseIDList = "";
        //获取关联专题集合
        TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
        tr.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr, null);
        String pvgStr = "320";
        Date d = new Date();
        Long timestamp=d.getTime();
        String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
        String signature= MD5_NEW.getMD5Result(md5key);
        String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/queryForWebservice.jsp";
        //url="http://langyilin.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
        String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
                gradeid+"&subjectId="+subjectid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+pageNow+"&pageSize="+
                pageSize;
        if(keyword!=null&&keyword.length()>0){
            keyword= URLEncoder.encode(keyword,"GBK");
            param+="&tchVersionId="+versionid+"&keyword="+keyword;
        }else{
            if(trList!=null&&trList.size()>0){
                StringBuilder sb = new StringBuilder();
                //首先得到本专题
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(courseid));
                List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
                if(tcList.get(0).getQuoteid()!=null)
                    sb.append("{course_id:\""+tcList.get(0).getQuoteid()+"\"}");
                for(int i = 0;i<trList.size();i++){
                    sb.append("{course_id:\""+trList.get(i).getRelatedcourseid()+"\"}");
                }
                String courseidlist = "["+sb.toString()+"]";
                param+="&courseIDList="+courseidlist;
            }
        }
        String returnval=sendPostURL(url,param);
        //  System.out.println(new String(returnval.getBytes("8859_1"),"GBK"));
        if(returnval.length()>0){
            //转换成JSON
            JSONObject jb=JSONObject.fromObject(returnval);
            String type=jb.containsKey("type")?jb.getString("type"):"";
            String ispage=jb.containsKey("ispage")?jb.getString("ispage"):"";
            Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
            if(type!=null&&type.trim().toLowerCase().equals("error")){
                je.setMsg("获取资源失败");
                je.setType("error");
            }else{
                je.setType("success");
                JSONArray jr=JSONArray.fromObject(objList);
                je.getObjList().add(jr);
                je.getObjList().add(ispage);
                TpTaskInfo task = new TpTaskInfo();
                task.setCourseid(Long.parseLong(courseid));
                task.setRemotetype(1);
                List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
                je.getObjList().add(taskList);
            }
        }

        response.getWriter().print(je.toJSON());
    }



    /**
     * 获取远程资源列表
     * ycy
     * */
    @RequestMapping(params="m=getMoreZhishi",method={RequestMethod.POST,RequestMethod.GET})
    public void getMoreZhishi(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String pageNow = request.getParameter("pageNow");
        String pageSize = request.getParameter("pageSize");
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String versionid = request.getParameter("versionid");
        String schoolid = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
        String courseid = request.getParameter("courseid");
        String keyword = request.getParameter("keyword");
        String courseIDList = "";
        //获取关联专题集合
        TpCourseRelatedInfo tr = new TpCourseRelatedInfo();
        tr.setCourseid(Long.parseLong(courseid));
        List<TpCourseRelatedInfo> trList = this.tpCourseRelatedManager.getList(tr,null);
        String pvgStr = "7";
        Date d = new Date();
        Long timestamp=d.getTime();
        String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
        String signature= MD5_NEW.getMD5Result(md5key);
        String url=UtilTool.utilproperty.getProperty("REMOTE_RESOURCE_IP")+"ett20/study/jx/queryForWebservice.jsp";
        //url="http://langyilin.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
        String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
                gradeid+"&subjectId="+subjectid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+pageNow+"&pageSize="+
                pageSize;
        if(keyword!=null&&keyword.length()>0){
            keyword= URLEncoder.encode(keyword,"GBK");
            param+="&tchVersionId="+versionid+"&keyword="+keyword;
        }else{
            if(trList!=null&&trList.size()>0){
                StringBuilder sb = new StringBuilder();
                //首先得到本专题
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(courseid));
                List<TpCourseInfo> tcList = this.tpCourseManager.getList(tc,null);
                if(tcList.get(0).getQuoteid()!=null)
                    sb.append("{course_id:\""+tcList.get(0).getQuoteid()+"\"}");
                for(int i = 0;i<trList.size();i++){
                    sb.append("{course_id:\""+trList.get(i).getRelatedcourseid()+"\"}");
                }
                String courseidlist = "["+sb.toString()+"]";
                param+="&courseIDList="+courseidlist;
            }
        }
        String returnval=sendPostURL(url,param);
        //  System.out.println(new String(returnval.getBytes("8859_1"),"GBK"));
        if(returnval.length()>0){
            //转换成JSON
            JSONObject jb=JSONObject.fromObject(returnval);
            String type=jb.containsKey("type")?jb.getString("type"):"";
            String ispage=jb.containsKey("ispage")?jb.getString("ispage"):"";
            Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
            if(type!=null&&type.trim().toLowerCase().equals("error")){
                je.setMsg("获取资源失败");
                je.setType("error");
            }else{
                je.setType("success");
                JSONArray jr=JSONArray.fromObject(objList);
                je.getObjList().add(jr);
                je.getObjList().add(ispage);
                TpTaskInfo task = new TpTaskInfo();
                task.setCourseid(Long.parseLong(courseid));
                task.setRemotetype(2);
                List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
                je.getObjList().add(taskList);
            }
        }

        response.getWriter().print(je.toJSON());
    }

//    /**
//     * 获取远程资源列表
//     * ycy
//     * */
//    @RequestMapping(params="m=getLikeRemoteResources",method={RequestMethod.POST,RequestMethod.GET})
//    public void getLikeRemoteResources(HttpServletRequest request,HttpServletResponse response) throws Exception {
//        JsonEntity je = new JsonEntity();
//        String pageNow = request.getParameter("pageNow");
//        String pageSize = request.getParameter("pageSize");
//        String gradeid = request.getParameter("gradeid");
//        String subjectid = request.getParameter("subjectid");
//        String versionid = request.getParameter("versionid");
//        String keyword = request.getParameter("keyword");
//        String courseid=request.getParameter("courseid");
//        String schoolid = UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_ID");
//        String pvgStr = "320";
//        versionid="484023358";
//        Date d = new Date();
//        Long timestamp=d.getTime();
//        //http://localhost:8080/sz_school/tpres?m=getRemoteResources&gradeid=3&subjectid=4&versionid=44&pageNow=1&pageSize=10
//        String md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
//        String signature= MD5_NEW.getMD5Result(md5key);
//        String url="http://langyilin.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
//        String param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
//                gradeid+"&subjectId="+subjectid+"&tchVersionId="+versionid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+pageNow+"&pageSize="+
//                pageSize;
//        if(keyword!=null&&keyword.length()>0){
//            keyword= URLEncoder.encode(keyword,"GBK");
//            param+="&keyword="+keyword;
//        }
//        String returnval=sendPostURL(url,param);
//        //  System.out.println(new String(returnval.getBytes("8859_1"),"GBK"));
//        if(returnval.length()>0){
//            //转换成JSON
//            JSONObject jb=JSONObject.fromObject(returnval);
//            String type=jb.containsKey("type")?jb.getString("type"):"";
//            Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
//            if(type!=null&&type.trim().toLowerCase().equals("error")){
//                je.setMsg("获取资源失败");
//                je.setType("error");
//            }else{
//                je.setType("success");
//                JSONArray jr=JSONArray.fromObject(objList);
//                je.getObjList().add(jr);
//                TpTaskInfo task = new TpTaskInfo();
//                task.setCourseid(Long.parseLong(courseid));
//                task.setRemotetype(1);
//                List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
//                je.getObjList().add(taskList);
//            }
//        }
//        pvgStr="7";
//        timestamp=d.getTime();
//        //http://localhost:8080/sz_school/tpres?m=getRemoteResources&gradeid=3&subjectid=4&versionid=44&pageNow=1&pageSize=10
//        md5key = schoolid+pvgStr+timestamp.toString()+"ett_dc_20146305645645647";
//        signature= MD5_NEW.getMD5Result(md5key);
//        url="http://langyilin.etiantian.com:8080/ett20/study/jx/queryForWebservice.jsp";
//        param="timestamp="+timestamp+"&schoolId="+schoolid+"&gradeId="+
//                gradeid+"&subjectId="+subjectid+"&tchVersionId="+versionid+"&pvgStr="+pvgStr+"&signature="+signature+"&pageNow="+pageNow+"&pageSize="+
//                pageSize;
//        if(keyword!=null&&keyword.length()>0){
//            keyword= URLEncoder.encode(keyword,"GBK");
//            param+="&keyword="+keyword;
//        }
//        String returnval2=sendPostURL(url,param);
//        if(returnval2.length()>0){
//            //转换成JSON
//            JSONObject jb=JSONObject.fromObject(returnval2);
//            String type=jb.containsKey("type")?jb.getString("type"):"";
//            Object objList=jb.containsKey("dataList")?jb.get("dataList"):null;
//            if(type!=null&&type.trim().toLowerCase().equals("error")){
//                je.setMsg("获取资源失败");
//                je.setType("error");
//            }else{
//                je.setType("success");
//                JSONArray jr=JSONArray.fromObject(objList);
//                je.getObjList().add(jr);
//                TpTaskInfo task = new TpTaskInfo();
//                task.setCourseid(Long.parseLong(courseid));
//                task.setRemotetype(2);
//                List<TpTaskInfo> taskList = this.tpTaskManager.getDoTaskResourceId(task);
//                je.getObjList().add(taskList);
//            }
//        }
//        //  response.setCharacterEncoding("GBK");
//        response.getWriter().print(je.toJSON());
//    }

    /**
     * 自定义微视频
     * 给视频添加关联试卷
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="doAddVideoPaper",method={RequestMethod.POST,RequestMethod.GET})
    public void doAddVideoPaper(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String resid=request.getParameter("resid");
        String paperid=request.getParameter("paperid");
        if(resid==null||resid.trim().length()<1
                ||paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        MicVideoPaperInfo mvp=new MicVideoPaperInfo();
        mvp.setMicvideoid(Long.parseLong(resid));
        mvp.setPaperid(Long.parseLong(paperid));
        List<MicVideoPaperInfo>mvpList=this.micVideoPaperManager.getList(mvp,null);
        if(mvpList!=null&&mvpList.size()>0){
            je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlList=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //修改微视频DiffType
        ResourceInfo res=new ResourceInfo();
        res.setResid(mvp.getMicvideoid());
        res.setDifftype(1);
        sql=new StringBuilder();
        objList=this.resourceManager.getUpdateSql(res,sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqlList.add(sql.toString());
        }

        //添加视频试卷关系
        sql=new StringBuilder();
        objList=this.micVideoPaperManager.getSaveSql(mvp,sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqlList.add(sql.toString());
        }

        //修改试卷类型

        if(sqlList.size()>0&&objListArray.size()>0&&sqlList.size()==objListArray.size()){
            if(this.resourceManager.doExcetueArrayProc(sqlList,objListArray)){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }




    /**
     * 自定义微视频
     * 解除视频与关联试卷
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="doCancelVideoPaper",method={RequestMethod.POST,RequestMethod.GET})
    public void doCancelVideoPaper(HttpServletRequest request,HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        String resid=request.getParameter("resid");
        String paperid=request.getParameter("paperid");
        if(resid==null||resid.trim().length()<1
                ||paperid==null||paperid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        MicVideoPaperInfo mvp=new MicVideoPaperInfo();
        mvp.setMicvideoid(Long.parseLong(resid));
        mvp.setPaperid(Long.parseLong(paperid));
        List<MicVideoPaperInfo>mvpList=this.micVideoPaperManager.getList(mvp,null);
        if(mvpList==null||mvpList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<String>sqlList=new ArrayList<String>();
        List<List<Object>>objListArray=new ArrayList<List<Object>>();

        //修改微视频DiffType
        ResourceInfo res=new ResourceInfo();
        res.setResid(mvp.getMicvideoid());
        res.setDifftype(0);
        sql=new StringBuilder();
        objList=this.resourceManager.getUpdateSql(res,sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqlList.add(sql.toString());
        }

        //删除视频试卷关系
        sql=new StringBuilder();
        objList=this.micVideoPaperManager.getDeleteSql(mvp,sql);
        if(objList!=null&&sql!=null){
            objListArray.add(objList);
            sqlList.add(sql.toString());
        }

        if(sqlList.size()>0&&objListArray.size()>0&&sqlList.size()==objListArray.size()){
            if(this.tpCourseResourceManager.doExcetueArrayProc(sqlList,objListArray)){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            }else
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(je.toJSON());
    }

    /**
     *后台调用接口
     * @param urlstr
     * @return
     */
    public static String sendPostURL(String urlstr,String params){
        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlstr);

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
                outputStreamWriter.write(params);

            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("错误!TOTALSCHOOL未响应!");
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("错误!");
                e.printStackTrace();;
                return null;
            }
        }else if(code==404){
            // 提示 返回
            System.out.println("错误!404错误，请联系管理人员!");
            return null;
        }else if(code==500){
            System.out.println("错误!500错误，请联系管理人员!");
            return null;
        }
        String  returnContent=stringBuffer.toString();
//        Map<String,Object> returnMap=null;
//        //转换成JSON
//        System.out.println(returnContent);
//        JSONObject jb=JSONObject.fromObject(returnContent);
//        String type=jb.containsKey("type")?jb.getString("type"):"";
//        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
//        Object objList=jb.containsKey("objList")?jb.get("objList"):null;
//
//        returnMap=new HashMap<String,Object>();
//        returnMap.put("type",type);
//        returnMap.put("msg",msg);
//        System.out.println(msg);
//
//        if(type!=null&&type.trim().toLowerCase().equals("success")){
//            System.out.println(msg);
//            JSONArray jr=JSONArray.fromObject(objList);
//            String val="";
//            if(jr.size()>0)
//                val=jr.get(0).toString();
//            returnMap.put("objList",val);
//
//        }else{
//            System.out.println(msg);
//        }

        return returnContent;
    }




}

