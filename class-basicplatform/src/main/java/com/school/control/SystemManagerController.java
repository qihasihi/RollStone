package com.school.control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.entity.SchoolLogoInfo;
import com.school.entity.TermInfo;
import com.school.manager.SchoolLogoManager;
import com.school.manager.SubjectManager;
import com.school.manager.TermManager;
import com.school.manager.inter.ISchoolLogoManager;
import com.school.manager.inter.ISubjectManager;
import com.school.manager.inter.ITermManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.school.control.base.BaseController;
import com.school.entity.SubjectInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import com.school.util.WriteProperties;

@Controller
@RequestMapping(value = "/sysm")
public class SystemManagerController extends BaseController<TermInfo>{
	private ISubjectManager subjectManager;
    private ITermManager termManager;
    private ISchoolLogoManager schoolLogoManager;
    public SystemManagerController(){
        this.subjectManager=this.getManager(SubjectManager.class);
        this.termManager=this.getManager(TermManager.class);
        this.schoolLogoManager = this.getManager(SchoolLogoManager.class);
    }
	@RequestMapping(params="m=logoconfig",method=RequestMethod.GET)
	public ModelAndView toLogoConfig(HttpServletRequest request,ModelAndView mp )throws Exception{
		return new ModelAndView("/systemmanager/logoconfig");  
	}
	
	@RequestMapping(params="m=tosubject",method=RequestMethod.GET)
     public ModelAndView toSetSubject(HttpServletRequest request,ModelAndView mp )throws Exception{
        List<SubjectInfo> subList = this.subjectManager.getList(null, null);
        request.setAttribute("subList", subList);
        List<Map<String, Object>> termList = new ArrayList<Map<String,Object>>();
        termList = this.termManager.getYearTerm();
        request.setAttribute("termList", termList.get(0));
        return new ModelAndView("/setupwizard");

    }
	
	@RequestMapping(params = "m=saveheadsrcfile", method = RequestMethod.POST)
	public void saveHeadSrcFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
		String msg = "";
		String returnVal = "";
		if (this.getUpload(request) == null) {// file==null||file.isEmpty()){
			msg = "未发现您要裁剪的图片，请重试!";
			returnVal = "{error:'" + msg + "'}";
			response.getWriter().print(returnVal);
			return;
		}
		List<String> filenameString = getFileArrayName(1);
		this.setFname(null);
		if (!this.fileupLoad(request)) {
			msg = "上传失败，请重试!";
			returnVal = "{error:'" + msg + "'}";
			response.getWriter().print(returnVal);
			return;
		}
		String filename = UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"
				+ filenameString.get(0);
		String returnname = filename;
		File f = new File(filename);
		if (!f.exists()) {
			msg = "未发现您要裁剪的图片，请重试!";
			returnVal = "{error:'" + msg + "'}";
			response.getWriter().print(returnVal);
			return;
		}
		Map<String, Long> propertyMap = UtilTool.getImageProperty(f);
		// 得到系统默认大小
		Long default_w = Long.parseLong("352");// UtilTool.utilprop.prop.get("修改头像设置默认宽度").toString());
		Long default_h = Long.parseLong("127");// UtilTool.utilprop.prop.getProperty("修改头像设置默认高度").toString());
		Long w = propertyMap.get("w");
		Long h = propertyMap.get("h");
		Long s = w/h;
		if (propertyMap.get("w") > default_w
				|| propertyMap.get("h") > default_h) {
			// 该图片进行缩放
			// 得到默认比便
			// 得到比例
			if(propertyMap.get("w") > default_w&&propertyMap.get("h") < default_h){
				w = Math.round((h * default_w * 1.0 / h));
			}
			if(propertyMap.get("h") > default_h && propertyMap.get("w") < default_w){
				h = Math.round((w * default_h * 1.0 / w));
			}
			if(propertyMap.get("w") > default_w
					&& propertyMap.get("h") > default_h){
				w = Math.round((h * default_w * 1.0 / h));
				h = Math.round((w * default_h * 1.0 / w));
			}
			//if (w >= h) {
				//w = Math.round((h * default_w * 1.0 / h));
				//h = Math.round((w * default_h * 1.0 / w));
			//} else {
			//	h = Math.round((w * default_h * 1.0 / w));
			//	w = Math.round((h * default_w * 1.0 / h));
			//}
			// 开始缩放
			returnname = new Date().getTime() + "zz"
					+ returnname.substring(returnname.lastIndexOf("."));
			UtilTool.Redraw(f, UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"
					+ returnname, w.intValue(), h.intValue());
			if (f.exists())
				f.delete();
		}
//		UserInfo user = new UserInfo();
//		UserInfo userinfo = (UserInfo) request.getSession().getAttribute(
//				"CURRENT_USER");
//		user.setUserid(userinfo.getUserid());
//		user.setUsername(userinfo.getUsername());
//		user.setHeadimage(userManager.getUserInfo(user).getHeadimage());
//		if (user.getHeadimage() != null
//				&& !user.getHeadimage().trim().equals("")) {
//			File temf = new File(request.getRealPath("/") + user.getHeadimage());
//			if (temf.exists())
//				temf.delete();
//		}
		returnname = returnname.substring(returnname.lastIndexOf("/") + 1);
//		user.setHeadimage("userUploadFile//" + returnname);
//		if (userManager.doUpdate(user)) {
			msg = "uploadfile/" + returnname + "|" + w + "|" + h + "|"
					+ propertyMap.get("s");
			returnVal = "{success:'" + msg + "'}";
			
			response.getWriter().print(returnVal);
//		} else {
//			msg = "图片上传出错!";
//			returnVal = "{error:'" + msg + "'}";
//			response.getWriter().print(returnVal);
//		}
	}

	@RequestMapping(params = "m=docuthead", method = RequestMethod.POST)
	public void doCutHead(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String src = request.getParameter("src");
		String x1 = request.getParameter("x1");
		String y1 = request.getParameter("y1");
		String x2 = request.getParameter("x2");
		String y2 = request.getParameter("y2");
		JsonEntity je = new JsonEntity();
		int w = 320;
		int h = 96;
		if (src == null || src.trim().length() < 1 ) {
			je.setMsg("错误，原因：参数不齐!");
			response.getWriter().print(je.toJSON());
			return;
		}
		
		String src1 = UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+src.split("/")[1];
		File f = new File(src1);
		if (!f.exists()) {
			je.setMsg("错误，该图片已经不存在!");
			response.getWriter().print(je.toJSON());
			return;
		}
		String lastname = src.substring(src.lastIndexOf("."));
		//String firstname = src.substring(0, src.lastIndexOf("."));
		String newname = "logo" + lastname;
		String newnamerealpath =UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ newname;
		if(x1 == null
				|| x1.trim().length() < 1 || !UtilTool.isDouble(x1)
				|| y1 == null || y1.trim().length() < 1
				|| !UtilTool.isDouble(y1) || x2 == null
				|| x2.trim().length() < 1 || !UtilTool.isDouble(x2)
				|| y2 == null || y2.trim().length() < 1
				|| !UtilTool.isDouble(y2)){
            try {
                int bytesum = 0;
                int byteread = 0;
                File oldfile = new File(src1);
                if (oldfile.exists()) { 				//文件存在时
                    InputStream inStream = new FileInputStream(src1); 				//读入原文件
                    FileOutputStream fs = new FileOutputStream(newnamerealpath);
                    byte[] buffer = new byte[1444];
                    int length;
                    while ( (byteread = inStream.read(buffer)) != -1) {
                        bytesum += byteread; //字节数 文件大小
                        fs.write(buffer, 0, byteread);
                    }
                    inStream.close();
                }
            }
            catch (Exception e) {
                System.out.println("复制单个文件操作出错");
                e.printStackTrace();
            }
        }else{
			w = Integer.parseInt(x2) - Integer.parseInt(x1);
			h = Integer.parseInt(y2) - Integer.parseInt(y1);
			if (w < 0)
				w = -w;
			if (h < 0)
				h = -h;
			UtilTool.cutImg(src1, newnamerealpath, w, h, Integer.parseInt(x1),
					Integer.parseInt(y1));
		}

		try {            
			int bytesum = 0;            
			int byteread = 0;            
			File oldfile = new File(newnamerealpath);
			if (oldfile.exists()) { 				//文件存在时                
				InputStream inStream = new FileInputStream(newnamerealpath); 				//读入原文件                
				FileOutputStream fs = new FileOutputStream(UtilTool.utilproperty.get("RESOURCE_SERVER_PATH")+"/imagesSchoolLogo/logo"+this.logined(request).getDcschoolid()+lastname);
				byte[] buffer = new byte[1444];                
				int length;                
				while ( (byteread = inStream.read(buffer)) != -1) {                    
					bytesum += byteread; //字节数 文件大小                  
					fs.write(buffer, 0, byteread);                
				}
				inStream.close();
                //插入数据库
                SchoolLogoInfo schoolLogoInfo = new SchoolLogoInfo();
                schoolLogoInfo.setSchoolid(this.logined(request).getDcschoolid());
                schoolLogoInfo.setLogosrc("imagesSchoolLogo/logo"+this.logined(request).getDcschoolid()+lastname);
                //首先删除旧的logo
                this.schoolLogoManager.doDelete(schoolLogoInfo);
                this.schoolLogoManager.doSave(schoolLogoInfo);
                //刷新session
                List<SchoolLogoInfo> logoList = this.schoolLogoManager.getList(schoolLogoInfo,null);
                if(logoList!=null){
                    request.getSession().setAttribute("logoObj",logoList.get(0));
                }
			}
		}        
		catch (Exception e) {
            je.setType("error");
            je.setMsg("找不到系统路径"+UtilTool.utilproperty.get("RESOURCE_SERVER_PATH")+"/imagesSchoolLogo");
			System.out.println("复制单个文件操作出错");            
			e.printStackTrace();        
		}
//		if (true) {
//			je.getObjList().add(newname);
//			je.setType("success");
//			WriteProperties.writeProperties(request.getRealPath("/")
//					+ "/WEB-INF/classes/properties/util.properties",
//					"LOGO_SRC", "logo"+lastname.toLowerCase());
//			UtilTool.utilproperty.setProperty("LOGO_SRC",
//					"logo"+lastname.toLowerCase());
//		} else {
//			je.getObjList().add(newname);
//			je.setType("error");
//		}
		response.getWriter().print(je.toJSON());
	}
}
