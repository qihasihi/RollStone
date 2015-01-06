package com.school.control.resource;

import com.school.control.base.BaseController;
import com.school.entity.resource.DownloadInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.manager.inter.resource.IDownloadManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/resdown")
public class DownloadAction extends BaseController<DownloadInfo>{
    @Autowired
    private IDownloadManager downloadManager;
    @Autowired
    private IResourceManager resourceManager;


	/**
	 * 执行添加
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=doadd",method=RequestMethod.POST)
	public void doAdd(HttpServletRequest request,HttpServletResponse response)throws Exception{
		DownloadInfo downTmp=this.getParameter(request, DownloadInfo.class);
		JsonEntity jEntity=new JsonEntity();
		if(downTmp.getResid()==null){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		String filename=request.getParameter("filename");
		if(filename==null||filename.trim().length()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		
		String isValidate=request.getParameter("isval");
		ResourceInfo restmp=new ResourceInfo();
		restmp.setResid(downTmp.getResid());
		List<ResourceInfo> rsList=this.resourceManager.getList(restmp, null);
		if(rsList==null||rsList.size()<1){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("RESOURCE_NO_DATA_EMPTY"));
			response.getWriter().print(jEntity.toJSON());return;
		}
		restmp=rsList.get(0);
		//检测是否有权限下载
		//查看是否有下载浏览
		//开始执行添加
		downTmp.setRef(this.downloadManager.getNextId());
		downTmp.setUserid(this.logined(request).getUserid());


        List<String> sqlArrayList=new ArrayList<String>();
        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=this.downloadManager.getSaveSql(downTmp,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }
        //修改resourcemanager
        sqlbuilder=new StringBuilder();
        objList=this.resourceManager.getUpdateNumAdd("rs_resource_info","res_id","DOWNLOADNUM",downTmp.getResid().toString(),1,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }

		if(this.downloadManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_SUCCESS"));						
			//下载文件的路径
			//String downPath=UtilTool.getResourceUrl(downTmp.getResid().toString(), filename,resourceFileList.get(0).getPath());
            String lastname=filename.substring(filename.lastIndexOf("."));
            if(UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR, lastname)&&
                    new File(UtilTool.getResourceLocation(request,downTmp.getResid(),2)+"/"+UtilTool.getResourceFileUrl(downTmp.getResid().toString(), lastname)+".mp4").exists()){
                lastname+=".mp4";

            }
            System.out.println("mp4path:"+UtilTool.getResourceLocation(request,downTmp.getResid(),2)+"/"+UtilTool.getResourceFileUrl(downTmp.getResid().toString(), lastname)+".mp4");



            String downPath=UtilTool.getResourceFileUrl(downTmp.getResid().toString(), lastname);
			if(downPath==null)
				jEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
			else{
				downPath=UtilTool.getResourceLocation(request,downTmp.getResid(),1)+downPath; //文件下载地址
				jEntity.getObjList().add(downPath);
				jEntity.setType("success");
			}	
		}else
			jEntity.setMsg(UtilTool.msgproperty.getProperty("SAVE_ERROR"));
		response.getWriter().print(jEntity.toJSON());	
	}
}
