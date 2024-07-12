package conduit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    private static final String[] PUBLIC_RESOURCES = {
        "/favicon.ico", "/actuator/health/**", "/actuator/info/**", "/error",
    };
    private final JwtAuthFilter jwtAuthFilter;

    WebSecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.securityMatcher("/api/**");
        http.csrf(CsrfConfigurer::disable);
        http.cors(CorsConfigurer::disable);
        http.authorizeHttpRequests(auth -> auth.requestMatchers(PUBLIC_RESOURCES)
                .permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/login", "/api/users")
                .permitAll()
                .requestMatchers(
                        HttpMethod.GET,
                        "/api/profiles/*",
                        "/api/tags",
                        "/api/articles",
                        "/api/articles/*",
                        "/api/articles/*/comments")
                .permitAll()
                .anyRequest()
                .authenticated());
        http.addFilterBefore(jwtAuthFilter, BasicAuthenticationFilter.class);
        http.exceptionHandling(c -> c.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)));
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
