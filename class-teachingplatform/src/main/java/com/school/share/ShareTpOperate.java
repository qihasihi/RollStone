package com.school.share;

import com.school.entity.DictionaryInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * 上传TpOperateInfo记录元素
 * Created by zhengzhou on 14-3-12.
 */
public class ShareTpOperate extends TimerTask {
    private ServletContext request;
    public ShareTpOperate(ServletContext application){
        this.request=application;
        //分校ID
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }

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
    //上传的文件序列
    private final String _UploadFileName="uploadFile.txt";
    //记录Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    //上传核心
    public void run(){
        //如果是这第一次执行，则是启动执行，则直接返回
        if(!SynchroUtil._shareTpOperate){
            SynchroUtil._shareTpOperate=true;
            logger.info("--------------------分享资源操作启动执行，不执行-------------------");
            return;
        }
        logger.info("--------------------分享资源操作启动执行-------------------");
        String firstDirectory= MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(_Schoolid) + UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString());
        String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/shareTpOperate/"; //根路径
        String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();    //xml文件的文件名
        String directionPath=parentDirectory+firstDirectory+"/";
        String dataFileParent=directionPath+"data/";

        String xmlPathUrl=dataFileParent+"/"+filename;

        String xmlTemplateURL=parentDirectory+"../"+_Template_name;
        //得到当前的分校KEY
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }
        String ftime=null;//上次上传的时间
        //得到上一次更新的时间
        IDictionaryManager dictionaryManager=(DictionaryManager) SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dictionaryManager.getDictionaryByType("UP_OPERATE_FTIME");//上次上传的时间
        DictionaryInfo fupdateDic=null;
        if(dicList!=null&&dicList.size()>0){
            ftime=dicList.get(0).getDictionaryvalue();
            fupdateDic=dicList.get(0);
        }
        Date currentDate=new Date();
        //记录需要上传的文件完整路径
        String uploadFileTxt=directionPath+_UploadFileName;
        //模板XML文件名称及路径
        File fromF=new File(xmlTemplateURL);
        if(!fromF.exists()){
            //记录错误信息
            System.out.println("分校异常!没有发现上传模版!");
            return;
        }

        //分页查询
        PageResult presult=new PageResult();
        presult.setPageNo(0);
        presult.setPageSize(_PageSize);

         boolean ishasData=false;
        while(true){
             presult.setPageNo(presult.getPageNo() + 1);
            //得到数据
            List<TpOperateInfo> operateList=ShareTpOperateUtil.getOperateByFtime(ftime,presult);
            if(operateList==null||operateList.size()<1)break;
            String writeUrl=xmlPathUrl;
            writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))+"_"+presult.getPageNo()+writeUrl.substring(writeUrl.lastIndexOf("."));
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

            //TpOperaetList解析
            for(TpOperateInfo op:operateList){
                if(op==null)continue;
                //写入XML
                if(!UtilTool.addDateToXml(writeUrl, op)){
                    System.out.println("异常错误，写入专题至XML文件失败，原因：未知!");
                    return;
                }
                //数据类型 1：专题 2：专题任务 3：专题资源 4：专题论题 5：专题主题 6：专题试题
//                if(op.getDatatype()==1){
//
//                }else
                if(op.getDatatype()==2){//专题任务
                    List<TpTaskInfo> taskList=ShareTpOperateUtil.getTpTaskList(op.getTargetid(),null);
                    if(taskList==null||taskList.size()<1)continue;
                    taskList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", taskList)){
                        System.out.println("异常错误，写入专题任务记录至XML文件失败，原因：未知!");
                        return;
                    }
                    //记录XML中,并得到相关图片进行压综
                   String path=MD5_NEW.getMD5Result(taskList.get(0).getTaskid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                   File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                    }
                    TpTaskInfo tk=taskList.get(0);
                    if(tk!=null)
                        if(tk.getTasktype()==1){// 1：资源学习  2：论题  3： 试题
                            ResourceInfo resEntity=ShareTpOperateUtil.getResourceByResourceId(tk.getTaskvalueid());
                            if(resEntity==null)continue;
                            resEntity.setCourseid(op.getCourseid());
                            List<ResourceInfo> reslist=new ArrayList<ResourceInfo>();
                            reslist.add(resEntity);
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", reslist)){
                                System.out.println("异常错误，写入专题任务记录至XML文件失败，原因：未知!");
                                return;
                            }
                            //记录XML中,并得到相关图片进行压综
                             path=MD5_NEW.getMD5Result(resEntity.getResid().toString());
                              folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                             f=new File(folderpath);
                            if(f.exists()&&f.isDirectory()){
                                ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                            }
                        }else if(tk.getTasktype()==2){
                            List<TpTopicInfo> tpcList=ShareTpOperateUtil.getTpTopicList(tk.getTaskvalueid(),null);
                            if(tpcList==null||tpcList.size()<1)continue;
                            tpcList.get(0).setCourseid(op.getCourseid());
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", tpcList)){
                                System.out.println("异常错误，写入论题记录至XML文件失败，原因：未知!");
                                return;
                            }
                            //记录XML中,并得到相关图片进行压综
                                path=MD5_NEW.getMD5Result(tpcList.get(0).getTopicid().toString());
                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                f=new File(folderpath);
                                if(f.exists()&&f.isDirectory()){
                                    ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                              }
                            //得到所有专题下的主题
                            PageResult childResult=new PageResult();
                            childResult.setPageSize(_PageSize_Children);
                            childResult.setPageNo(0);
                            while(true){
                                childResult.setPageNo(childResult.getPageNo()+1);
                                List<TpTopicThemeInfo> topicthemeList=ShareTpOperateUtil.getTpTopicThemeList(null,tpcList.get(0).getTopicid(),childResult);
                                if(topicthemeList==null||topicthemeList.size()<1)break;

                                for(TpTopicThemeInfo theme:topicthemeList){
                                    if(theme==null)continue;
                                    theme.setCourseid(op.getCourseid());
                                    //记录XML中,并得到相关图片进行压综
                                    path=MD5_NEW.getMD5Result(theme.getThemeid().toString());
                                     folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                     f=new File(folderpath);
                                    if(f.exists()&&f.isDirectory()){
                                        ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                                    }
                                }
                                if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", topicthemeList)){
                                    System.out.println("异常错误，写入主题记录至XML文件失败，原因：未知!");
                                    return;
                                }
//                                //记录XML中,并得到相关图片进行压综
//                                path=MD5_NEW.getMD5Result(topicthemeList.get(0).getTopicid().toString());
//                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
//                                f=new File(folderpath);
//                                if(f.exists()&&f.isDirectory()){
//                                    ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
//                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
//                                }
                            }
                        }else if(tk.getTasktype()==3){//试题
                            QuestionInfo quesEntity=ShareTpOperateUtil.getQuestionInfo(tk.getTaskvalueid());
                            if(quesEntity==null)continue;
                            List<QuestionInfo> quesList=new ArrayList<QuestionInfo>();
                            quesList.add(quesEntity);
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                                System.out.println("异常错误，写入试题记录至XML文件失败，原因：未知!");
                                return;
                            }
                            //记录XML中,并得到相关图片进行压综
                            path=MD5_NEW.getMD5Result(quesEntity.getQuestionid().toString());
                             folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                             f=new File(folderpath);
                            if(f.exists()&&f.isDirectory()){
                                ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                            }

                            //得到试题选项
                            List<QuestionOption> quesOptsList=ShareTpOperateUtil.getQuestionOption(quesEntity.getQuestionid());
                            if(quesOptsList==null||quesOptsList.size()<1)continue;
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                                System.out.println("异常错误，写入试题选项至XML文件失败，原因：未知!");
                                return;
                            }
                        }

                }else if(op.getDatatype()==3){//专题资源
                    ResourceInfo resEntity=ShareTpOperateUtil.getResourceByResourceId(op.getTargetid());
                    if(resEntity==null)continue;
                    resEntity.setCourseid(op.getCourseid());
                    List<ResourceInfo> reslist=new ArrayList<ResourceInfo>();
                    reslist.add(resEntity);
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", reslist)){
                        System.out.println("异常错误，写入专题任务记录至XML文件失败，原因：未知!");
                        return;
                    }
                    //记录XML中,并得到相关图片进行压综
                    String path=MD5_NEW.getMD5Result(resEntity.getResid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                    File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                    }
                }else if(op.getDatatype()==4){//专题论题
                    List<TpTopicInfo> tpcList=ShareTpOperateUtil.getTpTopicList(op.getTargetid(),null);
                    if(tpcList==null||tpcList.size()<1)continue;
                    tpcList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", tpcList)){
                        System.out.println("异常错误，写入论题记录至XML文件失败，原因：未知!");
                        return;
                    }
                    //得到所有专题下的主题
                    PageResult childResult=new PageResult();
                    childResult.setPageSize(_PageSize_Children);
                    childResult.setPageNo(0);
                    while(true){
                        childResult.setPageNo(childResult.getPageNo()+1);
                        List<TpTopicThemeInfo> topicthemeList=ShareTpOperateUtil.getTpTopicThemeList(null,tpcList.get(0).getTopicid(),childResult);
                        if(topicthemeList==null||topicthemeList.size()<1)break;

                        for(TpTopicThemeInfo theme:topicthemeList){
                             if(theme==null)continue;
                            theme.setCourseid(op.getCourseid());
                            //记录XML中,并得到相关图片进行压综
                            String path=MD5_NEW.getMD5Result(theme.getThemeid().toString());
                            String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                            File f=new File(folderpath);
                            if(f.exists()&&f.isDirectory()){
                                ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                                UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                            }
                        }
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", topicthemeList)){
                            System.out.println("异常错误，写入主题记录至XML文件失败，原因：未知!");
                            return;
                        }

                        //记录XML中,并得到相关图片进行压综
                        String path=MD5_NEW.getMD5Result(topicthemeList.get(0).getTopicid().toString());
                        String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        File f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                        }
                    }

                }else if(op.getDatatype()==5){//专题主题
                    List<TpTopicThemeInfo> topicthemeList=ShareTpOperateUtil.getTpTopicThemeList(op.getTargetid(),null,null);
                    if(topicthemeList==null||topicthemeList.size()<1)continue;
                    topicthemeList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", topicthemeList)){
                        System.out.println("异常错误，写入主题记录至XML文件失败，原因：未知!");
                        return;
                    }
                    //记录XML中,并得到相关图片进行压综
                    String path=MD5_NEW.getMD5Result(topicthemeList.get(0).getTopicid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                    File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                    }
                    List<TpTopicInfo> tpcList=ShareTpOperateUtil.getTpTopicList(topicthemeList.get(0).getTopicid(),null);
                    if(tpcList==null||tpcList.size()<1)continue;
                    tpcList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", tpcList)){
                        System.out.println("异常错误，写入论题记录至XML文件失败，原因：未知!");
                        return;
                    }

                }else if(op.getDatatype()==6){//专题试题
                    QuestionInfo quesEntity=ShareTpOperateUtil.getQuestionInfo(op.getTargetid());
                    if(quesEntity==null)continue;
                    List<QuestionInfo> quesList=new ArrayList<QuestionInfo>();
                    quesList.add(quesEntity);
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                        System.out.println("异常错误，写入试题记录至XML文件失败，原因：未知!");
                        return;
                    }
                    //记录XML中,并得到相关图片进行压综
                    String path=MD5_NEW.getMD5Result(quesEntity.getQuestionid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                    File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //生成zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
                    }

                    //得到试题选项
                    List<QuestionOption> quesOptsList=ShareTpOperateUtil.getQuestionOption(quesEntity.getQuestionid());
                    if(quesOptsList==null||quesOptsList.size()<1)continue;
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                        System.out.println("异常错误，写入试题选项至XML文件失败，原因：未知!");
                        return;
                    }
                }
            }


            //将文档路径写入TXT中进行上传
         //   UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data_"+presult.getPageNo()+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。

            //然后删除Xml文件
//            File tmp=new File(writeUrl);
//            if(tmp.exists()&&tmp.isFile()){
//                System.gc();    //先内在回收，防止文件占用，删除失败!
//                tmp.delete();
//            }
        }
        if(!ishasData){//如果没有数据，则记录当前时间
            if(fupdateDic==null){
                fupdateDic=new DictionaryInfo();
                fupdateDic.setDictionarytype("UP_OPERATE_FTIME");
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                fupdateDic.setDictionaryname("专题操作元素表上次更新时间!");
                dictionaryManager.doSave(fupdateDic);
            }else{
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                dictionaryManager.doUpdate(fupdateDic);
            }
        }else{
            //开始解析文件进行传输
            UtilTool.uploadResourceToTotalSchool(uploadFileTxt);//上传附件文件存储的txt文档
            //生成ZIP文档
             ZipUtil.genZip(dataFileParent,directionPath,firstDirectory+"_data");

            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data.zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。


            //调用接口
            String[] urlObjArray=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_OPERATE").toString().split("\\?");
            String url_operate_url=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+urlObjArray[0];
            String params=urlObjArray[0]+"&key="+key+"&resolveFileName="+firstDirectory+"_data.zip";
            Map<String,Object> sendMap= ShareTpOperateUtil.sendPostURL(url_operate_url,params);
            if(sendMap==null){
                System.out.println("异常错误，调用接收接口错误!  原因：未知!");
            }else{
                boolean istrue=false;
                String type=sendMap.get("type").toString();
                String msg=sendMap.get("msg").toString();
                if(type.trim().equals("success")){
                    if(fupdateDic==null){
                        fupdateDic=new DictionaryInfo();
                        fupdateDic.setDictionarytype("UP_OPERATE_FTIME");
                        fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                        fupdateDic.setDictionaryname("专题操作元素表上次更新时间!");
                        istrue=dictionaryManager.doSave(fupdateDic);
                    }else{
                        fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                        istrue=dictionaryManager.doUpdate(fupdateDic);
                    }
                    if(istrue){
                        System.out.println(fupdateDic.getDictionaryvalue()+"  上传专题操作记录成功!");
                    }else
                        System.out.println(fupdateDic.getDictionaryvalue()+" 错误，记录本次更新时间错误");
                }else{
                    //记录操作日志
                    System.out.println(msg);
                }
            }

        }
    }
}

/**
 *
 */
class ShareTpOperateUtil{
    /**
     * 得到问题选项列表
     * @param questionid
     * @return
     */
    public static  List<QuestionOption> getQuestionOption(Long questionid){
        if(questionid==null)return null;
        IQuestionOptionManager questionOptionManager =(QuestionOptionManager)SpringBeanUtil.getBean("questionOptionManager");
        QuestionOption questoption=new QuestionOption();
        questoption.setQuestionid(questionid);
        return questionOptionManager.getList(questoption,null);
    }

    /**
     * 得到试题信息
     * @param questionid 试题ID
     * @return
     */
    public static QuestionInfo getQuestionInfo(Long questionid){
        if(questionid==null)return null;
        IQuestionManager questionManager =(QuestionManager)SpringBeanUtil.getBean("questionManager");
        QuestionInfo questionInfo=new QuestionInfo();
        questionInfo.setQuestionid(questionid);

        PageResult presult=new PageResult();
        presult.setPageNo(1);presult.setPageSize(1);
        List<QuestionInfo> quesList=questionManager.getList(questionInfo,presult);
        if(quesList==null||quesList.size()<1)return null;
        return quesList.get(0);
    }


    /**
     * 得到专题论题
     * @param taskid
     * @param presult
     * @return
     */
    public static List<TpTaskInfo> getTpTaskList(Long taskid,PageResult presult){
        if(taskid==null)return null;
        if(presult==null){
            presult=new PageResult();
            presult.setPageNo(1);
            presult.setPageSize(1);
        }
        TpTaskInfo tpTask=new TpTaskInfo();
        tpTask.setTaskid(taskid);
        ITpTaskManager taskManager=(TpTaskManager)SpringBeanUtil.getBean("tpTaskManager");
        return taskManager.getList(tpTask,presult);
    }
    /**
     * 得到专题论题主题
     * @param themeid 专题ID
     * @param presult
     * @return
     */
    public static List<TpTopicThemeInfo> getTpTopicThemeList(Long themeid,Long topicid,PageResult presult){
        if(themeid==null)return null;
        if(presult==null){
            presult=new PageResult();
            presult.setPageNo(1);
            presult.setPageSize(1);
        }
        ITpTopicThemeManager topicManager=(TpTopicThemeManager)SpringBeanUtil.getBean("tpTopicThemeManager");
        TpTopicThemeInfo  theme=new TpTopicThemeInfo();
        if(themeid!=null)
            theme.setThemeid(themeid);
        if(topicid!=null)
            theme.setTopicid(topicid);
        theme.setSelectType(3);//1:不查 text  2:查text  3:不连查
        return topicManager.getList(theme,presult);
    }
    /**
     * 得到专题论题
     * @param topicid
     * @param presult
     * @return
     */
    public static List<TpTopicInfo> getTpTopicList(Long topicid,PageResult presult){
        if(topicid==null)return null;
        if(presult==null){
            presult=new PageResult();
            presult.setPageNo(1);
            presult.setPageSize(1);
        }
        TpTopicInfo tpTopic=new TpTopicInfo();
        tpTopic.setTopicid(topicid);
        ITpTopicManager topicManager=(TpTopicManager)SpringBeanUtil.getBean("tpTopicManager");
        return topicManager.getList(tpTopic,presult);
    }

    /**
     * 得到专题与教材连接表信息
     * @param courseid
     * @param presult
     * @return
     */
    public static List getTeachingMaterialByCourseId(Long courseid,PageResult presult){
        if(courseid==null)return null;
        // ITeachingMaterialManager teachingMaterialManager=(TeachingMaterialManager)SpringBeanUtil.getBean("tpOperateManager");


        return null;

    }
    /**
     * 得到专题操作信息
     * @param ftime  上次更新的时间 YYYY-MM-DD hh24:mi:ss格式
     * @param presult
     * @return
     */
    public static List<TpOperateInfo> getOperateByFtime(String ftime,PageResult presult){
        if(ftime==null||ftime.toString().trim().length()<1)
            ftime=null;
        ITpOperateManager tpOperateManager=(TpOperateManager)SpringBeanUtil.getBean("tpOperateManager");
        return tpOperateManager.getSynchroList(ftime, presult);
    }
    /**
     * 得到专题资源信息
     * @param courseid
     * @return
     */
    public static List<TpCourseResource> getCourseResouceByCourseId(Long courseid,PageResult presult){
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
    public static ResourceInfo getResourceByResourceId(Long resid){
        if(resid==null)return null;


        IResourceManager resourcemanager=(ResourceManager)SpringBeanUtil.getBean("resourceManager");
        ResourceInfo resEntity=new ResourceInfo();
        resEntity.setResid(resid);
        //一条记录
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<ResourceInfo> resList=resourcemanager.getList(resEntity,presult);
        if(resList==null||resList.size()<1)return null;
        return resList.get(0);
    }

    /**
     *后台调用接口
     * @param urlstr
     * @return
     */
    public static Map<String,Object> sendPostURL(String urlstr,String params){
        HttpURLConnection httpConnection;
        URL url;
        int code;
        try {
            url = new URL(urlstr);

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
            return null;
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
                e.printStackTrace();;
                return null;
            }
        }else if(code==404){
            // 提示 返回
            System.out.println("异常错误!404错误，请联系管理人员!");
            return null;
        }else if(code==500){
            System.out.println("异常错误!500错误，请联系管理人员!");
            return null;
        }
        String returnContent=null;
        try {
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String,Object> returnMap=null;
        //转换成JSON
        System.out.println(returnContent);
        JSONObject jb=JSONObject.fromObject(returnContent);
        String type=jb.containsKey("type")?jb.getString("type"):"";
        String msg=jb.containsKey("msg")?jb.getString("msg"):"";
        Object objList=jb.containsKey("objList")?jb.get("objList"):null;

        returnMap=new HashMap<String,Object>();
        returnMap.put("type",type);
        returnMap.put("msg",msg);
        System.out.println(msg);

        if(type!=null&&type.trim().toLowerCase().equals("success")){
            System.out.println(msg);
            JSONArray jr=JSONArray.fromObject(objList);
            String val="";
            if(jr.size()>0)
                val=jr.get(0).toString();
            returnMap.put("objList",val);

        }else{
            System.out.println(msg);
        }

        return returnMap;
    }

    /**
     * 得到当前分校的KEY值
     * @param request
     * @return
     */
    public static String getCurrentSchoolKey(ServletContext request){
        //得到文件中的KEY
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
            return null;
        } finally {
            if (br != null)
                try{
                    br.close();
                }catch(Exception e){e.printStackTrace();}
        }
        if(content!=null)
            return content.toString();
        else
            return null;
    }
}
