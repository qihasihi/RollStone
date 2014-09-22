package com.school.share;

import com.school.entity.resource.score.SchoolScoreRank;
import com.school.manager.inter.resource.score.ISchoolScoreRankManager;
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
 * ���·�У�������У�ÿ�����
 * Created by zhengzhou on 14-3-18.
 */
public class UpdateSchoolScoreRank extends TimerTask {

    private ServletContext request;
    public UpdateSchoolScoreRank(ServletContext application){
        this.request=application;
    }
    /**
     * ���ɵ�ģ��XML
     */
    private final static String _Template_name="ShareTemplate.xml";

    //��¼Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    @Override
    public void run() {
        //��������һ��ִ�У���������ִ�У���ֱ�ӷ���
        if(!SynchroUtil._updateSchoolScoreRank){
            SynchroUtil._updateSchoolScoreRank=true;
            logger.info("-------------------- ���·�У������������ִ�У���ִ��-------------------");
            return;
        }
        logger.info("-------------------- ���·�У������������ִ��-------------------");
       // Date currentDate=new Date();//��¼��ǰ���µ�ʱ���
        String key=UpdateSchoolScoreRankUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }
        String[] schoolUrl= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_SCHOOL_SCORE_RANK").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+schoolUrl[0];
        String params=schoolUrl[1]+"&key="+key;
        //��ʼ���󲢷��ظ�����Դ���ص�ַ
        Map<String,Object> fileLocaMap=UpdateSchoolScoreRankUtil.sendPostURL(postFileUrl, params);


        String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

        if(fileLocaMap==null||fileLocaMap.get("type")==null||!fileLocaMap.get("type").toString().trim().equals("success")){
            //��¼�쳣������־��
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
        //System.out.println(updateFileLocaPath);
        //�����ļ�XMLpath����ӦĿ¼��
        String fileName=UpdateSchoolScoreRankUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateSchoolScoreRank/";
        String toPath=toPathFolder+fileName;
        if(!UpdateSchoolScoreRankUtil.downLoadZipFile(updateFileLocaPath,toPath)){
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
            System.out.println("û�з��ֿ��Խ������ļ�����û�а汾���£���¼�����ݿ���");return;
        }
        //��У����
        ISchoolScoreRankManager schoolScoreRankManager=(SchoolScoreRankManager) SpringBeanUtil.getBean("schoolScoreRankManager");
        //ѭ������folderFiles

        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=null;
        //ɾ��ԭ�еļ�¼
        SchoolScoreRank entity=new SchoolScoreRank();
        entity.setOperatetype(1);
        objList=schoolScoreRankManager.getDeleteSql(entity,sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }


        boolean hasData=false,isError=false;
        for(File f:folderFiles){
            if(f!=null&&f.exists()&&f.isFile()){
                //��XML�еõ�ѧУ��������
                List<SchoolScoreRank> ssrList=UpdateSchoolScoreRankUtil.getSchoolScoreRankByXml(f.getPath());
                if(ssrList==null||ssrList.size()<1)continue;
                //ɾ��
                sqlbuilder=new StringBuilder();

                for(SchoolScoreRank sr:ssrList){
                    if(sr==null)continue;
                    hasData=true;
                    sqlbuilder=new StringBuilder();
                    sr.setOperatetype(1);
                    objList=schoolScoreRankManager.getSynchroSql(sr,sqlbuilder);
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        sqlArrayList.add(sqlbuilder.toString());
                        objArrayList.add(objList);
                    }
                }
                //�������
                if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
                    if(!schoolScoreRankManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
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
        System.out.println("���·�У�������!");
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
 * ��У�������й�����
 */
class UpdateSchoolScoreRankUtil{

    /**
     * ��XML�еõ�����
     * @param xmlpath xml�ľ��Ե�ַ
     * @return
     */
    public static List<SchoolScoreRank> getSchoolScoreRankByXml(String xmlpath){
        if(xmlpath==null)return null;
        List list= OperateXMLUtil.findXml(xmlpath, "//table //row", false);
        List<SchoolScoreRank> ssrList=new ArrayList<SchoolScoreRank>();
        if(list!=null){
            for (Object mapObj : list) {
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                if(courseMap!=null){
                    SchoolScoreRank ssr=new SchoolScoreRank();
                    if(courseMap.containsKey("Score")&&courseMap.get("Score")!=null
                            &&!courseMap.get("Score").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setScore(Float.parseFloat(courseMap.get("Score").toString()));
                    if(courseMap.containsKey("Ctime")&&courseMap.get("Ctime")!=null
                            &&!courseMap.get("Ctime").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setCtime(UtilTool.StringConvertToDate(courseMap.get("Ctime").toString().trim()));
                    if(courseMap.containsKey("ModelId")&&courseMap.get("ModelId")!=null
                            &&!courseMap.get("ModelId").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setModelid(Long.parseLong(courseMap.get("ModelId").toString()));
                    if(courseMap.containsKey("Rank")&&courseMap.get("Rank")!=null
                            &&!courseMap.get("Rank").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setRank(Long.parseLong(courseMap.get("Rank").toString()));
                    if(courseMap.containsKey("SchoolId")&&courseMap.get("SchoolId")!=null
                            &&!courseMap.get("SchoolId").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setSchoolid(Long.parseLong(courseMap.get("SchoolId").toString()));
                    if(courseMap.containsKey("TypeId")&&courseMap.get("TypeId")!=null
                            &&!courseMap.get("TypeId").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setTypeid(Long.parseLong(courseMap.get("TypeId").toString()));
                    if(courseMap.containsKey("SchoolName")&&courseMap.get("SchoolName")!=null
                            &&!courseMap.get("SchoolName").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setSchoolname(courseMap.get("SchoolName").toString());
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
