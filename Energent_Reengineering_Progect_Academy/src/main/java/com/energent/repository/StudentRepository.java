package com.energent.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.energent.entity.Academy;
import com.energent.entity.Student;

public interface StudentRepository extends JpaRepository<Student, String> {

	
	//see existsByID in CrudRepository<-PagingAndSortingRepository<-JPAREPOSITORY
	public List<Student> findByAcademy(Academy academy);
	public List<Student> findByLastname(String lastname);
}
