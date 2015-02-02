
package  com.school.manager.teachpaltform;

import java.util.List;
import java.util.UUID;

import com.school.entity.teachpaltform.TpTaskAllotInfo;
import com.school.util.UtilTool;
import jxl.Sheet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.school.dao.inter.teachpaltform.ITpTaskDAO;

import com.school.entity.teachpaltform.TpTaskInfo;
import com.school.dao.base.ICommonDAO;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.teachpaltform.ITpTaskManager;
import com.school.util.PageResult;

@Service
public class  TpTaskManager extends BaseManager<TpTaskInfo> implements ITpTaskManager  { 
	
	private ITpTaskDAO tptaskdao;
	
	@Autowired
	@Qualifier("tpTaskDAO")
	public void setTptaskdao(ITpTaskDAO tptaskdao) {
		this.tptaskdao = tptaskdao;
	}
	
	public Boolean doSave(TpTaskInfo tptaskinfo) {
		return this.tptaskdao.doSave(tptaskinfo);
	}
	
	public Boolean doDelete(TpTaskInfo tptaskinfo) {
		return this.tptaskdao.doDelete(tptaskinfo);
	}

	public Boolean doUpdate(TpTaskInfo tptaskinfo) {
		return this.tptaskdao.doUpdate(tptaskinfo);
	}
	
	public List<TpTaskInfo> getList(TpTaskInfo tptaskinfo, PageResult presult) {
		return this.tptaskdao.getList(tptaskinfo,presult);	
	}
	
	public List<Object> getSaveSql(TpTaskInfo tptaskinfo, StringBuilder sqlbuilder) {
		return this.tptaskdao.getSaveSql(tptaskinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(TpTaskInfo tptaskinfo, StringBuilder sqlbuilder) {
		return this.tptaskdao.getDeleteSql(tptaskinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(TpTaskInfo tptaskinfo, StringBuilder sqlbuilder) {
		return this.tptaskdao.getUpdateSql(tptaskinfo,sqlbuilder);
	}

	public TpTaskInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}
    /**
     * 得到同步的SQL
     * @param entity  对象实体
     * @param sqlbuilder  传出的SQL语句，必须实例化后
     * @return
     */
    public List<Object> getSynchroSql(TpTaskInfo entity,StringBuilder sqlbuilder){
        return this.tptaskdao.getSynchroSql(entity,sqlbuilder);
    }

    @Override
    public Boolean doSaveTaskMsg(TpTaskInfo t) {
        return this.tptaskdao.doSaveTaskMsg(t);
    }

    @Override
    public List<TpTaskInfo> getDoTaskResourceId(TpTaskInfo obj) {
        return this.tptaskdao.getDoTaskResourceId(obj);
    }

    @Override
	protected ICommonDAO<TpTaskInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return tptaskdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

    public List<TpTaskInfo> getTaskReleaseList(TpTaskInfo t, PageResult presult) {
        return this.tptaskdao.getTaskReleaseList(t,presult);
    }

    @Override
    public List<TpTaskInfo> getTaskDetailList(TpTaskInfo t, PageResult presult) {
        return this.tptaskdao.getTaskDetailList(t,presult);
    }

    public List<TpTaskInfo> getListbyStu(TpTaskInfo t, PageResult presult) {
        return this.tptaskdao.getListbyStu(t,presult);
    }

    @Override
    public List<TpTaskInfo> getUnionListbyStu(TpTaskInfo t, PageResult presult) {
        return this.tptaskdao.getUnionListbyStu(t,presult);
    }

    /**
     * 删除任务时，清除任务积分，删除完成记录
     * @param taskid
     * @param sqlbuilder
     * @return
     */
    public List<Object> getDelTpStuTaskScore(final Long taskid, StringBuilder sqlbuilder){
        return this.tptaskdao.getDelTpStuTaskScore(taskid,sqlbuilder);
    }

    @Override
    public List<TpTaskInfo> getTaskRemindList(TpTaskInfo t, PageResult presult) {
        return this.tptaskdao.getTaskRemindList(t,presult);
    }
}

