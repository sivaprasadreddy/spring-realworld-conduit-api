package conduit.users;

import conduit.users.usecases.shared.JwtHelper;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.repo.FindUserRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    private final FindUserRepository findUserRepository;
    private final JwtHelper jwtHelper;

    public AuthService(FindUserRepository findUserRepository, JwtHelper jwtHelper) {
        this.findUserRepository = findUserRepository;
        this.jwtHelper = jwtHelper;
    }

    public Optional<LoginUser> findUserByEmail(String email) {
        return findUserRepository
                .findUserByEmail(email)
                .map(user -> new LoginUser(
                        user.id(), user.email(), user.password(), user.username(), user.bio(), user.image()));
    }

    public void authenticateUser(String token) {
        if (token != null) {
            String email = jwtHelper.getEmailFromToken(token);
            if (email != null) {
                LoginUser loginUser = this.findUserByEmail(email).orElse(null);
                if (loginUser != null && jwtHelper.validateToken(token, loginUser)) {
                    loginUser.setToken(token);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            loginUser, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
    }

    public LoginUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof LoginUser loginUser) {
            return loginUser;
        }
        return null;
    }

    public LoginUser getCurrentUserOrThrow() {
        var loginUser = getCurrentUser();
        if (loginUser == null) {
            throw new AccessDeniedException("Access Denied");
        }
        return loginUser;
    }
}
