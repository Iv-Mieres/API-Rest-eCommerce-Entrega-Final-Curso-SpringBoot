package com.bazar.controlventas.controller.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.bazar.controlventas.model.exceptions.BadRequestException;
import com.bazar.controlventas.model.exceptions.ErrorDetails;
import com.bazar.controlventas.model.exceptions.StatusOkException;


@RestControllerAdvice
public class ControllExceptionsAdvice {

	// Controla Exceptions de Spring Validation

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex) {

		Map<String, String> mjError = new HashMap<>();

		for (FieldError fError : ex.getBindingResult().getFieldErrors()) {
			mjError.put(fError.getField(), fError.getDefaultMessage());
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(mjError);
	}
	
	// Controla BadRequest y Exceptiones generales 
	
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler({BadRequestException.class, Exception.class})
	public ResponseEntity<ErrorDetails> valoresUnicosExceptions(Exception ex) {

		ErrorDetails errorMj = new ErrorDetails();
		errorMj.setStatus(HttpStatus.BAD_REQUEST.value() + " BAD_REQUEST");
		errorMj.setMessage(ex.getLocalizedMessage());

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMj);
	}

	// Controla las consultas correctas que no devuelven valor

	@ResponseStatus(value = HttpStatus.OK)
	@ExceptionHandler(StatusOkException.class)
	public ResponseEntity<ErrorDetails> statusOkExceptions(Exception exSQL) {

		ErrorDetails errorMj = new ErrorDetails();
		errorMj.setStatus(HttpStatus.OK.value() + " OK");
		errorMj.setMessage(exSQL.getMessage());

		return ResponseEntity.status(HttpStatus.OK).body(errorMj);
	}

	// Controla errores de tipeo en la URI
	
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ErrorDetails> errorTipeoExceptions() {

		ErrorDetails errorMj = new ErrorDetails();
		errorMj.setStatus(HttpStatus.NOT_FOUND.value() + " NOT_FOUND");
		errorMj.setMessage("Error de tipeo. Revise los datos ingresados.");

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMj);
	}

}
