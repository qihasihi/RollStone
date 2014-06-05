package com.school.util.jqgrid;


/**
 * rules里的内容对象设置
 * 
 * @author zhengzhou
 * 
 */
public class JQGridDetial implements java.io.Serializable{
	private String field; // 查询字段
	private String op; // 查询操作
	private String data; // 选择的查询值

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	/**
	 * 得到当前的COndition
	 * @return
	 */
	public String getRealCondition(){
		String realop=this.getRealOpChar(this.getOp());
		if(realop==null||realop.trim().length()<1||data==null||data.trim().length()<1)
			return "";
		//
		String condition="";
		if(realop.trim().toUpperCase().indexOf("IN")!=-1){
			String[] splitData=data.split(",");
			
			String child_con=null;
			for (String sd : splitData) {
				if(sd.trim().length()>0){
					if(child_con!=null&&child_con.length()>0){
						child_con+=",'"+sd+"'";
					}else
						child_con="'"+sd+"'";					
				}
			}
			condition+=field+" "+realop+"("+child_con+") ";			
		}else if(realop.trim().toUpperCase().indexOf("LIKE")!=-1)
			condition+=field+" "+realop+"'%"+data+"%' ";
		else
			condition+=field+" "+realop+" '"+data+"' ";
		return condition;
	}
	
	/**
	 * 得到操作符号
	 * 
	 * @return
	 */
	private String getRealOpChar(String op) {
		String returnVal = op;
		if (op != null) {
			if (op.trim().equals("eq")) {
				returnVal = "=";
			} else if (op.trim().equals("ne")) {
				returnVal = "<>";
			} else if (op.trim().equals("lt")) {
				returnVal = "<";
			} else if (op.trim().equals("le")) {
				returnVal = "<=";
			} else if (op.trim().equals("gt")) {
				returnVal = ">";
			} else if (op.trim().equals("ge")) {
				returnVal = ">=";
			} else if (op.trim().equals("bw")) {
				returnVal = "LIKE";
			} else if (op.trim().equals("bn")) {
				returnVal = "NOT LIKE";
			} else if (op.trim().equals("in")) {
				returnVal = "IN";
			} else if (op.trim().equals("ni")) {
				returnVal = "NOT IN";
			} else if (op.trim().equals("ew")) {
				returnVal = "LIKE";
			} else if (op.trim().equals("en")) {
				returnVal = "NOT LIKE";
			} else if (op.trim().equals("cn")) {
				returnVal = "LIKE";
			} else if (op.trim().equals("nc")) {
				returnVal = "NOT LIKE";
			} else if (op.trim().equals("nu")) {
				returnVal = "IS NULL";
			} else if (op.trim().equals("nn")) {
				returnVal = "IS NOT NULL";
			}
		}
		return returnVal;
	}
}