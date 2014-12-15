package com.school.dao;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.school.dao.base.CommonDAO;
import com.school.entity.MyInfoUserInfo;
import com.school.dao.inter.IMyInfoUserDAO;
import com.school.util.PageResult;

@Component
public class MyInfoUserDAO extends CommonDAO<MyInfoUserInfo> implements IMyInfoUserDAO {

    public Boolean doSave(MyInfoUserInfo myinfouserinfo) {
        if (myinfouserinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getSaveSql(myinfouserinfo, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doDelete(MyInfoUserInfo myinfouserinfo) {
        if(myinfouserinfo==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(myinfouserinfo, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }return false;
    }

    public Boolean doUpdate(MyInfoUserInfo myinfouserinfo) {
        if (myinfouserinfo == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getUpdateSql(myinfouserinfo, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public List<MyInfoUserInfo> getList(MyInfoUserInfo myinfouserinfo, PageResult presult) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL j_myinfo_user_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if (myinfouserinfo.getMsgname() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgname());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getOperateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getOperateid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getMsgid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getUserref() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getUserref());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getTemplateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getTemplateid());
        } else
            sqlbuilder.append("null,");

        if (myinfouserinfo.getMydata() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMydata());
        } else
            sqlbuilder.append("null,");
        if(presult!=null&&presult.getPageNo()>0&&presult.getPageSize()>0){
            sqlbuilder.append("?,?,");
            objList.add(presult.getPageNo());
            objList.add(presult.getPageSize());
        }else{
            sqlbuilder.append("NULL,NULL,");
        }
        if(presult!=null&&presult.getOrderBy()!=null&&presult.getOrderBy().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(presult.getOrderBy());
        }else{
            sqlbuilder.append("NULL,");
        }
        sqlbuilder.append("?)}");
        List<Integer> types=new ArrayList<Integer>();
        types.add(Types.INTEGER);
        Object[] objArray=new Object[1];
        List<MyInfoUserInfo> myinfouserinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, MyInfoUserInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return myinfouserinfoList;
    }


    /**
     * 查询首页消息一段时间内的消息数量
     * @param msgid
     * @param userref
     * @param btime
     * @param etime
     * @return
     */
    public Integer getMyInfoUserInfoCountFirstPage(Integer msgid,String userref,String btime,String etime) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL j_myinfo_user_info_count_firstpage(");
        List<Object> objList=new ArrayList<Object>();
        if (msgid != null) {
            sqlbuilder.append("?,");
            objList.add(msgid);
        } else
            sqlbuilder.append("null,");
        if (userref != null) {
            sqlbuilder.append("?,");
            objList.add(userref);
        } else
            sqlbuilder.append("null,");
        if (btime!= null) {
            sqlbuilder.append("?,");
            objList.add(btime);
        } else
            sqlbuilder.append("null,");
        if (etime!= null) {
            sqlbuilder.append("?");
            objList.add(etime);
        } else
            sqlbuilder.append("null");
        sqlbuilder.append(")}");
        //sqlbuilder.append("?)}");
        List<List<String>> objMapList=this.executeResultProcedure(sqlbuilder.toString(), objList);
        if(objMapList==null||objMapList.size()<1||objMapList.get(0)==null||objMapList.get(0).toString().trim().length()<1)
            return 0;
        return Integer.parseInt(objMapList.get(0).get(0).toString());
    }

    public List<Object> getSaveSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
        if(myinfouserinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL j_myinfo_user_info_proc_add(");
        List<Object>objList = new ArrayList<Object>();
        if (myinfouserinfo.getMsgname() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgname());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getOperateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getOperateid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getMsgid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getUserref() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getUserref());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getTemplateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getTemplateid());
        } else
            sqlbuilder.append("null,");

        if (myinfouserinfo.getMydata() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMydata());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getClassid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getClassid());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 得到首页的动态数据
     * @param userref
     * @return
     */
    public List<MyInfoUserInfo> getSYMsgData(String userref){
        if(userref==null||userref.trim().length()<1)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL cal_page_index_msg_data(?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(userref);
        List<MyInfoUserInfo> myinfouserinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, null, MyInfoUserInfo.class, null);
        return myinfouserinfoList;
    }

    /**
     * 得到Im1.1.3中查询动态，加入班级的动态
     * @param clsid
     * @param userid
     * @return
     */
    public List<MyInfoUserInfo> getImMsgData(Integer clsid,Integer userid){
        StringBuilder sqlbuilder=new StringBuilder("{CALL cal_im1_1_3_class_dynamic_msg(");
        List<Object> objList=new ArrayList<Object>();
        if(clsid!=null){
            sqlbuilder.append("?,");
            objList.add(clsid);
        }else
            sqlbuilder.append("NULL");
        if(userid!=null){
            sqlbuilder.append("?");
            objList.add(userid);
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append(")}");
        List<MyInfoUserInfo> myinfouserinfoList=this.executeResult_PROC(sqlbuilder.toString(), objList, null, MyInfoUserInfo.class, null);
        return myinfouserinfoList;
    }

    /**
     * 得到首页的动态数据个数
     * @param userref
     * @return
     */
    public List<Map<String,Object>> getSYMsgDataCount(String userref){
        if(userref==null||userref.trim().length()<1)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL cal_page_index_msg_data_count(?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(userref);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }
    /**
     * 得到首页的动态数据个数
     * @return
     */
    public Integer getImMsgData(Integer clsid,Integer userid,String year){
        if(userid==null||year==null||year.trim().length()<1)return null;
        StringBuilder sqlbuilder=new StringBuilder("{CALL cal_im1_1_3_class_dynamic_count(");
        List<Object> objList=new ArrayList<Object>();
        if(clsid!=null){
            sqlbuilder.append("?,");
            objList.add(clsid);
        }else
            sqlbuilder.append("null,");
        if(userid!=null){
            sqlbuilder.append("?,");
            objList.add(userid);
        }else
            sqlbuilder.append("null,");
        if(year!=null){
            sqlbuilder.append("?,");
            objList.add(year);
        }else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");

        Object obj=this.executeSacle_PROC(sqlbuilder.toString(),objList==null?null:objList.toArray());
        if(obj!=null&&obj.toString().length()>0)
            return Integer.parseInt(obj.toString());
        return 0;
    }



    /**
     * 添加消息，通用 评教系统
     * @param templateid  模版ID
     * @param userref   接收人REF
     * @param msgid    消息类型
     * @param msgname   消息名称
     * @param operateuserref  操作人
     * @param data    组合数据类型
     * @return
     */
    public boolean doSaveTongYongThpjTimesteup(Integer userref,int typeid) {
        if(userref==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL myinfo_user_thpjtimesteup(");
        List<Object>objList = new ArrayList<Object>();
        sqlbuilder.append("?,");
        objList.add(userref);

        if (userref != null) {
            sqlbuilder.append("?,");
            objList.add(userref);
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询加入班级动态信息
     * @param userid
     * @param classid
     * @param dcschoolid
     * @return
     */
    public List<Map<String,Object>> getUserClassInJoinLog(int userid,int classid,int dcschoolid){
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL j_user_class_injoin_pro_search(?,?,?)}");
        List<Object>objList = new ArrayList<Object>();
        objList.add(userid);
        objList.add(classid);
        objList.add(dcschoolid);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }
    /**
     * 记录加入班级动态
     * @param userid
     * @param classid
     * @param dcschoolid
     * @param allowInJoin 1:允许  0：不允许
     * @param operateid
     * @return
     */
    public boolean doSaveUserClassInJoinLog(int userid,int classid,int dcschoolid,int allowInJoin,int operateid){
        StringBuilder sqlbuilder=new StringBuilder();
        sqlbuilder.append("{CALL j_user_class_injoin_pro_add(?,?,?,?,?,?)}");
        List<Object>objList = new ArrayList<Object>();
        objList.add(userid);
        objList.add(classid);
        objList.add(dcschoolid);
        objList.add(allowInJoin);
        objList.add(operateid);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public List<Object> getDeleteSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
        if(myinfouserinfo==null || sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL j_myinfo_user_info_proc_delete(");
        List<Object>objList = new ArrayList<Object>();
        if (myinfouserinfo.getMsgname() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgname());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getOperateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getOperateid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getMsgid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getUserref() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getUserref());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getTemplateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getTemplateid());
        } else
            sqlbuilder.append("null,");

        if (myinfouserinfo.getMydata() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMydata());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getUpdateSql(MyInfoUserInfo myinfouserinfo, StringBuilder sqlbuilder) {
        if(myinfouserinfo==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{CALL j_myinfo_user_info_proc_update(");
        List<Object>objList = new ArrayList<Object>();
        if (myinfouserinfo.getMsgname() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgname());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getOperateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getOperateid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getMsgid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMsgid());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getUserref() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getUserref());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getRef() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getRef());
        } else
            sqlbuilder.append("null,");
        if (myinfouserinfo.getTemplateid() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getTemplateid());
        } else
            sqlbuilder.append("null,");

        if (myinfouserinfo.getMydata() != null) {
            sqlbuilder.append("?,");
            objList.add(myinfouserinfo.getMydata());
        } else
            sqlbuilder.append("null,");
        sqlbuilder.append("?)}");
        return objList;
    }
}
