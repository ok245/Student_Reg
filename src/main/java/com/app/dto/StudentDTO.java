package com.app.dto;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
//salary , joinDate , lastName (Treat earlier name as first name : unique),password,email

@Getter
@Setter
@ToString

public class StudentDTO {
	@JsonProperty("id")
	private Integer studentId;
	@NotEmpty(message = "First name must be supplied")
	@Length(min = 4, max = 30, message = "Invalid First name length")

	private String name;

	@NotBlank(message = "Last name must be supplied")
	private String lastName;

	@NotBlank
	@Email(message = "Invalid Email")
	private String email;

	@JsonProperty(access = Access.WRITE_ONLY) //for de-serial only
	private String password;

	@NotBlank(message = "depatment is required")
	private String courseName;
	
	@Range(min = 10000, max = 50000, message = "Invalid salary")
	private double  courseFee;
	@Future(message = "join date msut be in future")
	private LocalDate enrollmentDate;
	@JsonProperty(access = Access.READ_ONLY) //for serialization only
	private String imagePath;

}
