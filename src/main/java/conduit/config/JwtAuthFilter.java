package conduit.config;

import conduit.ApplicationProperties;
import conduit.users.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final ApplicationProperties properties;

    public JwtAuthFilter(AuthService authService, ApplicationProperties properties) {
        this.authService = authService;
        this.properties = properties;
    }

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String token = this.getToken(request);
        authService.authenticateUser(token);
        chain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest request) {
        String authHeader = request.getHeader(properties.jwt().header());
        String prefix = "Token ";
        if (authHeader != null && authHeader.startsWith(prefix)) {
            return authHeader.substring(prefix.length());
        }
        return null;
    }
}
