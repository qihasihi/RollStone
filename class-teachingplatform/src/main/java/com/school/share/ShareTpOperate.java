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
 * �ϴ�TpOperateInfo��¼Ԫ��
 * Created by zhengzhou on 14-3-12.
 */
public class ShareTpOperate extends TimerTask {
    private ServletContext request;
    public ShareTpOperate(ServletContext application){
        this.request=application;
        //��УID
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }

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
    //�ϴ����ļ�����
    private final String _UploadFileName="uploadFile.txt";
    //��¼Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    //�ϴ�����
    public void run(){
        //��������һ��ִ�У���������ִ�У���ֱ�ӷ���
        if(!SynchroUtil._shareTpOperate){
            SynchroUtil._shareTpOperate=true;
            logger.info("--------------------������Դ��������ִ�У���ִ��-------------------");
            return;
        }
        logger.info("--------------------������Դ��������ִ��-------------------");
        String firstDirectory= MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(_Schoolid) + UtilTool.utilproperty.getProperty("TO_ETT_KEY").toString());
        String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/shareTpOperate/"; //��·��
        String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();    //xml�ļ����ļ���
        String directionPath=parentDirectory+firstDirectory+"/";
        String dataFileParent=directionPath+"data/";

        String xmlPathUrl=dataFileParent+"/"+filename;

        String xmlTemplateURL=parentDirectory+"../"+_Template_name;
        //�õ���ǰ�ķ�УKEY
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }
        String ftime=null;//�ϴ��ϴ���ʱ��
        //�õ���һ�θ��µ�ʱ��
        IDictionaryManager dictionaryManager=(DictionaryManager) SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dictionaryManager.getDictionaryByType("UP_OPERATE_FTIME");//�ϴ��ϴ���ʱ��
        DictionaryInfo fupdateDic=null;
        if(dicList!=null&&dicList.size()>0){
            ftime=dicList.get(0).getDictionaryvalue();
            fupdateDic=dicList.get(0);
        }
        Date currentDate=new Date();
        //��¼��Ҫ�ϴ����ļ�����·��
        String uploadFileTxt=directionPath+_UploadFileName;
        //ģ��XML�ļ����Ƽ�·��
        File fromF=new File(xmlTemplateURL);
        if(!fromF.exists()){
            //��¼������Ϣ
            System.out.println("��У�쳣!û�з����ϴ�ģ��!");
            return;
        }

        //��ҳ��ѯ
        PageResult presult=new PageResult();
        presult.setPageNo(0);
        presult.setPageSize(_PageSize);

         boolean ishasData=false;
        while(true){
             presult.setPageNo(presult.getPageNo() + 1);
            //�õ�����
            List<TpOperateInfo> operateList=ShareTpOperateUtil.getOperateByFtime(ftime,presult);
            if(operateList==null||operateList.size()<1)break;
            String writeUrl=xmlPathUrl;
            writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))+"_"+presult.getPageNo()+writeUrl.substring(writeUrl.lastIndexOf("."));
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

            //TpOperaetList����
            for(TpOperateInfo op:operateList){
                if(op==null)continue;
                //д��XML
                if(!UtilTool.addDateToXml(writeUrl, op)){
                    System.out.println("�쳣����д��ר����XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                    return;
                }
                //�������� 1��ר�� 2��ר������ 3��ר����Դ 4��ר������ 5��ר������ 6��ר������
//                if(op.getDatatype()==1){
//
//                }else
                if(op.getDatatype()==2){//ר������
                    List<TpTaskInfo> taskList=ShareTpOperateUtil.getTpTaskList(op.getTargetid(),null);
                    if(taskList==null||taskList.size()<1)continue;
                    taskList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", taskList)){
                        System.out.println("�쳣����д��ר�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                    //��¼XML��,���õ����ͼƬ����ѹ��
                   String path=MD5_NEW.getMD5Result(taskList.get(0).getTaskid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                   File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                    }
                    TpTaskInfo tk=taskList.get(0);
                    if(tk!=null)
                        if(tk.getTasktype()==1){// 1����Դѧϰ  2������  3�� ����
                            ResourceInfo resEntity=ShareTpOperateUtil.getResourceByResourceId(tk.getTaskvalueid());
                            if(resEntity==null)continue;
                            resEntity.setCourseid(op.getCourseid());
                            List<ResourceInfo> reslist=new ArrayList<ResourceInfo>();
                            reslist.add(resEntity);
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", reslist)){
                                System.out.println("�쳣����д��ר�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                return;
                            }
                            //��¼XML��,���õ����ͼƬ����ѹ��
                             path=MD5_NEW.getMD5Result(resEntity.getResid().toString());
                              folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                             f=new File(folderpath);
                            if(f.exists()&&f.isDirectory()){
                                ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                            }
                        }else if(tk.getTasktype()==2){
                            List<TpTopicInfo> tpcList=ShareTpOperateUtil.getTpTopicList(tk.getTaskvalueid(),null);
                            if(tpcList==null||tpcList.size()<1)continue;
                            tpcList.get(0).setCourseid(op.getCourseid());
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", tpcList)){
                                System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                return;
                            }
                            //��¼XML��,���õ����ͼƬ����ѹ��
                                path=MD5_NEW.getMD5Result(tpcList.get(0).getTopicid().toString());
                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                f=new File(folderpath);
                                if(f.exists()&&f.isDirectory()){
                                    ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                              }
                            //�õ�����ר���µ�����
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
                                    //��¼XML��,���õ����ͼƬ����ѹ��
                                    path=MD5_NEW.getMD5Result(theme.getThemeid().toString());
                                     folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                                     f=new File(folderpath);
                                    if(f.exists()&&f.isDirectory()){
                                        ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                                    }
                                }
                                if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", topicthemeList)){
                                    System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                    return;
                                }
//                                //��¼XML��,���õ����ͼƬ����ѹ��
//                                path=MD5_NEW.getMD5Result(topicthemeList.get(0).getTopicid().toString());
//                                folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
//                                f=new File(folderpath);
//                                if(f.exists()&&f.isDirectory()){
//                                    ZipUtil.genZip(folderpath,directionPath,path);    //����zip
//                                    UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
//                                }
                            }
                        }else if(tk.getTasktype()==3){//����
                            QuestionInfo quesEntity=ShareTpOperateUtil.getQuestionInfo(tk.getTaskvalueid());
                            if(quesEntity==null)continue;
                            List<QuestionInfo> quesList=new ArrayList<QuestionInfo>();
                            quesList.add(quesEntity);
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                                System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                return;
                            }
                            //��¼XML��,���õ����ͼƬ����ѹ��
                            path=MD5_NEW.getMD5Result(quesEntity.getQuestionid().toString());
                             folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                             f=new File(folderpath);
                            if(f.exists()&&f.isDirectory()){
                                ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                            }

                            //�õ�����ѡ��
                            List<QuestionOption> quesOptsList=ShareTpOperateUtil.getQuestionOption(quesEntity.getQuestionid());
                            if(quesOptsList==null||quesOptsList.size()<1)continue;
                            if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                                System.out.println("�쳣����д������ѡ����XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                                return;
                            }
                        }

                }else if(op.getDatatype()==3){//ר����Դ
                    ResourceInfo resEntity=ShareTpOperateUtil.getResourceByResourceId(op.getTargetid());
                    if(resEntity==null)continue;
                    resEntity.setCourseid(op.getCourseid());
                    List<ResourceInfo> reslist=new ArrayList<ResourceInfo>();
                    reslist.add(resEntity);
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", reslist)){
                        System.out.println("�쳣����д��ר�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                    //��¼XML��,���õ����ͼƬ����ѹ��
                    String path=MD5_NEW.getMD5Result(resEntity.getResid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                    File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                    }
                }else if(op.getDatatype()==4){//ר������
                    List<TpTopicInfo> tpcList=ShareTpOperateUtil.getTpTopicList(op.getTargetid(),null);
                    if(tpcList==null||tpcList.size()<1)continue;
                    tpcList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", tpcList)){
                        System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                    //�õ�����ר���µ�����
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
                            //��¼XML��,���õ����ͼƬ����ѹ��
                            String path=MD5_NEW.getMD5Result(theme.getThemeid().toString());
                            String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                            File f=new File(folderpath);
                            if(f.exists()&&f.isDirectory()){
                                ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                                UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                            }
                        }
                        if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", topicthemeList)){
                            System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                            return;
                        }

                        //��¼XML��,���õ����ͼƬ����ѹ��
                        String path=MD5_NEW.getMD5Result(topicthemeList.get(0).getTopicid().toString());
                        String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                        File f=new File(folderpath);
                        if(f.exists()&&f.isDirectory()){
                            ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                        }
                    }

                }else if(op.getDatatype()==5){//ר������
                    List<TpTopicThemeInfo> topicthemeList=ShareTpOperateUtil.getTpTopicThemeList(op.getTargetid(),null,null);
                    if(topicthemeList==null||topicthemeList.size()<1)continue;
                    topicthemeList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", topicthemeList)){
                        System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                    //��¼XML��,���õ����ͼƬ����ѹ��
                    String path=MD5_NEW.getMD5Result(topicthemeList.get(0).getTopicid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                    File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                    }
                    List<TpTopicInfo> tpcList=ShareTpOperateUtil.getTpTopicList(topicthemeList.get(0).getTopicid(),null);
                    if(tpcList==null||tpcList.size()<1)continue;
                    tpcList.get(0).setCourseid(op.getCourseid());
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", tpcList)){
                        System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }

                }else if(op.getDatatype()==6){//ר������
                    QuestionInfo quesEntity=ShareTpOperateUtil.getQuestionInfo(op.getTargetid());
                    if(quesEntity==null)continue;
                    List<QuestionInfo> quesList=new ArrayList<QuestionInfo>();
                    quesList.add(quesEntity);
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                        System.out.println("�쳣����д�������¼��XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                    //��¼XML��,���õ����ͼƬ����ѹ��
                    String path=MD5_NEW.getMD5Result(quesEntity.getQuestionid().toString());
                    String  folderpath=request.getRealPath("/")+"uploadfile/"+path+"/";
                    File f=new File(folderpath);
                    if(f.exists()&&f.isDirectory()){
                        ZipUtil.genZip(folderpath,directionPath,path);    //����zip
                        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+path+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
                    }

                    //�õ�����ѡ��
                    List<QuestionOption> quesOptsList=ShareTpOperateUtil.getQuestionOption(quesEntity.getQuestionid());
                    if(quesOptsList==null||quesOptsList.size()<1)continue;
                    if(!OperateXMLUtil.updateEntityToXml(writeUrl, "Ref", op.getRef() + "", quesList)){
                        System.out.println("�쳣����д������ѡ����XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                        return;
                    }
                }
            }


            //���ĵ�·��д��TXT�н����ϴ�
         //   UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data_"+presult.getPageNo()+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�

            //Ȼ��ɾ��Xml�ļ�
//            File tmp=new File(writeUrl);
//            if(tmp.exists()&&tmp.isFile()){
//                System.gc();    //�����ڻ��գ���ֹ�ļ�ռ�ã�ɾ��ʧ��!
//                tmp.delete();
//            }
        }
        if(!ishasData){//���û�����ݣ����¼��ǰʱ��
            if(fupdateDic==null){
                fupdateDic=new DictionaryInfo();
                fupdateDic.setDictionarytype("UP_OPERATE_FTIME");
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                fupdateDic.setDictionaryname("ר�����Ԫ�ر��ϴθ���ʱ��!");
                dictionaryManager.doSave(fupdateDic);
            }else{
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                dictionaryManager.doUpdate(fupdateDic);
            }
        }else{
            //��ʼ�����ļ����д���
            UtilTool.uploadResourceToTotalSchool(uploadFileTxt);//�ϴ������ļ��洢��txt�ĵ�
            //����ZIP�ĵ�
             ZipUtil.genZip(dataFileParent,directionPath,firstDirectory+"_data");

            UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data.zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�


            //���ýӿ�
            String[] urlObjArray=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_OPERATE").toString().split("\\?");
            String url_operate_url=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+urlObjArray[0];
            String params=urlObjArray[0]+"&key="+key+"&resolveFileName="+firstDirectory+"_data.zip";
            Map<String,Object> sendMap= ShareTpOperateUtil.sendPostURL(url_operate_url,params);
            if(sendMap==null){
                System.out.println("�쳣���󣬵��ý��սӿڴ���!  ԭ��δ֪!");
            }else{
                boolean istrue=false;
                String type=sendMap.get("type").toString();
                String msg=sendMap.get("msg").toString();
                if(type.trim().equals("success")){
                    if(fupdateDic==null){
                        fupdateDic=new DictionaryInfo();
                        fupdateDic.setDictionarytype("UP_OPERATE_FTIME");
                        fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                        fupdateDic.setDictionaryname("ר�����Ԫ�ر��ϴθ���ʱ��!");
                        istrue=dictionaryManager.doSave(fupdateDic);
                    }else{
                        fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                        istrue=dictionaryManager.doUpdate(fupdateDic);
                    }
                    if(istrue){
                        System.out.println(fupdateDic.getDictionaryvalue()+"  �ϴ�ר�������¼�ɹ�!");
                    }else
                        System.out.println(fupdateDic.getDictionaryvalue()+" ���󣬼�¼���θ���ʱ�����");
                }else{
                    //��¼������־
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
     * �õ�����ѡ���б�
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
     * �õ�������Ϣ
     * @param questionid ����ID
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
     * �õ�ר������
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
     * �õ�ר����������
     * @param themeid ר��ID
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
        theme.setSelectType(3);//1:���� text  2:��text  3:������
        return topicManager.getList(theme,presult);
    }
    /**
     * �õ�ר������
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
     * �õ�ר����̲����ӱ���Ϣ
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
     * �õ�ר�������Ϣ
     * @param ftime  �ϴθ��µ�ʱ�� YYYY-MM-DD hh24:mi:ss��ʽ
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
     * �õ�ר����Դ��Ϣ
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
     * �õ���Դ��Ϣ��¼
     * @return
     */
    public static ResourceInfo getResourceByResourceId(Long resid){
        if(resid==null)return null;


        IResourceManager resourcemanager=(ResourceManager)SpringBeanUtil.getBean("resourceManager");
        ResourceInfo resEntity=new ResourceInfo();
        resEntity.setResid(resid);
        //һ����¼
        PageResult presult=new PageResult();
        presult.setPageNo(1);
        presult.setPageSize(1);
        List<ResourceInfo> resList=resourcemanager.getList(resEntity,presult);
        if(resList==null||resList.size()<1)return null;
        return resList.get(0);
    }

    /**
     *��̨���ýӿ�
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
        } catch (Exception e) {			// �쳣��ʾ
            System.out.println("�쳣����!TOTALSCHOOLδ��Ӧ!");
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
                System.out.println("�쳣����!");
                e.printStackTrace();;
                return null;
            }
        }else if(code==404){
            // ��ʾ ����
            System.out.println("�쳣����!404��������ϵ������Ա!");
            return null;
        }else if(code==500){
            System.out.println("�쳣����!500��������ϵ������Ա!");
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
        //ת����JSON
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
     * �õ���ǰ��У��KEYֵ
     * @param request
     * @return
     */
    public static String getCurrentSchoolKey(ServletContext request){
        //�õ��ļ��е�KEY
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
