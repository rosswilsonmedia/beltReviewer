package com.codingdojo.beltreviewer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.beltreviewer.models.State;

public interface StateRepository extends CrudRepository<State, Long> {
	List<State> findAll();
}
