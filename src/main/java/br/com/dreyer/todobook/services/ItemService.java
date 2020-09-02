package br.com.dreyer.todobook.services;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.BeanSupplierContext;
import org.springframework.stereotype.Service;

import br.com.dreyer.todobook.models.Item;
import br.com.dreyer.todobook.models.TodoList;
import br.com.dreyer.todobook.models.exceptions.ObjectNotFoundException;
import br.com.dreyer.todobook.repository.ItemRepository;
import br.com.dreyer.todobook.repository.TodoListRepository;

@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;

	@Autowired
	private TodoListRepository todoListRepository;

	public Item findBy(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(String.format("Item id %d not found.", id)));
	}

	public List<Item> findAll() {
		return repository.findAll();
	}
	
	public List<Item> findAll(Long id) {
		return repository.findAll(id);
	}

	public Item save(Item entity) {
		Long todoListid = entity.getTodoList().getId();
		TodoList todoList = todoListRepository.findById(todoListid)
				.orElseThrow(() -> new ObjectNotFoundException(String.format("TodoList id %d not found", todoListid)));
		
		return repository.save(entity);
	}

	public Item update(Long id, Item entity) {
		Item target = findBy(id);

		BeanUtils.copyProperties(entity, target, "id");
		return repository.save(target);
	}

	public void delete(Long id) {
		Item target = findBy(id);
		repository.delete(target);
	}

}
