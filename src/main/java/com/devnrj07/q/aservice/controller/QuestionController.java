package com.devnrj07.q.aservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.devnrj07.q.aservice.businesslayer.FilterQuestionsService;
import com.devnrj07.q.aservice.exception.QuestionNotFound;
import com.devnrj07.q.aservice.model.ErrorResponse;
import com.devnrj07.q.aservice.model.Questions;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class QuestionController {

	@Autowired
	private FilterQuestionsService filterQuestionsService;

	@GetMapping("/questions")
	public Flux<Questions> AllQuestions() {
		return filterQuestionsService.getAllQuestions();
	}

	// Streaming DATA instead of sending as an JSON array
	@GetMapping(value = "/stream/questions", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Questions> streamQuestions() {
		return filterQuestionsService.streamAllQuestions();
	}

	@GetMapping("/question/{id}")
	public Mono<ResponseEntity<Questions>> QuestionsById(@PathVariable("id") String qId) {
		return filterQuestionsService.getQuestionById(qId).map(data -> ResponseEntity.ok(data))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@PostMapping("/questions")
	public Mono<Questions> addAQuestion(@Valid @RequestBody Questions question) {
		return filterQuestionsService.postAQuestion(question);
	}

	@PutMapping("/question/{id}")
	public Mono<ResponseEntity<Questions>> editAQuestion(@PathVariable("id") String qId,
			@Valid @RequestBody Questions question) {

		return filterQuestionsService.updateAQuestion(qId, question)
				.map(updatedQuestion -> new ResponseEntity<>(updatedQuestion, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));

	}

	@DeleteMapping("/question/{id}")
	public Mono<ResponseEntity<String>> removeAQuestion(@PathVariable("id") String qId) {

		return filterQuestionsService.deleteAQuestion(qId).map(data -> new ResponseEntity<>(qId, HttpStatus.OK));
	}

	/*

	Exception Handling

	Examples (These can be put into a @ControllerAdvice to handle exceptions globally)
*/

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse("A Tweet with the same text already exists"));
	}

	@ExceptionHandler(QuestionNotFound.class)
	public ResponseEntity handleTweetNotFoundException(QuestionNotFound ex) {
		return ResponseEntity.notFound().build();
	}

}
