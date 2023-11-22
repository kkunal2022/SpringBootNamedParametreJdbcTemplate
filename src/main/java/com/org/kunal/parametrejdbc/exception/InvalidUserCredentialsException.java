package com.org.kunal.parametrejdbc.exception;
/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
public class InvalidUserCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidUserCredentialsException(String msg) {
		super(msg);
	}

}
