package com.devnrj07.q.aservice.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.devnrj07.q.aservice.model.Questions;

@Repository
public interface QuestionsRepository extends ReactiveMongoRepository<Questions,String>{

}
