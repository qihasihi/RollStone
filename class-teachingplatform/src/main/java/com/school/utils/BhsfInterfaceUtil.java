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
 * 调用四中接口用户工具类
 * Created by qihaishi on 15-1-26.
 */
public class BhsfInterfaceUtil {

    /**
     * 按角色类型  操作用户数据
     *      角色类型   1=班主任  2=任课教师   3=学生
     * @param uentity  用户List集合
     * @param optype  操作类型：   add   update     delete
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
        md5key= LZX_MD5.getMD5Str(md5key);//生成md5加密
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
     * 批量添加用户信息
     * @param uentity  用户信息
     * @return
     */
    public static boolean addUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "addUser");
    }
    /**
     * 批量删除用户信息
     * @param uentity  用户信息
     * @return
     */
    public static boolean delUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "delUser");
    }
    /**
     * 批量修改用户信息
     * @param uentity  用户信息
     * @return
     */
    public static boolean updateUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "addUser");
    }

    /********************SCH2-WEB. 数校小组基础信息数据接口******************************/

    /**
     * 同步小组信息操作
     * @param entity  小组信息实体
     * @param optype  操作类型：   add   update     delete
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
     * 添加小组信息表
     * @param entity
     * @param schoolid
     * @return
     */
    public static boolean addGroupBase(final TpGroupInfo entity,final Integer schoolid){
        return OperateGroupBase(entity,schoolid,"add");
    }

    /**
     * 删除小组信息表
     * @param entity
     * @param schoolid
     * @return
     */
    public static boolean delGroupBase(final TpGroupInfo entity,final Integer schoolid){
        return OperateGroupBase(entity,schoolid,"delete");
    }

    /**
     * 修改小组信息表
     * @param entity
     * @param schoolid
     * @return
     */
    public static boolean updateGroupBase(final TpGroupInfo entity,final Integer schoolid){
        return OperateGroupBase(entity,schoolid,"update");
    }

    /********************************SCH1-WEB.数校班级基础信息数据接口*****************************************/

    /**
     * 同步班级信息操作
     * @param entity  班级信息实体
     * @param optype  操作类型：   add   update     delete
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
        md5key= LZX_MD5.getMD5Str(md5key);//生成md5加密
        paramMap.put("sign",md5key);
        JSONObject jo=UtilTool.sendPostUrl(BHSF_URL,paramMap,"UTF-8");
        if(jo!=null&&jo.containsKey("type")&&jo.get("type").toString().trim().equals("success"))
            return true;
        if(jo!=null)
            System.out.println(jo.get("msg"));
        return false;
    }

    /**
     * 添加或修改班级信息
     * @param entity 班级信息
     * @return
     */
    public static boolean addOrModifyClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"addOrModifyClass");
    }
    /**
     * 添加班级信息
     * @param entity 班级信息
     * @return
     */
    public static boolean delClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"delClass");
    }

    /**
     * 操作用户与班级数据
     * @param cuMapList  必带 userId，userType,subjectId 的三个key
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
        //存入
        paramMap.put("message",JSONArray.fromObject(cuMapList).toString());
        String md5key=time+"SX_OA";
        md5key= LZX_MD5.getMD5Str(md5key);//生成md5加密
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
     * 删除班级人员
     * @param cuMapList
     * @return
     */
    public static boolean delClassUser(List<Map<String,Object>> cuMapList){
        return OperateClassUser(cuMapList, "delClassUser");
    }
    /**
     * 添加班级人员
     * @param cuMapList
     * @return
     */
    public static boolean addClassUser(List<Map<String,Object>> cuMapList){
        return OperateClassUser(cuMapList, "addClassUser");
    }



    /**
     * 操作用户与小组数据
     * @param cuMapList  必带 userId，userType 的key
     * @param groupId
     * @param schoolid
     * @return
     */
    public static boolean OperateGroupUser(List<Map<String,Object>> cuMapList,Integer classid,Long groupId,Integer schoolid){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_GROUPUSER_INTERFACE").toString();
        if(groupId==null||schoolid==null||classid==null)return false;

        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");

        //得到data JSONObject
        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("groupId",groupId);
        tmpMap.put("classId",classid);
        tmpMap.put("schoolId",schoolid);
        if(cuMapList!=null&&cuMapList.size()>0)
            tmpMap.put("userList",JSONArray.fromObject(cuMapList).toString());
        //存入
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
    //main方法
    public static void main(String[] args){

    }

}
