package com.school.share;

import com.school.entity.DictionaryInfo;
import com.school.entity.teachpaltform.TeachVersionInfo;
import com.school.entity.teachpaltform.TeachingMaterialInfo;
import com.school.manager.DictionaryManager;
import com.school.manager.inter.IDictionaryManager;
import com.school.manager.inter.teachpaltform.ITeachVersionManager;
import com.school.manager.inter.teachpaltform.ITeachingMaterialManager;
import com.school.manager.teachpaltform.TeachVersionManager;
import com.school.manager.teachpaltform.TeachingMaterialManager;
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
 * 版本同步
 * Created by zhengzhou on 14-3-13.
 */
public class ShareTeachingMaterial extends TimerTask {
    private ServletContext request;
    public ShareTeachingMaterial(ServletContext application){
        this.request=application;
    }


     @Override
    public void run() {
        Date currentDate=new Date();//记录当前更新的时间点
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }
        String ftime=null;
        //得到上一次更新的时间
        IDictionaryManager dictionaryManager=(DictionaryManager) SpringBeanUtil.getBean("dictionaryManager");
        List<DictionaryInfo> dicList=dictionaryManager.getDictionaryByType("UPDATE_TEACHINGMATERIAL_FTIME");

        DictionaryInfo fupdateDic=null;
        if(dicList!=null&&dicList.size()>0){
            ftime=dicList.get(0).getDictionaryvalue();
            fupdateDic=dicList.get(0);
        }
     String[] teamaterialLocal=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_TEAMATERIAL").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+teamaterialLocal[0];
         String params=teamaterialLocal[1]+"&key="+key;
         if(ftime!=null)
             params+="&ftime="+ftime;
        //开始请求并返回更新资源下载地址
        Map<String,Object> fileLocaMap=ShareTeachingMaterialUtil.sendPostURL(postFileUrl,params);


        String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

//        Map<String,Object> fileLocaMap= UpdateCourseUtil.getUpdateCourse(key,ftime);
        if(fileLocaMap==null&&fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success")){
            //记录异常错误日志，
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
        //System.out.println(updateFileLocaPath);
        //下载文件XMLpath到对应目录下
        String fileName=ShareTeachingMaterialUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateTeachingMaterial/";
        String toPath=toPathFolder+fileName;
        if(!ShareTeachingMaterialUtil.downLoadZipFile(updateFileLocaPath,toPath)){
            //复制文件失败.记录至日志中。
            System.out.println("copy "+fileName+" error");
        }
        //开始解压下载下来的文件
        try {
            ZipUtil.unzip(toPath, toPathFolder+"/"+fileName.substring(0,fileName.lastIndexOf("."))+"/");
        } catch (Exception e) {
            e.printStackTrace();
            //记录异常，解压失败 并重新开始
            return;
        }
        //得到目录下的文件，进行解析
        File folder=new File(toPathFolder+"/"+fileName.substring(0,fileName.lastIndexOf("."))+"/");
        //得到下面的所有文件
        File[] folderFiles=folder.listFiles();
        //循环进行解析
        if(folderFiles==null||folderFiles.length<1){
            System.out.println("没有发现可以解析的文件，即没有教材信息更新，记录到数据库中");return;
        }
        //得到业务层访问类
        ITeachingMaterialManager teachingMaterialManager=(TeachingMaterialManager)SpringBeanUtil.getBean("teachingMaterialManager");

        boolean istrue=true;

        for(int i=0;i<folderFiles.length;i++){
            File tmp=folderFiles[i];
            if(tmp!=null&&tmp.exists()){
                StringBuilder sqlbuilder=null;
                List<Object> objList=null;

                List<List<Object>> objArrayList=new ArrayList<List<Object>>();
                List<String> sqlArrayList=new ArrayList<String>();


                List<TeachingMaterialInfo> tVsionList=ShareTeachingMaterialUtil.getTeachingMaterialByXml(tmp.getPath());
                if(tVsionList==null||tVsionList.size()<1)continue;
                for(TeachingMaterialInfo tv:tVsionList){
                    if(tv!=null){
                        sqlbuilder=new StringBuilder();
                        objList=teachingMaterialManager.getSynchroSql(tv, sqlbuilder);
                        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                            objArrayList.add(objList);
                            sqlArrayList.add(sqlbuilder.toString());
                        }
                    }
                }
                if(objArrayList.size()>0&&sqlArrayList.size()>0&&objArrayList.size()==sqlArrayList.size()){
                    istrue=teachingMaterialManager.doExcetueArrayProc(sqlArrayList,objArrayList);
                    if(!istrue){
                        System.out.println("更新教材信息失败!记录日志");
                        break;
                    }
                }
            }
        }
        //得到字典表的相关数据
        StringBuilder sqlbuilder=new StringBuilder();
        if(istrue){
            if(fupdateDic==null){
                fupdateDic=new DictionaryInfo();
                fupdateDic.setDictionarytype("UPDATE_TEACHINGMATERIAL_FTIME");
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                fupdateDic.setDictionaryname("教材信息上次更新时间!");
                istrue=dictionaryManager.doSave(fupdateDic);
            }else{
                fupdateDic.setDictionaryvalue(UtilTool.DateConvertToString(currentDate, UtilTool.DateType.type1));
                istrue=dictionaryManager.doUpdate(fupdateDic);
            }
            //删除多余文件
            System.gc();
            try {
                FileUtils.deleteDirectory(new File(toPathFolder));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(istrue){
           //更新成功后，执行生成其它
            if(teachingMaterialManager.genderOtherTeacherTeachMaterial()){
                System.out.println("更新教材信息成功!");
            }else{
                System.out.println("更新教材信息失败，原因：生成其它教材失败!");
            }

        }else{
            System.out.println("更新教材信息失败!记录日志");
        }
    }
}

class ShareTeachingMaterialUtil{
    /**
     * 在XML中得到数据
     * @param path 路径
     * @return
     */
    public static List<TeachingMaterialInfo> getTeachingMaterialByXml(String path){
        if(path==null)return null;
        //得到要解析后的实体  (不加载子项)
        List list= OperateXMLUtil.findXml(path, "//table //row", false);
        if(list==null||list.size()<1)return null;
        List<TeachingMaterialInfo> returnList=new ArrayList<TeachingMaterialInfo>();
        for (Object mapObj : list) {
            if(mapObj!=null){
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                TeachingMaterialInfo tvEntity=new TeachingMaterialInfo();
                if(courseMap.containsKey("VersionId")&&courseMap.get("VersionId")!=null&&!courseMap.get("VersionId").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setVersionid(Integer.parseInt(courseMap.get("VersionId").toString()));
                }
                if(courseMap.containsKey("Remark")&&courseMap.get("Remark")!=null&&!courseMap.get("Remark").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setRemark(courseMap.get("Remark").toString());
                }
                if(courseMap.containsKey("Ctime")&&courseMap.get("Ctime")!=null&&!courseMap.get("Ctime").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setCtime(UtilTool.StringConvertToDate(courseMap.get("Ctime").toString()));
                }
                if(courseMap.containsKey("MaterialId")&&courseMap.get("MaterialId")!=null&&!courseMap.get("MaterialId").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setMaterialid(Integer.parseInt(courseMap.get("MaterialId").toString()));
                }
                if(courseMap.containsKey("MaterialName")&&courseMap.get("MaterialName")!=null&&!courseMap.get("MaterialName").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setMaterialname(courseMap.get("MaterialName").toString());
                }
                if(courseMap.containsKey("GradeId")&&courseMap.get("GradeId")!=null&&!courseMap.get("GradeId").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setGradeid(Integer.parseInt(courseMap.get("GradeId").toString()));
                }
                if(courseMap.containsKey("SubjectId")&&courseMap.get("SubjectId")!=null&&!courseMap.get("SubjectId").toString().trim().toUpperCase().equals("NULL")){
                    tvEntity.setSubjectid(Integer.parseInt(courseMap.get("SubjectId").toString()));
                }
                returnList.add(tvEntity);
            }
        }
        return returnList.size()>0?returnList:null;
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
            httpConnection.setRequestProperty("Content-Length",String.valueOf(params.length()));
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
     * 根据link得到filename
     * @param locaPath
     * @return
     */
    public static String getFileName(String locaPath){
        if(locaPath==null)return null;
        return locaPath.substring(locaPath.lastIndexOf("/")+1);
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
        //开始下载文件
        URL url = null;
        InputStream in =null;
        try {
            url = new URL(zipLocaPath);
            in= url.openStream();
            File dir = new File(toPath);
            dir=new File(dir.getParent());
            if(!dir.exists()) //不存在，则创建
                dir.mkdirs();
            File tofile = new File(toPath);
            //开始复制文件
            FileOutputStream out = new FileOutputStream(tofile);
            Streams.copy(in, out, true);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
