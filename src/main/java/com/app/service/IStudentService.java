package com.app.service;

import java.util.List;

import com.app.dto.StudentDTO;
import com.app.entities.Student;

public interface IStudentService {
//get all student
	List<Student> getAllStudentsDetails();
	//save new student details
	StudentDTO saveStudentDetails(StudentDTO stud);
//	//delete student details
	String deleteStudentDetails(int studId);
//	//get student details by specified id
	Student getStudentDetails(int studId);
//	//update existing student details
	Student  updateStudentDetails(Student updatedDetachedStud);
}
