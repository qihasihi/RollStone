package com.school.utils;

import com.etiantian.unite.utils.UrlSigUtil;
import com.school.entity.ClassInfo;
import com.school.entity.UserInfo;
import com.school.entity.teachpaltform.TpGroupInfo;
import com.school.util.LZX_MD5;
import com.school.util.UtilTool;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * �������нӿ��û�������
 * Created by qihaishi on 15-1-26.
 */
public class BhsfInterfaceUtil {

    /**
     * ����ɫ����  �����û�����
     *      ��ɫ����   1=������  2=�ον�ʦ   3=ѧ��
     * @param uentity  �û�List����
     * @param optype  �������ͣ�   add   update     delete
     * @return
     */
    private static boolean OperateUserBase(final List<UserInfo> uentity,final String optype){
        String BHSF_URL=UtilTool.utilproperty.getProperty("BHSF_BASE_URL").toString();
        if(BHSF_URL==null||BHSF_URL.length()<1)return false;
        BHSF_URL+="foreignFace!"+optype+".action";

        if(uentity==null||uentity.size()<1)return false;
        HashMap<String,String> paramMap=new HashMap<String,String>();
        String time=new Date().getTime()+"";
        paramMap.put("timestamp",time);
        List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
        for(UserInfo u:uentity){
            Map<String,Object> tmpMap=new HashMap<String, Object>();
            tmpMap.put("sx_user_id",u.getUserid());
            tmpMap.put("userName",u.getUsername());
            tmpMap.put("role_id",u.getRoleIdStr());
            tmpMap.put("headimgurl",u.getHeadimage());
            String[]roleArray=u.getRoleIdStr().split(",");
            if(roleArray.length>0){
                for(String rid:roleArray){
                    if(rid.equals("18")){
                        tmpMap.put("teacher_name",u.getRealname());
                        tmpMap.put("teacher_sex",u.getUsersex());
                        tmpMap.put("teacher_no",u.getTeacherno());
                    }else if(rid.equals("36")){
                        tmpMap.put("stu_name",u.getRealname());
                        tmpMap.put("stu_sex",u.getUsersex());
                        tmpMap.put("stu_no",u.getStuno());
                    }else{
                        System.out.println("InValidate Role!");
                        return false;
                    }

                }
            }else{
                System.out.println("RoleId is empt!");
                return false;
            }
            mapList.add(tmpMap);
        }
        JSONArray jsonArray=JSONArray.fromObject(mapList);
        paramMap.put("message",jsonArray.toString());
        String md5key=time+"SX_OA";
        md5key= LZX_MD5.getMD5Str(md5key);//����md5����
        paramMap.put("sign",md5key);
        JSONObject jo=UtilTool.sendPostUrl(BHSF_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("type")&&jo.get("type").toString().trim().equals("success")){
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
        return OperateUserBase(uentity, "addUser");
    }
    /**
     * ����ɾ���û���Ϣ
     * @param uentity  �û���Ϣ
     * @return
     */
    public static boolean delUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "delUser");
    }
    /**
     * �����޸��û���Ϣ
     * @param uentity  �û���Ϣ
     * @return
     */
    public static boolean updateUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "addUser");
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
        String BHSF_URL=UtilTool.utilproperty.getProperty("BHSF_BASE_URL").toString();
        if(BHSF_URL==null||BHSF_URL.length()<1)return false;
        BHSF_URL+="foreignFace!"+optype+".action";
        if(entity==null||optype==null)return false;
        HashMap<String,String> paramMap=new HashMap<String,String>();
        String time=new Date().getTime()+"";
        paramMap.put("timestamp",time);
        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("sx_classid",entity.getClassid());
        tmpMap.put("className",entity.getClassname());
        String classGrade=entity.getClassgrade();
        tmpMap.put("type",entity.getDctype());
        tmpMap.put("class_grade",classGrade);
        tmpMap.put("year",entity.getYear());
        tmpMap.put("pattern",entity.getPattern());
        JSONArray jsonArray=JSONArray.fromObject(tmpMap);
        paramMap.put("message",jsonArray.toString());
        String md5key=time+"SX_OA";
        md5key= LZX_MD5.getMD5Str(md5key);//����md5����
        paramMap.put("sign",md5key);
        JSONObject jo=UtilTool.sendPostUrl(BHSF_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("type")&&jo.get("type").toString().trim().equals("success"))
            return true;
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;
    }

    /**
     * ��ӻ��޸İ༶��Ϣ
     * @param entity �༶��Ϣ
     * @return
     */
    public static boolean addOrModifyClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"addOrModifyClass");
    }
    /**
     * ��Ӱ༶��Ϣ
     * @param entity �༶��Ϣ
     * @return
     */
    public static boolean delClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"delClass");
    }

    /**
     * �����û���༶����
     * @param cuMapList  �ش� userId��userType,subjectId ������key
     * @param optype
     * @return
     */
    public static boolean OperateClassUser(List<Map<String,Object>> cuMapList,String optype){
        String BHSF_URL=UtilTool.utilproperty.getProperty("BHSF_BASE_URL").toString();
        if(BHSF_URL==null||BHSF_URL.length()<1)return false;
        BHSF_URL+="foreignFace!"+optype+".action";
        HashMap<String,String> paramMap=new HashMap<String,String>();
        String time=new Date().getTime()+"";
        paramMap.put("timestamp",time);
        //����
        paramMap.put("message",JSONArray.fromObject(cuMapList).toString());
        String md5key=time+"SX_OA";
        md5key= LZX_MD5.getMD5Str(md5key);//����md5����
        paramMap.put("sign",md5key);
        JSONObject jo=UtilTool.sendPostUrl(BHSF_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
            return true;
        }
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;

    }

    /**
     * ɾ���༶��Ա
     * @param cuMapList
     * @return
     */
    public static boolean delClassUser(List<Map<String,Object>> cuMapList){
        return OperateClassUser(cuMapList, "delClassUser");
    }
    /**
     * ��Ӱ༶��Ա
     * @param cuMapList
     * @return
     */
    public static boolean addClassUser(List<Map<String,Object>> cuMapList){
        return OperateClassUser(cuMapList, "addClassUser");
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

    public static boolean ModifyUser(UserInfo u,String identity){
        if(u==null||identity==null)
            return false;
        String ettUrl=UtilTool.utilproperty.get("ETT_INTER_IP").toString()+"modifyUser.do";
        //String ettUrl="http://192.168.10.59/study-im-service-1.0/modifyUser.do";
        if(ettUrl==null)return false;
        HashMap<String,String> tmpMap=new HashMap<String, String>();
        tmpMap.put("timestamp",new Date().getTime()+"");
        tmpMap.put("dcSchoolId",u.getDcschoolid().toString());
        tmpMap.put("jid",u.getEttuserid().toString());
        tmpMap.put("dcUserId",u.getUserid().toString());
        tmpMap.put("userName",u.getUsername());
        tmpMap.put("password",u.getPassword());
        tmpMap.put("email",u.getMailaddress());
        tmpMap.put("identity",identity);
        String sign=UrlSigUtil.makeSigSimple("modifyUser.do",tmpMap);
        tmpMap.put("sign",sign);
        JSONObject jo=UtilTool.sendPostUrl(ettUrl,tmpMap,"UTF-8");
        System.out.println(UtilTool.decode(jo.get("msg").toString()));
        if(jo!=null&&jo.containsKey("result")&&jo.get("result").toString().trim().equals("1")){
            return true;
        }
        return false;
    }
    //main����
    public static void main(String[] args){

    }

}
