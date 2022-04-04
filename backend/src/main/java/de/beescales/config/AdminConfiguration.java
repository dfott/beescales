package de.beescales.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.Properties;

@Configuration
public class AdminConfiguration {

  @Value("${ADMIN_USER:user}")
  private String username;

  @Value("${ADMIN_PASS:password123}")
  private String password;

  public String getUsername() {
    return this.username;
  }

  public String getPassword() {
    return this.password;
  }

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    final Properties users = new Properties();
    users.put(this.username, this.password + ",ROLE_USER,enabled");
    return new InMemoryUserDetailsManager(users);
  }
}
