/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.exception
 */
package com.org.kunal.parametrejdbc.exception;

import java.io.Serial;

/**
 * Kumar.Kunal
 */
public class ApiException extends RuntimeException {
	
	@Serial
	private static final long serialVersionUID = 5551467266748177173L;

	public ApiException(String message) {
		super(message);
	}

}