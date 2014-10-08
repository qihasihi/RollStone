package com.school.control;

import java.io.File;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.school.manager.*;
import com.school.manager.inter.*;
import com.school.utils.EttInterfaceUserUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.school.util.UtilTool.DateType;
import com.school.control.base.BaseController;
import com.school.entity.ClassInfo;
import com.school.entity.ClassUser;
import com.school.entity.ClassYearInfo;
import com.school.entity.GradeInfo;
import com.school.entity.RoleUser;
import com.school.entity.StudentInfo;
import com.school.entity.UserIdentityInfo;
import com.school.entity.UserInfo;
import com.school.manager.base.IBaseManager;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.UtilTool;

@Controller
@Scope("prototype") 
@RequestMapping(value="/student")
public class StudentController extends BaseController<StudentInfo> {
    private IClassYearManager classYearManager;
    private IGradeManager gradeManager;
    private IStudentManager studentManager;
    private IOperateExcelManager operaterexcelmanager;
    private IClassManager classManager;
    private IClassUserManager classUserManager;
    private IUserManager userManager;
    private IUserIdentityManager userIdentityManager;
    private IRoleUserManager roleUserManager;
    public StudentController(){
        this.classYearManager=this.getManager(ClassYearManager.class);
        this.gradeManager=this.getManager(GradeManager.class);
        this.studentManager=this.getManager(StudentManager.class);
        this.operaterexcelmanager=this.getManager(OperateExcelManager.class);
        this.classManager=this.getManager(ClassManager.class);
        this.classUserManager=this.getManager(ClassUserManager.class);
        this.userManager=this.getManager(UserManager.class);
        this.userIdentityManager=this.getManager(UserIdentityManager.class);
        this.roleUserManager=this.getManager(RoleUserManager.class);
    }

	@RequestMapping(params="m=list",method=RequestMethod.GET)
	public ModelAndView toStudentList(HttpServletRequest request,ModelAndView mp )throws Exception{
		PageResult p = new PageResult();
		p.setOrderBy("c.e_time desc"); 
		List<ClassYearInfo>classyearList=this.classYearManager.getList(null, p); 
		List<GradeInfo>gradeList=this.gradeManager.getList(null, null);
		request.setAttribute("classyearList", classyearList);
		request.setAttribute("gradeList", gradeList);
		return new ModelAndView("/student/list");  
	}
	
	
	@RequestMapping(params="m=ajaxlist",method=RequestMethod.POST) 
	public void getAjaxStudentList(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity je=new JsonEntity();
		String stuno=request.getParameter("stuno");
		if(stuno==null||stuno.trim().length()<1){
			je.setMsg(UtilTool.msgproperty.getProperty("PARAM_ERROR"));
			response.getWriter().print(je.toJSON());
			return;
		}
		StudentInfo s=new StudentInfo();
		s.setStuno(stuno);
		List<StudentInfo>stuList=this.studentManager.getList(s, null);
		je.setType("success");
		je.setObjList(stuList);
		response.getWriter().print(je.toJSON());
	}
	
	@RequestMapping(params="m=loadExlForStudent",method=RequestMethod.POST)  
	public void toLoadStudentExcel(MultipartHttpServletRequest request,HttpServletResponse response) 
		throws Exception{
		JsonEntity je = new JsonEntity();
		if(this.getUpload(request)==null||this.getUpload(request).length<1){
			je.setMsg("系统未发现您上传的文件!");
			response.getWriter().print(je.toJSON());
			return;
		}		
		List<String>fname=this.getFileArrayName(1);
		String msg="{\"type\":\"error\",\"msg\":\"文件名获取失败!\"}";
		if(fname!=null&&fname.size()>0){
			if(this.fileupLoad(request)){
				String filename=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+fname.get(0);
				msg=this.loadExlForStudent(filename,request);
				File f=new File(filename);
				if(f.exists()){
					f.delete();
				}
			}
		}
		response.getWriter().print(msg);
	}
	
	public String loadExlForStudent(String filename,HttpServletRequest request) {
		String msg = "用户导入成功!";
        String type="success";
		if (filename==null||filename.trim().length() < 1)
			return "{\"type\":\"error\",\"msg\":\"文件上传失败!\"}";
		List<Object>objList=null;
		String clsid=request.getParameter("classid");
        List<String> unionUserIdList=new ArrayList<String>();
        List<Integer> clsIdList=new ArrayList<Integer>();
		try{
			//Excel存在值
			objList=this.operaterexcelmanager.includeExcel(filename, new StudentManager());
			
			Integer freeClsid=null;
			if(objList!=null&&objList.size()>0){
				String impName="用户导入:"+UtilTool.DateConvertToString(new Date(), com.school.util.UtilTool.DateType.type2);
				List<String>sqlListArray=new ArrayList<String>();
				List<List<Object>>objListArray=new ArrayList<List<Object>>();
				for (Object stuObj : objList) {
					StringBuilder sqlbuilder = new StringBuilder();
					List<Object> objparaList = null;
					StudentInfo stu=(StudentInfo)stuObj;
					ClassInfo cls=new ClassInfo();
					cls.setClassgrade(stu.getClassinfo().getClassgrade().trim());
					cls.setYear(stu.getClassinfo().getYear().trim());
					cls.setClassname(stu.getClassinfo().getClassname().trim());
                    cls.setDcschoolid(this.logined(request).getDcschoolid());
					cls.setIslike(1);
					//验证班级存在
					List<ClassInfo>clsList=this.classManager.getList(cls, null);
					if(clsList==null||clsList.size()<1){
                        if(msg.trim().indexOf("其中")==-1){
                            msg+="其中 ";
                        }
						msg+=stu.getStuno()+"（班级不存在）,";
                        continue;
					}

					if(clsid!=null&&clsid.toString().trim().length()>0&&!clsid.trim().equals("undefined")){
						if(!clsid.trim().equals(clsList.get(0).getClassid().toString())){
                            if(msg.trim().indexOf("其中")==-1){
                                msg+="其中 ";
                            }
							msg+=stu.getStuno()+"（班级信息不匹配）,";
							continue;
						}
					}
                    StudentInfo selstu=new StudentInfo();
                    selstu.setStuno(stu.getStuno());
                    //selstu.setDcschoolid(this.logined(request).getDcschoolid());
                    //验证学生存在
                    List<StudentInfo> selstuList=this.studentManager.getList(selstu, null);
                    //如果学生存在，并具导入的班级是行政班，则判断该学生是否在当前班级学年中有多个行政班级，如果有多个，不导入相关数据
                    if(selstuList!=null&&selstuList.size()>0&&selstuList.get(0)!=null
                            &&clsList!=null&&clsList.get(0)!=null&&clsList.get(0).getPattern()!=null&&clsList.get(0).getPattern().trim().equals("行政班")){
                        //得到当前学生的行政班级
                        ClassUser cu=new ClassUser();
                        cu.setYear(stu.getClassinfo().getYear().trim());
                        cu.setUserid(selstuList.get(0).getUserref());
                        cu.setRelationtype("学生");
                        cu.setPattern("行政班");
                        List<ClassUser> cuList=this.classUserManager.getList(cu,null);
                        boolean ishasPatternCls=false;
                        if(cuList!=null&&cuList.size()>0){
                            for(ClassUser tmp:cuList){
                                if(tmp!=null&&tmp.getClassid().intValue()!=clsList.get(0).getClassid().intValue()){
                                    ishasPatternCls=true;break;
                                }
                            }
                        }
                        if(ishasPatternCls){
                            if(msg.trim().indexOf("其中")==-1){
                                msg+="其中 ";
                            }
                            msg+=stu.getStuno()+"（存在行政班）,";
                            continue;
                        }
                     }

					//删除该班级下的所有学生
					if(freeClsid==null||(freeClsid!=null&&clsList.get(0).getClassid().intValue()!=freeClsid.intValue())){
						ClassUser cutmp=new ClassUser();
						cutmp.setClassid(clsList.get(0).getClassid());
						cutmp.setRelationtype("学生");
						objparaList=this.classUserManager.getDeleteSql(cutmp, sqlbuilder);
                        //记录classid，向ett发送人员信息
                        if(clsIdList!=null&&!clsIdList.contains(clsList.get(0).getClassid())){
                            clsIdList.add(clsList.get(0).getClassid());
                        }
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						freeClsid=clsList.get(0).getClassid();
						sqlbuilder=new StringBuilder();
						objparaList=this.classUserManager.getAddOperateLog(this.logined(request).getRef()
								,"class_user_info",null,null,null,"DELETE","导入学生名册，根据班级ID批量删除数据!", sqlbuilder);

						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
					}



					if(selstuList!=null&&selstuList.size()>0&&selstuList.get(0)!=null){
						ClassUser cu=new ClassUser();
						cu.getClassinfo().setClassid(clsList.get(0).getClassid());
						cu.getUserinfo().setRef(selstuList.get(0).getUserref());
						cu.setRelationtype("学生");
                       // cu.getClassinfo().setDcschoolid(this.logined(request).getDcschoolid());
						//验证学生班级关系不存在
						System.out.println(cu.getClassinfo().getClassid()+"     学生ref:"+cu.getUserinfo().getRef());
//						List<ClassUser>cuList=this.classUserManager.getList(cu, null);
//						
//						if(cuList!=null&&cuList.size()>0){//
							String cuNextID=UUID.randomUUID().toString();
							cu.setRef(cuNextID);
							sqlbuilder=new StringBuilder();
							objparaList=this.classUserManager.getSaveSql(cu, sqlbuilder);
                        //记录classid，向ett发送人员信息
                        if(clsIdList!=null&&!clsIdList.contains(clsList.get(0).getClassid())){
                            clsIdList.add(clsList.get(0).getClassid());
                        }


							objListArray.add(objparaList);
							sqlListArray.add(sqlbuilder.toString());							
							//添加操作记录
							sqlbuilder=new StringBuilder();
							objparaList=this.classUserManager.getAddOperateLog(this.logined(request).getRef()
									,"class_user_info",cuNextID,null,null,"ADD","导入学生名册，添加学生班级关系 数据!", sqlbuilder);
							
							objListArray.add(objparaList);
							sqlListArray.add(sqlbuilder.toString());	
					//	}
					}else{ 
						UserInfo u=new UserInfo();
						String userNextID=UUID.randomUUID().toString();
						u.setRef(userNextID);
						u.setUsername(stu.getStuno());
						u.setPassword("111111");
						u.setStateid(0);
                        u.setDcschoolid(this.logined(request).getDcschoolid());
						sqlbuilder=new StringBuilder();
						objparaList=this.userManager.getSaveSql(u, sqlbuilder);
                        if(unionUserIdList!=null&&!unionUserIdList.contains(userNextID)){
                            unionUserIdList.add(userNextID);
                        }


						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						//添加操作记录
						sqlbuilder=new StringBuilder();
						objparaList=this.userManager.getAddOperateLog(this.logined(request).getRef()
								,"user_info",userNextID,null,null,"ADD","导入学生名册，添加用户数据!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						
						String stuNextID=stu.getRef();
						stu.getUserinfo().setRef(userNextID);
						if(stuNextID==null||stuNextID.trim().length()<1){
							stuNextID=UUID.randomUUID().toString();
							stu.setRef(stuNextID);
							
							//添加学生操作记录	
						}						
						sqlbuilder=new StringBuilder();
						objparaList=this.studentManager.getSaveSql(stu, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());						
						
						//添加操作记录
						sqlbuilder=new StringBuilder();
						objparaList=this.studentManager.getAddOperateLog(this.logined(request).getRef()
								,"student_info",stuNextID,null,null,"ADD","导入学生名册，添加学生信息数据!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						//学生与班级
						ClassUser cu=new ClassUser();
						String cuNextId=UUID.randomUUID().toString();
						cu.setRef(cuNextId);
						cu.getClassinfo().setClassid(clsList.get(0).getClassid());
						System.out.println("clsList.size:"+clsList.size()+" clsid:"+clsList.get(0).getClassid());
						cu.getUserinfo().setRef(userNextID);
						cu.setRelationtype("学生");
						
						sqlbuilder=new StringBuilder();
						objparaList=this.classUserManager.getSaveSql(cu, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());

                        if(clsIdList!=null&&!clsIdList.contains(clsList.get(0).getClassid())){
                            clsIdList.add(clsList.get(0).getClassid());
                        }


						//添加操作记录
						sqlbuilder=new StringBuilder();
						objparaList=this.classUserManager.getAddOperateLog(this.logined(request).getRef()
								,"class_user_info",cuNextId,null,null,"ADD","导入学生名册，添加学生班级关系数据!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						
						String uidentityNextRef=this.userIdentityManager.getNextId();
						UserIdentityInfo uidentity=new UserIdentityInfo();
						uidentity.setIdentityname(UtilTool._IDENTITY_STUDENT);//
						uidentity.setUserid(userNextID);
						uidentity.setRef(uidentityNextRef);
						sqlbuilder=new StringBuilder();
						objparaList=this.userIdentityManager.getSaveSql(uidentity, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						//添加操作记录
						sqlbuilder=new StringBuilder();
						objparaList=this.userIdentityManager.getAddOperateLog(this.logined(request).getRef()
								,"j_user_identity_info",uidentityNextRef,null,null,"ADD","导入学生名册，添加学生身份关系数据!", sqlbuilder);						
						
						//学生与角色
						RoleUser ru=new RoleUser();
						String ruNextID=UUID.randomUUID().toString();
						ru.setRef(ruNextID);
						ru.getUserinfo().setRef(userNextID);
						ru.getRoleinfo().setRoleid(UtilTool._ROLE_STU_ID);  
						
						sqlbuilder=new StringBuilder();
						objparaList=this.roleUserManager.getSaveSql(ru, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
												
						//添加操作记录
						sqlbuilder=new StringBuilder();
						objparaList=this.roleUserManager.getAddOperateLog(this.logined(request).getRef()
								,"j_role_user",ruNextID,null,null,"ADD","导入学生名册，添加学生角色关系数据!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						
					}
				}
				
				if(sqlListArray.size()<1||objListArray.size()<1){
					msg= "{\"type\":\"error\",\"msg\":\"您上传的文件中不存在数据或该数据已存在于班级中!请仔细核对!\"}";    
				}else{
					boolean flag=this.studentManager.doExcetueArrayProc(sqlListArray, objListArray);
					if(flag){
                        if(msg.trim().indexOf("其中")==-1){
					    	msg="{\"type\":\"success\",\"msg\":\""+msg+"导入失败!\"}";
                        }else
                            msg="{\"type\":\"success\",\"msg\":\""+msg+"!\"}";

                        //向ETT传入用户数据(添加)
                        if(unionUserIdList!=null&&unionUserIdList.size()>0){
                            for (String tmpRef:unionUserIdList){
                                if(tmpRef!=null){
                                    if(!addUserToEtt(tmpRef)){
                                        logger.info("学生导入:"+tmpRef+"同步用户数据到ETT失败!");
                                    }else{
                                        logger.info("学生导入:"+tmpRef+"同步用户数据到ETT成功!");
                                    }

                                }
                            }
                        }
                        //向ETT传入班级用户数据
                        if(clsIdList!=null&&clsIdList.size()>0){
                            for (Integer tmpCls:clsIdList){
                                if(tmpCls!=null){
                                    if(!updateClassUserToEtt(tmpCls)){
                                        logger.info("学生导入:"+tmpCls+"同步班级数据到ETT失败!");
                                    }else{
                                        logger.info("学生导入:"+tmpCls+"同步班级数据到ETT成功!");
                                    }
                                }
                            }
                        }

                    }else
						msg="{\"type\":\"error\",\"msg\":\"导入班级成员失败!请重试!\"}"; 
				}

            }
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(); 
			return "{\"type\":\"error\",\"msg\":\"您上传的文件中数据出现错误!请仔细核对!\"}"; 
		}
		return msg; 
	}
    /**
     * 添加班级用户至ett
     * @param clsid
     * @return
     */
    private boolean updateClassUserToEtt(Integer clsid){
        if(clsid==null)return false;
        ClassInfo cls=new ClassInfo();
        cls.setClassid(clsid);
        List<ClassInfo> clsList=this.classManager.getList(cls,null);
        if(clsList==null||clsList.size()<1)return false;
        Integer dcschoolId=clsList.get(0).getDcschoolid();
       ClassUser cu=new ClassUser();
        cu.setClassid(clsid);
        List<ClassUser> cuTmpList=this.classUserManager.getList(cu,null);
        if(cuTmpList!=null&&cuTmpList.size()>0){
            // 必带 userId，userType,subjectId 的三个key
            List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
            for (ClassUser cuTmpe:cuTmpList){
                if(cuTmpe!=null){
                    Map<String,Object> tmpMap=new HashMap<String, Object>();
                    tmpMap.put("userId",cuTmpe.getUid());
                    Integer userType=3;
                    if(cuTmpe.getRelationtype()!=null){
                        if(cuTmpe.getRelationtype().trim().equals("任课老师"))
                            userType=2;
                        else if(cuTmpe.getRelationtype().trim().equals("班主任"))
                            userType=1;
                    }
                    tmpMap.put("userType",userType);
                    tmpMap.put("subjectId",cuTmpe.getSubjectid()==null?-1:cuTmpe.getSubjectid());
                    mapList.add(tmpMap);
                }
            }
            if(!EttInterfaceUserUtil.OperateClassUser(mapList, clsid, dcschoolId)){
                System.out.println("classUser同步至网校失败!");
                return false;
            } else
                System.out.println("classUser同步至网校成功!");
        }
        return true;
    }
    /**
     * 添加用户至ett
     * @param ref
     * @return
     */
    private boolean addUserToEtt(String ref){
        if(ref==null||ref.toString().trim().length()<1)return false;
        UserInfo u=new UserInfo();
        u.setRef(ref);
        PageResult presult=new PageResult();
        presult.setPageSize(1);
        List<UserInfo> tmpUList=this.userManager.getList(u,presult);
        if(tmpUList!=null&&tmpUList.size()>0){
            //调用用户接口
            if(EttInterfaceUserUtil.addUserBase(tmpUList)){
                System.out.println("用户信息同步至网校成功!");
            }else{
                System.out.println("用户信息同步至网校失败!");
                return false;
            }
        }
        return true;
    }



	/**
	 * 根据班级信息得到学生数据
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(params="m=getstudent",method=RequestMethod.POST) 
	public void getStudentByClass(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		String classIdStr=request.getParameter("classId");
		String year=request.getParameter("year");
		String pattern=request.getParameter("pattern");
		if(classIdStr==null||classIdStr.trim().length()<1){
			jeEntity.setMsg("异常错误，班级未接收到，请刷新页面后重试！");
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		
		List<StudentInfo> stuList = this.studentManager.getStudentByClass(Integer.parseInt(classIdStr), year, pattern);
		jeEntity.setObjList(stuList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
		
		
	}
	/**
	 * 根据学号得到学生信息
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(params="m=getref",method=RequestMethod.POST) 
	public void getStudentByStuno(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		String stuno = request.getParameter("stuno");
		if(stuno==null||stuno.trim().length()<1){
			jeEntity.setMsg("异常错误，学号未接收到，请刷新页面后重试！");
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		StudentInfo obj = new StudentInfo();
		obj.setStuno(stuno);
		List<StudentInfo> stuList = this.studentManager.getList(obj, null);
		jeEntity.setObjList(stuList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
		
		
	}
	
}
