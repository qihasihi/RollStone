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
 * 下行，
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
    //记录Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    @Override
    public void run() {
        //如果是这第一次执行，则是启动执行，则直接返回
        if(!SynchroUtil._updateResNum){
            SynchroUtil._updateResNum=true;
            logger.info("--------------------下行资源相关数量启动执行，不执行-------------------");
            return;
        }
        logger.info("--------------------下行资源相关数量启动执行-------------------");
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }

        String[] teamaterialLocal= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_UP_NUM").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+teamaterialLocal[0];
        String params=teamaterialLocal[1]+"&key="+key;
        //开始请求并返回更新资源下载地址
        Map<String,Object> fileLocaMap=UpdateResNumUtil.sendPostURL(postFileUrl,params);


        String updateFileLocaPath=null;//"http://localhost:8080/fileoperate/uploadfile/tmp/UpdateResNum/50000/updateResNum_1400752173844/updateResNum_1400752173844.zip";//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

//        Map<String,Object> fileLocaMap= UpdateCourseUtil.getUpdateCourse(key,ftime);
        if(fileLocaMap==null&&fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success")){
            //记录异常错误日志，
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
//        //System.out.println(updateFileLocaPath);
//        //下载文件XMLpath到对应目录下
        String fileName=UpdateResNumUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateResNum/";
        String toPath=toPathFolder+fileName;
//        if(!UpdateResNumUtil.downLoadZipFile(updateFileLocaPath,toPath)){
//            //复制文件失败.记录至日志中。
//            System.out.println("copy "+fileName+" error");
//        }
        //开始解压下载下来的文件
        try {
            ZipUtil.unzip(toPath, toPathFolder + "/" + fileName.substring(0, fileName.lastIndexOf(".")) + "/");
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
            System.out.println("没有发现可以解析的文件，即没有资源信息更新，记录到数据库中");return;
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
                        System.out.println("更新教材信息失败!记录日志");
                        istrue=false;
                        break;
                    }
                }
                if(!istrue)break;
            }
        }
        //删除多余文件
        System.gc();
        try {
            FileUtils.deleteDirectory(new File(toPathFolder));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(istrue){
            System.out.println("更新资源数量成功!");
        }else
            System.out.println("更新资源数量失败!");
    }
}
class UpdateResNumUtil{

    /**
     * 解释XML文件，得到实体
     * @param path
     * @return
     */
    public static List<ResourceInfo> getResourceList(String path){
        if(path==null)return null;
        //得到要解析后的实体  (不加载子项)
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
     * 得到同步SQL语句
     * @param res
     * @param sqlbuilder
     * @return
     */
    public static List<Object> getSynchroSql(ResourceInfo res,StringBuilder sqlbuilder){
        IResourceManager resourceManager=SpringBeanUtil.getBean("resourceManager", ResourceManager.class);
        return resourceManager.getUpdateResNum(res, sqlbuilder);
    }
    /**
     * 批量执行SQL语句
     * @param sqlArrayList
     * @param objArrayList
     * @return
     */
    public static boolean doExcetueArrayProc(List<String> sqlArrayList,List<List<Object>> objArrayList){
        IResourceManager resourceManager=SpringBeanUtil.getBean("resourceManager", ResourceManager.class);
        return resourceManager.doExcetueArrayProc(sqlArrayList, objArrayList);
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
