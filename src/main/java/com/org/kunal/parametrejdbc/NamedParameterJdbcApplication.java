package com.org.kunal.parametrejdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
/**
 * Kumar.Kunal
 * SpringBootNamedParameterJdbcTemplate
 * 2023
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class NamedParameterJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(NamedParameterJdbcApplication.class, args);
	}

}
