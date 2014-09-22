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
 * 同步用户分数排行真实姓名
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
     * 生成的模板XML
     */
    private final static String _Template_name="ShareTemplate.xml";
    //记录Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());
    @Override
    public void run() {
        //如果是这第一次执行，则是启动执行，则直接返回
        if(!SynchroUtil._updateUserScoreRankRealname){
            SynchroUtil._updateUserScoreRankRealname=true;
            logger.info("-------------------- 同步用户分数排行真实姓名启动执行，不执行-------------------");
            return;
        }
        logger.info("-------------------- 同步用户分数排行真实姓名启动执行-------------------");
        // Date currentDate=new Date();//记录当前更新的时间点
        String key=UpdateSchoolScoreRankUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }
        String[] schoolUrl= UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_USER_SCORE_RANK_GET_USERID").toString().split("\\?");
        String postFileUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION")+schoolUrl[0];
        String params=schoolUrl[1]+"&key="+key;
        //开始请求并返回更新资源下载地址
        Map<String,Object> fileLocaMap=UpdateSchoolScoreRankUtil.sendPostURL(postFileUrl, params);


        String updateFileLocaPath=null;//"http://192.168.8.96:8080/fileoperate/uploadfile//tmp/UpdateCourse/50000/firstUpdateCourse/firstUpdateCourse.zip";

        if(fileLocaMap==null||fileLocaMap.get("type")==null||!fileLocaMap.get("type").toString().trim().equals("success")){
            //记录异常错误日志，
            System.out.println(fileLocaMap.get("msg"));
            return;
        }
        updateFileLocaPath=fileLocaMap.get("objList").toString();
        //System.out.println(updateFileLocaPath);
        //下载文件XMLpath到对应目录下
        String fileName=UpdateSchoolScoreRankUtil.getFileName(updateFileLocaPath);
        String toPathFolder=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/tmp/sendfile/updateUserScoreRank/UserId/";
        String toPath=toPathFolder+fileName;
        if(!UpdateSchoolScoreRankUtil.downLoadZipFile(updateFileLocaPath,toPath)){
            //复制文件失败.记录至日志中。
            System.out.println("copy "+fileName+" error");
        }

        //开始解压下载下来的文件
        try {
            ZipUtil.unzip(toPath, toPathFolder);
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
        Boolean isEnd=false;
        //循环文件得到UserId
        for(File f:folderFiles){
            if(f!=null&&f.exists()&&f.isFile()){
                //得到UserId
                List<Object> objList=UpdateUserScoreRankRealnameUtil.getUserIdByXml(f.getPath());
                if(objList==null||objList.size()<1){
                    System.out.println(f.getPath()+" 没有数据!");
                    isEnd=true;
                    continue;
                }
                for(Object obj:objList){
                    if(obj!=null){
                       String realname=UpdateUserScoreRankRealnameUtil.getRealNameByUserId(obj.toString());
                        if(!OperateXMLUtil.updateXml(f.getPath(),"UserId",obj.toString(),new String[]{"RealName","SchoolId"},new Object[]{realname,_Schoolid})){
                            System.out.println("操作异常，原因：操作XML文件失败!");
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
            //开始组织数据，进行传递
            //打包数据
            if(!ZipUtil.genZip(folder.getPath(),toPathFolder,fileName.substring(0,fileName.lastIndexOf(".")))){
                System.out.println("异常错误，重新生成数据包异常!");
                return;
            }
            //开始
            String uploadFilePath=toPathFolder+"../"+_Template_name;
            //文件上传的数据
            File fpathF=new File(uploadFilePath);
            if(fpathF.exists()){System.gc();fpathF.delete();}
            UtilTool.writeFile(request
                    ,toPathFolder+"../",_Template_name
                    ,toPathFolder+"/"+fileName.substring(0,fileName.lastIndexOf("."))+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
            //开始上传文件
            UtilTool.uploadResourceToTotalSchool(uploadFilePath);//上传附件文件存储的txt文档
            //更新用户真实姓名
            if(!synchroUserRealName(key,fileName.substring(0,fileName.lastIndexOf("."))+".zip")){
                System.out.println("更新真实姓名失败!");
            }else{
                System.out.println("更新真实姓名失败!");
            }
        }
    }

    /**
     * 同步用户真实姓名
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
            //记录异常错误日志，
            System.out.println(fileLocaMap.get("msg"));
            return false;
        }
        return true;
    }




}

/**
 * 更新用户积分排行工具类
 */
class UpdateUserScoreRankRealnameUtil{
    /**
     * 从XML中得到USER_ID
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
     * 根据UserId得到真实姓名
     * @param userid
     * @return
     */
    public static String getRealNameByUserId(String userid){
        if(userid==null)return null;
        IUserScoreRankManager userScoreRankManager=(UserScoreRankManager) SpringBeanUtil.getBean("userScoreRankManager");
        return userScoreRankManager.getRealNameByUserId(userid);
    }
    /**
     * 得到XML中的UserScoreRank对象
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
