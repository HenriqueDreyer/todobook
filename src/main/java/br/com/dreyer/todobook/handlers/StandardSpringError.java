package br.com.dreyer.todobook.handlers;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Builder;
import lombok.Getter;

/**
 * @author <a href="mailto:henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @sinse 13 de abr de 2020
 */

@JsonInclude(value = Include.NON_NULL)
@Getter
@Builder
public class StandardSpringError implements Serializable{

	private static final long serialVersionUID = -1628563396417304928L;
	
	private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;    
    private List<Field> fields;
        
    @Getter
    @Builder
    public static class Field {
    	private String name;
		private String message;
    }
}
