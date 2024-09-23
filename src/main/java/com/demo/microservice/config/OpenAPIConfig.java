package com.demo.microservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
	@Bean
	public OpenAPI myOpenAPI() {

		Contact contact = new Contact();
		contact.setEmail("juanpasilva22@gmail.com");
		contact.setName("Juan Pablo Silva");
		contact.setUrl("https://www.linkedin.com/in/juan-pablo-silva-7a288a75");

		Info info = new Info()
				.title("Clientes API")
				.version("1.0")
				.contact(contact)
				.description("Esta API expone los correspondientes endpoints para administrar los clientes.");

		return new OpenAPI().info(info);
	}
}
