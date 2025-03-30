package com.thymeleaf.crud.thcrud.Service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final PasswordEncoder passwordEncoder;

  // Injecting the PasswordEncoder bean
  public UserService(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  public String encryptPassword(String password) {
    return passwordEncoder.encode(password);
  }
}
