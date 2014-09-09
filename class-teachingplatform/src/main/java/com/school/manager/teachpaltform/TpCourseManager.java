
package  com.school.manager.teachpaltform;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.teachpaltform.ITpCourseDAO;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class  TpCourseManager extends BaseManager<TpCourseInfo> implements ITpCourseManager  {

	private ITpCourseDAO tpcoursedao;

	@Autowired
	@Qualifier("tpCourseDAO")
	public void setTpcoursedao(ITpCourseDAO tpcoursedao) {
		this.tpcoursedao = tpcoursedao;
	}

	public Boolean doSave(TpCourseInfo tpcourseinfo) {
		return this.tpcoursedao.doSave(tpcourseinfo);
	}

	public Boolean doDelete(TpCourseInfo tpcourseinfo) {
		return this.tpcoursedao.doDelete(tpcourseinfo);
	}

	public Boolean doUpdate(TpCourseInfo tpcourseinfo) {
		return this.tpcoursedao.doUpdate(tpcourseinfo);
	}

	public List<TpCourseInfo> getList(TpCourseInfo tpcourseinfo, PageResult presult) {
		return this.tpcoursedao.getList(tpcourseinfo,presult);
	}

	public List<Object> getSaveSql(TpCourseInfo tpcourseinfo, StringBuilder sqlbuilder) {
		return this.tpcoursedao.getSaveSql(tpcourseinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpCourseInfo tpcourseinfo, StringBuilder sqlbuilder) {
		return this.tpcoursedao.getDeleteSql(tpcourseinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpCourseInfo tpcourseinfo, StringBuilder sqlbuilder) {
		return this.tpcoursedao.getUpdateSql(tpcourseinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return this.tpcoursedao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpCourseInfo entity,StringBuilder sqlbuilder){
        return this.tpcoursedao.getSynchroSql(entity,sqlbuilder);
    }
	public TpCourseInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<TpCourseInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tpcoursedao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return null;
	}

    public List<TpCourseInfo> getTchCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getTchCourseList(tpcourseinfo,presult);
    }

    @Override
    public List<TpCourseInfo> getCalanderCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getCalanderCourseList(tpcourseinfo,presult);
    }

    public List<TpCourseInfo> getStuCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getStuCourseList(tpcourseinfo,presult);
    }



    public List<TpCourseInfo> getTsList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getTsList(tpcourseinfo,presult);
    }

    public List<TpCourseInfo> getCouresResourceList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getCouresResourceList(tpcourseinfo,presult);
    }

    public List<TpCourseInfo> getCourseQuestionList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getCourseQuestionList(tpcourseinfo,presult);
    }

    public Boolean doCommentAndScore(TpCourseInfo tpcourseinfo) {
        return tpcoursedao.doCommentAndScore(tpcourseinfo);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<TpCourseInfo> getCourseList(TpCourseInfo tpcourseinfo, PageResult presult) {
        return tpcoursedao.getCourseList(tpcourseinfo,presult);
    }

    @Override
    public List<Map<String,Object>> getRelatedCourseList(Integer materialid, Integer userid,String coursename) {
        return tpcoursedao.getRelatedCourseList(materialid,userid,coursename);
    }
    /**
     * 得到专题积分是否录取完毕
     * @param clsid
     * @param subjectid
     * @param carrayid
     * @param garrayid
     * @param roletype  NULL OR 1:老师  2：学生
     * @return
     * @author zhengzhou
     */
    public List<Map<String,Object>> getCourseScoreIsOver(final Integer clsid,final Integer subjectid,final String carrayid,String garrayid, Integer roletype){
        return tpcoursedao.getCourseScoreIsOver(clsid, subjectid, carrayid, garrayid, roletype);
    }
    /**
     * 得到云端共享的集合
     * @param tpcourseinfo
     * @param presult
     * @return
     */
    public List<TpCourseInfo> getShareList(TpCourseInfo tpcourseinfo,PageResult presult){
        return tpcoursedao.getShareList(tpcourseinfo,presult);
    }
    public boolean doUpdateShareCourse(){
        return tpcoursedao.doUpdateShareCourse();
    }

    @Override
    public List<Map<String, Object>> getCourseCalendar(Integer usertype, Integer userid, Integer dcschoolid, String year, String month, String gradeid, String subjectid,Integer classid) {
        return tpcoursedao.getCourseCalendar(usertype,userid,dcschoolid,year,month,gradeid,subjectid,classid);
    }

}

