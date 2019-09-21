package com.devnrj07.q.aservice.businesslayer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devnrj07.q.aservice.exception.QuestionNotFound;
import com.devnrj07.q.aservice.model.Answers;
import com.devnrj07.q.aservice.model.Comments;
import com.devnrj07.q.aservice.model.Questions;
import com.devnrj07.q.aservice.repository.QuestionsRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class FilterQuestionsService {

	@Autowired
	private QuestionsRepository questionsRepository;
	
	
	
	public Flux<Questions> getAllQuestions(){
		
		return questionsRepository.findAll();
	}
	
	
	 // Questions are Sent to the client as Server Sent Events
	public Flux<Questions> streamAllQuestions(){
		
		return questionsRepository.findAll();
	}
	
	public Mono<Questions> getQuestionById(String qId){
		
		
		return questionsRepository.findById(qId);
	}
	
	public Flux<Questions> getQuestionsByCategory(String category){
		
		return questionsRepository.findAll()
				 .filter(data -> data.getCategory().toString()==category);
	}
	
	public Flux<Questions> getQuestionsAnsweredByUser(String uId){
		/*return questionsRepository.findById(uId)
					.flatmap(data -> data.getAnswers().filter(answerData -> answerData.getUId().toString()==uId));*/
		
		return null;
	}
	
	public Mono<Questions> postAQuestion(Questions question){
		String qId = "Ques::"+ Math.floor(Math.random()*1L);
		Mono<Questions> response = questionsRepository.findById(qId);
		
		 if(response == null) {
		  question.setQId(qId);  
	      return questionsRepository.save(question);
		 
		 }
		 else {
			 //handle error case gracefully;
			 return Mono.just(question);
		 } 
		
	}
	
	public String postAnAnswer(String qId, Answers answer){
		
		Mono<Questions> response = questionsRepository.findById(qId);
		String aId = "Ans::"+ Math.floor(Math.random()*1L);
		 if(response!= null) {
			 answer.setAId(aId);
			 response.block().setAnswers(answer);
			 
		 }
		 else {
			 throw new QuestionNotFound(qId);
		 }
		return aId;
	}
	
	public String postAcomment(String qId, String aId,Comments comment) {
		
		Mono<Questions> response = questionsRepository.findById(qId);
		String cId = "Comm::"+ Math.floor(Math.random()*1L);
		if(qId!= "" && aId == "") {
			//This is a question comment
			comment.setCId(cId);
			response.block().setComments(comment);
		}
		if(qId!="" && aId!="") {
			//This is an answer comment
			comment.setCId(cId);
		    response.block().getAnswers().setComments(comment);
			
		}
		if(response == null) {
			throw new QuestionNotFound(qId);
		}
		return cId;
	}
	
	public Mono<Questions> updateAQuestion(String qId, Questions question) {
		
		Mono<Questions> response = questionsRepository.findById(qId);
		if(response.hasElement().toString() == "true") {
			questionsRepository.deleteById(qId);
			question.setQId(qId);
			return questionsRepository.save(question);
			
		}
		else {
			throw new QuestionNotFound(qId);
		}
		
		
	}
	
	public void updateAnAnswer(String qId,String aId, Answers answer) {
		
		
		
	}
	
	public Long updateNoOfYes(String aId, double value){
		return 1L;
	}
	
	public Long updateNoOfNo(String aId, double value){
		
		return 1L;
	}
	
	public Mono<String> deleteAQuestion(String qId){
		
		 return  questionsRepository.findById(qId)
				   .flatMap(existingQuestion -> questionsRepository.delete(existingQuestion).then(Mono.just(qId)));
	}
	
	public Mono<String> deleteAnAnswer(String qId,String aId){
		
		return null;
	}
	
	public Mono<String> deleteAComment(String qId, String aId, String dId){
		return null;
	}
}
