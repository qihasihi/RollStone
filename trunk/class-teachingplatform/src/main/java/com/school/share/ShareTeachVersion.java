package com.school.share;

import com.school.entity.DictionaryInfo;
import com.school.entity.teachpaltform.TeachVersionInfo;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.teachpaltform.ITeachVersionManager;
import com.school.manager.teachpaltform.TeachVersionManager;
import com.school.util.OperateXMLUtil;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import com.school.util.ZipUtil;
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
 * �汾ͬ��
 * Created by zhengzhou on 14-3-13.
 */
public class ShareTeachVersion extends TimerTask {
    private ServletContext request;
    public ShareTeachVersion(ServletContext application){
        this.request=application;
        //��УID
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }

    /**
     * ���ɵ�ģ��XML
     */
    private final static String _Template_name="ShareTemplate.xml";

    /**
     * ��УID
     */
    private String _Schoolid=null;
    @Override
    public void run() {
        Date currentDate=new Date();//��¼��ǰ���µ�ʱ���
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }
        String ftime=null;
        //�õ���һ�θ��µ�ʱ��
        IDictionaryManager dictionaryManager=(DictionaryManager) SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dictionaryManager.getDictionaryByType("UPDATE_TEACHVERSION_FTIME");

        DictionaryInfo fupdateDic=null;
        if(dicList!=null&&dicList.size()>0){
            ftime=dicList.get(0).getDictionaryvalue();
            fupdateDic=dicList.get(0);
        }
        String[] teaversion=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_TEACHVERSION").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+teaversion[0];
        String params=teaversion[1]+"&key="+key;
        if(ftime!=null)
            params+="&ftime="+ftime;
        //��ʼ���󲢷��ظ�����Դ���ص�ַ
        Map<String,Object> fileLocaMap=ShareTeachVersionUtil.sendPostURL(postFileUrl,params);


        String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

//        Map<String,Object> fileLocaMap= UpdateCourseUtil.getUpdateCourse(key,ftime);
        if(fileLocaMap==null&&fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success")){
            //��¼�쳣������־��
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        if(fileLocaMap==null||fileLocaMap.get("objList")==null){
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
        //System.out.println(updateFileLocaPath);
        //�����ļ�XMLpath����ӦĿ¼��
        String fileName=ShareTeachingMaterialUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateTeachVersion/";
        String toPath=toPathFolder+fileName;
        if(!ShareTeachingMaterialUtil.downLoadZipFile(updateFileLocaPath,toPath)){
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
            System.out.println(folder.getPath()+"û�з��ֿ��Խ������ļ�����û�а汾���£���¼�����ݿ���");return;
        }
        //�õ�ҵ��������
        ITeachVersionManager teachVersionManager=(TeachVersionManager)SpringBeanUtil.getBean("teachVersionManager");

        boolean istrue=true;

        for(int i=0;i<folderFiles.length;i++){
            File tmp=folderFiles[i];
            if(tmp!=null&&tmp.exists()){
                StringBuilder sqlbuilder=null;
                List<Object> objList=null;

                List<List<Object>> objArrayList=new ArrayList<List<Object>>();
                List<String> sqlArrayList=new ArrayList<String>();


                List<TeachVersionInfo> tVsionList=ShareTeachVersionUtil.getTeachVersionByXml(tmp.getPath());
                if(tVsionList==null||tVsionList.size()<1)continue;
                for(TeachVersionInfo tv:tVsionList){
                    if(tv!=null){
                        sqlbuilder=new StringBuilder();
                        objList=teachVersionManager.getSynchroSql(tv,sqlbuilder);
                        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                            objArrayList.add(objList);
                            sqlArrayList.add(sqlbuilder.toString());
                        }
                    }
                }
                if(objArrayList.size()>0&&sqlArrayList.size()>0&&objArrayList.size()==sqlArrayList.size()){
                    istrue=teachVersionManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                    if(!istrue){
                        System.out.println("���°汾��Ϣʧ��!��¼��־");
                        break;
                    }
                }
            }
        }
        //�õ��ֵ����������
        StringBuilder sqlbuilder=new StringBuilder();
        if(istrue){
            if(fupdateDic==null){
                fupdateDic=new DictionaryInfo();
                fupdateDic.setDictionarytype("UPDATE_TEACHVERSION_FTIME");
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                fupdateDic.setDictionaryname("�汾��Ϣ�ϴθ���ʱ��!");
                istrue=dictionaryManager.doSave(fupdateDic);
            }else{
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                istrue=dictionaryManager.doUpdate(fupdateDic);
            }

            //ɾ�������ļ�
            System.gc();
            try {
                FileUtils.deleteDirectory(new File(toPathFolder));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(istrue){
            System.out.println("���°汾��Ϣ�ɹ�!");
        }else{
            System.out.println("���°汾��Ϣʧ��!��¼��־");
        }
    }
}

class ShareTeachVersionUtil{

    /**
     * ��XML�еõ�����
     * @param path ·��
     * @return
     */
    public static List<TeachVersionInfo> getTeachVersionByXml(String path){
        if(path==null)return null;
        //�õ�Ҫ�������ʵ��  (����������)
        List list= OperateXMLUtil.findXml(path, "//table //row", false);
        if(list==null||list.size()<1)return null;
        List<TeachVersionInfo> returnList=new ArrayList<TeachVersionInfo>();
        for (Object mapObj : list) {
            if(mapObj!=null){
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                TeachVersionInfo tvEntity=new TeachVersionInfo();
                if(courseMap.containsKey("VersionId")&&courseMap.get("VersionId")!=null&&!courseMap.get("VersionId").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setVersionid(Integer.parseInt(courseMap.get("VersionId").toString()));
                }
                if(courseMap.containsKey("Remark")&&courseMap.get("Remark")!=null&&!courseMap.get("Remark").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setRemark(courseMap.get("Remark").toString());
                }
                if(courseMap.containsKey("Ctime")&&courseMap.get("Ctime")!=null&&!courseMap.get("Ctime").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setCtime(UtilTool.StringConvertToDate(courseMap.get("Ctime").toString()));
                }
                if(courseMap.containsKey("VersionId")&&courseMap.get("VersionId")!=null&&!courseMap.get("VersionId").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setVersionid(Integer.parseInt(courseMap.get("VersionId").toString()));
                }
                if(courseMap.containsKey("VersionName")&&courseMap.get("VersionName")!=null&&!courseMap.get("VersionName").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setVersionname(courseMap.get("VersionName").toString());
                }
                returnList.add(tvEntity);
            }
        }
        return returnList.size()>0?returnList:null;
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
