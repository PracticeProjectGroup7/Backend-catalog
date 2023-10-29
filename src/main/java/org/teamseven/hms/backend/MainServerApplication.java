package org.teamseven.hms.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class MainServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(MainServerApplication.class, args);
  }

}
