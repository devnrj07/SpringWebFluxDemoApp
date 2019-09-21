package com.devnrj07.q.aservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection = "questions")
@Data
public class Questions extends UserDetails{

	@Id
	private String qId;
	
	@NotBlank
	@Size(max = 500)
    private String question;
	
	@NotNull
	private String[] category; 
	
	
	private Answers answers;
	
	
	private Comments comments;
}
