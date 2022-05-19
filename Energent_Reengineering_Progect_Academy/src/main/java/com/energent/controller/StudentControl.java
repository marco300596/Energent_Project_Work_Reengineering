package com.energent.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.energent.bean.MessageConfirm;
import com.energent.entity.Academy;
import com.energent.entity.Student;
import com.energent.service.AcademyService;
import com.energent.service.StudentService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/service-student")
@CrossOrigin(origins =" {\"http://localhost:8585\"}")
public class StudentControl {
	@Autowired
	private StudentService studentService;
	@Autowired
	private AcademyService academyService;
	
	@ApiOperation(value="show all student's detail")
	@PostMapping("/academies/{codeId}/students/add")
	public HashMap<String,Student> newStudent(@RequestBody Student student, @PathVariable String codeId) {
		
		HashMap<String,Student>confirm=new HashMap<>();
		confirm.put("new student's details:", student);
		return confirm;
	}

	@ApiOperation(value="show a message page after adding a student")
	@PostMapping("/academies/{codeId}/students/add/{fCode}")
	public HashMap<String,Student> addStudent(@RequestBody Student student, @PathVariable String codeId, @PathVariable String fCode) {
		HashMap<String,Student> confirm=new HashMap<>();
		if(!studentService.addStudent(student, codeId)){
			
			confirm.put("the student already exists", studentService.findStudentById(fCode));
		}
		else 
			confirm.put("the student selected is now added", studentService.findStudentById(fCode));
	      
		return confirm;
	}

	@ApiOperation(value="show a form page used to insert a new student")
	@PostMapping("/academies/{codeId}/students/student")
	public Student addNewStudent(@PathVariable String codeId){
		
		Academy academy = academyService.findAcademybyId(codeId);
		Student student = new Student();
		student.setAcademy(academy);
		
		
		return student;
			
	}

	@ApiOperation(value="show a page with a table of all the students in an academy")
	@GetMapping("/academies/academy/{codeId}/students")
	public List<Student>showsListStudent(@RequestBody Academy academy,@RequestBody Student student){
		
		return studentService.findStudentsByAcademy(academy);
		
	}

	@ApiOperation(value="show a form with all the details of the student modifiable")
	@PostMapping("/academies/{codeId}/students/update/{fCode}")
	public HashMap<String,Student> showUpdateStudent(@PathVariable String codeId,@PathVariable String fCode) {
	
	HashMap<String,Student>confirm=new HashMap<>();
		confirm.put("Do you want to update this student?", studentService.findStudentById(fCode));
		return confirm;
	}

	@ApiOperation(value="show a page with all the student's detail")
	@PutMapping("/academies/{codeId}/students/update/approved")
	public HashMap<String,Student> ConfirmUpdate(@PathVariable String codeId, @RequestBody Student student) {
	studentService.UpdateStudent(student,academyService.findAcademybyId(codeId));
	HashMap<String,Student>confirm=new HashMap<>();
		confirm.put("student updated", student);
		return confirm;
	}

	@ApiOperation(value="show a message with the response of the deletion of the student")
	@DeleteMapping("/academies/{codeId}/students/student/remove/confirm")
	public MessageConfirm removedStudentFromAcademy(@RequestBody Student student ,@RequestBody Academy academy) {
		 
				 student =studentService.findStudentById(student.getfCode());
				MessageConfirm message=new MessageConfirm("student removed");
			boolean temp=studentService.removeStudent(student.getfCode());
			if(temp==true) {
				message.setMessage("unable to remove the selected student ");
				return message;
				
			}
			else 
				return message;
		
	}

	@ApiOperation(value="show a page with the ready-to-be-cancelled-student's detail")
	@PostMapping ("/academies/{codeId}/students/remove/{fCode}")
	public HashMap<String,Student> confirmRemove (@PathVariable String codeId,@PathVariable String fCode,@RequestBody Student student ) {
		HashMap<String,Student>confirm=new HashMap<>();
		confirm.put("remove student?", student);
		return confirm;
			
	}
	
}

