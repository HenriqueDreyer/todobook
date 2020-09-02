package br.com.dreyer.todobook.services;

import br.com.dreyer.todobook.models.TodoList;
import br.com.dreyer.todobook.models.exceptions.ObjectNotFoundException;
import br.com.dreyer.todobook.repository.TodoListRepository;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TodoListService {

    @Autowired
    private TodoListRepository repository;

    public List<TodoList> findAll() {
    	return repository.findAll();
    }
    
    public TodoList findBy(Long id) {
    	return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(String.format("Todo List id %d not found.", id)));
    }
    
    public TodoList save(TodoList source) {
    	return repository.save(source);
    }
    
    public TodoList update(Long id, TodoList source) {
    	TodoList target = findBy(id);
    	BeanUtils.copyProperties(source, target, "id");
    	
    	return repository.save(target);
    }
    
    public void delete(Long id) {
    	TodoList source = findBy(id);
    	repository.delete(source);
    }
}
