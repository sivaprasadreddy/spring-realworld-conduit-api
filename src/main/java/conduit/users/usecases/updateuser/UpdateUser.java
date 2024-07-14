package conduit.users.usecases.updateuser;

import conduit.users.usecases.shared.JwtHelper;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.UserResponse;
import conduit.users.usecases.shared.repo.FindUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UpdateUser {
    private final FindUserRepository findUserRepository;
    private final UpdateUserRepository updateUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public UpdateUser(
            FindUserRepository findUserRepository,
            UpdateUserRepository updateUserRepository,
            PasswordEncoder passwordEncoder,
            JwtHelper jwtHelper) {
        this.findUserRepository = findUserRepository;
        this.updateUserRepository = updateUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    public UserResponse execute(LoginUser loginUser, UpdateUserCmd cmd) {
        if (cmd.password() != null && !cmd.password().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(cmd.password());
            cmd = new UpdateUserCmd(cmd.email(), cmd.username(), encodedPassword, cmd.bio(), cmd.image());
        }
        var email = updateUserRepository.updateUser(loginUser, cmd);
        var user = findUserRepository.findUserByEmail(email).orElseThrow();
        var token = jwtHelper.generateToken(email);
        return UserResponse.from(user, token);
    }
}
