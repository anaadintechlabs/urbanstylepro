package com.urbanstyle.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.urbanstyle.product.property.FileStorageProperties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@EntityScan("com.anaadihsoft.*")
@EnableJpaRepositories("com.urbanstyle.*")
@ComponentScan(basePackages = { "com.urbanstyle.*" })
@SpringBootApplication
@EnableConfigurationProperties({
	FileStorageProperties.class
})
public class ProductApplication {

	public static void main(String[] args) {
		final Logger log=LoggerFactory.getLogger(ProductApplication.class);
		SpringApplication.run(ProductApplication.class, args);
		log.info("starter called");
	}

}
