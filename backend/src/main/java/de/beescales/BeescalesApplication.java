package de.beescales;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.Properties;

@EnableScheduling
@SpringBootApplication
@EnableJpaRepositories
public class BeescalesApplication {

  public static void main(String[] args) {
    SpringApplication.run(BeescalesApplication.class, args);
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    final Properties users = new Properties();
    users.put("username", "password,ROLE_USER,enabled");
    return new InMemoryUserDetailsManager(users);
  }
}
