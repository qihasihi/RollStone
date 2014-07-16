package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.share.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhengzhou on 14-7-10.
 */
@Controller
@RequestMapping(value = "/synTest")
public class TestController extends BaseController{

    @RequestMapping(params="m=SynchroEttColumn",method=RequestMethod.GET)
    public void SynchroEttColumn(HttpServletRequest request,HttpServletResponse response){
        new SynchroEttColumns().run();
    }

    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateCloudMyInfo",method={RequestMethod.GET, RequestMethod.POST})
    public void updateCloudMyInfo(HttpServletRequest request,HttpServletResponse response){
        new UpdateRsMyInfoData(request.getSession().getServletContext()).run();
    }

    /**
     * 测试,最热资源同步
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateRsHotRank",method={RequestMethod.GET,RequestMethod.POST})
    public void UpdateRsHotRank(HttpServletRequest request,HttpServletResponse response){
        new UpdateHotResData(request.getSession().getServletContext()).run();
    }


    /**
     * 测试,分校信息同步
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateSchool",method={RequestMethod.GET,RequestMethod.POST})
    public void UpdateSchool(HttpServletRequest request,HttpServletResponse response){
        new UpdateSchool(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,资源数量上行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateResNum",method={RequestMethod.GET,RequestMethod.POST})
    public void updateResNum(HttpServletRequest request,HttpServletResponse response){
        new UpdateResNum(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,资源数量上行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=synchroResNum",method={RequestMethod.GET,RequestMethod.POST})
    public void synchroResNum(HttpServletRequest request,HttpServletResponse response){
        new ShareResNum(request.getSession().getServletContext()).run();
    }


    /**
     * 测试,分校tkw wv 排行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroSchoolScoreRank",method={RequestMethod.GET,RequestMethod.POST})
    public void SynchroSchoolScoreRank(HttpServletRequest request,HttpServletResponse response){
        new UpdateSchoolScoreRank(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,分校 排行
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroUserModelTotalScore",method={RequestMethod.GET,RequestMethod.POST})
    public void SynchroUserModelTotalScore(HttpServletRequest request,HttpServletResponse response){
        new UpdateUserModelTotalScore(request.getSession().getServletContext()).run();
    }
    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroCourse",method={RequestMethod.GET,RequestMethod.POST})
    public void SynchroCourse(HttpServletRequest request,HttpServletResponse response){
        new ShareCourse(request.getSession().getServletContext()).run();

    }
    /**
     * 测试,同步资源
     * @param request
     * @param response
     */
    @RequestMapping(params="m=ShareResource",method={RequestMethod.GET,RequestMethod.POST})
    public void SynchroResource(HttpServletRequest request,HttpServletResponse response){
        new ShareResource(request.getSession().getServletContext()).run();

    }
    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=updateCourse",method=RequestMethod.GET)
    public void updateCourse(HttpServletRequest request,HttpServletResponse response){
        new UpdateCourse(request.getSession().getServletContext()).run();

    }

    /**
     * 测试，同步版本
     *
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroTeachVersion",method=RequestMethod.GET)
    public void synchroTeachVersion(HttpServletRequest request,HttpServletResponse response){
        new ShareTeachVersion(request.getSession().getServletContext()).run();
    }
    /**
     * 测试，同步教材
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroTeachingMaterial",method=RequestMethod.GET)
    public void synchroTeachingMaterial(HttpServletRequest request,HttpServletResponse response){
        new ShareTeachingMaterial(request.getSession().getServletContext()).run();
    }
    @RequestMapping(params="m=SynchroResource",method=RequestMethod.GET)
        public void synchroResource(HttpServletRequest request,HttpServletResponse response){
        new ShareResource(request.getSession().getServletContext()).run();
    }

    /**
     * 测试,同步专题
     * @param request
     * @param response
     */
    @RequestMapping(params="m=SynchroOperate",method=RequestMethod.GET)
    public void SynchroOperate(HttpServletRequest request,HttpServletResponse response){
        new ShareTpOperate(request.getSession().getServletContext()).run();
    }
}
