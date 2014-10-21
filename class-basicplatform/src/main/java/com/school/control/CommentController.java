package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.CommentInfo;
import com.school.entity.DictionaryInfo;
import com.school.entity.ScoreInfo;
import com.school.entity.UserInfo;
import com.school.entity.resource.MyInfoCloudInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.TpCourseClass;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.manager.CommentManager;
import com.school.manager.DictionaryManager;
import com.school.manager.ScoreManager;
import com.school.manager.UserManager;
import com.school.manager.inter.ICommentManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.IScoreManager;
import com.school.manager.inter.IUserManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.ITpCourseClassManager;
import com.school.manager.inter.teachpaltform.ITpCourseManager;
import com.school.manager.inter.teachpaltform.award.ITpStuScoreLogsManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.TpCourseClassManager;
import com.school.manager.teachpaltform.TpCourseManager;
import com.school.manager.teachpaltform.award.TpStuScoreLogsManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/commoncomment")
public class CommentController extends BaseController<CommentInfo> {
    private ICommentManager commentManager;
    private IResourceManager resourceManager;
    private ITpCourseManager tpCourseManager;
    private IScoreManager scoreManager;
    private IUserManager userManager;
    private ITpCourseClassManager tpCourseClassManager;
    private IDictionaryManager dictionaryManager;
    private ITpStuScoreLogsManager tpStuScoreLogsManager;
    public CommentController(){
        tpStuScoreLogsManager=this.getManager(TpStuScoreLogsManager.class);
        this.commentManager=this.getManager(CommentManager.class);
        this.resourceManager=this.getManager(ResourceManager.class);
        this.tpCourseManager=this.getManager(TpCourseManager.class);
        this.scoreManager=this.getManager(ScoreManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.tpCourseClassManager=this.getManager(TpCourseClassManager.class);
        this.dictionaryManager=this.getManager(DictionaryManager.class);
    }
	/**
	 * 获取List
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxlist", method = RequestMethod.POST)
	public void AjaxGetList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		CommentInfo commentinfo = this.getParameter(request, CommentInfo.class);
		PageResult pageresult = this.getPageResultParameter(request);
        commentinfo.setCurrentLoginRef(this.logined(request).getRef());
		List<CommentInfo> commentList = commentManager.getList(commentinfo,
				pageresult);
		pageresult.setList(commentList);
		JsonEntity je = new JsonEntity();
		je.setType("success");
		je.setPresult(pageresult);
		response.getWriter().print(je.toJSON());
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxsave", method = RequestMethod.POST)
	public void doSubmitAddComment(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		UserInfo user = this.logined(request);

		CommentInfo commentinfo = this.getParameter(request, CommentInfo.class);
		if (commentinfo == null || commentinfo.getCommenttype() == null
				|| commentinfo.getCommentobjectid() == null) {
			commentinfo.setCommentuserid(user.getUserid());
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		commentinfo.setCommentuserid(this.logined(request).getUserid());
		commentinfo.setCommentid(this.commentManager.getNextId());

        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArraylist=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.commentManager.getSaveSql(commentinfo,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
            sqlArrayList.add(sqlbuilder.toString());
            objArraylist.add(objList);
        }
        //如果是资源评论，则修改评论数量
        if(commentinfo.getCommenttype()==1){
            sqlbuilder=new StringBuilder();
            objList=this.resourceManager.getUpdateNumAdd("rs_resource_info","res_id","COMMENTNUM",commentinfo.getCommentobjectid(),1,sqlbuilder);
            if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                sqlArrayList.add(sqlbuilder.toString());
                objArraylist.add(objList);
            }
            ResourceInfo rs=new ResourceInfo();
            rs.setResid(Long.parseLong(commentinfo.getCommentobjectid()));
            List<ResourceInfo> rsList=this.resourceManager.getList(rs,null);
            if(rsList!=null&&rsList.size()>0){
                sqlbuilder=new StringBuilder();
                MyInfoCloudInfo mc=new MyInfoCloudInfo();
                mc.setTargetid(Long.parseLong(commentinfo.getCommentobjectid().toString()));
                mc.setUserid(Long.parseLong(rsList.get(0).getUserid().toString()));
                if(commentinfo.getAnonymous()==null||commentinfo.getAnonymous()!=1)
                    mc.setData(this.logined(request).getRealname() + " 评论了你的资源 <a style=\"color:#0066CC\" href=\"resource?m=todetail&resid="+commentinfo.getCommentobjectid()+"\">#ETIANTIAN_SPLIT#</a> !");
                else
                    mc.setData("我的资源 <a style=\"color:#0066CC\"  href=\"resource?m=todetail&resid="+commentinfo.getCommentobjectid()+"\">#ETIANTIAN_SPLIT#</a> 被匿名评论了!");
                mc.setType(1);
                objList=this.resourceManager.getMyInfoCloudSaveSql(mc,sqlbuilder);
                if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0&&objList!=null){
                    sqlArrayList.add(sqlbuilder.toString());
                    objArraylist.add(objList);
                }
            }
        }
        if (sqlArrayList.size()>0&&sqlArrayList.size()==objArraylist.size()
                &&commentManager.doExcetueArrayProc(sqlArrayList,objArraylist)) {
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		} else {
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 对评论顶或踩
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "m=supportOrOppose", method = RequestMethod.POST)
	public void doSupportOrOppose(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
    		String type = request.getParameter("type");
		CommentInfo commentinfo = this.getParameter(request, CommentInfo.class);
		if (commentinfo == null || commentinfo.getCommentid() == null
				|| type == null
				|| (!type.equals("1")&&!type.equals("0"))){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		
		if (commentManager.doSupportOrOppose(commentinfo.getCommentid(), type.equals("1")?true:false)) {
            //添加记录
            this.commentManager.executeAddOperateLog(
                    this.logined(request).getRef(),"comment_info",commentinfo.getCommentid().toString(),null,null,"SupportOrOppose"
                    ,this.logined(request).getRealname()+"对"+commentinfo.getCommentid()+"进行了踩，赞的操作"
            );

			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			je.setType("success");
		} else {
			je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
		}
		response.getWriter().print(je.toJSON());
	}
	
	/**
	 * 添加
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(params = "m=ajaxTCCommentSave", method = RequestMethod.POST)
	public void AddCommentForTeacherCourse(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		UserInfo user = this.logined(request);

        List<String> sqlListArray = new ArrayList<String>();
        List<List<Object>> objListArray = new ArrayList<List<Object>>();
        List<Object> objList = null;
        StringBuilder sql = null;
        String classid=request.getParameter("classid");
		CommentInfo commentinfo = this.getParameter(request, CommentInfo.class);
		if (commentinfo == null || commentinfo.getCommenttype() == null
				|| commentinfo.getCommentobjectid() == null
				|| commentinfo.getScore() == null||classid==null||classid.trim().length()<1) {
			commentinfo.setCommentuserid(user.getUserid());
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		commentinfo.setCommentuserid(this.logined(request).getUserid());
		commentinfo.setCommentid(this.commentManager.getNextId());
        sql = new StringBuilder();
        objList = this.commentManager.getSaveSql(commentinfo, sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);

        ScoreInfo si = new ScoreInfo();
        si.setScoreid(this.scoreManager.getNextId());
        si.setScoretype(2);
        si.setScoreobjectid(commentinfo.getCommentobjectid());
        si.setScore(commentinfo.getScore());
        si.setScoreuserid(commentinfo.getCommentuserid());
        si.setCommentid(commentinfo.getCommentid());
        sql = new StringBuilder();
        objList = this.scoreManager.getSaveSql(si, sql);
        sqlListArray.add(sql.toString());
        objListArray.add(objList);

        if (sqlListArray.size() > 0 && objListArray.size() > 0) {
            if (this.tpCourseManager.doExcetueArrayProc(sqlListArray, objListArray)) {
                TpCourseInfo tc = new TpCourseInfo();
                tc.setCourseid(Long.parseLong(commentinfo.getCommentobjectid().toString()));
                tc.setAvgscore(Float.parseFloat(commentinfo.getScore().toString()));
                this.tpCourseManager.doCommentAndScore(tc);
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
                je.setType("success");
                //添加奖励加分
                Integer jid=this.logined(request).getEttuserid()==null?null:this.logined(request).getEttuserid();
                if(this.tpStuScoreLogsManager.awardStuScore(tc.getCourseid(),Long.parseLong(classid.trim()),null
                        ,Long.parseLong(this.logined(request).getUserid()+""),jid+"",10,this.logined(request).getDcschoolid(),2)){
                    je.setMsg("恭喜您,获得了1积分和1蓝宝石!");
                }else{
                    System.out.println("comment course"+tc.getCourseid()+" error!award");
                }
            } else {
                je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
            }
        } else {
            je.setMsg(UtilTool.msgproperty
                    .getProperty("ARRAYEXECUTE_NOT_EXECUTESQL"));
        }
		response.getWriter().print(je.toJSON());
	}

    /**
     * 添加专题资源评论
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(params = "m=doSubResourceComment", method = RequestMethod.POST)
    public void doSubResourceComment(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        CommentInfo commentinfo = this.getParameter(request, CommentInfo.class);
        if (commentinfo == null
                || commentinfo.getCommentobjectid() == null
                || commentinfo.getCommentcontext() == null) {
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        List<List<Object>>objListArray=new ArrayList<List<Object>>();
        List<String>sqlListArray=new ArrayList<String>();
        StringBuilder sql=null;
        List<Object>objList=null;

        commentinfo.setCommentuserid(this.logined(request).getUserid());
        commentinfo.setCommentid(this.commentManager.getNextId());
        commentinfo.setCommentparentid("0");
        commentinfo.setCommenttype(1);
        sql=new StringBuilder();
        objList=commentManager.getSaveSql(commentinfo,sql);
        if(objList.size()>0&&sql!=null&&sql.length()>0){
            objListArray.add(objList);
            sqlListArray.add(sql.toString());
        }
        if(commentinfo.getCommenttype()==1){
            sql=new StringBuilder();
            objList=this.resourceManager.getUpdateNumAdd("rs_resource_info","res_id","COMMENTNUM",commentinfo.getCommentobjectid(),1,sql);
            if(sql!=null&&sql.toString().trim().length()>0&&objList!=null){
                sqlListArray.add(sql.toString());
                objListArray.add(objList);
            }
        }

        if (commentManager.doExcetueArrayProc(sqlListArray,objListArray)) {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            je.setType("success");
        } else {
            je.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        }
        response.getWriter().print(je.toJSON());
    }

    /**
     * 查询专题资源评论
     * @throws Exception
     */
    @RequestMapping(params="m=getCommentList",method=RequestMethod.POST)
    public void getCommentList(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je =new JsonEntity();
        String courseid=request.getParameter("courseid");
        String resid=request.getParameter("resdetailid");
        if(resid==null||courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        PageResult presult=this.getPageResultParameter(request);
        presult.setOrderBy(" t.c_time  ");
        CommentInfo t=new CommentInfo();
        t.setCommentobjectid(resid);
        t.setCommenttype(1);
        //得到二级回复
        List<CommentInfo>commentReplyList=this.commentManager.getResouceCommentTreeList(t,presult);
        //得到一级评论
        t.setCommentparentid("0");
        presult.setOrderBy(" t.c_time desc");
        List<CommentInfo>commentList=this.commentManager.getResouceCommentList(t, presult);

        presult.getList().add(commentList);
        presult.getList().add(commentReplyList);
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    /**
     * 专题资源回复
     * @throws Exception
     */
    @RequestMapping(params="m=doReplyResource",method=RequestMethod.POST)
    public void doReplyResource(HttpServletRequest request,HttpServletResponse response)throws Exception{
        JsonEntity je= new JsonEntity();
        String  content =request.getParameter("replycontent");
        String  courseid =request.getParameter("courseid");
        String  parentcommentid =request.getParameter("parentcommentid");

        String  touserid =request.getParameter("touserid");
        String  resid=request.getParameter("resdetailid");

        if(parentcommentid==null||parentcommentid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(touserid==null||touserid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(courseid==null||courseid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(resid==null||resid.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }
        if(content==null||content.trim().length()<1){
            je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(je.toJSON());
            return;
        }

        String nextid=this.commentManager.getNextId();
        CommentInfo commentinfo=new CommentInfo();
        commentinfo.setCommentid(nextid);
        commentinfo.setCommentuserid(this.logined(request).getUserid());
        commentinfo.setReportuserid(this.logined(request).getUserid());
        commentinfo.setCommentcontext(content.trim());
        commentinfo.setCommentparentid(parentcommentid);
        commentinfo.setCommenttype(1);
        commentinfo.setCommentobjectid(resid);
        //touserid
        if(touserid!=null){
            //得到真实姓名
            UserInfo touser=new UserInfo();
            touser.setUserid(Integer.parseInt(touserid));
            List<UserInfo> touserList=this.userManager.getList(touser, null);
            if(touserList!=null&&touserList.size()>0){
                commentinfo.setTorealname(touserList.get(0).getRealname());
            }
            commentinfo.setTouserid(touserid);
        }
        if(this.commentManager.doSave(commentinfo)){
            je.setType("success");
            je.setMsg("回复成功!");
            //得到最新的数据
            commentinfo=new CommentInfo();
            commentinfo.setCommentid(nextid);
            List<CommentInfo> resList=this.commentManager.getResouceCommentList(commentinfo, null);
            if(resList==null||resList.size()<1){
                je.setMsg("未正常添加!请刷新确认!");
                je.setType("error");
            }
            je.getObjList().add(resList.get(0));
        }else
            je.setMsg("回复失败!");
        response.getWriter().print(je.toJSON());

    }

	@RequestMapping(params = "m=toCourseComment", method = RequestMethod.GET)
	public ModelAndView toCourseComment(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {

		UserInfo user = this.logined(request);
		String cid = request.getParameter("cid");
		JsonEntity jeEntity=new JsonEntity();

        if(cid==null||cid.length()==0){
            jeEntity.setMsg("参数错误！");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return null;
        }

		TpCourseClass tcc = new TpCourseClass();
		tcc.setCourseid(Long.parseLong(cid));
		List<TpCourseClass> tcclist = this.tpCourseClassManager.getList(tcc, null);

        if(tcclist==null||tcclist.size()==0){
            jeEntity.setMsg("无法找到待评价专题数据！");
            response.getWriter().print(jeEntity.getAlertMsgAndCloseWin());return null;
        }
		mp.put("tcc", tcclist.get(0));
        mp.put("commentobjectid", tcclist.get(0).getCourseid());
		Map<String, Object> dm = this.dictionaryManager
				.getDicMapByType(CommentInfo.DICTIONARY_TYPE_OF_COMMENT);
		if (dm != null) {
			mp.put("commenttype", dm.get("专题评论"));
			CommentInfo ci = new CommentInfo();
			ci.setCommenttype(Integer.parseInt(dm.get("专题评论").toString()));
			ci.setCommentuserid(user.getUserid());
			ci.setCommentobjectid(tcc.getCourseid().toString());
			List list = this.commentManager.getList(ci, null);
			if (list != null && list.size() > 0) {
				mp.put("result", "commented");
			}
		} else {
			mp.put("result", "errors");
		}
		return new ModelAndView("/teachpaltform/course/newCourseComment", mp);
	}

    /**
     * 获取班级专题评论List
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(params = "m=ajaxCourseCLasslist", method = RequestMethod.POST)
    public void ajaxCourseCLasslist(HttpServletRequest request,HttpServletResponse response) throws Exception {
        PageResult pageresult = this.getPageResultParameter(request);
        String courseid=request.getParameter("courseid");
        String classid = request.getParameter("classid");
        String classtype = request.getParameter("classtype");
        String tchid = request.getParameter("tchid");
        if(classtype==null){
            classtype="0";
            classid="0";
            tchid="0";
        }

        List<CommentInfo> commentList = commentManager.getListByClass(Long.parseLong(courseid),
                Integer.parseInt(classid),
                Integer.parseInt(classtype),
                Integer.parseInt(tchid),
                pageresult);

        List<Map<String,Object>> avgList = commentManager.getAvgByClass(Long.parseLong(courseid),
                Integer.parseInt(classid),
                Integer.parseInt(classtype),
                Integer.parseInt(tchid));
        List l = new ArrayList();
        l.add(commentList);
        Map<String,Object> asMap = new HashMap<String, Object>();
        asMap.put("AVG_SCORE",0);
        if(avgList!=null && avgList.size()>0)
           l.add(avgList.get(0));
        else
           l.add(new HashMap(asMap));
        pageresult.setList(l);
        JsonEntity je = new JsonEntity();
        je.setType("success");
        je.setPresult(pageresult);
        response.getWriter().print(je.toJSON());
    }

    /**
     * 删除回复
     * @throws Exception

    @RequestMapping(params="doDeleteRepley",method=RequestMethod.POST)
    public void doDeleteRepley(HttpServletRequest request,HttpServletResponse response) throws Exception{
        JsonEntity jeEntity=new JsonEntity();
        String ref=request.getParameter("ref");
        String type=request.getParameter("type");
        if(ref==null||ref.trim().length()<1
                ||type==null||type.trim().length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        TpResCommentInfo resInfo=new TpResCommentInfo();
        resInfo.setRef(ref);
        resInfo.setType(Integer.parseInt(type));
        List<TpResCommentInfo> resList=this.tpResCommentManager.getList(resInfo, null);
        if(resList==null||resList.size()<1){
            jeEntity.setMsg("您要删除的数据，已经不存在，请刷新页面后重试!");
            response.getWriter().print(jeEntity.toJSON());return;
        }
        //执行删除
        if(this.tpResCommentManager.doDelete(resInfo)){
            jeEntity.setType("success");
            jeEntity.setMsg("删除成功!");
        }else
            jeEntity.setMsg("删除失败!");
        response.getWriter().print(jeEntity.toJSON());

    } */





    /*
	@RequestMapping(params = "m=toCourseCommentList", method = RequestMethod.GET)
	public ModelAndView toCourseCommentList(HttpServletRequest request,
			HttpServletResponse response, ModelMap mp) throws Exception {
		String objectid = request.getParameter("objectid");
		Map<String, Object> dm = this.dictionaryManager
				.getDicMapByType(CommentInfo.DICTIONARY_TYPE_OF_COMMENT);
		if (dm != null && dm.get("专题评论") != null && objectid != null) {
			TeacherCourseInfo tc = new TeacherCourseInfo();
			tc.setCourseid(objectid);
			List<TeacherCourseInfo> cList = this.teacherCourseManager.getList(
					tc, null);
			mp.put("commentobjectid", objectid);
			mp.put("commenttype", dm.get("专题评论"));
			mp.put("course", cList.get(0));
			tc = new TeacherCourseInfo();
			tc.setUserid(this.logined(request).getRef());
			tc.setTermid(cList.get(0).getTermid());
			List<TeacherCourseInfo> courseList = this.teacherCourseManager
					.getList(tc, null);
			mp.put("courseList", courseList);
		}
		return new ModelAndView("/comment/courseCommentList", mp);
	}

	@RequestMapping(params = "m=getCourseCommentListAjax", method = RequestMethod.POST)
	public void getCourseCommentListAjax(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		CommentInfo commentinfo = this.getParameter(request, CommentInfo.class);
		String classid = request.getParameter("classid");
		if (classid != null) {
			List<CommentInfo> comlist = this.commentManager.getListByClass(
					commentinfo, null);
			if (comlist != null && comlist.size() > 0) {
				List<Map<String, Object>> lm = this.commentManager
						.getAvgByClass(Integer.parseInt(classid),
								commentinfo.getCommenttype(),
								commentinfo.getCommentobjectid());
				je.setMsg((lm != null && lm.size() > 0 ? lm.get(0)
						.get("AVG_SCORE").toString() : "0"));
				je.setType("success");
				je.setObjList(comlist);
			}
			response.getWriter().print(je.toJSON());
		}
	}
	*/
}
