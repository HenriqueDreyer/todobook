package br.com.dreyer.todobook.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@Data
@Entity
public class Item implements Serializable {

	private static final long serialVersionUID = -2073242448396139018L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
	
	@NotEmpty
	String title;
    
    String description;
    
    Boolean active = true;

    @NotNull
    @JsonIgnoreProperties(value = {"title","itens"})
    @ManyToOne
    @JoinColumn(name = "todolist_id")
    private TodoList todoList;

    @Override
    public String toString() {
        return String.format("{ 'todolist':'%s', 'description':'%s', 'active':%b }", todoList.getTitle(), description, active);
    }
}
