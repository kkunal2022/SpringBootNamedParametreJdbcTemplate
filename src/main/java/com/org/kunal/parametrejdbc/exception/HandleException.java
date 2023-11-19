/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.exception;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.org.kunal.parametrejdbc.purchaserequest.PurchaseRequestExceptionEnum;
import com.org.kunal.parametrejdbc.purchaserequest.ExceptionResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author kunal
 *
 */
@ControllerAdvice
@Slf4j
public class HandleException extends ResponseEntityExceptionHandler {

	/*
	 * Kumar.Kunal
	 */
	@ExceptionHandler(PurchaseRequestNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ExceptionResponse handle() {

		return ExceptionResponse.builder()
				.message(PurchaseRequestExceptionEnum.PURCHASE_REQUEST_NOT_FOUND_EXCEPTION.getMessage())
				.localDateTime(LocalDateTime.now()).build();
	}

	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<ExceptionResponse> handleMaxSizeException(MaxUploadSizeExceededException exc) {
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new ExceptionResponse(LocalDateTime.now(), "File too large!"));
	}
	
}
