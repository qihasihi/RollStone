package com.school.util.jqgrid;

import java.util.ArrayList;
import java.util.List;

/**
 * 后到利用JGrid操作
 * 
 * @author zhengzhou
 * 
 */
public class JQGridConditionEntity implements java.io.Serializable{
	private String groupOp;
	private List rules = new ArrayList();
	// 单条查询
	private String searchField;
	private String searchOper;
	private String searchString;

	public String getGroupOp() {
		return groupOp;
	}

	public void setGroupOp(String groupOp) {
		this.groupOp = groupOp;
	} 

	public List getRules() {
		return rules;
	}

	public void setRules(List rules) {
		this.rules = rules;
	}

	public String getSearchField() {
		return searchField;
	}

	public void setSearchField(String searchField) {
		this.searchField = searchField;
	}

	public String getSearchOper() {
		return searchOper;
	}

	public String getRealSearchOper() {
		return this.getRealOpChar(this.searchOper);
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
	public void setSearchOper(String searchOper) {
		this.searchOper = searchOper;
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	/**
	 * 得到JQgrid反回过来的条件
	 * 
	 * @return
	 */
	public String getJQGridConditionString() {
		if (rules.isEmpty() && (searchField == null||searchField.trim().length()<1))
			return "";
		StringBuilder condbuilder = new StringBuilder();
		if (!rules.isEmpty()) {
			for (Object o : rules) {
				if (o != null) {
					JQGridDetial gd=(JQGridDetial)o;					
					if(gd!=null){
						String con=gd.getRealCondition();
						if(con!=null&&con.trim().length()>0)
							condbuilder.append(groupOp+" "+con);
					}
				}
			}
		}else{
			String realOpChar=this.getRealOpChar(this.searchOper);
			if(realOpChar.trim().toUpperCase().indexOf("IN")!=-1){
				String[] splitData=searchString.split(",");
				
				String child_con=null;
				for (String sd : splitData) {
					if(sd.trim().length()>0){
						if(child_con!=null&&child_con.length()>0){
							child_con+=",'"+sd+"'";
						}else
							child_con="'"+sd+"'";					
					}
				}
				condbuilder.append(groupOp+" "+searchField+" "+realOpChar+"("+child_con+")");			
			}else if(realOpChar.trim().toLowerCase().indexOf("LIKE")!=-1)
				condbuilder.append(groupOp+" "+searchField+" "+realOpChar+"'%"+searchString+"%'");
			else
				condbuilder.append(groupOp+" "+searchField+" "+realOpChar+" '"+searchString+"'");
		}
		String tmp=condbuilder.toString();
		if(tmp.trim().length()>0)
			tmp=tmp.trim().substring(tmp.indexOf(" "));
		return tmp; 
	}
}