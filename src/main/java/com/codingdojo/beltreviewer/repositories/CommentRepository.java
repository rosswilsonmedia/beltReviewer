package com.codingdojo.beltreviewer.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.codingdojo.beltreviewer.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Long> {
	List<Comment> findAll();
}
