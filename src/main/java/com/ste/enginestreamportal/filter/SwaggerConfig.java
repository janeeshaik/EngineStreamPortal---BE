package com.ste.enginestreamportal.filter;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/**"))
				.apis(RequestHandlerSelectors.basePackage("com.ste.enginestreamportal"))
				.build()
				.apiInfo(apiDetails());
	}
	
	private ApiInfo apiDetails() {
		ApiInfo apiInfo = new ApiInfo(
				"Engine Stream Portal API",
				"Sample API's for Engine Stream Portal",
				"1.0",
				"Terms and service URL", 
				null, 
				"Engine Stream Portal API URL", 
				"http://com.ste.enginestreamportal",
				Collections.emptyList()
				);
				return apiInfo;
	}
	
}
