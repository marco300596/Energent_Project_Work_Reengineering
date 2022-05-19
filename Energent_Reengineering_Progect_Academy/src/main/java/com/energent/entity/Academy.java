package com.energent.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Academy {

	@Id
	@Column(length=10)
	private String codeId;
	@Column(length=20)
	private String title;
	@Column(length=20)
	private String location;
	@Column(length=10)
	private String startDate;
	@Column(length=10)
	private String endDate;

	@OneToMany(mappedBy = "academy")
	List<Student> students = new ArrayList<>();

	public List<Student> getStudents() {

		return students;
	}

	public void setStudents(List<Student> students) {

		this.students = students;
	}

	public String getCodeId() {

		return codeId;
	}

	public void setCodeId(String codeId) {

		this.codeId = codeId;
	}

	public String getTitle() {

		return title;
	}

	public void setTitle(String title) {

		this.title = title;
	}

	public String getLocation() {

		return location;
	}

	public void setLocation(String location) {

		this.location = location;
	}

	public String getStartDate() {

		return startDate;
	}

	public void setStartDate(String startDate) {

		this.startDate = startDate;
	}

	public String getEndDate() {

		return endDate;
	}

	public void setEndDate(String endDate) {

		this.endDate = endDate;
	}

	public Academy(String codeId, String title, String location, String startDate, String endDate) {

		this.codeId = codeId;
		this.title = title;
		this.location = location;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Academy() {
	}

	public Academy(String codeId) {
		super();
		this.codeId = codeId;
	}
	
}
