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
			je.setMsg("ϵͳδ�������ϴ����ļ�!");
			response.getWriter().print(je.toJSON());
			return;
		}		
		List<String>fname=this.getFileArrayName(1);
		String msg="{\"type\":\"error\",\"msg\":\"�ļ�����ȡʧ��!\"}";
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
		String msg = "�û�����ɹ�!";
        String type="success";
		if (filename==null||filename.trim().length() < 1)
			return "{\"type\":\"error\",\"msg\":\"�ļ��ϴ�ʧ��!\"}";
		List<Object>objList=null;
		String clsid=request.getParameter("classid");
        List<String> unionUserIdList=new ArrayList<String>();
        List<Integer> clsIdList=new ArrayList<Integer>();
		try{
			//Excel����ֵ
			objList=this.operaterexcelmanager.includeExcel(filename, new StudentManager());
			
			Integer freeClsid=null;
			if(objList!=null&&objList.size()>0){
				String impName="�û�����:"+UtilTool.DateConvertToString(new Date(), com.school.util.UtilTool.DateType.type2);
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
					//��֤�༶����
					List<ClassInfo>clsList=this.classManager.getList(cls, null);
					if(clsList==null||clsList.size()<1){
                        if(msg.trim().indexOf("����")==-1){
                            msg+="���� ";
                        }
						msg+=stu.getStuno()+"���༶�����ڣ�,";
                        continue;
					}

					if(clsid!=null&&clsid.toString().trim().length()>0&&!clsid.trim().equals("undefined")){
						if(!clsid.trim().equals(clsList.get(0).getClassid().toString())){
                            if(msg.trim().indexOf("����")==-1){
                                msg+="���� ";
                            }
							msg+=stu.getStuno()+"���༶��Ϣ��ƥ�䣩,";
							continue;
						}
					}
                    StudentInfo selstu=new StudentInfo();
                    selstu.setStuno(stu.getStuno());
                    //selstu.setDcschoolid(this.logined(request).getDcschoolid());
                    //��֤ѧ������
                    List<StudentInfo> selstuList=this.studentManager.getList(selstu, null);
                    //���ѧ�����ڣ����ߵ���İ༶�������࣬���жϸ�ѧ���Ƿ��ڵ�ǰ�༶ѧ�����ж�������༶������ж�����������������
                    if(selstuList!=null&&selstuList.size()>0&&selstuList.get(0)!=null
                            &&clsList!=null&&clsList.get(0)!=null&&clsList.get(0).getPattern()!=null&&clsList.get(0).getPattern().trim().equals("������")){
                        //�õ���ǰѧ���������༶
                        ClassUser cu=new ClassUser();
                        cu.setYear(stu.getClassinfo().getYear().trim());
                        cu.setUserid(selstuList.get(0).getUserref());
                        cu.setRelationtype("ѧ��");
                        cu.setPattern("������");
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
                            if(msg.trim().indexOf("����")==-1){
                                msg+="���� ";
                            }
                            msg+=stu.getStuno()+"�����������ࣩ,";
                            continue;
                        }
                     }

					//ɾ���ð༶�µ�����ѧ��
					if(freeClsid==null||(freeClsid!=null&&clsList.get(0).getClassid().intValue()!=freeClsid.intValue())){
						ClassUser cutmp=new ClassUser();
						cutmp.setClassid(clsList.get(0).getClassid());
						cutmp.setRelationtype("ѧ��");
						objparaList=this.classUserManager.getDeleteSql(cutmp, sqlbuilder);
                        //��¼classid����ett������Ա��Ϣ
                        if(clsIdList!=null&&!clsIdList.contains(clsList.get(0).getClassid())){
                            clsIdList.add(clsList.get(0).getClassid());
                        }
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						freeClsid=clsList.get(0).getClassid();
						sqlbuilder=new StringBuilder();
						objparaList=this.classUserManager.getAddOperateLog(this.logined(request).getRef()
								,"class_user_info",null,null,null,"DELETE","����ѧ�����ᣬ���ݰ༶ID����ɾ������!", sqlbuilder);

						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
					}



					if(selstuList!=null&&selstuList.size()>0&&selstuList.get(0)!=null){
						ClassUser cu=new ClassUser();
						cu.getClassinfo().setClassid(clsList.get(0).getClassid());
						cu.getUserinfo().setRef(selstuList.get(0).getUserref());
						cu.setRelationtype("ѧ��");
                       // cu.getClassinfo().setDcschoolid(this.logined(request).getDcschoolid());
						//��֤ѧ���༶��ϵ������
						System.out.println(cu.getClassinfo().getClassid()+"     ѧ��ref:"+cu.getUserinfo().getRef());
//						List<ClassUser>cuList=this.classUserManager.getList(cu, null);
//						
//						if(cuList!=null&&cuList.size()>0){//
							String cuNextID=UUID.randomUUID().toString();
							cu.setRef(cuNextID);
							sqlbuilder=new StringBuilder();
							objparaList=this.classUserManager.getSaveSql(cu, sqlbuilder);
                        //��¼classid����ett������Ա��Ϣ
                        if(clsIdList!=null&&!clsIdList.contains(clsList.get(0).getClassid())){
                            clsIdList.add(clsList.get(0).getClassid());
                        }


							objListArray.add(objparaList);
							sqlListArray.add(sqlbuilder.toString());							
							//��Ӳ�����¼
							sqlbuilder=new StringBuilder();
							objparaList=this.classUserManager.getAddOperateLog(this.logined(request).getRef()
									,"class_user_info",cuNextID,null,null,"ADD","����ѧ�����ᣬ���ѧ���༶��ϵ ����!", sqlbuilder);
							
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
						
						//��Ӳ�����¼
						sqlbuilder=new StringBuilder();
						objparaList=this.userManager.getAddOperateLog(this.logined(request).getRef()
								,"user_info",userNextID,null,null,"ADD","����ѧ�����ᣬ����û�����!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						
						String stuNextID=stu.getRef();
						stu.getUserinfo().setRef(userNextID);
						if(stuNextID==null||stuNextID.trim().length()<1){
							stuNextID=UUID.randomUUID().toString();
							stu.setRef(stuNextID);
							
							//���ѧ��������¼	
						}						
						sqlbuilder=new StringBuilder();
						objparaList=this.studentManager.getSaveSql(stu, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());						
						
						//��Ӳ�����¼
						sqlbuilder=new StringBuilder();
						objparaList=this.studentManager.getAddOperateLog(this.logined(request).getRef()
								,"student_info",stuNextID,null,null,"ADD","����ѧ�����ᣬ���ѧ����Ϣ����!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						//ѧ����༶
						ClassUser cu=new ClassUser();
						String cuNextId=UUID.randomUUID().toString();
						cu.setRef(cuNextId);
						cu.getClassinfo().setClassid(clsList.get(0).getClassid());
						System.out.println("clsList.size:"+clsList.size()+" clsid:"+clsList.get(0).getClassid());
						cu.getUserinfo().setRef(userNextID);
						cu.setRelationtype("ѧ��");
						
						sqlbuilder=new StringBuilder();
						objparaList=this.classUserManager.getSaveSql(cu, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());

                        if(clsIdList!=null&&!clsIdList.contains(clsList.get(0).getClassid())){
                            clsIdList.add(clsList.get(0).getClassid());
                        }


						//��Ӳ�����¼
						sqlbuilder=new StringBuilder();
						objparaList=this.classUserManager.getAddOperateLog(this.logined(request).getRef()
								,"class_user_info",cuNextId,null,null,"ADD","����ѧ�����ᣬ���ѧ���༶��ϵ����!", sqlbuilder);
						
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
						
						//��Ӳ�����¼
						sqlbuilder=new StringBuilder();
						objparaList=this.userIdentityManager.getAddOperateLog(this.logined(request).getRef()
								,"j_user_identity_info",uidentityNextRef,null,null,"ADD","����ѧ�����ᣬ���ѧ����ݹ�ϵ����!", sqlbuilder);						
						
						//ѧ�����ɫ
						RoleUser ru=new RoleUser();
						String ruNextID=UUID.randomUUID().toString();
						ru.setRef(ruNextID);
						ru.getUserinfo().setRef(userNextID);
						ru.getRoleinfo().setRoleid(UtilTool._ROLE_STU_ID);  
						
						sqlbuilder=new StringBuilder();
						objparaList=this.roleUserManager.getSaveSql(ru, sqlbuilder);
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
												
						//��Ӳ�����¼
						sqlbuilder=new StringBuilder();
						objparaList=this.roleUserManager.getAddOperateLog(this.logined(request).getRef()
								,"j_role_user",ruNextID,null,null,"ADD","����ѧ�����ᣬ���ѧ����ɫ��ϵ����!", sqlbuilder);
						
						objListArray.add(objparaList);
						sqlListArray.add(sqlbuilder.toString());
						
						
					}
				}
				
				if(sqlListArray.size()<1||objListArray.size()<1){
					msg= "{\"type\":\"error\",\"msg\":\"���ϴ����ļ��в��������ݻ�������Ѵ����ڰ༶��!����ϸ�˶�!\"}";    
				}else{
					boolean flag=this.studentManager.doExcetueArrayProc(sqlListArray, objListArray);
					if(flag){
                        if(msg.trim().indexOf("����")==-1){
					    	msg="{\"type\":\"success\",\"msg\":\""+msg+"����ʧ��!\"}";
                        }else
                            msg="{\"type\":\"success\",\"msg\":\""+msg+"!\"}";

                        //��ETT�����û�����(���)
                        if(unionUserIdList!=null&&unionUserIdList.size()>0){
                            for (String tmpRef:unionUserIdList){
                                if(tmpRef!=null){
                                    if(!addUserToEtt(tmpRef)){
                                        logger.info("ѧ������:"+tmpRef+"ͬ���û����ݵ�ETTʧ��!");
                                    }else{
                                        logger.info("ѧ������:"+tmpRef+"ͬ���û����ݵ�ETT�ɹ�!");
                                    }

                                }
                            }
                        }
                        //��ETT����༶�û�����
                        if(clsIdList!=null&&clsIdList.size()>0){
                            for (Integer tmpCls:clsIdList){
                                if(tmpCls!=null){
                                    if(!updateClassUserToEtt(tmpCls)){
                                        logger.info("ѧ������:"+tmpCls+"ͬ���༶���ݵ�ETTʧ��!");
                                    }else{
                                        logger.info("ѧ������:"+tmpCls+"ͬ���༶���ݵ�ETT�ɹ�!");
                                    }
                                }
                            }
                        }

                    }else
						msg="{\"type\":\"error\",\"msg\":\"����༶��Աʧ��!������!\"}"; 
				}

            }
		}catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace(); 
			return "{\"type\":\"error\",\"msg\":\"���ϴ����ļ������ݳ��ִ���!����ϸ�˶�!\"}"; 
		}
		return msg; 
	}
    /**
     * ��Ӱ༶�û���ett
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
            // �ش� userId��userType,subjectId ������key
            List<Map<String,Object>> mapList=new ArrayList<Map<String, Object>>();
            for (ClassUser cuTmpe:cuTmpList){
                if(cuTmpe!=null){
                    Map<String,Object> tmpMap=new HashMap<String, Object>();
                    tmpMap.put("userId",cuTmpe.getUid());
                    Integer userType=3;
                    if(cuTmpe.getRelationtype()!=null){
                        if(cuTmpe.getRelationtype().trim().equals("�ο���ʦ"))
                            userType=2;
                        else if(cuTmpe.getRelationtype().trim().equals("������"))
                            userType=1;
                    }
                    tmpMap.put("userType",userType);
                    tmpMap.put("subjectId",cuTmpe.getSubjectid()==null?-1:cuTmpe.getSubjectid());
                    mapList.add(tmpMap);
                }
            }
            if(!EttInterfaceUserUtil.OperateClassUser(mapList, clsid, dcschoolId)){
                System.out.println("classUserͬ������Уʧ��!");
                return false;
            } else
                System.out.println("classUserͬ������У�ɹ�!");
        }
        return true;
    }
    /**
     * ����û���ett
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
            //�����û��ӿ�
            if(EttInterfaceUserUtil.addUserBase(tmpUList)){
                System.out.println("�û���Ϣͬ������У�ɹ�!");
            }else{
                System.out.println("�û���Ϣͬ������Уʧ��!");
                return false;
            }
        }
        return true;
    }



	/**
	 * ���ݰ༶��Ϣ�õ�ѧ������
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
			jeEntity.setMsg("�쳣���󣬰༶δ���յ�����ˢ��ҳ������ԣ�");
			response.getWriter().print(jeEntity.toJSON());
			return;
		}
		
		List<StudentInfo> stuList = this.studentManager.getStudentByClass(Integer.parseInt(classIdStr), year, pattern);
		jeEntity.setObjList(stuList);
		jeEntity.setType("success");
		response.getWriter().print(jeEntity.toJSON());
		
		
	}
	/**
	 * ����ѧ�ŵõ�ѧ����Ϣ
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(params="m=getref",method=RequestMethod.POST) 
	public void getStudentByStuno(HttpServletRequest request,HttpServletResponse response)throws Exception{
		JsonEntity jeEntity=new JsonEntity();
		String stuno = request.getParameter("stuno");
		if(stuno==null||stuno.trim().length()<1){
			jeEntity.setMsg("�쳣����ѧ��δ���յ�����ˢ��ҳ������ԣ�");
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
