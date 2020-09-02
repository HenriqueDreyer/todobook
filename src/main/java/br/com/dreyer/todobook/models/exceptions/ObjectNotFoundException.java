package br.com.dreyer.todobook.models.exceptions;

public class ObjectNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = -7363132023443917816L;

	public ObjectNotFoundException(String message) {
		super(message);
	}
	
	public ObjectNotFoundException(Long id, String entity) {
		super(String.format("Object [id]:%d [class]:%s not found.", id, entity));
	}
}
