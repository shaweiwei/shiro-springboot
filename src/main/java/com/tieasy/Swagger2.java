package com.tieasy;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2可以很好的制作RESTful API，它可以轻松的整合到Spring Boot中，并与Spring MVC程序配合组织出强
 * 大RESTful API文档。它既可以减少我们创建文档的工作量，同时说明内容又整合入实现代码中，让维护文档和修改代
 * 码整合为一体，可以让我们在修改代码逻辑的同时方便的修改文档说明。另外Swagger2也提供了强大的页面测试功能来
 * 调试每个RESTful API
 * @author ko
 *
 */
@Configuration
@EnableSwagger2
public class Swagger2 {
	
	@Bean
	public Docket createRestApi() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).select()
				.apis(RequestHandlerSelectors.basePackage("com.tieasy.controller")).paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("Tieasy无纸化快检系统API")
				.description("快检系统分为PC端和移动端，移动端方便检验员现场检验记录上传报告，PC端方便报告归档打印。")
				.termsOfServiceUrl("http://www.woshishabi.com/").contact("woshishabi").version("1.0").build();
	}

}
