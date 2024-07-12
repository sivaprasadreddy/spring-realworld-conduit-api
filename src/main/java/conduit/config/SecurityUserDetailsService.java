package conduit.config;

import conduit.users.AuthService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityUserDetailsService implements UserDetailsService {
    private final AuthService authService;

    public SecurityUserDetailsService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        return authService
                .findUserByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user found with username " + username));
    }
}
