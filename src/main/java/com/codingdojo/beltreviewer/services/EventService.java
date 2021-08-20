package com.codingdojo.beltreviewer.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codingdojo.beltreviewer.models.Comment;
import com.codingdojo.beltreviewer.models.Event;
import com.codingdojo.beltreviewer.models.State;
import com.codingdojo.beltreviewer.repositories.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private CommentService commentService;

	private final EventRepository eventRepository;
	
	public EventService(EventRepository eventRepository) {
		this.eventRepository=eventRepository;
	}
	
	public List<Event> getAll(){
		return eventRepository.findAll();
	}
	
	public List<Event> getByState(State state){
		return eventRepository.findByState(state);
	}
	
	public List<Event> getByNotInState(State state){
		return eventRepository.findByStateNot(state);
	}
	
	public Event getOne(Long id) {
		Optional<Event> event = eventRepository.findById(id);
		if(event.isPresent()) {
			return event.get();
		}
		return null;
	}
	
	public Event createOne(Event e) {
		return eventRepository.save(e);
	}
	
	public Event updateOne(Event e) {
		return eventRepository.save(e);
	}
	
	public void deleteOne(Long id) {
		Event event = getOne(id);
		if(event != null) {
			for(Comment comment:event.getComments()) {
				commentService.deleteOne(comment.getId());
			}
			event.setAttendees(null);
			event.setComments(null);
			updateOne(event);
			eventRepository.deleteById(id);
		}
	}
}
