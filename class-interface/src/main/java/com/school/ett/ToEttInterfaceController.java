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
//        String sign = UrlSigUtil.makeSigSimple("ETT-SCHOOL-MODEL", map, UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString());
//        System.out.println("ettKey"+key+"   key:"+sign);
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

        if(this.userManager.doExcetueArrayProc(sqlArrayList,objArrayList))
            returnJO.put("type","success");
        else
            returnJO.put("msg","��ʧ��!ԭ��δ֪������ִ�д洢ʧ��!");
        response.getWriter().println(returnJO.toString());
    }

}

