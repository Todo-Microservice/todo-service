package com.example.demo.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Todo;

public interface TodoRepository extends CrudRepository<Todo, Integer> {
	String findByTitle(String words);
	
	List<Todo> findAllByCreatedBy(Integer createdBy);
}
