package com.school.control.teachpaltform.interactive;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
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
@RequestMapping(value="/tptopictheme")
public class TpTopicThemeController  extends BaseController<TpTopicThemeInfo> {
    @Autowired
    private ITpTaskAllotManager tpTaskAllotManager;
    @Autowired
    private ITpTopicThemeManager tpTopicThemeManager;
    @Autowired
    private ITpTopicManager tpTopicManager;
    @Autowired
    private ITpTaskManager tpTaskManager;
    @Autowired
    private ITaskPerformanceManager taskPerformanceManager;
    @Autowired
    private ITpCourseManager tpCourseManager;
    @Autowired
    private ITpOperateManager tpOperateManager;
    @Autowired
    private ITpStuScoreLogsManager tpStuScoreLogsManager;
    /**
     * 执行AJAX 查询
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=themeListjson",method = RequestMethod.POST)
    public void themeListJSON(HttpServletRequest request,HttpServletResponse response) throws Exception{
        TpTopicThemeInfo tpthemeInfo=this.getParameter(request,TpTopicThemeInfo.class);
        PageResult presult=this.getPageResultParameter(request);
        JsonEntity jsonEntity=new JsonEntity();
        if(tpthemeInfo.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
        }
        String clsid=request.getParameter("clsid");
        String typeid=request.getParameter("clstype");
        //排序
        String ordercol=request.getParameter("ordercol");
        String orderdirect=request.getParameter("ordertype");
        if(UtilTool.nullOrEmpty(ordercol)){
            if(UtilTool.nullOrEmpty(orderdirect))
                ordercol+=" "+orderdirect;
            presult.setOrderBy(ordercol);
        }
        if(clsid!=null&&clsid.toString().trim().length()>0){
            tpthemeInfo.setClsid(Integer.parseInt(clsid));
            tpthemeInfo.setClsType(Integer.parseInt(typeid));
        }

        //身份验证
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            //角色 2：代表学生
            tpthemeInfo.setSearchRoleType(2);
        }


        //得到列表
        tpthemeInfo.setLoginuserref(this.logined(request).getRef());
        List<TpTopicThemeInfo> tpTopicThemeList=this.tpTopicThemeManager.getList(tpthemeInfo,presult);

        presult.setList(tpTopicThemeList);
        jsonEntity.setPresult(presult);
        jsonEntity.setType("success");
        response.getWriter().print(jsonEntity.toJSON());
    }


    /**
     * 新版页 进入主帖
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(params ="m=getTopicZT")
    public ModelAndView getTopicZT(HttpServletRequest request,HttpServletResponse response,ModelMap mp) throws Exception{
        TpTopicThemeInfo tpthemeInfo=this.getParameter(request,TpTopicThemeInfo.class);
        PageResult presult=this.getPageResultParameter(request);
        JsonEntity jsonEntity=new JsonEntity();
        if(tpthemeInfo.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsg());
            return null;
        }
        String clsid=request.getParameter("clsid");
        String typeid=request.getParameter("clstype");
        //排序
        String ordercol=request.getParameter("ordercol");
        String orderdirect=request.getParameter("ordertype");
        if(UtilTool.nullOrEmpty(ordercol)){
            if(UtilTool.nullOrEmpty(orderdirect))
                ordercol+=" "+orderdirect;
            presult.setOrderBy(ordercol);
        }
        if(clsid!=null&&clsid.toString().trim().length()>0){
            tpthemeInfo.setClsid(Integer.parseInt(clsid));
            tpthemeInfo.setClsType(Integer.parseInt(typeid));
        }

        //身份验证
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            //角色 2：代表学生
            tpthemeInfo.setSearchRoleType(2);
        }
        //身份验证
        String roleStr="TEACHER";
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            roleStr="STUDENT";
        }
        mp.put("roleStr", roleStr);
        tpthemeInfo.setSelectType(2);//查询内容
        //得到列表
        tpthemeInfo.setLoginuserref(this.logined(request).getRef());
        List<TpTopicThemeInfo> tpTopicThemeList=this.tpTopicThemeManager.getList(tpthemeInfo,presult);
        //列表得到后，
        mp.put("ztTheme",tpTopicThemeList);
        mp.put("presult",presult);
        return new ModelAndView("interactive/topic/topic-tiezi",mp);
    }

    /**
     * 执行添加主题
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doSaveTopicTheme",method=RequestMethod.POST)
    public void doSaveTopicTheme(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicThemeInfo topictheme=this.getParameter(request,TpTopicThemeInfo.class);//得到参数
        //验证参数
        if(topictheme==null||topictheme.getTopicid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //验证标题，内容等参数
        if(!UtilTool.nullOrEmpty(topictheme.getThemetitle())||!UtilTool.nullOrEmpty(topictheme.getThemecontent())){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        //验证TpTopicInfo数据
        TpTopicInfo topicInfo=new TpTopicInfo();
        topicInfo.setTopicid(topictheme.getTopicid());
        topicInfo.setSelectType(2);/*查询类型  1:status<>3   2:不连接被删除的 */
        //查询数据是否存在
        //只得到一条数据
        //得到数据 (只取一条数据)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);

        List<TpTopicInfo> topicList=this.tpTopicManager.getList(topicInfo,presult);
        if(topicList==null||topicList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //添加数据


        Long themenextid=topictheme.getThemeid();
        if(themenextid==null)
            themenextid=this.tpTopicThemeManager.getNextId(true);
        topictheme.setThemeid(themenextid);
        topictheme.setTopicid(topicList.get(0).getTopicid());
        topictheme.setCourseid(topicList.get(0).getCourseid());
        topictheme.setViewcount(0);
        topictheme.setCuserid(this.logined(request).getUserid());
        topictheme.setIsessence(0);     //是否精华
        topictheme.setIstop(0);         //是否置顶
        topictheme.setCloudstatus(-1);  //上传云端 -1:未上传
        //整理执行语句
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.tpTopicThemeManager.getSaveSql(topictheme,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //更新程序
        if(topictheme.getThemecontent()!=null&&topictheme.getThemecontent().trim().length()>0){
            //得到theme_content的更新语句
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , topictheme.getThemecontent(), topictheme.getThemeid().toString(), sqlList, objArrayList);
        }
        if(topictheme.getCommentcontent()!=null&&topictheme.getCommentcontent().trim().length()>0){
            //得到comment_content的更新语句
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                , topictheme.getCommentcontent(), topictheme.getThemeid().toString(),sqlList,objArrayList);
        }
        //操作日志
        sqlbuilder=new StringBuilder();
        objList= this.tpTopicThemeManager.getAddOperateLog(this.logined(request).getRef(),"TP_TOPIC_THEME_INFO",themenextid.toString()
        ,null,null,"ADD","发布新主题:title:"+topictheme.getThemetitle(),sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        if(sqlList!=null&&objArrayList!=null&&sqlList.size()==objArrayList.size()&&sqlList.size()>0){
            if(this.tpTopicThemeManager.doExcetueArrayProc(sqlList,objArrayList)){
                 jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                jsonEntity.getObjList().add(tpTopicThemeManager.getNextId(true));
                jsonEntity.setType("success");

                //获取该学生当前专题下任务
                TpTaskInfo t=new TpTaskInfo();
                t.setCourseid(topicList.get(0).getCourseid());
                t.setUserid(this.logined(request).getUserid());
                List<TpTaskInfo> taskStuList=this.tpTaskManager.getListbyStu(t,null);
                if(taskStuList!=null&&taskStuList.size()>0){
                    //检测当前论题是否发布任务
                    TpTaskInfo task=new TpTaskInfo();
                    PageResult p=new PageResult();
                    p.setPageSize(1);
                    p.setPageNo(1);
                    task.setTaskvalueid(topictheme.getTopicid());
                    task.setCourseid(topicList.get(0).getCourseid());
                    List<TpTaskInfo>taskList=this.tpTaskManager.getTaskReleaseList(task, p);
                    if(taskList!=null&&taskList.size()>0&&taskList.get(0).getTaskstatus()!=null
                            &&!taskList.get(0).getTaskstatus().equals("1")&&!taskList.get(0).getTaskstatus().equals("3")){
                        for(TpTaskInfo tmpTask:taskStuList){
                            if(tmpTask.getTaskid().equals(taskList.get(0).getTaskid())
                                    &&taskList.get(0).getCriteria()!=null&&taskList.get(0).getCriteria()==2){
                                TaskPerformanceInfo tp=new TaskPerformanceInfo();
                                tp.setTaskid(taskList.get(0).getTaskid());
                                tp.setTasktype(taskList.get(0).getTasktype());
                                tp.setCourseid(taskList.get(0).getCourseid());
                                //tp.getTaskinfo().setGroupid(gsList.get(0).getGroupid());
                                tp.setCriteria(2);//发主题
                                tp.setUserid(this.logined(request).getRef());
                                tp.setIsright(1);
                                List<TaskPerformanceInfo>tpList=this.taskPerformanceManager.getList(tp,null);
                                if(tpList==null||tpList.size()<1)
                                    if(this.taskPerformanceManager.doSave(tp)){
                                       ///////////////////////////////奖励加分（发主帖）给提示
                                       //添加奖励积分
                                        TpTaskAllotInfo tallot=new TpTaskAllotInfo();
                                        tallot.setTaskid(taskList.get(0).getTaskid());

                                        tallot.getUserinfo().setUserid(this.logined(request).getUserid());
                                        List<Map<String,Object>> clsMapList=this.tpTaskAllotManager.getClassId(tallot);
                                        if(clsMapList==null||clsMapList.size()<1||clsMapList.get(0)==null||!clsMapList.get(0).containsKey("CLASS_ID")
                                                ||clsMapList.get(0).get("CLASS_ID")==null){
                                            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
                                            response.getWriter().println(jsonEntity.toJSON());return ;
                                        }

                                        //taskinfo:   4:成卷测试  5：自主测试   6:微视频
                                        //规则转换:    6             7         8
                                        Integer type=0;
                                        switch(taskList.get(0).getTasktype()){
                                            case 3:     //试题
                                                type=1;break;
                                            case 1:     //资源学习
                                                type=3;break;
                                            case 2:
                                                type=4;
                                                break;
                                        }
                                    String jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid().toString();
                            //                /*奖励加分通过*/
                                        if(this.tpStuScoreLogsManager.awardStuScore(taskList.get(0).getCourseid()
                                                ,Long.parseLong(clsMapList.get(0).get("CLASS_ID").toString())
                                                ,taskList.get(0).getTaskid()
                                                ,Long.parseLong(this.logined(request).getUserid()+""),jid,type,this.logined(request).getDcschoolid())){
                                            jsonEntity.setMsg("查看并提交论题:恭喜您,获得了1积分和1蓝宝石(没有调用接口)");
                                        }else
                                            System.out.println("awardScore error");


                                        System.out.println("添加互动论题完成|查看记录成功!");
                                    }else
                                        System.out.println("添加互动论题完成|查看记录失败!");
                            }
                        }
                    }
                }

            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * 删除主题(直接删除，主题没有回收站)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doDelTheme",method=RequestMethod.POST)
    public void doDelTheme(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicThemeInfo topicThemeInfo=this.getParameter(request,TpTopicThemeInfo.class);
        if(topicThemeInfo==null||topicThemeInfo.getThemeid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //查询数据是否存在
        //只得到一条数据
        //得到数据 (只取一条数据)
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        TpTopicThemeInfo tmpTopicTheme=new TpTopicThemeInfo();
        tmpTopicTheme.setThemeid(topicThemeInfo.getThemeid());
        tmpTopicTheme.setLoginuserref(this.logined(request).getRef());

        //得到并验证该主题
        List<TpTopicThemeInfo> topicThemeList=this.tpTopicThemeManager.getList(tmpTopicTheme,presult);
        if(topicThemeList==null||topicThemeList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //批量删除，组合
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> paraList=null;
        //删除回复信息

        //删除主题
        sqlbuilder=new StringBuilder();
        paraList=this.tpTopicThemeManager.getDeleteSql(topicThemeInfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paraList);
        }
        //向操作记录表添加记录
        //得到引用专题
        TpCourseInfo tc=new TpCourseInfo();
        tc.setCourseid(topicThemeList.get(0).getCourseid());
        List<TpCourseInfo> tcList=this.tpCourseManager.getList(tc, null);
        if(tcList==null||tcList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //如果是引用专题,则添加操作记录
        if(tcList.get(0).getQuoteid()!=null&&topicThemeList.get(0).getQuoteid()!=null){
	        TpOperateInfo oentity=new TpOperateInfo();
	        oentity.setCourseid(tcList.get(0).getQuoteid());
	        oentity.setDatatype(5);	//专题主题
	        oentity.setOperatetype(1);//删除
	        oentity.setCuserid(this.logined(request).getUserid());
	        oentity.setTargetid(topicThemeList.get(0).getQuoteid());
	        List<TpOperateInfo> tpOpList=this.tpOperateManager.getList(oentity, null);
	        if(tpOpList==null||tpOpList.size()<1){//没有记录,则添加
	        	oentity.setRef(this.tpOperateManager.getNextId(true));
	        	sqlbuilder=new StringBuilder();
	        	paraList=this.tpOperateManager.getSaveSql(oentity, sqlbuilder);
	        	if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
	        		 sqlList.add(sqlbuilder.toString());
	                 objArrayList.add(paraList);
	        	}
	        }
        }
        
        //开始执行
        if(sqlList!=null&&sqlList.size()==objArrayList.size()&&sqlList.size()>0){
            if(this.tpTopicThemeManager.doExcetueArrayProc(sqlList,objArrayList)){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                jsonEntity.setType("success");
            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
           jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        //返回
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * 更新列
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=updateColumnByThemeId",method=RequestMethod.POST)
    public void updateColumnByThemeId(HttpServletRequest request,HttpServletResponse response) throws Exception{
        TpTopicThemeInfo themeInfo=this.getParameter(request,TpTopicThemeInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        if(themeInfo==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //查询数据
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        TpTopicThemeInfo tmpTopicTheme=new TpTopicThemeInfo();
        tmpTopicTheme.setThemeid(themeInfo.getThemeid());
        tmpTopicTheme.setLoginuserref(this.logined(request).getRef());
        List<TpTopicThemeInfo> topicThemeList=this.tpTopicThemeManager.getList(tmpTopicTheme,presult);
        if(topicThemeList==null||topicThemeList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //批量删除，组合
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> paraList=null;

        //开始修改
        paraList=this.tpTopicThemeManager.getUpdateSql(themeInfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paraList);
        }
        sqlbuilder=new StringBuilder();
        //添加日志
        paraList=this.tpTopicThemeManager.getAddOperateLog(this.logined(request).getRef(),"TP_TOPIC_THEME_INFO", themeInfo.getThemeid().toString()
                ,null,null,"UPDATE","通过topictheme?m=updateColumnByThemeId修改数据",sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paraList);
        }
        //开始执行
        if(sqlList!=null&&sqlList.size()==objArrayList.size()&&sqlList.size()>0){
            if(this.tpTopicThemeManager.doExcetueArrayProc(sqlList,objArrayList)){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                jsonEntity.setType("success");
            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        //返回
        response.getWriter().print(jsonEntity.toJSON());
    }

    /**
     * 进入主题详情页面
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=toTopicThemeDetail",method=RequestMethod.GET)
    public ModelAndView toTopicThemeDetail(HttpServletRequest request,HttpServletResponse response ,ModelMap mp)throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        TpTopicThemeInfo topicThemeInfo=this.getParameter(request,TpTopicThemeInfo.class);
        if(topicThemeInfo==null||topicThemeInfo.getThemeid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }
        
        
        
        //得到数据
        //查询数据
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        TpTopicThemeInfo tmpTopicTheme=new TpTopicThemeInfo();
        tmpTopicTheme.setThemeid(topicThemeInfo.getThemeid());
        tmpTopicTheme.setLoginuserref(this.logined(request).getRef());
        tmpTopicTheme.setSelectType(2);     //查询内容
        List<TpTopicThemeInfo> topicThemeList=this.tpTopicThemeManager.getList(tmpTopicTheme,presult);
        if(topicThemeList==null||topicThemeList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.getAlertMsgAndCloseWin());return null;
        }



        
      //身份验证
        String roleStr="TEACHER";
        if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
            roleStr="STUDENT";
        }
        mp.put("roleStr", roleStr);
        mp.put("culoginid", this.logined(request).getUserid());

        //记录到日志中，同时更新记录
        TpTopicThemeInfo themeEntity=topicThemeList.get(0);

        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.tpTopicThemeManager.getUpdateNumAdd("tp_topic_theme_info","theme_id","view_count",themeEntity.getThemeid().toString(),1,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //查询是否已经有记录

        if(themeEntity.getIsread()<1){
            sqlbuilder=new StringBuilder();
            objList= this.tpTopicThemeManager.getAddOperateLog(this.logined(request).getRef(),"tp_topic_theme_info",themeEntity.getThemeid().toString()
                    ,"",themeEntity.getThemeid().toString(),"VIEW",this.logined(request).getRealname()+"查看了主题"+themeEntity.getThemeid(),sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }
        //更新
        if(sqlArrayList.size()!=0&&sqlArrayList.size()== objArrayList.size()){
            if(!this.tpTopicThemeManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
               jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
               response.getWriter().print(jsonEntity.getAlertMsgAndBack());return null;
            }
        }
        topicThemeList.get(0).setViewcount(topicThemeList.get(0).getViewcount()+1);
        //存储数据
        mp.put("theme",topicThemeList.get(0));
        return new ModelAndView("/interactive/theme/theme-detail",mp);
    }

    /**
     * 修改主题
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=doUpdateTheme",method=RequestMethod.POST)
    public void doUpdateTheme(HttpServletRequest request,HttpServletResponse response) throws Exception{
        TpTopicThemeInfo themeInfo=this.getParameter(request,TpTopicThemeInfo.class);
        JsonEntity jsonEntity=new JsonEntity();
        if(themeInfo==null||themeInfo.getThemeid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }

        //验证要修改的数据是否存在
        //查询数据
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        presult.setPageNo(1);
        TpTopicThemeInfo tmpTopicTheme=new TpTopicThemeInfo();
        tmpTopicTheme.setThemeid(themeInfo.getThemeid());
        tmpTopicTheme.setLoginuserref(this.logined(request).getRef());      //当前用户是否已经读过
       // tmpTopicTheme.setSelectType(2);
        List<TpTopicThemeInfo> topicThemeList=this.tpTopicThemeManager.getList(tmpTopicTheme,presult);
        if(topicThemeList==null||topicThemeList.size()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        //执行修改
        // 批量删除，组合
        List<String> sqlList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> paraList=null;

        //开始修改
        paraList=this.tpTopicThemeManager.getUpdateSql(themeInfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paraList);
        }

        //更新程序
        if(themeInfo.getThemecontent()!=null&&themeInfo.getThemecontent().trim().length()>0){
            //先清空
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , "", themeInfo.getThemeid().toString(), sqlList, objArrayList);
            //得到theme_content的更新语句
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
                    , themeInfo.getThemecontent(), themeInfo.getThemeid().toString(), sqlList, objArrayList);
        }
        if(themeInfo.getCommentcontent()!=null&&themeInfo.getCommentcontent().trim().length()>0){
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                    ,"", themeInfo.getThemeid().toString(),sqlList,objArrayList);
            //得到comment_content的更新语句
            this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
                    , themeInfo.getCommentcontent(), themeInfo.getThemeid().toString(),sqlList,objArrayList);
        }

        sqlbuilder=new StringBuilder();
        //添加日志
        String remark="通过topictheme?m=doUpdateTheme修改数据";
        if(themeInfo.getThemetitle()!=null)
            remark+="，修改了主题标题!";
        if(themeInfo.getThemecontent()!=null)
            remark+=",修改了主题内容!";
        if(themeInfo.getCommenttitle()!=null)
            remark+=",批注了主题标题!";
        if(themeInfo.getCommentcontent()!=null)
            remark+=",批注了主题内容!";
        paraList=this.tpTopicThemeManager.getAddOperateLog(this.logined(request).getRef(),"TP_TOPIC_THEME_INFO", themeInfo.getThemeid().toString()
                ,null,null,"UPDATE",remark,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlList.add(sqlbuilder.toString());
            objArrayList.add(paraList);
        }
        //开始执行
        if(sqlList!=null&&sqlList.size()==objArrayList.size()&&sqlList.size()>0){
            if(this.tpTopicThemeManager.doExcetueArrayProc(sqlList,objArrayList)){
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                jsonEntity.setType("success");
            }else
                jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        //返回
        response.getWriter().print(jsonEntity.toJSON());
    }
    /**
     * 教师操作，将学生的
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=addOrDelQuoteTheme",method=RequestMethod.POST)
    public void addOrDelQuoteTheme(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	JsonEntity jeEntity=new JsonEntity();
    	TpTopicThemeInfo entity=this.getParameter(request, TpTopicThemeInfo.class);
    	if(entity.getThemeid()==null){
    		 jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
             response.getWriter().print(jeEntity.toJSON());return;
    	}
    	String type=request.getParameter("type"); //1:可见，2：不可见
    	//验证Topicid是否存在，QuoteId是否存在
//    	TpTopicInfo tpc=new TpTopicInfo();
//    	tpc.setTopicid(entity.getTopicid());
//    	List<TpTopicInfo> tpcList=this.tpTopicManager.getList(tpc, null);
//    	if(tpcList==null||tpcList.size()<1){
//    		jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
//            response.getWriter().print(jeEntity.toJSON());return;
//    	}
    	//得到当前的TPICID对象
//    	TpTopicInfo tpcEntity=tpcList.get(0);
    	//验证引用的THEME entity
    	TpTopicThemeInfo theEntity=new TpTopicThemeInfo();
    	theEntity.setThemeid(entity.getThemeid());
    	List<TpTopicThemeInfo> theList=this.tpTopicThemeManager.getList(theEntity, null);
    	if(theList==null||theList.size()<1){
    		jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());return;
    	}
        //验证上一层是否存在
        Long topicid=theList.get(0).getTopicid();
        TpTopicInfo tpc=new TpTopicInfo();
    	tpc.setTopicid(topicid);
    	List<TpTopicInfo> tpcList=this.tpTopicManager.getList(tpc, null);
    	if(tpcList==null||tpcList.size()<1){
    		jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());return;
    	}

    	//得到引用的数据themeid
    	TpTopicThemeInfo quoteEntity=theList.get(0);
    	//组织数据，进行批量操作
    	List<String> sqlArrayList=new ArrayList<String>();
    	List<List<Object>> objArrayList=new ArrayList<List<Object>>();
    	List<Object> objList=null;
    	StringBuilder sqlbuilder=null;
    	//查询是否存在了相关引用数据(每个主题只能被引用一次)
    	theEntity=new TpTopicThemeInfo();
    	theEntity.setQuoteid(quoteEntity.getQuoteid());
    	theEntity.setCourseid(quoteEntity.getCourseid());
    	List<TpTopicThemeInfo> entityList=this.tpTopicThemeManager.getList(theEntity, null);
    	//记录引用的Course_id
    	Long quoteCourseid=quoteEntity.getCourseid();
    	theEntity=theList.get(0);
        theEntity.setCuserid(this.logined(request).getUserid());
    	theEntity.setStatus(Long.parseLong(type));
    	sqlbuilder=new StringBuilder();
    	objList=this.tpTopicThemeManager.getUpdateSql(theEntity, sqlbuilder);
    	if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
    		sqlArrayList.add(sqlbuilder.toString());
    		objArrayList.add(objList);
    	}
//    		if(theEntity.getThemecontent()!=null){
//    			  this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
//                          , "", quoteEntity.getThemeid().toString(),sqlArrayList,objArrayList);
//
//             	  //得到theme_content的更新语句
//                 this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "theme_content"
//                         , theEntity.getThemecontent(), theEntity.getThemeid().toString(),sqlArrayList,objArrayList);
//      		}
//      		if(theEntity.getCommentcontent()!=null){
//      		     this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
//                         , "", theEntity.getThemeid().toString(),sqlArrayList,objArrayList);
//
//      			//得到comment_content的更新语句
//                 this.tpTopicThemeManager.getArrayUpdateLongText("tp_topic_theme_info", "theme_id", "comment_content"
//                         , theEntity.getCommentcontent(), theEntity.getThemeid().toString(),sqlArrayList,objArrayList);
//      		}

    	
    	TpOperateInfo opEntity=new TpOperateInfo();
    	opEntity.setCourseid(quoteCourseid);
    	opEntity.setDatatype(5);//专题主题
    	opEntity.setTargetid(entity.getQuoteid());
    	opEntity.setOperatetype(2);
    	opEntity.setCuserid(this.logined(request).getUserid());
    	//查询是否存在
    	List<TpOperateInfo> tpOperateEntity=this.tpOperateManager.getList(opEntity, null);
    	boolean ishasOperate=(tpOperateEntity!=null&&tpOperateEntity.size()>0);
    	//添加操作记录
    	if(Integer.parseInt(type)==1){  //1:可见，则添加记录   2:不可见，则删除记录
    		//可见的情况下，不存在数据，则添加
    		if(!ishasOperate){
    			opEntity.setRef(this.tpOperateManager.getNextId(true));
    			sqlbuilder=new StringBuilder();
        		objList=this.tpOperateManager.getSaveSql(opEntity, sqlbuilder);
        		if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
        			sqlArrayList.add(sqlbuilder.toString());
        			objArrayList.add(objList);
        		}
    		}
    	}else
        if(Integer.parseInt(type)==2){
    		//不可见的情况下，存在数据，则删除
    		if(ishasOperate){
    			sqlbuilder=new StringBuilder();
        		objList=this.tpOperateManager.getDeleteSql(opEntity, sqlbuilder);
        		if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
        			sqlArrayList.add(sqlbuilder.toString());
        			objArrayList.add(objList);
        		}
        		
    		}
    	}
    	//执行批量操作
    	if(this.tpTopicThemeManager.doExcetueArrayProc(sqlArrayList, objArrayList)){
    		jeEntity.setType("success");
    		jeEntity.setMsg("操作成功!");
    	}else{
    		jeEntity.setMsg("操作失败，原因：批量操作失败!");
    	}
    	response.getWriter().print(jeEntity.toJSON());    	
    }
    
    /**
     * 赞与取消，
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=addOrCannelPariseTheme",method=RequestMethod.POST)
    public void addOrCannelPariseTheme(HttpServletRequest request,HttpServletResponse response) throws Exception{
    	TpTopicThemeInfo entity=this.getParameter(request, TpTopicThemeInfo.class);
    	
    	JsonEntity jeEntity=new JsonEntity();     	
    	if(entity.getThemeid()==null){
    		jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());return;        
    	}
    	String type=request.getParameter("type");
    	if(type==null)
    		type="1";
    	List<TpTopicThemeInfo> themeList=this.tpTopicThemeManager.getList(entity, null);
    	if(themeList==null||themeList.size()<1){
    		jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
    	List<List<Object>> objArrayList=new ArrayList<List<Object>>();
    	List<String> sqlArrayList=new ArrayList<String>();
    	List<Object> objList=null;
    	StringBuilder sqlbuilder=new StringBuilder();
    	
    	
    	if(Integer.parseInt(type)==1){//赞
    		sqlbuilder=new StringBuilder();
    		//修改数据
    		objList=this.tpTopicThemeManager.getUpdateNumAdd("tp_topic_theme_info", "theme_id", "praise_count"
    				, entity.getThemeid().toString(), 1, sqlbuilder);
    		 if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
    			 sqlArrayList.add(sqlbuilder.toString());
    	         objArrayList.add(objList);
    	     }
    		 sqlbuilder=new StringBuilder();
    		 objList=this.tpTopicThemeManager.getAddOperateLog(this.logined(request).getRef(),
    	    			"tp_topic_theme_info", entity.getThemeid().toString(), "", entity.getThemeid().toString()
    	    			, "PARISE", this.logined(request).getRealname()+"对"+entity.getThemeid()+"点了赞", sqlbuilder);
    	    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
    				 sqlArrayList.add(sqlbuilder.toString());
    		         objArrayList.add(objList);
    		   }
    		 
    	}else if(Integer.parseInt(type)==2){
    		sqlbuilder=new StringBuilder();
    		//修改数据
    		objList=this.tpTopicThemeManager.getUpdateNumAdd("tp_topic_theme_info", "theme_id", "praise_count"
    				, entity.getThemeid().toString(), 2, sqlbuilder);
    		 if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
    			 sqlArrayList.add(sqlbuilder.toString());
    	         objArrayList.add(objList);
    	     }
    		 
    		 sqlbuilder=new StringBuilder();
    		 objList=this.tpTopicManager.getDelOperateLog(this.logined(request).getRef(), "tp_topic_theme_info", entity.getThemeid().toString(), "PARISE", sqlbuilder);
    		 if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
    			 sqlArrayList.add(sqlbuilder.toString());
    	         objArrayList.add(objList);
    	     }    		 
    	}    	
    	 //开始执行
        if(sqlArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
            if(this.tpTopicThemeManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
            	jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            	jeEntity.setType("success");
            }else
            	jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }else
        	jeEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
      
         themeList=this.tpTopicThemeManager.getList(entity, null);
    	if(themeList==null||themeList.size()<1){
    		jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
    	//重新得到数量
        jeEntity.getObjList().add(themeList.get(0).getPraisecount());
        
        //返回
        response.getWriter().print(jeEntity.toJSON());
    	
    }
    
    
}
