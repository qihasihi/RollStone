package com.school.util;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * JSONʵ��
 * 
 * @author zhengzhou
 * 
 */
public class JsonEntity {
	// ״̬ success Or error
	private String type="error";
	// �����ӡ����Ϣ
	private String msg;

	// ��õĽ��
	public JsonEntity(String type, String msg){
		this.type = type;
		this.msg = msg;
	}
	public JsonEntity(String type, String msg, PageResult presult) {
		super();
		this.type = type;
		this.msg = msg;
		this.presult=presult;
	}

	public JsonEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * ״̬ success Or error
	 * 
	 * @return
	 */
	public String getType() {
		return type;
	}

	// ��ҳ��Ϣ
	private PageResult presult = new PageResult();

	/**
	 *  ��ҳ��Ϣ
	 * @return
	 */
	public PageResult getPresult() {
		return presult;
	}

	/**
	 * ��ҳ��Ϣ
	 * @param presult
	 */
	public void setPresult(PageResult presult) {
		this.presult = presult;
	}

	/**
	 * ״̬ success error
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * �����ӡ����Ϣ
	 * 
	 * @return
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * ϵͳ������ʾ��
	 * @return
	 */
	public String getAlertMsg(){
		String returnMsg="";
		if(msg!=null&&msg.length()>0){
			 returnMsg="<script type='text/javascript'>alert('"+msg+"');</script>";
			return returnMsg;
		}
		return returnMsg;
	}
	/**
	 * ϵͳ������ʾ��
	 * @return
	 */
	public String getAlertMsgAndBack(){
		String returnMsg="";
		if(msg!=null&&msg.length()>0){
			 returnMsg="<script type='text/javascript'>alert('"+msg+"');history.go(-1);</script>"; 
			return returnMsg;
		}
		return returnMsg;
	}
	/**
	 * ϵͳ������ʾ�򲢹رմ��ڡ�
	 * @return
	 */
	public String getAlertMsgAndCloseWin(){
		String returnMsg="";
		if(msg!=null&&msg.length()>0){
			 returnMsg="<script type='text/javascript'>alert('"+msg+"');self.close();</script>";
			return returnMsg;
		}
		return returnMsg;
	}
	/**
	 * ϵͳ������ʾ�򲢹رմ��ڡ�
	 * @return
	 */
	public String getAlertMsgAndSendRedirect(String url){
		String returnMsg="";
		if(msg!=null&&msg.length()>0){
			 returnMsg="<script type='text/javascript'>alert('"+msg+"');location.href='"+url+"';</script>";
			return returnMsg;
		}
		return returnMsg;
	}
	/**
	 * �����ӡ����Ϣ
	 * 
	 * @param msg
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}


	/**
	 * json���
	 * 
	 * @param objList
	 */
	public void setObjList(Collection objList) {
		this.presult.setList(objList);
	}
	/**
	 * json���
	 * 
	 * @param
	 */
	public Collection getObjList() {
		return	this.presult.getList();
	}


	/**
	 * ת����JSON
	 * 
	 * @return
	 * @throws Exception
	 */
	public String toJSON() throws Exception {	
		if(this.presult==null)
			this.presult=new PageResult();
		if(this.presult.getList()==null)
			this.presult.setList(new ArrayList());
		return toJSON(this);
	}

	private static JsonConfig getJsonConfig() {
		JsonConfig jsonConfig=new JsonConfig();   

		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (value == null)
					return true;
				String vType = value.getClass().getName();
				// ��ֹ��ѭ��.
                if (name.indexOf("alertMsg") != -1)
                    return true;
                if (vType.indexOf("org.hibernate.collection.PersistentSet") != -1) {
                    return true;
					
				} else if(vType.indexOf("java.sql")!=-1){
					return true;
				}else
					return false;
			}
		});
		jsonConfig.setExcludes(new String[]{"handler","hibernateLazyInitializer"});   

		return jsonConfig;
	}

	/**
	 * ת����JSON
	 * 
	 * @param objList
	 *            ����
	 * @return
	 */
	private String toJSON(Collection objList) {
		if (objList == null)
			return null;
		JSONArray jsar = JSONArray.fromObject(objList, getJsonConfig());
		return jsar.toString();
	}

	/**
	 * ת����JSON
	 * 
	 * @param obj
	 * @return
	 */
	private String toJSON(Object obj) {
		if (obj == null)
			return null;		
		JSONObject jsar = JSONObject.fromObject(obj, getJsonConfig());
		return jsar.toString();
	}
}
