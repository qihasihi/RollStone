package com.school.share;

import com.school.entity.DeptUser;
import com.school.entity.UserInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 *  专题同步(接口，定时执行[上行])
 *  Created by zhengzhou on 2014-2-22.
 *  @deprecated 涉及的表：
 *  dc_tp_course_info;               专题
 *  dc_tp_j_course_question;         专题试题
 *  DC_TP_J_COURSE_TASK_PAPER        专题任务试卷
 *  DC_TP_J_COURSE_TCH_MATERIA       专题教材
 *  DC_TP_J_COUR_RESOURCE            专题资源
 *  DC_TP_OPERATE_INFO               专题操作
 *  DC_TP_PAPER_INFO                 专题试卷
 *  DC_TP_TASK_INFO                  专题任务
 *  DC_TP_TOPIC_INFO                 专题论题
 *  DC_TP_TOPIC_THEME_INFO           专题论题主题
 */
public class ShareCourse extends TimerTask {
    /**
     * 生成的模板XML
     */
    private final static String _Template_name="ShareTemplate.xml";
    /**
     * 每批XML文件的个数
     */
    private Integer _PageSize=1;
    /**
     * 专题资源的记录
     */
    private Integer _PageSize_Children=20;
    /**
     * 分校ID
     */
    private String _Schoolid=null;

    private final String _UploadFileName="uploadFile.txt";

    private ServletContext request;
    public ShareCourse(ServletContext application){
        //分校ID
        this.request=application;
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }


    public static void main(String[] args){
        String templatepath="F:/javaworkspance/sz_school/WebRoot/uploadfile/tmp/"+_Template_name;
        File fromF=new File(templatepath);
        if(!fromF.exists()){
            //记录错误信息
            System.out.println("分校异常!没有发现资源上传模版!");
            return;
        }

        String writeUrl="F:/javaworkspance/sz_school/WebRoot/uploadfile/tmp/1393223762485.xml";
        //创建文件夹，并复制模板，进行操作
        File tmpF1=new File(writeUrl);
        try{
            if(!tmpF1.getParentFile().exists())
                tmpF1.getParentFile().mkdirs();
            if(tmpF1.exists()&&tmpF1.isFile())
                tmpF1.delete();
            //复制模板
            FileUtils.copyFile(fromF, tmpF1);
        }catch(Exception e){
            //记录日志
            System.out.println("分校异常!创建空文件失败，原因：未知");
            e.printStackTrace();
        }
        UserInfo us=new UserInfo();
        us.setUserid(2);
        us.setUsername("asdf");
        List<Object> col=new ArrayList<Object>();
        col.add("resid");
        OperateXMLUtil.addXml(writeUrl,col,new Object[]{"3"});
        OperateXMLUtil.addXml(writeUrl,col,new Object[]{"4"});
        List<UserInfo> usList=new ArrayList<UserInfo>();
        usList.add(us);
        us=new UserInfo();
        us.setUserid(4);
        us.setUsername("asdfaaaaaaaaaaaaaaaaa");
        usList.add(us);
        OperateXMLUtil.updateEntityToXml(writeUrl, "resid", "3", usList);
        List<DeptUser> dpList=new ArrayList<DeptUser>();
        DeptUser du=new DeptUser();
        du.setUserid(333333);
        du.setDeptid(3333);
        dpList.add(du);
        du=new DeptUser();
        du.setUserid(22222);
        du.setDeptid(22222);
        dpList.add(du);
        du=new DeptUser();
        du.setUserid(22222111);
        du.setDeptid(22222111);
        dpList.add(du);
        OperateXMLUtil.updateEntityToXml(writeUrl, "resid", "3", dpList);
        List list=OperateXMLUtil.findXml(writeUrl,"//table //row",false);
        System.out.println(list);
    }

    @Override
    public void run() {
        String firstDirectory= MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(_Schoolid) + UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString())+UtilTool.DateConvertToString(new Date(), UtilTool.DateType.smollDATE);
        String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/"; //根路径
        String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();    //xml文件的文件名
        ITpCourseManager courseManager=(TpCourseManager)SpringBeanUtil.getBean("tpCourseManager");
        String directionPath=parentDirectory+firstDirectory+"/";
        String dataFileParent=directionPath+"data/";
        String xmlPathUrl=dataFileParent+"/"+filename;

        //记录需要上传的文件完整路径
        String uploadFileTxt=directionPath+_UploadFileName;
        //模板XML文件名称及路径
        String templatepath=parentDirectory+_Template_name;
        File fromF=new File(templatepath);
        if(!fromF.exists()){
            //记录错误信息
            System.out.println("分校异常!没有发现资源上传模版!");
            return;
        }
        //分批量得到要上行的专题
        PageResult presult=new PageResult();
        presult.setPageSize(_PageSize);
        presult.setPageNo(0);
        TpCourseInfo courseEntity=new TpCourseInfo();
        courseEntity.setSharetype(2);   //云端共享
       // courseEntity.setCloudstatus(0);   //云端分享状态： 待审核
        courseEntity.setLocalstatus(1); //本地状态 正常

        boolean ishasData=false;
        //循环查询
        while(true){
            presult.setPageNo(presult.getPageNo()+1);
            String writeUrl=xmlPathUrl;
            //XML文件分批量
            writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))+"_"+presult.getPageNo()+writeUrl.substring(writeUrl.lastIndexOf("."));
            //查询要共享的专题
            List<TpCourseInfo> tpCourseList=courseManager.getShareList(courseEntity, presult);
            if(tpCourseList==null||tpCourseList.size()<1)   //说明没有要共享的专题了，退出
                break;
            //创建文件夹，并复制模板，进行操作
            File tmpF1=new File(writeUrl);
            try{
                if(!tmpF1.getParentFile().exists())
                    tmpF1.getParentFile().mkdirs();
                if(tmpF1.exists()&&tmpF1.isFile())
                    tmpF1.delete();
                //复制模板
                FileUtils.copyFile(fromF, tmpF1);
            }catch(Exception e){
                //记录日志
                System.out.println("分校异常!创建空文件失败，原因：未知");
                e.printStackTrace();
            }

            ishasData=true;
            File txtFile=new File(directionPath+_UploadFileName);
            if(txtFile.exists()){
                System.gc();        //垃圾处理，再删除文件，重新生成
                txtFile.delete();
            }
            for(TpCourseInfo tc:tpCourseList){
                //tc.setSchoolname(UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME"));
                //写入XML
                if(!ShareCourseUtil.addDateToXml(writeUrl,tc)){
                    System.out.println("异常错误，写入专题至XML文件失败，原因：未知!");
                    return;
                }
                //先记录空值
                UtilTool.writeFile(request,directionPath,_UploadFileName,"");
                //得到专题所用的图片素材进行上传
                String path=MD5_NEW.getMD5Result(tc.getCourseid().toString());
                String folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                File f=new File(folderpath);
                if(f.exists()&&f.isDirectory()){
                    ZipUtil.genZip(folderpath,directionPath,path);    //生成zip

                    //将生成的ZIP文件，记录到上传文件列表中。
                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");
                }

                //得到
                PageResult presource=new PageResult();
                presource.setPageNo(0);
                presource.setPageSize(_PageSize_Children);
                //循环得到专题操作记录
               /* while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List<TpOperateInfo> tpOperateList=getOperateByCourseId(tc.getCourseid(),presource);
                    if(tpOperateList==null||tpOperateList.size()<1)break;
                    //记录写入XML
                    //写入XML                   ;
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid() + "", tpOperateList)){
                        System.out.println("异常错误，写入专题操作记录至XML文件失败，原因：未知!");
                        return;
                    }
                }*/
                //循环得到专题教材记录
                presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List teachingMaterialList=getTeachingMaterialByCourseId(tc.getCourseid(),presource);
                    if(teachingMaterialList==null||teachingMaterialList.size()<1)break;
//                    for(int i=0;i<teachingMaterialList.size();i++){
                        //写入XML中
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",teachingMaterialList)){
                            System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                            return;
                        }
//                  }
                }
                //循环得到专题资源
                presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    //得到专题资源
                    List<TpCourseResource> tpCourseResourceList=getCourseResouceByCourseId(tc.getCourseid(),presource);
                    if(tpCourseResourceList==null||tpCourseResourceList.size()<1) //如果没有数据，则退出循环。
                        break;
                    //得到资源记录
                    for(TpCourseResource tcr:tpCourseResourceList){
                        if(tcr!=null&&tcr.getResid()!=null){
                            ResourceInfo resEntity=getResourceByResourceId(tcr.getResid());
                            //得到资源文件并记录在文件中
                            if(resEntity!=null){
                                List<ResourceInfo> resList=new ArrayList<ResourceInfo>();
                                resList.add(resEntity);
                                if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",resList)){
                                    System.out.println("异常错误，写入资源记录至XML文件失败，原因：未知!");
                                    return;
                                }
                                //查看资源文件是否存在，如果存在，则进行压缩
                                String directoryName=UtilTool.getResourceMd5Directory(resEntity.getResid().toString());
                                String filepath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+directoryName;
                                File tmpF=new File(filepath);
                                if(tmpF.exists()&&tmpF.isDirectory()){
                                    ZipUtil.genZip(filepath,directionPath,directoryName);//将资源文件生成ZIP文件至项目目录下
                                }
                            }
                        }
                    }
                    //写入XML中，
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",tpCourseResourceList)){
                        System.out.println("异常错误，写入专题资源记录至XML文件失败，原因：未知!");
                        return;
                    }
                }
                //循环得到专题论题及专题主题
                presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List<TpTopicInfo> topicList=getTpTopicList(tc.getCourseid(),presource);
                    if(topicList==null||topicList.size()<1)break;
                    //写入XML中，
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",topicList)){
                        System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                        return;
                    }
                    for(TpTopicInfo topic:topicList){
                        if(topic==null||topic.getTopicid()==null)continue;
                        //写入XML文件,并得到备注里的 附件 进行压综
                        path=MD5_NEW.getMD5Result(topic.getTopicid().toString());
                        folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                        }
                        //得到主题
                        PageResult pagetheme=new PageResult();
                        pagetheme.setPageSize(_PageSize_Children);
                        pagetheme.setPageNo(0);
                        while(true){
                            pagetheme.setPageNo(pagetheme.getPageNo()+1);
                            List<TpTopicThemeInfo> themeList=getTpTopicThemeList(topic.getTopicid(),pagetheme);
                            if(themeList==null||themeList.size()<1)break;
                            //记录XML中，并 得到  附件 成生压缩文件
                            for(TpTopicThemeInfo theme:themeList){
                                path= MD5_NEW.getMD5Result(theme.getThemeid().toString());
                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                f=new File(folderpath);
                                System.out.println(folderpath);
                                if(f.exists()&&f.isDirectory()){
                                    ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                                }
                            }
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",themeList)){
                                System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                                return;
                            }
                        }
                    }

                }

                //循环得到专题试卷记录  (暂不做)
//                presource.setPageNo(0);
//                while(true){
//                    presource.setPageNo(presource.getPageNo()+1);
//
//                }
                //循环得到专题试题记录
                presource.setPageNo(0);
                while(true){
                   presource.setPageNo(presource.getPageNo()+1);
                   List<TpCourseQuestion> courseQuestionList =getCourseQuestionList(tc.getCourseid(),presource);
                   if(courseQuestionList==null||courseQuestionList.size()<1)break;
                    for(TpCourseQuestion courseQues:courseQuestionList){
                        if(courseQues==null||courseQues.getQuestionid()==null)continue;
                        QuestionInfo ques=courseQues.getQuestioninfo();
                        if(ques==null)ques=getQuestionInfo(courseQues.getQuestionid());
                        if(ques==null)continue;
                        //记录XML中,并得到相关图片进行压综
                        path=MD5_NEW.getMD5Result(courseQues.getQuestionid().toString());
                        folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                        }
                        //将Question写入XML中
                        List<QuestionInfo> quesList=new ArrayList<QuestionInfo>();
                        quesList.add(ques);
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",quesList)){
                            System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                            return;
                        }
                        //得到答案信息
                        List<QuestionOption> quesOptsList=getQuestionOption(courseQues.getQuestionid());
                        if(quesOptsList==null||quesOptsList.size()<1)
                            continue;
                        //保存答案信息到数据库
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",quesOptsList)){
                            System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                            return;
                        }
                        //得到答案信息中的附件
                        for(QuestionOption qoption:quesOptsList){
                            if(qoption!=null&&qoption.getRef()!=null){
                                //写入XML文件,并得到备注里的 附件 进行压综
                                path=MD5_NEW.getMD5Result(qoption.getQuestionid().toString());
                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                f=new File(folderpath);
                                if(f.exists()&&f.isDirectory()){
                                    ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                                }
                            }
                        }

                    }
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",courseQuestionList)){
                        System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                        return;
                    }
                }
                //循环得到专题任务
                    presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List<TpTaskInfo> taskList=this.getTaskList(tc.getCourseid(),presource);
                    if(taskList==null||taskList.size()<1)break;
                    for(TpTaskInfo tk:taskList){
                        if(tk==null)continue;
                        //记录XML中,并得到相关图片进行压综
                        path=MD5_NEW.getMD5Result(tk.getTaskid().toString());
                        folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                        }
                    }
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",taskList)){
                        System.out.println("异常错误，写入专题教材记录至XML文件失败，原因：未知!");
                        return;
                    }
                }
            }
            //生成ZIP文档

            ZipUtil.genZip(dataFileParent, directionPath, firstDirectory + "_data_" + presult.getPageNo());
            //将文档路径写入TXT中进行上传
            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data_"+presult.getPageNo()+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
            //开始解析文件进行传输
            UtilTool.uploadResourceToTotalSchool(uploadFileTxt);//上传附件文件存储的txt文档
            //然后删除Xml文件
            File tmp=new File(writeUrl);
            if(tmp.exists()&&tmp.isFile()){
                System.gc();    //先内在回收，防止文件占用，删除失败!
                tmp.delete();
            }
        }

        //如果有数据，则调用接口
        if(ishasData){
            //得到key
            String fpath = request.getRealPath("/") + "school.txt";
            BufferedReader br = null;
            StringBuilder content = null;
            try {
                br = new BufferedReader(new FileReader(fpath.trim()));
                String lineContent = null;
                while ((lineContent = br.readLine()) != null) {
                    if (content == null)
                        content = new StringBuilder(lineContent);
                    else
                        content.append("\n" + lineContent);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return;
            } finally {
                if (br != null)
                    try{
                        br.close();
                    }catch(Exception e){e.printStackTrace();}
            }
            if(content==null||content.toString().trim().length()<1){
                System.out.println("异常错误，school.txt中没有发现加密狗码!请联系总校相关客服人员!");
                //插入错误信息
                return;
            }
            String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
            totalSchoolUrl+="/synchroCourse";
            String params="m=tbjxpt&key="+content.toString()+"&resolveFilename="+firstDirectory+"_data.zip";

            //调用总校接口，解析文件
            if(ShareCourseUtil.sendCoursePathToTotalSchool(totalSchoolUrl,params)){
              //  开始更新数据库，更新已经同步的资源
                if(courseManager.doUpdateShareCourse()){
                    System.out.println("resource resolved success!");
                }else{
                    System.out.println("resource update success!but localhost dataset is error!");
                }
            }else{
                System.out.println("course resolved error!");
            }
        }
    }
    /**
     * 得到问题选项列表
     * @param questionid
     * @return
     */
    private  List<QuestionOption> getQuestionOption(Long questionid){
        if(questionid==null)return null;
        IQuestionOptionManager questionOptionManager =(QuestionOptionManager)SpringBeanUtil.getBean("questionOptionManager");
        QuestionOption questoption=new QuestionOption();
        questoption.setQuestionid(questionid);
        List<QuestionOption> qoList= questionOptionManager.getList(questoption,null);
        if(qoList!=null&&qoList.size()>0){
            for (QuestionOption qo:qoList){
                if(qo!=null){
                    if(qo.getSaveContent()!=null)
                        qo.setContent(qo.getSaveContent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/", "_SZ_SCHOOL_IMG_PLACEHOLDER_"));
                }
            }
        }
        return qoList;
    }

    /**
     * 得到任务列表
     * @param courseid
     * @param presult
     * @return
     */
    private List<TpTaskInfo> getTaskList(Long courseid,PageResult presult){
        if(courseid==null)return null;
        ITpTaskManager taskManager =(TpTaskManager)SpringBeanUtil.getBean("tpTaskManager");
        TpTaskInfo taskInfo=new TpTaskInfo();
        taskInfo.setCourseid(courseid);
        return taskManager.getList(taskInfo,presult);
    }

    /**
     * 得到试题信息
     * @param questionid 试题ID
     * @return
     */
    private QuestionInfo getQuestionInfo(Long questionid){
        if(questionid==null)return null;
        IQuestionManager questionManager =(QuestionManager)SpringBeanUtil.getBean("questionManager");
        QuestionInfo questionInfo=new QuestionInfo();
        questionInfo.setQuestionid(questionid);

        PageResult presult=new PageResult();
        presult.setPageNo(1);presult.setPageSize(1);
        List<QuestionInfo> quesList=questionManager.getList(questionInfo,presult);
        if(quesList==null||quesList.size()<1)return null;
        QuestionInfo ques= quesList.get(0);
        if(ques!=null){
            if(ques.getAnalysis()!=null)
                ques.setAnalysis(ques.getSaveAnalysis().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/", "_SZ_SCHOOL_IMG_PLACEHOLDER_"));
            if(ques.getContent()!=null)
                ques.setContent(ques.getSaveContent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/", "_SZ_SCHOOL_IMG_PLACEHOLDER_"));
        }
        return ques;
    }
    /**
     * 得到专题试题
     * @param courseid
     * @param presult
     * @return
     */
    private List<TpCourseQuestion> getCourseQuestionList(Long courseid,PageResult presult){
        if(courseid==null)return null;
        ITpCourseQuestionManager courseQuestionManager =(TpCourseQuestionManager)SpringBeanUtil.getBean("tpCourseQuestionManager");
        TpCourseQuestion courseQuestion=new TpCourseQuestion();
        courseQuestion.setCourseid(courseid);
        List<TpCourseQuestion> tpCqList= courseQuestionManager.getList(courseQuestion,presult);
        if(tpCqList!=null&&tpCqList.size()>0){
            for (TpCourseQuestion tcq:tpCqList){
                QuestionInfo ques= tcq.getQuestioninfo();
                if(ques!=null){
                    if(ques.getAnalysis()!=null)
                        ques.setAnalysis(ques.getSaveAnalysis().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/", "_SZ_SCHOOL_IMG_PLACEHOLDER_"));
                    if(ques.getContent()!=null)
                        ques.setContent(ques.getSaveContent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/", "_SZ_SCHOOL_IMG_PLACEHOLDER_"));
                }
            }
        }
        return tpCqList;
    }

    /**
     * 得到专题论题主题
     * @param topicid 专题ID
     * @param presult
     * @return
     */
    private List<TpTopicThemeInfo> getTpTopicThemeList(Long topicid,PageResult presult){
        if(topicid==null)return null;
        ITpTopicThemeManager topicManager=(TpTopicThemeManager)SpringBeanUtil.getBean("tpTopicThemeManager");
        TpTopicThemeInfo  theme=new TpTopicThemeInfo();
        theme.setTopicid(topicid);
        theme.setSelectType(3);//1:不查 text  2:查text  3:不连查
        List<TpTopicThemeInfo> topicThemeList= topicManager.getList(theme,presult);

        if(topicThemeList!=null&&topicThemeList.size()>0){
            for (TpTopicThemeInfo ttobj:topicThemeList){
                if(ttobj!=null){
                    if(ttobj.getThemecontent()!=null)
                        ttobj.setThemecontent(ttobj.getThemecontent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/","_SZ_SCHOOL_IMG_PLACEHOLDER_"));
                    if(ttobj.getCommentcontent()!=null)
                        ttobj.setCommentcontent(ttobj.getCommentcontent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/","_SZ_SCHOOL_IMG_PLACEHOLDER_"));
                 }
            }
        }
        return topicThemeList;
    }
    /**
     * 得到专题论题
     * @param courseid
     * @param presult
     * @return
     */
    private List<TpTopicInfo> getTpTopicList(Long courseid,PageResult presult){
        if(courseid==null)return null;
        TpTopicInfo tpTopic=new TpTopicInfo();
        tpTopic.setCourseid(courseid);
        ITpTopicManager topicManager=(TpTopicManager)SpringBeanUtil.getBean("tpTopicManager");
        List<TpTopicInfo> tpcList= topicManager.getList(tpTopic,presult);
        if(tpcList!=null&&tpcList.size()>0){
            for (TpTopicInfo tpc:tpcList){
                if(tpc!=null&&tpc.getTopiccontent()!=null){
                    tpc.setTopiccontent(tpc.getTopiccontent().replaceAll("ueditor\\/jsp\\/\\.\\.\\/\\.\\.\\/","_SZ_SCHOOL_IMG_PLACEHOLDER_"));
                }
            }
        }
        return tpcList;
    }

    /**
     * 得到专题与教材连接表信息
     * @param courseid
     * @param presult
     * @return
     */
    private List getTeachingMaterialByCourseId(Long courseid,PageResult presult){
        if(courseid==null)return null;
        ITpCourseTeachingMaterialManager teachingMaterialManager=(TpCourseTeachingMaterialManager)SpringBeanUtil.getBean("tpCourseTeachingMaterialManager");
        TpCourseTeachingMaterial ctma=new TpCourseTeachingMaterial();
        ctma.setCourseid(courseid);
        return teachingMaterialManager.getList(ctma,presult);
    }
    /**
     * 得到专题操作信息
     * @param courseid
     * @param presult
     * @return
     */
    private List<TpOperateInfo> getOperateByCourseId(Long courseid,PageResult presult){
        if(courseid==null)
            return null;
        TpOperateManager tpOperateManager=(TpOperateManager)SpringBeanUtil.getBean("tpOperateManager");
        TpOperateInfo operateInfo=new TpOperateInfo();
        operateInfo.setCourseid(courseid);
        return tpOperateManager.getList(operateInfo,presult);
    }
    /**
     * 得到专题资源信息
     * @param courseid
     * @return
     */
    private List<TpCourseResource> getCourseResouceByCourseId(Long courseid,PageResult presult){
        if(courseid==null)
            return null;
        ITpCourseResourceManager courseResourceManager=(TpCourseResourceManager)SpringBeanUtil.getBean("tpCourseResourceManager");
        TpCourseResource courseResource=new TpCourseResource();
        courseResource.setCourseid(courseid);
        return courseResourceManager.getList(courseResource,presult);
    }

    /**
     * 得到资源信息记录
     * @return
     */
    private ResourceInfo getResourceByResourceId(Long resid){
        if(resid==null)return null;

        IResourceManager resourcemanager=(ResourceManager)SpringBeanUtil.getBean("resourceManager");
        ResourceInfo resEntity=new ResourceInfo();
        resEntity.setResid(resid);
        resEntity.setResstatus(-3);//resstatus<>3 没有被删除
        resEntity.setResdegree(3);//3:本地资源
        resEntity.setNetsharestatus(-1);//并且，未同步
        //一条记录
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<ResourceInfo> resList=resourcemanager.getList(resEntity,presult);
        if(resList==null||resList.size()<1)return null;
        return resList.get(0);
    }

}


/**
 * ShareCourse工具类
 */
class ShareCourseUtil{
    /**
     * fasong ziyuan
     * @param totalSchoolUrl
     * @return
     */
    public static boolean sendCoursePathToTotalSchool(String totalSchoolUrl,String params){
        if(totalSchoolUrl==null)return false;

        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(totalSchoolUrl.toString());

            httpConnection = (HttpURLConnection) url.openConnection();

            httpConnection.setRequestMethod("POST");
            if(params!=null)
            httpConnection.setRequestProperty("Content-Length",
            String.valueOf(params.length()));
            httpConnection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");

            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);
			/*
			 * PrintWriter printWriter = new
			 * PrintWriter(httpConnection.getOutputStream());
			 * printWriter.print(parameters); printWriter.close();
			 */

            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    httpConnection.getOutputStream(), "8859_1");
            if(params!=null)
             outputStreamWriter.write(params);
            outputStreamWriter.flush();
            outputStreamWriter.close();

            code = httpConnection.getResponseCode();
        } catch (Exception e) {			// 异常提示
            System.out.println("异常错误!TOTALSCHOOL未响应!");
            return false;
        }
        StringBuffer stringBuffer = new StringBuffer();
        if (code == HttpURLConnection.HTTP_OK) {
            try {
                String strCurrentLine;
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(httpConnection.getInputStream()));
                while ((strCurrentLine = reader.readLine()) != null) {
                    stringBuffer.append(strCurrentLine).append("\n");
                }
                reader.close();
            } catch (IOException e) {
                System.out.println("异常错误!");
                return false;
            }
        }else if(code==404){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return false;
        }else if(code==500){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return false;
        }
        String returnContent=null;
        try {
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //转换成JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        String type=jb.containsKey("type")?jb.getString("type"):"";
        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
        if(type!=null&&type.trim().toLowerCase().equals("success")){
            System.out.println(msg);
            return true;
        }else{
            System.out.println(msg);return false;
        }
    }
    /**
     * 写入XML文件中
     * @param filepath  XML文件路径
     * @param t   要写入的实体 要写入的实体
     * @param <T>
     * @return
     */
    public static <T> boolean addDateToXml(String filepath,T t){
        if(t==null||filepath==null)return false;
        Method[] med=t.getClass().getMethods();
        List<String> columnsList=new ArrayList<String>();
        List<Object> valueList=new ArrayList<Object>();
        for(Method md:med){
            if(md.getName().length()>3&&md.getName().indexOf("get")==0){
                Object obj=null;
                try{
                    obj=md.invoke(t);;
                    //System.out.println(md.getName()+":"+obj);
                }catch(Exception e){
                    e.printStackTrace();
                }
                String key=md.getName().substring(3);
                Object val=obj;
                if(!columnsList.contains(key)){
                    columnsList.add(key);
                    valueList.add((val==null?"NULL":val));
                }
            }
        }
        return OperateXMLUtil.addXml(filepath,columnsList,valueList.toArray());
    }
}