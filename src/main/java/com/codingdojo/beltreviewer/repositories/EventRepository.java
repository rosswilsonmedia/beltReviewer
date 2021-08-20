package com.codingdojo.beltreviewer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.beltreviewer.models.Event;
import com.codingdojo.beltreviewer.models.State;

public interface EventRepository extends CrudRepository<Event, Long> {
	List<Event> findAll();
	
	List<Event> findByState(State state);
	
	List<Event> findByStateNot(State state);
}
