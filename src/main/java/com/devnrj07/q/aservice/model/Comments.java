package com.devnrj07.q.aservice.model;

import java.util.List;

import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Comments extends UserDetails{
	
	
	private String cId;
	
	@Size(max=200)
	private List<String> comment;
    
}
