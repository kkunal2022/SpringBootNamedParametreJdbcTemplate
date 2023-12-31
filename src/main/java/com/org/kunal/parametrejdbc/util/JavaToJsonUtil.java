/**
 * kunal
 * parametrejdbc
 * com.org.kunal.parametrejdbc.util
 */
package com.org.kunal.parametrejdbc.util;

import com.github.victools.jsonschema.generator.SchemaGeneratorConfigBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.OptionPreset;
import com.github.victools.jsonschema.generator.SchemaGenerator;
import com.github.victools.jsonschema.generator.SchemaGeneratorConfig;
import com.github.victools.jsonschema.generator.SchemaVersion;
import com.org.kunal.parametrejdbc.stockitemnew.Stocks;
import lombok.extern.slf4j.Slf4j;

/**
 * Kumar.Kunal
 * parametrejdbc
 * 2023
*/
@Slf4j
public class JavaToJsonUtil {
	
	public static void main(String[] args) {
        SchemaGeneratorConfigBuilder schemaGeneratorConfigBuilder = new SchemaGeneratorConfigBuilder
                (SchemaVersion.DRAFT_2020_12, OptionPreset.PLAIN_JSON);
        SchemaGeneratorConfig schemaGeneratorConfig = schemaGeneratorConfigBuilder.build();
        SchemaGenerator schemaGenerator = new SchemaGenerator(schemaGeneratorConfig);

        //JsonNode jsonSchema = schemaGenerator.generateSchema(StockRequest.class);
        JsonNode jsonSchema = schemaGenerator.generateSchema(Stocks.class);
        System.out.println(jsonSchema.toPrettyString());
    }

}
