package com.github.riset_backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@OpenAPIDefinition(servers = {@Server(url = "https://dev.risetconstruction.net", description = "Default Server URL")})
public class RisetBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(RisetBackendApplication.class, args);
	}
}
