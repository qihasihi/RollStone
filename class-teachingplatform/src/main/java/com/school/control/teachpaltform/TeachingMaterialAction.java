package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.TeachingMaterialInfo;
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
    public TeachingMaterialAction(){
        this.teachingMaterialManager=this.getManager(TeachingMaterialManager.class);
    }
	/**
	 * ��ȡ�̲��б�(��ҳ)
	 * @param request
	 * @param response
	 * @throws Exception
     */
	@RequestMapping(params = "m=getTchingMaterialList", method = RequestMethod.POST)
	public void qryStuCourseList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JsonEntity je = new JsonEntity();
        String subjectid=request.getParameter("subjectid");
		// ��ҳ��ѯ
        TeachingMaterialInfo tm = this.getParameter(request, TeachingMaterialInfo.class);
        if(subjectid!=null&&subjectid.trim().length()>0)
            tm.setSubjectid(Integer.parseInt(subjectid));
		List<TeachingMaterialInfo> tmList = this.teachingMaterialManager.getList(tm, null);
        je.setObjList(tmList);
        je.setType("success");
		response.getWriter().print(je.toJSON());
	}
}
