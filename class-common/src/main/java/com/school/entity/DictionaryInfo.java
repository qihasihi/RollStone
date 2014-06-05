package com.school.entity;

import com.school.util.UtilTool;
import com.school.util.UtilTool.DateType;

import javax.persistence.Entity;

@Entity
public class DictionaryInfo implements java.io.Serializable {

	public void DictionaryInfo() {
	}

	private java.lang.String dictionaryname;
	private java.util.Date ctime;
	private java.util.Date mtime;
	private java.lang.String dictionarytype;
	private java.lang.String dictionaryvalue;
	private java.lang.String dictionarydescription;
	private java.lang.Integer orderidx;
	private java.lang.String ref;

	
	
	public java.lang.String getDictionarydescription() {
		return dictionarydescription;
	}

	public void setDictionarydescription(java.lang.String dictionarydescription) {
		this.dictionarydescription = dictionarydescription;
	}

	public java.lang.String getDictionaryname() {
		return dictionaryname;
	}

	public void setDictionaryname(java.lang.String dictionaryname) {
		this.dictionaryname = dictionaryname;
	}

	public java.util.Date getCtime() {
		return ctime;
	}

	public void setCtime(java.util.Date ctime) {
		this.ctime = ctime;
	}

	public java.util.Date getMtime() {
		return mtime;
	}

	public void setMtime(java.util.Date mtime) {
		this.mtime = mtime;
	}

	public java.lang.String getDictionarytype() {
		return dictionarytype;
	}

	public void setDictionarytype(java.lang.String dictionarytype) {
		this.dictionarytype = dictionarytype;
	}

	public java.lang.String getDictionaryvalue() {
		return dictionaryvalue;
	}

	public void setDictionaryvalue(java.lang.String dictionaryvalue) {
		this.dictionaryvalue = dictionaryvalue;
	}

	public java.lang.Integer getOrderidx() {
		return orderidx;
	}

	public void setOrderidx(java.lang.Integer orderidx) {
		this.orderidx = orderidx;
	}

	public java.lang.String getRef() {
		return ref;
	}

	public void setRef(java.lang.String ref) {
		this.ref = ref;
	}

	public String getCtimeString() {
		return (ctime == null ? "" : UtilTool.DateConvertToString(ctime,
				DateType.type1));
	}

	public String getMtimeString() {
		return (mtime == null ? "" : UtilTool.DateConvertToString(mtime,
				DateType.type1));
	}
	
	public enum DictOrderEnum{
		DICTIONARY_NAME,DICTIONARY_VALUE,DICTIONARY_IDX
	}
}