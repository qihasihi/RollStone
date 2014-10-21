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
 * ����ETT������ݵĴ洢���̡�
 * Created by zhengzhou on 14-10-21.
 */
public class OperateEttController extends BaseController{
    /**
     * ��֤�û���(1���ǿ���֤  2���Ƿ��ظ�)
     * @param request
     * @param response
     * @throws Exception
     */
    public void validateUserName(HttpServletRequest request,HttpServletResponse response) throws Exception{
        response.getWriter().println(OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID)).toJSON());
    }


    /**
     * ע����У�ʺ�
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doRegisterEttAccount")
    public void doRegisterEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //��֤����
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
            //���¼���û����Ƿ����
            JsonEntity je=OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //******************************ע���ʺ�-------------------------/
            //������ã�����ӿ�����������
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
            //1:�ɹ�
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                jsonEntity.setType("success");
                jsonEntity.setMsg("�޸ĳɹ�!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("����ԭ��δ֪!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }

    /**
     * ע����У�ʺ�
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doUpdateEttAccount")
    public void doUpdateEttAccount(HttpServletRequest request,HttpServletResponse response)throws Exception{
        //��֤����
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
            //���¼���û����Ƿ����
            JsonEntity je=OperateEttControllerUtil.validateEttUserNameHas(request,this.validateRole(request,UtilTool._ROLE_STU_ID));
            if(!je.getType().trim().equals("success")){
                response.getWriter().println(je.toJSON());return;
            }
            //******************************ע���ʺ�-------------------------/
            //������ã�����ӿ�����������
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
            //1:�ɹ�
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                jsonEntity.setType("success");
                jsonEntity.setMsg("ע��ɹ�!");
            }else{
                jsonEntity.setType("error");
                jsonEntity.setMsg(jsonObject.getString("msg"));
            }
        }else{
            jsonEntity.setMsg("����ԭ��δ֪!");
        }
        response.getWriter().println(jsonEntity.toJSON());
    }
    public static void main(String[] args){
        String uname="a332_qqcom����";
        String regex = "[a-zA-Z0-9_\\u4e00-\\u9fa5]+";

        System.out.println(matchingText(regex, uname));

    }
    /**
     * ������֤ (��UtilTool����)
     *
     * @author ֣��(2010-06-31 ����02:20:16)
     * @param expression
     * @param text
     * @return boolean
     */
    public static boolean matchingText(String expression, String text) {
        boolean bool = false;
        if (expression != null && !"".equals(expression) && text != null
                && !"".equals(text.toLowerCase())) {
            Pattern p = Pattern.compile(expression); // ������ʽ
            Matcher m = p.matcher(text.toLowerCase()); // �������ַ���
            bool = m.matches();
        }
        return bool;
    }

}


/**
 * ������
 */
class OperateEttControllerUtil{
    /***********************---------------��֤������Ϣ------------------*************************/
    /**
     * ��֤�û����Ƿ����
     * @param request
     * @return
     */
    public static JsonEntity validateEttUserNameHas(HttpServletRequest request,Boolean isstu){
        String userName=request.getParameter("userName");
        String oldUserName=request.getParameter("oldUserName");
        JsonEntity jsonEntity=new JsonEntity();
        if(userName==null||userName.trim().length()<1){
            jsonEntity.setMsg("�û�������Ϊ�գ������������!");
          return jsonEntity;
        }
        if(oldUserName!=null&&userName.trim().equals(oldUserName.trim())){
            jsonEntity.setMsg("��֤�ɹ�!");
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
        //1:�ɹ�
        if(jsonObject!=null&&jsonObject.containsKey("code")&& jsonObject.getInt("code")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("��֤�ɹ�!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(jsonObject.getString("msg"));
        }
        return jsonEntity;
    }

    /**
     * ��֤������Ϣ��
     * @param request
     * @return
     * @throws Exception
     */
    public static String validateRegisterParam(HttpServletRequest request) throws Exception{
        if(!ValidateRequestParam(request)){
            return "��������Ϊ��!���������ݺ����µ��!";
        }
        HashMap<String,String> paramMap=getRequestParam(request);
        if(!paramMap.containsKey("userName"))
            return "û�м�⵽�û���!��ˢ�º�����";
        if(!paramMap.containsKey("password"))
            return "û�м�⵽����!��ˢ�º�����";
        ///////////////////��֤�û���
        //λ��6--12�ַ���1��������2���ַ���
        int uNameSize=paramMap.get("userName").toString().trim().length();
        //�û�������6���ֻ����12����
        if(uNameSize<6||uNameSize>12)
            return "�û�����������6���ֻ����12����!�����";
        //�û���ֻ�����֡���ĸ����Сд�����»��ߡ������ַ�!
        // �����пո�
        if(!UtilTool.matchingText("[a-zA-Z0-9_\\u4e00-\\u9fa5]+",paramMap.get("userName")))
            return "�û���ֻ�����֡���ĸ����Сд�����»��ߡ������ַ�!�����";
        //////////////////��֤����
        //λ��6--12�ַ�
        int passSize=paramMap.get("password").trim().length();
        if(passSize<6||passSize>12)
            return "���벻������6���ֻ����12����!�����";
        //ֻ�����֡���ĸ����Сд�����»��ߣ��ұ���ͬʱ�����ֺ���ĸ
        if(!UtilTool.matchingText("[a-zA-Z0-9_]+",paramMap.get("password")))
            return "����ֻ�����֡���ĸ����Сд�����»��ߣ��ұ���ͬʱ�����ֺ���ĸ!�����";
        //��֤�Ƿ��������
        if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
            return "�������ͬʱ�����ֺ���ĸ!�����";
        //��֤�Ƿ�����ַ�
        if(!UtilTool.matchingText("[\\w[0-9]]+",paramMap.get("password")))
            return "�������ͬʱ�����ֺ���ĸ!�����";
        if(paramMap.containsKey("email")){
            //��֤����
            String email=paramMap.get("email");
            if(!UtilTool.matchingText("^([\\w-\\.]{4,18})@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",email))
                return "�����ʽ����ȷ�������";
        }
        return "TRUE";
    }


 /*******-------------------------------ע�� ��У�ʺ�-------------------------*******/
    /**
     * ע��Ett�ʺ�
     * @param dcSchoolId ��У��УID
     * @param dcUserId  ��У�û�ID
     * @param userName  ע����û���
     * @param password  ע�������
     * @param email     ע��ʱ�õ�����
     * @param identity  ��� 1=ѧ�� 2=��ʦ
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
     * ע��Ettѧ���ʺ�
     * @param dcSchoolId ��У��УID
     * @param dcUserId ��У�û�ID
     * @param userName ע����û���
     * @param password ע�������
     * @param email    ע��ʱ�õ�����
     * @return
     */
    public static boolean registerStuEttAccount( final Integer dcSchoolId,
                                                 final  Integer dcUserId,
                                                 final  String userName,      //��ʦ�����޸��û��� ѧ�����Ի��û���
                                                 final String password,
                                                 final String email){
        return registerEttAccount(dcSchoolId,dcUserId,userName,password,email,1);
    }
    /**
     * ע��Ett��ʦ�ʺ�
     * @param dcSchoolId ��У��УID
     * @param dcUserId ��У�û�ID
     * @param userName ע����û���
     * @param password ע�������
     * @param email    ע��ʱ�õ�����
     * @return
     */
    public static boolean registerTeaEttAccount(final Integer dcSchoolId,
                                                  final Integer dcUserId,
                                                  final String userName,
                                                  final String password,
                                                  final String email){
        return registerEttAccount(dcSchoolId,dcUserId,userName,password,email,2);
    }
    /*******-------------------------------�޸� ��У�ʺ�-------------------------*******/
    /**
     * �޸���У�ʺ���Ϣ
     * @param jid        �޸ĵ�JID
     * @param dcSchoolId    ��УID
     * @param userName      �û���
     * @param password      ����
     * @param identity      ��� 1=ѧ�� 2=��ʦ
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
     * �޸���Уѧ���ʺ���Ϣ
     * @param jid        �޸ĵ�JID
     * @param dcSchoolId    ��УID
     * @param userName      �û���
     * @param password      ����
     * @return
     */
    public static boolean updateStuEttAccount(final Integer jid,
                                              final Integer dcSchoolId,
                                              final String userName,
                                              final String password){
        return updateEttAccount(jid,dcSchoolId,userName,password,1);
    }
    /**
     * �޸���У��ʦ�ʺ���Ϣ(userName�û���)
     * @param jid        �޸ĵ�JID
     * @param dcSchoolId    ��УID
     * @param userName �û���
     * @param password      ����
     * @return
     */
    public static boolean updateTeaEttAccount(final Integer jid,
                                              final Integer dcSchoolId,
                                             final String userName, //��ʦ�����޸��û��� ѧ�����Ի��û���
                                              final String password){
        return updateEttAccount(jid,dcSchoolId,userName,password,2);
    }



    /////////////���߷���
    /**
     * ��֤RequestParams��ز���
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
     * ��֤RequestParams��ز���
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
