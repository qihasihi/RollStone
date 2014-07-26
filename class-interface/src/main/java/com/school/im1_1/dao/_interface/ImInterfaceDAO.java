package com.school.im1_1.dao._interface;

import com.school.dao.base.CommonDAO;
import com.school.im1_1.dao.inter._interface.IImInterfaceDAO;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-7-25.
 */
@Component
public class ImInterfaceDAO extends CommonDAO<ImInterfaceInfo> implements IImInterfaceDAO {
    @Override
    public Boolean doSave(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public Boolean doUpdate(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public Boolean doDelete(ImInterfaceInfo obj) {
        return null;
    }

    @Override
    public List<ImInterfaceInfo> getList(ImInterfaceInfo obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getUpdateSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Object> getDeleteSql(ImInterfaceInfo obj, StringBuilder sqlbuilder) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getStudyModule(ImInterfaceInfo obj) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_studymodule_proc_list(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUserid());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getUsertype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getUsertype());
        }else{
            sqlbuilder.append("null,");
        }
        if(obj.getSchoolid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getSchoolid());
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getClassTaskCourse(ImInterfaceInfo obj) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_classtask_proc_getcourse(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getClassid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getClassid());
        }else{
            sqlbuilder.append("null");
        }
//        if(obj.getSchoolid()!=null){
//            sqlbuilder.append("?");
//            objList.add(obj.getSchoolid());
//        }else{
//            sqlbuilder.append("null");
//        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }

    public List<Map<String, Object>> getClassTaskTask(Long courseid) {
        StringBuilder sqlbuilder = new StringBuilder();
        sqlbuilder.append("{CALL imapi_classtask_proc_task(");
        List<Object> objList=new ArrayList<Object>();
        if(courseid!=null){
            sqlbuilder.append("?");
            objList.add(courseid);
        }else{
            sqlbuilder.append("null");
        }
//        if(obj.getSchoolid()!=null){
//            sqlbuilder.append("?");
//            objList.add(obj.getSchoolid());
//        }else{
//            sqlbuilder.append("null");
//        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        if(list!=null&&list.size()>0)
            return list;
        return null;
    }
}
