package  com.school.dao;

import com.school.dao.base.CommonDAO;
import com.school.dao.inter.IClassDAO;
import com.school.entity.AdminPerformance;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.stereotype.Component;

import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ClassDAO extends CommonDAO<ClassInfo> implements IClassDAO {

    public Boolean doDelete(ClassInfo obj) {//
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=getDeleteSql(obj, sqlbuilder);
        Object afficeObj=this.executeSacle_PROC(sqlbuilder.toString(), objList.toArray());
        if(afficeObj!=null&&afficeObj.toString().trim().length()>0&&Integer.parseInt(afficeObj.toString())>0){
            return true;
        }return false;
    }

//	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
//			List<List<Object>> objArrayList) {
//		return this.executeArray_SQL(sqlArrayList, objArrayList);
//	}

    public Boolean doSave(ClassInfo obj) {
        if (obj == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getSaveSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public Boolean doUpdate(ClassInfo obj) {
        if (obj == null)
            return false;
        StringBuilder sqlbuilder = new StringBuilder();
        List<Object> objList = this.getUpdateSql(obj, sqlbuilder);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),
                objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    public List<Object> getDeleteSql(ClassInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call class_proc_delete(");
        List<Object>objList = new ArrayList<Object>();
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getLzxclassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxclassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getIsflag()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getIsflag());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<ClassInfo> getList(ClassInfo obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL class_info_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null)
            sqlbuilder.append("null,null,null,null,null,null,null,null,null,null,0,null,null,null,null,null,null,null,");//增加两个字段
        else{
            if(obj.getClassid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getLzxclassid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getLzxclassid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getDcschoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getDcschoolid());
            }else
                sqlbuilder.append("NULL,");

            if(obj.getClassname()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassname());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getClassgrade()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassgrade());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getType()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getType());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getYear()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getYear());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getPattern()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getPattern());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getUserid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getUserid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getIslike()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getIslike());
            }else
                sqlbuilder.append("0,");
            if(obj.getSubjectid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getDctype()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getDctype());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getIsflag()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getIsflag());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getSubjectstr()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectstr());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getInvitecode()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getInvitecode());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getImvaldatecode()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getImvaldatecode());
            }else
                sqlbuilder.append("NULL,");

            //新增期次id字段
            if(obj.getTermid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getTermid());
            }else
                sqlbuilder.append("NULL,");
            //新增活动类型字段
            if(obj.getActivitytype()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getActivitytype());
            }else
                sqlbuilder.append("NULL,");
        }
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
        List<ClassInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return roleList;
    }

    public List<ClassInfo> getIm113List(ClassInfo obj, PageResult presult){
        StringBuilder sqlbuilder=new StringBuilder("{CALL class_info_im113_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(obj==null)
            sqlbuilder.append("null,null,null,null,");
        else{

            if(obj.getDcschoolid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getDcschoolid());
            }else
                sqlbuilder.append("NULL,");

            if(obj.getCuserid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getCuserid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getClassid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getClassid());
            }else
                sqlbuilder.append("NULL,");
            if(obj.getSearchUid()!=null){
                sqlbuilder.append("?,");
                objList.add(obj.getSearchUid());
            }else
                sqlbuilder.append("NULL,");
        }
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
        List<ClassInfo> roleList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassInfo.class, objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        return roleList;
    }
    /**
     * 根据UserRef,YEAR,PAttern得到班级
     * @param userref
     * @param year
     * @param pattern
     * @return
     */
    public List<ClassInfo> getClassByUserYearPattern(String userref,String year,String pattern) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL class_proc_theme_split(");
        List<Object> objList=new ArrayList<Object>();
        if(userref!=null&&userref.trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(userref);
        }else
            sqlbuilder.append("NULL,");
        if(year!=null&&year.trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(year);
        }else
            sqlbuilder.append("NULL,");
        if(pattern!=null&&pattern.trim().length()>0){
            sqlbuilder.append("?");
            objList.add(pattern);
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(")}");

        List<Integer> types=new ArrayList<Integer>();
        Object[] objArray=new Object[1];
        List<ClassInfo> clsList=this.executeResult_PROC(sqlbuilder.toString(), objList, types, ClassUser.class, objArray);
        if(clsList!=null&&clsList.size()>0)
            return clsList;
        else
            return null;
    }

    public List<Object> getSaveSql(ClassInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call class_proc_add(");
        List<Object>objList = new ArrayList<Object>();

        if(obj.getLzxclassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxclassid());
        }else
            sqlbuilder.append("NULL,");

        sqlbuilder.append("?,");
        objList.add(obj.getDcschoolid());

        if(obj.getClassgrade()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassgrade());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassname()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassname());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getYear()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getYear());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getType());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getPattern()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getPattern());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDctype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDctype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getIsflag()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getIsflag());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAllowjoin()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getAllowjoin());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getVerifytime()!=null){
            sqlbuilder.append("?,");
            objList.add(UtilTool.DateConvertToString(obj.getVerifytime(), UtilTool.DateType.type1));
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClsnum()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClsnum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getInvitecode()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getInvitecode());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCuserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getImvaldatecode()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getImvaldatecode());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getActivitytype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getActivitytype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getTermid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getTermid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    public List<Object> getUpdateSql(ClassInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call class_proc_update(");
        List<Object>objList = new ArrayList<Object>();
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassgrade()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassgrade());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassname()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassname());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getYear()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getYear());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getType());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getPattern()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getPattern());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            if(obj.getSubjectid()==-999)
                sqlbuilder.append("-999,");
            else{
                sqlbuilder.append("?,");
                objList.add(obj.getSubjectid());
            }
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDctype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDctype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getIsflag()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getIsflag());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getAllowjoin()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getAllowjoin());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getVerifytime()!=null){
            sqlbuilder.append("?,");
            objList.add(UtilTool.DateConvertToString(obj.getVerifytime(), UtilTool.DateType.type1));
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClsnum()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClsnum());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getInvitecode()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getInvitecode());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getActivitytype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getActivitytype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getTermid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getTermid());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 升级操作
     * @param year
     * @return
     */
    public Boolean doClassLevelUp(String year,Integer dcschoolid) {
        if (year == null)
            return false;
        StringBuilder sqlbuilder=new StringBuilder("{CALL class_auto_level_up(?,?,?)}");
        List<Object> objList=new ArrayList<Object>();
        objList.add(year);
        objList.add(dcschoolid);
        Object afficeObj = this.executeSacle_PROC(sqlbuilder.toString(),objList.toArray());
        if (afficeObj != null && afficeObj.toString().trim().length() > 0
                && Integer.parseInt(afficeObj.toString()) > 0) {
            return true;
        }
        return false;
    }

    /**
     * 得到更新或添加的SQL(乐知行)
     * @param obj
     * @param sqlbuilder
     * @return
     */
    public List<Object> getSaveOrUpdateSql(ClassInfo obj, StringBuilder sqlbuilder) {
        if(obj==null||sqlbuilder==null)
            return null;
        sqlbuilder.append("{call class_proc_addOrUpdate(");
        List<Object>objList = new ArrayList<Object>();

        if(obj.getLzxclassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getLzxclassid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassgrade()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassgrade());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassname()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassname());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getYear()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getYear());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getType()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getType());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getPattern()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getPattern());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDctype()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDctype());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getIsflag()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getIsflag());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getDcschoolid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getDcschoolid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getCuserid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getCuserid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getImvaldatecode()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getImvaldatecode());
        }else
            sqlbuilder.append("NULL,");
        sqlbuilder.append("?)}");
        return objList;
    }

    /**
     * 得到已建立的班级数量
     * @param schoolId 分校id
     * @param year 学年的值
     * @return 已有的班级数量
     */
    public int getTotalClass(int schoolId, String year, int from) {
        int total = 0;
        int pre = 0;
        int num = 0;
        int [] sYear = new int[5];
        if(from != 1) {
            String transYear = year+"#";
            for (int i = 0; i < transYear.length(); i++) {
                if(transYear.charAt(i) < 48 || transYear.charAt(i) > 57) {
                    if(i > pre) {
                        sYear[num] = Integer.valueOf(transYear.substring(pre, i)) - 1;
                        num++;
                    }
                    pre = i + 1;
                }
            }

            year = sYear[0] +"~"+ sYear[1];
        }
        if(schoolId < 50000 || year == null || year.equals(""))
            return 0;

        StringBuilder sb = new StringBuilder("{call class_proc_total(");
        sb.append(schoolId);
        sb.append(",'");
        sb.append(year);
        sb.append("',");
        sb.append(from);
        sb.append(",?)}");
        Object objTotal=this.executeSacle_PROC(sb.toString(),null);
        if(objTotal!=null)
            total=Integer.parseInt(objTotal.toString());
        return total;
    }

    //通过年级和期次获取班级
    public List<ClassInfo> getClassByGradeTerm(int gradeId,int termId) {
        List<ClassInfo> list = null;

        StringBuilder sb = new StringBuilder("{call class_info_proc_list_elite_class(");
        sb.append(gradeId);
        sb.append(",");
        sb.append(termId);
        sb.append(")}");

        list=this.executeResult_PROC(sb.toString(), null, null, ClassInfo.class, null);
        return list;
    }

    //获取所有期次id
    public List<ClassInfo> getAllTerm() {
        List<ClassInfo> list = null;

        StringBuilder sb = new StringBuilder("{call class_info_proc_list_term()}");

        list=this.executeResult_PROC(sb.toString(), null, null, ClassInfo.class, null);
        return list;
    }

    @Override
    public List<AdminPerformance> getAdminPerformance(ClassInfo obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_split(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getBegintime()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getBegintime());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getEndtime()!=null&&obj.getEndtime().trim().length()>0){
            sqlbuilder.append("?,");
            objList.add(obj.getEndtime());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getGradeid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getGradeid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL,");
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
        Object[] objArray=new Object[1];
        List<AdminPerformance> list=this.executeResult_PROC(sqlbuilder.toString(), objList,types,AdminPerformance.class,objArray);
        if(presult!=null&&objArray[0]!=null&&objArray[0].toString().trim().length()>0)
            presult.setRecTotal(Integer.parseInt(objArray[0].toString().trim()));
        if(list!=null&&list.size()>0)
            return list;
        else
            return null;
    }

    @Override
    public List<List<String>> getAdminPerformanceStu(ClassInfo obj, PageResult presult) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_stu(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getGradeid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getGradeid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getSubjectid()!=null&&obj.getSubjectid().intValue()>0){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else
            sqlbuilder.append("NULL,");
        if(obj.getClassid()!=null&&obj.getClassid().intValue()>0){
            sqlbuilder.append("?");
            objList.add(obj.getClassid());
        }else
            sqlbuilder.append("NULL");
        sqlbuilder.append(")}");
        List<List<String>> list = this.executeResultProcedure(sqlbuilder.toString(),objList);
        return list;
    }

    @Override
    public List<ClassInfo> getAdminPerformanceTeaClass(Integer schoolid, Integer gradeid, Integer subjectid) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_getclass(");
        List<Object> objList=new ArrayList<Object>();
        if(schoolid!=null){
            sqlbuilder.append("?,");
            objList.add(schoolid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(gradeid!=null){
            sqlbuilder.append("?,");
            objList.add(gradeid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(subjectid!=null){
            sqlbuilder.append("?");
            objList.add(subjectid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<ClassInfo> list = this.executeResult_PROC(sqlbuilder.toString(),objList,null,ClassInfo.class,null);
        return list;
    }

    @Override
    public List<ClassInfo> getAdminPerformanceStuClass(Integer schoolid, Integer gradeid, Integer subjectid) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_stu_class(");
        List<Object> objList=new ArrayList<Object>();
        if(schoolid!=null){
            sqlbuilder.append("?,");
            objList.add(schoolid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(gradeid!=null){
            sqlbuilder.append("?,");
            objList.add(gradeid);
        }else{
            sqlbuilder.append("NULL,");
        }
        if(subjectid!=null){
            sqlbuilder.append("?");
            objList.add(subjectid);
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<ClassInfo> list = this.executeResult_PROC(sqlbuilder.toString(),objList,null,ClassInfo.class,null);
        return list;
    }

    @Override
    public List<Map<String, Object>> getAdminPerformanceStuCol(ClassInfo obj) {
        StringBuilder sqlbuilder=new StringBuilder("{CALL admin_performance_proc_stu_paper(");
        List<Object> objList=new ArrayList<Object>();
        if(obj.getGradeid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getGradeid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getSubjectid()!=null){
            sqlbuilder.append("?,");
            objList.add(obj.getSubjectid());
        }else{
            sqlbuilder.append("NULL,");
        }
        if(obj.getClassid()!=null){
            sqlbuilder.append("?");
            objList.add(obj.getClassid());
        }else{
            sqlbuilder.append("null");
        }
        sqlbuilder.append(")}");
        List<Map<String,Object>> list = this.executeResultListMap_PROC(sqlbuilder.toString(),objList);
        return list;
    }
}
