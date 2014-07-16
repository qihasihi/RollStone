package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.SubjectUser;
import com.school.entity.TermInfo;
import com.school.entity.teachpaltform.TrusteeShipClass;
import com.school.manager.TermManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.teachpaltform.ITrusteeShipClassManager;
import com.school.manager.teachpaltform.TrusteeShipClassManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "trusteeship")
public class TrusteeShipClassController extends BaseController<TrusteeShipClass> {
    private ITrusteeShipClassManager trusteeShipClassManager;
    private ITermManager termManager;

    public TrusteeShipClassController(){
        this.trusteeShipClassManager=this.getManager(TrusteeShipClassManager.class);
        this.termManager=this.getManager(TermManager.class);
    }
    /**
     * 查询可以进行托管的老师
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=getTrusteeShipTchs", method = RequestMethod.POST)
    public void getTrusteeShipTchs(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if(ti==null)
            ti = this.termManager.getMaxIdTerm(true);
        // asdf
        String grade = request.getParameter("grade");
        String subjectid=request.getParameter("subjectid");

        if(grade==null || grade.trim().length()<1||subjectid==null||subjectid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return ;
        }

        String tchname = request.getParameter("tchname");
        if(tchname==null || tchname.trim().length()==0){
            tchname=null;
        }

        // 受托管的老师必须与本人教授学科相同
     /*   SubjectUser su = new SubjectUser();
        su.setUserid(this.logined(request).getRef());
        List<SubjectUser> suList = this.subjectUserManager.getList(su,null);
        String subjects="";
        for(SubjectUser tmpSu:suList){
            subjects+=tmpSu.getSubjectid()+",";
        }
        if(subjects.length()>0)
            subjects=subjects.substring(0,subjects.length()-1);
        else
            subjects=null; */

        List<Map<String, Object>> tchList
                = this.trusteeShipClassManager.getTrusteeShipTchs(subjectid, grade, ti.getYear(), tchname);
        je.setType("success");
        je.setObjList(tchList);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 教师专题首页获取托管消息
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=getTrusteeShips", method = RequestMethod.POST)
    public void getTrusteeShips(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        List tl = new ArrayList();

        TrusteeShipClass tsc = new TrusteeShipClass();
        tsc.setReceiveteacherid(this.logined(request).getUserid());
        tsc.setIsaccept(0);
        List<TrusteeShipClass> tscList = this.trusteeShipClassManager.getList(tsc,null);
        tl.addAll(tscList);

        tsc = new TrusteeShipClass();
        tsc.setTrustteacherid(this.logined(request).getUserid());
        tsc.setIsaccept(2);
        tscList = this.trusteeShipClassManager.getList(tsc,null);
        tl.addAll(tscList);

        je.setObjList(tl);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 添加托管老师记录
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=addTrusteeShip", method = RequestMethod.POST)
    public void addTrusteeShip(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TrusteeShipClass tsc = this.getParameter(request, TrusteeShipClass.class);

        if(tsc==null || tsc.getReceiveteacherid()==null
                || tsc.getTrustclassid()==null){
            je.setType("error");
            je.setMsg("参数错误！！");
            response.getWriter().print(je.toJSON());
            return ;
        }
        tsc.setTrustteacherid(this.logined(request).getUserid());
        if(this.trusteeShipClassManager.doSave(tsc)){
            je.setType("success");
        }else{
            je.setType("error");
            je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 修改托管记录
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=updateTrusteeShip", method = RequestMethod.POST)
    public void updateTrusteeShip(HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TrusteeShipClass tsc = this.getParameter(request, TrusteeShipClass.class);
        if(tsc==null || tsc.getRef()==null
                || tsc.getIsaccept()==null){
            je.setType("error");
            je.setMsg("参数错误！！");
            response.getWriter().print(je.toJSON());
            return ;
        }
        if(this.trusteeShipClassManager.doUpdate(tsc)){
            je.setType("success");
        }else{
            je.setType("error");
            je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 删除托管老师记录
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "m=delTrusteeShip", method = RequestMethod.POST)
    public void delTrusteeShip(HttpServletRequest request,
                               HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        TrusteeShipClass tsc = this.getParameter(request, TrusteeShipClass.class);

        if(tsc==null){
            je.setType("error");
            je.setMsg("参数错误！！");
            response.getWriter().print(je.toJSON());
            return ;
        }

        if(tsc.getRef()==null && tsc.getTrustclassid()==null){
            je.setType("error");
            je.setMsg("参数错误！！");
            response.getWriter().print(je.toJSON());
            return ;
        }

        if(tsc.getTrustclassid()!=null)
            tsc.setTrustteacherid(this.logined(request).getUserid());

        if(this.trusteeShipClassManager.doDelete(tsc)){
            je.setType("success");
        }else{
            je.setType("error");
            je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }
}
