
package  com.school.manager;

import com.school.dao.base.ICommonDAO;
import com.school.dao.inter.ICommentDAO;
import com.school.entity.CommentInfo;
import com.school.manager.base.BaseManager;
import com.school.manager.inter.ICommentManager;
import com.school.util.PageResult;
import jxl.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class  CommentManager extends BaseManager<CommentInfo> implements ICommentManager  { 
	
	private ICommentDAO commentdao;
	
	@Autowired
	@Qualifier("commentDAO")
	public void setCommentdao(ICommentDAO commentdao) {
		this.commentdao = commentdao;
	}
	
	public Boolean doSave(CommentInfo commentinfo) {
		return commentdao.doSave(commentinfo);
	}
	
	public Boolean doDelete(CommentInfo commentinfo) {
		return commentdao.doDelete(commentinfo);
	}

	public Boolean doUpdate(CommentInfo commentinfo) {
		return commentdao.doUpdate(commentinfo);
	}
	
	public List<CommentInfo> getList(CommentInfo commentinfo, PageResult presult) {
		return commentdao.getList(commentinfo,presult);	
	}
	
	public List<Object> getSaveSql(CommentInfo commentinfo, StringBuilder sqlbuilder) {
		return commentdao.getSaveSql(commentinfo,sqlbuilder);
	}

	public List<Object> getDeleteSql(CommentInfo commentinfo, StringBuilder sqlbuilder) {
		return commentdao.getDeleteSql(commentinfo,sqlbuilder);
	}

	public List<Object> getUpdateSql(CommentInfo commentinfo, StringBuilder sqlbuilder) {
		return commentdao.getUpdateSql(commentinfo,sqlbuilder);
	}

	public Boolean doExcetueArrayProc(List<String> sqlArrayList,
			List<List<Object>> objArrayList) {
		return commentdao.doExcetueArrayProc(sqlArrayList,objArrayList);
	}
	
	public CommentInfo getOfExcel(Sheet rs, int cols, int d, String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ICommonDAO<CommentInfo> getBaseDAO() {
		// TODO Auto-generated method stub
		return commentdao;
	}

	@Override
	public String getNextId() {
		// TODO Auto-generated method stub
		return UUID.randomUUID().toString();
	}

	public List<Map<String,Object>> getAvgByClass(Long courseid,Integer classid,Integer classtype,Integer tchid) {
		// TODO Auto-generated method stub
		return this.commentdao.getAvgByClass(courseid,classid, classtype, tchid);
	}

    public List<CommentInfo> getListByClass(Long courseid,Integer classid,Integer classtype,Integer tchid,PageResult presult) {
		// TODO Auto-generated method stub
		return this.commentdao.getListByClass(courseid,classid,classtype,tchid, presult);
	}

	public boolean doSupportOrOppose(String comment_id, boolean type) {
		// TODO Auto-generated method stub
		return this.commentdao.doSupportOrOppose(comment_id, type);
	}

    public List<CommentInfo> getResouceCommentList(CommentInfo commentInfo, PageResult pageResult) {
        return this.commentdao.getResouceCommentList(commentInfo,pageResult);
    }

    public List<CommentInfo> getResouceCommentTreeList(CommentInfo commentInfo, PageResult pageResult) {
        return this.commentdao.getResouceCommentTreeList(commentInfo,pageResult);
    }

    @Override
    public List<Map<String, Object>> getClassList(Long courseid,Integer teacherid,Integer type) {
        return this.commentdao.getClassList(courseid,teacherid,type);
    }

    @Override
    public List<Map<String, Object>> getVirClassList(Long courseid,Integer teacherid,Integer type) {
        return this.commentdao.getVirClassList(courseid,teacherid,type);
    }
}

