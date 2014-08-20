package com.school.control;

import com.school.entity.*;
import com.school.manager.SchoolManager;
import com.school.manager.inter.ISchoolManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping(value = "/tpuser")
public class TpUserController extends UserController {

    private ISchoolManager schoolManager;

    public TpUserController(){
        this.schoolManager=this.getManager(SchoolManager.class);
    }
    /**
     * 网校教务进入班级管理
     * @param request
     * @param response
     * @param mp
     * @return
     * @throws Exception
     */
    @RequestMapping(params = "toJWIndex", method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView toUserList(HttpServletRequest request,
                                   HttpServletResponse response, ModelMap mp) throws Exception {
        JsonEntity je=new JsonEntity();
        String schoolid=request.getParameter("schoolid");
        String schoolname=request.getParameter("schoolname");
        String userid=request.getParameter("userid");
        if(schoolid==null||schoolid.trim().length()<1||!UtilTool.isNumber(schoolid)){
            je.setMsg("Schoolid is empty!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        if(schoolname==null||schoolname.trim().length()<1){
            je.setMsg("Schoolname is empty!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        if(userid==null||userid.trim().length()<1||!UtilTool.isNumber(userid)){
            je.setMsg("Userid is empty!");
            response.getWriter().print(je.toJSON());
            return null;
        }
        List<Object>objList=null;
        StringBuilder sql=null;
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();

        //验证添加分校信息
        String totalSchoolUrl="localhost:8080/totalSchool/franchisedSchool?jwValidateSchool";
        String totalParams="schoolid="+schoolid+"&schoolname="+java.net.URLEncoder.encode(schoolname,"UTF-8");
        if(!sendValidateUserInfoTotalSchool(totalSchoolUrl,totalParams)){
            je.setMsg("Operate SchoolInfo Error...");
            response.getWriter().print(je.toJSON());
            return null;
        }



        UserInfo u=new UserInfo();
        u.setDcschoolid(Integer.parseInt(schoolid));
        u.setEttuserid(Integer.parseInt(userid));
        List<UserInfo>userList=this.userManager.getList(u,null);
        if(userList==null||userList.size()<1){
            //添加用户
            String userNextRef = UUID.randomUUID().toString();
            u.setRef(userNextRef);
            u.setPassword(schoolid+userid);
            u.setUsername("教务"+schoolid+userid);
            u.setStateid(0);
            u.setDcschoolid(Integer.parseInt(schoolid));
            sql = new StringBuilder();
            objList = this.userManager.getSaveSql(u, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加用户与身份关联信息
            String identityNextRef = UUID.randomUUID().toString();
            UserIdentityInfo ui = new UserIdentityInfo();
            ui.setRef(identityNextRef);
            ui.getUserinfo().setRef(userNextRef);
            ui.setIdentityname("教职工");
            sql = new StringBuilder();
            objList = this.userIdentityManager.getSaveSql(ui, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }


            TeacherInfo t = new TeacherInfo();
            t.setUserid(userNextRef);
            t.setTeachername("教务"+schoolid+userid);
            t.setTeachersex("男");

            sql = new StringBuilder();
            objList = this.teacherManager.getSaveSql(t, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加网校教务角色
            RoleUser jwru = new RoleUser();
            jwru.setRef(UUID.randomUUID().toString());
            jwru.getUserinfo().setRef(userNextRef);
            jwru.getRoleinfo().setRoleid(UtilTool._ROLE_WXJW_ID);

            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(jwru, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加默认教师角色
            String ruNextRef = UUID.randomUUID().toString();
            RoleUser ru = new RoleUser();
            ru.setRef(ruNextRef);
            ru.getUserinfo().setRef(userNextRef);
            ru.getRoleinfo().setRoleid(UtilTool._ROLE_TEACHER_ID);

            sql = new StringBuilder();
            objList = this.roleUserManager.getSaveSql(ru, sql);
            if (objList != null && sql != null) {
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }

            //添加教师角色默认权限
            RoleColumnRightInfo rc=new RoleColumnRightInfo();
            rc.setRoleid(UtilTool._ROLE_TEACHER_ID);
            List<RoleColumnRightInfo>rcList=this.roleColumnRightManager.getList(rc, null);

            if(rcList!=null&&rcList.size()>0){
                for (RoleColumnRightInfo roleColumnRightInfo : rcList) {
                    UserColumnRightInfo ucr=new UserColumnRightInfo();
                    ucr.setColumnid(roleColumnRightInfo.getColumnid());
                    ucr.setUserid(userNextRef);
                    ucr.setRef(this.userColumnRightManager.getNextId());
                    ucr.setColumnrightid(roleColumnRightInfo.getColumnrightid());
                    sql=new StringBuilder();
                    objList=this.userColumnRightManager.getSaveSql(ucr, sql);
                    if(objList!=null&&sql!=null){
                        sqlListArray.add(sql.toString());
                        objListArray.add(objList);
                    }
                }
            }
        }

        if(sqlListArray.size()>0&&sqlListArray.size()>0){
            if(this.userManager.doExcetueArrayProc(sqlListArray,objListArray)){
                //返回绑定成功数据
                UserInfo sel=new UserInfo();
                sel.setEttuserid(Integer.parseInt(userid));
                sel.setDcschoolid(Integer.parseInt(schoolid));
                List<UserInfo>ettUserList=this.userManager.getList(sel,null);
                if(ettUserList!=null&&ettUserList.size()>0){
                    JSONObject obj = new JSONObject();
                    obj.element("userid", ettUserList.get(0).getUserid());
                    obj.element("jid", ettUserList.get(0).getEttuserid());
                    obj.element("schoolid",ettUserList.get(0).getDcschoolid());
                    JSONArray array=new JSONArray();
                    array.add(obj);
                    //com.etiantian.unite.utils.UrlSigUtil.encodeUrl()
                }

            }else{
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
                response.getWriter().print(je.getAlertMsgAndBack());
                return null;
            }

        }

        //获取年级
        PageResult p=new PageResult();
        p.setOrderBy("g.grade_id desc");
        p.setPageNo(0);
        p.setPageSize(0);
        List<GradeInfo>gradeList=this.gradeManager.getList(null,p);
        //获取学科
        List<SubjectInfo>subjectList=this.subjectManager.getList(null,null);
        //班级类型
        List<DictionaryInfo>classType=this.dictionaryManager.getDictionaryByType("WX_CLASS_TYPE");

        mp.put("gradeList",gradeList);
        mp.put("subjectList",subjectList);
        mp.put("classType",classType);
        return new ModelAndView("/teachpaltform/ettClass/class-admin",mp);
    }


    public  boolean sendValidateUserInfoTotalSchool(String sendUrl,String params){
        if(sendUrl==null)return false;

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(sendUrl.toString());

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
           /* if(params!=null)
                httpConnection.setRequestProperty("Content-Length",
                        String.valueOf(params.length()));*/
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
            if(params!=null){
                outputStreamWriter.write(params);
                System.out.println("params:"+params);
            }
            /*if(jsondata!=null)
                outputStreamWriter.write(java.net.URLEncoder.encode(jsondata,"UTF-8"));*/

            outputStreamWriter.flush();
            outputStreamWriter.close();
            // objOutputStrm.flush();
            //objOutputStrm.close();
            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!未响应!");
            return false;
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
                System.out.println("异常错误!");
                return false;
            }
        }else if(code==HttpURLConnection.HTTP_NOT_FOUND){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return false;
        }else if(code==HttpURLConnection.HTTP_SERVER_ERROR){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return false;
        }
        String returnContent=null;
        try {
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //转换成JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        String type=jb.containsKey("type")?jb.getString("type"):"";
        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
        if(type!=null&&type.trim().toLowerCase().equals("success")){
            System.out.println(msg);
            return true;
        }else{
            System.out.println(msg);return false;
        }
    }
}

