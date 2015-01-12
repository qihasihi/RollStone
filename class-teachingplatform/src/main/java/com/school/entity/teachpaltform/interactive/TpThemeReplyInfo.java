package  com.school.entity.teachpaltform.interactive ;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import java.util.Date;

public class TpThemeReplyInfo  implements java.io.Serializable{

	public void TpThemeReplyInfo (){}
   
    private java.lang.String replycontent;
    private Date ctime;
    private java.lang.Long userid;
    private java.lang.Long themeid;
    private java.lang.Long replyid;
    private Long topicid;
    private Long toreplyid;
    private String torealname;
    
    private String cheadimage;
    private String crealname;
    private Object rownum;

    public Long getToreplyid() {
        return toreplyid;
    }

    public void setToreplyid(Long toreplyid) {
        this.toreplyid = toreplyid;
    }

    public String getTorealname() {
        return torealname;
    }

    public void setTorealname(String torealname) {

        this.torealname = torealname;
    }

    public String getCheadimage() {
		return cheadimage;
	}
	public void setCheadimage(String cheadimage) {
		this.cheadimage = cheadimage;
	}
	public String getCrealname() {
		return crealname;
	}
	public void setCrealname(String crealname) {
		this.crealname = crealname;
	}
	public Object getRownum() {
		return rownum;
	}
	public void setRownum(Object rownum) {
		this.rownum = rownum;
	}
	public Long getTopicid() {
		return topicid;
	}
	public void setTopicid(Long topicid) {
		this.topicid = topicid;
	}
	public java.lang.String getReplycontent(){
      return replycontent;
    }
    public void setReplycontent(java.lang.String replycontent){
      this.replycontent = replycontent;
    }
    public Date getCtime(){
      return ctime;
    }
    public void setCtime(Date ctime){
      this.ctime = ctime;
    }
    public java.lang.Long getUserid(){
      return userid;
    }
    public void setUserid(java.lang.Long userid){
      this.userid = userid;
    }
    public java.lang.Long getThemeid(){
      return themeid;
    }
    public void setThemeid(java.lang.Long themeid){
      this.themeid = themeid;
    }
    public java.lang.Long getReplyid(){
      return replyid;
    }
    public void setReplyid(java.lang.Long replyid){
      this.replyid = replyid;
    }
    
    public String getCtimeString(){
    	if(this.ctime==null)return "";
    	return UtilTool.DateConvertToString(this.ctime, DateType.type1);    		
    }
    public String getAutoCtimeString(){
        String returnVal="";
        if(this.getCtime()!=null){
            Long dt=System.currentTimeMillis()-this.getCtime().getTime();
            int dLong=(int)(dt/60000);//分钟
            String sHtml="刚刚";
            if(dLong<1)
                sHtml=(int)(dt%60000)/1000+"秒前";
            else if(dLong>=1&&dLong<60)
                sHtml=dLong+"分钟前";
            else if(dLong>=60){
                dLong=(int)(dLong/60);
                if(dLong<24)
                    sHtml=dLong+"小时前";
                else
                    sHtml=UtilTool.DateConvertToString(this.getCtime(), UtilTool.DateType.type1);
            }
            returnVal=sHtml;
        }
        return returnVal;
    }

}