package backend.security;

import backend.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtFilter;
    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter, CustomUserDetailsService userDetailsService) {
        this.jwtFilter = jwtFilter;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // --- Endpoints públicos ---
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/authentication/**").permitAll()
                        .requestMatchers("/products/approved", "/uploads/**").permitAll()

                        // --- Rutas USUARIO COMÚN ---
                        .requestMatchers("/products/create",
                                "/products/update/**",
                                "/products/user/**",
                                "/products/edit/**",
                                "/products/exclude/**",
                                "/products/delete/**",
                                "/products/raiting/**")
                        .hasAuthority("USUARIO COMÚN")

                        // --- Rutas MODERADOR ---
                        .requestMatchers("/products/pending",
                                "/products/approve/**",
                                "/products/reject/**",
                                "/sanctions/all-sanctions",
                                "/sanctions/apply-sanction",
                                "/sanctions/**")
                        .hasAuthority("MODERADOR")

                        // --- Rutas ADMINISTRADOR ---
                        .requestMatchers("/notification/admin-notifications",
                                "/notification/my-notifications")
                        .hasAuthority("ADMINISTRADOR")

                        // --- Todo lo demás requiere autenticación ---
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }
}