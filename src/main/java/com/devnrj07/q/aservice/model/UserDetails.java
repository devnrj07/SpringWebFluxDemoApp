package com.devnrj07.q.aservice.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetails {

	
	private String uId;
	
	@NotBlank
	private String userName;
	
	@NotNull
	private Date createdAt = new Date();
	
	@NotNull
	private boolean edited = false;
}
