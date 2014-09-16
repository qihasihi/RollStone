package com.school.dao.teachpaltform.award;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.teachpaltform.award.ITpGroupScoreDAO;
import com.school.dao.inter.teachpaltform.award.ITpStuScoreDAO;
import com.school.entity.teachpaltform.award.TpStuScore;
import com.school.util.PageResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhengzhou on 14-6-24.
 */
@Component
public class TpStuScoreDAO extends CommonDAO<TpStuScore> implements ITpStuScoreDAO {
    @Override
    public Boolean doSave(final TpStuScore obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getSaveSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
        
    }

    @Override
    public Boolean doUpdate(final TpStuScore obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getUpdateSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public Boolean doDelete(final TpStuScore obj) {
        if(obj==null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.getDeleteSql(obj, sqlbuilder);
        if(sqlbuilder.length()>0)
            return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
        return false;
    }

    @Override
    public List<TpStuScore> getList(final TpStuScore obj, PageResult presult) {
        return null;
    }

    @Override
    public List<Object> getSaveSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_stu_score_add(");

        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
         sqlbuilder.append("?,?,?,");
        objlist.add(obj.getAttendanceNum());
        objlist.add(obj.getSimilingNum());
        objlist.add(obj.getViolationDisNum());
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getTaskscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getTaskscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCommentscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCommentscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCoursetotalscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCoursetotalscore());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objlist;
    }

    /**
     * ����ͳ��С�����
     * @param taskid
     * @param classid
     * @param userid
     * @param courseid
     * @param dcschoolid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getUpdateStaticesGroupScore(final Long taskid,final Integer classid
            ,final Integer userid,final Long courseid,final Integer dcschoolid,StringBuilder sqlbuilder){
        if(taskid==null||classid==null||userid==null||courseid==null||dcschoolid==null||sqlbuilder==null){
            return null;
        }
        sqlbuilder.append("{CALL cal_tp_stu_statices_group_score(?,?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(taskid);
        objList.add(classid);
        objList.add(userid);
        objList.add(courseid);
        objList.add(dcschoolid);
        return objList;
    }
    /**
     * ��������ִ��ͳ��
     * ���༶����==��¼���ύ������
     * @return
     */
    public boolean tpStuScoreCkAllComplateInput(final Long courseid,final Integer classid,final Integer subjectid,final Integer dcschoolid){
        if(courseid==null||classid==null||subjectid==null||dcschoolid==null){
            return false;
        }
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_stu_score_ck_allComplateInput(?,?,?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(courseid);
        objList.add(classid);
        objList.add(subjectid);
        objList.add(dcschoolid);
        return this.executeQuery_PROC(sqlbuilder.toString(),objList.toArray());
    }



    @Override
    public List<Object> getUpdateSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_stu_score_update(");
        if(obj.getRef()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getRef());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAttendanceNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getAttendanceNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSimilingNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSimilingNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getViolationDisNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getViolationDisNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objlist;
    }

    /**
     * �õ�ҳ���ϵĲ�ѯ
     * @param courseid
     * @param classid
     * @param classtype
     * @return
     */
    public List<Map<String,Object>> getPageDataList(final Long courseid,final Long classid,final Integer classtype
            ,final Integer subjectid,final String groupidStr,final Integer uid){
        List<Object> objList=new ArrayList<Object>();
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_group_score_list(");
        if(courseid!=null){
            sqlbuilder.append("?,");
            objList.add(courseid);
        }else
            sqlbuilder.append("NULL,");
        if(classid!=null){
            sqlbuilder.append("?,");
            objList.add(classid);
        }else
            sqlbuilder.append("NULL,");
        if(classtype!=null){
            sqlbuilder.append("?,");
            objList.add(classtype);
        }else
            sqlbuilder.append("NULL,");
        if(subjectid!=null){
            sqlbuilder.append("?,");
            objList.add(subjectid);
        }else
            sqlbuilder.append("NULL,");
        if(groupidStr!=null){
            sqlbuilder.append("?,");
            objList.add(groupidStr);
        }else
            sqlbuilder.append("NULL,");
        if(uid!=null){
            sqlbuilder.append("?");
            objList.add(uid);
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(")}");

        return this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
    }

    /**
     * ��ӻ��޸�
     * @param obj
     * @return
     */
    public boolean AddOrUpdate(final TpStuScore obj){
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_stu_score_addOrupdate(");

        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAttendanceNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getAttendanceNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSimilingNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSimilingNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getViolationDisNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getViolationDisNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getTaskscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getTaskscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCommentscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCommentscore());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return this.executeQuery_PROC(sqlbuilder.toString(),objlist.toArray());
    }
    /**
     * ��ӻ��޸Ļ���
     * @param obj
     * @return
     */
    public boolean AddOrUpdateColScore(final TpStuScore obj){
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_stu_score_update_colscore(");

        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");

        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAttendanceNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getAttendanceNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSimilingNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSimilingNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getViolationDisNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getViolationDisNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getTaskscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getTaskscore());
        }else
            sqlbuilder.append("NULL,");

        if(obj.getCommentscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCommentscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCoursetotalscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCoursetotalscore());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return this.executeQuery_PROC(sqlbuilder.toString(),objlist.toArray());
    }


    /**
     * ��ӻ��޸Ļ���
     * @param obj
     * @return
     */
    public List<Object> getAddOrUpdateColScore(final TpStuScore obj,StringBuilder sqlbuilder){
        if(sqlbuilder==null||obj==null)return null;
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_stu_score_update_colscore(");

        if(obj.getUserid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getUserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");

        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAttendanceNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getAttendanceNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSimilingNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSimilingNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getViolationDisNum()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getViolationDisNum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getTaskscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getTaskscore());
        }else
            sqlbuilder.append("NULL,");

        if(obj.getCommentscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCommentscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGroupscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getGroupscore());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCoursetotalscore()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCoursetotalscore());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objlist;
    }

    /**
     * ����ͳ��
     * @param subjectid
     * @param classid
     * @return
     */
    public List<Map<String,Object>> getScoreStatices(final Integer subjectid,final Integer classid,final String sort){
        StringBuilder sqlbuilder=new StringBuilder("{CALL tp_group_score_statices_score(?,?,?)}");
        List<Object> objlist=new ArrayList<Object>();
        objlist.add(subjectid);
        objlist.add(classid);
        objlist.add(sort==null?"asc":sort);
        return this.executeResultListMap_PROC(sqlbuilder.toString(),objlist);
    }

    /**
     * ��ʦ�鳤�ύʱ����ʼ���������
     * @param obj
     * @return
     */
    public boolean stuScoreLastInit(final TpStuScore obj,String groupidArr){
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objlist=new ArrayList<Object>();
        sqlbuilder.append("{CALL tp_stu_score_lastInit(");

        if(obj.getCourseid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getCourseid());
        }else
            sqlbuilder.append("NULL,");

        if(groupidArr!=null){
            sqlbuilder.append("?,");
            objlist.add(groupidArr);
        }else
            sqlbuilder.append("NULL,");

        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");

        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClasstype()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getClasstype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objlist.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return this.executeQuery_PROC(sqlbuilder.toString(),objlist.toArray());
    }

    @Override
    public List<Object> getDeleteSql(final TpStuScore obj, StringBuilder sqlbuilder) {
        return null;
    }
}
