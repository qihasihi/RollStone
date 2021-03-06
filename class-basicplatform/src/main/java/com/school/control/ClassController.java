package com.school.control;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.entity.ClassInfo;
import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.util.*;
import com.school.utils.*;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value="/cls")
public class ClassController extends BaseController<ClassInfo>{
    //记录Log4J
    private Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private IClassManager classManager;
    @Autowired
    private IClassYearManager classYearManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private ISubjectManager subjectManager;
    @Autowired
    private ITermManager termManager;
    @Autowired
    private IClassUserManager classUserManager;
    @Autowired
    private IOperateExcelManager operaterexcelmanager;
    @Autowired
    private IUserManager userManager;


    @RequestMapping(params="m=list",method=RequestMethod.GET)
    public ModelAndView toClassList(HttpServletRequest request,ModelMap mp )throws Exception{
        JsonEntity jsonEntity=new JsonEntity();
        PageResult p = new PageResult();

        List<ClassYearInfo> classyearList=null;
        //得到当前学年
        TermInfo crnTerm=this.termManager.getAutoTerm();
        if(crnTerm==null||crnTerm.getYear()==null){
            //jsonEntity.setMsg("当前时间不属于任何学年!");
            classyearList=this.classYearManager.getList(null, p);
        }else{
            ClassYearInfo cy=new ClassYearInfo();
            cy.setDyEqClassyearvalue(crnTerm.getYear());
            classyearList=this.classYearManager.getList(cy,p);
        }
        mp.put("classyearList", classyearList);

        p.setOrderBy("c.e_time desc");
        List<GradeInfo>gradeList=this.gradeManager.getList(null, null);
        mp.put("gradeList", gradeList);

        //得到全部学科
        List<SubjectInfo> subList=this.subjectManager.getList(null, null);
        request.setAttribute("subList", subList);
        //得到当前年份
        TermInfo currentTm=this.termManager.getAutoTerm();
        mp.put("currentYear", currentTm.getYear());
        //得到allowAutoLevel
        int allowAutoLevel=0;


        //验证该是否在条件内执行
        //得到下一个学年
        TermInfo tm=new TermInfo();
        Long t=UtilTool.StringConvertToDate(Calendar.getInstance().get(Calendar.YEAR) + "-09-01", UtilTool.DateType.smollDATE).getTime();
        if(new Date().getTime()<t){
            tm.setDYYear(currentTm.getYear());
        }else
            tm.setYear(currentTm.getYear());
        PageResult presult=new PageResult();
        presult.setOrderBy(" u.YEAR ASC ");
        presult.setPageSize(1);

        List<TermInfo> tmList=this.termManager.getList(tm, presult);
        if(tmList!=null&&tmList.size()>0){
            String nextyear=currentTm.getYear();
            //得到当前nextyear下所有的行政班
            presult=new PageResult();
            presult.setPageSize(1);
            ClassInfo clsentity=new ClassInfo();
            clsentity.setYear(nextyear);
            clsentity.setPattern("行政班");
            clsentity.setDcschoolid(this.logined(request).getDcschoolid());
            List<ClassInfo> nextClsList=this.classManager.getList(clsentity, presult);
            if(nextClsList==null||nextClsList.size()<1){
                allowAutoLevel=1;
            }
            if (allowAutoLevel == 1) {
                int count = 0;
                int pre = 0;
                int num = 0;
                int [] sYear = new int[5];
                String transYear = nextyear+"#";
                for (int i = 0; i < transYear.length(); i++) {
                    if(transYear.charAt(i) < 48 || transYear.charAt(i) > 57) {
                        if(i > pre) {
                            sYear[num] = Integer.valueOf(transYear.substring(pre, i)) - 1;
                            num++;
                        }
                        pre = i + 1;
                    }
                }
                transYear = sYear[0] +"~"+ sYear[1];
                clsentity.setYear(transYear);
                List<ClassInfo> maxClsList=this.classManager.getList(clsentity, null);
                for (ClassInfo cls:maxClsList) {
                    if(cls.getClassgrade() != null && !cls.getClassgrade().equals("高三")  && !cls.getClassgrade().equals("初三")) {
                        count ++;
                    }
                }
                if(count == 0)
                    allowAutoLevel = 0;
            }
        }

        //增加获取期次列表并放入session
        List<ClassInfo> termList = this.classManager.getAllTerm();
        request.setAttribute("clsTmList",termList);

        request.setAttribute("allowAutoLevel", allowAutoLevel);
        return new ModelAndView("/class/list");
    }

    /**
     * 班级成员详情
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=detail",method=RequestMethod.GET)
    public ModelAndView toClassUserDetail(HttpServletRequest request,HttpServletResponse response,ModelAndView mp )throws Exception{
        String classid=request.getParameter("classid");
        JsonEntity jeEntity=new JsonEntity();
        if(classid==null||classid.trim().length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
        }
        //验证班级
        ClassInfo clsInfo=new ClassInfo();
        clsInfo.setClassid(Integer.parseInt(classid.trim()));
        List<ClassInfo> clsList=this.classManager.getList(clsInfo, null);
        if(clsList==null||clsList.size()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());return null;
        }
        //PageResult p = new PageResult();
        ClassUser cu=new ClassUser();
        cu.setRelationtype("学生");
        if(classid!=null&&classid.trim().length()>0)
            cu.setClassid(Integer.parseInt(classid));
        //只显示启用的学生帐号，不显示禁用的学生
        cu.getUserinfo().setStateid(0);
        List<ClassUser>stuList=this.classUserManager.getList(cu,null);
        //得到全部学科
        List<SubjectInfo> subList=this.subjectManager.getList(null, null);
        request.setAttribute("subList", subList);
        //得到全部年级
        List<GradeInfo> gradeList=this.gradeManager.getList(null, null);
        request.setAttribute("gdList", gradeList);

        request.setAttribute("stuList", stuList);
        request.setAttribute("clsinfo", clsList.get(0));


        return new ModelAndView("/class/detail/detail");
    }

    /**
     * 根据班级ID得到班级人员
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=getClsUserByClsId",method=RequestMethod.POST)
    public void getClsUserByClsId(HttpServletRequest request,HttpServletResponse response) throws Exception{
        PageResult presult=this.getPageResultParameter(request);
        String clsid=request.getParameter("classid");
        // String relationType=request.getParameter("relationtype");
        JsonEntity jsonEntity=new JsonEntity();
        if(clsid==null||clsid.trim().length()<1){//||relationType==null||relationType.trim().length()<1){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());return;
        }
        ClassUser cu=new ClassUser();
        cu.setRelationtype("学生");
        cu.setClassid(Integer.parseInt(clsid));
        //只显示启用的学生帐号，不显示禁用的学生
        cu.getUserinfo().setStateid(0);
        List<ClassUser> stuList=this.classUserManager.getList(cu,presult);
        presult.getList().add(stuList);
        presult.getList().add(clsid);
        jsonEntity.setType("success");
        jsonEntity.setPresult(presult);
        response.getWriter().print(jsonEntity.toJSON());
    }

    @RequestMapping(params="m=exportClsExcel",method=RequestMethod.GET)
    public String exportClsExcel(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String classid=request.getParameter("classid");
        if(classid==null||classid.length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return null;
        }
        String msg=exportStudent(response, classid);
        if(msg!=null&&msg.length()>0){
            je.setMsg(msg);
            response.getWriter().print(je.getAlertMsgAndBack());
            return null;
        }
        return null;
    }

    public String exportStudent(HttpServletResponse response,String classid) throws Exception{
        List<List<String>> dataList = new ArrayList<List<String>>();
        List<String> columns = new ArrayList<String>();
        columns.add("学号");
        columns.add("姓名");
        columns.add("性别");
        //columns.add("年份");

        String filename = null;
        String title = null;
        ClassUser cu=new ClassUser();
        cu.setRelationtype("学生");
        cu.setClassid(Integer.parseInt(classid));
        cu.getUserinfo().setStateid(0);//不查询禁用的学生信息
        List<ClassUser>clsUserList=this.classUserManager.getList(cu, null);
        if(clsUserList==null||clsUserList.size()<1){
            return "数据为空!";
        }
        for (ClassUser clsu : clsUserList) {
            List<String> tempList = new ArrayList<String>();
            tempList.add(clsu.getStuno());
            tempList.add(clsu.getUserinfo().getRealname());
            tempList.add(clsu.getUserinfo().getSex());
            //	tempList.add(clsu.getClassgrade()+clsu.getClassname());
            //	tempList.add(clsu.getYear());
            dataList.add(tempList);
        }
        filename=title=clsUserList.get(0).getYear()+clsUserList.get(0).getClassgrade()+clsUserList.get(0).getClassname()+"学生名单";
        return this.operaterexcelmanager.ExplortExcel(response,filename, columns,
                dataList, title, String.class, null);
    }

    /**
     * 获取List
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxlist",method=RequestMethod.POST)
    public void AjaxGetList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
        String type=request.getParameter("dtype");
        String flag=request.getParameter("flag");
        String subjectStr=request.getParameter("subject");
        if(type!=null&&type.trim().length()>0){
            classinfo.setType(type);
        }
        if(subjectStr!=null&&subjectStr.trim().length()>0){
            classinfo.setSubjectstr(subjectStr);
        }
        PageResult pageresult = this.getPageResultParameter(request);
        pageresult.setOrderBy(" u.c_time desc ");//排序，启用，在前，禁用在后
        if(flag!=null&&flag.length()>0)
            pageresult=null;
        classinfo.setDcschoolid(this.logined(request).getDcschoolid());
        List<ClassInfo> classList =classManager.getList(classinfo, pageresult);
        if(pageresult!=null){
            pageresult.setList(classList);
            je.setPresult(pageresult);
        }else
            je.setObjList(classList);
        je.setType("success");

        response.getWriter().print(je.toJSON());
    }
    /**
     * 根据条件导出班级模板。
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=explortClsTemplate",method=RequestMethod.POST)
    public void explortClsTemplate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
        String type=request.getParameter("dtype");
        if(type!=null&&type.trim().length()>0){
            classinfo.setType(type);
        }
        if(classinfo.getClassgrade()==null||classinfo.getClassgrade().trim().length()<1)
            classinfo.setClassgrade(null);

        if(classinfo.getPattern()==null||classinfo.getPattern().trim().length()<1)
            classinfo.setPattern(null);

        if(classinfo.getYear()==null||classinfo.getYear().trim().length()<1)
            classinfo.setYear(null);

        if(classinfo.getClassname()==null||classinfo.getClassname().trim().length()<1)
            classinfo.setClassname(null);

        PageResult pageresult = this.getPageResultParameter(request);
        pageresult.setOrderBy(" u.isflag asc ");//排序，启用，在前，禁用在后
        List<ClassInfo> classList =classManager.getList(classinfo, pageresult);

        JsonEntity jEntity=new JsonEntity();
        if(classList==null||classList.size()<1){
            jEntity.setMsg("无法继续导出班级模块，原因：根据相关条件，未查找到对应班级!");
            response.getWriter().print(jEntity.getAlertMsgAndCloseWin());return;
        }
        //开始组织数据
        List<String> sheetNameList=new ArrayList<String>();
        List<List<String>> columnsList=new ArrayList<List<String>>();
        List datalist=new ArrayList();
        List<String> titleList=new ArrayList<String>();
        List<Class<? extends Object>> entityClsList=new ArrayList<Class<? extends Object>>();
        List<String> explortObjList=new ArrayList<String>();
        for (int i=0;i<classList.size();i++) {
            if(i>=4)break;       //只显示4条
            ClassInfo clstmp = classList.get(i);
            if(clstmp!=null){
                //sheet
                sheetNameList.add(clstmp.getClassgrade()+clstmp.getClassname());
                //column
                List<String> coltmpList=new ArrayList<String>();
                coltmpList.add("学号");
                coltmpList.add("姓名");
                coltmpList.add("性别");
                columnsList.add(coltmpList);
                //title
                String title=clstmp.getYear()+" "+clstmp.getClassgrade()+"("+clstmp.getClassname()+")";
                titleList.add(title);
                //datalist   (空)
                List<List<String>> dataArrayList=new ArrayList<List<String>>();
                datalist.add(dataArrayList);
                //entityCls
                entityClsList.add(null);
                //explortObjList
                explortObjList.add(null);
            }
        }

       String stemp="学年班级学生导入模板";
        String filename=classList.get(0).getYear()+stemp;
        //导出
        this.operaterexcelmanager.ExplortExcel(response, filename,sheetNameList,columnsList
                ,datalist,titleList,entityClsList,explortObjList);
    }


    /**
     * 去修改
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=toupd",method=RequestMethod.POST)
    public void toUpdateClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
        if(classinfo.getClassid()==null){
            je.setMsg(UtilTool.msgproperty.getProperty("CLASS_CLASSID_EMPTY"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<ClassInfo> classList = classManager.getList(classinfo, null);
        je.setObjList(classList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 添加
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="m=ajaxsave",method=RequestMethod.POST)
    @Transactional
    public void doSubmitAddClassyear(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();

        ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
        String type=request.getParameter("dtype");
        String year=request.getParameter("dyear");
        String pattern=request.getParameter("dpattern");
        if(classinfo.getClassgrade()==null||
                classinfo.getClassgrade().trim().length()<1||
                classinfo.getClassname()==null||
                classinfo.getClassname().length()<1||type==null||year==null||pattern==null){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        classinfo.setYear(year);
        classinfo.setIslike(1);
        //已存在当前数据 无法添加
        List<ClassInfo>classList=this.classManager.getList(classinfo, null);
        if(classList!=null&&classList.size()>0){
            je.setMsg(UtilTool.msgproperty.getProperty("DATA_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        classinfo.setPattern(pattern);
        classinfo.setType(type);
        classinfo.setIsflag(1);
        if(classManager.doSave(classinfo)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            ClassInfo entity=this.getParameter(request, ClassInfo.class);
            entity.setYear(year);
            entity.setIslike(1);
            classList =this.classManager.getList(entity,null);
            if(classList!=null&&classList.size()>0){
                  if(!EttInterfaceUserUtil.addClassBase(classList.get(0))){
                      System.out.println("同步添加班级失败!");
                      je.setType("error");
                      je.setMsg("同步添加班级失败!");
                      transactionRollback();
                  }else
                      System.out.println("同步添加班级成功!");
            }
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }



    /**
     * 修改
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="m=modify",method=RequestMethod.POST)
    @Transactional
    public void doSubmitUpdateRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
        if(classinfo.getClassid()==null
            //||classinfo.getYear()==null
                ){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        ClassInfo clsInfo=new ClassInfo();
        clsInfo.setClassid(classinfo.getClassid());
        List<ClassInfo> clsList=this.classManager.getList(clsInfo, null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(je.toJSON());return;
        }
        ClassInfo clsEntity=clsList.get(0);
        if(classinfo.getClassgrade()!=null&&!classinfo.getClassgrade().equals(clsEntity.getClassgrade()))
            clsEntity.setClassgrade(classinfo.getClassgrade());
        if(classinfo.getClassname()!=null&&!classinfo.getClassname().equals(clsEntity.getClassname()))
            clsEntity.setClassname(classinfo.getClassname());
        if(classinfo.getType()!=null&&!classinfo.getType().equals(clsEntity.getType()))
            clsEntity.setType(classinfo.getType());
        if(classinfo.getPattern()!=null&&!classinfo.getPattern().equals(clsEntity.getPattern()))
            clsEntity.setPattern(classinfo.getPattern());
        if(classinfo.getPattern()!=null&&classinfo.getPattern().equals("分层班")
                &&classinfo.getSubjectid()!=null&&classinfo.getSubjectid()!=clsEntity.getSubjectid())
            clsEntity.setSubjectid(classinfo.getSubjectid());
        else if(classinfo.getPattern()!=null&&classinfo.getPattern().equals("行政班"))
            clsEntity.setSubjectid(-999);
        if(classinfo.getYear()!=null&&!classinfo.getYear().equals(clsEntity.getYear()))
            clsEntity.setYear(classinfo.getYear());
        if(classinfo.getIsflag()!=null&&classinfo.getIsflag()!=clsEntity.getIsflag())
            clsEntity.setIsflag(classinfo.getIsflag());
        if(classinfo.getTermid()!=null&&classinfo.getTermid()!=clsEntity.getTermid())
            clsEntity.setTermid(classinfo.getTermid());
        if(classinfo.getActivitytype()!=null&&classinfo.getActivitytype()!=clsEntity.getActivitytype())
            clsEntity.setActivitytype(classinfo.getActivitytype());

        if(classManager.doUpdate(clsEntity)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            //修改
            ClassInfo entity=new ClassInfo();
            entity.setClassid(classinfo.getClassid());
            clsList=this.classManager.getList(clsInfo, null);
            if(clsList!=null&&clsList.size()>0){
                if(!EttInterfaceUserUtil.updateClassBase(clsList.get(0))){
                    System.out.println("修改班级，同步数据失败!");
                    je.setType("error");
                    je.setMsg("同步数据失败!");
                    transactionRollback();
                }else{
                    System.out.println("修改班级，同步数据成功!");
                }
            }
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 删除
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="m=del",method=RequestMethod.POST)
    @Transactional
    public void doDeleteRole(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je = new JsonEntity();
        ClassInfo classinfo = this.getParameter(request, ClassInfo.class);
        if(classinfo.getClassid()==null){
            je.setMsg(UtilTool.msgproperty.getProperty("CLASS_CLASSID_EMPTY"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //跟据ID得到这个班级现在的学生人数
        List<ClassInfo> clsList=this.classManager.getList(classinfo, null);
        if(clsList==null||clsList.size()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(je.toJSON());
            return;
        }
        //验证： 当班级人数为0时，删除启用，可以删除本班级。
//		if(clsList.get(0).getNum()!=null&&UtilTool.isNumber(clsList.get(0).getNum().toString())
//				&&Integer.parseInt(clsList.get(0).getNum().toString())>0){
//			je.setMsg("异常错误，该班级还存在学生，请清空学生后再删除班级!");
//			response.getWriter().print(je.toJSON());return;
//		}
        if(classManager.doDelete(classinfo)){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            //同步删除班级以及人员至ETT 先删除该班级下的人员

            if(delEttClassUser(classinfo.getClassid(), clsList.get(0).getDcschoolid(), true)){
                System.out.println("班级人员删除同步至网校信息成功!");
                if(!EttInterfaceUserUtil.delClassBase(clsList.get(0))){
                    System.out.println("班级删除同步至网校信息失败!");
                    je.setType("error");
                    je.setMsg("班级删除同步至网校信息失败!");
                    transactionRollback();
                }else
                    System.out.println("班级删除同步至网校信息成功!");
            }else{
                System.out.println("班级人员删除同步至网校信息失败!");
                je.setType("error");
                je.setMsg("班级人员删除同步至网校信息失败!");
                transactionRollback();
            }

        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 进入COPY班级学生
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=toCopyClsStudent",method=RequestMethod.POST)
    public void toCopyClassStudent(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String clsid=request.getParameter("classid");
        JsonEntity jeEntity=new JsonEntity();
        if(clsid==null||clsid.toString().trim().length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
        ClassInfo clsEntity=new ClassInfo();
        clsEntity.setClassid(Integer.parseInt(clsid.toString().trim()));
        List<ClassInfo> classList=this.classManager.getList(clsEntity, null);
        if(classList==null||classList.size()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }

        //如果目的班级是行政班，可选班级为上一学年的行政班级

        //灰色名字为已经分配到了与“目的班级”同类型的其它班级中，
        //确定时提示，如果继续操作，从其它班级删除，调到此班。
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(10000);
        presult.setOrderBy(" u.class_id asc ");

        clsEntity=classList.get(0);

        List<ClassInfo> tmpClsList=null;
        if(clsEntity.getPattern().trim().equals("行政班")){
            ClassYearInfo cyEntity=new ClassYearInfo();
            cyEntity.setClassyearvalue(clsEntity.getYear());
            List<Map<String,Object>> objMapList=this.classYearManager.getClassYearPree(cyEntity);
            if(objMapList!=null&&objMapList.size()>0){
                //得到上一年份
                String cyValue=objMapList.get(0).get("CLASS_YEAR_VALUE").toString();

                ClassInfo tmpClsEntity=new ClassInfo();
                tmpClsEntity.setYear(cyValue);
                tmpClsEntity.setPattern("行政班");
                tmpClsEntity.setClassgrade(clsEntity.getClassgrade());
                //tmpClsEntity.setSubjectid(clsEntity.getSubjectid());
                tmpClsEntity.setDcschoolid(this.logined(request).getDcschoolid());
                tmpClsList=this.classManager.getList(tmpClsEntity, presult);
            }
        }else if(clsEntity.getPattern().trim().equals("分层班")){
            //如果目的班级是分层班，可选班级为本学年的行政班
            ClassInfo tmpEntity=new ClassInfo();
            tmpEntity.setYear(clsEntity.getYear());
            tmpEntity.setPattern("行政班");
            tmpEntity.setClassgrade(clsEntity.getClassgrade());
            tmpEntity.setDcschoolid(this.logined(request).getDcschoolid());
            tmpClsList=this.classManager.getList(tmpEntity, presult);
        }
        jeEntity.setObjList(tmpClsList);
        jeEntity.setType("success");
        response.getWriter().print(jeEntity.toJSON());
    }
    /**
     * 得到调班学生及情况
     * @param request
     * @param response
     */
    @RequestMapping(params="m=tiaobancu",method=RequestMethod.POST)
    public void getTiaoBanClassUser(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        ClassInfo clsInfo=this.getParameter(request, ClassInfo.class);
//		if(clsInfo==null||clsInfo.getClassid()==null){
//			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
//			response.getWriter().print(jeEntity.toJSON());return;
//		}
        ClassInfo tmpSearchCls=new ClassInfo();
        tmpSearchCls.setClassid(clsInfo.getClassid());
        List<ClassInfo> clsList=this.classManager.getList(tmpSearchCls, null);
        if(clsList==null||clsList.size()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
        //得到学生
        ClassInfo tmpCls=clsList.get(0);
        List<Map<String,Object>> objMapList=this.classUserManager.getClassUserWithTiaoban(clsInfo.getPattern(), clsInfo.getYear(),clsInfo.getClassid().toString());
        jeEntity.setObjList(objMapList);
        jeEntity.setType("success");
        response.getWriter().print(jeEntity.toJSON());
    }

    /**
     * 班级详情页，调班执行
     * @param request
     * @param response
     * @throws Exception
     */
    //cls?m=doAddClsUserDetail
    @RequestMapping(params="m=doAddClsUserDetail",method=RequestMethod.POST)
    public void doAddClsUserDetail(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        ClassInfo clsInfo=this.getParameter(request, ClassInfo.class);
        if(clsInfo==null||clsInfo.getClassid()==null){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
        String uid=request.getParameter("uid");
        if(uid==null||uid.trim().length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());return;
        }

        //验证班级是否存在
        ClassInfo clsEntity=new ClassInfo();
        clsEntity.setClassid(clsInfo.getClassid());
        List<ClassInfo> clsList=this.classManager.getList(clsEntity, null);
        if(clsList==null||clsList.size()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("ENTITY_NOT_EXISTS"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
        clsEntity=clsList.get(0);

        //存储相关联的班级信息（用处：向ETT发送人员信息）
        List<Integer> allowClssList=new ArrayList<Integer>();
        allowClssList.add(clsEntity.getClassid());
        //开始添加数据
        String[] uidArray=uid.split(",");
        StringBuilder sqlbuilder=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        for (String tmpuid : uidArray) {
            if(tmpuid==null||tmpuid.trim().length()<1)continue;
            //验证用户是否存在
            UserInfo utmp=new UserInfo();
            utmp.setRef(tmpuid.trim());
            List<UserInfo> uList=this.userManager.getList(utmp, null);
            if(uList==null||uList.size()<1){
                jeEntity.setMsg("错误，学生ID:"+tmpuid.trim()+" 没有发现该学生!");
                response.getWriter().print(jeEntity.toJSON());
                return;
            }
            //UserInfo currentUsTmp=uList.get(0);
            //查找用户是否存在于同年份，同类型，同学科的存在,并删除
            ClassUser cutmp=new ClassUser();
            if(clsEntity.getPattern().trim().equals("分层班"))
                if(clsEntity.getSubjectid()!=null)
                    cutmp.setSubjectid(clsEntity.getSubjectid());	//subjectid
            cutmp.setClassgrade(clsEntity.getClassgrade());	//classgrade
            cutmp.setRelationtype("学生");			//relation_type
            cutmp.setPattern(clsEntity.getPattern());	//pattern
            cutmp.setUserid(tmpuid.trim());		//userid
            cutmp.setYear(clsEntity.getYear());
            List<ClassUser> cutmpList=this.classUserManager.getList(cutmp, null);
            if(cutmpList!=null&&cutmpList.size()>0){
                for(ClassUser tmpCu:cutmpList){
                    if(tmpCu.getClassid()!=null&&!allowClssList.contains(tmpCu.getClass())){
                        allowClssList.add(tmpCu.getClassid());
                    }
                }
                sqlbuilder=new StringBuilder();
                List<Object> objList=this.classUserManager.getDeleteSql(cutmp, sqlbuilder);
                if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
                    sqlArrayList.add(sqlbuilder.toString());
                    objArrayList.add(objList);
                }
                String remark="批量删除，条件："+clsEntity.getPattern()+"，学科:"+clsEntity.getSubjectid()+","+clsEntity.getClassgrade()+",学生,"+tmpuid+",";
                remark+=","+clsEntity.getYear();

                sqlbuilder=new StringBuilder();
                objList=this.classUserManager.getAddOperateLog(this.logined(request).getRef(),"j_class_user"
                        ,null,null, null,"DELETE", remark,sqlbuilder);
                if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
                    sqlArrayList.add(sqlbuilder.toString());
                    objArrayList.add(objList);
                }
            }

            //添加
            ClassUser addcu=new ClassUser();
            addcu.setRef(this.classUserManager.getNextId());
            addcu.setClassid(clsEntity.getClassid());
            addcu.setUserid(tmpuid.trim());
            addcu.setRelationtype("学生");
            addcu.setSubjectid(clsEntity.getSubjectid());
            sqlbuilder=new StringBuilder();
            List<Object> objList=this.classUserManager.getSaveSql(addcu, sqlbuilder);
            if(sqlbuilder!=null&&objList!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList.size()>0&&objArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.classUserManager.doExcetueArrayProc(sqlArrayList, objArrayList)){
                jeEntity.setType("success");
                jeEntity.setMsg("操作成功!");
                //向ETT发送变动请求
                if(allowClssList!=null&&allowClssList.size()>0){
                    for(Integer clsidInte:allowClssList){
                        if(clsidInte!=null){
                            //更新人员
                            if(!updateEttClassUser(clsidInte)){
                                logger.info("后台调班:"+clsidInte+"向ett更新人员失败!");
                                jeEntity.setType("error");
                                jeEntity.setMsg("向ett更新人员失败!");
                                transactionRollback();
                            }else{
                                logger.info("后台调班:"+clsidInte+"向ett更新人员成功!");
                            }
                        }
                    }
                }
            }else
                jeEntity.setMsg("操作失败，原因：wwww未知!");
        }else{
            jeEntity.setMsg("错误，没有可执行的操作!请重试!");
        }
        response.getWriter().print(jeEntity.toJSON());
    }
    /**
     * 自动升级
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=levelup",method=RequestMethod.POST)
    public void doClsLevelUp(HttpServletRequest request,HttpServletResponse response) throws Exception{
        String year=request.getParameter("year");
        String schoolid=request.getParameter("schoolid");
        JsonEntity jeEntity=new JsonEntity();

        if(year==null||year.trim().length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());return;
        }
        Integer dcschoolid=this.logined(request).getDcschoolid();
        if(schoolid!=null&&schoolid.trim().length()>0){
            dcschoolid=Integer.parseInt(schoolid);
        }
        //验证该是否在条件内执行
        //得到下一个学年
        TermInfo tm=new TermInfo();
        tm.setYear(year);
        PageResult presult=new PageResult();
        presult.setOrderBy(" u.YEAR ASC ");
        presult.setPageSize(1);
        List<TermInfo> tmList=this.termManager.getList(tm, presult);
        if(tmList==null||tmList.size()<1){
            jeEntity.setMsg("错误，没有发现当前年份!");
            response.getWriter().print(jeEntity.toJSON());return;
        }
        //如果当前时间小于9--1号。
        Long ndateTime=UtilTool.StringConvertToDate(Calendar.getInstance().get(Calendar.YEAR) + "-09-01", UtilTool.DateType.smollDATE).getTime();
        String nextyear=tmList.get(0).getYear();


        //得到当前nextyear下所有的行政班
        presult=new PageResult();
        presult.setPageSize(1);
        ClassInfo clsentity=new ClassInfo();
        clsentity.setYear(nextyear);
        clsentity.setDcschoolid(dcschoolid);
        clsentity.setPattern("行政班");
        List<ClassInfo> nextClsList=this.classManager.getList(clsentity, presult);
        if(nextClsList!=null&&nextClsList.size()>0){
            jeEntity.setMsg("错误，在"+nextyear+"中存在于新的行政班级!请确认!");
            response.getWriter().print(jeEntity.toJSON());return;
        }


        //则说明是上一学期,则得到当前学年
        if(new Date().getTime()<ndateTime){
            tm=new TermInfo();
            tm.setDYYear(nextyear);
            tmList=this.termManager.getList(tm, presult);
            if(tmList==null||tmList.size()<1){
                jeEntity.setMsg("错误，没有发现下一个年份!请确认是跨年!");
                response.getWriter().print(jeEntity.toJSON());return;
            }


        }else{  //如果超过9-1号，则说明要得到上一学年
            tm=new TermInfo();
            tm.setxYYear(nextyear);
            presult.setOrderBy(" u.YEAR DESC ");

            tmList=this.termManager.getList(tm, presult);
            if(tmList==null||tmList.size()<1){
                jeEntity.setMsg("错误，没有发现下一个年份!请确认是跨年!");
                response.getWriter().print(jeEntity.toJSON());return;
            }
            nextyear=tmList.get(0).getYear();
        }

        if(this.classManager.doClassLevelUp(nextyear,dcschoolid)){
            jeEntity.setMsg("自动升级成功!请刷新页面!");
            jeEntity.setType("success");
            //自动升级调用ETT接口，升级
            if(!levelUpSendToEtt(year)){
                jeEntity.setType("error");
                jeEntity.setMsg("操作失败，同步至网校信息失败!");
                transactionRollback();
            }
        }else
            jeEntity.setMsg("升级失败!原因：未知!");
        response.getWriter().print(jeEntity.toJSON());
    }



    @RequestMapping(params ="m=lzxUpdateM",method=RequestMethod.POST)
    @Transactional
    public void lzxUpdateM(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Object timeStr=request.getParameter("timestamp");
        Object schoolid=request.getParameter("lzx_school_id");
        Object dcschoolid=request.getParameter("dcschoolid");
        Object key=request.getParameter("key");
        //验证相关参数
        if(timeStr==null||timeStr.toString().trim().length()<1||!UtilTool.isNumber(timeStr.toString())){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，登陆时间戳参数缺少!\"}");return;
        }
        if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，分校ID为空!!\"}");return;
        }
        if(dcschoolid==null||dcschoolid.toString().trim().length()<1||!UtilTool.isNumber(dcschoolid.toString())){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，网校分校ID为空!!\"}");return;
        }


        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");
            return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        if(key==null||key.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，key为空!!\"}");
            return;
        }

        //验证是否在三分钟内
        Long ct=Long.parseLong(timeStr.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
      /*  if(d>3){//大于三分钟
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，响应超时!接口三分钟内有效!\"}");
            return;
        }*/
        //验证key
        String md5key=timeStr.toString()+schoolid+dcschoolid;
        md5key+=timeStr.toString();
        md5key= MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
        if(!md5key.trim().equals(key.toString().trim())){//如果不一致，则说明非法登陆
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，非法登陆!!\"}");return;
        }
        //添加到相应集合中，用于传入ett中    规则是 lzx_class_id+","+dc_school_id
        List<String> lzxClsList=new ArrayList<String>();

        String hasClsid=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object classid=clsJo.get("lzx_classid");
            Object className=clsJo.get("class_name");
            Object pattern=clsJo.get("pattern");
            Object classGrade=clsJo.get("class_grade");
            Object year=clsJo.get("year");
            Object type= clsJo.get("type");
            Object subjectid=clsJo.get("subject_id");
            Object isflag=clsJo.get("isflag");

            if(classid==null||classid.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，班级ID为空!!\"}");return;
            }
            if(className==null||className.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，班级名称为空!!\"}");
                return;
            }else
                className=java.net.URLDecoder.decode(className.toString(),"GBK");
            if(pattern==null||pattern.toString().trim().length()<1){
                //response.getWriter().print("异常错误，班级类型为空!!");return;
                pattern="行政班";
            }else
                pattern=java.net.URLDecoder.decode(pattern.toString(),"GBK");
            if(classGrade==null||classGrade.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，年级为空!!\"}");
                return;
            }else
                classGrade=java.net.URLDecoder.decode(classGrade.toString(),"GBK");
            if(year==null||year.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，学年为空!!\"}");
                return;
            }
            if(type==null||type.toString().trim().length()<1){
                // response.getWriter().print("异常错误，班级类型为空!!");return;
                type="NORMAL";
            }
            if(pattern!=null&&pattern.toString().trim().equals("分层班")){
                if(subjectid==null||subjectid.toString().trim().length()<1||!UtilTool.isNumber(subjectid.toString())){
                    response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，分层班的班级要添加学科ID,学科ID为空!!\"}");
                    return;
                }
            }
            if(isflag==null||isflag.toString().trim().length()<1||!UtilTool.isNumber(isflag.toString())){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，班级是否启用为空!!\"}");return;
            }

            //验证通过
            ClassInfo cls=new ClassInfo();
            cls.setClassname(className.toString());
            cls.setClassgrade(classGrade.toString());
            cls.setYear(year.toString());
            cls.setPattern(pattern.toString());
            cls.setLzxclassid(classid.toString());
            cls.setDcschoolid(Integer.parseInt(dcschoolid.toString()));
//            List<ClassInfo> clsList=this.classManager.getList(cls,null);
//            if(clsList!=null&&clsList.size()>0){
//                hasClsid=(hasClsid==null?classid.toString():hasClsid+","+classid.toString());
//                continue;
//            }

            cls.setType(type.toString());

            //cls.setClassid(Integer.parseInt(classid.toString()));
            cls.setIsflag(Integer.parseInt(isflag.toString()));
            if(pattern.toString().trim().equals("分层班")){
                cls.setSubjectid(Integer.parseInt(subjectid.toString()));
            }
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getSaveOrUpdateSql(cls,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
            //查询该用户是否存在，存在则是修改不存在则是添加
            ClassInfo searchCls=new ClassInfo();
            searchCls.setDcschoolid(Integer.parseInt(dcschoolid.toString()));
            searchCls.setLzxclassid(classid.toString());
            List<ClassInfo> tmpClsList=this.classManager.getList(searchCls,null);
            int opType=1;  //添加
            if(tmpClsList!=null&&tmpClsList.size()>0)
               opType=2;            //修改

            //添加到相应集合中，用于传入ett中
            if(cls.getLzxclassid()!=null&&cls.getLzxclassid().length()>0&&cls.getDcschoolid()!=null){
                lzxClsList.add(cls.getLzxclassid()+","+cls.getDcschoolid()+","+opType);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                if(hasClsid!=null){
                    response.getWriter().println("{\"type\":\"error\",\"msg\":\""+hasClsid+"\"}");
                }else
                    response.getWriter().println("{\"type\":\"success\"}");

               //////////////////////////同步班级至ETTfd

                //开始同步ETT
                if(lzxClsList!=null&&lzxClsList.size()>0){
                    for(String tmpstr:lzxClsList){
                        String[] itemArray=tmpstr.split(",");
                        String lzxclassid=itemArray[0].trim();
                        Integer scl=Integer.parseInt(itemArray[1].trim());
                        Integer optype=Integer.parseInt(itemArray[2].trim());

                            //查询
                            ClassInfo tmpCls=new ClassInfo();
                            tmpCls.setLzxclassid(lzxclassid);
                            tmpCls.setDcschoolid(scl);
                            List<ClassInfo> cList=this.classManager.getList(tmpCls,null);
                            if(cList!=null&&cList.size()>0){
                                //同步到四中OA
                                if(optype==1){
                                   if(EttInterfaceUserUtil.addClassBase(cList.get(0))){
                                       logger.error(cList.get(0).getClassid()+"更新班级成功!");
                                   }else{
                                       logger.error(cList.get(0).getClassid() + "更新班级失败!");
                                       transactionRollback();
                                       response.getWriter().println("{\"type\":\"error\",\"msg\":\""+cList.get(0).getClassid()+"更新班级失败!\"}");return;
                                   }
                                }else{
                                    if(EttInterfaceUserUtil.updateClassBase(cList.get(0))){
                                        logger.error(cList.get(0).getClassid()+"更新班级成功!");
                                    }else{
                                        logger.error(cList.get(0).getClassid()+"更新班级失败!");
                                        transactionRollback();
                                        response.getWriter().println("{\"type\":\"error\",\"msg\":\""+cList.get(0).getClassid()+"更新班级失败!\"}");return;
                                    }
                                }
                                //如果是四中，则同步修改或添加相关班级信息
                                if(dcschoolid.toString().trim().equals(UtilTool.utilproperty.getProperty("BHSF_SCHOOL_ID"))){
                                    //同步到四中
                                    if(!BhsfInterfaceUtil.addOrModifyClassBase(cList.get(0))){
                                        logger.error(cList.get(0).getClassid()+"更新四中班级失败!");
                                        transactionRollback();
                                        response.getWriter().println("{\"type\":\"error\",\"msg\":\""+cList.get(0).getClassid()+"更新四中班级失败!\"}");return;
                                    }
                                }
                            }else{
                                logger.error("没有查到班级!");
                                transactionRollback();
                                response.getWriter().println("{\"type\":\"error\",\"msg\":\"更新班级失败!没有查询到相关班级!\"}");return;
                            }
                    }
                }
            }else{
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，原因：未知!\"}");return;
            }
        }else{
            if(hasClsid!=null)
                response.getWriter().println("{\"type\":\"error\",\"msg\":\""+hasClsid+"\"}");
            else
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"没有可添加或修改的班级记录可以操作!\"}");return;
        }
    }


    @RequestMapping(params ="m=lzxUpdate",method=RequestMethod.POST)
    public void lzxUpdate(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Object timeStr=request.getParameter("timestamp");
        Object schoolid=request.getParameter("lzx_school_id");
        Object key=request.getParameter("key");
        //验证相关参数
        if(timeStr==null||timeStr.toString().trim().length()<1||!UtilTool.isNumber(timeStr.toString())){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，登陆时间戳参数缺少!\"}");return;
        }
        if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，分校ID为空!!\"}");return;
        }



        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");
            return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        if(key==null||key.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，key为空!!\"}");
            return;
        }

        //验证是否在三分钟内
        Long ct=Long.parseLong(timeStr.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，响应超时!接口三分钟内有效!\"}");
            return;
        }
        //验证key
        String md5key=timeStr.toString()+schoolid;
        md5key+=timeStr.toString();
        md5key= MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
        if(!md5key.trim().equals(key.toString().trim())){//如果不一致，则说明非法登陆
            response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，非法登陆!!\"}");return;
        }


        String hasClsid=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object classid=clsJo.get("lzx_classid");
            Object className=clsJo.get("class_name");
            Object pattern=clsJo.get("pattern");
            Object classGrade=clsJo.get("class_grade");
            Object year=clsJo.get("year");
            Object type= clsJo.get("type");
            Object subjectid=clsJo.get("subject_id");
            Object isflag=clsJo.get("isflag");

            if(classid==null||classid.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，班级ID为空!!\"}");return;
            }
            if(className==null||className.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，班级名称为空!!\"}");
                return;
            }else
                className=java.net.URLDecoder.decode(className.toString(),"GBK");
            if(pattern==null||pattern.toString().trim().length()<1){
                //response.getWriter().print("异常错误，班级类型为空!!");return;
                pattern="行政班";
            }else
                pattern=java.net.URLDecoder.decode(pattern.toString(),"GBK");
            if(classGrade==null||classGrade.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，年级为空!!\"}");
                return;
            }else
                classGrade=java.net.URLDecoder.decode(classGrade.toString(),"GBK");
            if(year==null||year.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，学年为空!!\"}");
                return;
            }
            if(type==null||type.toString().trim().length()<1){
                // response.getWriter().print("异常错误，班级类型为空!!");return;
                type="NORMAL";
            }
            if(pattern!=null&&pattern.toString().trim().equals("分层班")){
                if(subjectid==null||subjectid.toString().trim().length()<1||!UtilTool.isNumber(subjectid.toString())){
                    response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，分层班的班级要添加学科ID,学科ID为空!!\"}");
                    return;
                }
            }
            if(isflag==null||isflag.toString().trim().length()<1||!UtilTool.isNumber(isflag.toString())){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，班级是否启用为空!!\"}");return;
            }

            //验证通过
            ClassInfo cls=new ClassInfo();
            cls.setClassname(className.toString());
            cls.setClassgrade(classGrade.toString());
            cls.setYear(year.toString());
            cls.setPattern(pattern.toString());
            cls.setLzxclassid(classid.toString());
//            List<ClassInfo> clsList=this.classManager.getList(cls,null);
//            if(clsList!=null&&clsList.size()>0){
//                hasClsid=(hasClsid==null?classid.toString():hasClsid+","+classid.toString());
//                continue;
//            }

            cls.setType(type.toString());

            cls.setClassid(Integer.parseInt(classid.toString()));
            cls.setIsflag(Integer.parseInt(isflag.toString()));
            if(pattern.toString().trim().equals("分层班")){
                cls.setSubjectid(Integer.parseInt(subjectid.toString()));
            }
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getSaveOrUpdateSql(cls,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                if(hasClsid!=null){
                    response.getWriter().println("{\"type\":\"error\",\"msg\":\""+hasClsid+"\"}");
                }else
                    response.getWriter().println("{\"type\":\"success\"}");
            }else{
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"异常错误，原因：未知!\"}");return;
            }
        }else{
            if(hasClsid!=null)
                response.getWriter().println("{\"type\":\"error\",\"msg\":\""+hasClsid+"\"}");
            else
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"没有可添加或修改的班级记录可以操作!\"}");return;
        }
    }

    /**
     * 乐知行，删除方法
     * @param request
     * @param response
     */
    @RequestMapping(params="m=lzxDelM",method=RequestMethod.GET)
    @Transactional
    public void lzxDelM(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Object timeStr=request.getParameter("timestamp");
        Object schoolid=request.getParameter("lzx_school_id");
        Object dcschoolid=request.getParameter("dcschoolid");
        Object key=request.getParameter("key");
        //验证相关参数
        if(timeStr==null||timeStr.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，登陆时间戳参数缺少!\"}");return;
        }
        if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，分校ID为空!!\"}");return;
        }
        if(key==null||key.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，key为空!!\"}");
            return;
        }

        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }

        //验证是否在三分钟内
//        Long ct=Long.parseLong(timeStr.toString());
//        Long nt=new Date().getTime();
//        double d=(nt-ct)/(1000*60);
//        if(d>3){//大于三分钟
//            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，响应超时!接口三分钟内有效!\"}");return;
//        }
        //验证key
        String md5key=timeStr.toString()+schoolid+dcschoolid;
        md5key+=timeStr.toString();
        md5key= MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
        if(!md5key.trim().equals(key.toString().trim())){//如果不一致，则说明非法登陆
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，非法登陆!!\"}");
            return;
        }
        List<String> lzxClsList=new ArrayList<String>();
        String noDelClsId=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object lzxclassid=clsJo.get("lzx_classid");

            if(lzxclassid==null||lzxclassid.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，班级ID为空!!\"}");return;
            }
            //验证班级是否存在
            //验证通过
            ClassInfo cls=new ClassInfo();
            cls.setLzxclassid(lzxclassid.toString());
            cls.setDcschoolid(Integer.parseInt(dcschoolid.toString()));
            List<ClassInfo> clsList=this.classManager.getList(cls,null);
            if(clsList==null||clsList.size()<1){
                response.getWriter().println("{\"type\":\"error\",\"msg\":\"没有发现该班级!!\"}");return;
            }
            Integer classid=clsList.get(0).getClassid();

            //查看是否存在学生记录
            ClassUser cutmp=new ClassUser();
            cutmp.setClassid(Integer.parseInt(classid.toString()));
            cutmp.getUserinfo().setDcschoolid(Integer.parseInt(dcschoolid.toString()));
            PageResult presult=new PageResult();
            presult.setPageSize(1);
            presult.setPageNo(1);
            List<ClassUser> cuList=this.classUserManager.getList(cutmp,presult);
            if(cuList!=null&&cuList.size()>0){
                noDelClsId=(noDelClsId==null?classid.toString():noDelClsId+","+classid);
                continue;
            }
            //验证通过
            cls=new ClassInfo();
//            cls.setLzxclassid(lzxclassid.toString());
            cls.setClassid(classid);
            cls.setDcschoolid(Integer.parseInt(dcschoolid.toString()));
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getDeleteSql(cls, sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }

            //添加到相应集合中，用于传入ett中
            if(cls.getLzxclassid()!=null&&cls.getLzxclassid().length()>0&&cls.getDcschoolid()!=null){
                lzxClsList.add(cls.getLzxclassid()+","+cls.getDcschoolid());
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                if(noDelClsId!=null&&noDelClsId.toString().trim().length()>0){
                    response.getWriter().println("{\"type\":\"success\",\"msg\":\""+noDelClsId+"\"}");
                }else
                    response.getWriter().println("{\"type\":\"success\"}");

                //////////////////////////同步班级至ETTfd

                //开始同步ETT
                if(lzxClsList!=null&&lzxClsList.size()>0){
                    for(String tmpstr:lzxClsList){
                        String[] itemArray=tmpstr.split(",");
                        String lzxclassid=itemArray[0].trim();
                        Integer scl=Integer.parseInt(itemArray[1].trim());
                        //查询
                        ClassInfo tmpCls=new ClassInfo();
                        tmpCls.setLzxclassid(lzxclassid);
                        tmpCls.setDcschoolid(scl);
                        List<ClassInfo> cList=this.classManager.getList(tmpCls,null);
                        if(cList!=null&&cList.size()>0){
                            if(EttInterfaceUserUtil.delClassBase(cList.get(0))){
                                logger.error(cList.get(0).getClassid()+"删除Ett班级成功!");
                            }else{
                                logger.error(cList.get(0).getClassid()+"删除Ett班级失败!");
                                transactionRollback();
                                response.getWriter().println("{\"type\":\"success\",\"msg\":\"删除Ett班级失败!\"}");
                                return;
                            }
                            //同步删除班级到四中
                            if(dcschoolid.toString().trim().equals(UtilTool.utilproperty.getProperty(""))){
                                if(!BhsfInterfaceUtil.delClassBase(cList.get(0))){
                                    logger.error(cList.get(0).getClassid()+"删除四中班级失败!");
                                    transactionRollback();
                                    response.getWriter().println("{\"type\":\"success\",\"msg\":\"删除四中班级失败!\"}");
                                    return;
                                }
                            }
                        }
                    }
                }

            }else{
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，原因：未知!\"}");
            }
        }else{
            if(noDelClsId!=null&&noDelClsId.toString().trim().length()>0){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\""+noDelClsId+"\"}");
            }else
                 response.getWriter().println("{\"type\":\"success\",\"msg\":\"没有可添加或修改的班级记录可以操作!\"}");
        }
    }
    /**
     * 乐知行，删除方法
     * @param request
     * @param response
     */
    @RequestMapping(params="m=lzxDel",method=RequestMethod.POST)
    public void lzxDel(HttpServletRequest request,HttpServletResponse response) throws Exception{
        Object timeStr=request.getParameter("timestamp");
        Object schoolid=request.getParameter("lzx_school_id");
        Object key=request.getParameter("key");
        //验证相关参数
        if(timeStr==null||timeStr.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，登陆时间戳参数缺少!\"}");return;
        }
        if(schoolid==null||schoolid.toString().trim().length()<1||!UtilTool.isNumber(schoolid.toString())){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，分校ID为空!!\"}");return;
        }
        if(key==null||key.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，key为空!!\"}");
            return;
        }

        String clsArrayjson=request.getParameter("clsarrayjson");
        if(clsArrayjson==null||clsArrayjson.toString().trim().length()<1){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }
        net.sf.json.JSONArray clsJr=net.sf.json.JSONArray.fromObject(clsArrayjson);
        if(clsJr==null||clsJr.isEmpty()){
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，没有发现您要添加或修改的班级数据Json!\"}");return;
        }

        //验证是否在三分钟内
        Long ct=Long.parseLong(timeStr.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，响应超时!接口三分钟内有效!\"}");return;
        }
        //验证key
        String md5key=timeStr.toString()+schoolid;
        md5key+=timeStr.toString();
        md5key= MD5_NEW.getMD5ResultCode(md5key);//生成md5加密
        if(!md5key.trim().equals(key.toString().trim())){//如果不一致，则说明非法登陆
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，非法登陆!!\"}");
            return;
        }

        String noDelClsId=null;
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        Iterator jrIte=clsJr.iterator();
        while(jrIte.hasNext()){
            net.sf.json.JSONObject clsJo=(net.sf.json.JSONObject)jrIte.next();
            Object classid=clsJo.get("lzx_classid");

            if(classid==null||classid.toString().trim().length()<1){
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，班级ID为空!!\"}");return;
            }
            //查看是否存在学生记录
            ClassUser cutmp=new ClassUser();
            cutmp.setClassid(Integer.parseInt(classid.toString()));
            PageResult presult=new PageResult();
            presult.setPageSize(1);
            presult.setPageNo(1);
            List<ClassUser> cuList=this.classUserManager.getList(cutmp,presult);
            if(cuList!=null&&cuList.size()>0){
                noDelClsId=(noDelClsId==null?classid.toString():noDelClsId+","+classid);
                continue;
            }
            //验证通过
            ClassInfo cls=new ClassInfo();
            cls.setLzxclassid(classid.toString());
            StringBuilder sqlbuilder=new StringBuilder();
            List<Object> objList=this.classManager.getDeleteSql(cls, sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                sqlArrayList.add(sqlbuilder.toString());
                objArrayList.add(objList);
            }
        }

        if(sqlArrayList!=null&&sqlArrayList.size()>0&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
            if(this.classManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                if(noDelClsId!=null&&noDelClsId.toString().trim().length()>0){
                    response.getWriter().println("{\"type\":\"success\",\"msg\":\""+noDelClsId+"\"}");
                }else
                    response.getWriter().println("{\"type\":\"success\"}");
            }else{
                response.getWriter().println("{\"type\":\"success\",\"msg\":\"异常错误，原因：未知!\"}");
            }
        }else{
            response.getWriter().println("{\"type\":\"success\",\"msg\":\"没有可添加或修改的班级记录可以操作!\"}");
        }
    }

    /**
     * 自动升级发送请求到ETT
     * @param year
     * @return
     */
    private boolean levelUpSendToEtt(String year){
        //得到当前年份的所有班级
        if(year==null||year.trim().length()<1)return false;
        ClassInfo clsEntity=new ClassInfo();
        clsEntity.setYear(year);
        List<ClassInfo> clsList=this.classManager.getList(clsEntity,null);
        if(clsList==null||clsList.size()<1)return false;
        for(ClassInfo cls:clsList){
            if(cls!=null){
                //发送ETT班级信息
                if(!EttInterfaceUserUtil.addClassBase(cls)){
                    System.out.println("自动升级，"+cls.getClassid()+" "+cls.getDcschoolid()+"传入ETT失败!");
                }else
                    System.out.println("自动升级，"+cls.getClassid()+" "+cls.getDcschoolid()+"传入ETT成功!");

                //发送ETT学生成员数据

                if(!updateEttClassUser(cls.getClassid())){
                    System.out.println("自动升级，"+cls.getClassid()+" 班级人员传入ETT失败!");
                }else
                    System.out.println("自动升级，"+cls.getClassid()+" 班级人员传入ETT成功!");

            }
        }
        return true;
    }


    /**
     * 删除班级下的学生，班主任，学生等信息
     * @param classid   删除的班级id
     * @param isDelTea 是否删除老师信息
     * @return
     */
    private boolean delEttClassUser(Integer classid,Integer dcschool,Boolean isDelTea){
        if(classid==null)return false;
        //得到班级信息
//        ClassInfo clsEntity=new ClassInfo();
//        clsEntity.setClassid(classid);
//        List<ClassInfo> clsList=this.classManager.getList(clsEntity,null);
//        if(clsList==null||clsList.size()<1){
//            return false;
//        }

        List<Map<String,Object>> mapList=null;
        if(isDelTea!=null&&!isDelTea){
            mapList=new ArrayList<Map<String, Object>>();
            //查任课老师
            ClassUser cu=new ClassUser();
            cu.setClassid(classid);
            cu.setRelationtype("任课老师");
            List<ClassUser> cuTeaList=this.classUserManager.getList(cu,null);
            if(cuTeaList!=null&&cuTeaList.size()>0){
                for(ClassUser tmp:cuTeaList){
                    // 必带 userId，userType,subjectId 的三个key
                    Map<String,Object> reMap=new HashMap<String, Object>();
                    reMap.put("userId",tmp.getUid());
                    reMap.put("subjectId",cu.getSubjectid()==null?-1:cu.getSubjectid());
                    reMap.put("userType",2);
                    mapList.add(reMap);
                }
            }
            //查班主任
            cu.setRelationtype("班主任");
            List<ClassUser> cuBanList=this.classUserManager.getList(cu,null);
            if(cuBanList!=null&&cuBanList.size()>0){
                for(ClassUser tmp:cuBanList){
                    // 必带 userId，userType,subjectId 的三个key
                    Map<String,Object> reMap=new HashMap<String, Object>();
                    reMap.put("userId",tmp.getUid());
                    reMap.put("jid",(tmp.getEttuserid()==null?-1:tmp.getEttuserid()));
                    reMap.put("subjectId",cu.getSubjectid()==null?-1:cu.getSubjectid());
                    reMap.put("userType",1);
                    mapList.add(reMap);
                }
            }
        }
        return EttInterfaceUserUtil.OperateClassUser(mapList,classid,dcschool);
    }

    /**
     * 更新班级信息
     * @param clsid 班级ID
     * @return
     */
    private boolean updateEttClassUser(Integer clsid){
        if(clsid!=null){
            //查询班级信息
            ClassInfo cls=new ClassInfo();
            cls.setClassid(clsid);
            List<ClassInfo> clsList=this.classManager.getList(cls,null);
            if(clsList==null||clsList.size()<1)return false;
            Integer dcschoolid =clsList.get(0).getDcschoolid();
            //得到该班级的人员信息
            ClassUser cu=new ClassUser();
            cu.setClassid(clsid);
            List<ClassUser> cuTmpList=this.classUserManager.getList(cu,null);
            if(cuTmpList!=null&&cuTmpList.size()>0){
                // 必带 userId，userType,subjectId 的三个key
                List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
                for (ClassUser cuTmpe:cuTmpList){
                    if(cuTmpe!=null){
                        Map<String,Object> tmpMap=new HashMap<String, Object>();
                        tmpMap.put("userId",cuTmpe.getUid());
                        Integer userType=3;
                        if(cuTmpe.getRelationtype()!=null){
                            if(cuTmpe.getRelationtype().trim().equals("任课老师"))
                                userType=2;
                            else if(cuTmpe.getRelationtype().trim().equals("班主任"))
                                userType=1;
                        }
                        tmpMap.put("userType",userType);
                        tmpMap.put("jid",(cuTmpe.getEttuserid()==null?-1:cuTmpe.getEttuserid()));
                        tmpMap.put("subjectId",cuTmpe.getSubjectid()==null?-1:cuTmpe.getSubjectid());
                        mapList.add(tmpMap);
                    }
                }
                if(!EttInterfaceUserUtil.OperateClassUser(mapList,clsid,dcschoolid)){
                    System.out.println("classUser同步至网校失败!");
                    return false;
                } else
                    System.out.println("classUser同步至网校成功!");
            }
        }else
            return false;
        return true;
    }

    @RequestMapping(params="m=validateClass",method=RequestMethod.POST)
    public void validateClassNum(HttpServletRequest request, HttpServletResponse response){
        String sId = request.getParameter("schoolId");
        String year = request.getParameter("year");
        String from = request.getParameter("from");
        int schoolId = 0;
        int existClass = 0;
        int maxClass = 0;

        if(sId != null && !sId.equals(""))
            schoolId = Integer.valueOf(sId);

        if(year == null || from == null || year.equals("") || from.equals("")) {
            try {
                response.getWriter().println("{\"result\":\"wrong\"}");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (schoolId >= 50000) {
            if(from.equals("addClass"))
                existClass = this.classManager.getTotalClass(schoolId, year, 1);
            else
                existClass = this.classManager.getTotalClass(schoolId, year, 2);
            System.out.println("this is existclass="+existClass);
            HashMap<String,String> paramMap=new HashMap<String,String>();
            paramMap.put("time",new Date().getTime()+"");

            paramMap.put("schoolId",sId);
            paramMap.put("year",year);
            String val = UrlSigUtil.makeSigSimple("groupInterFace.do", paramMap);
            paramMap.put("sign",val);
            // http\://int.etiantian.com\:34180/totalSchool/ cls?m=getClsNum&schoolid=&year=
            String url=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
            //http://localhost:8080/totalSchool/franchisedSchool?m=getTC
            url +="franchisedSchool?m=getTC";

            net.sf.json.JSONObject jo=UtilTool.sendPostUrl(url,paramMap,"UTF-8");
            if(jo!=null&&jo.containsKey("result"))
                maxClass = Integer.valueOf(jo.get("result").toString());
            if(jo == null) {
                try {
                    response.getWriter().println("{\"result\":\"wrong\"}");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(from.equals("addClass") && existClass < maxClass) {
                try {
                    response.getWriter().println("{\"result\":\"success\"}");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if(!from.equals("addClass") && existClass <= maxClass){
                try {
                    response.getWriter().println("{\"result\":\"success\"}");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    response.getWriter().println("{\"result\":\"wrong\"}");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            try {
                response.getWriter().println("{\"result\":\"wrong\"}");
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}




















