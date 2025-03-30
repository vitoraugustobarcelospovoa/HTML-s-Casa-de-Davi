package com.thymeleaf.crud.thcrud.Security;

import com.thymeleaf.crud.thcrud.Repository.UserRepository;
import com.thymeleaf.crud.thcrud.Service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();  // Configuração do PasswordEncoder
  }

  @Bean
  public UserDetailsService userDetailsService(UserRepository userRepository) {
    return new CustomUserDetailsService(userRepository, passwordEncoder());
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .authorizeHttpRequests(auth -> auth
        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/h2-console/**", "/register").permitAll()  // Permitir H2 console sem autenticação
        .anyRequest().authenticated()
      )
      .formLogin(form -> form
        .loginPage("/login")
        .defaultSuccessUrl("/home", true)
        .failureUrl("/login?error=true")
        .permitAll()
      )
      .logout(logout -> logout
        .logoutUrl("/logout")
        .logoutSuccessUrl("/login?logout=true")
        .permitAll()
      );

    // Habilitar o acesso ao H2 Console
    http.headers(headers -> headers
      .xssProtection(xss -> xss.disable()) // Desabilitar proteção XSS (se necessário)
      .contentSecurityPolicy(csp -> csp.policyDirectives(
        "default-src 'self';" +
          "script-src 'self' 'unsafe-inline' 'unsafe-eval' 'unsafe-hashed-content';" +  // Permitir execução de inline scripts e scripts de conteúdo dinâmico
          "style-src 'self' 'unsafe-inline';" +  // Permitir estilos inline
          "font-src 'self';" +  // Permitir fontes de dentro do domínio
          "img-src 'self' data:;" +  // Permitir imagens embutidas e imagens locais
          "connect-src 'self';"  // Permitir conexões locais (se necessário)
      ))
    );

    // Configuração de CSRF (apenas para o console H2)
    http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")); // Ignorar CSRF para o console H2

    return http.build();
  }
}








