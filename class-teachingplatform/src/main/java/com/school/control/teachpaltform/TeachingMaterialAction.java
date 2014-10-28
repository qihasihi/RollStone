package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.dao.inter.IGradeDAO;
import com.school.entity.GradeInfo;
import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.manager.GradeManager;
import com.school.manager.inter.IGradeManager;
import com.school.manager.inter.teachpaltform.ITeachingMaterialManager;
import com.school.manager.teachpaltform.TeachingMaterialManager;
import com.school.util.JsonEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "teachingmaterial")
public class TeachingMaterialAction extends BaseController<TeachingMaterialInfo> {
    private ITeachingMaterialManager teachingMaterialManager;
    private IGradeManager gradeManager;
    public TeachingMaterialAction(){
        this.teachingMaterialManager=this.getManager(TeachingMaterialManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
    }
	/**
	 * 获取教材列表(分页)
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(params = "m=getTchingMaterialList", method = RequestMethod.POST)
	public void qryStuCourseList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
        String subjectid=request.getParameter("subjectid");
		// 分页查询
        TeachingMaterialInfo tm = this.getParameter(request, TeachingMaterialInfo.class);
        if(subjectid!=null&&subjectid.trim().length()>0)
            tm.setSubjectid(Integer.parseInt(subjectid));

        if(tm!=null&&tm.getGradeid()!=null){
            GradeInfo g=new GradeInfo();
            g.setGradeid(tm.getGradeid());
            List<GradeInfo>gList=this.gradeManager.getList(g,null);
            if(gList!=null&&gList.size()>0){
                tm.setGradename(gList.get(0).getGradevalue().substring(0,1));
            }
        }
        tm.setGradeid(null);
		List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);
        je.setObjList(tmList);
        je.setType("success");
		response.getWriter().print(je.toJSON());
	}
}
