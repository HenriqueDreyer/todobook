package br.com.dreyer.todobook.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dreyer.todobook.models.TodoList;
import br.com.dreyer.todobook.services.TodoListService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "http://localhost:4200")
public class TodoListController {

	@Autowired
	private TodoListService service;

	@GetMapping
	public List<TodoList> findAll() {
		List<TodoList> source = service.findAll();
		return source;
	}

	@GetMapping("/{id}")
	public ResponseEntity<TodoList> findBy(@PathVariable Long id) {
		TodoList source = service.findBy(id);
		return ResponseEntity.ok(source);
	}

	@PostMapping
	public ResponseEntity<TodoList> save(@RequestBody(required = true) @Valid TodoList todoList) {
		TodoList source = service.save(todoList);
		return ResponseEntity.status(HttpStatus.CREATED).body(source);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TodoList> update(@PathVariable Long id,
			@RequestBody(required = true) @Valid TodoList todoList) {
		
		TodoList body = service.update(id, todoList);
		return ResponseEntity.ok(body);

	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}
}
