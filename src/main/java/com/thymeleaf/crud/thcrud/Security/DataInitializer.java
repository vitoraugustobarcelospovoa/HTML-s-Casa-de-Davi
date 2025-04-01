package com.thymeleaf.crud.thcrud.Security;

import com.thymeleaf.crud.thcrud.Model.UserModel;
import com.thymeleaf.crud.thcrud.Repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findByUsername("admin").isEmpty()) {
            UserModel user = new UserModel();
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("123456"));
            user.setCPF("000.000.000-00");
            user.setTelefone("(00)99999-9999");
            user.setCep("00000-000");
            user.setEndereco("Rua Exemplo");
            user.setBairro("Centro");
            user.setCidade("Cidade Exemplo");
            user.setUf("EX");

            userRepository.save(user);
        }
    }
}
