package com.app.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.app.dto.StudentDTO;

import com.app.entities.Student;
import com.app.repository.StudentRepository;

@Service
@Transactional
public class StudentServiceImpl implements IStudentService {
	
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private StudentRepository studRepo;	
	@Override
	public List<Student> getAllStudentsDetails() {
		return studRepo.findAll();
	}

	@Override
	public StudentDTO saveStudentDetails(StudentDTO srtudDto) {
		// map dto --> entity
		Student student = mapper.map(srtudDto, Student.class);
		//persist student details in the db
		Student persistentStud = studRepo.save(student);// method rets PERSISTENT emp ref
		// map entity --> dto
		return mapper.map(persistentStud, StudentDTO.class);
	}// in case of no errs : hib auto dirty chking @ session.flush ---tx.commit
		// --inserts rec --L1 cache destroyed -- pooled out db cn rets to the pool
		// --rets DETACHED pojo to the caller

	@Override
	public String deleteStudentDetails(int studId) {
		String mesg = "Deletion of student details failed!!!!!!!!!!!";

		if (studRepo.existsById(studId)) {
			studRepo.deleteById(studId);
			mesg = "Student details deleted successfully , for Student id :" + studId;
		}

		return mesg;
	}

	@Override
	public Student getStudentDetails(int studId) {
		// TODO Auto-generated method stub
		Student student = studRepo.getById(studId);
		student.getEnrollmentDate();
		return student;
		// .orElseThrow(() -> new ResourceNotFoundException("Invalid emp id !!!!!!" +
		// empId));
	}

	@Override
	public Student updateStudentDetails(Student updatedDetachedStud) {
		// TODO Auto-generated method stub
		return studRepo.save(updatedDetachedStud);
	}

}
