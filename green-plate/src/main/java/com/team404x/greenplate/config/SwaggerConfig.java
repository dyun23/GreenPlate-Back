package com.team404x.greenplate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI().components(new Components()).info(info());
	}

	private Info info() {
		return new Info().title("Green Plate").description("Green Plate API").version("1.0.0");
	}
}
