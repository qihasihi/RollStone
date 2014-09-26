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
 * �ṩ��ETIANTIAN��վ�Ľӿ�
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
     * www.etiantian.com���÷�У���а�
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
        //��֤�Ƿ�����������
        Long ct=Long.parseLong(timestamp.toString());
        Long nt=new Date().getTime();
        double d=(nt-ct)/(1000*60);
        if(d>3){//����������
              returnJO.put("msg","��Ӧ��ʱ!");
        }
        HashMap<String,String> map = new HashMap();
        map.put("jId",jId);
        map.put("userId",userId);
        map.put("schoolId",schoolid);
        map.put("timeStamp",timestamp);
        Boolean b = UrlSigUtil.verifySigSimple("bindJID", map, key);
        if(!b){
            returnJO.put("msg","��֤ʧ�ܣ��Ƿ���¼");
            response.getWriter().println(returnJO.toString());
            return;
        }
       StringBuilder sqlbuilder=new StringBuilder();
        List<String>sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        //��ѯ��JID�Ƿ��Ѿ���
        UserInfo userInfo=new UserInfo();
        userInfo.setEttuserid(Integer.parseInt(jId.trim()));
        List<Object> objList=this.userManager.getUpdateEttUserByEttUserIdSql(Integer.parseInt(jId.trim()),sqlbuilder);
        if(sqlbuilder!=null){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        //�õ���ǰ��У�µĸ��û�
        userInfo=new UserInfo();
        userInfo.setUserid(Integer.parseInt(userId));
        userInfo.setDcschoolid(Integer.parseInt(schoolid));
        System.out.println("bindJId----3");
        List<UserInfo> userList=this.userManager.getList(userInfo,presult);
        if(userList==null||userList.size()<1){
            returnJO.put("msg","�ڸ÷�У��û�з��ָ�ѧ��!��У:"+schoolid+" ѧ��:"+userId);
            response.getWriter().println(returnJO.toString());return;
        }
        //�����޸�
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
             //�󶨳ɹ����ٵ�ͷ��ش�
            //��¼״̬����Ϣ
            String type="success";
            String msg=null;
            //��ѯ��ǰ�û�����Ϣ
            userInfo=new UserInfo();
            userInfo.setUserid(Integer.parseInt(userId));
            userInfo.setDcschoolid(Integer.parseInt(schoolid));
            List<UserInfo> uList=this.userManager.getList(userInfo,null);
            if(uList!=null&&uList.size()>0&&uList.get(0)!=null&&uList.get(0).getHeadimage()!=null&&uList.get(0).getHeadimage().trim().length()>0){
                int returnType=HeadImgToEtt(uList.get(0));
                //1:�ɹ�        2:��Ϣ���������ܾ�����       3:ͼƬ��������    4����Ϣ�����������ɹ�����ִ�з���ʧ��
//                if(returnType==3){
//                    type="error";
//                    msg="ͬ��ͷ����Ϣʧ�ܣ�ԭ��ͷ��ͼƬ������!";
//                }else if(returnType==4){
//                    type="error";
//                    msg="ͬ��ͷ����Ϣʧ��!";
//                }
            }
//            System.out.println("imghead----6");
//            System.out.println("imghead----"+msg);
            returnJO.put("type", type);
//            if(type=="error"&&msg!=null)
//                returnJO.put("msg",msg);


            //����ǰ��JID����session��jid
            UserInfo sessionU=this.logined(request);
            sessionU.setEttuserid(Integer.parseInt(jId));
            this.setAttributeSession(request, "CURRENT_USER", sessionU);
        }else
            returnJO.put("msg","��ʧ��!ԭ��δ֪������ִ�д洢ʧ��!");
        System.out.println("imghead----"+returnJO.get("msg"));
        response.getWriter().println(returnJO.toString());
    }
}

