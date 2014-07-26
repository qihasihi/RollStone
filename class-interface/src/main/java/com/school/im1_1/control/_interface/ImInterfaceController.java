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
     * 进入小组管理页面
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
//            response.getWriter().print("{\"result\":\"error\",\"message\":\"验证失败，非法登录\"}");
//            return;
//        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setUserid(Integer.parseInt(userid));
        obj.setUsertype(Integer.parseInt(usertype));
        List<Map<String,Object>> list = this.imInterfaceManager.getStudyModule(obj);
        StringBuilder sb = new StringBuilder();
        if(list!=null&&list.size()>0){
            sb.append("{\"result\":\"success\"");
            sb.append(",\"msg\":\"成功\"");
            sb.append(",\"data\":{\"classes\":[");
            for(int i = 0;i<list.size();i++){
                sb.append("{");
                sb.append("\"classId\":\""+list.get(i).get("CLASS_ID")+"\"");
                sb.append(",\"className\":\""+list.get(i).get("CLASS_NAME")+"\"");
                sb.append(",\"notifyNum\":\""+list.get(i).get("UNCOMPLETENUM")+"\"");
                sb.append("}");
                if(i!=list.size()-1){
                    sb.append(",");
                }
            }
            sb.append("]");
            sb.append(",\"activityNotifyNum\":\"12\"");
            sb.append("}");
            sb.append("}");
        }else{
            sb.append("{\"result\":\"error\",\"message\":\"当前用户没有学习目录，请联系管理员\"}");
        }
        response.getWriter().print(sb.toString());
    }

    /**
     * 进入小组管理页面
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
//            response.getWriter().print("{\"result\":\"error\",\"message\":\"验证失败，非法登录\"}");
//            return;
//        }
        ImInterfaceInfo obj = new ImInterfaceInfo();
        obj.setSchoolid(Integer.parseInt(schoolid));
        obj.setClassid(Integer.parseInt(classid));
        List<Map<String,Object>> courseList = this.imInterfaceManager.getClassTaskCourse(obj);
        StringBuilder sb = new StringBuilder();
        if(courseList!=null&&courseList.size()>0){
            for(int i = 0;i<courseList.size();i++){
                List<Map<String,Object>> taskList = this.imInterfaceManager.getClassTaskTask(Long.parseLong(courseList.get(i).get("COURSE_ID").toString()));
                courseList.get(i).put("taskList",taskList);
            }
        }else{
            sb.append("{\"result\":\"error\",\"message\":\"当前用户没有学习目录，请联系管理员\"}");
        }
        Map m = new HashMap();
        m.put("result","success");
        m.put("message","成功");
        m.put("courses",courseList);
        JSONObject object = JSONObject.fromObject(m);
        response.getWriter().print(object.toString());
    }
}
