package com.school.ett;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.UserInfo;
import com.school.manager.UserManager;
import com.school.manager.inter.IUserManager;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import jcore.jsonrpc.common.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
//        String sign = UrlSigUtil.makeSigSimple("ETT-SCHOOL-MODEL", map, UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString());
//        System.out.println("ettKey"+key+"   key:"+sign);
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

        if(this.userManager.doExcetueArrayProc(sqlArrayList,objArrayList))
            returnJO.put("type","success");
        else
            returnJO.put("msg","绑定失败!原因：未知，可能执行存储失败!");
        response.getWriter().println(returnJO.toString());
    }

}

