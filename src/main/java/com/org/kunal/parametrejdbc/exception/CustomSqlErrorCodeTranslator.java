/**
 * @author Kumar.Kunal
 */
package com.org.kunal.parametrejdbc.exception;

import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

/**
 * @author kunal
 *
 */
public class CustomSqlErrorCodeTranslator extends SQLErrorCodeSQLExceptionTranslator {

	@Override
	protected DataAccessException customTranslate(String task, String sql, SQLException sqlException) {
		if (sqlException.getErrorCode() == 1062) {
			return new DuplicateKeyException("Custom SQL Error Code Translator. Duplicate Exception Raised", sqlException);
		}
		return super.customTranslate(task, sql, sqlException);
	}

}
