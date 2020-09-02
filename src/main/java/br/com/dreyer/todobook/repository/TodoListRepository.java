package br.com.dreyer.todobook.repository;

import br.com.dreyer.todobook.models.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoListRepository extends JpaRepository<TodoList, Long> {
}
