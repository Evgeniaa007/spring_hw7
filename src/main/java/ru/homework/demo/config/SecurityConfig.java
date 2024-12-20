package ru.homework.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    /**
     * метод, который предоставляет доступ к страницам
     * в зависимости от типа пользователя
     * @param http запрос
     * @return возвращает страиницу
     * @throws Exception
     */
    @Bean
    SecurityFilterChain securityFilterChain (HttpSecurity http) throws Exception{
        http.authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/").permitAll()
                .requestMatchers("/public-data").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/private-data").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
            )
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/"));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * предоставление доступа в зависимости от роли пользователя
     * @return пользователь с его правами
     */
    @Bean
    UserDetailsManager inMemoryUserDetailsManager() {
        var user = User.withUsername("user").password("{noop}abc123").roles("USER").build();
        var admin = User.withUsername("admin").password("{noop}def456").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

}
