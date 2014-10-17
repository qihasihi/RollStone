package com.school.utils;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.ClassInfo;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * ����ETT�ӿ��û�������
 * Created by zhengzhou on 14-8-28.
 */
public class EttInterfaceUserUtil {

    /**
     * ����ɫ����  �����û�����
     *      ��ɫ����   1=������  2=�ον�ʦ   3=ѧ��
     * @param uentity  �û�List����
     * @param optype  �������ͣ�   add   update     delete
     * @return
     */
    private static boolean OperateUserBase(final List<UserInfo> uentity,final String optype){
        String addToEtt_URL= UtilTool.utilproperty.getProperty("TO_ETT_USER_INTERFACE").toString();
        if(uentity==null||uentity.size()<1)return false;
        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(UserInfo u:uentity){
            Map<String,Object> tmpMap=new HashMap<String, Object>();
            tmpMap.put("action",optype);
            tmpMap.put("userId",u.getUserid());
            tmpMap.put("userName",u.getUsername());
            tmpMap.put("realName",u.getRealname());
            tmpMap.put("userType",u.getUserType());
            tmpMap.put("schoolId",u.getDcschoolid());
            mapList.add(tmpMap);
        }
        JSONArray jsonArray=JSONArray.fromObject(mapList);
        paramMap.put("data",jsonArray.toString());
        String val = UrlSigUtil.makeSigSimple("userInterFace.do", paramMap);
        paramMap.put("sign",val);
        JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
            return true;
        }
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;
    }

    /**
     * ��������û���Ϣ
     * @param uentity  �û���Ϣ
     * @return
     */
    public static boolean addUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "add");
    }
    /**
     * ����ɾ���û���Ϣ
     * @param uentity  �û���Ϣ
     * @return
     */
    public static boolean delUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "delete");
    }
    /**
     * �����޸��û���Ϣ
     * @param uentity  �û���Ϣ
     * @return
     */
    public static boolean updateUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "update");
    }

    /********************SCH2-WEB. ��УС�������Ϣ���ݽӿ�******************************/

    /**
     * ͬ��С����Ϣ����
     * @param entity  С����Ϣʵ��
     * @param optype  �������ͣ�   add   update     delete
     * @return
     */
    private static boolean OperateGroupBase(final TpGroupInfo entity,final Integer schoolid,final String optype){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_GROUP_INTERFACE").toString();
        if(entity==null||schoolid==null||optype==null)return false;
        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");

        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("action",optype);
        tmpMap.put("classId",entity.getClassid());
        tmpMap.put("groupId",entity.getGroupid());
        tmpMap.put("groupName",entity.getGroupname());
        tmpMap.put("subjectId",entity.getSubjectid());
        tmpMap.put("schoolId",schoolid);

        JSONObject jsonObject=JSONObject.fromObject(tmpMap);
        paramMap.put("data",jsonObject.toString());
        String val = UrlSigUtil.makeSigSimple("groupInterFace.do", paramMap);
        paramMap.put("sign",val);
        JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1"))
            return true;
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;
    }

    /**
     * ���С����Ϣ��
     * @param entity
     * @param schoolid
     * @return
     */
    public static boolean addGroupBase(final TpGroupInfo entity,final Integer schoolid){
        return OperateGroupBase(entity,schoolid,"add");
    }

    /**
     * ɾ��С����Ϣ��
     * @param entity
     * @param schoolid
     * @return
     */
    public static boolean delGroupBase(final TpGroupInfo entity,final Integer schoolid){
        return OperateGroupBase(entity,schoolid,"delete");
    }

    /**
     * �޸�С����Ϣ��
     * @param entity
     * @param schoolid
     * @return
     */
    public static boolean updateGroupBase(final TpGroupInfo entity,final Integer schoolid){
        return OperateGroupBase(entity,schoolid,"update");
    }

    /********************************SCH1-WEB.��У�༶������Ϣ���ݽӿ�*****************************************/

    /**
     * ͬ���༶��Ϣ����
     * @param entity  �༶��Ϣʵ��
     * @param optype  �������ͣ�   add   update     delete
     * @return
     */
    private static boolean OperateClassBase(final ClassInfo entity,final String optype){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_CLASS_INTERFACE").toString();
        if(entity==null||optype==null)return false;
        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");

        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("action",optype);
        tmpMap.put("schoolId",entity.getDcschoolid());
        tmpMap.put("classId",entity.getClassid());
        tmpMap.put("className",entity.getClassgrade()+entity.getClassname());
//        Integer classType=1;
//        if(entity.getPattern()!=null&&entity.getPattern().trim().equals("�ֲ��"))
//            classType=2;
        String classGrade=entity.getClassgrade();
        if(classGrade!=null){
            if(classGrade.equals("����"))
                classGrade="�������꼶";
            else if(classGrade.equals("�߶�"))
                classGrade="���ж��꼶";
            else if(classGrade.equals("��һ"))
                classGrade="����һ�꼶";
            else if(classGrade.equals("����"))
                classGrade="�������꼶";
            else if(classGrade.equals("����"))
                classGrade="���ж��꼶";
            else if(classGrade.equals("��һ"))
                classGrade="����һ�꼶";
            else if(classGrade.equals("��һ"))
                classGrade="����һ�꼶";
        }

        tmpMap.put("classType",entity.getDctype());
        tmpMap.put("gradeName",classGrade);
        tmpMap.put("academicYear",entity.getYear());
        tmpMap.put("subjectId",entity.getSubjectid()==null?-1:entity.getSubjectid());
        if(entity.getDcschoolid().intValue()==1&&entity.getDctype()==2&&
                entity.getInvitecode()!=null&&entity.getInvitecode().length()>0)
            tmpMap.put("parentKey",entity.getInvitecode());

        JSONObject jsonObject=JSONObject.fromObject(tmpMap);
        paramMap.put("data",jsonObject.toString());
        String val = UrlSigUtil.makeSigSimple("classInterFace.do", paramMap);
        paramMap.put("sign",val);
        JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1"))
            return true;
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;
    }

    /**
     * ��Ӱ༶��Ϣ
     * @param entity �༶��Ϣ
     * @return
     */
    public static boolean addClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"add");
    }
    /**
     * ��Ӱ༶��Ϣ
     * @param entity �༶��Ϣ
     * @return
     */
    public static boolean delClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"delete");
    }
    /**
     * �޸İ༶��Ϣ
     * @param entity �༶��Ϣ
     * @return
     */
    public static boolean updateClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"update");
    }


    /**
     * �����û���༶����
     * @param cuMapList  �ش� userId��userType,subjectId ������key
     * @param classid
     * @param schoolid
     * @return
     */
    public static boolean OperateClassUser(List<Map<String,Object>> cuMapList,Integer classid,Integer schoolid){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_JOINCLASSUSER_INTERFACE").toString();
        if(classid==null||schoolid==null)return false;

        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");

        //�õ�data JSONObject
        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("classId",classid);
        tmpMap.put("schoolId",schoolid);
        if(cuMapList!=null&&cuMapList.size()>0)
            tmpMap.put("userList",JSONArray.fromObject(cuMapList).toString());
        //����
        paramMap.put("data",JSONObject.fromObject(tmpMap).toString());
//        JSONObject jb=JSONObject.fromObject(paramMap);
        String val = UrlSigUtil.makeSigSimple("jionClassUserInterFace.do", paramMap);
        paramMap.put("sign",val);
        JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
            return true;
        }
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;

    }


    /**
     * �����û���С������
     * @param cuMapList  �ش� userId��userType ��key
     * @param groupId
     * @param schoolid
     * @return
     */
    public static boolean OperateGroupUser(List<Map<String,Object>> cuMapList,Integer classid,Long groupId,Integer schoolid){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_GROUPUSER_INTERFACE").toString();
        if(groupId==null||schoolid==null||classid==null)return false;

        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");

        //�õ�data JSONObject
        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("groupId",groupId);
        tmpMap.put("classId",classid);
        tmpMap.put("schoolId",schoolid);
        if(cuMapList!=null&&cuMapList.size()>0)
            tmpMap.put("userList",JSONArray.fromObject(cuMapList).toString());
        //����
        paramMap.put("data",JSONObject.fromObject(tmpMap).toString());
//        JSONObject jb=JSONObject.fromObject(paramMap);
        String val = UrlSigUtil.makeSigSimple("jionGroupUserInterFace.do", paramMap);
        paramMap.put("sign",val);
        JSONObject jo=UtilTool.sendPostUrl(addToEtt_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
            return true;
        }
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;

    }
    //main����
    public static void main(String[] args){

    }

}
