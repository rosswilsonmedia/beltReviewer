package com.codingdojo.beltreviewer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codingdojo.beltreviewer.models.State;
import com.codingdojo.beltreviewer.repositories.StateRepository;

@Service
public class StateService {

	private final StateRepository stateRepository;
	
	public StateService(StateRepository stateRepository) {
		this.stateRepository=stateRepository;
	}
	
	public List<State> getAll(){
		return stateRepository.findAll();
	}

}
