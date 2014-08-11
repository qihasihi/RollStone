package com.school.im1_1.dao.inter._interface;

import com.school.dao.base.ICommonDAO;
import com.school.im1_1.entity._interface.ImInterfaceInfo;
import java.util.*;

/**
 * Created by yuechunyang on 14-7-25.
 */
public interface IImInterfaceDAO extends ICommonDAO<ImInterfaceInfo>{
    public List<Map<String ,Object>> getStudyModule(ImInterfaceInfo obj);
    public List<Map<String,Object>> getClassTaskCourse(ImInterfaceInfo obj);
    public List<Map<String,Object>> getClassTaskTask(Long courseid,Integer userid);
    public List<Map<String,Object>> getTaskInfo(Long taskid,Integer classid);
    public List<Map<String,Object>> getTaskUserRecord(Long taskid,Integer classid,Integer isvir,Integer userid);
}
