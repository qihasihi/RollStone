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
public class ShareResNum extends TimerTask {
    //��¼Log4J
    private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(this.getClass());

    private String _Schoolid=null;
    private ServletContext request;
    public ShareResNum(ServletContext application){
        //��УID
        this.request=application;
        _Schoolid= UtilTool.utilproperty.get("CURRENT_SCHOOL_ID").toString();
    }

    private final String _UploadFileName="uploadFile.txt";
    /**
     * ���ɵ�ģ��XML
     */
    private final static String _Template_name="ShareTemplate.xml";
    /**
     * ÿ��XML�ļ��ĸ���
     */
    private Integer _PageSize=1000;


    @Override
    public void run(){
        if(!SynchroUtil._shareResNumBo){
            SynchroUtil._shareResNumBo=true;
            logger.info("--------------------������Դ��������ִ�У���ִ��-------------------");
            return;
        }
        logger.info("--------------------������Դ����ִ��-------------------");
        String key=UpdateCourseUtil.getCurrentSchoolKey(request);
        if(key==null){//��¼������־
            System.out.println("�쳣���󣬵õ���УKEYʧ�ܣ������Ƿ����School.txt�ļ�!");return;
        }
        String firstDirectory= "ShareResNum";
        String filename=new StringBuffer().append(firstDirectory).append(".xml").toString();    //xml�ļ����ļ���
        //String firstDirectory=UtilTool.DateConvertToString(new Date(),DateType.smollDATE)+"_"+schoolid;
        String parentDirectory=request.getRealPath("/")+"/uploadfile/tmp/"; //��·��
        String directionPath=parentDirectory+firstDirectory+"/";
        String dataFileParent=directionPath+"data/";
        String xmlPathUrl=dataFileParent+"/"+filename;
        if(new File(directionPath).exists()){ //����ļ����ڣ�����ɾ��
            try {
                FileUtils.deleteDirectory(new File(directionPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!new File(dataFileParent).exists()) //�鿴Ŀ¼�ṹ�Ƿ����ڣ������ڣ��򴴽�
            new File(dataFileParent).mkdirs();
        //��¼��Ҫ�ϴ����ļ�����·��
        String uploadFileTxt=directionPath+_UploadFileName;
        //ģ��XML�ļ����Ƽ�·��
        String templatepath=parentDirectory+_Template_name;
        File fromF=new File(templatepath);
        if(!fromF.exists()){
            //��¼������Ϣ
            System.out.println("��У�쳣!û�з����ϴ�ģ��!");
            return;
        }
        PageResult presult=new PageResult();
        presult.setPageSize(_PageSize);
        presult.setPageNo(0);
        //�õ�Ҫ�������Դ
        ResourceInfo rssearch=new ResourceInfo();
        rssearch.setResdegree(-3);//ֻ��ѯ������׼
        boolean isSuccess=true;
        while(true){
            presult.setPageNo(presult.getPageNo() + 1);

            String writeUrl=xmlPathUrl;
            //XML�ļ�������
            writeUrl=writeUrl.substring(0,writeUrl.lastIndexOf("."))+"_"+presult.getPageNo()+writeUrl.substring(writeUrl.lastIndexOf("."));
            //��ѯҪ�����ר��
            //�õ�����
            List<ShareResNumInfo> shareNumList=ShareResNumUtil.getShareResNumList(rssearch,Long.parseLong(_Schoolid),presult);

            if(shareNumList==null||shareNumList.size()<1)   //˵��û��Ҫ�����ר���ˣ��˳�
                break;
            //�����ļ��У�������ģ�壬���в���
            File tmpF1=new File(writeUrl);
            try{
                if(!tmpF1.getParentFile().exists())
                    tmpF1.getParentFile().mkdirs();
                if(tmpF1.exists()&&tmpF1.isFile())
                    tmpF1.delete();
                //����ģ��
                FileUtils.copyFile(fromF, tmpF1);
            }catch(Exception e){
                isSuccess=false;
                //��¼��־
                System.out.println("��У�쳣!�������ļ�ʧ�ܣ�ԭ��δ֪");
                e.printStackTrace();
            }
            //д��XML
            for (ShareResNumInfo entity:shareNumList){
                if(!ShareCourseUtil.addDateToXml(writeUrl,entity)){
                    isSuccess=false;
                    System.out.println("�쳣����д��ר����XML�ļ�ʧ�ܣ�ԭ��δ֪!");
                    return;
                }
            }
         }
        if(!isSuccess){
           //��ת���쳣����!
           return;
        }
        //�ȼ�¼��ֵ
        UtilTool.writeFile(request,directionPath,_UploadFileName,"");
        //���
        //�������ļ����д�� zip  �����뵽����������
        ZipUtil.genZip(dataFileParent, directionPath, firstDirectory + "_data_" + presult.getPageNo());
        //���ĵ�·��д��TXT�н����ϴ�
        UtilTool.writeFile(request,directionPath,_UploadFileName,directionPath+firstDirectory+"_data_"+presult.getPageNo()+".zip\r\n");  //�����ɵ�ZIP�ļ�����¼���ϴ��ļ��б��С�
        //��ʼ�����ļ����д���
        UtilTool.uploadResourceToTotalSchool(uploadFileTxt);//�ϴ������ļ��洢��txt�ĵ�

        //��ʼ�������ݽ�������
        String totalSchoolUrl=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_LOCATION");
        String totalMethod=UtilTool.utilproperty.getProperty("TOTAL_SCHOOL_SYNCHRO_RES_NUM");
        String params=totalMethod.split("\\?")[1];

        totalSchoolUrl+="/"+totalMethod.split("\\?")[0];
        params+="&key="+key+"&resolveFilename="+firstDirectory+".zip";
        Map<String,Object> fileLocaMap=ShareResNumUtil.sendPostURL(totalSchoolUrl,params);
        if(fileLocaMap==null||fileLocaMap.get("type")!=null&&!fileLocaMap.get("type").toString().trim().equals("success")){
            //��¼�쳣������־��
            System.out.println(fileLocaMap.get("msg"));
            return;
        }else
            System.out.println("������Դ���ݳɹ�!");
    }
}

/**
 * ��ʱʵ��
 */
class ShareResNumInfo implements  Serializable{
    /**
     * REF         NUMBER(19)                  ID
     SCHOOL_ID   NUMBER(9)                   ѧУID
     RES_ID      NUMBER(19)                  ��ԴID���ƶ�ID��
     CLICKS      NUMBER(9)                   ��У�������
     COMMENTNUM  NUMBER(9)                   ��У���۴���
     DOWNLOADNUM NUMBER(9)                   ��У���ش���
     STORENUM    NUMBER(9)                   ��У�ղش���
     PRAISENUM   NUMBER(9)                   ��У�޴���
     RECOMENDNUM NUMBER(9)                   ��У�Ƽ�����
     REPORTNUM   NUMBER(9)                   ��У�ٱ�����
     CTIME       DATE                sysdate ����ʱ��
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
 * ������
 */
class ShareResNumUtil{
    /**
     * �õ�Ҫ������ʵ�壬��ת���������
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
}
