package com.org.kunal.parametrejdbc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
/**
 * Kumar.Kunal
 */
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ParametreJdbcApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParametreJdbcApplication.class, args);
	}

}
