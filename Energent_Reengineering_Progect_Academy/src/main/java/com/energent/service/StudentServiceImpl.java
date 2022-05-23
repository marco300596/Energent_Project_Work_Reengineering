package com.energent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energent.entity.Academy;
import com.energent.entity.Student;
import com.energent.repository.AcademyRepository;
import com.energent.repository.StudentRepository;
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepository studentRepository;
	@Autowired
	AcademyRepository academyRepository;
	
	@Override
	public boolean addStudent(Student student, String id) {
		
		boolean result = false;
		Academy academy = academyRepository.findById(id).get();
		if (!studentRepository.existsById(student.getfCode())) {
			student.setAcademy(academy);
			studentRepository.save(student);
			result = true;
			return result;
		}
		return result;
	}
	
	public void UpdateStudent(Student student, Academy academy) {
	
	Student studentToUpdate = studentRepository.findById(student.getfCode()).get();
	studentToUpdate.setfCode(student.getfCode());
	studentToUpdate.setFirstname(student.getFirstname());
	studentToUpdate.setLastname(student.getLastname());
	studentToUpdate.setAge(student.getAge());
	studentToUpdate.setAcademy(academy);
	studentRepository.save(studentToUpdate);
	
  
	
}
	
	@Override
	public boolean removeStudent(String fCode) {

		studentRepository.deleteById(fCode);
		return studentRepository.existsById(fCode);
	}

	@Override
	public List<Student> findStudentsByAcademy(Academy academy) {
		
		List<Student> students = studentRepository.findByAcademy(academy);		
		return students;
		
	}

	@Override
	public List<Student> findStudentsByLastname(String lastname) {
		
		return studentRepository.findByLastname(lastname);
	}
	
	@Override
	public Student findStudentById(String fCode) {
		
		return studentRepository.findById(fCode).get();
	}
}
