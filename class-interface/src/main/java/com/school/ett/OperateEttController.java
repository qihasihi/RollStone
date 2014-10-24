package com.school.ett;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.control.base.BaseController;
import com.school.entity.ClassUser;
import com.school.entity.TermInfo;
import com.school.entity.UserInfo;
import com.school.manager.ClassUserManager;
import com.school.manager.TermManager;
import com.school.manager.UserManager;
import com.school.manager.inter.IClassUserManager;
import com.school.manager.inter.ITermManager;
import com.school.manager.inter.IUserManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ����ETT������ݵĴ洢���̡�
 * Created by zhengzhou on 14-10-21.
 */
@Controller
@RequestMapping(value="/operateEtt")
public class OperateEttController extends BaseController<String>{
    private IUserManager userManager;
    private IClassUserManager classUserManager;
    private ITermManager termManager;
    public OperateEttController(){
        this.userManager=this.getManager(UserManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.termManager=this.getManager(TermManager.class);
    }
    /**
     * ��֤�û���(1���ǿ���֤  2���Ƿ��ظ�)
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params="m=validateUserName")
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
            //�õ����û����꼶
            UserInfo u = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
            // ////////////////////////////�����洢
            int currentClsCode=0;
            // �����ѧ���������꼶��ID ��ʦ����
            if(this.validateRole(request,UtilTool._ROLE_STU_ID)){
                    TermInfo tm=this.termManager.getAutoTerm();
                    String uidRef=u.getRef();
                    ClassUser cu = new ClassUser();
                    cu.setUserid(uidRef);
                    // ��ѯ��ǰ���꼶  �����ǰѧ��û�����ݣ��򲻴���ѧ��
                    cu.setYear(tm.getYear().trim());
                    List<ClassUser> cuList = this.classUserManager.getList(cu, null);
                    if(cuList!=null&&cuList.size()>0){
                        String clsGrade=cuList.get(0).getClassgrade();
                        if (clsGrade != null) {
                            if (clsGrade.trim().equals("����")
                                    || clsGrade.trim().equals("�������꼶")) {
                                    currentClsCode=1;
                            } else if (clsGrade.trim().equals("�߶�")
                                    || clsGrade.trim().equals("���ж��꼶")) {
                                 currentClsCode = 2;
                            } else if (clsGrade.trim().equals("��һ")
                                    || clsGrade.trim().equals("����һ�꼶")) {
                                 currentClsCode = 3;
                            } else if (clsGrade.trim().equals("����")
                                    || clsGrade.trim().equals("�������꼶")) {
                                currentClsCode= 4;
                            } else if (clsGrade.trim().equals("����")
                                    || clsGrade.trim().equals("���ж��꼶")) {
                                currentClsCode= 5;
                            } else if (clsGrade.trim().equals("��һ")
                                    || clsGrade.trim().equals("����һ�꼶")) {
                                currentClsCode= 6;
                            } else if (clsGrade.trim().equals("Сѧ���꼶")
                                    || clsGrade.trim().equals("С��")) {
                                currentClsCode= 7;
                            } else if (clsGrade.trim().equals("Сѧ���꼶")
                                    || clsGrade.trim().equals("С��")) {
                                currentClsCode= 8;
                            } else if (clsGrade.trim().equals("Сѧ���꼶")
                                    || clsGrade.trim().equals("С��")) {
                                currentClsCode= 9;
                            } else if (clsGrade.trim().equals("Сѧ���꼶")
                                    || clsGrade.trim().equals("С��")) {
                                currentClsCode= 10;
                            }
                    }
                }
            }
            //******************************ע���ʺ�-------------------------/
            //������ã�����ӿ�����������
            HashMap<String,String> paraMap=new HashMap<String, String>();
            paraMap.put("dcSchoolId",this.logined(request).getDcschoolid()+"");
            paraMap.put("dcUserId",this.logined(request).getUserid()+"");
            paraMap.put("userName",userName);
            paraMap.put("password",password);
            paraMap.put("email",email);
//            if(this.validateRole(request,UtilTool._ROLE_STU_ID))
            paraMap.put("gradeId",currentClsCode+"");
            paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?"1":"2");
            paraMap.put("timestamp",new Date().getTime()+"");
            // paraMap.put("identity",this.validateRole(request,UtilTool._ROLE_STU_ID)?1:2);
            paraMap.put("sign", UrlSigUtil.makeSigSimple("registerax.do",paraMap));
            String urlstr=UtilTool.utilproperty.getProperty("REGISTER_ETT_ACCOUNT");
            JSONObject jsonObject=UtilTool.sendPostUrl(urlstr,paraMap,"UTF-8");
            //1:�ɹ�
            if(jsonObject!=null&&jsonObject.containsKey("result")&& jsonObject.getInt("result")==1){
                if(jsonObject.containsKey("data")&&jsonObject.get("data")!=null){
                    int jid=jsonObject.getJSONObject("data").getInt("jid");
                    UserInfo updateU=new UserInfo();
                    updateU.setEttuserid(jid);
                    updateU.setUserid(this.logined(request).getUserid());
                    updateU.setRef(this.logined(request).getRef());
                    if(userManager.doUpdate(updateU)){
                        jsonEntity.setType("success");
                        jsonEntity.setMsg("ע��ɹ�!");
                        response.getWriter().println(jsonEntity.toJSON());return;
                    }
                }
                jsonEntity.setType("error");
                jsonEntity.setMsg("ע��ɹ�!����ʧ��!");
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
    public static JsonEntity validateEttUserNameHas(HttpServletRequest request,Boolean isstu) throws Exception{
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
        String encoding="UTF-8";
        String urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETT_TEA_USERNAME_HAS_URL");
        if(isstu){
            urlstr=UtilTool.utilproperty.getProperty("VALIDATE_ETT_STU_USERNAME_HAS_URL");
            paraMap.put("userName",userName);
        }else
            paraMap.put("username",java.net.URLEncoder.encode(userName,"UTF-8"));
        JSONObject jsonObject=UtilTool.sendPostUrlNotEncoding(urlstr,paraMap,encoding);
        //1:�ɹ�
        if(jsonObject!=null&&jsonObject.containsKey("code")&& jsonObject.getInt("code")==1){
            jsonEntity.setType("success");
            jsonEntity.setMsg("��֤�ɹ�!");
        }else{
            jsonEntity.setType("error");
            jsonEntity.setMsg(java.net.URLDecoder.decode(jsonObject.getString("msg"),"UTF-8"));
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
        int uNameSize=checkWordSize(paramMap.get("userName").toString().trim());
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
        if(!UtilTool.matchingText("[\\w[a-zA-Z_]]+",paramMap.get("password")))
            return "�������ͬʱ�����ֺ���ĸ!�����";
        if(paramMap.containsKey("email")){
            //��֤����
            String email=paramMap.get("email");
            if(!UtilTool.matchingText("^([\\w-\\.]{4,18})@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([\\w-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$",email))
                return "�����ʽ����ȷ�������";
        }
        return "TRUE";
    }

    /**
     * ͳ���ַ������ַ�����(����2���ַ�)
     * @param str
     * @return
     */
    private static int checkWordSize(String str) {
        if(str == null || str.length() == 0) {
            return 0;
        }
        int count = 0;
        char[] chs = str.toCharArray();
        for(int i = 0; i < chs.length; i++) {
            count += (chs[i] > 0xff) ? 2 : 1;
        }
        return count;
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
