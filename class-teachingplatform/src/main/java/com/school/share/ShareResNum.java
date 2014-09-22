package com.school.share;

import com.school.entity.resource.ResourceInfo;
import com.school.entity.teachpaltform.TpCourseInfo;
import com.school.manager.inter.resource.IResourceManager;
import com.school.manager.resource.ResourceManager;
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
 * 上行，
 * CLICKS
 * ,COMMENTNUM
 * ,DOWNLOADNUM
 * ,STORENUM
 * ,PRAISENUM
 * ,RECOMENDNUM
 * ,REPORTNUM
 * Created by zhengzhou on 14-5-22.
 */
public class ShareResNum extends TimerTask {
    //记录Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String _Schoolid=null;
    private ServletContext request;
    public ShareResNum(ServletContext application){
        //分校ID
        this.request=application;
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }

    private final String _UploadFileName="uploadFile.txt";
    /**
     * 生成的模板XML
     */
    private final static String _Template_name="ShareTemplate.xml";
    /**
     * 每批XML文件的个数
     */
    private Integer _PageSize=1000;


    @Override
    public void run(){
        if(!SynchroUtil._shareResNumBo){
            SynchroUtil._shareResNumBo=true;
            logger.info("--------------------分享资源数量启动执行，不执行-------------------");
            return;
        }
        logger.info("--------------------分享资源数量执行-------------------");
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//记录操作日志
            System.out.println("异常错误，得到分校KEY失败，请检测是否存在School.txt文件!");return;
        }
        String firstDirectory= "ShareResNum";
        String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();    //xml文件的文件名
        //String firstDirectory=UtilTool.DateConvertToString(new Date(),DateType.smollDATE)+"_"+schoolid;
        String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/"; //根路径
        String directionPath=parentDirectory+firstDirectory+"/";
        String dataFileParent=directionPath+"data/";
        String xmlPathUrl=dataFileParent+"/"+filename;
        if(new File(directionPath).exists()){ //如果文件存在，则先删除
            try {
                FileUtils.deleteDirectory(new File(directionPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!new File(dataFileParent).exists()) //查看目录结构是否则在，不存在，则创建
            new File(dataFileParent).mkdirs();
        //记录需要上传的文件完整路径
        String uploadFileTxt=directionPath+_UploadFileName;
        //模板XML文件名称及路径
        String templatepath=parentDirectory+_Template_name;
        File fromF=new File(templatepath);
        if(!fromF.exists()){
            //记录错误信息
            System.out.println("分校异常!没有发现上传模版!");
            return;
        }
        PageResult presult=new PageResult();
        presult.setPageSize(_PageSize);
        presult.setPageNo(0);
        //得到要分享的资源
        ResourceInfo rssearch=new ResourceInfo();
        rssearch.setResdegree(-3);//只查询共享，标准
        boolean isSuccess=true;
        while(true){
            presult.setPageNo(presult.getPageNo() + 1);

            String writeUrl=xmlPathUrl;
            //XML文件分批量
            writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))+"_"+presult.getPageNo()+writeUrl.substring(writeUrl.lastIndexOf("."));
            //查询要共享的专题
            //得到数据
            List<ShareResNumInfo> shareNumList=ShareResNumUtil.getShareResNumList(rssearch,Long.parseLong(_Schoolid),presult);

            if(shareNumList==null||shareNumList.size()<1)   //说明没有要共享的专题了，退出
                break;
            //创建文件夹，并复制模板，进行操作
            File tmpF1=new File(writeUrl);
            try{
                if(!tmpF1.getParentFile().exists())
                    tmpF1.getParentFile().mkdirs();
                if(tmpF1.exists()&&tmpF1.isFile())
                    tmpF1.delete();
                //复制模板
                FileUtils.copyFile(fromF, tmpF1);
            }catch(Exception e){
                isSuccess=false;
                //记录日志
                System.out.println("分校异常!创建空文件失败，原因：未知");
                e.printStackTrace();
            }
            //写入XML
            for (ShareResNumInfo entity:shareNumList){
                if(!ShareCourseUtil.addDateToXml(writeUrl,entity)){
                    isSuccess=false;
                    System.out.println("异常错误，写入专题至XML文件失败，原因：未知!");
                    return;
                }
            }
         }
        if(!isSuccess){
           //跳转，异常错误!
           return;
        }
        //先记录空值
        UtilTool.writeFile(request,directionPath,_UploadFileName,"");
        //打包
        //将数据文件进行打包 zip  并加入到发送序列中
        ZipUtil.genZip(dataFileParent, directionPath, firstDirectory + "_data_" + presult.getPageNo());
        //将文档路径写入TXT中进行上传
        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data_"+presult.getPageNo()+".zip\r\n");  //将生成的ZIP文件，记录到上传文件列表中。
        //开始解析文件进行传输
        UtilTool.uploadResourceToTotalSchool(uploadFileTxt);//上传附件文件存储的txt文档

        //开始进行数据解析请求
        String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
        String totalMethod=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_SYNCHRO_RES_NUM");
        String params=totalMethod.split("\\?")[1];

        totalSchoolUrl+="/"+totalMethod.split("\\?")[0];
        params+="&key="+key+"&resolveFilename="+firstDirectory+".zip";
        Map<String,Object> fileLocaMap=ShareResNumUtil.sendPostURL(totalSchoolUrl,params);
        if(fileLocaMap==null||fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success")){
            //记录异常错误日志，
            System.out.println(fileLocaMap.get("msg"));
            return;
        }else
            System.out.println("更新资源数据成功!");
    }
}

/**
 * 临时实体
 */
class ShareResNumInfo implements  Serializable{
    /**
     * REF         NUMBER(19)                  ID
     SCHOOL_ID   NUMBER(9)                   学校ID
     RES_ID      NUMBER(19)                  资源ID（云端ID）
     CLICKS      NUMBER(9)                   本校点击次数
     COMMENTNUM  NUMBER(9)                   本校评论次数
     DOWNLOADNUM NUMBER(9)                   本校下载次数
     STORENUM    NUMBER(9)                   本校收藏次数
     PRAISENUM   NUMBER(9)                   本校赞次数
     RECOMENDNUM NUMBER(9)                   本校推荐次数
     REPORTNUM   NUMBER(9)                   本校举报次数
     CTIME       DATE                sysdate 创建时间
     */
    private Long resid;
    private Long schoolid;
    private Long clicks;
    private Long commentnum;
    private Long downloadnum;
    private Long storenum;
    private Long praisenum;
    private Long recomendnum;
    private Long reportnum;

    public Long getResid() {
        return resid;
    }

    public void setResid(Long resid) {
        this.resid = resid;
    }

    public Long getSchoolid() {
        return schoolid;
    }

    public void setSchoolid(Long schoolid) {
        this.schoolid = schoolid;
    }

    public Long getClicks() {
        return clicks;
    }

    public void setClicks(Long clicks) {
        this.clicks = clicks;
    }

    public Long getCommentnum() {
        return commentnum;
    }

    public void setCommentnum(Long commentnum) {
        this.commentnum = commentnum;
    }

    public Long getDownloadnum() {
        return downloadnum;
    }

    public void setDownloadnum(Long downloadnum) {
        this.downloadnum = downloadnum;
    }

    public Long getStorenum() {
        return storenum;
    }

    public void setStorenum(Long storenum) {
        this.storenum = storenum;
    }

    public Long getPraisenum() {
        return praisenum;
    }

    public void setPraisenum(Long praisenum) {
        this.praisenum = praisenum;
    }

    public Long getRecomendnum() {
        return recomendnum;
    }

    public void setRecomendnum(Long recomendnum) {
        this.recomendnum = recomendnum;
    }

    public Long getReportnum() {
        return reportnum;
    }

    public void setReportnum(Long reportnum) {
        this.reportnum = reportnum;
    }
}

/**
 * 工具类
 */
class ShareResNumUtil{
    /**
     * 得到要操作的实体，并转换成相关类
     * @param res
     * @param schoolid
     * @param presult
     * @return
     */
    public static List<ShareResNumInfo> getShareResNumList(ResourceInfo res,Long schoolid,PageResult presult){
        IResourceManager resourceManager=SpringBeanUtil.getBean("resourceManager", ResourceManager.class);
        List<ResourceInfo> resList=resourceManager.getList(res,presult);
        if(resList==null||resList.size()<1)return null;
        List<ShareResNumInfo> shareList=new ArrayList<ShareResNumInfo>();
        for (ResourceInfo rs:resList){
            ShareResNumInfo entity=new ShareResNumInfo();
            entity.setResid(rs.getResid());
            entity.setSchoolid(schoolid);
            entity.setCommentnum(Long.parseLong(rs.getCommentnum()==null?"0":(rs.getCommentnum()+"")));
            entity.setDownloadnum(Long.parseLong(rs.getDownloadnum()==null?"0":(rs.getDownloadnum()+"")));
            entity.setPraisenum(Long.parseLong(rs.getPraisenum()==null?"0":(rs.getPraisenum()+"")));
            entity.setRecomendnum(Long.parseLong(rs.getRecomendnum()==null?"0":(rs.getRecomendnum()+"")));
            entity.setReportnum(Long.parseLong(rs.getReportnum()==null?"0":(rs.getReportnum()+"")));
            entity.setStorenum(Long.parseLong(rs.getStorenum()==null?"0":(rs.getStorenum()+"")));
            entity.setClicks(Long.parseLong(rs.getClicks()==null?"0":(rs.getClicks()+"")));
            shareList.add(entity);
        }
        return shareList;
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
}
