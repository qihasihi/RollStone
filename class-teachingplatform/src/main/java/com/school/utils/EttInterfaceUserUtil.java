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
 * 调用ETT接口用户工具类
 * Created by zhengzhou on 14-8-28.
 */
public class EttInterfaceUserUtil {

    /**
     * 按角色类型  操作用户数据
     *      角色类型   1=班主任  2=任课教师   3=学生
     * @param uentity  用户List集合
     * @param optype  操作类型：   add   update     delete
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
     * 批量添加用户信息
     * @param uentity  用户信息
     * @return
     */
    public static boolean addUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "add");
    }
    /**
     * 批量删除用户信息
     * @param uentity  用户信息
     * @return
     */
    public static boolean delUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "delete");
    }
    /**
     * 批量修改用户信息
     * @param uentity  用户信息
     * @return
     */
    public static boolean updateUserBase(final List<UserInfo> uentity){
        return OperateUserBase(uentity, "update");
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
//        if(entity.getPattern()!=null&&entity.getPattern().trim().equals("分层班"))
//            classType=2;
        String classGrade=entity.getClassgrade();
        if(classGrade!=null){
            if(classGrade.equals("高三"))
                classGrade="高中三年级";
            else if(classGrade.equals("高二"))
                classGrade="高中二年级";
            else if(classGrade.equals("高一"))
                classGrade="高中一年级";
            else if(classGrade.equals("初三"))
                classGrade="初中三年级";
            else if(classGrade.equals("初二"))
                classGrade="初中二年级";
            else if(classGrade.equals("初一"))
                classGrade="初中一年级";
            else if(classGrade.equals("初一"))
                classGrade="初中一年级";
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
     * 添加班级信息
     * @param entity 班级信息
     * @return
     */
    public static boolean addClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"add");
    }
    /**
     * 添加班级信息
     * @param entity 班级信息
     * @return
     */
    public static boolean delClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"delete");
    }
    /**
     * 修改班级信息
     * @param entity 班级信息
     * @return
     */
    public static boolean updateClassBase(final ClassInfo entity){
        return OperateClassBase(entity,"update");
    }


    /**
     * 操作用户与班级数据
     * @param cuMapList  必带 userId，userType,subjectId 的三个key
     * @param classid
     * @param schoolid
     * @return
     */
    public static boolean OperateClassUser(List<Map<String,Object>> cuMapList,Integer classid,Integer schoolid){
        String addToEtt_URL=UtilTool.utilproperty.getProperty("TO_ETT_JOINCLASSUSER_INTERFACE").toString();
        if(classid==null||schoolid==null)return false;

        HashMap<String,String> paramMap=new HashMap<String,String>();
        paramMap.put("time",new Date().getTime()+"");

        //得到data JSONObject
        Map<String,Object> tmpMap=new HashMap<String, Object>();
        tmpMap.put("classId",classid);
        tmpMap.put("schoolId",schoolid);
        if(cuMapList!=null&&cuMapList.size()>0)
            tmpMap.put("userList",JSONArray.fromObject(cuMapList).toString());
        //存入
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
    //main方法
    public static void main(String[] args){

    }

}
