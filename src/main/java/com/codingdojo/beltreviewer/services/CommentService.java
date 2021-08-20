package com.codingdojo.beltreviewer.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.codingdojo.beltreviewer.models.Comment;
import com.codingdojo.beltreviewer.repositories.CommentRepository;

@Service
public class CommentService {

	private final CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository=commentRepository;
	}
	
	public List<Comment> getAll(){
		return commentRepository.findAll();
	}
	
	public Comment createOne(Comment c) {
		return commentRepository.save(c);
	}
	
    public void deleteOne(Long id) {
    	commentRepository.deleteById(id);
    }
}
