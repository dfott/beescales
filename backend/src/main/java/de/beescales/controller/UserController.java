package de.beescales.controller;

import de.beescales.data.Admin;
import de.beescales.data.Token;
import de.beescales.util.JwtUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {

  JwtUtil jwtUtil;
  InMemoryUserDetailsManager userDetailsManager;

  @PostMapping("/auth")
  public ResponseEntity<Token> getAuthentication(@RequestBody Admin admin) {
    UserDetails user = userDetailsManager.loadUserByUsername(admin.getUsername());
    if (user != null && user.getPassword().equals(admin.getPassword())) {
      Token token = new Token(jwtUtil.generateToken(user));
      return ResponseEntity.ok(token);
    }
    return ResponseEntity.badRequest().build();
  }
}
