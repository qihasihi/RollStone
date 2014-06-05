
package  com.school.manager.inter;

import com.school.entity.CommentInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.PageResult;

import java.util.List;
import java.util.Map;

public interface ICommentManager  extends IBaseManager<CommentInfo> { 

	public List<Map<String,Object>> getAvgByClass(Long courseid,Integer classid,Integer classtype,Integer tchid);

    public List<CommentInfo> getListByClass(Long courseid,Integer classid,Integer classtype,Integer tchid,PageResult presult);
	
	public boolean doSupportOrOppose(String comment_id,boolean type);

    public List<CommentInfo>getResouceCommentList(CommentInfo commentInfo,PageResult pageResult);
    public List<CommentInfo>getResouceCommentTreeList(CommentInfo commentInfo,PageResult pageResult);
    public List<Map<String,Object>> getClassList(Long courseid,Integer teacherid,Integer type);
    public List<Map<String,Object>> getVirClassList(Long courseid,Integer teacherid,Integer type);
} 
