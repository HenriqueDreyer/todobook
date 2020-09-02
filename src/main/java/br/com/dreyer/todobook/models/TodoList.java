package br.com.dreyer.todobook.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity
public class TodoList implements Serializable {

	private static final long serialVersionUID = 6091905513575534698L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	private String title;

	@JsonIgnoreProperties(value = {"description","todoList"})
	@Valid
	@OneToMany(mappedBy = "todoList", fetch = FetchType.LAZY)
	private List<Item> itens;
}
