package com.service.sport_companion.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.service.sport_companion.domain.repository")
@EntityScan(basePackages = "com.service.sport_companion.domain")
@ComponentScan(basePackages = "com.service.sport_companion")
@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BatchApplication.class, args);
	}

}
