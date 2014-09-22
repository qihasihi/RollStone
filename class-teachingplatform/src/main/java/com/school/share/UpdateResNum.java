package com.school.share;

import com.school.entity.DictionaryInfo;
import com.school.entity.resource.ResourceInfo;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.resource.ResourceManager;
import com.school.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * ���У�
 * CLICKS
 * ,COMMENTNUM
 * ,DOWNLOADNUM
 * ,STORENUM
 * ,PRAISENUM
 * ,RECOMENDNUM
 * ,REPORTNUM
 * Created by zhengzhou on 14-5-22.
 */
public class UpdateResNum extends TimerTask{
    private ServletContext request;
    public UpdateResNum(ServletContext application){
        this.request=application;
    }
    //��¼Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    @Override
    public void run() {
        //��������һ��ִ�У���������ִ�У���ֱ�ӷ���
        if(!SynchroUtil._updateResNum){
            SynchroUtil._updateResNum=true;
            logger.info("--------------------������Դ�����������ִ�У���ִ��-------------------");
            return;
        }
        logger.info("--------------------������Դ�����������ִ��-------------------");
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }

        String[] teamaterialLocal= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_NUM").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+teamaterialLocal[0];
        String params=teamaterialLocal[1]+"&key="+key;
        //��ʼ���󲢷��ظ�����Դ���ص�ַ
        Map<String,Object> fileLocaMap=UpdateResNumUtil.sendPostURL(postFileUrl,params);


        String updateFileLocaPath=null;//"http://localhost:8080/fileoperate/uploadfile/tmp/UpdateResNum/50000/updateResNum_1400752173844/updateResNum_1400752173844.zip";//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

//        Map<String,Object> fileLocaMap= UpdateCourseUtil.getUpdateCourse(key,ftime);
        if(fileLocaMap==null&&fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success")){
            //��¼�쳣������־��
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
//        //System.out.println(updateFileLocaPath);
//        //�����ļ�XMLpath����ӦĿ¼��
        String fileName=UpdateResNumUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateResNum/";
        String toPath=toPathFolder+fileName;
//        if(!UpdateResNumUtil.downLoadZipFile(updateFileLocaPath,toPath)){
//            //�����ļ�ʧ��.��¼����־�С�
//            System.out.println("copy "+fileName+" error");
//        }
        //��ʼ��ѹ�����������ļ�
        try {
            ZipUtil.unzip(toPath, toPathFolder + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + "/");
        } catch (Exception e) {
            e.printStackTrace();
            //��¼�쳣����ѹʧ�� �����¿�ʼ
            return;
        }
        //�õ�Ŀ¼�µ��ļ������н���
        File folder=new File(toPathFolder+"/"+fileName.substring(0,fileName.lastIndexOf("."))+"/");
        //�õ�����������ļ�
        File[] folderFiles=folder.listFiles();
        //ѭ�����н���
        if(folderFiles==null||folderFiles.length<1){
            System.out.println("û�з��ֿ��Խ������ļ�����û����Դ��Ϣ���£���¼�����ݿ���");return;
        }
        boolean istrue=true;

        for(int i=0;i<folderFiles.length;i++){
            File tmp=folderFiles[i];
            if(tmp!=null&&tmp.exists()){
                StringBuilder sqlbuilder=null;
                List<Object> objList=null;

                List<List<Object>> objArrayList=new ArrayList<List<Object>>();
                List<String> sqlArrayList=new ArrayList<String>();


                List<ResourceInfo> tVsionList=UpdateResNumUtil.getResourceList(tmp.getPath());
                if(tVsionList==null||tVsionList.size()<1)continue;
                for(ResourceInfo tv:tVsionList){
                    if(tv!=null){
                        sqlbuilder=new StringBuilder();
                        objList=UpdateResNumUtil.getSynchroSql(tv, sqlbuilder);
                        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                            objArrayList.add(objList);
                            sqlArrayList.add(sqlbuilder.toString());
                        }
                    }
                }
                if(objArrayList.size()>0&&sqlArrayList.size()>0&&objArrayList.size()==sqlArrayList.size()){
                    istrue=UpdateResNumUtil.doExcetueArrayProc(sqlArrayList,objArrayList);
                    if(!istrue){
                        System.out.println("���½̲���Ϣʧ��!��¼��־");
                        istrue=false;
                        break;
                    }
                }
                if(!istrue)break;
            }
        }
        //ɾ�������ļ�
        System.gc();
        try {
            FileUtils.deleteDirectory(new File(toPathFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(istrue){
            System.out.println("������Դ�����ɹ�!");
        }else
            System.out.println("������Դ����ʧ��!");
    }
}
class UpdateResNumUtil{

    /**
     * ����XML�ļ����õ�ʵ��
     * @param path
     * @return
     */
    public static List<ResourceInfo> getResourceList(String path){
        if(path==null)return null;
        //�õ�Ҫ�������ʵ��  (����������)
        List list= OperateXMLUtil.findXml(path, "//table //row", false);
        if(list==null||list.size()<1)return null;
        List<ResourceInfo> returnList=new ArrayList<ResourceInfo>();
        for (Object mapObj : list) {
            if(mapObj!=null){
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                ResourceInfo entity=new ResourceInfo();
                if(courseMap.containsKey("RES_ID")||courseMap.get("RES_ID")!=null)
                    entity.setResid(Long.parseLong(courseMap.get("RES_ID").toString()));
                if(courseMap.containsKey("VIEW_NUM")||courseMap.get("VIEW_NUM")!=null)
                    entity.setClicks(Integer.parseInt(courseMap.get("VIEW_NUM").toString()));
                if(courseMap.containsKey("COMMENT_NUM")||courseMap.get("COMMENT_NUM")!=null)
                    entity.setCommentnum(Integer.parseInt(courseMap.get("COMMENT_NUM").toString()));
                if(courseMap.containsKey("COLLECT_NUM")||courseMap.get("COLLECT_NUM")!=null)
                    entity.setStorenum(Integer.parseInt(courseMap.get("COLLECT_NUM").toString()));
                if(courseMap.containsKey("PRAISE_NUM")||courseMap.get("PRAISE_NUM")!=null)
                    entity.setPraisenum(Integer.parseInt(courseMap.get("PRAISE_NUM").toString()));
                if(courseMap.containsKey("RECOMMEND_NUM")||courseMap.get("RECOMMEND_NUM")!=null)
                    entity.setRecomendnum(Integer.parseInt(courseMap.get("RECOMMEND_NUM").toString()));
                if(courseMap.containsKey("REPORT_NUM")||courseMap.get("REPORT_NUM")!=null)
                    entity.setReportnum(Integer.parseInt(courseMap.get("REPORT_NUM").toString()));
                if(courseMap.containsKey("DOWN_NUM")||courseMap.get("DOWN_NUM")!=null)
                    entity.setDownloadnum(Integer.parseInt(courseMap.get("DOWN_NUM").toString()));
                returnList.add(entity);
            }
        }
        return returnList;
    }
    /**
     * �õ�ͬ��SQL���
     * @param res
     * @param sqlbuilder
     * @return
     */
    public static List<Object> getSynchroSql(ResourceInfo res,StringBuilder sqlbuilder){
        IResourceManager resourceManager=SpringBeanUtil.getBean("resourceManager", ResourceManager.class);
        return resourceManager.getUpdateResNum(res, sqlbuilder);
    }
    /**
     * ����ִ��SQL���
     * @param sqlArrayList
     * @param objArrayList
     * @return
     */
    public static boolean doExcetueArrayProc(List<String> sqlArrayList,List<List<Object>> objArrayList){
        IResourceManager resourceManager=SpringBeanUtil.getBean("resourceManager", ResourceManager.class);
        return resourceManager.doExcetueArrayProc(sqlArrayList, objArrayList);
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
     * ����link�õ�filename
     * @param locaPath
     * @return
     */
    public static String getFileName(String locaPath){
        if(locaPath==null)return null;
        return locaPath.substring(locaPath.lastIndexOf("/")+1);
    }
    /**
     * ����ZIP��ָ��Ŀ¼��
     * @param zipLocaPath
     * @return
     */
    public static boolean downLoadZipFile(String zipLocaPath,String toPath){
        //��֤����
        if(zipLocaPath==null||zipLocaPath.toString().trim().length()<1
                ||toPath==null||toPath.toString().trim().length()<1){
            return false;
        }
        //��ʼ�����ļ�
        URL url = null;
        InputStream in =null;
        try {
            url = new URL(zipLocaPath);
            in= url.openStream();
            File dir = new File(toPath);
            dir=new File(dir.getParent());
            if(!dir.exists()) //�����ڣ��򴴽�
                dir.mkdirs();
            File tofile = new File(toPath);
            //��ʼ�����ļ�
            FileOutputStream out = new FileOutputStream(tofile);
            Streams.copy(in, out, true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
