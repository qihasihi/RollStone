package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.entity.teachpaltform.TpVirtualClassInfo;
import com.school.entity.teachpaltform.TpVirtualClassStudent;
import com.school.manager.ClassManager;
import com.school.manager.ClassUserManager;
import com.school.manager.GradeManager;
import com.school.manager.TermManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.teachpaltform.ITpVirtualClassManager;
import com.school.manager.teachpaltform.TpVirtualClassManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "virtualclass")
public class TpVirtualClassController extends BaseController<TpVirtualClassInfo> {
    private ITpVirtualClassManager tpVirtualClassManager;
    private ITermManager termManager;
    private IGradeManager gradeManager;
    private IClassManager classManager;
    private IClassUserManager classUserManager;

    public TpVirtualClassController(){
        this.tpVirtualClassManager=this.getManager(TpVirtualClassManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
    }
    /**
     * �����ʦ����ҳ
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */

    @RequestMapping(params = "m=toClassManager", method = RequestMethod.GET)
    public ModelAndView toClassManager(HttpServletRequest request,HttpServletResponse response,
                                            ModelMap mp) throws Exception {
        JsonEntity jeEntity = new JsonEntity();
        String subjectid=request.getParameter("subjectid");
        String gradeid=request.getParameter("gradeid");
        if(subjectid==null||subjectid.trim().length()<1||
                gradeid==null||gradeid.trim().length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.getAlertMsgAndBack());
            return null;
        }
        TermInfo ti = this.termManager.getMaxIdTerm(false);
        if(ti==null)
            ti = this.termManager.getMaxIdTerm(true);
        mp.put("term", ti);

        if(ti.getRef()==null||ti.getRef().trim().length()<1){
            jeEntity.setMsg("��ǰ����ѧ��ʱ�䣡��");
            response.getWriter().print(jeEntity.getAlertMsgAndBack());
            return null;
        }

        GradeInfo g=new GradeInfo();
        g.setGradeid(Integer.parseInt(gradeid));
        List<GradeInfo> gradeList = this.gradeManager.getList(g,null);
//        ClassInfo cla = new ClassInfo();
//        cla.setYear(ti.getYear());
//        List<ClassInfo> classList = this.classManager.getList(cla,null);
        ClassUser c = new ClassUser();
        //��ǰѧ�ڡ�ѧ�ơ��꼶�µ��ڿΰ༶
        c.setClassgrade(gradeList.get(0).getGradevalue());
        c.setUserid(this.logined(request).getRef());
        c.setRelationtype("�ο���ʦ");
        c.setSubjectid(Integer.parseInt(subjectid));
        c.setYear(ti.getYear());
        List<ClassUser>classList=this.classUserManager.getList(c,null);
        UserInfo u=this.logined(request);
        List<Map<String,Object>> clsList=this.tpVirtualClassManager.getListBytch(u.getRef(), ti.getYear());
        mp.put("classes", clsList);
        mp.put("grades", gradeList);
        mp.put("classList", classList);
        mp.put("gradeid",gradeid);
        mp.put("subjectid",subjectid);
        return new ModelAndView("/teachpaltform/classmanager/classManager", mp);
    }

    //  ajax ��ȡѧ�ڽ�ʦ�Ľ��ڵ�ѧ�ƺ��꼶��Ϣ
    @RequestMapping(params = "m=addVirtualClass", method = RequestMethod.POST)
    public void addVirtualClass(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        TpVirtualClassInfo tvc = this.getParameter(request, TpVirtualClassInfo.class);
        if(tvc == null
                || tvc.getVirtualclassname()==null
                || tvc.getVirtualclassname().trim().length()==0) {
            je.setType("error");
            je.setMsg("�༶���Ʋ������󣡣�");
            response.getWriter().print(je.toJSON());
            return ;
        }
        //���С�����Ƿ����
        tvc.setCuserid(this.logined(request).getUserid());
        List<TpVirtualClassInfo> tvcList = this.tpVirtualClassManager.getList(tvc,null);
        if(tvcList != null
                && tvcList.size()>0) {
            je.setType("error");
            je.setMsg("�ð༶���Ѵ��ڣ�");
            response.getWriter().print(je.toJSON());
            return ;
        }
        //��Ӱ༶
        if(this.tpVirtualClassManager.doSave(tvc)){
            je.setType("success");
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());
    }

    //  ajax ��ȡѧ�ڽ�ʦ�Ľ��ڵ�ѧ�ƺ��꼶��Ϣ
    @RequestMapping(params = "m=changeVirClassStatus", method = RequestMethod.POST)
    public void changeVirClassStatus(HttpServletRequest request,
                                HttpServletResponse response) throws Exception {
        JsonEntity je=new JsonEntity();
        TpVirtualClassInfo tvc = this.getParameter(request, TpVirtualClassInfo.class);
        if(tvc == null
                || tvc.getVirtualclassid()==null
                || tvc.getStatus()==null) {
            je.setType("error");
            je.setMsg("�������󣡣�");
            response.getWriter().print(je.toJSON());
            return ;
        }
        if(this.tpVirtualClassManager.doUpdate(tvc)){
            je.setType("success");
        }else{
            je.setMsg(UtilTool.msgproperty.getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
        response.getWriter().print(je.toJSON());

    }

}
