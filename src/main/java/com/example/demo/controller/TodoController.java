package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Todo;
import com.example.demo.repo.TodoRepository;

@RestController
@RequestMapping("/todo")
public class TodoController {
	
	Logger logger = LoggerFactory.getLogger(TodoController.class);
	
	
	@Autowired
	private TodoRepository todoRepository;
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getTodo(@PathVariable int id) {
		Optional<Todo> todo = todoRepository.findById(id);
		
		if(todo.isPresent()) {
			return ResponseEntity.ok(todo.get());
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PostMapping
	public ResponseEntity<?> createtodo(@RequestBody Todo todo) {
		todo = todoRepository.save(todo);
		
		if(todo.getId() != null) {
			return ResponseEntity.ok(todo);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping
	public ResponseEntity<?> updateDescription(@RequestBody Todo todo) {
		 Optional<Todo> todoToUpdate = todoRepository.findById(todo.getId());
		
		if(todoToUpdate.isPresent()) {
			Todo t = todoToUpdate.get();
			t.setDesc(todo.getDesc());
			todoRepository.save(t);
			
			return ResponseEntity.ok(t);
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletetodo(@PathVariable int id) {
		Optional<Todo> todo = todoRepository.findById(id);
		
		if(todo.isPresent()) {
			todoRepository.delete(todo.get());
			return ResponseEntity.ok("success");
		}
		
		return ResponseEntity.badRequest().build();
	}
	
	@GetMapping("/u/{userId}")
	public ResponseEntity<?> getTodoForUser(@PathVariable int userId) {
		
		logger.debug("Get todos for user: " + userId);
		
		List<Todo> todos = todoRepository.findAllByCreatedBy(userId);
		
		if(todos == null) {
			return ResponseEntity.badRequest().build();
		}

		logger.debug("todos found for: " + userId);
		
		return ResponseEntity.ok(todos);
	}
}
