package com.school.control.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.resource.IStoreManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.resource.StoreManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.resource.StoreInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@RequestMapping(value="/store") 
public class StoreController extends BaseController<StoreInfo>{
    private IStoreManager storeManager;
    private IResourceManager resourceManager;

    public StoreController(){
        this.storeManager=this.getManager(StoreManager.class);
        this.resourceManager=this.getManager(ResourceManager.class);
    }

	/**
	 * 添加
	 * @param request
	 * @param response
	 * @param mp
	 * @throws Exception
	 */
	@RequestMapping(params="m=doadd",method=RequestMethod.POST)
	public void doAddStore(HttpServletRequest request,HttpServletResponse response,ModelAndView mp) throws Exception{
		JsonEntity jEntity=new JsonEntity();
		
		StoreInfo storeinfo=this.getParameter(request, StoreInfo.class);
		if(storeinfo==null){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("STORE_IS_NULL"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		if(storeinfo.getResid()==null){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("STORE_RES_ID_NULL"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		
		//验证是否已经存在
		StoreInfo storetmp=new StoreInfo();
		storetmp.setResid(storeinfo.getResid());
		storetmp.setUserid(this.logined(request).getUserid());
		List<StoreInfo> storeList=storeManager.getList(storetmp, null);
		if(storeList!=null&&storeList.size()>0){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("STORE_HAS_MORE"));
			response.getWriter().print(jEntity.toJSON());return;
		}

        //开始添加
        storetmp.setRef(this.storeManager.getNextId());
        //修改状态,同时写入操作记录
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.storeManager.getSaveSql(storetmp, sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //修改数量
        sqlbuilder=new StringBuilder();
        objList=this.storeManager.getUpdateNumAdd("rs_resource_info","res_id","STORENUM",storetmp.getResid().toString(),1,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

        //开始添加
        //storetmp.setRef(this.storeManager.getNextId());
        if(sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                jEntity.setType("success");
                jEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else
                jEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            jEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        }
        response.getWriter().print(jEntity.toJSON());
	}
	
	/**
	 * 删除收藏
	 * @param request
	 * @param response
	 * @param mp
	 * @throws Exception
	 */
	@RequestMapping(params="m=dodelete",method=RequestMethod.POST)
	public void doDeleteStore(HttpServletRequest request,HttpServletResponse response,ModelAndView mp) throws Exception{
		JsonEntity jEntity=new JsonEntity();
		
		StoreInfo storeinfo=this.getParameter(request, StoreInfo.class);
		if(storeinfo==null){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("STORE_IS_NULL"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		if(storeinfo.getResid()==null){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("STORE_RES_ID_NULL"));
			response.getWriter().print(jEntity.toJSON());return;
		}		
		//验证是否已经存在
		StoreInfo storetmp=new StoreInfo();
		storetmp.setResid(storeinfo.getResid());
		storetmp.setUserid(this.logined(request).getUserid());
		List<StoreInfo> storeList=storeManager.getList(storetmp, null);
		if(storeList==null||storeList.size()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("STORE_IS_NULL"));
			response.getWriter().print(jEntity.toJSON());return;
		}

        //修改状态,同时写入操作记录
        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();

        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.storeManager.getDeleteSql(storetmp,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //修改数量
        sqlbuilder=new StringBuilder();
        objList=this.storeManager.getUpdateNumAdd("rs_resource_info","res_id","STORENUM",storetmp.getResid().toString(),2,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }


		//开始添加
		//storetmp.setRef(this.storeManager.getNextId());
        if(sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
            if(this.resourceManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                jEntity.setType("success");
                jEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            }else
                jEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
        }else{
            jEntity.setMsg(UtilTool.msgproperty.getProperty("NO_EXECUTE_SQL"));
        }
		response.getWriter().print(jEntity.toJSON());
	}
	
	@RequestMapping(params="m=ajaxList", method = RequestMethod.POST)
	public void getList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StoreInfo storeinfo = this.getParameter(request, StoreInfo.class);
        String schoolname=request.getParameter("schoolname");
		JsonEntity je = new JsonEntity();
		PageResult presult = this.getPageResultParameter(request);

		List<StoreInfo> extList = this.storeManager.getList(storeinfo, presult);
		presult.setList(extList);
		je.setPresult(presult);
		response.getWriter().print(je.toJSON());
	}
	
}
