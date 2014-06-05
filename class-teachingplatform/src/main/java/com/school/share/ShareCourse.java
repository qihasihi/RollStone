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
 *  ר��ͬ��(�ӿڣ���ʱִ��[����])
 *  Created by zhengzhou on 2014-2-22.
 *  @deprecated �漰�ı�
 *  dc_tp_course_info;               ר��
 *  dc_tp_j_course_question;         ר������
 *  DC_TP_J_COURSE_TASK_PAPER        ר�������Ծ�
 *  DC_TP_J_COURSE_TCH_MATERIA       ר��̲�
 *  DC_TP_J_COUR_RESOURCE            ר����Դ
 *  DC_TP_OPERATE_INFO               ר�����
 *  DC_TP_PAPER_INFO                 ר���Ծ�
 *  DC_TP_TASK_INFO                  ר������
 *  DC_TP_TOPIC_INFO                 ר������
 *  DC_TP_TOPIC_THEME_INFO           ר����������
 */
public class ShareCourse extends TimerTask {
    /**
     * ���ɵ�ģ��XML
     */
    private final static String _Template_name="ShareTemplate.xml";
    /**
     * ÿ��XML�ļ��ĸ���
     */
    private Integer _PageSize=1;
    /**
     * ר����Դ�ļ�¼
     */
    private Integer _PageSize_Children=20;
    /**
     * ��УID
     */
    private String _Schoolid=null;

    private final String _UploadFileName="uploadFile.txt";

    private ServletContext request;
    public ShareCourse(ServletContext application){
        //��УID
        this.request=application;
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }


    public static void main(String[] args){
        String templatepath="F:/javaworkspance/sz_school/WebRoot/uploadfile/tmp/"+_Template_name;
        File fromF=new File(templatepath);
        if(!fromF.exists()){
            //��¼������Ϣ
            System.out.println("��У�쳣!û�з�����Դ�ϴ�ģ��!");
            return;
        }

        String writeUrl="F:/javaworkspance/sz_school/WebRoot/uploadfile/tmp/1393223762485.xml";
        //�����ļ��У�������ģ�壬���в���
        File tmpF1=new File(writeUrl);
        try{
            if(!tmpF1.getParentFile().exists())
                tmpF1.getParentFile().mkdirs();
            if(tmpF1.exists()&&tmpF1.isFile())
                tmpF1.delete();
            //����ģ��
            FileUtils.copyFile(fromF, tmpF1);
        }catch(Exception e){
            //��¼��־
            System.out.println("��У�쳣!�������ļ�ʧ�ܣ�ԭ��δ֪");
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
        String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/"; //��·��
        String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();    //xml�ļ����ļ���
        ITpCourseManager courseManager=(TpCourseManager)SpringBeanUtil.getBean("tpCourseManager");
        String directionPath=parentDirectory+firstDirectory+"/";
        String dataFileParent=directionPath+"data/";
        String xmlPathUrl=dataFileParent+"/"+filename;

        //��¼��Ҫ�ϴ����ļ�����·��
        String uploadFileTxt=directionPath+_UploadFileName;
        //ģ��XML�ļ����Ƽ�·��
        String templatepath=parentDirectory+_Template_name;
        File fromF=new File(templatepath);
        if(!fromF.exists()){
            //��¼������Ϣ
            System.out.println("��У�쳣!û�з�����Դ�ϴ�ģ��!");
            return;
        }
        //�������õ�Ҫ���е�ר��
        PageResult presult=new PageResult();
        presult.setPageSize(_PageSize);
        presult.setPageNo(0);
        TpCourseInfo courseEntity=new TpCourseInfo();
        courseEntity.setSharetype(2);   //�ƶ˹���
       // courseEntity.setCloudstatus(0);   //�ƶ˷���״̬�� �����
        courseEntity.setLocalstatus(1); //����״̬ ����

        boolean ishasData=false;
        //ѭ����ѯ
        while(true){
            presult.setPageNo(presult.getPageNo()+1);
            String writeUrl=xmlPathUrl;
            //XML�ļ�������
            writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))+"_"+presult.getPageNo()+writeUrl.substring(writeUrl.lastIndexOf("."));
            //��ѯҪ�����ר��
            List<TpCourseInfo> tpCourseList=courseManager.getShareList(courseEntity, presult);
            if(tpCourseList==null||tpCourseList.size()<1)   //˵��û��Ҫ�����ר���ˣ��˳�
                break;
            //�����ļ��У�������ģ�壬���в���
            File tmpF1=new File(writeUrl);
            try{
                if(!tmpF1.getParentFile().exists())
                    tmpF1.getParentFile().mkdirs();
                if(tmpF1.exists()&&tmpF1.isFile())
                    tmpF1.delete();
                //����ģ��
                FileUtils.copyFile(fromF, tmpF1);
            }catch(Exception e){
                //��¼��־
                System.out.println("��У�쳣!�������ļ�ʧ�ܣ�ԭ��δ֪");
                e.printStackTrace();
            }

            ishasData=true;
            File txtFile=new File(directionPath+_UploadFileName);
            if(txtFile.exists()){
                System.gc();        //����������ɾ���ļ�����������
                txtFile.delete();
            }
            for(TpCourseInfo tc:tpCourseList){
                //tc.setSchoolname(UtilTool.utilproperty.getProperty("CURRENT_SCHOOL_NAME"));
                //д��XML
                if(!ShareCourseUtil.addDateToXml(writeUrl,tc)){
                    System.out.println("�쳣����д��ר����XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                    return;
                }
                //�ȼ�¼��ֵ
                UtilTool.writeFile(request,directionPath,_UploadFileName,"");
                //�õ�ר�����õ�ͼƬ�زĽ����ϴ�
                String path=MD5_NEW.getMD5Result(tc.getCourseid().toString());
                String folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                File f=new File(folderpath);
                if(f.exists()&&f.isDirectory()){
                    ZipUtil.genZip(folderpath,directionPath,path);    //����zip

                    //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");
                }

                //�õ�
                PageResult presource=new PageResult();
                presource.setPageNo(0);
                presource.setPageSize(_PageSize_Children);
                //ѭ���õ�ר�������¼
               /* while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List<TpOperateInfo> tpOperateList=getOperateByCourseId(tc.getCourseid(),presource);
                    if(tpOperateList==null||tpOperateList.size()<1)break;
                    //��¼д��XML
                    //д��XML                   ;
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid() + "", tpOperateList)){
                        System.out.println("�쳣����д��ר�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                }*/
                //ѭ���õ�ר��̲ļ�¼
                presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List teachingMaterialList=getTeachingMaterialByCourseId(tc.getCourseid(),presource);
                    if(teachingMaterialList==null||teachingMaterialList.size()<1)break;
//                    for(int i=0;i<teachingMaterialList.size();i++){
                        //д��XML��
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",teachingMaterialList)){
                            System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                            return;
                        }
//                  }
                }
                //ѭ���õ�ר����Դ
                presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    //�õ�ר����Դ
                    List<TpCourseResource> tpCourseResourceList=getCourseResouceByCourseId(tc.getCourseid(),presource);
                    if(tpCourseResourceList==null||tpCourseResourceList.size()<1) //���û�����ݣ����˳�ѭ����
                        break;
                    //�õ���Դ��¼
                    for(TpCourseResource tcr:tpCourseResourceList){
                        if(tcr!=null&&tcr.getResid()!=null){
                            ResourceInfo resEntity=getResourceByResourceId(tcr.getResid());
                            //�õ���Դ�ļ�����¼���ļ���
                            if(resEntity!=null){
                                List<ResourceInfo> resList=new ArrayList<ResourceInfo>();
                                resList.add(resEntity);
                                if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",resList)){
                                    System.out.println("�쳣����д����Դ��¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                    return;
                                }
                                //�鿴��Դ�ļ��Ƿ���ڣ�������ڣ������ѹ��
                                String directoryName=UtilTool.getResourceMd5Directory(resEntity.getResid().toString());
                                String filepath=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+directoryName;
                                File tmpF=new File(filepath);
                                if(tmpF.exists()&&tmpF.isDirectory()){
                                    ZipUtil.genZip(filepath,directionPath,directoryName);//����Դ�ļ�����ZIP�ļ�����ĿĿ¼��
                                }
                            }
                        }
                    }
                    //д��XML�У�
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",tpCourseResourceList)){
                        System.out.println("�쳣����д��ר����Դ��¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                }
                //ѭ���õ�ר�����⼰ר������
                presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List<TpTopicInfo> topicList=getTpTopicList(tc.getCourseid(),presource);
                    if(topicList==null||topicList.size()<1)break;
                    //д��XML�У�
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",topicList)){
                        System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                    for(TpTopicInfo topic:topicList){
                        if(topic==null||topic.getTopicid()==null)continue;
                        //д��XML�ļ�,���õ���ע��� ���� ����ѹ��
                        path=MD5_NEW.getMD5Result(topic.getTopicid().toString());
                        folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                        }
                        //�õ�����
                        PageResult pagetheme=new PageResult();
                        pagetheme.setPageSize(_PageSize_Children);
                        pagetheme.setPageNo(0);
                        while(true){
                            pagetheme.setPageNo(pagetheme.getPageNo()+1);
                            List<TpTopicThemeInfo> themeList=getTpTopicThemeList(topic.getTopicid(),pagetheme);
                            if(themeList==null||themeList.size()<1)break;
                            //��¼XML�У��� �õ�  ���� ����ѹ���ļ�
                            for(TpTopicThemeInfo theme:themeList){
                                path= MD5_NEW.getMD5Result(theme.getThemeid().toString());
                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                f=new File(folderpath);
                                System.out.println(folderpath);
                                if(f.exists()&&f.isDirectory()){
                                    ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                                }
                            }
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",themeList)){
                                System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                return;
                            }
                        }
                    }

                }

                //ѭ���õ�ר���Ծ��¼  (�ݲ���)
//                presource.setPageNo(0);
//                while(true){
//                    presource.setPageNo(presource.getPageNo()+1);
//
//                }
                //ѭ���õ�ר�������¼
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
                        //��¼XML��,���õ����ͼƬ����ѹ��
                        path=MD5_NEW.getMD5Result(courseQues.getQuestionid().toString());
                        folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                        }
                        //��Questionд��XML��
                        List<QuestionInfo> quesList=new ArrayList<QuestionInfo>();
                        quesList.add(ques);
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",quesList)){
                            System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                            return;
                        }
                        //�õ�����Ϣ
                        List<QuestionOption> quesOptsList=getQuestionOption(courseQues.getQuestionid());
                        if(quesOptsList==null||quesOptsList.size()<1)
                            continue;
                        //�������Ϣ�����ݿ�
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",quesOptsList)){
                            System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                            return;
                        }
                        //�õ�����Ϣ�еĸ���
                        for(QuestionOption qoption:quesOptsList){
                            if(qoption!=null&&qoption.getRef()!=null){
                                //д��XML�ļ�,���õ���ע��� ���� ����ѹ��
                                path=MD5_NEW.getMD5Result(qoption.getQuestionid().toString());
                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                f=new File(folderpath);
                                if(f.exists()&&f.isDirectory()){
                                    ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                                }
                            }
                        }

                    }
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",courseQuestionList)){
                        System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                }
                //ѭ���õ�ר������
                    presource.setPageNo(0);
                while(true){
                    presource.setPageNo(presource.getPageNo()+1);
                    List<TpTaskInfo> taskList=this.getTaskList(tc.getCourseid(),presource);
                    if(taskList==null||taskList.size()<1)break;
                    for(TpTaskInfo tk:taskList){
                        if(tk==null)continue;
                        //��¼XML��,���õ����ͼƬ����ѹ��
                        path=MD5_NEW.getMD5Result(tk.getTaskid().toString());
                        folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                        }
                    }
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Courseid", tc.getCourseid()+"",taskList)){
                        System.out.println("�쳣����д��ר��̲ļ�¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                }
            }
            //����ZIP�ĵ�

            ZipUtil.genZip(dataFileParent, directionPath, firstDirectory + "_data_" + presult.getPageNo());
            //���ĵ�·��д��TXT�н����ϴ�
            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data_"+presult.getPageNo()+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
            //��ʼ�����ļ����д���
            UtilTool.uploadResourceToTotalSchool(uploadFileTxt);//�ϴ������ļ��洢��txt�ĵ�
            //Ȼ��ɾ��Xml�ļ�
            File tmp=new File(writeUrl);
            if(tmp.exists()&&tmp.isFile()){
                System.gc();    //�����ڻ��գ���ֹ�ļ�ռ�ã�ɾ��ʧ��!
                tmp.delete();
            }
        }

        //��������ݣ�����ýӿ�
        if(ishasData){
            //�õ�key
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
                System.out.println("�쳣����school.txt��û�з��ּ��ܹ���!����ϵ��У��ؿͷ���Ա!");
                //���������Ϣ
                return;
            }
            String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
            totalSchoolUrl+="/synchroCourse";
            String params="m=tbjxpt&key="+content.toString()+"&resolveFilename="+firstDirectory+"_data.zip";

            //������У�ӿڣ������ļ�
            if(ShareCourseUtil.sendCoursePathToTotalSchool(totalSchoolUrl,params)){
              //  ��ʼ�������ݿ⣬�����Ѿ�ͬ������Դ
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
     * �õ�����ѡ���б�
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
     * �õ������б�
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
     * �õ�������Ϣ
     * @param questionid ����ID
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
     * �õ�ר������
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
     * �õ�ר����������
     * @param topicid ר��ID
     * @param presult
     * @return
     */
    private List<TpTopicThemeInfo> getTpTopicThemeList(Long topicid,PageResult presult){
        if(topicid==null)return null;
        ITpTopicThemeManager topicManager=(TpTopicThemeManager)SpringBeanUtil.getBean("tpTopicThemeManager");
        TpTopicThemeInfo  theme=new TpTopicThemeInfo();
        theme.setTopicid(topicid);
        theme.setSelectType(3);//1:���� text  2:��text  3:������
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
     * �õ�ר������
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
     * �õ�ר����̲����ӱ���Ϣ
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
     * �õ�ר�������Ϣ
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
     * �õ�ר����Դ��Ϣ
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
     * �õ���Դ��Ϣ��¼
     * @return
     */
    private ResourceInfo getResourceByResourceId(Long resid){
        if(resid==null)return null;

        IResourceManager resourcemanager=(ResourceManager)SpringBeanUtil.getBean("resourceManager");
        ResourceInfo resEntity=new ResourceInfo();
        resEntity.setResid(resid);
        resEntity.setResstatus(-3);//resstatus<>3 û�б�ɾ��
        resEntity.setResdegree(3);//3:������Դ
        resEntity.setNetsharestatus(-1);//���ң�δͬ��
        //һ����¼
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<ResourceInfo> resList=resourcemanager.getList(resEntity,presult);
        if(resList==null||resList.size()<1)return null;
        return resList.get(0);
    }

}


/**
 * ShareCourse������
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
        } catch (Exception e) {			// �쳣��ʾ
            System.out.println("�쳣����!TOTALSCHOOLδ��Ӧ!");
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
                System.out.println("�쳣����!");
                return false;
            }
        }else if(code==404){
            // ��ʾ ����
            System.out.println("�쳣����!404��������ϵ������Ա!");
            return false;
        }else if(code==500){
            System.out.println("�쳣����!500��������ϵ������Ա!");
            return false;
        }
        String returnContent=null;
        try {
            returnContent=new String(stringBuffer.toString().getBytes("gbk"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //ת����JSON
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
     * д��XML�ļ���
     * @param filepath  XML�ļ�·��
     * @param t   Ҫд���ʵ�� Ҫд���ʵ��
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