package com.school.ett;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.UserInfo;
import com.school.manager.UserManager;
import com.school.manager.inter.IUserManager;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * 提供给ETIANTIAN网站的接口
 * Created by zhengzhou on 14-8-16.
 */
@Controller
@RequestMapping("/toEttFace")
public class ToEttInterfaceController extends BaseController<String> {
    private IUserManager userManager;
    public ToEttInterfaceController(){
        userManager=this.getManager(UserManager.class);
    }

    /**
     * www.etiantian.com调用分校进行绑定
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=bindJID",method= {RequestMethod.POST,RequestMethod.GET})
    public void bindJid(HttpServletRequest request,HttpServletResponse response)throws Exception{
        System.out.println("bindJId----1");
        String jId = request.getParameter("jId");
        String userId=request.getParameter("userId");
        String schoolid = request.getParameter("schoolId");
        String timestamp = request.getParameter("timeStamp");
        String key = request.getParameter("sign");
        JSONObject returnJO=new JSONObject();
        returnJO.put("type","error");
        if(jId==null||userId==null||schoolid==null||timestamp==null||key==null
                ||jId.toString().trim().length()<1
                ||userId.toString().trim().length()<1
                ||schoolid.toString().trim().length()<1
                ||timestamp.toString().trim().length()<1
                ||key.toString().trim().length()<1){
            System.out.println("bindJId----2");
            returnJO.put("msg",UtilTool.msgproperty.getProperty("PARAM_ERROR").toString());
            response.getWriter().println(returnJO.toString());return;
        }
        //验证是否在三分钟内
        Long ct=Long.parseLong(timestamp.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//大于三分钟
              returnJO.put("msg","响应超时!");
        }
        HashMap<String,String> map = new HashMap();
        map.put("jId",jId);
        map.put("userId",userId);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
        Boolean b = UrlSigUtil.verifySigSimple("bindJID", map, key);
        if(!b){
            returnJO.put("msg","验证失败，非法登录");
            response.getWriter().println(returnJO.toString());
            return;
        }
       StringBuilder sqlbuilder=new StringBuilder();
        List<String>sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        //查询该JID是否已经绑定
        UserInfo userInfo=new UserInfo();
        userInfo.setEttuserid(Integer.parseInt(jId.trim()));
        List<Object> objList=this.userManager.getUpdateEttUserByEttUserIdSql(Integer.parseInt(jId.trim()),sqlbuilder);
        if(sqlbuilder!=null){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        //得到当前分校下的该用户
        userInfo=new UserInfo();
        userInfo.setUserid(Integer.parseInt(userId));
        userInfo.setDcschoolid(Integer.parseInt(schoolid));
        System.out.println("bindJId----3");
        List<UserInfo> userList=this.userManager.getList(userInfo,presult);
        if(userList==null||userList.size()<1){
            returnJO.put("msg","在该分校中没有发现该学生!分校:"+schoolid+" 学生:"+userId);
            response.getWriter().println(returnJO.toString());return;
        }
        //进行修改
        userInfo.setRef(userList.get(0).getRef());
        userInfo.setEttuserid(Integer.parseInt(jId.trim()));
        sqlbuilder=new StringBuilder();
        objList=this.userManager.getUpdateSql(userInfo,sqlbuilder);
        if(sqlbuilder!=null){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        System.out.println("bindJId----4");
        if(this.userManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
            System.out.println("bindJId----5");
             //绑定成功后，再调头像回传
            //记录状态，信息
            String type="success";
            String msg=null;
            //查询当前用户的信息
            userInfo=new UserInfo();
            userInfo.setUserid(Integer.parseInt(userId));
            userInfo.setDcschoolid(Integer.parseInt(schoolid));
            List<UserInfo> uList=this.userManager.getList(userInfo,null);
            if(uList!=null&&uList.size()>0&&uList.get(0)!=null&&uList.get(0).getHeadimage()!=null&&uList.get(0).getHeadimage().trim().length()>0){
                int returnType=HeadImgToEtt(uList.get(0));
                //1:成功        2:信息不完整，拒绝操作       3:图片不存在了    4：信息完整，操作成功，但执行返回失败
//                if(returnType==3){
//                    type="error";
//                    msg="同步头像信息失败，原因：头像图片不存在!";
//                }else if(returnType==4){
//                    type="error";
//                    msg="同步头像信息失败!";
//                }
            }
//            System.out.println("imghead----6");
//            System.out.println("imghead----"+msg);
            returnJO.put("type", type);
//            if(type=="error"&&msg!=null)
//                returnJO.put("msg",msg);


            //将当前的JID存入session中jid
            UserInfo sessionU=this.logined(request);
            sessionU.setEttuserid(Integer.parseInt(jId));
            this.setAttributeSession(request, "CURRENT_USER", sessionU);
        }else
            returnJO.put("msg","绑定失败!原因：未知，可能执行存储失败!");
        System.out.println("imghead----"+returnJO.get("msg"));
        response.getWriter().println(returnJO.toString());
    }
}

