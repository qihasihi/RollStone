package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.GradeInfo;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.entity.teachpaltform.TpVirtualClassStudent;
import com.school.manager.TermManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassStudentManager;
import com.school.manager.teachpaltform.TpVirtualClassStudentManager;
import com.school.util.JsonEntity;
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

@Controller
@RequestMapping(value = "virtualclassstudent")
public class TpVirtualClassStudentController extends BaseController<TpVirtualClassStudent> {
    @Autowired
    private ITermManager termManager;
    @Autowired
    private ITpVirtualClassStudentManager tpVirtualClassStudentManager;

    //  ajax 获取虚拟班级学生列表
    @RequestMapping(params = "m=searchStudentList", method = RequestMethod.POST)
    public void getSearchStudentList(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        TpVirtualClassStudent tvc = this.getParameter(request, TpVirtualClassStudent.class);
        if(tvc == null
                || tvc.getVirtualclassid()==null) {
            je.setType("error");
            je.setMsg("参数错误无法查询！");
            response.getWriter().print(je.toJSON());
            return ;
        }
        String grade = request.getParameter("grade");
        String classid = request.getParameter("classid");
        String stuname = request.getParameter("stuname");

        if(grade==null || grade.trim().equals("0"))
            grade=null;
        if(classid==null || classid.trim().equals("0"))
            classid=null;
        if(stuname==null || stuname.trim().length()==0)
            stuname=null;
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if(ti==null)
            ti = this.termManager.getMaxIdTerm(true);
        if(ti == null) {
            je.setType("error");
            je.setMsg("当前不在学期时间！！");
            response.getWriter().print(je.toJSON());
            return ;
        }
        //检查小组名是否存在
        List<Map<String,Object>> stuList = this.tpVirtualClassStudentManager
                .getStudentList(
                        grade,
                        classid != null ? Integer.parseInt(classid) : null,
                        stuname,
                        ti.getYear(),
                        tvc.getVirtualclassid(),
                        this.logined(request).getDcschoolid()
                        );
        je.setObjList(stuList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    //  ajax 获取学期教师的教授的学科和年级信息
    @RequestMapping(params = "m=getVirClassStudent", method = RequestMethod.POST)
    public void getVirClassStudent(HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        TpVirtualClassStudent tvc = this.getParameter(request, TpVirtualClassStudent.class);
        if(tvc == null
                || tvc.getVirtualclassid()==null) {
            je.setType("error");
            je.setMsg("班级参数错误！");
            response.getWriter().print(je.toJSON());
            return ;
        }
        //检查小组名是否存在
        tvc.setIscomplete(0);
        List<TpVirtualClassStudent> stuList = this.tpVirtualClassStudentManager.getList(tvc,null);
        je.setObjList(stuList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    //  给虚拟班级添加学生
    @RequestMapping(params = "m=addVirtualClassStudents", method = RequestMethod.POST)
    public void addVirtualClassStudents(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        TpVirtualClassStudent tvc = this.getParameter(request, TpVirtualClassStudent.class);

        if(tvc == null
                || tvc.getVirtualclassid()==null) {
            je.setType("error");
            je.setMsg("班级参数错误！");
            response.getWriter().print(je.toJSON());
            return ;
        }
        String students = request.getParameter("students");
        if(students==null || students.split(",").length==0){
            je.setType("error");
            je.setMsg("学生参数获取错误！");
            response.getWriter().print(je.toJSON());
            return ;
        }

        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        StringBuilder sql=null;
        List<Object> objList=null;

        tvc.setCuserid(this.logined(request).getUserid());

        sql = new StringBuilder();
        objList = this.tpVirtualClassStudentManager.getDeleteSql(tvc, sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);

        for(String stuid : students.split(",")){
            tvc.setUserid(Integer.parseInt(stuid));
            sql = new StringBuilder();
            objList = this.tpVirtualClassStudentManager.getSaveSql(tvc, sql);
            if (sql == null || objList == null) {
                je.setMsg("添加学生出错！");// 异常错误，参数不齐，无法正常访问!
                je.setType("error");
                response.getWriter().print(je.toJSON());
                return ;
            }
            sqlListArray.add(sql.toString());
            objListArray.add(objList);
        }
        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            boolean bo = this.tpVirtualClassStudentManager.doExcetueArrayProc(sqlListArray, objListArray);
            if (bo) {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }
}
