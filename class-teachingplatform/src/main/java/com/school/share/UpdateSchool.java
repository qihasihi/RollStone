package com.school.share;

import com.school.entity.SchoolInfo;
import com.school.entity.resource.score.SchoolScoreRank;
import com.school.manager.SchoolManager;
import com.school.manager.inter.ISchoolManager;
import com.school.manager.resource.score.SchoolScoreRankManager;
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
 * ��У��Ϣ�����
 * Created by zhengzhou on 14-4-7.
 */
public class UpdateSchool  extends TimerTask {
    private ServletContext request;
    public UpdateSchool(ServletContext application){
        //��УID
        this.request=application;
    }
    //��¼Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    @Override
    public void run() {
        //��������һ��ִ�У���������ִ�У���ֱ�ӷ���
        if(!SynchroUtil._updateSchool){
            SynchroUtil._updateSchool=true;
            logger.info("--------------------��У��Ϣ���������ִ�У���ִ��-------------------");
            return;
        }
        logger.info("--------------------��У��Ϣ���������ִ��-------------------");
        // Date currentDate=new Date();//��¼��ǰ���µ�ʱ���
        String key=UpdateSchoolUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }
        String[] schoolUrl= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_SCHOOL_INFO").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+schoolUrl[0];
        String params=schoolUrl[1]+"&key="+key;
        //��ʼ���󲢷��ظ�����Դ���ص�ַ
        Map<String,Object> fileLocaMap=UpdateSchoolUtil.sendPostURL(postFileUrl, params);


        String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

        if(fileLocaMap==null||fileLocaMap.get("type")==null||!fileLocaMap.get("type").toString().trim().equals("success")){
            //��¼�쳣������־��
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
        //System.out.println(updateFileLocaPath);
        //�����ļ�XMLpath����ӦĿ¼��
        String fileName=UpdateSchoolUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateSchoolInfo/";
        String toPath=toPathFolder+fileName;
        if(!UpdateSchoolUtil.downLoadZipFile(updateFileLocaPath,toPath)){
            //�����ļ�ʧ��.��¼����־�С�
            System.out.println("copy "+fileName+" error");
        }
        //��ʼ��ѹ�����������ļ�
        try {
            ZipUtil.unzip(toPath, toPathFolder+"/"+fileName.substring(0,fileName.lastIndexOf("."))+"/");
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
            System.out.println("û�з��ֿ��Խ������ļ�����û�з�У���£���¼�����ݿ���");return;
        }
        //��У����
        ISchoolManager schoolManager=(SchoolManager) SpringBeanUtil.getBean("schoolManager");
        //ѭ������folderFiles

        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=null;
//        //ɾ��ԭ�еļ�¼
//        SchoolInfo entity=new SchoolInfo();
//        objList=schoolManager.getDeleteSql(entity,sqlbuilder);
//        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
//            sqlArrayList.add(sqlbuilder.toString());
//            objArrayList.add(objList);
//        }


        boolean hasData=false,isError=false;
        for(File f:folderFiles){
            if(f!=null&&f.exists()&&f.isFile()){
                System.out.println(f.getPath());
                //��XML�еõ�ѧУ��������
                List<SchoolInfo> ssrList=UpdateSchoolUtil.getSchoolByXml(f.getPath());
                if(ssrList==null||ssrList.size()<1)continue;
                for(SchoolInfo sr:ssrList){
                    if(sr==null)continue;
                    hasData=true;
                    if(sr.getEnable()==1){
                        //ɾ���÷�У
                        SchoolInfo delSchool=new SchoolInfo();
                        delSchool.setSchoolid(sr.getSchoolid());
                        sqlbuilder=new StringBuilder();
                        objList=schoolManager.getDeleteSql(delSchool,sqlbuilder);
                        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                            sqlArrayList.add(sqlbuilder.toString());
                            objArrayList.add(objList);
                        }
                        continue;
                    }
                    sqlbuilder=new StringBuilder();
                    objList=schoolManager.getSaveSql(sr, sqlbuilder);
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        sqlArrayList.add(sqlbuilder.toString());
                        objArrayList.add(objList);
                    }
                }
                //�������
                if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
                    if(!schoolManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                        isError=true;
                        System.out.println("�쳣��������ִ��SQL���ʧ��!filename="+f.getPath());
                    }
                    objArrayList=new ArrayList<List<Object>>();
                    sqlArrayList=new ArrayList<String>();
                }else{
                    isError=true;
                    System.out.println("�쳣����û�п�ִ�е�SQL���!");
                }
                if(isError){
                    break;
                }
            }
        }
        System.out.println("���·�У���!");
        if(!hasData){
            System.out.println("û�пɸ��µ���Ϣ��");
        }
        if(!isError){//ɾ���ļ�
            System.gc();
            try {
                //ɾ���ļ���
                FileUtils.deleteDirectory(new File(toPathFolder));
                //ɾ��toPath
                new File(toPath).delete();
            } catch (IOException e) {
            }
        }
    }
}

/**
 *
 */
class UpdateSchoolUtil{

    /**
     * �õ���У��Ϣ
     * @param fullpath
     * @return
     */
    public static List<SchoolInfo> getSchoolByXml(String fullpath){
        if(fullpath==null)return null;
        List list= OperateXMLUtil.findXml(fullpath, "//table //row", false);
        List<SchoolInfo> ssrList=new ArrayList<SchoolInfo>();
        if(list!=null){
            for (Object mapObj : list) {
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                if(courseMap!=null){
                    SchoolInfo ssr=new SchoolInfo();
                    if(courseMap.containsKey("Schoolid")&&courseMap.get("Schoolid")!=null
                            &&!courseMap.get("Schoolid").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setSchoolid(Long.parseLong(courseMap.get("Schoolid").toString()));
                    if(courseMap.containsKey("Name")&&courseMap.get("Name")!=null
                            &&!courseMap.get("Name").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setName(courseMap.get("Name").toString().trim());
                    if(courseMap.containsKey("Enable")&&courseMap.get("Enable")!=null
                            &&!courseMap.get("Enable").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setEnable(Integer.parseInt(courseMap.get("Enable").toString().trim()));
                    if(courseMap.containsKey("Ip")&&courseMap.get("Ip")!=null
                            &&!courseMap.get("Ip").toString().trim().toUpperCase().equals("NULL")){
                        ssr.setIp(courseMap.get("Ip").toString().trim());
                        if(ssr.getIp().lastIndexOf("/")!=ssr.getIp().length()-1){
                            ssr.setIp(ssr.getIp()+"/");
                        }
                    }
//                    if(courseMap.containsKey("Ctime")&&courseMap.get("Ctime")!=null
//                            &&!courseMap.get("Ctime").toString().trim().toUpperCase().equals("NULL"))
//                        ssr.setCtime(UtilcourseMap.get("ModelId").toString()));
                    ssrList.add(ssr);
                }
            }
        }
        return ssrList;
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
