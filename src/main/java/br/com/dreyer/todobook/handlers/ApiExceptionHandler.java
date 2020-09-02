package br.com.dreyer.todobook.handlers;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import br.com.dreyer.todobook.models.exceptions.ObjectNotFoundException;

/**
 * @author <a href="mailto:henriquedreyer@gmail.com">Henrique Dreyer</a>
 *
 * @sinse 13 de abr de 2020
 */
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		BindingResult bindingResult = ex.getBindingResult();

		List<StandardSpringError.Field> fields = bindingResult.getFieldErrors().stream().map(field -> {
			String fieldName = field.getField();
			String fieldMessage = field.getDefaultMessage();

			return StandardSpringError.Field.builder().name(fieldName).message(fieldMessage).build();
		}).collect(Collectors.toList());

		StandardSpringError body = StandardSpringError.builder().timestamp(LocalDateTime.now()).status(status.value())
				.error(status.name()).path(((ServletWebRequest) request).getRequest().getRequestURI())
				.message("Alguns campos obrigatórios precisam ser preenchidos.").fields(fields).build();

		return super.handleExceptionInternal(ex, body, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.name()).message("Invalid URL")
				.path(((ServletWebRequest) request).getRequest().getRequestURI()).build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		Throwable root = ExceptionUtils.getRootCause(ex);
		if (root instanceof InvalidFormatException) {
			return handleInvalidFormatException((InvalidFormatException) root, headers, status, request);

		} else if (root instanceof UnrecognizedPropertyException) {
			return handleUnrecognizedPropertyException((UnrecognizedPropertyException) root, headers, status, request);
		}

		// TODO
		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.name()).message(ex.getMessage())
				.path(((ServletWebRequest) request).getRequest().getRequestURI()).build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private ResponseEntity<Object> handleUnrecognizedPropertyException(UnrecognizedPropertyException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(field -> field.getFieldName()).collect(Collectors.joining("."));
		String detail = String.format("The property '%s' not exists. Remove this and try again.", path);

		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(status.BAD_REQUEST.value()).error(status.BAD_REQUEST.name()).message(detail)
				.path(((ServletWebRequest) request).getRequest().getRequestURI()).build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	private ResponseEntity<Object> handleInvalidFormatException(InvalidFormatException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {

		String path = ex.getPath().stream().map(ref -> ref.getFieldName()).collect(Collectors.joining("."));

		String detail = String.format("The property '%s' is not compatible with '%s'. Enter a %s value.", path,
				ex.getValue(), ex.getTargetType().getSimpleName());

		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(status.BAD_REQUEST.value()).error(status.BAD_REQUEST.name()).message(detail)
				.path(((ServletWebRequest) request).getRequest().getRequestURI()).build();

		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<Object> handleObjectNotFound(ObjectNotFoundException objectNotFound, WebRequest req) {
		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.name())
				.message(objectNotFound.getMessage()).path(((ServletWebRequest) req).getRequest().getRequestURI())
				.build();

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.name()).message("Invalid URL")
				.path(((ServletWebRequest) request).getRequest().getRequestURI()).build();
		return handleExceptionInternal(ex, error, headers, status, request);
	}

	@ExceptionHandler(SQLException.class)
	public ResponseEntity<Object> handleSQLException(
			SQLException ex, WebRequest req) {
		
		StandardSpringError error = StandardSpringError.builder().timestamp(LocalDateTime.now())
				.status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.name())
				.message(ex.getMessage()).path(((ServletWebRequest) req).getRequest().getRequestURI())
				.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

}
