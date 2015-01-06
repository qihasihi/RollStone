package com.school.ett;

import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.manager.ClassManager;
import com.school.manager.inter.IClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.util.MD5_NEW;
import jcore.jsonrpc.common.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by yuchunfan on 14-12-25.
 */
@Controller
@RequestMapping(value = "eliteClassEtt")
public class EliteClassInterfaceController extends BaseController<String> {
    private IClassManager classManager;
    private ITpCourseManager courseManager;
    public EliteClassInterfaceController(){
        this.classManager=this.getManager(ClassManager.class);
        this.courseManager=this.getManager(TpCourseManager.class);
    }

    @RequestMapping(params="m=getClass")
    public void getEliteClass(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String gradeId = request.getParameter("gradeId");
        String termId = request.getParameter("termId");
        String timestamp = request.getParameter("timestamp");
        String sign = request.getParameter("sign");
        JSONObject json = new JSONObject();
        List<ClassInfo> list = null;

        if(gradeId == null || gradeId.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        if(termId == null || termId.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        if(timestamp == null || timestamp.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        if(sign == null || sign.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        String md5 = MD5_NEW.getMD5Result(gradeId + termId + timestamp);
        if(!md5.equals(sign)) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        list = classManager.getClassByGradeTerm(Integer.valueOf(gradeId),Integer.valueOf(termId));

        if(list == null) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }

        JSONArray array = new JSONArray();
        for(ClassInfo cls : list) {
            JSONObject obj = new JSONObject();
            obj.put("classId", cls.getClassid());
            obj.put("subjectId", cls.getSubjectid());
            obj.put("className", cls.getClassname());
            obj.put("gradeId", gradeId);
            obj.put("termId", termId);
            array.put(obj);
        }

        json.put("type", "success");
        json.put("classList", array.toString());
        response.getWriter().println(URLEncoder.encode(json.toString(), "utf-8"));
    }

    @RequestMapping(params="m=getCourse")
    public void getEliteCourse(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String gradeId = request.getParameter("gradeId");
        String termId = request.getParameter("termId");
        String timestamp = request.getParameter("timestamp");
        String subjectId = request.getParameter("subjectId");
        String sign = request.getParameter("sign");
        JSONObject json = new JSONObject();
        List<TpCourseInfo> list = null;
        System.out.println("gradeId="+gradeId+"  termId="+termId);
        System.out.println("timestamp="+ timestamp +"  subjectId="+subjectId+"  sign="+sign);

        if(gradeId == null || gradeId.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        if(termId == null || termId.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }

        if(subjectId == null || subjectId.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        if(timestamp == null || timestamp.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        if(sign == null || sign.equals("")) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        String md5 = MD5_NEW.getMD5Result(gradeId + termId + subjectId + timestamp);
        System.out.println("接口生成MD5="+md5);
        if(!md5.equals(sign)) {
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        list = courseManager.getCourseByGradeTermSubject(Integer.valueOf(gradeId),Integer.valueOf(termId),Integer.valueOf(subjectId));
        if(list == null) {
            System.out.println("结果集列表为空");
            json.put("type","error");
            response.getWriter().println(json.toString());
            return;
        }
        System.out.println("结果集列表长度"+list.size());
        JSONArray array = new JSONArray();
        for(TpCourseInfo tci : list) {
            JSONObject obj = new JSONObject();
            obj.put("courseId", tci.getCourseid());
            obj.put("teacherName", tci.getTeachername());
            obj.put("courseName", tci.getCoursename());
            obj.put("subjectId", tci.getSubjectid());
            SimpleDateFormat sdf1 = new SimpleDateFormat("MM月dd日");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            String endTime = sdf2.format(tci.getEndtime());
            String beginTime = sdf2.format(tci.getBegintime());


            if(sdf2.parse(endTime).after(sdf2.parse(beginTime))) {
                obj.put("courseTime", sdf1.format(tci.getBegintime())+"---"+sdf1.format(tci.getEndtime()));
            }
            else {
                obj.put("courseTime", sdf1.format(tci.getBegintime()));
            }

            obj.put("gradeId", gradeId);
            obj.put("termId", termId);
            array.put(obj);
        }

        json.put("type", "success");
        json.put("courseList", array.toString());
        System.out.println("JSON==" + json.toString());
        response.getWriter().println(URLEncoder.encode(json.toString(), "utf-8"));
    }
}
