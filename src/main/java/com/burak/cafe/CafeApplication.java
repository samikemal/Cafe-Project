package com.burak.cafe;

import com.burak.cafe.controller.ErrorController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ServletComponentScan
@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.burak.cafe.repositories"})
@ComponentScan(basePackages = {"com.burak.cafe.dao.Imp"},basePackageClasses = ErrorController.class)
@EntityScan(basePackages = {"com.burak.cafe.entity"})
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})//Spring Boot’a classpath bazında beans,
// diğer bean’leri ve çeşitli özellik ayarlarını eklemeye başlasın der.
public class CafeApplication
{
	public static void main(String[] args) {
		SpringApplication.run(CafeApplication.class, args);
	}
}
