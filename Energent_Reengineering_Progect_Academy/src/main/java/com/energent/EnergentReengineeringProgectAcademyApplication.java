package com.energent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;

@SpringBootApplication
public class EnergentReengineeringProgectAcademyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergentReengineeringProgectAcademyApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                   .title("Documentation: Academy Management Project Work.")
                   .description("MySite Service Exposed Operations.\nSee Website to watch an implementation of those\n\n<u>Realized by</u>:<ul><b><li>Gianmarco</li><li>Marco</li><li>Jamir</li><li>Antonio</li></b></ul>\n<small><b>For info or support:</b>\nMarco: 3207931840</small>")
                   .version("1.0")
                   .contact(new Contact("Marco", "https://github.com/marco300596/progetto-corso", "m.casentini96@gmail.com"))
                   .build();
    }
}
