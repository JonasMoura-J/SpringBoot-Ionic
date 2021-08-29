package com.web.SpringService.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket porductApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.web.SpringService"))
				.build()
				.apiInfo(metaInfo());
	}
	
	private ApiInfo metaInfo() {
		@SuppressWarnings("rawtypes")
		ApiInfo apiInfo = new ApiInfo(
				"Spring Service API REST",
				"API REST de pedidos de produtos",
				"1.0",
				"Terms of Service",
				new Contact("Jonas de Moura", "https://www.linkedin.com/in/jonas- moura-47b766182", "jonasmourat9090@gmail.com"),
				"Apache license Version 2.0",
				"https://www.apache.org/licesen.html", new ArrayList<VendorExtension>()
				
				);
		return apiInfo;
	}
}