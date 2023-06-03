package com.app.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.dto.StudentDTO;
import com.app.entities.Student;
import com.app.service.IStudentService;
//import com.app.service.ImageHandlingService;
import com.app.service.ImageHandlingService;

@RestController // MANDATORY : composed of @Controller at the cls level + @ResponseBody(for
				// marshalling : java ---> json) addedd implicitly on ret types of all req
				// handling methods , annotated by @ReqMapping / @GetMapping .......
@RequestMapping("/api/students")
@CrossOrigin(origins = "http://localhost:3000")
@Validated
public class StudentController {
	// dep : student service i/f
	@Autowired
	private IStudentService studService;

	// dep : image handling service i/f
	@Autowired
	private ImageHandlingService imageHandlingService;

	public StudentController() {
		System.out.println("in ctor of " + getClass());
	}

	// add req handling method (REST API call) to send all students
	@GetMapping
	public ResponseEntity<?> listAllStudents() {
		System.out.println("in list students");
		List<Student> list = studService.getAllStudentsDetails();
		// o.s.ResponseEntity(T body,HttpStatus sts)
		if (list.isEmpty())
			return new ResponseEntity<>("Empty student List !!!!", HttpStatus.OK);
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	// add req handling method to create new students
	@PostMapping
	public ResponseEntity<StudentDTO> saveStudentDetails(@RequestBody @Valid StudentDTO stud)
	// To inform SC , to un marshall(de-serialization , json/xml --> Java obj) the
	// method arg.
	{
		System.out.println("in save student " + stud);// id : null...
//		return  ResponseEntity.ok(studService.saveStudentDetails(stud));

		return new ResponseEntity<>(studService.saveStudentDetails(stud), HttpStatus.CREATED);
	}

	// add req handling method to delete stud details
	@DeleteMapping("/{studId}") // can use ANY name for a path var.
	// @PathVariable => a binding between a path var to method arg.
	public String deleteStudentDetails(@PathVariable @Range(min = 1, max = 100, message = "Invalid student id!!!") int studId) {
		System.out.println("in del stud " + studId);
		return studService.deleteStudentDetails(studId);
	}

	// add a method to get specific student  dtls
	@GetMapping("/{id}")
	// @PathVariable => a binding between a path var to method arg.
	public ResponseEntity<?> getStudentDetails(@PathVariable int id) {
		System.out.println("in get stud " + id);
		Student student = studService.getStudentDetails(id);
		System.out.println("stud class " + student.getClass());
		return ResponseEntity.ok(student);

	}

//	// add a method to update existing resource
	@PutMapping
	public Student updateStudentDetails(@RequestBody Student stud) {
		System.out.println("in update student " + stud);// id not null
		return studService.updateStudentDetails(stud);
	}

	// add a method to upload image on the server side folder
	@PostMapping("/{studId}/image")
	public ResponseEntity<?> uploadImage(@PathVariable int studId, @RequestParam MultipartFile imageFile)
			throws IOException {
		System.out.println("in upload image " + studId);
		System.out.println("uploaded img file name " + imageFile.getOriginalFilename() + " content type "
				+ imageFile.getContentType() + " size " + imageFile.getSize());
		// invoke service layer method to save uploaded file in the server side folder
		// --ImageHandligService
		StudentDTO studentDTO = imageHandlingService.storeImage(studId, imageFile);
		return ResponseEntity.ok(studentDTO);
	}

	// add req handling method to download image for specific student
	@GetMapping(value = "/{studId}/image",produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE,
			MediaType.IMAGE_PNG_VALUE })
	public ResponseEntity<?> downloadImage(@PathVariable int studId) throws IOException{
		System.out.println("in img download " + studId);
		//invoke service layer method , to get image data from the server side folder
		byte[] imageContents=imageHandlingService.restoreImage(studId);
		return ResponseEntity.ok(imageContents);
	}

}
