package com.school.share;

import com.school.dao.inter.resource.score.IUserScoreRankDAO;
import com.school.entity.resource.score.SchoolScoreRank;
import com.school.entity.resource.score.UserScoreRank;
import com.school.manager.inter.resource.score.IUserScoreRankManager;
import com.school.manager.resource.score.UserScoreRankManager;
import com.school.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.util.Streams;

import javax.servlet.ServletContext;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * ͬ���û�����������ʵ����
 * Created by zhengzhou on 14-3-18.
 */
public class UpdateUserScoreRankRealname extends TimerTask {
    private  String _Schoolid=null;
    private ServletContext request;
    public UpdateUserScoreRankRealname(ServletContext request){
        this.request=request;
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
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
        if(!SynchroUtil._updateUserScoreRankRealname){
            SynchroUtil._updateUserScoreRankRealname=true;
            logger.info("-------------------- ͬ���û�����������ʵ��������ִ�У���ִ��-------------------");
            return;
        }
        logger.info("-------------------- ͬ���û�����������ʵ��������ִ��-------------------");
        // Date currentDate=new Date();//��¼��ǰ���µ�ʱ���
        String key=UpdateSchoolScoreRankUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }
        String[] schoolUrl= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_USER_SCORE_RANK_GET_USERID").toString().split("\\?");
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
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateUserScoreRank/UserId/";
        String toPath=toPathFolder+fileName;
        if(!UpdateSchoolScoreRankUtil.downLoadZipFile(updateFileLocaPath,toPath)){
            //�����ļ�ʧ��.��¼����־�С�
            System.out.println("copy "+fileName+" error");
        }

        //��ʼ��ѹ�����������ļ�
        try {
            ZipUtil.unzip(toPath, toPathFolder);
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
        Boolean isEnd=false;
        //ѭ���ļ��õ�UserId
        for(File f:folderFiles){
            if(f!=null&&f.exists()&&f.isFile()){
                //�õ�UserId
                List<Object> objList=UpdateUserScoreRankRealnameUtil.getUserIdByXml(f.getPath());
                if(objList==null||objList.size()<1){
                    System.out.println(f.getPath()+" û������!");
                    isEnd=true;
                    continue;
                }
                for(Object obj:objList){
                    if(obj!=null){
                       String realname=UpdateUserScoreRankRealnameUtil.getRealNameByUserId(obj.toString());
                        if(!OperateXMLUtil.updateXml(f.getPath(),"UserId",obj.toString(),new String[]{"RealName","SchoolId"},new Object[]{realname,_Schoolid})){
                            System.out.println("�����쳣��ԭ�򣺲���XML�ļ�ʧ��!");
                            isEnd=true;
                            break;
                        }
                    }
                }
                if(isEnd)
                    break;
            }
        }
        if(!isEnd){
            //��ʼ��֯���ݣ����д���
            //�������
            if(!ZipUtil.genZip(folder.getPath(),toPathFolder,fileName.substring(0,fileName.lastIndexOf(".")))){
                System.out.println("�쳣���������������ݰ��쳣!");
                return;
            }
            //��ʼ
            String uploadFilePath=toPathFolder+"../"+_Template_name;
            //�ļ��ϴ�������
            File fpathF=new File(uploadFilePath);
            if(fpathF.exists()){System.gc();fpathF.delete();}
            UtilTool.writeFile(request
                    ,toPathFolder+"../",_Template_name
                    ,toPathFolder+"/"+fileName.substring(0,fileName.lastIndexOf("."))+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
            //��ʼ�ϴ��ļ�
            UtilTool.uploadResourceToTotalSchool(uploadFilePath);//�ϴ������ļ��洢��txt�ĵ�
            //�����û���ʵ����
            if(!synchroUserRealName(key,fileName.substring(0,fileName.lastIndexOf("."))+".zip")){
                System.out.println("������ʵ����ʧ��!");
            }else{
                System.out.println("������ʵ����ʧ��!");
            }
        }
    }

    /**
     * ͬ���û���ʵ����
     * @param key
     * @param resolveFilname
     * @return
     */
    private boolean synchroUserRealName(String key,String resolveFilname){
        String[] tmpArray=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_USER_SCORE_UPDATE_REAL_NAME").split("\\?");
        String urlStr=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+tmpArray[0];
        String param=tmpArray[1]+"&key="+key+"&resolveFilename="+resolveFilname;
        Map<String,Object> fileLocaMap=UpdateSchoolScoreRankUtil.sendPostURL(urlStr,param);
        if(fileLocaMap==null||fileLocaMap.size()<1)
            return false;
        if(fileLocaMap==null||fileLocaMap.get("type")==null||!fileLocaMap.get("type").toString().trim().equals("success")){
            //��¼�쳣������־��
            System.out.println(fileLocaMap.get("msg"));
            return false;
        }
        return true;
    }




}

/**
 * �����û��������й�����
 */
class UpdateUserScoreRankRealnameUtil{
    /**
     * ��XML�еõ�USER_ID
     * @param xmlpath
     * @return
     */
    public static List<Object> getUserIdByXml(String xmlpath){
        if(xmlpath==null)return null;
        List list= OperateXMLUtil.findXml(xmlpath, "//table //row", false);
        List<Object> ssrList=new ArrayList<Object>();
        if(list!=null){
            for (Object mapObj : list) {
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                if(courseMap!=null){
                    if(courseMap.containsKey("UserId")&&courseMap.get("UserId")!=null
                            &&!courseMap.get("UserId").toString().trim().toUpperCase().equals("UserId"))
                        ssrList.add(courseMap.get("UserId").toString().trim());
                }
            }
        }
        return ssrList;
    }

    /**
     * ����UserId�õ���ʵ����
     * @param userid
     * @return
     */
    public static String getRealNameByUserId(String userid){
        if(userid==null)return null;
        IUserScoreRankManager userScoreRankManager=(UserScoreRankManager) SpringBeanUtil.getBean("userScoreRankManager");
        return userScoreRankManager.getRealNameByUserId(userid);
    }
    /**
     * �õ�XML�е�UserScoreRank����
     * @param xmlpath
     * @return
     */
    public static List<UserScoreRank> getUserScoreRankByXml(String xmlpath){
        if(xmlpath==null)return null;
        List list= OperateXMLUtil.findXml(xmlpath, "//table //row", false);
        List<UserScoreRank> ssrList=new ArrayList<UserScoreRank>();
        if(list!=null){
            for (Object mapObj : list) {
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                if(courseMap!=null){
                    UserScoreRank usr=new UserScoreRank();
                    if(courseMap.containsKey("UserId")&&courseMap.get("UserId")!=null
                            &&!courseMap.get("UserId").toString().trim().toUpperCase().equals("NULL"))
                        usr.setUserid(Long.parseLong(courseMap.get("UserId").toString().trim()));
                    if(courseMap.containsKey("ModelId")&&courseMap.get("ModelId")!=null
                            &&!courseMap.get("ModelId").toString().trim().toUpperCase().equals("NULL"))
                        usr.setModelid(Long.parseLong(courseMap.get("ModelId").toString().trim()));
                    if(courseMap.containsKey("UserId")&&courseMap.get("UserId")!=null
                            &&!courseMap.get("UserId").toString().trim().toUpperCase().equals("NULL"))
                        usr.setUserid(Long.parseLong(courseMap.get("UserId").toString().trim()));
                    if(courseMap.containsKey("TypeId")&&courseMap.get("TypeId")!=null
                            &&!courseMap.get("TypeId").toString().trim().toUpperCase().equals("NULL"))
                        usr.setTypeid(Long.parseLong(courseMap.get("TypeId").toString().trim()));
                    if(courseMap.containsKey("Score")&&courseMap.get("Score")!=null
                            &&!courseMap.get("Score").toString().trim().toUpperCase().equals("NULL"))
                        usr.setScore(Float.parseFloat(courseMap.get("Score").toString().trim()));
                    if(courseMap.containsKey("UserRealName")&&courseMap.get("UserRealName")!=null
                            &&!courseMap.get("UserRealName").toString().trim().toUpperCase().equals("NULL"))
                        usr.setUserrealname(courseMap.get("UserRealName").toString().trim());
                    if(courseMap.containsKey("SchoolName")&&courseMap.get("SchoolName")!=null
                            &&!courseMap.get("SchoolName").toString().trim().toUpperCase().equals("NULL"))
                        usr.setSchoolname(courseMap.get("SchoolName").toString().trim());
                    ssrList.add(usr);
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
