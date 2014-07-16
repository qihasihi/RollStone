package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.InitWizardInfo;
import com.school.entity.SubjectInfo;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.manager.InitWizardManager;
import com.school.manager.SubjectManager;
import com.school.manager.TermManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IInitWizardManager;
import com.school.manager.inter.ISubjectManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.IUserManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import com.school.util.WriteProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuechunyang on 14-3-6.
 */
@Controller
@RequestMapping(value="/init")
public class InitWizardController extends BaseController<InitWizardInfo> {
    private IUserManager userManager;
    private IInitWizardManager initWizardManager;
    private ITermManager termManager;
    private ISubjectManager subjectManager;
    public InitWizardController(){
        this.userManager=this.getManager(UserManager.class);
        this.initWizardManager=this.getManager(InitWizardManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.subjectManager=this.getManager(SubjectManager.class);
    }
    /**
     * 初始化向导修改密码
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=changepassword", method = RequestMethod.POST)
    public void changePassword(HttpServletRequest request,
                               HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je = new JsonEntity();
        UserInfo u = this.logined(request);
        if (u == null) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        UserInfo userinfo = new UserInfo();
        if (userinfo != null) {
            userinfo.setUserid(u.getUserid());
            String newPassword = request.getParameter("new_password");
            if (userManager.doLogin(userinfo) != null) {
                userinfo.setPassword(newPassword);
                if (userManager.doUpdate(userinfo)) {
                    userinfo = userManager.getUserInfo(userinfo);
                    je.setType("success");
                    InitWizardInfo init =new InitWizardInfo();
                    List<InitWizardInfo> initList = this.initWizardManager.getList(null,null);
                    if(initList!=null&&initList.size()>0){
                        init.setRef(initList.get(0).getRef());
                        init.setCurrentStep(1);
                        init.setSuccess(1);
                        this.initWizardManager.doUpdate(init);
                    }else{
                        init.setCurrentStep(1);
                        init.setSuccess(1);
                        this.initWizardManager.doSave(init);
                    }
                } else {
                    je.setMsg("系统出错，无法修改！");
                    je.setType("error");
                }
            } else {
                je.setMsg(UtilTool.msgproperty
                        .getProperty("USER_LOGIN_PASSWORD_ERROR"));
                je.setType("error");
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("USER_LOGIN_PASSWORD_ERROR"));
            je.setType("error");
        }

        response.getWriter().print(je.toJSON());
    }

    /**
     * 初始化向导修改学期
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=setupmodify",method=RequestMethod.POST)
    public void dosetupUpdate(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je=new JsonEntity();
        String ref1 = request.getParameter("ref1");
        String ref2 = request.getParameter("ref2");
        String btime1 = request.getParameter("btime1");
        String btime2 = request.getParameter("btime2");
        String etime1 = request.getParameter("etime1");
        String etime2 = request.getParameter("etime2");
        btime1+=" 00:00:00";
        btime2+=" 00:00:00";
        etime1+=" 00:00:00";
        etime2+=" 00:00:00";
        if(ref1==null||ref2==null||
                btime1==null||btime2==null||
                etime1==null||etime2==null){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        TermInfo tobj = null;
        //存放值得数据集集合
        List<String> sqlArrayList = new ArrayList<String>();
        //存放sql的集合
        List<List<Object>> objArrayList = new ArrayList<List<Object>>();
        //存放第一学期数据
        tobj = new TermInfo();
        tobj.setRef(ref1);
        tobj.setSemesterbegindate(UtilTool.StringConvertToDate(btime1));
        tobj.setSemesterenddate(UtilTool.StringConvertToDate(etime1));
        //拼sql的对象和存放值得对象
        List<Object> objList;
        StringBuilder sql ;
        objList = new ArrayList<Object>();
        sql = new StringBuilder();

        objList = this.termManager.getUpdateSql(tobj, sql);
        objArrayList.add(objList);
        sqlArrayList.add(sql.toString());
        //下面存放第二学期数据
        tobj = new TermInfo();
        tobj.setRef(ref2);
        tobj.setSemesterbegindate(UtilTool.StringConvertToDate(btime2));
        tobj.setSemesterenddate(UtilTool.StringConvertToDate(etime2));

        objList = new ArrayList<Object>();
        sql = new StringBuilder();

        objList = this.termManager.getUpdateSql(tobj, sql);

        objArrayList.add(objList);
        sqlArrayList.add(sql.toString());

        Boolean b = this.termManager.doExcetueArrayProc(sqlArrayList, objArrayList);


        if(b){
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
            InitWizardInfo init =new InitWizardInfo();
            List<InitWizardInfo> initList = this.initWizardManager.getList(null,null);
            if(initList!=null){
                init.setRef(initList.get(0).getRef());
                init.setCurrentStep(2);
                init.setSuccess(1);
                this.initWizardManager.doUpdate(init);
            }
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 初始化向导学科设置
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=setupadd",method=RequestMethod.POST)
    protected void setupadd(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity je = new JsonEntity();
        String subjectname = request.getParameter("subjectname");
        SubjectInfo si = new SubjectInfo();
        si.setSubjecttype(2);
        this.subjectManager.doDelete(si);
        if(subjectname!=null&&subjectname.length()>0){
            SubjectInfo subjectInfo = null;
            //存放值得数据集集合
            List<String> sqlArrayList = new ArrayList<String>();
            //存放sql的集合
            List<List<Object>> objArrayList = new ArrayList<List<Object>>();
            //拼sql的对象和存放值得对象
            List<Object> objList;
            StringBuilder sql ;

            String[] subjectnames = subjectname.split("-");
            for(int i = 0;i<subjectnames.length;i++){
                if(subjectnames[i].length()>0){
                    subjectInfo = new SubjectInfo();
                    subjectInfo.setSubjectname(subjectnames[i]);
                    subjectInfo.setSubjecttype(2);
                    objList = new ArrayList<Object>();
                    sql = new StringBuilder();
                    objList = this.subjectManager.getSaveSql(subjectInfo, sql);
                    objArrayList.add(objList);
                    sqlArrayList.add(sql.toString());
                }
            }
            Boolean b = this.subjectManager.doExcetueArrayProc(sqlArrayList, objArrayList);
            if(b){
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                InitWizardInfo init =new InitWizardInfo();
                List<InitWizardInfo> initList = this.initWizardManager.getList(null,null);
                if(initList!=null){
                    init.setRef(initList.get(0).getRef());
                    init.setCurrentStep(3);
                    init.setSuccess(0);
                    this.initWizardManager.doUpdate(init);
                }
            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
            response.getWriter().print(je.toJSON());
        }else{
            je.setType("success");
            response.getWriter().print(je.toJSON());
            WriteProperties.writeProperties(request.getRealPath("/")
                    + "/WEB-INF/classes/properties/util.properties",
                    "ISFIRSTLOGIN", "1");
            UtilTool.utilproperty.setProperty("ISFIRSTLOGIN",
                    "1");
        }
    }
}
