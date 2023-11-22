package com.org.kunal.parametrejdbc.exception;
/**
 * @author kunal
 * @project SpringBootNamedParametreJdbcTemplate
 */
public class DisabledUserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DisabledUserException(String msg) {
		super(msg);
	}

}
