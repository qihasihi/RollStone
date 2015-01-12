package com.school.control.teachpaltform.interactive;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.interactive.TpThemeReplyInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.inter.teachpaltform.interactive.ITpThemeReplyManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value="/tpthemereply")
public class TpThemeReplyController extends BaseController<TpThemeReplyInfo>{

    @Autowired
    private ITpTopicThemeManager tpTopicThemeManager;
    @Autowired
    private ITpThemeReplyManager tpThemeReplyManager;
	/**
	 * ����
	 * @param request
	 * @param response
	 * @throws Exception
	 */
@RequestMapping(params="m=doAddReply",method=RequestMethod.POST)
	public void doAddReply(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TpThemeReplyInfo entity=this.getParameter(request, TpThemeReplyInfo.class);
		JsonEntity jsonEntity=new JsonEntity();
		
		if(entity.getThemeid()==null||entity.getReplycontent()==null){
			jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
		}
		//��֤�Ƿ���ڸ�����
		TpTopicThemeInfo theme=new TpTopicThemeInfo();
		theme.setThemeid(entity.getThemeid());
		List<TpTopicThemeInfo> themeList=this.tpTopicThemeManager.getList(theme, null);
		if(themeList==null||themeList.size()<1){
		    jsonEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jsonEntity.toJSON());return;
		}
		Long nextid=this.tpThemeReplyManager.getNextId(true);
		entity.setUserid(Long.parseLong(this.logined(request).getUserid().toString()));
		entity.setTopicid(themeList.get(0).getTopicid());
		entity.setReplyid(nextid);
		List<String> sqlArrayList=new ArrayList<String>();
		List<List<Object>> objArrayList=new ArrayList<List<Object>>(); 
		List<Object> objList=null;
		StringBuilder sqlbuilder=new StringBuilder();
		objList=this.tpThemeReplyManager.getSaveSql(entity, sqlbuilder);
		if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
			sqlArrayList.add(sqlbuilder.toString());
			objArrayList.add(objList);
		}
		if(entity.getReplycontent()!=null){
			this.tpTopicThemeManager.getArrayUpdateLongText("tp_theme_reply_info", "REPLY_ID", "reply_content"
	                    , entity.getReplycontent(), nextid.toString(), sqlArrayList, objArrayList);
	    }
    //�������ظ�
    TpTopicThemeInfo them=new TpTopicThemeInfo();
    them.setThemeid(entity.getThemeid());
    them.setLastfabiao(this.logined(request).getRealname()+" "+UtilTool.DateConvertToString(new Date(), UtilTool.DateType.type4));
    //������󷢲�
    sqlbuilder=new StringBuilder();
    objList=this.tpTopicThemeManager.getUpdateSql(them,sqlbuilder);
    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
        sqlArrayList.add(sqlbuilder.toString());
        objArrayList.add(objList);
    }
    //������
    if(entity.getReplyid()!=null){
        sqlbuilder=new StringBuilder();
        objList=this.tpTopicThemeManager.getUpdateNumAdd("tp_topic_theme_info","theme_id","pinglunshu",entity.getThemeid().toString(),1,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
    }

		if(objArrayList.size()!=0&&objArrayList.size()==sqlArrayList.size()){
			if(this.tpThemeReplyManager.doExcetueArrayProc(sqlArrayList, objArrayList)){
				jsonEntity.setType("success");
				jsonEntity.setMsg("����ɹ�!");
			}else{
				jsonEntity.setMsg("����ʧ��!ԭ��δ֪!");
			}
		}
		response.getWriter().print(jsonEntity.toJSON());
		
	}
	
	/**
	 * ��ѯ������JSON,�������themeid
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=getReplyListJSON",method=RequestMethod.POST)
	public void getReplyListJSON(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TpThemeReplyInfo entity=this.getParameter(request, TpThemeReplyInfo.class);
		JsonEntity jeEntity=new JsonEntity();
		if(entity.getThemeid()==null){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
		}
		//�õ���ز�ѯ 
		PageResult presult=this.getPageResultParameter(request);
		List<TpThemeReplyInfo> replyList=this.tpThemeReplyManager.getList(entity, presult);
		presult.setList(replyList);
		jeEntity.setPresult(presult);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
	}

    /**
     * ����themeid��themeid string�õ����ۼ��ظ�
     * @param request
     * @throws Exception
     */
    @RequestMapping(params="m=getReplyByThemeId",method=RequestMethod.POST)
    public void getReplyListByThemeId(HttpServletRequest request,HttpServletResponse response) throws Exception{

        String themeidStr=request.getParameter("themeidstr");
        JsonEntity jeEntity=new JsonEntity();
        if(themeidStr==null|| themeidStr.length()<1){
            jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
        }
        PageResult presult=this.getPageResultParameter(request);
        //��ѯ
        String totalStr=null;
        List<TpThemeReplyInfo> replyList=this.tpThemeReplyManager.getListByThemeIdStr(themeidStr,1, presult); //�����
//        if(presult.getList()!=null&&presult.getList().size()>0&&presult.getList().iterator().next()!=null)
//            totalStr=presult.getList().iterator().next().toString();
        presult.getList().add(replyList);

        List<TpThemeReplyInfo> plList=this.tpThemeReplyManager.getListByThemeIdStr(themeidStr,2, null); //�����
        presult.getList().add(plList);
//        if(totalStr!=null)
//            presult.getList().add(totalStr);
        jeEntity.setPresult(presult);
        jeEntity.setType("success");
        response.getWriter().print(jeEntity.toJSON());
    }
	/**
	 * ִ��ɾ��
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doDeleteReply",method=RequestMethod.POST)
	public void doDeleteReply(HttpServletRequest request,HttpServletResponse response) throws Exception{
		TpThemeReplyInfo entity=this.getParameter(request, TpThemeReplyInfo.class);
		JsonEntity jeEntity=new JsonEntity();
		if(entity.getReplyid()==null){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jeEntity.toJSON());
            return;
		}
		//��ѯ�Ƿ񻹴���
		TpThemeReplyInfo replyInfo=new TpThemeReplyInfo();
		replyInfo.setReplyid(entity.getReplyid());
		List<TpThemeReplyInfo> replyList=this.tpThemeReplyManager.getList(replyInfo, null);
		if(replyList==null||replyList.size()<1){
			jeEntity.setMsg(UtilTool.msgproperty.getProperty("ERR_NO_DATE"));
            response.getWriter().print(jeEntity.toJSON());return;		
		}
		//��֤�Ƿ���ͬһ����
//		if(replyList.get(0).getUserid()!=null&&replyList.get(0).getUserid().intValue()!=this.logined(request).getUserid().intValue()){
//			jeEntity.setMsg(UtilTool.msgproperty.getProperty("NO_SERVICE_RIGHT"));
//			response.getWriter().print(jeEntity.toJSON());return;
//		}
		
		//ִ��ɾ��
		
		List<List<Object>> objArrayList=new ArrayList<List<Object>>();
		List<String> sqlArrayList=new ArrayList<String>();
		List<Object> objList=null;
		StringBuilder sqlbuilder=new StringBuilder();
		objList=this.tpThemeReplyManager.getDeleteSql(replyInfo, sqlbuilder);
		if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
			sqlArrayList.add(sqlbuilder.toString());
			objArrayList.add(objList);
		}
        //ִ��theme-1
        //������
        sqlbuilder=new StringBuilder();
        objList=this.tpTopicThemeManager.getUpdateNumAdd("tp_topic_theme_info","theme_id","pinglunshu",replyList.get(0).getThemeid().toString(),2,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
		//ִ��
		if(objArrayList.size()!=0&&objArrayList.size()==sqlArrayList.size()){
			if(this.tpThemeReplyManager.doExcetueArrayProc(sqlArrayList, objArrayList)){
				jeEntity.setType("success");
				jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
			}else{
				jeEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			}
		}
		response.getWriter().print(jeEntity.toJSON());
		
	}	
}
