package conduit.users.usecases.registration;

import conduit.shared.BadRequestException;
import conduit.users.usecases.shared.JwtHelper;
import conduit.users.usecases.shared.models.User;
import conduit.users.usecases.shared.models.UserResponse;
import conduit.users.usecases.shared.repo.FindUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
class UserRegistration {
    private final FindUserRepository findUserRepository;
    private final UserRegistrationRepository registrationRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtHelper jwtHelper;

    public UserRegistration(
            FindUserRepository findUserRepository,
            UserRegistrationRepository registrationRepo,
            PasswordEncoder passwordEncoder,
            JwtHelper jwtHelper) {
        this.findUserRepository = findUserRepository;
        this.registrationRepo = registrationRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtHelper = jwtHelper;
    }

    public UserResponse execute(RegistrationCmd cmd) {
        if (findUserRepository.emailExists(cmd.email())) {
            throw new BadRequestException("User with email " + cmd.email() + " already exists");
        }
        if (findUserRepository.usernameExists(cmd.username())) {
            throw new BadRequestException("User with username " + cmd.username() + " already exists");
        }
        var encPwd = passwordEncoder.encode(cmd.password());
        User user = new User(null, cmd.email(), encPwd, cmd.username(), null, null);
        var savedUser = registrationRepo.createUser(user);
        var token = jwtHelper.generateToken(user.email());
        return UserResponse.from(savedUser, token);
    }
}
