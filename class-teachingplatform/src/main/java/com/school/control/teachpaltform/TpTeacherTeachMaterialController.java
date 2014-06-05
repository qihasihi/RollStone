package com.school.control.teachpaltform;

import com.school.control.base.BaseController;
import com.school.entity.teachpaltform.TpTeacherTeachMaterial;
import com.school.manager.inter.teachpaltform.ITpTeacherTeachMaterialManager;
import com.school.manager.teachpaltform.TpTeacherTeachMaterialManager;
import com.school.util.JsonEntity;
import com.school.util.UtilTool;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhengzhou on 14-3-29.
 */
@Controller
@RequestMapping(value="/tpteacherteamater")
public class TpTeacherTeachMaterialController  extends BaseController<TpTeacherTeachMaterial>{
    private ITpTeacherTeachMaterialManager tpTeacherTeachMaterialManager;
    public TpTeacherTeachMaterialController(){
        this.tpTeacherTeachMaterialManager=this.getManager(TpTeacherTeachMaterialManager.class);
    }
    /**
     * 添加或，如有对应数据，则不操作，没有则添加
     * @param request
     * @param response
     */
    @RequestMapping(params="m=doSaveOrUpdate",method= RequestMethod.POST)
    public void doSaveTeacherTeachMaterial(HttpServletRequest request,HttpServletResponse response)throws Exception{
        TpTeacherTeachMaterial entity=this.getParameter(request,TpTeacherTeachMaterial.class);
        JsonEntity jsonEntity=new JsonEntity();
        if(entity==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
        }
        if(entity.getGradeid()==null||entity.getSubjectid()==null||entity.getTermid()==null||entity.getMaterialid()==null){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
            response.getWriter().print(jsonEntity.toJSON());
            return;
        }
        entity.setUserid(this.logined(request).getUserid());
        //执行添加
        if(this.tpTeacherTeachMaterialManager.doSave(entity)){
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_SUCCESS"));
            jsonEntity.setType("success");
        }else
            jsonEntity.setMsg(UtilTool.msgproperty.getProperty("OPERATE_ERROR"));
        response.getWriter().print(jsonEntity.toJSON());
    }

}
