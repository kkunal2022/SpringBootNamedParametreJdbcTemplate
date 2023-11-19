/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.config
 */
package com.org.kunal.parametrejdbc.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * kunal
 * parametrejdbc
 * 2023
*/
@Configuration
public class MapperConfig {

	@Bean
	ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
