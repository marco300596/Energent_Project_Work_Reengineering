package com.energent.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Student {

	@Id
	@Column(length=16, nullable=false)
	private String fCode;
	@Column(length=10, nullable=false)
	private String firstname;
	@Column(length=10, nullable=false)
	private String lastname;
	@Column(nullable=false)
	private int age;
	
	@ManyToOne
	@JsonIgnoreProperties("students")
	private Academy academy;

	public String getfCode() {
	
		return fCode;
	}

	public void setfCode(String fCode) {
		
		this.fCode = fCode;
	}

	public String getFirstname() {
		
		return firstname;
	}

	public void setFirstname(String firstname) {
		
		this.firstname = firstname;
	}

	public String getLastname() {
		
		return lastname;
	}

	public void setLastname(String lastname) {
		
		this.lastname = lastname;
	}

	public int getAge() {
		
		return age;
	}

	public void setAge(int age) {
		
		this.age = age;
	}

	public Academy getAcademy() {
		
		return academy;
	}

	public void setAcademy(Academy academy) {
		
		this.academy = academy;
	}

	public Student(String fCode, String firstname, String lastname, int age, Academy academy,boolean stato) {
		stato=stato;
        this.fCode = fCode;
		this.firstname = firstname;
		this.lastname = lastname;
		this.age = age;
	}

	public Student() {
	}
	
	
}
