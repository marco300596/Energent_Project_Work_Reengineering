package com.energent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energent.entity.Academy;

public interface AcademyRepository extends JpaRepository<Academy, String> {

	public List<Academy> findByTitle(String title);
	public List<Academy> findByLocation(String location);
	public List<Academy> findByStartDate(String startDate);
	public List<Academy> findByEndDate(String endDate);
}
