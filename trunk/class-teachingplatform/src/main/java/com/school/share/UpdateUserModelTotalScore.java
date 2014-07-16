package com.school.share;

import com.school.entity.UserModelTotalScoreInfo;
import com.school.entity.resource.score.UserModelTotalScore;
import com.school.manager.inter.resource.score.IUserModelTotalScoreManager;
import com.school.manager.resource.score.UserModelTotalScoreManager;
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
 * 更新用户总分排行(第日更新)
 * Created by zhengzhou on 14-3-18.
 */
public class UpdateUserModelTotalScore extends TimerTask {
    private ServletContext request;
    public UpdateUserModelTotalScore(ServletContext application){
        this.request=application;
    }
    /**
     * 生成的模板XML
     */
    private final static String _Template_name="ShareTemplate.xml";


    @Override
    public void run() {
        // Date currentDate=new Date();//记录当前更新的时间点
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }
        String[] schoolUrl= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_USER_MODEL_TOTAL_SCORE").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+schoolUrl[0];
        String params=schoolUrl[1]+"&key="+key;
        //开始请求并返回更新资源下载地址
        Map<String,Object> fileLocaMap=UpdateUserModelTotalScoreUtil.sendPostURL(postFileUrl,params);
        String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";
        if(fileLocaMap==null||fileLocaMap.get("type")==null||!fileLocaMap.get("type").toString().trim().equals("success")){
            //记录异常错误日志，
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
        //System.out.println(updateFileLocaPath);
        //下载文件XMLpath到对应目录下
        String fileName=UpdateUserModelTotalScoreUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateUserModelTotalScore/";
        String toPath=toPathFolder+fileName;
        if(!UpdateUserModelTotalScoreUtil.downLoadZipFile(updateFileLocaPath,toPath)){
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
            System.out.println("没有发现可以解析的文件，即没有版本更新，记录到数据库中");return;
        }
        //该分校的用户排行
        IUserModelTotalScoreManager userModelTotalScoreManager=(UserModelTotalScoreManager) SpringBeanUtil.getBean("userModelTotalScoreManager");
        //循环解析folderFiles

        List<List<Object>> objArrayList=new ArrayList<List<Object>>();
        List<String> sqlArrayList=new ArrayList<String>();
        StringBuilder sqlbuilder=new StringBuilder();
        List<Object> objList=null;
        //删除原有的记录
        objList=userModelTotalScoreManager.getDeleteSql(new UserModelTotalScoreInfo(),sqlbuilder);
        if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
            sqlArrayList.add(sqlbuilder.toString());
            objArrayList.add(objList);
        }


        boolean hasData=false,isError=false;
        for(File f:folderFiles){
            if(f!=null&&f.exists()&&f.isFile()){
                //从XML中得到学校积分数据
                List<UserModelTotalScore> ssrList=UpdateUserModelTotalScoreUtil.getUserModelTotalScoreRankByXml(f.getPath());
                if(ssrList==null||ssrList.size()<1)continue;
                //删除
                sqlbuilder=new StringBuilder();
                for(UserModelTotalScore sr:ssrList){
                    if(sr==null)continue;
                    hasData=true;
                    sqlbuilder=new StringBuilder();
                    objList=userModelTotalScoreManager.getSynchroSQL(sr,sqlbuilder);
                    if(sqlbuilder!=null&&sqlbuilder.toString().trim().length()>0){
                        sqlArrayList.add(sqlbuilder.toString());
                        objArrayList.add(objList);
                    }
                }
                //批量添加
                if(sqlArrayList!=null&&objArrayList!=null&&sqlArrayList.size()>0&&sqlArrayList.size()==objArrayList.size()){
                    if(!userModelTotalScoreManager.doExcetueArrayProc(sqlArrayList,objArrayList)){
                        isError=true;
                        System.out.println("异常错误，批量执行SQL语句失败!filename="+f.getPath());
                    }
                    objArrayList=new ArrayList<List<Object>>();
                    sqlArrayList=new ArrayList<String>();
                }else{
                    isError=true;
                    System.out.println("异常错误，没有可执行的SQL语句!");

                }
                if(isError){
                    break;
                }
            }
        }
        System.out.println("更新分校排行完成!");
        if(!hasData){
            System.out.println("没有可更新的信息！");
        }
        if(!isError){//删除文件
            System.gc();
            try {
                //删除文件夹
                FileUtils.deleteDirectory(new File(toPathFolder));
                //删除toPath
                new File(toPath).delete();
            } catch (IOException e) {
            }
        }
    }
}

/**
 * 工具类
 */
class UpdateUserModelTotalScoreUtil{

    /**
     * 在XML中得到数据
     * @param xmlpath xml的绝对地址
     * @return
     */
    public static List<UserModelTotalScore> getUserModelTotalScoreRankByXml(String xmlpath){
        if(xmlpath==null)return null;
        List list= OperateXMLUtil.findXml(xmlpath, "//table //row", false);
        List<UserModelTotalScore> ssrList=new ArrayList<UserModelTotalScore>();
        if(list!=null){
            for (Object mapObj : list) {
                Map<String,Object> courseMap=(Map<String,Object>)mapObj;
                if(courseMap!=null){
                    UserModelTotalScore ssr=new UserModelTotalScore();
                    if(courseMap.containsKey("ModelId")&&courseMap.get("ModelId")!=null
                            &&!courseMap.get("ModelId").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setModelid(Integer.parseInt(courseMap.get("ModelId").toString()));
                    if(courseMap.containsKey("UserId")&&courseMap.get("UserId")!=null
                            &&!courseMap.get("UserId").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setUserid(Integer.parseInt(courseMap.get("UserId").toString().trim()));
                    if(courseMap.containsKey("TotalScore")&&courseMap.get("TotalScore")!=null
                            &&!courseMap.get("TotalScore").toString().trim().toUpperCase().equals("NULL"))
                        ssr.setTotalscore(Integer.parseInt(courseMap.get("TotalScore").toString()));
                    ssrList.add(ssr);
                }
            }
        }
        return ssrList;
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

