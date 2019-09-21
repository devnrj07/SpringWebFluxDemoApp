package com.devnrj07.q.aservice.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Data;
import reactor.core.publisher.Flux;

@Data
public class Answers extends UserDetails {
	
	
	private String aId;
	
	@NotBlank
	@Size(max = 500)
	private String answer;
    
	private static long yes;
	
	private static long no;
	
	@NotBlank
	private Comments comments;
}
