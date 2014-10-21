package com.school.Interceptor;

import com.school.control.ClassController;
import com.school.control.FranchisedSchoolController;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by yuchunfan on 14-10-20.
 */
public class AddClassInterceptor implements HandlerInterceptor {

    //添加action为：cls?m=ajaxsave
    private String mappingURL = ".*/cls\\?m=ajaxsave.*";//利用正则映射到需要拦截的路径

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {

        String url = request.getRequestURL().toString()+"?"+request.getQueryString();
        if(url != null && url.matches(mappingURL)) {
            int schoolId = Integer.valueOf(request.getParameter("dcschoolid"));
            String classYear = request.getParameter("dyear");
            if (schoolId >= 50000) {
                int totalClass = new FranchisedSchoolController().getTotalSchool(schoolId,classYear);
                int existClass = new ClassController().getTotalClass(schoolId, classYear);
                if (totalClass <= existClass) {
                    response.getWriter().println("{\"result\":\"Wrong!\"}");
                    return false;
                }
                return true;
            }
            response.getWriter().println("{\"result\":\"Wrong!\"}");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {

    }
}
