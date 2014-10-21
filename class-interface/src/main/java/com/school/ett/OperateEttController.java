package com.school.ett;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 操作ETT相关数据的存储过程。
 * Created by zhengzhou on 14-10-21.
 */
public class OperateEttController extends BaseController{
    /**
     * 验证用户名(1、非空验证  2、是否重复)
     * @param request
     * @param response
     * @throws Exception
     */
    public void validateUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.getWriter().println(OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID)).toJSON());
    }


    /**
     * 注册网校帐号
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doRegisterEttAccount")
    public void doRegisterEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //验证参数
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        JsonEntity jsonEntity=new JsonEntity();
        String msg=OperateEttControllerUtil.validateRegisterParam(request);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonEntity.setMsg(msg);
                response.getWriter().println(jsonEntity.toJSON());return;
            }
            //重新检测用户名是否可用
            JsonEntity je=OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //******************************注册帐号-------------------------/
            //如果可用，则调接口添加相关数据
            HashMap<String,String> paraMap=new HashMap<String, String>();
            paraMap.put("dcSchoolId",this.logined(request).getDcschoolid()+"");
            paraMap.put("dcUserId",this.logined(request).getUserid()+"");
            paraMap.put("userName",userName);
            paraMap.put("password",password);
            paraMap.put("email",email);
            paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?"1":"2");
            paraMap.put("timestamp",new Date().getTime()+"");
            // paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?1:2);
            paraMap.put("sign", UrlSigUtil.makeSigSimple("",paraMap));
            String urlstr=UtilTool.utilproperty.getProperty("REGISTER_ETT_ACCOUNT");
            JSONObject jsonObject=UtilTool.sendPostUrl(urlstr,paraMap,"UTF-8");
            //1:成功
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                jsonEntity.setType("success");
                jsonEntity.setMsg("修改成功!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("错误，原因：未知!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**
     * 注册网校帐号
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doUpdateEttAccount")
    public void doUpdateEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //验证参数
        String userName=request.getParameter("userName");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        JsonEntity jsonEntity=new JsonEntity();
        String msg=OperateEttControllerUtil.validateRegisterParam(request);
        if(msg!=null&&msg.trim().length()>0){
            if(!msg.trim().equals("TRUE")){
                jsonEntity.setMsg(msg);
                response.getWriter().println(jsonEntity.toJSON());return;
            }
            //重新检测用户名是否可用
            JsonEntity je=OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //******************************注册帐号-------------------------/
            //如果可用，则调接口添加相关数据
            HashMap<String,String> paraMap=new HashMap<String, String>();
            paraMap.put("dcSchoolId",this.logined(request).getDcschoolid()+"");
            paraMap.put("dcUserId",this.logined(request).getUserid()+"");
            paraMap.put("userName",userName);
            paraMap.put("password",password);
            paraMap.put("email",email);
            paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?"1":"2");
            paraMap.put("timestamp",new Date().getTime()+"");
            // paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?1:2);
            paraMap.put("sign", UrlSigUtil.makeSigSimple("",paraMap));
            String urlstr=UtilTool.utilproperty.getProperty("MODIFY_ETT_ACCOUNT");
            JSONObject jsonObject=UtilTool.sendPostUrl(urlstr,paraMap,"UTF-8");
            //1:成功
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                jsonEntity.setType("success");
                jsonEntity.setMsg("注册成功!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("错误，原因：未知!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }
    public static void main(String[] args){
        String uname="a332_qqcom国家";
        String regex = "[a-zA-Z0-9_\\u4e00-\\u9fa5]+";

        System.out.println(matchingText(regex, uname));

    }
    /**
     * 正则验证 (在UtilTool类中)
     *
     * @author 郑舟(2010-06-31 下午02:20:16)
     * @param expression
     * @param text
     * @return boolean
     */
    public static boolean matchingText(String expression, String text) {
        boolean bool = false;
        if (expression != null && !"".equals(expression) && text != null
                && !"".equals(text.toLowerCase())) {
            Pattern p = Pattern.compile(expression); // 正则表达式
            Matcher m = p.matcher(text.toLowerCase()); // 操作的字符串
            bool = m.matches();
        }
        return bool;
    }

}


/**
 * 工具类
 */
class OperateEttControllerUtil{
    /***********************---------------验证参数信息------------------*************************/
    /**
     * 验证用户名是否存在
     * @param request
     * @return
     */
    public static JsonEntity validateEttUserNameHas(HttpServletRequest request,Boolean isstu){
        String userName=request.getParameter("userName");
        String oldUserName=request.getParameter("oldUserName");
        JsonEntity jsonEntity=new JsonEntity();
        if(userName==null||userName.trim().length()<1){
            jsonEntity.setMsg("用户名不能为空，请输入后重试!");
          return jsonEntity;
        }
        if(oldUserName!=null&&userName.trim().equals(oldUserName.trim())){
            jsonEntity.setMsg("验证成功!");
            jsonEntity.setType("success");
            return jsonEntity;
        }
        HashMap<String,String> paraMap=new HashMap<String, String>();
        paraMap.put("username",userName);
        String urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETT_TEA_USERNAME_HAS_URL");
        if(isstu){
            urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETT_STU_USERNAME_HAS_URL");
            paraMap.put("userName",userName);
        }
        JSONObject jsonObject=UtilTool.sendPostUrl(urlstr,paraMap,"UTF-8");
        //1:成功
        if(jsonObject!=null&&jsonObject.containsKey("code")&& jsonObject.getInt("code")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("验证成功!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(jsonObject.getString("msg"));
        }
        return jsonEntity;
    }

    /**
     * 验证参数信息。
     * @param request
     * @return
     * @throws Exception
     */
    public static String validateRegisterParam(HttpServletRequest request) throws Exception{
        if(!ValidateRequestParam(request)){
            return "参数不能为空!请输入数据后，重新点击!";
        }
        HashMap<String,String> paramMap=getRequestParam(request);
        if(!paramMap.containsKey("userName"))
            return "没有检测到用户名!请刷新后重试";
        if(!paramMap.containsKey("password"))
            return "没有检测到密码!请刷新后重试";
        ///////////////////验证用户名
        //位数6--12字符（1个汉字算2个字符）
        int uNameSize=paramMap.get("userName").toString().trim().length();
        //用户名少于6个字或多于12个字
        if(uNameSize<6||uNameSize>12)
            return "用户名不能少于6个字或多于12个字!请更改";
        //用户名只含数字、字母（大小写）、下划线、汉字字符!
        // 不能有空格
        if(!UtilTool.matchingText("[a-zA-Z0-9_\\u4e00-\\u9fa5]+",paramMap.get("userName")))
            return "用户名只含数字、字母（大小写）、下划线、汉字字符!请更改";
        //////////////////验证密码
        //位数6--12字符
        int passSize=paramMap.get("password").trim().length();
        if(passSize<6||passSize>12)
            return "密码不能少于6个字或多于12个字!请更改";
        //只含数字、字母（大小写）、下划线，且必须同时有数字和字母
        if(!UtilTool.matchingText("[a-zA-Z0-9_]+",paramMap.get("password")))
            return "密码只含数字、字母（大小写）、下划线，且必须同时有数字和字母!请更改";
        //验证是否存在数字
        if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
            return "密码必须同时有数字和字母!请更改";
        //验证是否存在字符
        if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
            return "密码必须同时有数字和字母!请更改";
        if(paramMap.containsKey("email")){
            //验证邮箱
            String email=paramMap.get("email");
            if(!UtilTool.matchingText("^([\\w-\\.]{4,18})@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",email))
                return "邮箱格式不正确，请更改";
        }
        return "TRUE";
    }


 /*******-------------------------------注册 网校帐号-------------------------*******/
    /**
     * 注册Ett帐号
     * @param dcSchoolId 数校分校ID
     * @param dcUserId  数校用户ID
     * @param userName  注册的用户名
     * @param password  注册的密码
     * @param email     注册时用的邮箱
     * @param identity  身份 1=学生 2=老师
     * @return
     */
    private static boolean registerEttAccount(
            final  Integer dcSchoolId,
            final Integer dcUserId,
            final String userName,
            final String password,
            final String email,
            final int   identity){
        return true;
    }

    /**
     * 注册Ett学生帐号
     * @param dcSchoolId 数校分校ID
     * @param dcUserId 数校用户ID
     * @param userName 注册的用户名
     * @param password 注册的密码
     * @param email    注册时用的邮箱
     * @return
     */
    public static boolean registerStuEttAccount( final Integer dcSchoolId,
                                                 final  Integer dcUserId,
                                                 final  String userName,      //教师不能修改用户名 学生可以换用户名
                                                 final String password,
                                                 final String email){
        return registerEttAccount(dcSchoolId,dcUserId,userName,password,email,1);
    }
    /**
     * 注册Ett教师帐号
     * @param dcSchoolId 数校分校ID
     * @param dcUserId 数校用户ID
     * @param userName 注册的用户名
     * @param password 注册的密码
     * @param email    注册时用的邮箱
     * @return
     */
    public static boolean registerTeaEttAccount(final Integer dcSchoolId,
                                                  final Integer dcUserId,
                                                  final String userName,
                                                  final String password,
                                                  final String email){
        return registerEttAccount(dcSchoolId,dcUserId,userName,password,email,2);
    }
    /*******-------------------------------修改 网校帐号-------------------------*******/
    /**
     * 修改网校帐号信息
     * @param jid        修改的JID
     * @param dcSchoolId    分校ID
     * @param userName      用户名
     * @param password      密码
     * @param identity      身份 1=学生 2=老师
     * @return
     */
    private static boolean updateEttAccount(final Integer jid,
                                             final Integer dcSchoolId,
                                             final String userName,
                                             final String password,
                                             final int   identity){
        return true;
    }
    /**
     * 修改网校学生帐号信息
     * @param jid        修改的JID
     * @param dcSchoolId    分校ID
     * @param userName      用户名
     * @param password      密码
     * @return
     */
    public static boolean updateStuEttAccount(final Integer jid,
                                              final Integer dcSchoolId,
                                              final String userName,
                                              final String password){
        return updateEttAccount(jid,dcSchoolId,userName,password,1);
    }
    /**
     * 修改网校教师帐号信息(userName用户名)
     * @param jid        修改的JID
     * @param dcSchoolId    分校ID
     * @param userName 用户名
     * @param password      密码
     * @return
     */
    public static boolean updateTeaEttAccount(final Integer jid,
                                              final Integer dcSchoolId,
                                             final String userName, //教师不能修改用户名 学生可以换用户名
                                              final String password){
        return updateEttAccount(jid,dcSchoolId,userName,password,2);
    }



    /////////////工具方法
    /**
     * 验证RequestParams相关参数
     * @param request
     * @return
     * @throws Exception
     */
    public static Boolean ValidateRequestParam(HttpServletRequest request) throws Exception{
        Enumeration eObj=request.getParameterNames();
        boolean returnBo=true;
        if(eObj!=null){
            while(eObj.hasMoreElements()){
                Object obj=eObj.nextElement();
                if(obj==null||obj.toString().trim().length()<1||request.getQueryString().toString().equals(obj))
                    continue;

                Object val=request.getParameter(obj.toString());
                if(val==null||val.toString().trim().length()<1){
                    returnBo=!returnBo;
                    break;
                }
            }
        }

        return returnBo;
    }
    /**
     * 验证RequestParams相关参数
     * @param request
     * @return
     * @throws Exception
     */
    public static HashMap<String,String> getRequestParam(HttpServletRequest request) throws Exception{
        Enumeration eObj=request.getParameterNames();
        HashMap<String,String> returnMap=null;
        if(eObj!=null){
            returnMap=new HashMap<String, String>();
            while(eObj.hasMoreElements()){
                Object obj=eObj.nextElement();
                if(obj==null||obj.toString().trim().length()<1||obj.toString().trim().equals("m")||obj.toString().equals(request.getQueryString()))
                    continue;
                Object val=request.getParameter(obj.toString());
                returnMap.put(obj.toString(),val.toString());
            }
        }
        return returnMap;
    }



}
