package com.school.control.base;


import FTsafe.Ry4S;
import com.school.entity.RoleUser;
import com.school.entity.UserInfo;
import com.school.util.JsonEntity;
import com.school.util.PageResult;
import com.school.util.SpringBeanUtil;
import com.school.util.UtilTool;
import com.school.util.jqgrid.JQGridConditionEntity;
import com.school.util.jqgrid.JQGridDetial;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import net.sf.json.JSONObject;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * 控制器基类
 *
 * @author zhengzhou
 */
@Controller
@Scope("prototype")
public class BaseController<T extends java.io.Serializable> {




    /**
     * 得到Manager  以Class为例  getManager(ClassManager.getClass());
     * @param cls    要转换的实体类名称
     * @param <T>
     * @return
     *
     */
    public <T> T getManager(Class<T> cls){
        return  SpringBeanUtil.getBean(cls.getSimpleName(), cls);
    }
    public BaseController(){};


    /******************************** 公共方法区域 ***********************************/


    /**
     * 将参数转换成实体成型
     * @param request
     * @param clazz
     */
    public T getParameter(HttpServletRequest request,
                          Class<? extends Object> clazz) throws Exception {
        Object obj = null;

        if (request == null || clazz == null)
            return null;
        Enumeration<String> paraNameStrArr = request.getParameterNames();
        if (paraNameStrArr != null) {
            obj = clazz.newInstance();
            while (paraNameStrArr.hasMoreElements()) {

                String paraNameStr = paraNameStrArr.nextElement();

                Object paraVal = request.getParameter(paraNameStr);
                // 进行封装成Entity
                if (clazz != null && obj != null&&paraVal!=null) {
                    Method[] methods = clazz.getMethods(); // 获取
                    // 该类的所有方法
                    // 查找所有方法中的set方法 利用反射把取出的字段通过set方法对对象中的字段赋值
                    String colName = paraNameStr;
                    // 获取Set方法名字
                    String methodName = "set"
                            + colName.substring(0, 1).toUpperCase() // 第1个大写
                            + colName.substring(1).replace("_", "");

                    // _empty适用于jgrid的添加
                    if (paraVal != null && !paraVal.equals("_empty"))
                        for (Method m : methods)
                            if (methodName.equals(m.getName())) {
                                Object val=paraVal;
                                //  System.out.println(m.getName()+"  "+val);
                                if(m.getParameterTypes()!=null&&m.getParameterTypes().length>0&&val!=null&&
                                        !m.getParameterTypes()[0].getSimpleName().toLowerCase().equals("string"))
                                    val= ConvertUtils.convert(val.toString(), m.getParameterTypes()[0]);

                                m.invoke(obj, val); // obj:被实例化的类
                                break;
                            }
                }
            }
        }
        return (T) obj;
    }

    /**
     * 验证当前用户的角色
     *
     * @param rid
     */
    public boolean validateRole(HttpServletRequest request,Integer rid) {
        boolean returnVal = false;
        UserInfo cuuser = request.getAttribute("tmpUser")==null?this.logined(request):(UserInfo)request.getAttribute("tmpUser");
        if (cuuser != null && cuuser.getCjJRoleUsers().size() > 0) {
            for (Object ruObj : cuuser.getCjJRoleUsers()) {
                RoleUser ru = (RoleUser) ruObj;
                if (ru.getRoleinfo().getRoleid()!= null
                        && ru.getRoleinfo().getRoleid().intValue() == rid.intValue()) {
                    returnVal = true;
                    break;
                }
            }
        }
        return returnVal;
    }



    /**
     * 获取分页参数的所有信息
     */
    public PageResult getPageResultParameter(HttpServletRequest request) {
        // 获取排序组件信息
        PageResult pageresult = new PageResult();
        String pageNo = request.getParameter("pageResult.pageNo");
        String pageSize = request.getParameter("pageResult.pageSize");
        String orderBy = request.getParameter("pageResult.orderBy");
        String sort = request.getParameter("pageResult.sort");
        String objList=request.getParameter("pageResult.objList");
        if (pageNo != null && pageNo.trim().length() > 0) {
            pageresult.setPageNo(Integer.parseInt(pageNo.trim()));
        }
        if (pageSize != null && pageSize.trim().length() > 0) {
            pageresult.setPageSize(Integer.parseInt(pageSize.trim()));
        }
        if (orderBy != null && orderBy.trim().length() > 0) {
            pageresult.setOrderBy(orderBy.trim());
        }
        if (sort != null && sort.trim().length() > 0) {
            pageresult.setSort(sort);
        }
        //将objList转化成List<object>
        if(objList!=null&&objList.length()>0){
            Map<String, Class> m=new HashMap<String, Class>();
            m.put("rules", JQGridDetial.class);
            JSONObject filt=JSONObject.fromObject(objList);
            JQGridConditionEntity pg=(JQGridConditionEntity)JSONObject.toBean(filt, JQGridConditionEntity.class,m);
            pageresult.setJqgridcondition(pg);
        }
        return pageresult;
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        System.out.println(UtilTool.gender_MD5("1123一五六数学课堂（王秋嬛）.mp4"));
    }
    /**
     * 文件上传用
     */
    private static final long serialVersionUID = 572146812454l;
    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * 用String数组封装多个文件名称
     */
    private String[]uploadFileName;
    public MultipartFile myFile;
    private String fname;
    private List<String>fileNameList=new ArrayList<String>();

    public MultipartFile[] getUpload(HttpServletRequest request){
        MultipartFile[]fileArray=null;
        MultipartHttpServletRequest multipartrequest = (MultipartHttpServletRequest)request;
        Map<String,MultipartFile>fileMaps =multipartrequest.getFileMap();
        if(fileMaps!=null&&fileMaps.size()>0){
            fileArray=new MultipartFile[fileMaps.size()];
            uploadFileName=new String[fileMaps.size()];
            int idx=0;
            for(Map.Entry<String, MultipartFile>entity:fileMaps.entrySet()){
                fileArray[idx]=entity.getValue();
                MultipartFile file = entity.getValue();
                uploadFileName[idx]=file.getOriginalFilename();
                idx+=1;
            }
        }
        return fileArray;
    }

    /**
     * 根据上传文件名  组成生成文件名
     * @param fnameSize
     * @return
     */
    public List<String>getFileArrayName(int fnameSize){
        fileNameList.clear();
        for(int i=0;i<fnameSize;i++){
            fileNameList.add(new Date().getTime()
                    + ""
                    + i
                    + "."
                    + this.getUploadFileName()[i].substring(this
                    .getUploadFileName()[i].lastIndexOf(".")+1));
        }
        return fileNameList;
    }

    /**
     * 文件上传
     * @return
     */
    public boolean fileupLoad(HttpServletRequest request)throws Exception{
        try {
            if(this.getFname()==null){
                this.setFname(this.getFileNameList().get(0));
            }
            String filename = this.getFname();
            File imageFile = new File(request.getRealPath("/")
                    + UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+ filename);
            if(myFile==null)
                myFile=this.getUpload(request)[0];
            copy(myFile.getInputStream(),imageFile);
            myFile=null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 多文件上传
     */
    public boolean fileupLoad(HttpServletRequest request,boolean ismulti){
        boolean flag = true;
        int i=0;
        if(this.getUpload(request)!=null&&this.getUpload(request).length>0){
            try {
                for(;i<this.getUpload(request).length;i++){
                    File f=new File(request.getRealPath("/")+UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+this.getFileNameList().get(i));
                    copy(this.getUpload(request)[i].getInputStream(),f);
                }
            }catch (Exception e) {
                System.out.println(e.getMessage());
                flag=false;
            }
            if(!flag){
                for(int j=0;j<i;j++){
                    File f=new File(request.getRealPath("/")+UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+this.getFileNameList().get(i));
                    if(f.exists())
                        f.delete();
                }
            }
        }
        return flag;
    }

    public List<String> getFileNameList() {
        return fileNameList;
    }
    public void setFileNameList(List<String> fileNameList) {
        this.fileNameList = fileNameList;
    }

    public String[] getUploadFileName() {
        return uploadFileName;
    }
    public void setUploadFileName(String[] uploadFileName) {
        this.uploadFileName = uploadFileName;
    }
    public MultipartFile getMyFile() {
        return myFile;
    }
    public void setMyFile(MultipartFile myFile) {
        this.myFile = myFile;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }

    /**
     * 文件复制 以文件流的形式
     *
     * @param src
     * @param dst
     * @throws Exception
     */
    public static void copy(InputStream src, File dst) throws Exception {

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new BufferedInputStream(src, BUFFER_SIZE);
            out = new BufferedOutputStream(new FileOutputStream(dst),
                    BUFFER_SIZE);
            byte[] buffer = new byte[BUFFER_SIZE];
            while (in.read(buffer) > 0) {
                out.write(buffer);
            }
        } finally {
            if (null != in) {
                in.close();
            }
            if (null != out) {
                out.close();
            }
        }

    }

    /**
     * java 文件下载
     */
    public void doloadLocalFile(HttpServletRequest request,HttpServletResponse response,String fname)
            throws Exception{
        JsonEntity je = new JsonEntity();
        String filename=request.getRealPath("/")+UtilTool.utilproperty.getProperty("USER_UPLOAD_FILE")+"/"+fname;
        File f=new File(filename);
        if(!f.exists()){
            je.setMsg("系统中未发现该文件!请联系管理员审查!");
            je.getAlertMsgAndBack();
            response.getWriter().print(je.toJSON());
            return;
        }
        //读到流中
        InputStream in = new BufferedInputStream(new FileInputStream(f));

        //清除头部空白行
        response.reset();
        //设置输出格式
        response.setContentType("bin");
        response.addHeader("Content-Disposition", "attchment; filename=\"" + filename + "\"");
        //取出流中数据
        byte[] b = new byte[100];
        int len;
        try {
            while( (len=in.read(b)) >0){
                response.getOutputStream().write(b, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /**
     * 存入Session 中。
     *
     * @param request
     *            HttpServletRequest对象
     * @param key
     *            存入Session的key
     * @param val
     *            存入Session的value（值）
     */
    protected void setAttributeSession(HttpServletRequest request, String key,
                                       Object val) {
        if (key == null)
            return;
        request.getSession().setAttribute(key, val);
    }


    /**
     * 是否登录 操作
     *
     * @return
     */
    protected UserInfo logined(HttpServletRequest request) {
        UserInfo usr = (UserInfo) request.getSession().getAttribute("CURRENT_USER");
        return usr;
    }

    public static boolean isNum(String str){
        if(str==null||str.trim().length()==0)
            return false;
        return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
    }



    /**
     * 验证客户端功能权限
     * @param fnid
     * @return
     * @throws Exception
     */
    public Boolean validateFn(HttpServletRequest request,Integer fnid) throws Exception{
        if (request == null)
            return null;
	/*	String fpath = request.getRealPath("/")+"school.txt";
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
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} finally {
			if (br != null)
				br.close();
		}
		DESPlus des = new DESPlus();
		String schoolkeyfn = des.decrypt(content.toString());
		String[] fn = schoolkeyfn.split("\\*");
		String[] fnids = fn[2].split(",");
		String schoolkey = this.validateRy4S().replace("*", "");
		Boolean b = false;
		if(schoolkey.length()>1){		
			String schoolkey2 = schoolkeyfn.split("\\*")[0]+"*"+schoolkeyfn.split("\\*")[1];
			if(schoolkey.trim().equals(schoolkey2.replace("*", "").trim())){
				if(fnid!=null){
					FnClickInfo obj = new FnClickInfo();
					obj.setFnid(fnid);
					List<FnClickInfo> list = this.fnclickManager.getList(obj, null);
					if(list.size()<1){					
						for(int k = 0;k<fnids.length;k++){
							if(Integer.parseInt(fnids[k])==fnid){
								b=true;
							}
						}
						if(b){
							obj.setClickcount(1);
							Boolean arg = this.fnclickManager.doSave(obj);
						}
					}else{
						int count = list.get(0).getClickcount();
						if(count==10){
							b = true;
						}else{
							for(int k = 0;k<fnids.length;k++){
								if(Integer.parseInt(fnids[k])==fnid){
									b=true;
								}
							}
							if(b){
								obj.setClickcount(count+1);
								Boolean arg = this.fnclickManager.doUpdate(obj);
							}
						}
					}
					
				}
			}
		}*/
        return true;
    }

    /**
     * 验证加密狗，功能权限
     * @param ：schoolid*fnid，fnid，fnid。。。。。。
     * @return boolean true or false
     * @author 岳春阳
     * @throws Exception
     * @date  2013-06-18 14:02
     */
    public String validateRy4S() throws Exception{

        //验证有没有权限
        short[] handle=new short[1];
        short[] handleAll=new short[1024];
        int[] lp1=new int[1];
        int[] lp2=new int[2];
        short[] p1=new short[1];
        short[] p2=new short[1];
        short[] p3=new short[1];
        short[] p4=new short[1];
        byte[]  buffer=new byte[1024];
        short	retval;

        Ry4S rockey=new Ry4S();

        p1[0] = -(0xffff-0xc44c)-1;
        p2[0] = -(0xffff-0xc8f8)-1;
        p3[0] = 	0x0799;
        p4[0] = -(0xffff-0xc43b)-1;

        retval=rockey.Rockey(rockey.RY_FIND,handle,lp1,lp2,p1,p2,p3,p4,buffer);//查询狗
        if(retval!=rockey.ERR_SUCCESS)
        {
            return "1";
        }
        System.out.println("Find Rockey4Smart:"+lp1[0]);
        retval=rockey.Rockey(rockey.RY_OPEN,handle,lp1,lp2,p1,p2,p3,p4,buffer);//打开狗
        if(retval!=rockey.ERR_SUCCESS)
        {
            return "2";
        }
        int i=1;



        p1[0]=3;
        p2[0]=499;
        for(int m=0;m<1024;m++)buffer[m]=0;
        retval = rockey.Rockey(rockey.RY_READ,handle,lp1,lp2,p1,p2,p3,p4,buffer);//读锁
        if(retval!=rockey.ERR_SUCCESS)
        {
            return "3";
        }
        return new String(buffer);
    }


    /*****************************资源工具**********************************/
    /**
     * 输出图片
     * @param response
     * @param path
     * @throws Exception
     */
    protected void writeImage(HttpServletResponse response,String path) throws Exception {
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

    /**
     * 强制压缩/放大图片到固定的大小
     * @param  path String 原始图片路径(绝对完整路径)
     * @param w int 新宽度
     * @param h int 新高度
     * @throws IOException
     */
    protected String ImgResize(String path,int w, int h) throws IOException {
        File _file = new File(path);
        _file.setReadOnly();
        File    srcFile  = _file;
        String  fileSuffix = _file.getName().substring(
                (_file.getName().lastIndexOf(".") + 1),
                (_file.getName().length()));
        String fname=UtilTool.utilproperty.getProperty("RESOURCE_SERVER_PATH").toString()+"/tmp/imgCache/"+srcFile.getParentFile().getName()+"/";
        File parF=new File(fname);
        if(!parF.exists())
              parF.mkdirs();
        fname+=srcFile.getName()+((w+h)/100)+"."+fileSuffix;
//        String fname=srcFile.getPath().substring(0,
//                (srcFile.getPath().lastIndexOf(".")))
//                + srcFile.getName().substring(0,srcFile.getName().lastIndexOf(".")) + "_"+(w+h)/100+"." + fileSuffix;
        File    destFile    = new File(fname);
        if(!destFile.exists()){
            //说明是远程文件,
            if(_file.getPath().indexOf("http:")==0){
                File tmpWirteFile=new File(destFile.getParent()+"/"+_file.getName());
                if(!tmpWirteFile.exists()){
                    //实例化url
                    URL url = new URL(_file.getPath().replaceAll("\\\\","//"));
                    //载入图片到输入流
                    java.io.BufferedInputStream bis = new BufferedInputStream(url.openStream());
                    //实例化存储字节数组
                    byte[] bytes = new byte[1024];


                    //设置写入路径以及图片名称
                    OutputStream bos = new FileOutputStream(tmpWirteFile);
                    int len;
                    while ((len = bis.read(bytes)) > 0) {
                        bos.write(bytes, 0, len);
                    }
                    bis.close();
                    bos.flush();
                    bos.close();
                }
                _file=tmpWirteFile;
            }
            Image srcImage  = javax.imageio.ImageIO.read(_file);
            //得到图片的原始大小， 以便按比例压缩。
            int  imageWidth = srcImage.getWidth(null);
            int  imageHeight = srcImage.getHeight(null);
            //        System.out.println("width: " + imageWidth);
            //        System.out.println("height: " + imageHeight);
            //得到合适的压缩大小，按比例。
            if ( imageWidth >= imageHeight)
                h = (int)Math.round((imageHeight * w * 1.0 / imageWidth));
            else
                w = (int)Math.round((imageWidth * h * 1.0 / imageHeight));

            //构建图片对象
            BufferedImage _image = new BufferedImage(w, h,
                    BufferedImage.TYPE_INT_RGB);
            //绘制缩小后的图
            _image.getGraphics().drawImage(srcImage, 0, 0, w, h, null);
            //输出到文件流
            FileOutputStream out = new FileOutputStream(destFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            encoder.encode(_image);
            out.flush();
            out.close();
        }
        return destFile.getPath();
    }

}
