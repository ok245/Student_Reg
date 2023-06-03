package com.app.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import com.app.custom_exceptions.ResourceNotFoundException;
import com.app.dto.StudentDTO;
import com.app.entities.Student;
import com.app.repository.StudentRepository;

@Service
@Transactional
public class ImageHandlingServiceImpl implements ImageHandlingService {
	@Value("${file.upload.location}")
	private String baseFolder;
	// dep : emp dao i/f
	@Autowired
	private StudentRepository studRepo;
	// dep : Model mapper
	@Autowired
	private ModelMapper mapper;

	@Override
	public StudentDTO storeImage(int studId, MultipartFile imageFile) throws IOException {
		// get emp dtls from emp id
		Student stud = studRepo.findById(studId).orElseThrow(() -> new ResourceNotFoundException("Invalid stud Id"));
		// emp => persistent
		// get complete path to the file , to be stored
		String completePath = baseFolder + File.separator + imageFile.getOriginalFilename();
		System.out.println("complete path " + completePath);
		System.out.println("Copied no of bytes "
				+ Files.copy(imageFile.getInputStream(), Paths.get(completePath), StandardCopyOption.REPLACE_EXISTING));
		// save complete path to the image in db
		
		//In case of saving file in db : simply call : imageFile.getBytes() --> byte[] --call setter on emp !
		stud.setImagePath(completePath);// save complete path to the file in db
		return mapper.map(stud, StudentDTO.class);
	}

	@Override
	public byte[] restoreImage(int studId) throws IOException{
		// get emp dtls from emp id
		Student stud = studRepo.findById(studId).orElseThrow(() -> new ResourceNotFoundException("Invalid Stud Id"));
		// emp => persistent
		// get complete img path from db --> extract image contents n send it to the
		// caller
		String path = stud.getImagePath();
		System.out.println("img path " + path);
		//API of java.nio.file.Files class : public byte[] readAllBytes(Path path)
		return Files.readAllBytes(Paths.get(path));
		//in case of BLOB in DB --simply call emp.getImage() --> byte[] --> ret it to the caller!
	}

}
