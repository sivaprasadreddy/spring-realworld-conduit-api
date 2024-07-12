package conduit.users.usecases.login;

import conduit.users.usecases.shared.JwtHelper;
import conduit.users.usecases.shared.models.UserResponse;
import conduit.users.usecases.shared.repo.FindUserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
class UserLogin {
    private final FindUserRepository findUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public UserLogin(FindUserRepository findUserRepository, PasswordEncoder passwordEncoder, JwtHelper jwtHelper) {
        this.findUserRepository = findUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    public UserResponse execute(String email, String password) {
        var user = findUserRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));
        if (!passwordEncoder.matches(password, user.password())) {
            throw new BadCredentialsException("Bad credentials");
        }
        String token = jwtHelper.generateToken(user.email());
        return UserResponse.from(user, token);
    }
}
