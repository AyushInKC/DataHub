package com.JavaJunkie.DataHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class DataHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(DataHubApplication.class, args);
	}

}
