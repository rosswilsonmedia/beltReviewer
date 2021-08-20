package com.codingdojo.beltreviewer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.beltreviewer.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	List<User> findAll();
	User findByEmail(String email);
}
