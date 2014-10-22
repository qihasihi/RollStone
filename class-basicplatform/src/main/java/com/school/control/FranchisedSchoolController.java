package com.school.control;

import com.school.control.base.BaseController;
import com.school.entity.FranchisedSchoolInfo;
import com.school.entity.TermInfo;
import com.school.manager.ClassManager;
import com.school.manager.ClassYearManager;
import com.school.manager.FranchisedSchoolManager;
import com.school.manager.TermManager;
import com.school.manager.inter.*;
import com.school.util.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by yuchunfan on 14-10-21.
 */
@Controller
public class FranchisedSchoolController extends BaseController{
//public class FranchisedSchoolController extends BaseController<FranchisedSchoolInfo> {
//
//    private IFranchisedSchoolManager franchisedSchoolManager;
//    private ITermManager termManager;
//    public FranchisedSchoolController(){
//        this.franchisedSchoolManager=this.getManager(FranchisedSchoolManager.class);
//        this.termManager=this.getManager(TermManager.class);
//    }
//
//
//    @RequestMapping(params = "m=fCheck", method = RequestMethod.POST)
//    public void getTotalSchool(HttpServletRequest request, HttpServletResponse response) {
//        int schoolId =  Integer.valueOf(request.getParameter("schoolId"));
//        String year = request.getParameter("year");
//        int res = this.franchisedSchoolManager.getTotalClass(schoolId, year);
//        try {
//            response.getWriter().write(res);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @RequestMapping(params = "m=lCheck", method = RequestMethod.POST)
//    public void getNextTotalSchool(HttpServletRequest request, HttpServletResponse response) {
//        int schoolId =  Integer.valueOf(request.getParameter("schoolId"));
//        String year = request.getParameter("year");
//
//        TermInfo tm=new TermInfo();
//        tm.setYear(year);
//        PageResult presult=new PageResult();
//        presult.setOrderBy(" u.YEAR ASC ");
//        presult.setPageSize(1);
//        List<TermInfo> tmList=this.termManager.getList(tm, presult);
//        if(tmList==null||tmList.size()<1){
//            try {
//                response.getWriter().println("{\"result\":\"Wrong!\"}");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        String nextyear = tmList.get(0).getYear();
//
//        int res = this.franchisedSchoolManager.getTotalClass(schoolId, nextyear);
//        try {
//            response.getWriter().write(res);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
