package br.com.dreyer.todobook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.dreyer.todobook.models.Item;

public interface ItemRepository extends JpaRepository<Item, Long>{
	
	@Query(value = "from Item where active = true")
	public List<Item> findAll();
	
	@Query(value = "from Item where todolist_id = :id and active = true")
	public List<Item> findAll(Long id);

}
