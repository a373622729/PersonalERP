package com.qf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@ServletComponentScan
@MapperScan("com.qf.mapper")
//@EnableScheduling
//@EnableCaching
public class PersonalErpApplication {

//	@Bean
//	public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//		return new EmbeddedServletContainerCustomizer() {
//			@Override
//			public void customize(ConfigurableEmbeddedServletContainer container) {
//
////				ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
//				ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
//				ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
//
//				container.addErrorPages(error404Page, error500Page);
//			}
//		};
//	}

	public static void main(String[] args) {
		SpringApplication.run(PersonalErpApplication.class, args);
	}
}
