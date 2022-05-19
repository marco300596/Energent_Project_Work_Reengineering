package com.energent.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.energent.entity.Academy;
import com.energent.repository.AcademyRepository;

@Service
public class AcademyServiceImpl implements AcademyService {

	@Autowired
	AcademyRepository academyRepository;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // used to inform the parsing function of the pattern used
	String systemDate = LocalDate.now().format(formatter); //initialize a string with the value of the actual date
	List<Academy> resultAcademies = new ArrayList<>();
	List<Academy> academies = new ArrayList<>();
	
	
	/*
	 * this method is used to see if the selected pair of dates can be used fon an academy
	 */
	public int rightDate(String start, String end) {
		
		int result = 2;
		LocalDate startDate = LocalDate.parse(start, formatter); // used to parse
		startDate.minusMonths(1);
		LocalDate endDate = LocalDate.parse(end, formatter);
		endDate.minusMonths(1);
		if(startDate.isBefore(endDate)) {
			
			result = compareToActualDate(startDate);
			return result;
			}
		
		return result;
	}
	
	public int compareToActualDate(LocalDate givenDate) {
	
		/*
		 * 
		 */
		int result = 1;
		if (givenDate.isAfter(LocalDate.parse(systemDate, formatter)) || givenDate.isEqual(LocalDate.parse(systemDate, formatter))) { // comparison between system's and starting date
		
			result = 0;
			return result;
		}
		
		return result;
	}
	
	public boolean matchingDate(String givenStartingDateString, String givenEndingDateString, LocalDate actualDate) {
		/*
		 * this method is used to find the academies to insert in the annual report
		 */
		
		LocalDate minexpectedDate = actualDate.minusYears(1);
		LocalDate givenStartingDate = LocalDate.parse(givenStartingDateString, formatter);
		LocalDate givenEndingDate = LocalDate.parse(givenEndingDateString, formatter);
		return (((givenStartingDate.isAfter(minexpectedDate)) || givenStartingDate.isEqual(minexpectedDate)) && ((givenEndingDate.isBefore(actualDate)) || (givenEndingDate.isEqual(actualDate))));
	}
	
	@Override
	public Academy findAcademybyId(String codeId) {
		
		Academy academy = null;
		if (academyRepository.existsById(codeId)) 
			academy = academyRepository.findById(codeId).get();
		return academy;
	}
	
	@Override
	public int addAcademy(Academy academy) {
		
		int result = 1;
		if (!academyRepository.existsById(academy.getCodeId())) { // control to see if exists an academy with the inserted code
			if(rightDate(academy.getStartDate(), academy.getEndDate()) == 0) { // control if the dates can be used
			
				academyRepository.save(academy);
				result = 0;
				return result;
			}else {
				
				result = 2;
				return result;
			}
		}
		
		return result;
		
	}
	
	@Override
	public boolean updateAcademy(Academy academy) {
	
		boolean res=false;
		if(rightDate(academy.getStartDate(), academy.getEndDate()) == 0){ // see if the dates can be used
			res = true;
			/*
			Academy academyToUpdate = findAcademybyId(academy.getCodeId());
			academyToUpdate.setCodeId(academy.getCodeId());
			academyToUpdate.setTitle(academy.getTitle());
			academyToUpdate.setLocation(academy.getLocation());
			academyToUpdate.setStartDate(academy.getStartDate());
			academyToUpdate.setEndDate(academy.getEndDate());
			academyToUpdate.setStudents(academy.getStudents());
			*/
			academyRepository.save(academy);
			return res;
		}
		
		return res;
	}

	@Override
	public boolean removeAcademy(String codeId) {

		academyRepository.deleteById(codeId);
		return academyRepository.existsById(codeId);
	}
	
	@Override
	public List<Academy> findAllAcademies(){
		
		/*
		 * this method will be used in every listing method and in the total report
		 */
		return academyRepository.findAll();
	}
	
	@Override
	public List<Academy> findAcademiesForTable(){
		
		/*
		 * thisa method is called to list all the accademies actually active
		 */

		resultAcademies.clear();
		academies = findAllAcademies();
		for (Academy academy : academies) {
			int a = compareToActualDate(LocalDate.parse(academy.getEndDate(), formatter));
			if (a == 0){
				resultAcademies.add(academy);
				
			}
		}
		return resultAcademies;
		
	}

	@Override
	public List<Academy> findAcademiesByTitle(String title) {
		
		return academyRepository.findByTitle(title);
	}

	@Override
	public List<Academy> findAcademiesByLocation(String location) {
		
		return academyRepository.findByLocation(location);
	}

	@Override
	public List<Academy> findAcademiesByStartDate(String startDate) {
		

		academies = findAllAcademies();
		for (Academy academy : academies) {
			int a = rightDate(startDate, academy.getStartDate());
			if (a == 0||a == 1) {
				resultAcademies.add(academy);
			}
		}
		return resultAcademies;
}

	@Override
	public List<Academy> findAcademiesByEndDate(String endDate) {
		

		academies = findAllAcademies();
		for (Academy academy : academies) {
			int a = rightDate(academy.getEndDate(), endDate);
			if (a == 1||a == 0) {
				resultAcademies.add(academy);
			}
		}
		return resultAcademies;
}
	
	@Override
	public List<Academy> findAcademiesByStartAndEndDate(String startDate, String endDate) {
		

		academies = findAllAcademies();
		for (Academy academy : academies) {
			int a = rightDate(academy.getEndDate(), endDate);
			int b = rightDate(startDate, academy.getStartDate());
			if ((a == 1||a == 0) && (b == 0||b == 1)) {
				resultAcademies.add(academy);
			}
		}
		return resultAcademies;
	}
	
	@Override
	public List<Academy> findAllAcademiesForAnnualReport(){
		/*
		 * this method will return all the academies that were taken in a year
		 */

		academies = findAllAcademies();
		for(Academy academy : academies)
			if (matchingDate(academy.getStartDate(), academy.getEndDate(), LocalDate.parse(systemDate, formatter)))
				resultAcademies.add(academy);
		return resultAcademies;
	}
	
}
