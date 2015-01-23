package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.*;
import com.school.manager.inter.*;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/sysm")
public class SystemManagerController extends BaseController<TermInfo>{
    @Autowired
	private ISubjectManager subjectManager;
    @Autowired
    private ITermManager termManager;
    @Autowired
    private ISchoolLogoManager schoolLogoManager;
    @Autowired
    private IClassManager classManager;
    @Autowired
    private IGradeManager gradeManager;
    @Autowired
    private IOperateExcelManager operaterexcelmanager;

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
                je.setType("success");
                je.setMsg("上传成功");
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

    @RequestMapping(params="m=toAdminPerformance",method=RequestMethod.GET)
    public ModelAndView toAdminPerformance(HttpServletRequest request,ModelAndView mp )throws Exception{
        List<GradeInfo> gradeList = this.gradeManager.getAdminPerformanceTeaGrade(this.logined(request).getDcschoolid());
        request.setAttribute("gradeList",gradeList);
        return new ModelAndView("/systemmanager/adminPerformance-t");
    }

    @RequestMapping(params="m=toAdminPerformanceStu",method=RequestMethod.GET)
    public ModelAndView toAdminPerformanceStu(HttpServletRequest request,ModelAndView mp )throws Exception{
        List<GradeInfo> gradeList = this.gradeManager.getAdminPerformanceStuGrade(this.logined(request).getDcschoolid());
        request.setAttribute("gradeList",gradeList);
        return new ModelAndView("/systemmanager/adminPerformance-s");
    }

    @RequestMapping(params = "m=getAdminPerformanceSubject", method = RequestMethod.POST)
    public void getAdminPerformanceSubject(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        if(gradeid==null){
            je.setMsg("请选择年级");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        List<SubjectInfo> subjectList = this.subjectManager.getAdminPerformanceTeaSubject(this.logined(request).getDcschoolid(),Integer.parseInt(gradeid));
        je.setObjList(subjectList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=getAdminPerformanceSubjectStu", method = RequestMethod.POST)
    public void getAdminPerformanceSubjectStu(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        if(gradeid==null){
            je.setMsg("请选择年级");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        List<SubjectInfo> subjectList = this.subjectManager.getAdminPerformanceStuSubject(this.logined(request).getDcschoolid(),Integer.parseInt(gradeid));
        je.setObjList(subjectList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=getAdminPerformanceClass", method = RequestMethod.POST)
    public void getAdminPerformanceClass(HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        if(gradeid==null){
            je.setMsg("请选择年级");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        List<ClassInfo> classList = this.classManager.getAdminPerformanceTeaClass(this.logined(request).getDcschoolid(),Integer.parseInt(gradeid),Integer.parseInt(subjectid));
        je.setObjList(classList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=getAdminPerformanceClassStu", method = RequestMethod.POST)
    public void getAdminPerformanceClassStu(HttpServletRequest request,
                                         HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        if(gradeid==null){
            je.setMsg("请选择年级");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        List<ClassInfo> classList = this.classManager.getAdminPerformanceStuClass(this.logined(request).getDcschoolid(),Integer.parseInt(gradeid),Integer.parseInt(subjectid));
        je.setObjList(classList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=getAdminPerformance", method = RequestMethod.POST)
    public void getAdminPerformance(HttpServletRequest request,
                          HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        String btime = request.getParameter("btime");
        String etime = request.getParameter("etime");
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String classid = request.getParameter("classid");
        if(gradeid==null||subjectid==null||classid==null){
            je.setMsg("缺少必要参数，请刷新后重试");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        ClassInfo c = new ClassInfo();
        c.setClassid(Integer.parseInt(classid));
        c.setGradeid(Integer.parseInt(gradeid));
        c.setSubjectid(Integer.parseInt(subjectid));
        if(btime!=null&&etime!=null){
            c.setBegintime(btime);
            c.setEndtime(etime);
        }
        List<AdminPerformance> objList = this.classManager.getAdminPerformance(c,presult);
        presult.getList().add(objList);
        je.setType("success");
        if(presult.getPageNo()==1){
            List<AdminPerformance> objListTotal = this.classManager.getAdminPerformance(c,null);
            presult.getList().add(objListTotal);
        }else{
            presult.getList().add(null);
        }
        je.setPresult(presult);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=getAdminPerformanceStu", method = RequestMethod.POST)
    public void getAdminPerformanceStu(HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String classid = request.getParameter("classid");
        if(gradeid==null||subjectid==null||classid==null){
            je.setMsg("缺少必要参数，请刷新后重试");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        ClassInfo c = new ClassInfo();
        c.setClassid(Integer.parseInt(classid));
        c.setGradeid(Integer.parseInt(gradeid));
        c.setSubjectid(Integer.parseInt(subjectid));
        List<Map<String,Object>> colList = this.classManager.getAdminPerformanceStuCol(c);
        List<List<String>> objList = this.classManager.getAdminPerformanceStu(c,presult);
        je.getObjList().add(colList);
        je.getObjList().add(objList);
        je.setType("success");
        response.getWriter().print(je.toJSON());
    }

    @RequestMapping(params = "m=exportAdminPerformance", method = RequestMethod.POST)
    public void exportOrGetTotal(HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        JsonEntity je = new JsonEntity();
        PageResult presult = this.getPageResultParameter(request);
        String btime = request.getParameter("btime");
        String etime = request.getParameter("etime");
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String classid = request.getParameter("classid");
        if(gradeid==null||subjectid==null||classid==null){
            je.setMsg("缺少必要参数，请刷新后重试");
            je.setType("error");
            response.getWriter().print(je.toJSON());
            return;
        }
        ClassInfo c = new ClassInfo();
        c.setClassid(Integer.parseInt(classid));
        List<ClassInfo> clist = this.classManager.getList(c,null);
        ClassInfo cobj = clist.get(0);
        c.setGradeid(Integer.parseInt(gradeid));
        c.setSubjectid(Integer.parseInt(subjectid));
        if(btime!=null&&etime!=null){
            c.setBegintime(btime);
            c.setEndtime(etime);
        }
        List<AdminPerformance> objList = this.classManager.getAdminPerformance(c,null);
        if(objList!=null&&objList.size()>0){
            //导出用变量
            List<String> sheetNameList=new ArrayList<String>();
            List<List<String>> columnsList=new ArrayList<List<String>>();
            List datalist=new ArrayList();
            List<String> titleList=new ArrayList<String>();
            List<Class<? extends Object>> entityClsList=new ArrayList<Class<? extends Object>>();
            List<String> explortObjList=new ArrayList<String>();
            sheetNameList.add(cobj.getClassgrade()+cobj.getClassname());
            //excel列头
            List<String> coltmpList=new ArrayList<String>();
            coltmpList.add("专题");
            coltmpList.add("任务数");
            coltmpList.add("已结束任务");
            coltmpList.add("任务完成率");
            coltmpList.add("专题评价");
            coltmpList.add("资源学习");
            coltmpList.add("互动交流");
            coltmpList.add("微课程");
            coltmpList.add("成卷测试");
            coltmpList.add("自主测试");
            coltmpList.add("直播课");
            coltmpList.add("试题");
            coltmpList.add("一般任务");
            columnsList.add(coltmpList);
            //title
            String title=cobj.getYear()+" "+cobj.getClassgrade()+"("+cobj.getClassname()+")";
            titleList.add(title);
            //datalist   (空)
            List<List<String>> dataArrayList=new ArrayList<List<String>>();
            //组织数据，把数据变成List<List<String>>格式
            List<String> data = new ArrayList<String>();
            for(int i = 0 ;i<objList.size();i++){
                data = new ArrayList<String>();
                AdminPerformance obj = objList.get(i);
                data.add(obj.getCoursename());
                data.add(obj.getTasknum().toString());
                data.add(obj.getEndtasknum().toString());
                data.add(obj.getCompleterate().toString());
                data.add(obj.getEvaluation().toString());
                data.add(obj.getResourcetask().toString());
                data.add(obj.getInteractivetask().toString());
                data.add(obj.getMicrotask().toString());
                data.add(obj.getCoilingtesttask().toString());
                data.add(obj.getSelftesttask().toString());
                data.add(obj.getLivetask().toString());
                data.add(obj.getQuestask());
                data.add(obj.getGeneraltask());
                dataArrayList.add(data);
            }
            datalist.add(dataArrayList);
            //entityCls
            entityClsList.add(null);
            //explortObjList
            explortObjList.add(null);
//        je.setObjList(objList);
//        je.setType("success");
//        response.getWriter().print(je.toJSON());
            String stemp="班级课程统计";
            String filename=cobj.getYear()+stemp;
            //导出
            this.operaterexcelmanager.ExplortExcel(response, filename,sheetNameList,columnsList
                    ,datalist,titleList,entityClsList,explortObjList);
        }
    }

    @RequestMapping(params = "m=exportAdminPerformanceStu", method = RequestMethod.POST)
    public void exportStuPerformance(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        String gradeid = request.getParameter("gradeid");
        String subjectid = request.getParameter("subjectid");
        String classid = request.getParameter("classid");
        if(gradeid==null||subjectid==null||classid==null){
            return;
        }
        ClassInfo c = new ClassInfo();
        c.setClassid(Integer.parseInt(classid));
        List<ClassInfo> clist = this.classManager.getList(c,null);
        ClassInfo cobj = clist.get(0);
        c.setGradeid(Integer.parseInt(gradeid));
        c.setSubjectid(Integer.parseInt(subjectid));
        List<List<String>> objList = this.classManager.getAdminPerformanceStu(c, null);
        List<Map<String,Object>> colList = this.classManager.getAdminPerformanceStuCol(c);
        TermInfo term = this.termManager.getAutoTerm();
        if(objList!=null&&objList.size()>0){
            //导出用变量
            List<String> sheetNameList=new ArrayList<String>();
            List<List<String>> columnsList=new ArrayList<List<String>>();
            List datalist=new ArrayList();
            List<String> titleList=new ArrayList<String>();
            List<Class<? extends Object>> entityClsList=new ArrayList<Class<? extends Object>>();
            List<String> explortObjList=new ArrayList<String>();
            sheetNameList.add(cobj.getClassgrade()+cobj.getClassname());
            //excel列头
            List<String> coltmpList=new ArrayList<String>();
            coltmpList.add("姓名");
            for(int i = 0;i<colList.size();i++){
                coltmpList.add(colList.get(i).get("COLNAME").toString());
            }
            coltmpList.add("总分");
            columnsList.add(coltmpList);
            //title
            String title=cobj.getYear()+" "+cobj.getClassgrade()+"("+cobj.getClassname()+")";
            titleList.add(title);
            //datalist   (空)
            List<List<String>> dataArrayList=new ArrayList<List<String>>();
            //组织数据，把数据变成List<List<String>>格式
            for(int i = 0 ;i<objList.size();i++){
                List<String> obj = objList.get(i);
                dataArrayList.add(obj);
            }
            datalist.add(dataArrayList);
            //entityCls
            entityClsList.add(null);
            //explortObjList
            explortObjList.add(null);
//        je.setObjList(objList);
//        je.setType("success");
//        response.getWriter().print(je.toJSON());
            String stemp="学生答卷统计";
            String filename=term.getSemesterbegindatestring()+"~"+term.getSemesterenddatestring()+cobj.getClassgrade()+cobj.getClassname()+stemp;
            //导出
            this.operaterexcelmanager.ExplortExcel(response, filename,sheetNameList,columnsList
                    ,datalist,titleList,entityClsList,explortObjList);
        }
    }
}
