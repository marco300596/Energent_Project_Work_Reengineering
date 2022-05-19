package com.energent.controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.logging.Logger;
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

import com.energent.bean.Message;
import com.energent.bean.MessageConfirm;
import com.energent.entity.Academy;
import com.energent.entity.Student;
import com.energent.service.AcademyService;
import com.energent.service.StudentService;

import io.swagger.annotations.ApiOperation;
import net.bytebuddy.asm.Advice.This;

@RestController
@CrossOrigin(origins = "{\"http://localhost:8585\"}")
@RequestMapping("/academy-service")
public class AcademyController {
	
	/*
	 * è da chiedere a salvatore se il mav può essere salvato staticamente
	 * e se in questo modo si può tenere traccia degli eventi che affronta
	 * quest'ultimo.
	 */
	Logger logger = Logger.getLogger(this.getClass());
	@Autowired
	private AcademyService academyService;
	
	@Autowired
	private StudentService studentService;
	
	@ApiOperation(value="show all academies and students")
	@GetMapping("/totalReport")
	public HashMap<String,List> showReport() {

		HashMap<String,List> reportAll=new HashMap();
		List<Student> studentList = new ArrayList<>();
		List<Academy> academiesList = academyService.findAllAcademies();
		logger.info(academiesList.toString());
		if(!academiesList.isEmpty()) {
			
			for(Academy academy : academiesList) {
				studentList.addAll(studentService.findStudentsByAcademy(academy));
				logger.info(reportAll.toString());
			}		
		}
		reportAll.put("students", studentList);
		reportAll.put("academy", academiesList);
		logger.info(studentList.toString());
		return reportAll;
	}

	@ApiOperation(value="show all academies and students of one year")
	@GetMapping("/annualReport")
	public HashMap<String,List> showAnnualReport() {
		
		HashMap<String,List> reportAll=new HashMap<>();
		List<Academy> academiesList = new ArrayList<>();
		List<Student> studentList = new ArrayList<>();
		academiesList = academyService.findAllAcademiesForAnnualReport();
		if(!academiesList.isEmpty()) {
			
			for(Academy academy : academiesList) {
				studentList.addAll(studentService.findStudentsByAcademy(academy));
				logger.info(studentList.toString());
			}
		}
	 
		reportAll.put("academy", academiesList);
		reportAll.put("student", studentList);
		logger.info(studentList.toString());
		return reportAll;
	
	}

	@ApiOperation(value="show a form page for an academy to insert")
	@PostMapping("/academy")
	public Academy addAcademy(@RequestBody Academy academy) {
		
		academy = new Academy();
		return academy;
	}

	@ApiOperation(value="show all the details of an inserted academy")
	@PostMapping("/AcademyConfirm")
	public HashMap<String,Academy> confirmAcademy(@RequestBody Academy academy) {
		
		HashMap<String,Academy> mapacademy=new HashMap<>();
		mapacademy.put("new academy's details:", academy);
		return mapacademy;
	}
	
	@ApiOperation(value="show a message after inserting the academy")
	@PostMapping("/confirm/{codeId}")
	public MessageConfirm resultAcademy(@PathVariable String codeId, @ModelAttribute("academy") Academy academy) {
		
		MessageConfirm message=new MessageConfirm("operation successful");
		int res = academyService.addAcademy(academy);
		if (res == 2){// in case the date inserted is not right
			message.setMessage("wrong date inserted");
			return message;
			
		}if (res == 1) { // in case we inserted an already existing code
			message.setMessage("the academy already exists");
			return message;
			
		} //in case everything checks out
			return message;
			
	}

	@ApiOperation(value="display all the academies filtered by a parameter or all of them")
	@PostMapping("/academies")
	public List<Academy> showAcademies(@RequestBody Message message) {
		/*
		 * this method is in charge of check if everything is inserted correctly
		 * and to call the page and method that is in charge with the 
		 * system's response.
		 */
		List<Academy> academies = new ArrayList<>();
		 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
		if(message.getCode() != "") {
			
			academies = new ArrayList<>();
			Academy academy1 = academyService.findAcademybyId(message.getCode());
			academies.add(academy1);
			
			
			return academies;
		}if(!(message.getName() != "")) {
			
			academies = academyService.findAcademiesByTitle(message.getName());
			return academies;
		}if(!(message.getLocation() != "")) {
			
			academies = academyService.findAcademiesByLocation(message.getLocation());
			return academies;
		}
		if((message.getEdate()!=null) && (message.getSdate()!=null)) {
		if((!(message.getEdate().toLocalDate().format(formatter).equals("01/01/2000"))&&(!(message.getSdate().toLocalDate().format(formatter).equals("01/01/2000"))))){
				
				academies = academyService.findAcademiesByStartAndEndDate(message.getSdate().toLocalDate().format(formatter), message.getEdate().toLocalDate().format(formatter));
				return academies;
		}
		}if(message.getEdate()!=null) {
		if(!(message.getEdate().toLocalDate().format(formatter).equals("01/01/2000"))){
				
				academies = academyService.findAcademiesByEndDate(message.getEdate().toLocalDate().format(formatter));
				return academies;
		}
		}if(message.getSdate()!=null) {
		if(!(message.getSdate().toLocalDate().format(formatter).equals("01/01/2000"))){
				
				academies = academyService.findAcademiesByStartDate(message.getSdate().toLocalDate().format(formatter));
				return academies;
		}
		}else {
		
			academies = academyService.findAcademiesForTable();
			return academies;
		}
		academies=academyService.findAcademiesForTable();
		return academies;
	}
	
	@ApiOperation(value="displaying of the updated academy")
	@PostMapping("/academies/{codeId}/update")
	public HashMap<String,Academy> showUpdateAcademy(@PathVariable String codeId) {
		HashMap<String,Academy>confirm=new HashMap<>();
		confirm.put("Do you want to update this academy?", academyService.findAcademybyId(codeId));
		return confirm;
	}

	@ApiOperation(value="updating of the academy")
	@PutMapping("/AcademyApproved")
	public HashMap<String,Academy> updateAcademies(@RequestBody Academy academy) {
		String message="academy updated";
		HashMap<String,Academy>mapAcademy=new HashMap<>();
		if (!academyService.updateAcademy(academy)) { // in case the date inserted is not right
			message="wrong date inserted";
			mapAcademy.put(message,new Academy());
		}
		else {	// in case everything checks out we have to set a new page to land to otherwise we will be stuck in a loop
			Academy academy1 = academyService.findAcademybyId(academy.getCodeId());
			mapAcademy.put(message, academy1);
		}
		return mapAcademy;
	}

	@ApiOperation(value="show accademies and all student associated")
	@PostMapping("/academies/{codeId}/remove")
	public  HashMap<Academy,List<Student>> removeWarningAcademy(@PathVariable String codeId) {
		HashMap<Academy,List<Student>>mapAcademy=new HashMap<>();
		Academy academy=academyService.findAcademybyId(codeId);
		List<Student>students=studentService.findStudentsByAcademy(academy);
		
		
		mapAcademy.put(academy, students);
		return mapAcademy;
		
	}

	@ApiOperation(value="confirm of academy and student deletion")
	@DeleteMapping("/academies/{codeId}/remove/confirm")
	public MessageConfirm removeAcademy(@PathVariable String codeId) {
		
		MessageConfirm mes=new MessageConfirm("academy removed with students");
		
		List<Student> students = studentService.findStudentsByAcademy(academyService.findAcademybyId(codeId));
		for(Student student: students)
			studentService.removeStudent(student.getfCode());
		if(!academyService.removeAcademy(codeId)) {
		   mes.setMessage("unable to remove the selected student");
		   return mes;
		}else{
			return mes;
		}
		
		
	}
}
	
	

	
	

