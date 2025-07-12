package diegosWafles.infraestructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import diegosWafles.domain.port.output.UserRepositoryPort;
import diegosWafles.infraestructure.security.JwtAuthTokenFilter;
import diegosWafles.infraestructure.security.JwtUtils;

@Configuration
public class SecurityConfig {

    private final JwtUtils jwtUtils;
    private final UserRepositoryPort userRepository;

    public SecurityConfig(JwtUtils jwtUtils, UserRepositoryPort userRepository) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
    }

    @Bean
    public JwtAuthTokenFilter authenticationJwtTokenFilter() {
        return new JwtAuthTokenFilter(jwtUtils, userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/register").hasAuthority("Administrador")
                        .requestMatchers("/api/users/me/password").hasAuthority("Vendedor")
                        .requestMatchers("/api/users/**").hasAuthority("Administrador")
                        .requestMatchers("/api/roles/**").hasAuthority("Administrador")
                        .requestMatchers("/api/providers/**").hasAuthority("Administrador")
                        .requestMatchers("/api/ingredients/**").hasAuthority("Administrador")
                        .requestMatchers("/api/recipes/**").hasAuthority("Administrador")
                        .requestMatchers("/api/products/**").hasAuthority("Administrador")
                        .requestMatchers("/api/test/**").hasAuthority("Administrador")
                        .requestMatchers("/api/orders/**").hasAuthority("Administrador")
                        .anyRequest().authenticated()
                );

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
