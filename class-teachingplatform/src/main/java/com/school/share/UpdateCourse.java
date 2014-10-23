package com.school.share;

import com.ckfinder.connector.utils.FileUtils;
import com.school.entity.DictionaryInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.*;
import com.school.entity.teachpaltform.interactive.TpTopicInfo;
import com.school.entity.teachpaltform.interactive.TpTopicThemeInfo;
import com.school.entity.teachpaltform.paper.MicVideoPaperInfo;
import com.school.entity.teachpaltform.paper.PaperInfo;
import com.school.entity.teachpaltform.paper.PaperQuestion;
import com.school.entity.teachpaltform.paper.TpCoursePaper;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.inter.teachpaltform.*;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicManager;
import com.school.manager.inter.teachpaltform.interactive.ITpTopicThemeManager;
import com.school.manager.inter.teachpaltform.paper.IMicVideoPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperManager;
import com.school.manager.inter.teachpaltform.paper.IPaperQuestionManager;
import com.school.manager.inter.teachpaltform.paper.ITpCoursePaperManager;
import com.school.manager.resource.ResourceManager;
import com.school.manager.teachpaltform.*;
import com.school.manager.teachpaltform.interactive.TpTopicManager;
import com.school.manager.teachpaltform.interactive.TpTopicThemeManager;
import com.school.manager.teachpaltform.paper.MicVideoPaperManager;
import com.school.manager.teachpaltform.paper.PaperManager;
import com.school.manager.teachpaltform.paper.PaperQuestionManager;
import com.school.manager.teachpaltform.paper.TpCoursePaperManager;
import com.school.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * Created by zhengzhou on 14-3-10.
 */
/**
 * 分校端同步专题
 */
public class UpdateCourse extends TimerTask{


    private static final String _xmlDataFolder="xmlData";
    private ServletContext request;
    public UpdateCourse(ServletContext application){
        //分校ID
        this.request=application;
    }


    /**
     * 获取更新XML的每次数量
     */
    private static final Integer _getXmlLenPath_Size=150;
    //记录Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    //分校端同步资源
    @Override
    public void run() {
        //如果是这第一次执行，则是启动执行，则直接返回
        if(!SynchroUtil._updateCourse){
            SynchroUtil._updateCourse=true;
            logger.info("--------------------下行专题启动执行，不执行-------------------");
            return;
        }
        logger.info("--------------------下行专题启动执行-------------------");
        Date currentDate=new Date();//记录当前更新的时间点
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }
        String ftime=null;
        //得到上一次更新的时间
        IDictionaryManager dictionaryManager=(DictionaryManager) SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dictionaryManager.getDictionaryByType("UPDATE_COURSE_FTIME");

        DictionaryInfo fupdateDic=null;
        if(dicList!=null&&dicList.size()>0){
            ftime=dicList.get(0).getDictionaryvalue();
            fupdateDic=dicList.get(0);
        }
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_FILE_POST_URL");
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateCourse/";//检测是否上次更新为异常问题
        File folderXmlF=new File(toPathFolder+_xmlDataFolder);
        if(!folderXmlF.exists()||folderXmlF.listFiles().length<1){
                //开始请求并返回更新资源下载地址
                String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";
                Map<String,Object> fileLocaMap= UpdateCourseUtil.getUpdateCourse(key,ftime);
                if(fileLocaMap==null||fileLocaMap.get("objList")==null||(fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success"))){
                    //记录异常错误日志，
                    System.out.println("异常错误，原因：totalSchool生成XML失败!");
                    return;
                }
                updateFileLocaPath=fileLocaMap.get("objList").toString();
                //System.out.println(updateFileLocaPath);
                //下载文件XMLpath到对应目录下
                String fileName=UpdateCourseUtil.getFileName(updateFileLocaPath);
                //得到当前时间
                if(ftime==null&&fileName.indexOf(".")!=-1)
                      currentDate=new Date(Long.parseLong(fileName.substring(0, fileName.lastIndexOf("."))));

                String toPath=toPathFolder+fileName;
                if(!UpdateCourseUtil.downLoadZipFile(updateFileLocaPath,toPath)){
                    //复制文件失败.记录至日志中。
                    System.out.println("copy "+fileName+" error");return;
                }
                //开始解压下载下来的文件
                try {
                   ZipUtil.unzip(toPath,folderXmlF.getPath());//解压完成后，删除压缩包
                    System.gc();
                    new File(toPath).delete();
                } catch (Exception e) {
                    e.printStackTrace();
                    //记录异常，解压失败 并重新开始
                    return;
                }

            //得到字典表的相关数据
            if(fupdateDic==null){
                    fupdateDic=new DictionaryInfo();
                    fupdateDic.setDictionarytype("UPDATE_COURSE_FTIME");
                    fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                    fupdateDic.setDictionaryname("专题上次更新时间!");
                    if(!dictionaryManager.doSave(fupdateDic)){
                        System.out.println("专题记录时间失败!");
                    }
            }else{
                    fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                    if(!dictionaryManager.doUpdate(fupdateDic)){
                        System.out.println("专题记录时间失败!");
                    }
            }
        }
        //得到目录下的文件，进行解析
        File folder=new File(toPathFolder+"/"+_xmlDataFolder+"/");
        //得到下面的所有文件
        File[] folderFiles=folder.listFiles();
        //循环进行解析
        if(folderFiles==null||folderFiles.length<1){
            System.out.println("没有发现可以解析的文件，即没有资源更新，记录到数据库中");return;
        }
        //是否全部执行成功
        boolean istrue=true;
    //专题
        ITpCourseManager tpCourseManager=(TpCourseManager)SpringBeanUtil.getBean("tpCourseManager");
        //论题
        ITpTopicManager topicManager=(TpTopicManager)SpringBeanUtil.getBean("tpTopicManager");
        //论题主题
        ITpTopicThemeManager themeManager=(TpTopicThemeManager)SpringBeanUtil.getBean("tpTopicThemeManager");
        //专题资源的Manager层
        ITpCourseResourceManager coureResourceManager=(TpCourseResourceManager)SpringBeanUtil.getBean("tpCourseResourceManager");
        //资源
        IResourceManager resourceManager=(ResourceManager)SpringBeanUtil.getBean("resourceManager");
        //任务
        ITpTaskManager taskManager=(TpTaskManager)SpringBeanUtil.getBean("tpTaskManager");
        //问题
        IQuestionManager questionManager=(QuestionManager)SpringBeanUtil.getBean("questionManager");
        //问题答案
        IQuestionOptionManager questionOptionManager=(QuestionOptionManager)SpringBeanUtil.getBean("questionOptionManager");
        //专题问题
        ITpCourseQuestionManager courseQuestionManager=(TpCourseQuestionManager)SpringBeanUtil.getBean("tpCourseQuestionManager");
        //专题教材
        ITpCourseTeachingMaterialManager tchMaterialManager=(TpCourseTeachingMaterialManager)SpringBeanUtil.getBean("tpCourseTeachingMaterialManager");
        //专题试卷
        ITpCoursePaperManager coursePaperManager=(TpCoursePaperManager)SpringBeanUtil.getBean("tpCoursePaperManager");
        //试卷
        IPaperManager paperManager=(PaperManager)SpringBeanUtil.getBean("paperManager");
        //试卷与专题
        ITpCoursePaperManager tpCoursePaperManager=(TpCoursePaperManager)SpringBeanUtil.getBean("tpCoursePaperManager");
        //试卷与试题连接
        IPaperQuestionManager paperQuestionManager=(PaperQuestionManager)SpringBeanUtil.getBean("paperQuestionManager");
        //微视频与试卷关系
        IMicVideoPaperManager micVideoPaperManager=(MicVideoPaperManager)SpringBeanUtil.getBean("micVideoPaperManager");
        //视频组关联关系
        IQuesTeamRelaManager quesTeamRelaManager=(QuesTeamRelaManager)SpringBeanUtil.getBean("quesTeamRelaManager");

        //取出相关前100个文件记录，进行循环
        while(true){
            List<String> xmlPathList=new ArrayList<String>();
            folderFiles=folder.listFiles();
            if(folderFiles==null||folderFiles.length<1)break;
            for(int i=0;i<folderFiles.length;i++){
                if(i==_getXmlLenPath_Size){
                    break;
                }
                xmlPathList.add(folderFiles[i].getPath());
            }
        //循环读取文件
        for(int i=0;i<xmlPathList.size();i++){
            File tmp=new File(xmlPathList.get(i));
            if(tmp!=null&&tmp.exists()){
                List<TpCourseInfo> courseList=UpdateCourseUtil.getTpCourseByXml(tmp.getPath());
                if(courseList!=null&&courseList.size()>0){
                    StringBuilder sqlbuilder=null;
                    List<Object> objList=null;

                    List<List<Object>> objArrayList=new ArrayList<List<Object>>();
                    List<String> sqlArrayList=new ArrayList<String>();
                    //继续循环
                    for(TpCourseInfo ctmp:courseList){
                         if(ctmp!=null){
                             //得到专题的SQL
                             sqlbuilder=new StringBuilder();
                             //得到同步的SQL
                              objList=tpCourseManager.getSynchroSql(ctmp,sqlbuilder);
                             if(sqlbuilder!=null){
                                 objArrayList.add(objList);
                                 sqlArrayList.add(sqlbuilder.toString());
                             }
                             //得到附件
                            String topath= UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(ctmp.getCourseid().toString());

                             if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,ctmp.getCourseid().toString(),key,2,topath,null,null)){
                                 //文件失败
                                 System.out.println("专题附件文件下载失败!");//istrue=false;break;
                             }
                             //专题教材
                             List<TpCourseTeachingMaterial> tchMaterialList=UpdateCourseUtil.getTpCourseTchMateriaByXml(tmp.getPath(),ctmp.getCourseid());
                             if(tchMaterialList!=null&&tchMaterialList.size()>0){
                                 for(TpCourseTeachingMaterial tchmaterial:tchMaterialList){
                                     sqlbuilder=new StringBuilder();
                                     objList=tchMaterialManager.getSynchroSql(tchmaterial,sqlbuilder);
                                     if(sqlbuilder!=null){
                                         objArrayList.add(objList);
                                         sqlArrayList.add(sqlbuilder.toString());
                                     }

                                     //每条记录执行执行添加
                                     if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()==50){
                                         istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                         if(!istrue){
                                             System.out.println("更新专题失败!记录日志");
                                             break;
                                         }
                                         sqlArrayList=new ArrayList<String>();
                                         objArrayList=new ArrayList<List<Object>>();
                                     }
                                 }
                             }
                             //每条记录执行执行添加
                             if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                 istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                 if(!istrue){
                                     System.out.println("更新专题失败!记录日志");
                                     break;
                                 }
                                 sqlArrayList=new ArrayList<String>();
                                 objArrayList=new ArrayList<List<Object>>();
                             }

                             //得到论题
                            List<TpTopicInfo> tpicList=UpdateCourseUtil.getTpTopicByXml(tmp.getPath(),ctmp.getCourseid());
                             if(tpicList!=null&&tpicList.size()>0){
                                 for(TpTopicInfo tp:tpicList){
                                     if(tp!=null){
                                         sqlbuilder=new StringBuilder();
                                        //得到论题SQL语句
                                         objList=topicManager.getSynchroSql(tp,sqlbuilder);

                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }
                                         //得到附件
                                         topath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(tp.getTopicid().toString());

                                         if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,tp.getTopicid().toString(),key,2,topath,null,null)){
                                             //文件失败
                                             System.out.println("论题附件文件下载失败!");//istrue=false;break;
                                         }

                                         //论到论题主题
                                         List<TpTopicThemeInfo> themeList=UpdateCourseUtil.getTpTopicThemeByXml(tmp.getPath(),ctmp.getCourseid(),tp.getTopicid());
                                         if(themeList==null||themeList.size()<1)continue;
                                         for(TpTopicThemeInfo theme:themeList){
                                             //得到主题的SQL语句
                                             themeManager.getSynchroSql(theme,sqlArrayList,objArrayList);

                                             //每条记录执行执行添加
                                             if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()==50){
                                                 istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                                 if(!istrue){
                                                     System.out.println("更新专题失败!记录日志");
                                                     break;
                                                 }
                                                 sqlArrayList=new ArrayList<String>();
                                                 objArrayList=new ArrayList<List<Object>>();
                                             }


                                             //得到附件
                                             topath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(theme.getThemeid().toString());

                                             if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,theme.getThemeid().toString(),key,2,topath,null,null)){
                                                 //文件失败
                                                 System.out.println("主题附件文件下载失败!");//istrue=false;break;
                                             }
                                         }
                                         //每条记录执行执行添加
                                         if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                             istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                             if(!istrue){
                                                 System.out.println("更新专题失败!记录日志");
                                                 break;
                                             }
                                             sqlArrayList=new ArrayList<String>();
                                             objArrayList=new ArrayList<List<Object>>();
                                         }
                                     }
                                 }
                             }

//                             //得到专题资源的SQL语句
                             List<TpCourseResource> cRList=UpdateCourseUtil.getTpCourseResourceByXml(tmp.getPath(),ctmp.getCourseid());
                             if(cRList!=null&&cRList.size()>0){
                                for(TpCourseResource cr:cRList){
                                    if(cr==null)continue;
                                    List<ResourceInfo> resList=UpdateCourseUtil.getResourceByXml(tmp.getPath(),ctmp.getCourseid(),cr.getResid(),null);
                                    if(resList!=null&&resList.size()>0){//执行
                                        ResourceInfo rsEntity=resList.get(0);
                                        sqlbuilder=new StringBuilder();
                                        //得到资源的同步语句
                                        objList=resourceManager.getSynchroSql(rsEntity,sqlbuilder);
                                        if(sqlbuilder!=null){
                                            objArrayList.add(objList);
                                            sqlArrayList.add(sqlbuilder.toString());
                                        }

                                        //得到附件
                                        topath= UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_SERVER_PATH")+"/"+UtilTool.getResourceMd5Directory(resList.get(0).getResid().toString());
                                        System.out.println("资源文件"+topath);
                                        if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,resList.get(0).getResid().toString(),key,1,topath,resList.get(0).getFilename(),null)){
                                            //文件失败
                                            System.out.println("资源文件下载失败!");//istrue=false;break;
                                        }
                                        //判断是否是教学资源
//                                        if(rsEntity.getSourceType()!=null){
//                                            switch(rsEntity.getSourceType()){//1 教学参考，2 知识导学，3 学案导学，4 高清学案 ，5 分校上传
//                                                case 1:
//                                                    cr.setResourcetype(2);
//                                                    break;
//                                                case 2:
//                                                case 3:
//                                                case 4:
//                                                    cr.setResourcetype(1);break;
//                                                case 5:
//                                                    cr.setResourcetype(2);break;
//                                            }
//                                        }
                                    }


                                    //得到专题资源的SQL语句
                                    sqlbuilder=new StringBuilder();
                                    objList=coureResourceManager.getSynchroSql(cr,sqlbuilder);
                                    if(sqlbuilder!=null){
                                        objArrayList.add(objList);
                                        sqlArrayList.add(sqlbuilder.toString());
                                    }


                                    //每条记录执行执行添加
                                    if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()==50){
                                        istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                        if(!istrue){
                                            System.out.println("更新专题失败!记录日志");
                                            break;
                                        }
                                        sqlArrayList=new ArrayList<String>();
                                        objArrayList=new ArrayList<List<Object>>();
                                    }
                                    //得到附件
                                }
                             }

                             //每条记录执行执行添加
                             if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                 istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                 if(!istrue){
                                     System.out.println("更新专题失败!记录日志");
                                     break;
                                 }
                                 sqlArrayList=new ArrayList<String>();
                                 objArrayList=new ArrayList<List<Object>>();
                             }


                             //得到问题的SQL语句     试题组      试题答案
                             List<QuestionInfo> quesList=UpdateCourseUtil.getQuestionByXml(tmp.getPath(),ctmp.getCourseid());
                             if(quesList!=null&&quesList.size()>0){
                                 for(QuestionInfo ques:quesList){
                                     if(ques==null)continue;
                                     //得到问题的SQL语句
                                     sqlbuilder=new StringBuilder();
                                     objList=questionManager.getSynchroSql(ques,sqlbuilder);
                                     if(sqlbuilder!=null){
                                         objArrayList.add(objList);
                                         sqlArrayList.add(sqlbuilder.toString());
                                     }
                                     topath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(ques.getQuestionid().toString());
                                     if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,ques.getQuestionid().toString(),key,2,topath,null,ques.getSourceType())){
                                         //文件失败
                                         System.out.println("问题附件文件下载失败!");//istrue=false;break;
                                     }

                                     //得到问题答案的SQL语句
                                     List<QuestionOption> quesOptsList=UpdateCourseUtil.getQuestionOptsByXml(tmp.getPath(),ctmp.getCourseid(),ques.getQuestionid());
                                     if(quesOptsList!=null&&quesOptsList.size()>0){
                                         //先删除,再添加
                                         sqlbuilder=new StringBuilder();
                                         QuestionOption delObj=new QuestionOption();
                                         delObj.setQuestionid(ques.getQuestionid());
                                         objList=questionOptionManager.getDeleteSql(delObj,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }
                                         for(QuestionOption option:quesOptsList){
                                             //得到问题答案的SQL语句
                                             sqlbuilder=new StringBuilder();
                                             objList=questionOptionManager.getSynchroSql(option,sqlbuilder);
                                             if(sqlbuilder!=null){
                                                 objArrayList.add(objList);
                                                 sqlArrayList.add(sqlbuilder.toString());
                                             }

                                             //每条记录执行执行添加
                                             if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()==50){
                                                 istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                                 if(!istrue){
                                                     System.out.println("更新专题失败!记录日志");
                                                     break;
                                                 }
                                                 sqlArrayList=new ArrayList<String>();
                                                 objArrayList=new ArrayList<List<Object>>();
                                             }

//                                             topath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(option.getRef().toString());
//                                             if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,option.getRef().toString(),key,2,topath,null,null)){
//                                                 //文件失败
//                                                 System.out.println("问题选项附件文件下载失败!");//istrue=false;break;
//                                             }
                                         }
                                     }
                                     //每条记录执行执行添加
                                     if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                         istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                         if(!istrue){
                                             System.out.println("更新专题失败!记录日志");
                                             break;
                                         }
                                         sqlArrayList=new ArrayList<String>();
                                         objArrayList=new ArrayList<List<Object>>();
                                     }
                                     //得到问题下的试题组
                                     List<QuesTeamRela> qtrList=UpdateCourseUtil.getEttQuesTeamRelaByXml(tmp.getPath(),ctmp.getCourseid(),ques.getQuestionid());
                                     if(qtrList!=null&&qtrList.size()>0){
                                         //先删除再添加
                                         sqlbuilder=new StringBuilder();
                                         QuesTeamRela delTmp=new QuesTeamRela();
                                         delTmp.setTeamid(ques.getQuestionid());
                                         objList =quesTeamRelaManager.getDeleteSql(delTmp,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }
                                         //循环添加
                                         for(QuesTeamRela qtTmp:qtrList){
                                             if(qtTmp!=null){
                                                 sqlbuilder=new StringBuilder();
                                                 objList=quesTeamRelaManager.getSaveSql(qtTmp,sqlbuilder);
                                                 if(sqlbuilder!=null){
                                                     objArrayList.add(objList);
                                                     sqlArrayList.add(sqlbuilder.toString());
                                                 }
                                             }
                                         }
                                     }
                                 }
                             }
                             //每条记录执行执行添加
                             if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                 istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                 if(!istrue){
                                     System.out.println("更新专题失败!记录日志");
                                     break;
                                 }
                                 sqlArrayList=new ArrayList<String>();
                                 objArrayList=new ArrayList<List<Object>>();
                             }
                             //得到问题的SQL语句
                             List<Map<String,Object>> quesTypeList=UpdateCourseUtil.getQuesPaperTypeByXml(tmp.getPath(),ctmp.getCourseid());
                            if(quesTypeList!=null&&quesTypeList.size()>0){
                                for(Map<String,Object> soMap:quesTypeList){
                                    if(soMap==null)continue;

                                    TpCoursePaper coursePaper=new TpCoursePaper();
                                    String papertype=soMap.get("Papertype").toString();
                                    String questionid=soMap.get("Questionid").toString();
                                    String sort=soMap.containsKey("Sort")?soMap.get("Sort").toString():null;
                                    if(papertype.trim().toLowerCase().equals("a"))
                                        coursePaper.setPapertype(1);
                                    else if(papertype.trim().toLowerCase().equals("b"))
                                        coursePaper.setPapertype(2);
                                    coursePaper.setCourseid(ctmp.getCourseid());
                                    PageResult presult=new PageResult();
                                    List<TpCoursePaper> cpList=coursePaperManager.getABSynchroList(coursePaper, presult);
                                    //得到ID
                                     Long paperid=coursePaperManager.getNextId(false);
                                    //如果不存在，则添加试卷信息
                                    if(cpList==null||cpList.size()<1){
                                        PaperInfo p=new PaperInfo();
                                        p.setPaperid(paperid);
                                        p.setPapername(ctmp.getCoursename()+"标准测试"+papertype );
                                        p.setPapertype(coursePaper.getPapertype());
                                        p.setScore(100F);
                                        p.setCuserid(0);
                                        sqlbuilder=new StringBuilder();
                                        objList=paperManager.getSaveSql(p, sqlbuilder);
                                        if(sqlbuilder!=null){
                                            objArrayList.add(objList);
                                            sqlArrayList.add(sqlbuilder.toString());
                                        }
                                        //向TPCoursePaper表添加数据
                                        TpCoursePaper cp=new TpCoursePaper();
                                        cp.setCourseid(ctmp.getCourseid());
                                        cp.setPaperid(paperid);
                                        cp.setScore(10F);
                                        sqlbuilder=new StringBuilder();
                                        objList=tpCoursePaperManager.getSaveSql(cp, sqlbuilder);
                                        if(sqlbuilder!=null){
                                            objArrayList.add(objList);
                                            sqlArrayList.add(sqlbuilder.toString());
                                        }
                                        //执行
                                        //每条记录执行执行添加
                                        if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                            istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                            if(!istrue){
                                                System.out.println("更新试卷失败!记录日志");
                                                break;
                                            }
                                            sqlArrayList=new ArrayList<String>();
                                            objArrayList=new ArrayList<List<Object>>();
                                        }

                                    }else
                                        paperid=cpList.get(0).getPaperid();

                                    //向PaperQuestion表中添加数据
                                    PaperQuestion pq=new PaperQuestion();
                                    pq.setPaperid(paperid);
                                    pq.setQuestionid(Long.parseLong(questionid.toString()));
                                    pq.setOrderidx(sort==null?null:Integer.parseInt(sort.trim()));
                                    pq.setScore(10F);
                                    sqlbuilder=new StringBuilder();
                                    objList=paperQuestionManager.getSynchroSql(pq,sqlbuilder);
                                    if(sqlbuilder!=null){
                                        objArrayList.add(objList);
                                        sqlArrayList.add(sqlbuilder.toString());
                                    }
                                }
                            }

                             //得到专题问题的SQL语句
                             List<TpCourseQuestion> cqlist=UpdateCourseUtil.getCourseQuesByXml(tmp.getPath(),ctmp.getCourseid());
                             if(cqlist!=null&&cqlist.size()>0){
                                 for(TpCourseQuestion cq:cqlist){
                                     if(cq==null)continue;
                                     sqlbuilder=new StringBuilder();
                                     QuestionInfo q=new QuestionInfo();
                                     q.setQuestionid(cq.getQuestionid());
                                     List<QuestionInfo> qList=questionManager.getList(q,null);
                                     if(qList!=null&&qList.size()>0){
                                         //如果是组试题内的题，则不添加
                                         if(qList.get(0).getQuestiontype()!=1&&qList.get(0).getQuestiontype()!=2
                                                 &&qList.get(0).getQuestiontype()!=6
                                                 &&qList.get(0).getQuestiontype()!=3
                                                 &&qList.get(0).getQuestiontype()!=4){
                                             continue;
                                         }
                                     }
                                     //得到专题问题的同步SQL
                                       objList=courseQuestionManager.getSynchroSql(cq,sqlbuilder);
                                     if(sqlbuilder!=null){
                                         objArrayList.add(objList);
                                         sqlArrayList.add(sqlbuilder.toString());
                                     }

                                     //每条记录执行执行添加
                                     if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()==50){
                                         istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                         if(!istrue){
                                             System.out.println("更新专题失败!记录日志");
                                             break;
                                         }
                                         sqlArrayList=new ArrayList<String>();
                                         objArrayList=new ArrayList<List<Object>>();
                                     }

                                 }
                                 //每条记录执行执行添加
                                 if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                     istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                     if(!istrue){
                                         System.out.println("更新专题失败!记录日志");
                                         break;
                                     }
                                     sqlArrayList=new ArrayList<String>();
                                     objArrayList=new ArrayList<List<Object>>();
                                 }
                             }


                             //
                             List<TpTaskInfo> tkList=UpdateCourseUtil.getTpTaskByXml(tmp.getPath(),ctmp.getCourseid());
                             if(tkList!=null&&tkList.size()>0){
                                 for(TpTaskInfo tk:tkList){
                                     if(tk!=null){
                                         //得到同步任务的同步SQL
                                         sqlbuilder=new StringBuilder();
                                         objList=taskManager.getSynchroSql(tk,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }

//                                        topath= UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(tk.getTaskid().toString());
//                                        if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,tk.getTaskid().toString(),key,2,topath,null,null)){
//                                            //文件失败
//                                            System.out.println("任务附件文件下载失败!");//istrue=false;break;
//                                        }
                                         //每条记录执行执行添加
                                         if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()==150){
                                             istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                             if(!istrue){
                                                 System.out.println("更新专题失败!记录日志");
                                                 break;
                                             }
                                             sqlArrayList=new ArrayList<String>();
                                             objArrayList=new ArrayList<List<Object>>();
                                         }
                                     }
                                 }
                                 //每条记录执行执行添加
                                 if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0){
                                     istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                     if(!istrue){
                                         System.out.println("更新专题失败!记录日志");
                                         break;
                                     }
                                     sqlArrayList=new ArrayList<String>();
                                     objArrayList=new ArrayList<List<Object>>();
                                 }
                             }

                             ///////////////////////////////////微视频表///////////////////////////
                             //微视频资源添加
                             List<ResourceInfo> resList=UpdateCourseUtil.getResourceByXml(tmp.getPath(),ctmp.getCourseid(),null,1);
                             if(resList!=null&&resList.size()>0){//执行
                                 for(ResourceInfo rsEntity:resList){
                                     sqlbuilder=new StringBuilder();
                                     //得到资源的同步语句
                                     objList=resourceManager.getSynchroSql(rsEntity,sqlbuilder);
                                     if(sqlbuilder!=null){
                                         objArrayList.add(objList);
                                         sqlArrayList.add(sqlbuilder.toString());
                                     }
                                     //得到附件
                                    topath= UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_SERVER_PATH")+"/"+UtilTool.getResourceMd5Directory(rsEntity.getResid().toString());
                                     System.out.println("微视频资源文件"+topath);
                                     //下载微视频文件
                                     if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,rsEntity.getResid().toString(),key,-1,topath,rsEntity.getFilename(),null)){
                                         //文件失败
                                         System.out.println("资源文件下载失败!");
                                     }
                                     /////////////////////////////微视频与专题的关联/////////////////////////
                                     TpCourseResource tcr=new TpCourseResource();
                                     tcr.setResid(rsEntity.getResid());
                                     tcr.setCourseid(ctmp.getCourseid());
                                     List<TpCourseResource> tcrList=coureResourceManager.getList(tcr,null);
                                     if(tcrList==null||tcrList.size()<1){
                                         tcr.setResourcetype(1);
                                         //添加
                                         sqlbuilder=new StringBuilder();
                                         objList=coureResourceManager.getSynchroSql(tcr,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }
                                     }



                                     //每条记录执行执行添加
                                     if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()&&sqlArrayList.size()>0&&sqlArrayList.size()%100==0){
                                         istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                         if(!istrue){
                                             System.out.println("更新专题失败!记录日志");
                                             break;
                                         }
                                         sqlArrayList=new ArrayList<String>();
                                         objArrayList=new ArrayList<List<Object>>();
                                     }


                                     //查询是否有与试卷的对应关系
                                     MicVideoPaperInfo mvp=new MicVideoPaperInfo();
                                     mvp.setMicvideoid(rsEntity.getResid());
                                     PageResult pSearch=new PageResult();
                                     pSearch.setPageSize(1);
                                     List<MicVideoPaperInfo> mvpList=micVideoPaperManager.getList(mvp,pSearch);
                                     Long newPaperid=null;
                                     if(mvpList==null||mvpList.size()<1){
                                         //创建试卷
                                         newPaperid=paperManager.getNextId(false);
                                         PaperInfo tmpPaper=new PaperInfo();
                                         tmpPaper.setPaperid(newPaperid);
                                         tmpPaper.setScore(100F);
                                         tmpPaper.setPapertype(5);//微视频试卷
                                         tmpPaper.setCuserid(0);
                                         tmpPaper.setPapername(ctmp.getCoursename()+"微课程测试"+newPaperid);
                                         sqlbuilder=new StringBuilder();
                                         objList=paperManager.getSaveSql(tmpPaper,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }
                                         /////////////////////////////////////微视频与试卷关系表///////////////////////////
                                         mvp.setPaperid(newPaperid);
                                         sqlbuilder=new StringBuilder();
                                         objList=micVideoPaperManager.getSaveSql(mvp,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }


                                         //每条记录执行执行添加
                                         if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
                                             istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                             if(!istrue){
                                                 System.out.println("更新专题失败!记录日志");
                                                 break;
                                             }
                                             sqlArrayList=new ArrayList<String>();
                                             objArrayList=new ArrayList<List<Object>>();
                                         }


                                     }else
                                         newPaperid=mvpList.get(0).getPaperid();


                                     ///////////////////////////////////微视频对应的试卷///////////////////////////
                                     List<QuestionInfo> littleViewQuesList=UpdateCourseUtil.getLittleViewQuesByXml(tmp.getPath(),ctmp.getCourseid(),rsEntity.getResid());
                                     if(littleViewQuesList==null||littleViewQuesList.size()<1)continue;

                                     for(int iv=0;iv<littleViewQuesList.size();iv++){
                                         QuestionInfo q=littleViewQuesList.get(iv);
                                         sqlbuilder=new StringBuilder();
                                         //得到试题的同步语句
                                         objList=questionManager.getSynchroSql(q,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }
                                         //添加试卷与试题的关系 数据

                                         //判断该试卷下是否存在该试题
                                         PaperQuestion pq=new PaperQuestion();
                                         pq.setPaperid(newPaperid);
                                         pq.setQuestionid(q.getQuestionid());
                                         List<PaperQuestion> pqList=paperQuestionManager.getList(pq,pSearch);
                                         if(pqList==null|| pqList.size()<1){
                                             //没有则添加,有则过。
                                             pq.setOrderidx(iv+1);
                                             //添加
                                             sqlbuilder=new StringBuilder();
                                             //得到资源的同步语句
                                             objList=paperQuestionManager.getSaveSql(pq,sqlbuilder);
                                             if(sqlbuilder!=null){
                                                 objArrayList.add(objList);
                                                 sqlArrayList.add(sqlbuilder.toString());
                                             }
                                         }else{
                                             //修改序号
                                             pq=pqList.get(0);
                                             pq.setOrderidx(iv+1);
                                             sqlbuilder=new StringBuilder();
                                             //得到资源的同步语句
                                             objList=paperQuestionManager.getUpdateSql(pq, sqlbuilder);
                                             if(sqlbuilder!=null){
                                                 objArrayList.add(objList);
                                                 sqlArrayList.add(sqlbuilder.toString());
                                             }
                                         }
                                         ///////////////////////////////////微视频问题选项///////////////////////////
                                         //先删除,再添加
                                         sqlbuilder=new StringBuilder();
                                         QuestionOption delObj=new QuestionOption();
                                         delObj.setQuestionid(q.getQuestionid());
                                         objList=questionOptionManager.getDeleteSql(delObj,sqlbuilder);
                                         if(sqlbuilder!=null){
                                             objArrayList.add(objList);
                                             sqlArrayList.add(sqlbuilder.toString());
                                         }

                                         List<QuestionOption> quesOptsList=UpdateCourseUtil.getQuestionOptsByXml(tmp.getPath(),ctmp.getCourseid(),q.getQuestionid());
                                         if(quesOptsList!=null&&quesOptsList.size()>0){
                                             for(QuestionOption option:quesOptsList){
                                                 //得到问题答案的SQL语句
                                                 sqlbuilder=new StringBuilder();
                                                 objList=questionOptionManager.getSynchroSql(option,sqlbuilder);
                                                 if(sqlbuilder!=null){
                                                     objArrayList.add(objList);
                                                     sqlArrayList.add(sqlbuilder.toString());
                                                 }
    //                                             topath=UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+MD5_NEW.getMD5Result(option.getRef().toString());
    //                                             if(!UpdateCourseUtil.copyResourceToPath(postFileUrl,option.getRef().toString(),key,2,topath,null,null)){
    //                                                 //文件失败
    //                                                 System.out.println("问题选项附件文件下载失败!");//istrue=false;break;
    //                                             }
                                             }
                                         }
                                         //每条记录执行执行添加
                                         if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
                                             istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                             if(!istrue){
                                                 System.out.println("更新专题失败!记录日志");
                                                 break;
                                             }
                                             sqlArrayList=new ArrayList<String>();
                                             objArrayList=new ArrayList<List<Object>>();
                                         }
                                     }
                                 }
//                                 //每条记录执行执行添加
                                 if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()==objArrayList.size()){
                                     istrue=tpCourseManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                                     if(!istrue){
                                         System.out.println("更新专题失败!记录日志");
                                         break;
                                     }
                                     sqlArrayList=new ArrayList<String>();
                                     objArrayList=new ArrayList<List<Object>>();
                                 }

                             }
                         }
                    }

                   if(!istrue)break;
                }

                //更新完成，删除当前文件
                System.gc();
                tmp.delete();
            }
        }
        if(!istrue)break;
    }
        if(istrue){
            System.out.println("更新专题成功!");
        }else{
            System.out.println("更新专题失败!记录日志");
        }

    }
}

/**
 * 更新专题接口工具类
 */
class UpdateCourseUtil{
    private static List _list=null;
    private static String _path=null;
    /**
     * 从服务器得到相关附件，资源地址，然后下载到指定目录下
     * @param locapath
     * @param id
     * @param key
     * @param type
     * @param topath
     * @return
     */
    public static boolean copyResourceToPath(String locapath,String id,String key,Integer type,String topath,String fname,Integer sourceType){
        if(locapath==null||id==null||key==null||topath==null)return false;
        if(type==null)type=2;  //1：资源文件   2：附件   3:微视频文件

        //查看topath是否存在
        File topathF=new File(topath+"/");
        if(topathF.exists()){
            System.out.println(topath+"存在文件目录，不用下载");
            return true; //存在文件，不用下载
        }
        //开始组织链接
        String[] urlArray=locapath.split("\\?");
        StringBuilder postURL=new StringBuilder(urlArray[0]);
        StringBuilder params=new StringBuilder(urlArray[1]).append("&id=").append(id);
        params.append("&key=").append(key);
        params.append("&type=").append(type);
        if(type==2&&sourceType!=null)
            params.append("&sourcetype=").append(sourceType);
        System.out.println(postURL+"   "+params.toString());
        Map<String,Object> map=sendPostURL(postURL.toString(),params.toString());
        if(map==null||map.get("type")==null||!map.get("type").toString().trim().equals("success")
                ||!map.containsKey("objList")||map.get("objList")==null){
            //请求失败!
            System.out.println("请求得到文件失败!原因：未知!");
            return false;
        }
        //得到链接地址
        String fileUrl=map.get("objList").toString();
        if(fileUrl==null||fileUrl.trim().length()<1)//文件包不存在
            return false;

        //复制文件
        //得到文件后缀名
        String lastName="";
        String fileName="001";
        //默认为001.后缀
        String[] filepatharray={fileUrl};
        if(fileUrl.indexOf("|")!=-1){
            String[] fileUrlArray=fileUrl.split("\\|");
            filepatharray=fileUrlArray;
        }
        for(String tfirurl:filepatharray){
            if(tfirurl==null||tfirurl.trim().length()<1)
                break;
            String tmp=tfirurl.substring(tfirurl.lastIndexOf("/"));
            if(tmp.indexOf(".")!=-1)
                lastName=tmp.substring(tmp.lastIndexOf("."));
            String downPath=topath+"/001"+lastName;
            String unzipPath=topath+"/";
            if(lastName.trim().length()<1){
                lastName=".zip";
                fileName=topath.substring(topath.lastIndexOf("/")+1);
                downPath =topath+"/../"+fileName+lastName;
                unzipPath+="/";
            }
            if(!topathF.exists()){
              //没有目录则创建
                topathF.mkdirs();
            }
            if(downLoadZipFile(tfirurl,downPath)){
                //异常错误，原因：无法下载+fileUrl的文件
            //    System.out.println("异常错误，原因：无法下载"+fileUrl+"的文件");
                //文件名称
                fileName+=lastName;
                //更名
              //  new File(topath+"/001").renameTo(new File(topath+"/",fileName));
                //如果是ZIP就解压
                if(lastName!=null&&lastName.trim().length()>0&&lastName.trim().toUpperCase().equals(".ZIP")){
                    try {
                        ZipUtil.unzip(downPath,unzipPath);
                        //改名
                        if(fname!=null){
                            new File(topath+"/"+fname).renameTo(new File(topath+"/"+"001"+fname.substring(fname.lastIndexOf("."))));
                        }
                        //删除压缩包
                        System.gc();
                        FileUtils.delete(new File(downPath));
                    } catch (Exception e) {
                        e.printStackTrace();
                        return false;
                    }
                    //解压后，将文件复制出来

                }
            }
        }
        return true;
    }


    /**
     * 得到资源信息
     * @param xmlFullName
     * @param schoolCourseId
     * @return
     */
    public static List<ResourceInfo> getResourceByXml(String xmlFullName,
                                                           Long schoolCourseId,Long resid,Integer difftype) {
        // TODO Auto-generated method stub
        if(xmlFullName==null||schoolCourseId==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<ResourceInfo> returnList=new ArrayList<ResourceInfo>();
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                    &&courseMap.get("CourseId").toString().trim().equals(schoolCourseId.toString())){
                if(courseMap.containsKey("DcResResourceInfoList")){
                    List<Map<String,Object>> crMapList=(List<Map<String,Object>>)courseMap.get("DcResResourceInfoList");
                    if(crMapList!=null&&crMapList.size()>0){
                        for (Map<String, Object> map : crMapList) {
                            if(map!=null&&map.get("Resid")!=null
                                    &&!map.get("Resid").toString().trim().toUpperCase().equals("NULL")){
                                if(resid!=null&&!map.get("Resid").toString().trim().equals(resid.toString()))continue;
                                ResourceInfo res=new ResourceInfo();
                                if(map.containsKey("Difftype")&&map.get("Difftype")!=null&&!map.get("Difftype").toString().trim().toUpperCase().equals("NULL")){
                                    res.setDifftype(Integer.parseInt(map.get("Difftype").toString()));
                                }
                                if(difftype!=null&&(res.getDifftype()==null||res.getDifftype().intValue()!=difftype.intValue()))
                                    continue;
                                if(map.containsKey("Appobj")&&map.get("Appobj")!=null&&!map.get("Appobj").toString().trim().toUpperCase().equals("NULL")){
                                    res.setUseobject(map.get("Appobj").toString());
                                }
                                res.setCourseid(schoolCourseId);
                                if(map.containsKey("Authorid")&&map.get("Authorid")!=null&&!map.get("Authorid").toString().trim().toUpperCase().equals("NULL")){
                                    res.setUserid(Integer.parseInt(map.get("Authorid").toString()));
                                }
                                if(map.containsKey("Authorname")&&map.get("Authorname")!=null&&!map.get("Authorname").toString().trim().toUpperCase().equals("NULL")){
                                    res.setRealname(map.get("Authorname").toString().replaceAll("\\\\", "\\\\\\\\"));
                                }
                                if(map.containsKey("Resid")&&map.get("Resid")!=null&&!map.get("Resid").toString().trim().toUpperCase().equals("NULL")){
                                    res.setResid(Long.parseLong(map.get("Resid").toString()));
                                }
                                if(map.containsKey("Resname")&&map.get("Resname")!=null&&!map.get("Resname").toString().trim().toUpperCase().equals("NULL")){
                                    res.setResname(map.get("Resname").toString().replaceAll("\\\\", "\\\\\\\\"));
                                }
                                if(map.containsKey("Resremark")&&map.get("Resremark")!=null&&!map.get("Resremark").toString().trim().toUpperCase().equals("NULL")){
                                    res.setResintroduce(map.get("Resremark").toString().replaceAll("\\\\", "\\\\\\\\"));
                                }
                                //修改 微视频学习资源，从学生版同步到资源系统中，其资源类型标签为课件。( http://192.168.10.8:8080/browse/PZYXT-171)
                                if(res.getDifftype()!=null&&res.getDifftype().intValue()==1){
                                    res.setRestype(3);  //3:课件  资源类型
                                }else if(map.containsKey("Restype")&&map.get("Restype")!=null&&!map.get("Restype").toString().trim().toUpperCase().equals("NULL")){
                                    res.setRestype(Integer.parseInt(map.get("Restype").toString()));
                                }

                                if(map.containsKey("Filesize")&&map.get("Filesize")!=null&&!map.get("Filesize").toString().trim().toUpperCase().equals("NULL")){
                                    res.setFilesize(Long.parseLong(map.get("Filesize").toString()));
                                }
                                if(map.containsKey("Filesuffix")&&map.get("Filesuffix")!=null&&!map.get("Filesuffix").toString().trim().toUpperCase().equals("NULL")){
                                    res.setFilesuffixname(map.get("Filesuffix").toString());
                                }
                                if(map.containsKey("CtimeString")&&map.get("CtimeString")!=null&&!map.get("CtimeString").toString().trim().toUpperCase().equals("NULL")){
                                    res.setCtime(UtilTool.StringConvertToDate(map.get("CtimeString").toString()));
                                }
                                if(map.containsKey("Oldgradeid")&&map.get("Oldgradeid")!=null&&!map.get("Oldgradeid").toString().trim().toUpperCase().equals("NULL")){
                                    res.setGrade(Integer.parseInt(map.get("Oldgradeid").toString()));
                                }
                                if(map.containsKey("Oldsubjectid")&&map.get("Oldsubjectid")!=null&&!map.get("Oldsubjectid").toString().trim().toUpperCase().equals("NULL")){
                                    res.setSubject(Integer.parseInt(map.get("Oldsubjectid").toString()));
                                }
                                if(map.containsKey("Filename")&&map.get("Filename")!=null&&!map.get("Filename").toString().trim().toUpperCase().equals("NULL")){
                                    res.setFilename(map.get("Filename").toString());
                                }
                                if(map.containsKey("Schoolname")&&map.get("Schoolname")!=null&&!map.get("Schoolname").toString().trim().toUpperCase().equals("NULL")){
                                    res.setSchoolname(map.get("Schoolname").toString().replaceAll("\\\\", "\\\\\\\\"));
                                }
                                if(map.containsKey("SourceType")&&map.get("SourceType")!=null&&!map.get("SourceType").toString().trim().toUpperCase().equals("NULL")){
                                    res.setSourceType(Integer.parseInt(map.get("SourceType").toString()));
                                }
                                if(map.containsKey("Filetype")&&map.get("Filetype")!=null&&!map.get("Filetype").toString().trim().toUpperCase().equals("NULL")){
                                    res.setFiletype(Integer.parseInt(map.get("Filetype").toString()));
                                }
                                if(map.containsKey("Type")&&map.get("Type")!=null&&!map.get("Type").toString().trim().toUpperCase().equals("NULL")){
                                    //类型( -1:删除 -2:恶意   1:待审核   2:共享3:标准  )
                                    //资源状态：0：待审核 1:已通过 2:未通过 3：已删除
                                    //网校分享类型(-1：未同步  0:待审核 1:未通过 2:已删除 3:共享 4:标准)
                                    //RES_DEGREE  资源等级（1:标准 2:共享 3:本地）
                                    Integer type=Integer.parseInt(map.get("Type").toString());
                                    switch(type){
                                        case -1:
                                            type=3;
                                            res.setNetsharestatus(2);
                                            break;
                                        case -2:
                                            type=2;
                                            res.setNetsharestatus(2);
                                            break;
                                        case 1:
                                            type=0;
                                            res.setNetsharestatus(0);
                                            break;
                                        case 2:
                                            type=1;
                                            res.setNetsharestatus(3);
                                            break;
                                        case 3:
                                            type=1;
                                            res.setNetsharestatus(4);
                                            break;
                                    }
                                    res.setResstatus(1);
                                }
                                res.setResdegree(1);//默认根据专题过来的资源为标准共享
                                returnList.add(res);
                            }
                        }
                    }
                }
            }
        }

        return returnList;
    }
    /**
     * 得到的专题资源包
     * @param xmlFullName
     * @param courseid
     * @return
     */
    public static List<TpCourseResource> getTpCourseResourceByXml(String xmlFullName,Long courseid){
        if(xmlFullName==null||courseid==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<TpCourseResource> returnList=new ArrayList<TpCourseResource>();
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                    &&courseMap.get("CourseId").toString().trim().equals(courseid.toString())){
                if(courseMap.containsKey("TpCourseResourceList")){
                    List<Map<String,Object>> crMapList=(List<Map<String,Object>>)courseMap.get("TpCourseResourceList");
                    if(crMapList!=null&&crMapList.size()>0){
                        for (Map<String, Object> crtmp : crMapList) {
                            if(crtmp==null)continue;
                            TpCourseResource tr=new TpCourseResource();
                            if(crtmp.containsKey("ResId")&&crtmp.get("ResId")!=null&&!crtmp.get("ResId").toString().trim().toUpperCase().equals("NULL")){
                                tr.setResid(Long.parseLong(crtmp.get("ResId").toString()));
                            }
                            if(crtmp.containsKey("Ref")&&crtmp.get("Ref")!=null&&!crtmp.get("Ref").toString().trim().toUpperCase().equals("NULL")){
                                tr.setRef(Long.parseLong(crtmp.get("Ref").toString()));
                            }
                            if(crtmp.containsKey("CtimeString")&&crtmp.get("CtimeString")!=null&&!crtmp.get("CtimeString").toString().trim().toUpperCase().equals("NULL")){
                                tr.setCtime(UtilTool.StringConvertToDate(crtmp.get("CtimeString").toString()));
                            }
                            if(crtmp.containsKey("RefeRenceType")&&crtmp.get("RefeRenceType")!=null&&!crtmp.get("RefeRenceType").toString().trim().toUpperCase().equals("NULL")){
                                tr.setResourcetype(Integer.parseInt(crtmp.get("RefeRenceType").toString()));
                            }
                            if(crtmp.containsKey("Enable")&&crtmp.get("Enable")!=null&&!crtmp.get("Enable").toString().trim().toUpperCase().equals("NULL")){
                                tr.setTaskflag(Integer.parseInt(crtmp.get("Enable").toString()));
                            }
                            if(crtmp.containsKey("ResourceType")&&crtmp.get("ResourceType")!=null&&!crtmp.get("ResourceType").toString().trim().toUpperCase().equals("NULL")){
                                tr.setResourcetype(Integer.parseInt(crtmp.get("ResourceType").toString()));
                            }
                            //tr.setEnable(enable)

                            tr.setCourseid(courseid);
                            returnList.add(tr);

                        }
                    }
                }
            }
        }
        return returnList;
    }
    /**
     * 得到的专题资源包
     * @param xmlFullName
     * @param courseid
     * @return
     */
    public static List<TpCourseTeachingMaterial> getTpCourseTchMateriaByXml(String xmlFullName,Long courseid){
        if(xmlFullName==null||courseid==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<TpCourseTeachingMaterial> returnList=new ArrayList<TpCourseTeachingMaterial>();

        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                    &&courseMap.get("CourseId").toString().trim().equals(courseid.toString())){
                if(courseMap.containsKey("TpCourseTchMateriaList")){
                    List<Map<String,Object>> crMapList=(List<Map<String,Object>>)courseMap.get("TpCourseTchMateriaList");
                    if(crMapList!=null&&crMapList.size()>0){
                        for (Map<String, Object> crtmp : crMapList) {
                            if(crtmp==null)continue;

                            TpCourseTeachingMaterial tr=new TpCourseTeachingMaterial();
                            if(crtmp.containsKey("Ref")&&crtmp.get("Ref")!=null&&!crtmp.get("Ref").toString().trim().toUpperCase().equals("NULL")){
                                tr.setRef(Long.parseLong(crtmp.get("Ref").toString()));
                            }
                            if(crtmp.containsKey("Ctime")&&crtmp.get("Ctime")!=null&&!crtmp.get("Ctime").toString().trim().toUpperCase().equals("NULL")){
                                tr.setCtime(UtilTool.StringConvertToDate(crtmp.get("Ctime").toString(), UtilTool.DateType.GMT));
                            }
                            if(crtmp.containsKey("TchMaterialId")&&crtmp.get("TchMaterialId")!=null&&!crtmp.get("TchMaterialId").toString().trim().toUpperCase().equals("NULL")){
                                tr.setTeachingmaterialid(Integer.parseInt(crtmp.get("TchMaterialId").toString()));
                            }
                            tr.setCourseid(courseid);
                            returnList.add(tr);
                        }
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 从XML中得到TaskInfo信息
     * @param xmlFullName
     * @param courseid
     * @return
     */
    public static List<TpTaskInfo> getTpTaskByXml(String xmlFullName,Long courseid){
        if(xmlFullName==null||courseid==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<TpTaskInfo> returnList=new ArrayList<TpTaskInfo>();
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                    &&courseMap.get("CourseId").toString().trim().equals(courseid.toString())){
                if(courseMap.containsKey("TpTaskInfoList")&&courseMap.get("TpTaskInfoList")!=null){
                    List<Map<String,Object>> tkMapList=(List<Map<String,Object>>)courseMap.get("TpTaskInfoList");
                    if(tkMapList==null||tkMapList.size()<1)continue;
                    for (Map<String, Object> tkmp : tkMapList) {
                        if(tkmp==null)continue;
                        TpTaskInfo tk=new TpTaskInfo();
                        tk.setCourseid(courseid);
                        if(tkmp.containsKey("TaskId")&&tkmp.get("TaskId")!=null&&!tkmp.get("TaskId").toString().trim().toUpperCase().equals("NULL")){
                            tk.setTaskid(Long.parseLong(tkmp.get("TaskId").toString()));
                        }
                        if(tkmp.containsKey("TaskType")&&tkmp.get("TaskType")!=null&&!tkmp.get("TaskType").toString().trim().toUpperCase().equals("NULL")){
                            tk.setTasktype(Integer.parseInt(tkmp.get("TaskType").toString()));
                        }
                        if(tkmp.containsKey("TaskValueId")&&tkmp.get("TaskValueId")!=null&&!tkmp.get("TaskValueId").toString().trim().toUpperCase().equals("NULL")){
                            tk.setTaskvalueid(Long.parseLong(tkmp.get("TaskValueId").toString()));
                        }
                        if(tkmp.containsKey("TaskName")&&tkmp.get("TaskName")!=null&&!tkmp.get("TaskName").toString().trim().toUpperCase().equals("NULL")){
                            tk.setTaskname(tkmp.get("TaskName").toString());
                        }
                        if(tkmp.containsKey("TaskRemark")&&tkmp.get("TaskRemark")!=null&&!tkmp.get("TaskRemark").toString().trim().toUpperCase().equals("NULL")){
                            tk.setTaskremark(tkmp.get("TaskRemark").toString().replaceAll("\\\\","\\\\\\\\"));
                        }
                        if(tkmp.containsKey("CtimeString")&&tkmp.get("CtimeString")!=null&&!tkmp.get("CtimeString").toString().trim().toUpperCase().equals("NULL")){
                            tk.setCtime(UtilTool.StringConvertToDate(tkmp.get("CtimeString").toString()));
                        }
                        if(tkmp.containsKey("CriteriaType")&&tkmp.get("CriteriaType")!=null&&!tkmp.get("CriteriaType").toString().trim().toUpperCase().equals("NULL")){
                            tk.setCriteria(Integer.parseInt(tkmp.get("CriteriaType").toString()));
                        }
                        // 类型（-1：已删除   -2：恶意   0：待审核   1：通过）
                        //云端分享状态 -2:恶意 -1：未上传 0：待审核 2：已删除 3:通过
                        if(tkmp.containsKey("TaskStatus")&&tkmp.get("TaskStatus")!=null&&!tkmp.get("TaskStatus").toString().trim().toUpperCase().equals("NULL")){
                            Integer status=Integer.parseInt(tkmp.get("TaskStatus").toString().trim());
                            switch(status){
                                case -1:
                                    status=2;
                                    break;
                                case -2:
                                    status=-2;
                                    break;
                                case 0:
                                    status=0;
                                    break;
                                case 1:
                                    status=3;
                                    break;
                            }
                            tk.setCloudstatus(status);
                        }
                        //是否引用别的任务
//						if(tkmp.containsKey("Criteria")&&tkmp.get("Criteria")!=null&&!tkmp.get("Criteria").toString().trim().toUpperCase().equals("NULL")){
//							tk.setCriteriaType(Integer.parseInt(tkmp.get("Criteria").toString()));
//						}

//                        tk.setSchoolId(schoolid);
//                        tk.setSchoolName(schoolname);
                        returnList.add(tk);
                    }
                }

            }
        }
        return returnList;
    }



    /**
     * 得到微视频对应的试题
     * @param xmlFullName
     * @param courseid
     * @param resourceid
     * @return
     */
    public static List<QuestionInfo> getLittleViewQuesByXml(String xmlFullName,Long courseid,Long resourceid){
        if(xmlFullName==null||courseid==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<QuestionInfo> returnList=new ArrayList<QuestionInfo>();	//返回的集合
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")){
                if(courseMap.get("CourseId").toString().trim().equals(courseid.toString())){
                    if(courseMap.containsKey("LittleViewQuesInfoList")){
                        List<Map<String,Object>> quesList=(List<Map<String,Object>>)courseMap.get("LittleViewQuesInfoList");
                        if(quesList==null||quesList.size()<1)return returnList;
                        if(resourceid==null)return returnList;
                        //循环得到信息
                        for (Map<String, Object> quesMap : quesList) {
                            QuestionInfo ques=new QuestionInfo();
                            if(quesMap.containsKey("Resourceid")&&quesMap.get("Resourceid")!=null
                                    &&!quesMap.get("Resourceid").toString().trim().toUpperCase().equals("NULL")&&
                                    !quesMap.get("Resourceid").toString().equals(resourceid.toString())){
                                continue;
                            }
                            if(quesMap.containsKey("Body")&&quesMap.get("Body")!=null
                                    &&!quesMap.get("Body").toString().trim().toUpperCase().equals("NULL")){
                                ques.setContent(quesMap.get("Body").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("Status")&&quesMap.get("Status")!=null
                                    &&!quesMap.get("Status").toString().trim().toUpperCase().equals("NULL")){
                                ques.setStatus(Integer.parseInt(quesMap.get("Status").toString()));
                            }
                            if(quesMap.containsKey("Ctime")&&quesMap.get("Ctime")!=null
                                    &&!quesMap.get("Ctime").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCtime(UtilTool.StringConvertToDate(quesMap.get("CtimeString").toString()));
                            }
                            if(quesMap.containsKey("SourceType")&&quesMap.get("SourceType")!=null
                                    &&!quesMap.get("SourceType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setSourceType(Integer.parseInt(quesMap.get("SourceType").toString().trim()));
                            }
                            if(quesMap.containsKey("Type")&&quesMap.get("Type")!=null
                                    &&!quesMap.get("Type").toString().trim().toUpperCase().equals("NULL")){
                                Integer type=Integer.parseInt(quesMap.get("Type").toString());
                                switch(type){
                                    case -1:
                                        type=-2;
                                        break;
                                    case -2:
                                        type=2;
                                        break;
                                    case 1:
                                        type=2;
                                        ques.setQuestionlevel(3);
                                        break;
                                    case 2:
                                        type=4;
                                        ques.setQuestionlevel(4);
                                        break;
                                }

                                ques.setCloudstatus(type);
                            }
                            //类型（-1：恶意   -2：已删除   0：待审核   1：共享   2：标准）
                            //云端共享状态 -2:恶意 -1:未上传 0：待审核 1：未通过 2：已删除 3：共享 4：标准

                            if(quesMap.containsKey("Answertype")&&quesMap.get("Answertype")!=null
                                    &&!quesMap.get("Answertype").toString().trim().toUpperCase().equals("NULL")){
                                ques.setQuestiontype(Integer.parseInt(quesMap.get("Answertype").toString()));
                            }
                            if(quesMap.containsKey("Questionid")&&quesMap.get("Questionid")!=null
                                    &&!quesMap.get("Questionid").toString().trim().toUpperCase().equals("NULL")){
                                ques.setQuestionid(Long.parseLong(quesMap.get("Questionid").toString()));
                            }
                            if(quesMap.containsKey("Resolve")&&quesMap.get("Resolve")!=null
                                    &&!quesMap.get("Resolve").toString().trim().toUpperCase().equals("NULL")){
                                ques.setAnalysis(quesMap.get("Resolve").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("CuserId")&&quesMap.get("CuserId")!=null
                                    &&!quesMap.get("CuserId").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCuserid(quesMap.get("CuserId").toString());
                            }
                            if(quesMap.containsKey("CuserName")&&quesMap.get("CuserName")!=null
                                    &&!quesMap.get("CuserName").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCusername(quesMap.get("CuserName").toString());
                            }
                            if(quesMap.containsKey("Grade")&&quesMap.get("Grade")!=null
                                    &&!quesMap.get("Grade").toString().trim().toUpperCase().equals("NULL")){
                                ques.setGrade(quesMap.get("Grade").toString());
                            }
                            if(quesMap.containsKey("PaperType")&&quesMap.get("PaperType")!=null
                                    &&!quesMap.get("PaperType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setPapertypeid(Integer.parseInt(quesMap.get("PaperType").toString()));
                            }
                            if(quesMap.containsKey("ExamType")&&quesMap.get("ExamType")!=null
                                    &&!quesMap.get("ExamType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setExamtype(Integer.parseInt(quesMap.get("ExamType").toString()));
                            }
                            if(quesMap.containsKey("ExamSubjectTyp")&&quesMap.get("ExamSubjectTyp")!=null
                                    &&!quesMap.get("ExamSubjectTyp").toString().trim().toUpperCase().equals("NULL")){
                                ques.setExamsubjecttype(Integer.parseInt(quesMap.get("ExamSubjectTyp").toString()));
                            }
                            if(quesMap.containsKey("ExamArea")&&quesMap.get("ExamArea")!=null
                                    &&!quesMap.get("ExamArea").toString().trim().toUpperCase().equals("NULL")){
                                ques.setAxamarea(quesMap.get("ExamArea").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("ExamYear")&&quesMap.get("ExamYear")!=null
                                    &&!quesMap.get("ExamYear").toString().trim().toUpperCase().equals("NULL")){
                                ques.setExamyear(quesMap.get("ExamYear").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("Province")&&quesMap.get("Province")!=null
                                    &&!quesMap.get("Province").toString().trim().toUpperCase().equals("NULL")){
                                ques.setProvince(quesMap.get("Province").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("City")&&quesMap.get("City")!=null
                                    &&!quesMap.get("City").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCity(quesMap.get("City").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("correctanswer")&&quesMap.get("City")!=null
                                    &&!quesMap.get("correctanswer").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCorrectanswer(quesMap.get("correctanswer").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
//
//                            ques.setSchoolId(schoolid);
//                            ques.setSchoolName(schoolname);
                            returnList.add(ques);
                        }
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 从XML中得到问题信息
     * @param xmlFullName
     * @param courseid
     * @return
     */
    public static List<QuestionInfo> getQuestionByXml(String xmlFullName,Long courseid){
        if(xmlFullName==null||courseid==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<QuestionInfo> returnList=new ArrayList<QuestionInfo>();	//返回的集合
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")){
                if(courseMap.get("CourseId").toString().trim().equals(courseid.toString())){
                    if(courseMap.containsKey("QuestionInfoList")){
                        List<Map<String,Object>> quesList=(List<Map<String,Object>>)courseMap.get("QuestionInfoList");
                        if(quesList==null||quesList.size()<1)return returnList;
                        //循环得到信息
                        for (Map<String, Object> quesMap : quesList) {
                            QuestionInfo ques=new QuestionInfo();
                            if(quesMap.containsKey("Body")&&quesMap.get("Body")!=null
                                    &&!quesMap.get("Body").toString().trim().toUpperCase().equals("NULL")){
                                ques.setContent(quesMap.get("Body").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("Status")&&quesMap.get("Status")!=null
                                    &&!quesMap.get("Status").toString().trim().toUpperCase().equals("NULL")){
                                ques.setStatus(Integer.parseInt(quesMap.get("Status").toString()));
                            }
                            if(quesMap.containsKey("Ctime")&&quesMap.get("Ctime")!=null
                                    &&!quesMap.get("Ctime").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCtime(UtilTool.StringConvertToDate(quesMap.get("CtimeString").toString()));
                            }
                            if(quesMap.containsKey("SourceType")&&quesMap.get("SourceType")!=null
                                    &&!quesMap.get("SourceType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setSourceType(Integer.parseInt(quesMap.get("SourceType").toString().trim()));
                            }
                            if(quesMap.containsKey("Type")&&quesMap.get("Type")!=null
                                    &&!quesMap.get("Type").toString().trim().toUpperCase().equals("NULL")){
                                Integer type=Integer.parseInt(quesMap.get("Type").toString());
                                switch(type){
                                    case -1:
                                        type=-2;
                                        break;
                                    case -2:
                                        type=2;
                                        break;
                                    case 1:
                                        type=2;
                                        ques.setQuestionlevel(3);
                                        break;
                                    case 2:
                                        type=4;
                                        ques.setQuestionlevel(4);
                                        break;
                                }

                                ques.setCloudstatus(type);
                            }
                            //类型（-1：恶意   -2：已删除   0：待审核   1：共享   2：标准）
                            //云端共享状态 -2:恶意 -1:未上传 0：待审核 1：未通过 2：已删除 3：共享 4：标准

                            if(quesMap.containsKey("AnswerType")&&quesMap.get("AnswerType")!=null
                                    &&!quesMap.get("AnswerType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setQuestiontype(Integer.parseInt(quesMap.get("AnswerType").toString()));
                            }
                            if(quesMap.containsKey("QuestionId")&&quesMap.get("QuestionId")!=null
                                    &&!quesMap.get("QuestionId").toString().trim().toUpperCase().equals("NULL")){
                                ques.setQuestionid(Long.parseLong(quesMap.get("QuestionId").toString()));
                            }
                            if(quesMap.containsKey("Resolve")&&quesMap.get("Resolve")!=null
                                    &&!quesMap.get("Resolve").toString().trim().toUpperCase().equals("NULL")){
                                ques.setAnalysis(quesMap.get("Resolve").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("CuserId")&&quesMap.get("CuserId")!=null
                                    &&!quesMap.get("CuserId").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCuserid(quesMap.get("CuserId").toString());
                            }
                            if(quesMap.containsKey("CuserName")&&quesMap.get("CuserName")!=null
                                    &&!quesMap.get("CuserName").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCusername(quesMap.get("CuserName").toString());
                            }
                            if(quesMap.containsKey("Grade")&&quesMap.get("Grade")!=null
                                    &&!quesMap.get("Grade").toString().trim().toUpperCase().equals("NULL")){
                                ques.setGrade(quesMap.get("Grade").toString());
                            }
                            if(quesMap.containsKey("PaperType")&&quesMap.get("PaperType")!=null
                                    &&!quesMap.get("PaperType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setPapertypeid(Integer.parseInt(quesMap.get("PaperType").toString()));
                            }
                            if(quesMap.containsKey("ExamType")&&quesMap.get("ExamType")!=null
                                    &&!quesMap.get("ExamType").toString().trim().toUpperCase().equals("NULL")){
                                ques.setExamtype(Integer.parseInt(quesMap.get("ExamType").toString()));
                            }
                            if(quesMap.containsKey("ExamSubjectTyp")&&quesMap.get("ExamSubjectTyp")!=null
                                    &&!quesMap.get("ExamSubjectTyp").toString().trim().toUpperCase().equals("NULL")){
                                ques.setExamsubjecttype(Integer.parseInt(quesMap.get("ExamSubjectTyp").toString()));
                            }
                            if(quesMap.containsKey("ExamArea")&&quesMap.get("ExamArea")!=null
                                    &&!quesMap.get("ExamArea").toString().trim().toUpperCase().equals("NULL")){
                                ques.setAxamarea(quesMap.get("ExamArea").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("ExamYear")&&quesMap.get("ExamYear")!=null
                                    &&!quesMap.get("ExamYear").toString().trim().toUpperCase().equals("NULL")){
                                ques.setExamyear(quesMap.get("ExamYear").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("Province")&&quesMap.get("Province")!=null
                                    &&!quesMap.get("Province").toString().trim().toUpperCase().equals("NULL")){
                                ques.setProvince(quesMap.get("Province").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("City")&&quesMap.get("City")!=null
                                    &&!quesMap.get("City").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCity(quesMap.get("City").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            if(quesMap.containsKey("correctanswer")&&quesMap.get("City")!=null
                                    &&!quesMap.get("correctanswer").toString().trim().toUpperCase().equals("NULL")){
                                ques.setCorrectanswer(quesMap.get("correctanswer").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
//
//                            ques.setSchoolId(schoolid);
//                            ques.setSchoolName(schoolname);
                            returnList.add(ques);
                        }
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 从XML中得到问题信息
     * @param xmlFullName
     * @param courseid
     * @return
     */
    public static List<Map<String,Object>> getQuesPaperTypeByXml(String xmlFullName,Long courseid){
        if(xmlFullName==null||courseid==null) return null;
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();	//返回的集合
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")){
                if(courseMap.get("CourseId").toString().trim().equals(courseid.toString())){
                    if(courseMap.containsKey("QuesPaperTypeInfoList")){
                        List<Map<String,Object>> quesList=(List<Map<String,Object>>)courseMap.get("QuesPaperTypeInfoList");
                        if(quesList==null||quesList.size()<1)return returnList;
                        //循环得到信息
                        returnList=quesList;
                    }
                }
            }
        }
        return returnList;
    }


    /**
     * 解析得到专题的集合
     * @param xmlpath 分解析的XML完整路径
     */
    public static List<TpCourseInfo> getTpCourseByXml(String xmlpath){
        if(_path==null||!xmlpath.equals(_path)||_list==null){
            _path=xmlpath;
            _list=OperateXMLUtil.findXml(xmlpath, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;

        List<TpCourseInfo> returnList=new ArrayList<TpCourseInfo>();
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //建立实体
            Object obj=null;
            TpCourseInfo courseInfo=new TpCourseInfo();
            if(courseMap.containsKey("CourseId")){		//ID
                obj=courseMap.get("CourseId");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setCourseid(Long.parseLong(obj.toString()));
                }
            }
            if(courseMap.containsKey("TeacherName")){	//教师NAME
                obj=courseMap.get("TeacherName");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setTeachername(obj.toString().replaceAll("\\\\","\\\\\\\\"));
                }
            }
            if(courseMap.containsKey("CtimeString")){			//创建时间
                obj=courseMap.get("CtimeString");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setCtime(UtilTool.StringConvertToDate(obj.toString()));
                }
            }
            if(courseMap.containsKey("MtimeString")){			//审核时间
                obj=courseMap.get("MtimeString");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setMtime(UtilTool.StringConvertToDate(obj.toString()));
                }
            }
			if(courseMap.containsKey("Type")){		//云端状态
                obj=courseMap.get("Type");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    Integer coludsstatus=Integer.parseInt(obj.toString());
                    switch(coludsstatus){
                        case -1:
                            coludsstatus=2;
                            break;
                        case -2:
                            coludsstatus=-1;
                            break;
                        case 1:
                            coludsstatus=0;
                            break;
                        case 2:
                            courseInfo.setCourselevel(2);   //共享
                            coludsstatus=3;
                            break;
                        case 3:
                            courseInfo.setCourselevel(1);       //标准
                            coludsstatus=4;
                            break;
                    }
                    if(coludsstatus==-1)
                        coludsstatus=2;
                    courseInfo.setCloudstatus(Integer.parseInt(obj.toString()));
                }
			}
            if(courseMap.containsKey("CourseName")){		//专题名称
                obj=courseMap.get("CourseName");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setCoursename(obj.toString().replaceAll("\\\\","\\\\\\\\"));
                }
            }
//			if(courseMap.containsKey("Materialid")){		//教材ID
//				obj=courseMap.get("Coursename");
//				if(!obj.toString().trim().toUpperCase().equals("NULL")){
//					//新建云端ID,将分校的专题ID赋入对应的字段
//					courseInfo.setCourseName(obj.toString());
//				}
//			}
//			if(courseMap.containsKey("Sharetype")){			//同享类型
//
//			}
            if(courseMap.containsKey("CourseId")){			//创建人ID
                obj=courseMap.get("CourseId");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setCourseid(Long.parseLong(obj.toString()));
                }
            }
            if(courseMap.containsKey("Status")){		//专题状态
                obj=courseMap.get("Status");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    Integer status=Integer.parseInt(obj.toString());
//                    switch(status){
//                        case 1:
//                            break;
//                        case 2:
//                            break;
//                    }

                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setCoursestatus(status);
                }
            }
            if(courseMap.containsKey("SchoolName")){		//分校名称
                obj=courseMap.get("SchoolName");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setSchoolname(obj.toString().replaceAll("\\\\","\\\\\\\\"));
                }
            }

//			if(courseMap.containsKey("Courselevel")){		//专题等级
//
//			}
//
            if(courseMap.containsKey("Introduction")){		//专题简介
                obj=courseMap.get("Introduction");
                if(!obj.toString().trim().toUpperCase().equals("NULL")){
                    //新建云端ID,将分校的专题ID赋入对应的字段
                    courseInfo.setIntroduction(obj.toString().replaceAll("\\\\","\\\\\\\\"));
                }
            }
//            if(courseMap.containsKey("Quoteid")){			//引用专题编号
//                obj=courseMap.get("Quoteid");
//                if(!obj.toString().trim().toUpperCase().equals("NULL")){
//                    //新建云端ID,将分校的专题ID赋入对应的字段
//                    courseInfo.setIntroduction(obj.toString());
//                }
//            }

            returnList.add(courseInfo);

        }


        return returnList;
    }

    /**
     * 得到XML中的ThemeInfo对象
     * @param xmlFullName
     * @param schoolCourseId
     * @param schoolTopicId
     * @return
     */
    public static List<TpTopicThemeInfo> getTpTopicThemeByXml(
            String xmlFullName, Long schoolCourseId, Long schoolTopicId) {
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;

        List<TpTopicThemeInfo> returnList=new ArrayList<TpTopicThemeInfo>();
        for (Object obj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)obj;
            if(courseMap.containsKey("CourseId")&&courseMap.containsKey("TpTopicThemeInfoList")){
                if(courseMap.get("CourseId")!=null
                        &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                        &&courseMap.get("CourseId").toString().trim().equals(schoolCourseId.toString())){
                    List<Map<String,Object>> themeList=(List<Map<String,Object>>)courseMap.get("TpTopicThemeInfoList");
                    if(themeList==null||themeList.size()<1)break;
                    for (Map<String, Object> mp : themeList) {
                        if(mp.containsKey("Topicid")&&mp.get("Topicid")!=null
                                &&!mp.get("Topicid").toString().trim().toUpperCase().equals("NULL")){
                            String topicid=mp.get("Topicid").toString().trim();
                            if(topicid.equals(schoolTopicId.toString())){
                                TpTopicThemeInfo theme=new TpTopicThemeInfo();
                                if(mp.containsKey("Commentuserid")&&mp.get("Commentuserid")!=null
                                        &&!mp.get("Commentuserid").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setCommentuserid(mp.get("Commentuserid").toString());
                                }
                                if(mp.containsKey("Courseid")&&mp.get("Courseid")!=null
                                        &&!mp.get("Courseid").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setCourseid(Long.parseLong(mp.get("Courseid").toString()));
                                }
                                if(mp.containsKey("Themetitle")&&mp.get("Themetitle")!=null
                                        &&!mp.get("Themetitle").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setThemetitle(mp.get("Themetitle").toString().replaceAll("\\\\","\\\\\\\\"));
                                }
                                if(mp.containsKey("Themecontent")&&mp.get("Themecontent")!=null
                                        &&!mp.get("Themecontent").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setThemecontent(mp.get("Themecontent").toString().replaceAll("\\\\","\\\\\\\\"));
                                }
                                if(mp.containsKey("Themeid")&&mp.get("Themeid")!=null
                                        &&!mp.get("Themeid").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setThemeid(Long.parseLong(mp.get("Themeid").toString()));
                                }
                                if(mp.containsKey("Commentcontent")&&mp.get("Commentcontent")!=null
                                        &&!mp.get("Commentcontent").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setThemecontent(mp.get("Commentcontent").toString().replaceAll("\\\\","\\\\\\\\"));
                                }
                                if(mp.containsKey("Commenttitle")&&mp.get("Commenttitle")!=null
                                        &&!mp.get("Commenttitle").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setCommenttitle(mp.get("Commenttitle").toString().replaceAll("\\\\","\\\\\\\\"));
                                }
                                if(mp.containsKey("Cuserid")&&mp.get("Cuserid")!=null
                                        &&!mp.get("Cuserid").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setCuserid(Integer.parseInt(mp.get("Cuserid").toString()));
                                }
                                if(mp.containsKey("Istop")&&mp.get("Istop")!=null
                                        &&!mp.get("Istop").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setIstop(Integer.parseInt(mp.get("Istop").toString()));
                                }
                                if(mp.containsKey("Isessence")&&mp.get("Isessence")!=null
                                        &&!mp.get("Isessence").toString().trim().toUpperCase().equals("NULL")){
                                    theme.setIsessence(Integer.parseInt(mp.get("Isessence").toString()));
                                }
                                if(mp.containsKey("Status")&&mp.get("Status")!=null
                                        &&!mp.get("Status").toString().trim().toUpperCase().equals("NULL")){
                                    //0：待审核  1：已删除  3：恶意  4：通过  ORACLE
                                    //云端共享状态 -1：未上传 0：待审核 1：未通过 2：已删除 3：通过  4:恶意
                                    Integer status=Integer.parseInt(mp.get("Status").toString());
                                    switch(status){
                                        case 1:
                                            status=2;
                                            break;
                                        case 3:
                                            status=4;
                                            break;
                                    }
                                    theme.setIsessence(status);
                                }

                                theme.setTopicid(schoolTopicId);
                                returnList.add(theme);
                            }
                        }
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 解析得到专题论题的集合
     * @param xmlFullName 分解析的XML完整路径
     * @param courseId 得到CourseId下的文件
     */
    public static List getTpTopicByXml(String xmlFullName,Long courseId) {
        List<TpTopicInfo> topicList=new ArrayList<TpTopicInfo>();

        //得到要解析后的实体  (不加载子项)
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        for (Object obj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)obj;
            if(courseMap.containsKey("CourseId")&&courseMap.containsKey("TpTopicInfoList")){
                if(courseMap.get("CourseId")!=null
                        &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                        &&courseMap.get("CourseId").toString().trim().equals(courseId.toString())){
                    Object courseIdObj=courseMap.get("CourseId");
                    if(courseIdObj!=null&&courseIdObj.toString().trim().length()>0){
                        if(courseIdObj.toString().trim().equals(courseId.toString())){
                            List<Map<String,Object>> topicMapList=(List<Map<String,Object>>)courseMap.get("TpTopicInfoList");
                            for (Map<String, Object> mpTopic : topicMapList) {
                                TpTopicInfo topic=new TpTopicInfo();
//                                topic.sets(Long.parseLong(schoolid.toString()));
//                                topic.setSchoolName(schoolname);
                                //Status
                                if(mpTopic.containsKey("Status")&&mpTopic.get("Status")!=null&&!mpTopic.get("Status").toString().toUpperCase().equals("NULL")){

                                    topic.setStatus(Integer.parseInt(mpTopic.get("Status").toString().trim()));
                                }

                                //Status
                                if(mpTopic.containsKey("Ctime")&&mpTopic.get("Ctime")!=null&&!mpTopic.get("Ctime").toString().toUpperCase().equals("NULL"))
                                    topic.setCtimeString(mpTopic.get("Ctime").toString().trim());

                                //Courseid
                                topic.setCourseid(courseId);
                                //Topicid
                                if(mpTopic.containsKey("Topicid")&&mpTopic.get("Topicid")!=null&&!mpTopic.get("Topicid").toString().toUpperCase().equals("NULL"))
                                    topic.setTopicid(Long.parseLong(mpTopic.get("Topicid").toString().trim()));

                                //Topictitle
                                if(mpTopic.containsKey("Topictitle")&&mpTopic.get("Topictitle")!=null&&!mpTopic.get("Topictitle").toString().toUpperCase().equals("NULL"))
                                    topic.setTopictitle(mpTopic.get("Topictitle").toString().trim().replaceAll("\\\\","\\\\\\\\"));

                                //Topiccontent
                                if(mpTopic.containsKey("Topiccontent")&&mpTopic.get("Topiccontent")!=null&&!mpTopic.get("Topiccontent").toString().toUpperCase().equals("NULL"))
                                    topic.setTopiccontent(mpTopic.get("Topiccontent").toString().trim().replaceAll("\\\\","\\\\\\\\"));

                                //Cuserid
                                if(mpTopic.containsKey("Cuserid")&&mpTopic.get("Cuserid")!=null&&!mpTopic.get("Cuserid").toString().toUpperCase().equals("NULL"))
                                    topic.setCuserid(Integer.parseInt(mpTopic.get("Cuserid").toString().trim()));

                                //Orderidx
                                if(mpTopic.containsKey("Orderidx")&&mpTopic.get("Orderidx")!=null&&!mpTopic.get("Orderidx").toString().toUpperCase().equals("NULL"))
                                    topic.setOrderidx(Integer.parseInt(mpTopic.get("Orderidx").toString().trim()));

                                //Topickeyword
                                if(mpTopic.containsKey("Topickeyword")&&mpTopic.get("Topickeyword")!=null&&!mpTopic.get("Topickeyword").toString().toUpperCase().equals("NULL"))
                                    topic.setTopickeyword(mpTopic.get("Topickeyword").toString().trim().replaceAll("\\\\","\\\\\\\\"));
                                //cloud_status
                                //类型（-1：已删除   -2：恶意   0：待审核  1：通过）
                                //云端共享状态 -1:未上传 0：待审核 1：未通过 2：已删除 3：通过
                                if(mpTopic.containsKey("Type")&&mpTopic.get("Type")!=null&&!mpTopic.get("Type").toString().toUpperCase().equals("NULL")){
                                    Integer type=Integer.parseInt(mpTopic.get("Type").toString());
                                    switch(type){
                                        case -1:
                                            type=2;
                                            break;
                                        case 1:
                                            type=3;
                                            break;
                                    }
                                    topic.setCloudstatus(type);
                                }

                                topicList.add(topic);
                            }
                        }
                    }
                    break;
                }
            }
        }
        return topicList;
    }

    /**
     * 解析得到专题论题的集合
     * @param xmlFullName 分解析的XML完整路径
     * @param courseId 得到CourseId下的文件
     */
    public static List getEttQuesTeamRelaByXml(String xmlFullName,Long courseId,Long teamid) {
        List<QuesTeamRela> qtrList=new ArrayList<QuesTeamRela>();
        //得到要解析后的实体  (不加载子项)
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        for (Object obj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)obj;
            if(courseMap.containsKey("CourseId")&&courseMap.containsKey("QuesTeamRelaList")){
                if(courseMap.get("CourseId")!=null
                        &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                        &&courseMap.get("CourseId").toString().trim().equals(courseId.toString())){
                    Object courseIdObj=courseMap.get("CourseId");
                    if(courseIdObj!=null&&courseIdObj.toString().trim().length()>0){
                        if(courseIdObj.toString().trim().equals(courseId.toString())){
                            List<Map<String,Object>> topicMapList=(List<Map<String,Object>>)courseMap.get("QuesTeamRelaList");
                            for (Map<String, Object> mp : topicMapList) {
                                if(mp.containsKey("Teamid")&&mp.get("Teamid")!=null&&mp.get("Teamid").toString().trim().equals(teamid.toString())){
                                    QuesTeamRela qtr=new QuesTeamRela();//
                                    //Status
                                    if(mp.containsKey("Ref")&&mp.get("Ref")!=null&&!mp.get("Ref").toString().toUpperCase().equals("NULL"))
                                        qtr.setRef(Long.parseLong(mp.get("Ref").toString().trim()));
                                    //Quesid
                                    if(mp.containsKey("Quesid")&&mp.get("Quesid")!=null&&!mp.get("Quesid").toString().toUpperCase().equals("NULL"))
                                        qtr.setQuesid(Long.parseLong(mp.get("Quesid").toString().trim()));
                                    //Teamid
                                    if(mp.containsKey("Teamid")&&mp.get("Teamid")!=null&&!mp.get("Teamid").toString().toUpperCase().equals("NULL"))
                                        qtr.setTeamid(Long.parseLong(mp.get("Teamid").toString().trim()));
                                    //Orderid
                                    if(mp.containsKey("Orderid")&&mp.get("Orderid")!=null&&!mp.get("Orderid").toString().toUpperCase().equals("NULL"))
                                        qtr.setOrderid(Integer.parseInt(mp.get("Orderid").toString().trim()));

                                    qtrList.add(qtr);
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return qtrList;
    }
    /**
     * 解析得到问题答案信息
     * @param xmlFullName 分解析的XML完整路径
     * @param courseId 得到CourseId下的文件
     * @param  schQuestionid 问题的ID
     */
    public static List<QuestionOption> getQuestionOptsByXml(String xmlFullName,Long courseId,Long schQuestionid) {
        if(xmlFullName==null||courseId==null)return null;
        //得到要解析后的实体  (不加载子项)
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<QuestionOption> returnList=new ArrayList<QuestionOption>();	//返回的集合
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")){
                if(courseMap.get("CourseId").toString().trim().equals(courseId.toString())&&courseMap.containsKey("QuestionAnswerList")){
                    List<Map<String,Object>> quesOptionList=(List<Map<String,Object>>)courseMap.get("QuestionAnswerList");
                    if(quesOptionList==null||quesOptionList.size()<1)return returnList;
                    //循环得到信息
                    for (Map<String, Object> quesOptMap : quesOptionList) {
                        if(schQuestionid==null||(quesOptMap.get("QuestionId")!=null&&quesOptMap.get("QuestionId").toString().trim().equals(schQuestionid.toString()))){
                            QuestionOption quesAnswer=new QuestionOption();

                            if(courseMap.containsKey("QuestionId")&&courseMap.get("CourseId")!=null&&!quesOptMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setQuestionid(Long.parseLong(courseMap.get("CourseId").toString()));//云端的ID
                            }

                            if(quesOptMap.containsKey("Body")&&quesOptMap.get("Body")!=null
                                    &&!quesOptMap.get("Body").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setContent(quesOptMap.get("Body").toString().replaceAll("\\\\","\\\\\\\\"));
                            }
                            //						if(quesOptMap.containsKey("Ref")&&quesOptMap.get("Ref")!=null
                            //								&&!quesOptMap.get("Ref").toString().trim().toUpperCase().equals("NULL")){
                            //							quesAnswer.setRef(Long.parseLong(quesOptMap.get("Ref").toString()));
                            //						}
                            if(quesOptMap.containsKey("Score")&&quesOptMap.get("Score")!=null
                                    &&!quesOptMap.get("Score").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setScore(Integer.parseInt(quesOptMap.get("Score").toString()));
                            }
                            if(quesOptMap.containsKey("Ctime")&&quesOptMap.get("Ctime")!=null
                                    &&!quesOptMap.get("Ctime").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setCtime(UtilTool.StringConvertToDate(quesOptMap.get("Ctime").toString()));
                            }
                            if(quesOptMap.containsKey("OptionType")&&quesOptMap.get("OptionType")!=null
                                    &&!quesOptMap.get("OptionType").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setOptiontype(quesOptMap.get("OptionType").toString());
                            }
                            if(quesOptMap.containsKey("Right")&&quesOptMap.get("Right")!=null
                                    &&!quesOptMap.get("Right").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setIsright(Integer.parseInt(quesOptMap.get("Right").toString()));
                            }
                            if(quesOptMap.containsKey("Ref")&&quesOptMap.get("Ref")!=null
                                    &&!quesOptMap.get("Ref").toString().trim().toUpperCase().equals("NULL")){
                                quesAnswer.setRef(Integer.parseInt(quesOptMap.get("Ref").toString()));
                            }

                            if(!returnList.contains(quesAnswer))
                                returnList.add(quesAnswer);
                        }
                    }
                }
            }
        }
        return returnList;
    }


    /**
     * 得到专题问题
     * @param xmlFullName
     * @param courseId
     * @return
     */
    public static List<TpCourseQuestion> getCourseQuesByXml(String xmlFullName,Long courseId){
        if(xmlFullName==null||courseId==null)return null;
        //得到要解析后的实体  (不加载子项)
        if(_path==null||!xmlFullName.equals(_path)||_list==null){
            _path=xmlFullName;
            _list=OperateXMLUtil.findXml(xmlFullName, "//table //row", true);
        }
        if(_list==null||_list.size()<1)return null;
        List<TpCourseQuestion> returnList=new ArrayList<TpCourseQuestion>();	//返回的集合
        //循环得到相关实体
        for (Object mapObj : _list) {
            Map<String,Object> courseMap=(Map<String,Object>)mapObj;
            //相关ID,不能为空
            if(courseMap.containsKey("CourseId")&&courseMap.get("CourseId")!=null
                    &&!courseMap.get("CourseId").toString().trim().toUpperCase().equals("NULL")
                    &&courseMap.get("CourseId").toString().trim().equals(courseId.toString())
                    ){
                if(courseMap.containsKey("TpCourseQuestionList")){
                    List<Map<String,Object>> courseQuesList=(List<Map<String,Object>>)courseMap.get("TpCourseQuestionList");
                    if(courseQuesList==null||courseQuesList.size()<1)return returnList;
                    for (Map<String, Object> cqmap : courseQuesList) {
                        if(cqmap!=null){
                            TpCourseQuestion cq=new TpCourseQuestion();
                            if(cqmap.containsKey("QuestionId")&&cqmap.get("QuestionId")!=null
                                    &&!cqmap.get("QuestionId").toString().trim().toUpperCase().equals("NULL")){
                                cq.setQuestionid(Long.parseLong(cqmap.get("QuestionId").toString()));
                            }
                            cq.setCourseid(courseId);
                           // cq.set(1);
                            if(cqmap.containsKey("referenceType")&&cqmap.get("referenceType")!=null
                                    &&!cqmap.get("referenceType").toString().trim().toUpperCase().equals("NULL")){
                                cq.setIsreference(Integer.parseInt(cqmap.get("referenceType").toString()));
                            }
//                            if(cqmap.containsKey("Ref")&&cqmap.get("Ref")!=null
//                                    &&!cqmap.get("Ref").toString().trim().toUpperCase().equals("NULL")){
//                                cq.setRef(Long.parseLong(cqmap.get("Ref").toString()));
//                            }
                            returnList.add(cq);
                        }
                    }
                }
            }
        }
        return returnList;
    }

    /**
     * 下载ZIP到指定目录下
     * @param zipLocaPath
     * @return
     */
    public static boolean downLoadZipFile(String zipLocaPath,String toPath){
        //验证参数
        if(zipLocaPath==null||zipLocaPath.toString().trim().length()<1
                ||toPath==null||toPath.toString().trim().length()<1){
            return false;
        }
        FileOutputStream out=null;
        InputStream in=null;
        try {
        URL url=new URL(zipLocaPath);
        URLConnection uc=url.openConnection();
        uc.connect();
        HttpURLConnection huc=(HttpURLConnection)uc;
        if(huc.getResponseCode()!=HttpURLConnection.HTTP_OK){
            //判断是否成功连接到http，如果不能连接则返回
            System.out.println("can't connect");
            return false;
        }
            File dir = new File(toPath);
            dir=new File(dir.getParent());
            if(!dir.exists()) //不存在，则创建
                dir.mkdirs();
            File tofile = new File(toPath);
            in=(uc.getInputStream());//打开输入流
            byte[] data=new byte[1024*10];
            int l=in.read(data);
            if(!tofile.exists()) tofile.createNewFile();
             out=new FileOutputStream(tofile);
            while(l!=-1){
                out.write(data, 0, l);
                l=in.read(data);
            }
            out.flush();
            out.close();
            System.out.println(zipLocaPath+" 下载OK");
//
//
//        //开始下载文件
//        URL url = null;
//        InputStream in =null;
//
//            url = new URL(zipLocaPath);
//            in= url.openStream();
//            File dir = new File(toPath);
//            dir=new File(dir.getParent());
//            if(!dir.exists()) //不存在，则创建
//                dir.mkdirs();
//            File tofile = new File(toPath);
//            //开始复制文件
//            FileOutputStream out = new FileOutputStream(tofile);
//            Streams.copy(in, out, true);
        } catch (Exception e) {
           // e.printStackTrace();
            try{
            if(in!=null)in.close();
            if(out!=null){out.flush();out.close();}
            }catch(Exception e1){}
            return false;
        }
        return true;
    }

    /**
     * 根据link得到filename
     * @param locaPath
     * @return
     */
    public static String getFileName(String locaPath){
        if(locaPath==null)return null;
        return locaPath.substring(locaPath.lastIndexOf("/")+1);
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

    /**
     * fasong ziyuan
     * @param key school.txt中记录的字符串
     * @param ftime 上一次更新的时间
     * @return  生成数据文件的远程地址
     */
    public static Map<String,Object> getUpdateCourse(String key,String ftime){
        if(key==null||key.trim().length()<1)return null;
        //组织生成的数据文件地址
        String[] urlArray=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UPDATE_COURSE").toString().split("\\?");
        StringBuilder totalSchoolUrl=new StringBuilder();
        totalSchoolUrl.append(
                UtilTool.utilproperty.get("TOTAL_SCHOOL_LOCATION")).append(urlArray[0]);
        StringBuilder params=new StringBuilder(urlArray[1]).append("&key=").append(key);
        if(ftime!=null){
            params.append("&ftime=");
            params.append(ftime);
        }
        System.out.println(totalSchoolUrl+"?"+params.toString());
        return sendPostURL(totalSchoolUrl.toString(),params.toString());
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

}
