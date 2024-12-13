package ch.pa5.greenfit.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(requests -> requests.anyRequest().authenticated())
//        .exceptionHandling(
//            exception ->
//                exception.authenticationEntryPoint(
//                    new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
        .oauth2Login(Customizer.withDefaults());
    return http.build();
  }
}
