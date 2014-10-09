package com.school.util;

        import java.awt.*;
        import java.awt.image.BufferedImage;

        import java.io.*;
        import java.lang.Exception;
        import java.lang.reflect.Method;
        import java.math.BigDecimal;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.net.URLEncoder;
        import java.security.MessageDigest;
        import java.sql.Blob;
        import java.sql.Clob;
        import java.sql.SQLException;
        import java.text.ParseException;
        import java.text.SimpleDateFormat;
        import java.util.*;
        import java.util.List;
        import java.util.concurrent.TimeUnit;
        import java.util.regex.Matcher;
        import java.util.regex.Pattern;

        import javax.imageio.ImageIO;
        import javax.imageio.ImageReadParam;
        import javax.imageio.ImageReader;
        import javax.imageio.stream.ImageInputStream;
        import javax.servlet.ServletContext;
        import javax.servlet.ServletOutputStream;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;

        import com.school.entity.RightUser;
        import com.school.entity.UserInfo;
        import com.school.util.Pdf2Swf.Office2Pdf;
        import com.school.util.Pdf2Swf.Pdf2Swf;
        import com.school.util.sendfile.SendFile;
        import com.sun.image.codec.jpeg.JPEGCodec;
        import com.sun.image.codec.jpeg.JPEGImageEncoder;

        import net.sf.json.JSONArray;
        import net.sf.json.JSONObject;
        import org.apache.commons.lang.math.RandomUtils;
        import org.springframework.web.context.request.RequestContextHolder;
        import org.springframework.web.context.request.ServletRequestAttributes;
        import org.w3c.dom.Element;
        import org.w3c.dom.NodeList;

public class UtilTool implements java.io.Serializable {
    /************************** 全局 常量********************** */
    public static boolean isSynchroFileType=false;//是否同步了资源文件类型
    private final static String KEY="ettpLatforMsiZhonGJ8";
    /**
     * 1:公告   2:网上公示
     */
    public final static String[] _NOTICE_TYPE={"0","1","2","3"};



    // 用户表中的默认密码
    public final static String _USER_PASSWORD_DEFAULT = "1234";

    // 时间
    private final static String _TYPE1 = "yyyy-MM-dd HH:mm:ss";
    private final static String _TYPE2 = "yyyy/MM/dd HH:mm:ss";
    private final static String _TYPE3 = "HH:mm:ss";
    private final static String _TYPE4="yyyy/MM/dd HH:mm";
    private final static String _TYPE5="M月dd日 HH:mm";
    private final static String _YEAR = "yyyy";
    private final static String _YEARANDMONTH = "yyyy-MM";
    private final static String _SMOLLDATE = "yyyy-MM-dd";
    private final static String _FULL_DATE_CHINA = "yyyy年MM月dd日 HH时mm分ss秒";
    private final static String _DATE_GMT="EEE MMM dd hh:mm:ss z yyyy";

    public final static String[]AZ={"A","B","C","D","E","F","G","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static int _IS_SIMPLE_RESOURCE=1;//精简版标识   1：普通  2：精简

    /**
     * Spring配置文件  (取消重新读取的方式，改为利用SpringBeanutil.getBean("userManager")的方式)
     */
   // public static ApplicationContext _springApplicatioinContext=SpringProperty.getInstance();
    /**
     * 精简版资源系统配置文件
     */
    public final static Properties simpleResUtilProperty=SimpleResUtilProperty.getInstance().prop;
    /**
     * 积分道具奖励加分
     */
    public final static Properties stuScoreAwardUtilProperty=StuScoreAwardUtilProperty.getInstance().prop;

    /**
     * 输出信息配置文件
     */
    public final static Properties msgproperty=MsgProperty.getInstance().prop;

    /**
     * 工具Util信息配置文件
     */
    public static Properties utilproperty=UtilProperty.getInstance().prop;
    //UtilTool的Spring对象
    //public final static HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    /**
     * 输出校风扣分规则配置文件
     */
    public final static Properties scoreproperty=EthosScoreRuleProperty.getInstance().prop;

    /**
     * 特殊字符正则
     */
    public final static String SPECIAL_CHARACTERS = "[%\\|\\<\\>\\'\\＇\\\"\\\\$\\^\\&\\|]+$";

    private static String[] _SUB_CONTENT = null;
    /**
     * 身份。
     */
    public static String _IDENTITY_STUDENT="学生";
    public static String _IDENTITY_TEACHER="教职工";

    //////////////////////////帐号
    /**
     * 学生角色ID
     */
    public static final Integer _ROLE_STU_ID=1;
    /**
     * 教师角色ID
     */
    public static final Integer _ROLE_TEACHER_ID=2;

    /**
     * 家长角色ID
     */
    public static final Integer _ROLE_STU_PARENT_ID=3;

    /**
     * 班主任角色ID
     */
    public static final Integer _ROLE_CLASSADVISE_ID=5;
    /**
     * 管理员角色ID
     */
    public static final Integer _ROLE_ADMIN_ID=4;

    /**
     * 职工角色ID(废弃)
     */
    public static final Integer _ROLE_WORKER_ID=999;

    /**
     * 校领导角色ID
     */
    public static final Integer _ROLE_SCHOOL_LEADER_ID=6;


    /**
     * 正年级组长角色ID
     */
    public static final Integer _ROLE_GRADE_LEADER_ID=9;

    /**
     * 副年级组长角色ID
     */
    public static final Integer _ROLE_GRADE_FU_LEADER_ID=10;

    /**
     * 教研组长
     */
    public static final Integer _ROLE_TEACH_LEADER_ID=13;

    /**
     * 副教研组长
     */
    public static final Integer _ROLE_TEACH_FU_LEADER_ID=14;

    /**
     * 部门主任
     */
    public static final Integer _ROLE_DEPT_LEADER_ID=11;

    /**
     * 副部门主任
     */
    public static final Integer _ROLE_DEPT_FU_LEADER_ID=12;

    /**
     * 自定义部门负责人
     */
    public static final Integer _ROLE_FREE_DEPT_LEADER_ID=18;

    /**
     * 备课组长
     */
    public static final Integer _ROLE_PREPARE_LEADER_ID=17;

    /**
     * 网校教务ID
     */
    public static final Integer _ROLE_WXJW_ID=22;






    /**
     * 上传图片允许的类型
     */
    public static  String _IMG_SUFFIX_TYPE_REGULAR = "(.jpg|.png|.jpeg|.bmp|.gif|.jpe|.JPG|.PNG|.JPEG|.BMP|.GIF|.JPE)$";
    /**
     * 上传视频允许的类型
     */
    public static  String _VIEW_SUFFIX_TYPE_REGULAR = "(.rmvb|.avi|.mov|.flv|.mpg|.vob|.asf|.rm|.tp|.divx|.mpeg|.mpe|.wmv|.mp4|.mkv)$";
    /**
     * 上传视频允许的类型
     */
    public static  String _MP3_SUFFIX_TYPE_REGULAR = "(.mp3|.wav|.wma)$";
    /**
     * 上传SWF允许的类型
     */
    public static  String _SWF_SUFFIX_TYPE_REGULAR = "(.swf)$";
    /**
     * 文档文件类型
     */
    public static  String _DOC_SUFFIX_TYPE_REGULAR ="(.xls|.xlsx|.doc|.docx|.ppt|.pptx|.xml|.pdf|.txt|.vsd|.rtf)$";

    /**
     * 转换允许类型
     */
    public static  String _DOC_CONVERT_SUFFIX_TYPE_REGULAR ="(.xls|.xlsx|.doc|.docx|.ppt|.pptx|.xml|.pdf|.txt|.vsd|.rtf)$";


    public static  String _TXT_SUFFIX_TYPE_REGULAR ="(.txt)$";
    public static  String _PDF_SUFFIX_TYPE_REGULAR ="(.pdf)$";







    /************************* 全局方法 ********************************/
    /**
     * 得到部门
     */
    public static List<Map<String,Object>> _GetDeptType(){
        //得到
        List<Map<String,Object>> returnMap=new ArrayList<Map<String,Object>>();
        Map<String,Object> objMap=new HashMap<String, Object>();
        objMap.put("deptid", "1");
        objMap.put("deptname","行政部门");
        returnMap.add(objMap);
        objMap=new HashMap<String, Object>();
        objMap.put("deptid", "2");
        objMap.put("deptname","教研组");
        returnMap.add(objMap);
        objMap=new HashMap<String, Object>();
        objMap.put("deptid", "3");
        objMap.put("deptname","年级组");
        returnMap.add(objMap);
        objMap=new HashMap<String, Object>();
        objMap.put("deptid", "4");
        objMap.put("deptname","自定义部门");
        returnMap.add(objMap);

        return returnMap;
    }


    public static String getCurrentLocation(HttpServletRequest request){
        String proc_name=request.getHeader("x-etturl");
        if(proc_name==null){
            proc_name=request.getContextPath();
        }else{
            ///group1/1.jsp
            proc_name=proc_name.substring(0,proc_name.substring(1).indexOf("/"));
        }
        return  new StringBuilder(request.getScheme()).append("://")
                .append(request.getServerName()).append(":").append(request.getServerPort())
                .append("/")
                .append(proc_name)
                .append("/").toString();
    }

    public static String getCurrentIpPort(HttpServletRequest request){
        return new StringBuilder(request.getScheme()).append("://")
                .append(request.getServerName()).append(":").append(request.getServerPort())
                .append("/").toString();
    }

    /**
     * 日期转换
     * @param str
     * @param dt
     * @return
     */
    public static Date StringConvertToDate(String str,DateType dt){
        if (str == null || str.trim() == "")
            return null;
       if(dt==null){
            if(str.indexOf("GMT")!=-1)
                dt=DateType.GMT;
           else
                dt=DateType.type1;
       }
        if (str.indexOf("年") != -1 || str.indexOf("月") != -1
                || str.indexOf("日") != -1 || str.indexOf("时") != -1
                || str.indexOf("分") != -1 || str.indexOf("秒") != -1)
            str = str.replace("年", "-").replace("月", "-").replace("日", "")
                    .replace("时", ":").replace("分", ":").replace("秒", "");

        SimpleDateFormat daformat = null;
        if (DateType.type1 == dt) {
            daformat = new SimpleDateFormat(_TYPE1);
        } else if (DateType.type2 == dt) {
            daformat = new SimpleDateFormat(_TYPE2);
        } else if (DateType.type3 == dt) {
            daformat = new SimpleDateFormat(_TYPE3);
        } else if (DateType.type4 == dt) {
            daformat = new SimpleDateFormat(_TYPE4);
        } else if (DateType.type5 == dt) {
            daformat = new SimpleDateFormat(_TYPE5);
        } else if (DateType.year == dt) {
            daformat = new SimpleDateFormat(_YEAR);
        } else if (DateType.yearAndMonth == dt) {
            daformat = new SimpleDateFormat(_YEARANDMONTH);
        } else if (DateType.smollDATE == dt) {
            daformat = new SimpleDateFormat(_SMOLLDATE);
        } else if (DateType.YearForChina == dt) {
            daformat = new SimpleDateFormat(_FULL_DATE_CHINA);
        }else if(DateType.GMT==dt){
            daformat=new SimpleDateFormat(_DATE_GMT,Locale.ENGLISH);
        }
        Date returndt = null;
        try {
            returndt = daformat.parse(str);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return returndt;

    }
    /**
     * 从String 转换成 DATE
     *
     * @param str
     * @return
     */
    public static Date StringConvertToDate(String str) {
        if (str == null || str.trim() == "")
            return null;
        return StringConvertToDate(str,null);

    }

    /**
     * 从DATE 转换成 String
     *
     * @param da
     * @param dt
     * @return
     */
    public static String DateConvertToString(Date da, DateType dt) {
        if (da == null || dt == null)
            return null;
        SimpleDateFormat daformat = null;
        if (DateType.type1 == dt) {
            daformat = new SimpleDateFormat(_TYPE1);
        } else if (DateType.type2 == dt) {
            daformat = new SimpleDateFormat(_TYPE2);
        } else if (DateType.type3 == dt) {
            daformat = new SimpleDateFormat(_TYPE3);
        }else if (DateType.type4 == dt) {
            daformat = new SimpleDateFormat(_TYPE4);
        }else if (DateType.type5 == dt) {
            daformat = new SimpleDateFormat(_TYPE5);
        } else if (DateType.year == dt) {
            daformat = new SimpleDateFormat(_YEAR);
        } else if (DateType.yearAndMonth == dt) {
            daformat = new SimpleDateFormat(_YEARANDMONTH);
        } else if (DateType.smollDATE == dt) {
            daformat = new SimpleDateFormat(_SMOLLDATE);
        } else if (DateType.YearForChina == dt) {
            daformat = new SimpleDateFormat(_FULL_DATE_CHINA);
        }else if(DateType.GMT==dt){
            daformat=new SimpleDateFormat(_DATE_GMT,Locale.ENGLISH);
        }
        String d = null;
        d = daformat.format(da);
        return d;
    }

    public static enum DateType {
        /**
         * YYYY-MM-dd HH:mm:ss
         */
        type1,
        /**
         * yyyy/MM/dd hh:mm:ss
         */
        type2,
        /**
         * hh:mm:ss
         */
        type3,
        /**
         *  yyyy/MM/dd hh:mm
         */
        type4,
        /**
         * MM/dd hh:mm
         */
        type5,
        /**
         * yyyy
         */
        year,
        /**
         * yyyy-MM
         */
        yearAndMonth,
        /**
         * yyyy-MM-dd
         */
        smollDATE, YearForChina,GMT
    }

    public static Clob oracleStr2Clob(String str) throws Exception {
        java.sql.Clob c = new javax.sql.rowset.serial.SerialClob(str
                .toCharArray());
        return c;
    }

    /**
     *
     * Description:将string对象转换为Clob对象,Blob处理方式与此相同
     *
     * @param str
     * @param lob
     * @return
     * @throws Exception
     * @mail zhengzhou_1990@hotmail.com
     */
    public static Clob oracleStr2Clob(String str, Clob lob) throws Exception {
        Method methodToInvoke = lob.getClass().getMethod(
                "getCharacterOutputStream", (Class[]) null);
        Writer writer = (Writer) methodToInvoke.invoke(lob, (Object[]) null);
        writer.write(str);
        writer.close();
        return lob;
    }

    /**
     * 数据库Clob对象转换为String
     *
     * @param clob
     * @return
     * @throws Exception
     * @mail zhengzhou_1990@hotmail.com
     */
    @SuppressWarnings("unused")
    public static String clobToString(Clob clob) {
        if (clob == null)
            return null;
        try {
            // 以 java.io.Reader 对象形式（或字符流形式）
            // 检索此 Clob 对象指定的 CLOB 值 --Clob的转换
            Reader inStreamDoc = clob.getCharacterStream();
            // 取得clob的长度
            char[] tempDoc = new char[(int) clob.length()];
            inStreamDoc.read(tempDoc);
            inStreamDoc.close();
            return new String(tempDoc);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException es) {
            es.printStackTrace();
        }
        return null;
    }

    /**
     * 限制 图片 大小 (工具方法.)
     *
     * @param width
     *            宽度
     * @param height
     *            高度
     * @return
     */
    protected boolean astrictImgSize(String fname, double width, double height) {
        boolean flag = true;
        File fi = new File(fname);
        if (fi.exists()) {
            BufferedImage bi = null;
            try {
                bi = ImageIO.read(fi);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                flag = false;
                return flag;
            }
            if (bi.getWidth() > width) {
                flag = false;
            } else {
                if (height != -1) {
                    return bi.getHeight() <= height;
                }
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 根据文件路径 得到文件里第一行的值
     *
     * @param filepath
     * @return
     */
    private static void getContentWithFile(String filepath) {
        if (filepath == null || "".equals(filepath.trim())) // 参数是否正确传递
            return;
        File f = new File(filepath);
        if (!f.exists())
            return; // 文件是否存在
        String lineInfo = null;
        FileInputStream fr = null;
        BufferedReader br = null;
        try {
            fr = new FileInputStream(filepath);
            InputStreamReader is = new InputStreamReader(fr, "UTF-8");
            br = new BufferedReader(is);
            // while(br.ready()){
            lineInfo = br.readLine();
            // }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)
                    br.close();
                if (fr != null)
                    fr.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (lineInfo != null) {
            _SUB_CONTENT = lineInfo.split(",");
        }
        System.out.println(_SUB_CONTENT);
    }

    /**
     * 根据字符串得到在文件中的位置。没有发现则返回100
     *
     * @param filepath
     *            文件的位置
     * @param positionStr
     *            字符串
     * @return
     */
    public static int getPosition(String filepath, String positionStr) {
        if (filepath == null || "".equals(filepath.trim())
                || positionStr == null || "".equals(positionStr.trim()))
            return 100;
        if (_SUB_CONTENT == null)
            getContentWithFile(filepath);
        if (_SUB_CONTENT == null || _SUB_CONTENT.length < 1)
            return 100;
        int cnLen = _SUB_CONTENT.length;
        int i = 0;
        for (; i < cnLen; i++) {
            String fileStr = _SUB_CONTENT[i].toString().trim();
            System.out.println(fileStr + "    " + positionStr + "   "
                    + fileStr.equals(positionStr.trim()));
            if (fileStr.equals(positionStr.trim())) {
                break;
            }

        }
        return i;
    }

    /**************************** 文件读写区域 *************************************************/
    /**
     * 写入文件
     *
     * @param request
     *            HttpServletRequest对象
     * @param fpath
     *            文件名称 (必须存在此文件)
     * @param writeContent
     *            要写入的内容
     * @return 是否写入成功
     */
    public static boolean writeFile(HttpServletRequest request, String fpath,
                                    String writeContent) {
        if (request == null || fpath == null)
            return false;
        if (writeContent == null)
            writeContent = "";

        PrintWriter pw = null;
        try {
            pw = new PrintWriter(request.getRealPath("/") + "initial/"
                    + fpath.trim());
            pw.write(writeContent);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        } finally {
            if (pw != null)
                pw.close();
        }

        return true;
    }

    /**
     * 写入文件
     *
     * @param request
     *            HttpServletRequest对象
     * @param directorypath
     *            文件名称 (必须存在此文件)
     * @param writeContent
     *            要写入的内容
     * @return 是否写入成功
     */
    public static boolean writeFile(HttpServletRequest request
            , String directorypath,String fname,
                                    String writeContent) {
        if (request == null || directorypath == null||fname==null)
            return false;
        if (writeContent == null)
            writeContent = "";
        File tmpf=new File(directorypath);
        if(!tmpf.exists()||tmpf.isFile())
            tmpf.mkdirs();
        tmpf=new File(directorypath+"/"+fname);


        FileOutputStream outFile = null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            outFile = new FileOutputStream(tmpf,true);
            osw=new OutputStreamWriter(outFile,"UTF-8");
            bw=new BufferedWriter(osw);
            if(writeContent!=null&&writeContent.length()>4000){
                int reLength=(writeContent.length()/4000)+(writeContent.length()%4000>0?1:0);
                for(int i=0;i<reLength;i++){
                    String reStr=null;
                    if(i==reLength-1&&writeContent.length()%4000>0){
                        reStr=writeContent.substring(i*4000);
                    }else
                        reStr=writeContent.substring(i*4000,(i+1)*4000);
                    bw.write(reStr);
                }
            }else
                bw.write(writeContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw!=null)
                    bw.close();
                if(osw!=null)
                    osw.close();
                if (outFile != null)
                    outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
    /**
     * 写入文件
     *
     * @param request
     *            HttpServletRequest对象
     * @param directorypath
     *            文件名称 (必须存在此文件)
     * @param writeContent
     *            要写入的内容
     * @return 是否写入成功
     */
    public static boolean writeFile(ServletContext request
            , String directorypath,String fname,
                                    String writeContent)  {
        if (request == null || directorypath == null||fname==null)
            return false;
        if (writeContent == null)
            writeContent = "";
        File tmpf=new File(directorypath);
        if(!tmpf.exists()||tmpf.isFile())
            tmpf.mkdirs();
        tmpf=new File(directorypath+"/"+fname);
       if(!tmpf.exists()||tmpf.isDirectory()){
            try{
            tmpf.createNewFile();
            }catch(Exception e){e.printStackTrace();}
        }

        FileOutputStream outFile = null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            outFile = new FileOutputStream(tmpf,true);
            osw=new OutputStreamWriter(outFile,"UTF-8");
            bw=new BufferedWriter(osw);
            if(writeContent!=null&&writeContent.length()>4000){
                int reLength=(writeContent.length()/4000)+(writeContent.length()%4000>0?1:0);
                for(int i=0;i<reLength;i++){
                    String reStr=null;
                    if(i==reLength-1&&writeContent.length()%4000>0){
                        reStr=writeContent.substring(i*4000);
                    }else
                        reStr=writeContent.substring(i*4000,(i+1)*4000);
                    bw.write(reStr);
                }
            }else
                bw.write(writeContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(bw!=null)
                    bw.close();
                if(osw!=null)
                    osw.close();
                if (outFile != null)
                    outFile.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * 读取文件
     *
     * @param request
     *            HttpServletRequest对象
     * @param fpath
     *            文件名称 (必须存在此文件)
     * @return 读取的内容(注意：多行的时候 \n 代表换行)
     * @throws IOException
     */
    public static String readFile(HttpServletRequest request, String fpath)
            throws IOException {
        if (request == null || fpath == null || fpath.trim().equals(""))
            return null;

        BufferedReader br = null;
        StringBuilder content = null;
        try {
            br = new BufferedReader(new FileReader(request.getRealPath("/")
                    + "initial/" + fpath.trim()));
            String lineContent = null;
            while ((lineContent = br.readLine()) != null) {
                if (content == null)
                    content = new StringBuilder(lineContent);
                else
                    content.append("\n" + lineContent);
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        } finally {
            if (br != null)
                br.close();
        }

        return content == null ? null : content.toString();
    }

    /**
     * 冒泡排序(特殊)
     *
     * @param s 数字String类型数组
     * @return 排序后相对之前的索引号
     */
    public static int[] sort(String[] s) {
        int[] returnIdx = new int[s.length];
        // 重新复制数组
        String[] n = new String[s.length];
        for (int i = 0; i < s.length; i++)
            n[i] = s[i];
        // 将原始的数组进行交换
        for (int i = 0; i < s.length - 1; i++) {
            String temp;
            for (int j = 0; j < s.length - i - 1; j++) {
                Double tempI = Double.parseDouble(s[j].trim());
                Double tempJ = Double.parseDouble(s[j + 1].trim());
                if (tempI < tempJ) {
                    temp = s[j + 1];
                    s[j + 1] = s[j];
                    s[j] = temp;
                }
            }
        }

        // 判断索引
        for (int i = 0; i < s.length; i++) {
            Double tempI = Double.parseDouble(s[i].trim());
            for (int j = 0; j < n.length; j++) {
                Double tempJ = Double.parseDouble(n[j].trim());
                if (tempI.equals(tempJ)) {
                    boolean flag = false;
                    for (int idx : returnIdx) {
                        if (j == idx) {
                            flag = true;
                        }
                    }
                    if (!flag) {
                        returnIdx[i] = j;
                        continue;
                    }
                }
            }
        }
        return returnIdx;
    }

    // 判断

    /**
     *判断字符串是否为Null Or Empty
     *
     * @param strText
     *            要判断的字符串
     * @return 是否为Null Or Empty
     */
    public static boolean nullOrEmpty(String strText) {
        if (strText == null || strText.trim().length()<1)
            return false;
        return true;
    }

    /**
     * 正则验证 (在UtilTool类中)
     *
     * @author 郑舟(2010-06-31 下午02:20:16)
     * @param expression
     * @param text
     * @return boolean
     */
    public static boolean matchingText(String expression, String text) {
        boolean bool = false;
        if (expression != null && !"".equals(expression) && text != null
                && !"".equals(text.toLowerCase())) {
            Pattern p = Pattern.compile(expression); // 正则表达式
            Matcher m = p.matcher(text.toLowerCase()); // 操作的字符串
            bool = m.matches();
        }
        return bool;
    }

    /**
     **MD5实例
     */
    public static String gender_MD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }

    /**
     * 判断是否为数字
     *
     * @param strNum
     * @return
     */
    public static boolean isNumber(String strNum) {
        if (strNum != null && strNum.indexOf("-") == 0)
            return strNum.replace("-", "").matches("[0-9]{1,}");
        else
            return strNum.matches("[0-9]{1,}");
    }

    /**
     * 判断是否为数字
     *
     * @param strNum
     * @return
     */
    public static boolean isDouble(String strNum) {
        try {
            Double.parseDouble(strNum);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /*********** 图片操作 *************/
    /**
     * 图片切割
     *
     * @param sourcePath
     *            图片地址
     * @param descpath
     *            图片存放的地址
     * @param width
     *            切割宽度
     * @param height
     *            切割高度
     * @param x1
     *            开始x结点（left）
     * @param y1
     *            开始y结点（top）
     *
     *            图片高度
     * @throws IOException
     */
    public static void cutImg( String sourcePath, String descpath,int width, int height,int x1, int y1) throws IOException {
        FileInputStream is = null;
        ImageInputStream iis = null;
        try {
            is = new FileInputStream(sourcePath);
            String fileSuffix = sourcePath.substring(sourcePath
                    .lastIndexOf(".") + 1);
            Iterator<ImageReader> it = ImageIO
                    .getImageReadersByFormatName(fileSuffix);
            ImageReader reader = it.next();
            iis = ImageIO.createImageInputStream(is);
            reader.setInput(iis, true);
            ImageReadParam param = reader.getDefaultReadParam();
            Rectangle rect = new Rectangle(x1, y1, width, height);
            param.setSourceRegion(rect);
            BufferedImage bi = reader.read(0, param);
            ImageIO.write(bi, fileSuffix, new File(descpath));
        } catch (Exception ex) {
            //ex.printStackTrace();
            System.out.println(ex);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                is = null;
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
                iis = null;
            }
        }
    }

    /**
     *
     * @param file
     *            要重绘的图片
     * @param redrawImage
     *            重绘后的图片
     * @param redrawWidth
     *            重绘图片的宽度
     * @param redrawHeight
     *            重绘图片的高度
     */
    public static void Redraw(File file, String redrawImage, int redrawWidth,
                              int redrawHeight) {
        try {
            String lastName=file.getName().substring(file.getName().lastIndexOf(".")+1);

            if(lastName.equalsIgnoreCase("PNG")){

                BufferedImage src = ImageIO.read(file);  // 读入源图像

                //  获取一个宽、长是原来scale的图像实例
                Image image = src.getScaledInstance(redrawWidth, redrawHeight, Image.SCALE_DEFAULT);

                //缩放图像
                BufferedImage tag = new BufferedImage(redrawWidth, redrawHeight, BufferedImage.TYPE_INT_ARGB_PRE);
                Graphics2D g = tag.createGraphics();

                g.drawImage(image, 0, 0, null); // 绘制缩小后的图
                g.dispose();

                OutputStream out = new FileOutputStream(redrawImage);
                ImageIO.write(tag, "png", out);// 输出
                out.close();



            }else{
                Image image = ImageIO.read(file);
                BufferedImage bufImage = new BufferedImage((int) redrawWidth,
                        (int) redrawHeight, BufferedImage.TYPE_INT_RGB);
                /*
                 * // 选择图像平滑度比缩放速度具有更高优先级的图像缩放算法。
                 * bufImage.getGraphics().drawImage(image
                 * .getScaledInstance(redrawWidth,redrawHeight, Image.SCALE_SMOOTH),
                 * 0, 0, null);
                 */
                /*
                 * //使用 Area Averaging 图像缩放算法。
                 * bufImage.getGraphics().drawImage(image
                 * .getScaledInstance(redrawWidth,redrawHeight,
                 * Image.SCALE_AREA_AVERAGING), 0, 0, null);
                 */
                // 选择一种图像缩放算法，在这种缩放算法中，缩放速度比缩放平滑度具有更高的优先级。
                bufImage.getGraphics().drawImage(
                        image.getScaledInstance(redrawWidth, redrawHeight,
                                Image.SCALE_FAST), 0, 0, null);
                FileOutputStream out = new FileOutputStream(redrawImage);
                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
                encoder.encode(bufImage);
                out.close();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void  ReduceImg(File file,String filename,int default_w,int default_h){
        Long w;
        Long h;
        float cos;
        try{
            BufferedImage src = ImageIO.read(file);  // 读入源图像
            w=Long.valueOf(src.getWidth());
            h=Long.valueOf(src.getHeight());


            if (w > default_w
                    || h > default_h) {
                // 该图片进行缩放
                // 得到默认比便
                if(w>h){
                    if(w>default_w){
                        cos=default_w*1.0f/w*1.0f;
                        w=Math.round((w * default_w * 1.0 / w));
                        h=Math.round(h*1.0*cos);
                    }
                }
                if(w<=h){
                    if(h>default_h){
                        cos=default_h*1.0f/h*1.0f;
                        h=Math.round((h * default_h * 1.0 / h));
                        w=Math.round(w*1.0*cos);
                    }
                }
            }
            UtilTool.Redraw(file,filename,w.intValue(),h.intValue());
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    /**
     * 进行编码设置
     *
     * @param src
     * @return
     */
    public static String escape(String src) {
        int i;
        char j;
        StringBuffer tmp = new StringBuffer();
        tmp.ensureCapacity(src.length() * 6);
        for (i = 0; i < src.length(); i++) {
            j = src.charAt(i);
            if (Character.isDigit(j) || Character.isLowerCase(j)
                    || Character.isUpperCase(j))
                tmp.append(j);
            else if (j < 256) {
                tmp.append("%");
                if (j < 16)
                    tmp.append("0");
                tmp.append(Integer.toString(j, 16));
            } else {
                tmp.append("%u");
                tmp.append(Integer.toString(j, 16));
            }
        }
        return tmp.toString();
    }
    /**
     * 功能权限比较
     * @param u 当前登陆的对像
     * @param serviceid 比较的ID
     * @return 是否有该权限
     */
    public static boolean functionRight(UserInfo u,BigDecimal serviceid){
        boolean returnFlag=false;
        if(u==null||u.getFunctionRightList()==null||u.getFunctionRightList().size()<1)
            return returnFlag;
        for (RightUser ru : u.getFunctionRightList()) {
            if(ru!=null&&ru.getPagerightid()!=null&&ru.getPagerightid().intValue()==serviceid.intValue()){
                returnFlag=true;
                break;
            }
        }
        return returnFlag;
    }

    /*********** 图片操作 *************/
    /**
     * 得到图片的长宽，大小等信息
     */
    public static Map<String,Long> getImageProperty(File f){
        Map<String, Long> map = new HashMap<String, Long>(3);
        if(!f.exists())
            return null;
        try {
            FileInputStream fis = new FileInputStream(f);
            BufferedImage buff = ImageIO.read(f);
            map.put("w", buff.getWidth() * 1L);
            map.put("h", buff.getHeight() * 1L);
            map.put("s", f.length());
            fis.close();
        } catch (FileNotFoundException e) {
            System.err.println("所给的图片文件" + f.getPath() + "不存在！计算图片尺寸大小信息失败！");
            map = null;
        } catch (IOException e) {
            System.err.println("计算图片" + f.getPath() + "尺寸大小信息失败！");
            map = null;
        }
        return map;
    }

    public static String blobToString(Blob blob){
        if(blob==null)
            return null;
        String result = "";
        try {
            int bolblen = (int)blob.length();
            byte[] data = blob.getBytes(1, bolblen);
            result=new String(data, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 改英文% '改成中文 ％　‘
     * @param str1
     * @return
     */
    public static String clearSQLStr(String str1){
        if(str1!=null&&str1.trim().length()>0)
            str1=str1.replaceAll("'","‘");
        return  str1;
    }

    /**
     * 转换Office 2 Swf
     * @param request request对象
     * @param resid  源文件路径，如："e:/test.docx"
     * @param suffix  源文件名
     * @return
     */
    public static  boolean Office2Swf(HttpServletRequest request,String resid,String suffix){
        boolean flag=false;
        if(resid==null||suffix==null)
            return false;
        //String lastname=filename.substring(filename.lastIndexOf("."));
        //String firstname=filename.substring(0, filename.lastIndexOf("."));
        String lastname=suffix.toLowerCase();
        String firstname="001";
        //String destPath=utilproperty.getProperty("RESOURCE_SERVER_PATH")+"/"+getConvertPath(resid,suffix);
        String destPath=getResourceLocation(Long.parseLong(resid),2)+"/"+getConvertPath(resid,suffix);
        String filename="";
        //TXT文件要拷贝成ODT文件后转换，否则出现乱码
        if(UtilTool.matchingText(UtilTool._TXT_SUFFIX_TYPE_REGULAR, lastname)){
            filename=firstname+".odt";
        }
        if(UtilTool.matchingText(UtilTool._PDF_SUFFIX_TYPE_REGULAR, lastname)){
            Pdf2Swf.PDF2SWF(request,getResourceLocation(Long.parseLong(resid),2)+"/"+getResourceUrl(resid,suffix), destPath);
            return true;
        }
        System.out.println("filepath:"+getResourceLocation(Long.parseLong(resid),2)+"/"+getResourceUrl(resid,suffix));
        File f=new File(getResourceLocation(Long.parseLong(resid),2)+"/"+getResourceUrl(resid,suffix));

        String pdfpath=getResourceLocation(Long.parseLong(resid),2)+"/"+getResourceUrl(resid,".pdf");
        if(!new File(pdfpath).exists()){
            Office2Pdf office2pdf=new Office2Pdf();
            pdfpath=office2pdf.office2pdf(request,getResourceLocation(Long.parseLong(resid),2)+"/"+getResourceUrl(resid,suffix), null);
        }

        System.out.println("pdfpath:"+pdfpath);
        if(pdfpath!=null&&pdfpath.length()>0){
            if(f.exists()&&f.length()>2097152){
                System.out.println("file > 2M wait....... ");
                try{
                   // Thread.sleep(TimeUnit.MINUTES.toMillis(5));
                }catch (Exception e){

                }
            }
            Pdf2Swf.PDF2SWF(request,pdfpath, destPath);
            //SWF生成后删除PDF
            if(new File(pdfpath).exists()){
                new File(pdfpath).delete();
            }
            flag=true;
        }else{
            System.out.println("con't find pdf,resource file is epmty!");
        }

        return flag;
    }

    /**
     * 获取文件类型
     * @return
     */
    public static String getResourseType(String filename) {
        if (filename== null||filename.lastIndexOf(".")<0)
            return null;
        String suffix=filename.substring(filename.lastIndexOf(".")).toLowerCase();
        boolean flag = UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "jpeg";
        flag = UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "video";
        flag = UtilTool.matchingText(UtilTool._MP3_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "mp3";
        flag = UtilTool.matchingText(UtilTool._DOC_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "doc";
        flag = UtilTool.matchingText(UtilTool._SWF_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "swf";
        return "other";
    }





    /**
     * 获取转换文件类型
     * @return
     */
    public static String getConvertResourseType(String filename) {
        if (filename== null||filename.lastIndexOf(".")<0)
            return null;
        String suffix=filename.substring(filename.lastIndexOf(".")).toLowerCase();
        boolean flag = UtilTool.matchingText(UtilTool._IMG_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "jpeg";
        flag = UtilTool.matchingText(UtilTool._VIEW_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "video";
        flag = UtilTool.matchingText(UtilTool._MP3_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "mp3";
        flag = UtilTool.matchingText(UtilTool._DOC_CONVERT_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "doc";
        flag = UtilTool.matchingText(UtilTool._SWF_SUFFIX_TYPE_REGULAR, suffix);
        if (flag)
            return "swf";
        return "other";
    }
    /**
     * 将文件的大小改成有单位的形式
     * @param fileSize
     * @return
     */
    public static String formatFileSize(Integer fileSize) {
        String unit = " B";
        if (fileSize > 1024) {
            unit = " KB";
            fileSize = fileSize/1024;
            if (fileSize > 1024) {
                unit = " MB";
                fileSize = fileSize / 1024;
                if (fileSize > 1024) {
                    unit = " G";
                    fileSize = fileSize / 1024;
                    if(fileSize>1024){
                        unit=" T";
                        fileSize = fileSize / 1024;
                    }
                }
            }
        }
        return Math.round(fileSize * 100) / 100 + unit;
    }

    /**
     * 得到资源文件夹
     * @param resid   资源ID
     * @return
     */
    public static String getResourceMd5Directory(String resid){
        if(resid==null)return null;
        return MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(resid)+KEY);
    }
    /**
     * 得到资源系统文件路径
     * @param resid 资源ID
     * @param suffix  文件名
     * @return
     */
    public static String getResourceUrl(String resid,String suffix){
        if(suffix==null||resid==null)
            return null;
        String path=getResourceMd5Directory(resid);
        String lastName=suffix.toLowerCase();
        //String file=gender_MD5(filename.replaceAll("'"," ").replaceAll(","," ").trim());
        String file="001";
        //.odt转换时增加.txt后缀
        if(lastName!=null&&lastName.equals(".txt")){
            lastName=".odt";
        }
        return path+"/"+file+lastName;
    }


    /**
     * 得到资源系统文件路径
     * @param resid 资源ID
     * @param suffix  文件名
     * @return
     */
    public static String getResourceFileUrl(String resid,String suffix){
        if(suffix==null||resid==null)
            return null;
        String path=getResourceMd5Directory(resid);
        String lastName=suffix;
        //String file=gender_MD5(filename.replaceAll("'"," ").replaceAll(","," ").trim());
        String file="001";
        //.odt转换时增加.txt后缀
        return path+"/"+file+lastName;
    }

    /**
     * 得到文件路径 的前部份
     * @param resid 资源ID >0是网校资源 <0是分校资源
     * @param type   1:域名路径   2:真实路径
     * @return
     */
    public static String getResourceLocation(Long resid,Integer type){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String returnVal=null;
        if(resid>0){
            if(type==null||type==1)     //云端
                returnVal=request.getSession().getAttribute("RESOURCE_CLOUD_DOWN_DIRECTORY_HEAD").toString();
            else if(type==2)                //云端真实路径
                returnVal=UtilTool.utilproperty.getProperty("RESOURCE_CLOUD_SERVER_PATH");
        }else{
            if(type==null||type==1)     //本地
                returnVal=request.getSession().getAttribute("RESOURCE_FILE_PATH_HEAD").toString();
            else if(type==2)                //本地真实路径
                returnVal=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH");
        }
        return returnVal;
    }

    public static void main(String[] args){

    }




    public String getTomcatPath(){

        //return getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        return getClass().getProtectionDomain().getCodeSource().getLocation().getHost();

    }
    /**
     * 得到资源系统文件路径
     * @param resid 资源ID
     * @param filename  文件名
     * @return
     */
    public static String getResourceViewUrl(HttpServletRequest request,String resid,String filename,String path){
        if(resid==null||filename==null)
            return null;
        String residfilename=path;
        if(path==null||path.trim().length()<1)
            residfilename=MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(resid)+KEY);
        String file=gender_MD5(filename.replaceAll("'"," ").replaceAll(","," ").trim());
        //return UtilTool.utilproperty.getProperty("RESOURCE_FILE_PATH_HEAD")+residfilename+"/"+file+".swf";
        //String returnPath=UtilTool.utilproperty.getProperty("RESOURCE_FILE_UPLOAD_HEAD")+"uploadfile/"+residfilename+"/"; //"http://"+request.getServerName()+":"+request.getLocalPort()+"/fileoperate/" +
        String returnPath=UtilTool.getResourceLocation(Long.parseLong(resid),1)+"/"+residfilename+"/"; //"http://"+request.getServerName()+":"+request.getLocalPort()+"/fileoperate/" +
        String filetype=getResourseType(filename);
        if(filetype.equals("doc"))
            returnPath+=file+".swf";
        else if(filetype.equals("jpeg"))
            returnPath+=file+"_smail.jpg";
        else if(filetype.equals("video"))
            returnPath+=file+".mp4";
        else if(filetype.equals("mp3"))
            returnPath+=file+"."+filename.substring(filename.lastIndexOf("."));
        return returnPath;
    }

    /**
     * 得到转换SWF文件目标路径
     * @param resid
     * @param suffix
     * @return
     */
    public static String getConvertPath(String resid,String suffix){
        if(resid==null||suffix==null)
            return null;
        String lastName=suffix.toLowerCase();
        //String file=gender_MD5(filename.replaceAll("'"," ").replaceAll(","," ").trim());
        String file="001";
        //.odt转换时增加.txt后缀
//        if(lastName!=null&&lastName.equals(".odt")){
//            file=gender_MD5(filename.substring(0, filename.lastIndexOf("."))+".txt".replaceAll("'"," ").replaceAll(","," ").trim());
//        }
        return MD5_NEW.getMD5Result(MD5_NEW.getMD5Result(resid)+KEY)+"/"+file+".swf";
    }
    /**
     * 准备路径信息进行上传
     * @param txtfilepath
     * @return
     */
    public static boolean uploadResourceToTotalSchool(String txtfilepath){
        if(txtfilepath==null||txtfilepath.trim().length()<1){
            return false;
        }
        File tmpf=new File(txtfilepath);
        if(!tmpf.exists()||tmpf.isDirectory())
            return false;
        //读取文件
        BufferedReader br = null;
        //StringBuilder content = null;
        try {
            br = new BufferedReader(new FileReader(tmpf));
            String lineContent = null;
            List<String> filepath=new ArrayList<String>();
            Integer whileSize=0;
            while ((lineContent = br.readLine()) != null) {
                if(lineContent.trim().length()>0){
                    whileSize++;
                    filepath.add(lineContent.trim());
                    //输出，测试
                    System.out.println(lineContent.trim());
                    if(whileSize%100==0){
                        //通过FTP进行传输
                        FtpClientUtil.upload(filepath);
                        //发送文件完成，清空继续组织
                        filepath=new ArrayList<String>();
                    }
                }
            }
            if(filepath!=null&&filepath.size()>0){
                //发送文件
                FtpClientUtil.upload(filepath);
                //发送文件完成，清空继续组织
                filepath=new ArrayList<String>();
//                SendFile s = new SendFile();
//                if (s.sendFileOperate(filepath)){
//                    System.out.println("fa song wenjian success");
//                    //发送文件完成，清空继续组织
//                    filepath=new ArrayList<String>();
//                }else{
//                    System.out.println("fa song wenjian error!zhongzhi fasong！");
//                    return false;
//                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }catch(IOException e){
            e.printStackTrace();return false;
        } finally {
            if (br != null)
                try{
                    br.close();
                }catch(Exception e){}
        }
        return true;
    }



    /**
     * 动态模版权类型
     * @author zhengzhou		 *
     */
    public static enum MYINFO_TEMPLATE_TYPE{
        /**
         * 发起活动
         */
        FAQI_ACTIVITY{public Integer getValue(){return 1;}},
        /**
         * 申请活动
         */
        SHENQIAN_ACTIVITY{public Integer getValue(){return 2;}},
        /**
         * 审核活动
         */
        SHENHE_ACTIVITY{public Integer getValue(){return 3;}},
        /**
         * 修改活动
         */
        XIUGAI_ACTIVITY{public Integer getValue(){return 4;}},
        /**
         * 删除活动
         */
        DELETE_ACTIVITY{public Integer getValue(){return 5;}},
        /**
         * 添加同行评价
         */
        TIANJIA_THPJ{public Integer getValue(){return 6;}},
        /**
         * 修改同行评价
         */
        XIUGAI_THPJ{public Integer getValue(){return 7;}},
        /**
         * 添加学生评价评价
         */
        TIANJIA_XSPJ{public Integer getValue(){return 8;}},
        /**
         * 修改学生评价
         */
        XIUGAI_XSPJ{public Integer getValue(){return 9;}},
        /**
         *发布公告
         */
        FABU_NOTICE{public Integer getValue(){return 10;}},
        /**
         * 发布网上公示
         */
        FABU_INTERNETNOTICE{public Integer getValue(){return 11;}},
        /**
         * 校风提醒
         */
        TIXIAN_ETHOS{public Integer getValue(){return 12;}},
        /**
         * 教学平台
         */
        TJRW_JXPT{public Integer getValue(){return 13;}},
        /**
         * 教师调动提醒
         */
        JSTD_JCPT{public Integer getValue(){return 14;}},
        /**
         * 教师虚似班级申请
         */
        TEA_XLBJ_SQ{public Integer getValue(){return 15;}},
        /**
         * 教师管理虚假班级申请
         */
        XLBJ_ADMIN_SQ{public Integer getValue(){return 16;}},
        /**
         * 新学期创建起止时间
         */
        NEW_TERM_TIME_SETTING{public Integer getValue(){return 17;}},
        /**
         * 新学期创建授课信息
         */
        NEW_TERM_CLASSUSER_SETTING{public Integer getValue(){return 18;}},
        /**
         * 新学期创建部门
         */
        NEW_TERM_DEPT_SETTING{public Integer getValue(){return 19;}},
        /**
         * 用户数据修改
         */
        YHSJ_UPDATE{public Integer getValue(){return 20;}},
        /**
         * 角色权限添加
         */
        JSQX_TIANJIA{public Integer getValue(){return 21;}}	,
        /**
         * 角色权限取消
         */
        JSQX_QUXIAO{public Integer getValue(){return 22;}},
        /**
         * 资源审核未通过
         */
        RESOURCE_SHENHE_NO{public Integer getValue(){return 23;}},
        /**
         * 资源审核通过
         */
        RESOURCE_SHENHE_YES{public Integer getValue(){return 24;}};
        public abstract Integer getValue();
    }
    /**
     * 动态MSG_ID类型
     * @author zhengzhou
     */
    public static enum MYINFO_MSG_TYPE{
        /**
         * 资源审核通过
         *   1:公告(ggtd)
         *2:申请(sqtd)
         *3:审核(shtd)
         *4:报名(bmtd)
         *5:录取(lqtd)
         *6:发帖(fttd)
         *7:任命(rmtd)
         *8:任务(rwtd)
         *9:成绩(cjtd)
         *10:活动(hdtd)
         *11:用户修改(yhxgtd)
         *12:调班(tbtd)
         *13:校风提醒(xftd)
         *14:通知(tztd)
         */
        NOTICE{public Integer getValue(){return 1;}},
        SHENQING{public Integer getValue(){return 2;}},
        SHENHE{public Integer getValue(){return 3;}},
        BAOMING{public Integer getValue(){return 4;}},
        LUQU{public Integer getValue(){return 5;}},
        FATIE{public Integer getValue(){return 6;}},
        RENMING{public Integer getValue(){return 7;}},
        RENWU{public Integer getValue(){return 8;}},
        CHENGJI{public Integer getValue(){return 9;}},
        HUODONG{public Integer getValue(){return 10;}},
        YONGHUXIUGAI{public Integer getValue(){return 11;}},
        TIAOBAN{public Integer getValue(){return 12;}},
        XFTX{public Integer getValue(){return 13;}},
        TONGZHI{public Integer getValue(){return 14;}};
        public abstract Integer getValue();
    }

    /**
     * 拷贝目录下文件至新目录
     * @param srcPath
     * @param targPath
     */
    public static void CopyDirecToryFile(File srcPath, File targPath) {
        if (!targPath.exists()) {
            targPath.mkdir();
        }
        if (srcPath.isFile()) {
            System.out.println(srcPath.getName() + " 是文件!");
        }
        else{
            File[] files=srcPath.listFiles();
            for(File file:files){
                if(file.isDirectory())
                    CopyDirecToryFile(file,new File(targPath,file.getName()));
            }
        }
    }

    // 复制文件
    public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }



    public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
        // 新建目标目录
        if(!new File(targetDir).exists())
            (new File(targetDir)).mkdirs();

        // 获取源文件夹当前下的文件或目录
        File[] file = (new File(sourceDir)).listFiles();
        for (int i = 0; i < file.length; i++) {
            if (file[i].isFile()) {
                // 源文件
                File sourceFile = file[i];
                // 目标文件
                File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
                copyFile(sourceFile, targetFile);
            }
            if (file[i].isDirectory()) {
                // 准备复制的源文件夹
                String dir1 = sourceDir + "/" + file[i].getName();
                // 准备复制的目标文件夹
                String dir2 = targetDir + "/" + file[i].getName();
                copyDirectiory(dir1, dir2);
            }
        }
    }

    /**
     * 写入XML文件中
     * @param filepath  XML文件路径
     * @param t   要写入的实体 要写入的实体
     * @param <T>
     * @return
     */
    public static <T> boolean addDateToXml(String filepath,T t){
        if(t==null||filepath==null)return false;
        Method[] med=t.getClass().getMethods();
        List<String> columnsList=new ArrayList<String>();
        List<Object> valueList=new ArrayList<Object>();
        for(Method md:med){
            if(md.getName().length()>3&&md.getName().indexOf("get")==0){
                Object obj=null;
                try{
                    obj=md.invoke(t);;
                    //System.out.println(md.getName()+":"+obj);
                }catch(Exception e){
                    e.printStackTrace();
                }
                String key=md.getName().substring(3);
                Object val=obj;
                if(!columnsList.contains(key)){
                    columnsList.add(key);
                    valueList.add((val==null?"NULL":val));
                }
            }
        }
        return OperateXMLUtil.addXml(filepath,columnsList,valueList.toArray());
    }


    /**
     * 输出图片流
     * @param response
     * @param path
     * @throws Exception
     */
    public static void writeImage(HttpServletResponse response,String path) throws Exception {
        if(new File(path).exists()){
            response.reset();
            ServletOutputStream output = response.getOutputStream();
            InputStream in = new FileInputStream(path);
            byte tmp[] = new byte[256];
            int i=0;
            while ((i = in.read(tmp)) != -1) {
                output.write(tmp, 0, i);
            }
            in.close();
            output.flush(); //强制清出缓冲区
            output.close();
        }
    }


    /**
     * 发送POST
     * @param urlstr URL
     * @param paramMap  参数
     * @param  requestEncoding 参数编码
     */
    public static JSONObject sendPostUrl(String urlstr,Map<String,String> paramMap,String requestEncoding){
        HttpURLConnection httpConnection=null;
        URL url;
        int code;
        try {
            //组织参数
            StringBuffer params = new StringBuffer();
            if(paramMap!=null&&paramMap.size()>0){
                for (Iterator iter = paramMap.entrySet().iterator(); iter
                        .hasNext();)
                {
                    Map.Entry element = (Map.Entry) iter.next();
                    params.append(element.getKey().toString());
                    params.append("=");
                    params.append(URLEncoder.encode(element.getValue().toString(),requestEncoding));
                    params.append("&");
                }

                if (params.length() > 0)
                {
                    params = params.deleteCharAt(params.length() - 1);
                }
            }

                url = new URL(urlstr);

                httpConnection = (HttpURLConnection) url.openConnection();

                httpConnection.setRequestMethod("POST");
                if(params!=null)
                    httpConnection.setRequestProperty("Content-Length",
                            String.valueOf(params.toString().length()));
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
                    outputStreamWriter.write(params.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();

                code = httpConnection.getResponseCode();
            } catch (Exception e) {			// 异常提示
                System.out.println("异常错误!TOTALSCHOOL未响应!");
                if(httpConnection!=null)httpConnection.disconnect();
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
                    if(httpConnection!=null)httpConnection.disconnect();
                } catch (IOException e) {
                    System.out.println("异常错误!");
                    if(httpConnection!=null)httpConnection.disconnect();
                    return null;
                }
            }else if(code==404){
                if(httpConnection!=null)httpConnection.disconnect();
                // 提示 返回
                System.out.println("异常错误!404错误，请联系管理人员!");
                return null;
            }else if(code==500){
                if(httpConnection!=null)httpConnection.disconnect();
                System.out.println("异常错误!500错误，请联系管理人员!");
                return null;
            }
            String returnContent=null;
            try {
                returnContent=new String(stringBuffer.toString().getBytes("gbk"),requestEncoding);
            } catch (UnsupportedEncodingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //转换成JSON
            System.out.println(returnContent);
            JSONObject jb=JSONObject.fromObject(returnContent);
            return jb;
    }

    /**
     * 获取当前距某个时间点还有多长时间，返回某天、多少小时前、多少分钟前等
     * @time 两个时间相减后的秒数
     */
    public static String convertTimeForTask(int times,String ctime){
        String returnVal = "";
        int time =times;
        int days = 0;
        int hours =0;
        int mins = 0;
        int seconds = 0;
        if(time>0){
            seconds = time%60;
            if(seconds>0||time>=60){
                mins = time/60;
            }else{
                seconds = seconds*60;
            }
            if(mins>0){
                hours = mins/60;
            }
            if(hours>0){
                days= hours/24;
            }
        }
        if(days>0){
            String t = ctime;
            t = t.split("-")[1]+"月"+t.split("-")[2].split(" ")[0]+"日";
            returnVal=t;
        }else{
            if(hours>0){
                returnVal=hours+"小时前";
            }else{
                if(mins>0){
                    returnVal =mins+"分钟前";
                }else{
                    if(seconds>0){
                        returnVal = seconds+"秒前";
                    }
                }
            }
        }
        return returnVal;
    }
}
