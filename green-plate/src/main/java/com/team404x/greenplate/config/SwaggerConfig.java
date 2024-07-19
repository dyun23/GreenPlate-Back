package com.team404x.greenplate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.media.StringSchema;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {



	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
			.components(new Components()
				.addSecuritySchemes("bearerAuth", new SecurityScheme()
					.type(SecurityScheme.Type.HTTP)
					.scheme("bearer")
					.bearerFormat("JWT")
					.in(SecurityScheme.In.HEADER)
					.name("Authorization")))
			.info(new Info()
				.title("Green Plate")
				.description("Green Plate API")
				.version("1.0.0"));
	}

	@Bean
	public OperationCustomizer customize() {
		return (operation, handlerMethod) -> {
			if (handlerMethod.hasMethodAnnotation(SecuredOperation.class)) {
				operation.addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
			}
			return operation;
		};
	}
}
