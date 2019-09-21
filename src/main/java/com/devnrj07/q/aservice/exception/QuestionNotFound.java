package com.devnrj07.q.aservice.exception;

public class QuestionNotFound extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuestionNotFound(String qId) {
		super("Question Not Found with id "+ qId);
	}

}
