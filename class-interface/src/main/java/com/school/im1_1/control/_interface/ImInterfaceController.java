package com.school.im1_1.control._interface;

import com.school.control.base.BaseController;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.im1_1.manager._interface.ImInterfaceManager;
import com.school.util.JsonEntity;
import com.school.util.MD5_NEW;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuechunyang on 14-7-25.
 */
@Controller
@RequestMapping(value="/imapi")
public class ImInterfaceController extends BaseController<ImInterfaceInfo>{
    private ImInterfaceManager imInterfaceManager;
    public ImInterfaceController(){
        this.imInterfaceManager=this.getManager(ImInterfaceManager.class);
    }

    /**
     * ����С�����ҳ��
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=getStudyModule",method= RequestMethod.GET)
    public void getStudyModule(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        String userid = request.getParameter("userid");
        String usertype=request.getParameter("usertype");
        String schoolid = request.getParameter("schoolid");
//        String timestamp = request.getParameter("timestamp");
//        String checkcode = request.getParameter("checkcode");
//        String md5key = userid+usertype+schoolid+timestamp;
//        String md5code = MD5_NEW.getMD5ResultImCode(md5key);
//        if(!checkcode.trim().equals(md5code)){
//            response.getWriter().print("{\"result\":\"error\",\"message\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
//            return;
//        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setUserid(Integer.parseInt(userid));
        obj.setUsertype(Integer.parseInt(usertype));
        List<Map<String,Object>> list = this.imInterfaceManager.getStudyModule(obj);
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(list!=null&&list.size()>0){
            m2.put("classes",list);
            m2.put("activityNotifyNum","12");
        }else{
            m.put("result","error");
            m.put("message","��ǰ�û�û��ѧϰĿ¼������ϵ����Ա");
        }
        m.put("result","success");
        m.put("message","�ɹ�");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * ����С�����ҳ��
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=getClassTask",method= RequestMethod.GET)
    public void getClassTask(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String classid = request.getParameter("classid");
        String schoolid = request.getParameter("schoolid");
//        String timestamp = request.getParameter("timestamp");
//        String checkcode = request.getParameter("checkcode");
//        String md5key = classid+schoolid+timestamp;
//        String md5code = MD5_NEW.getMD5ResultImCode(md5key);
//        if(!checkcode.trim().equals(md5code)){
//            response.getWriter().print("{\"result\":\"error\",\"message\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
//            return;
//        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        List<Map<String,Object>> courseList = this.imInterfaceManager.getClassTaskCourse(obj);
        Map m = new HashMap();
        if(courseList!=null&&courseList.size()>0){
            for(int i = 0;i<courseList.size();i++){
                List<Map<String,Object>> taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSEID").toString()));
                courseList.get(i).put("taskList",taskList);
            }
        }else{
            m.put("result","error");
            m.put("message","��ǰû��ר��");
        }

        m.put("result","success");
        m.put("message","�ɹ�");
        m.put("data",courseList);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }

    /**
     * ����С�����ҳ��
     * @param request
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params="m=getClassCalendar",method= RequestMethod.GET)
    public void getClassCalendar(HttpServletRequest request,HttpServletResponse response,ModelMap mp)throws Exception{
        JsonEntity je = new JsonEntity();
        String userid = request.getParameter("userid");
        String usertype=request.getParameter("usertype");
        String classid = request.getParameter("classid");
        String schoolid = request.getParameter("schoolid");
//        String timestamp = request.getParameter("timestamp");
//        String checkcode = request.getParameter("checkcode");
//        String md5key = classid+schoolid+timestamp;
//        String md5code = MD5_NEW.getMD5ResultImCode(md5key);
//        if(!checkcode.trim().equals(md5code)){
//            response.getWriter().print("{\"result\":\"error\",\"message\":\"��֤ʧ�ܣ��Ƿ���¼\"}");
//            return;
//        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        List<Map<String,Object>> courseList = this.imInterfaceManager.getClassTaskCourse(obj);
        Map m = new HashMap();
        Map m2 = new HashMap();
        if(courseList!=null&&courseList.size()>0){
           m2.put("courseList",courseList);
        }else{
            m.put("result","error");
            m.put("message","��ǰû��ר��");
        }
        m2.put("personTotalScore","350");
        m2.put("teamScore","130");
        m2.put("taskScore","150");
        m2.put("presenceScore","50");
        m2.put("smileScore","10");
        m2.put("illegalScore","10");
        m.put("result","success");
        m.put("message","�ɹ�");
        m.put("data",m2);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }
}
