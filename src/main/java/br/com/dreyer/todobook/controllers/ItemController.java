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

import br.com.dreyer.todobook.models.Item;
import br.com.dreyer.todobook.services.ItemService;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {
	
	@Autowired
	private ItemService service;
	
	@GetMapping
	public List<Item> findAll(){
		List<Item> itens = service.findAll();
		return itens;
	}
	
	@GetMapping("/todolist/{id}")
	public List<Item> findAll(@PathVariable Long todoList_id){
		List<Item> itens = service.findAll(todoList_id);
		return itens;
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Item> findBy(@PathVariable(name = "id") Long id) {
		Item item = service.findBy(id);
		return ResponseEntity.ok(item);
	}
	
	@PostMapping
	public ResponseEntity<Item> save(@RequestBody(required = true) @Valid Item item){
		Item source = service.save(item);
		return ResponseEntity.status(HttpStatus.CREATED).body(source);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody(required = true) @Valid Item item){
		Item source = service.update(id, item);
		return ResponseEntity.ok(source);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.ok().build();
	}

}
