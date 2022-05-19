package com.energent.service;

import java.time.LocalDate;
import java.util.List;

import com.energent.entity.Academy;


public interface AcademyService {

	public Academy findAcademybyId(String codeId);
	public int addAcademy(Academy academy);
	public boolean updateAcademy(Academy academy);
	public boolean removeAcademy(String codeId);
	public List<Academy> findAllAcademies();
	public List<Academy> findAcademiesByTitle(String title);
	public List<Academy> findAcademiesByLocation(String location);
	public List<Academy> findAcademiesByStartDate(String startDate);
	public List<Academy> findAcademiesByEndDate(String endDate);
	public List<Academy> findAcademiesByStartAndEndDate(String startDate, String endDate);
	public List<Academy> findAcademiesForTable();
	public List<Academy> findAllAcademiesForAnnualReport();
}
