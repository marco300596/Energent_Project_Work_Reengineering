package com.energent.bean;

import java.sql.Date;

public class Message {

	private String code;
	private String location;
	private String name;
	private Date sdate;
	private Date edate;

	

	public Message() {
	}



	public Message(String code, String location, String name, Date sdate, Date edate) {
		
		this.code = code;
		this.location = location;
		this.name = name;
		this.sdate = sdate;
		this.edate = edate;
	}



	public String getCode() {
		return code;
	}



	public void setCode(String code) {
		this.code = code;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public Date getSdate() {
		return sdate;
	}



	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}



	public Date getEdate() {
		return edate;
	}



	public void setEdate(Date edate) {
		this.edate = edate;
	}



	public String getLocation() {
		return location;
	}



	public void setLocation(String location) {
		this.location = location;
	}
	
	
}
