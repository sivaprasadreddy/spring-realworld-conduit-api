package conduit.users.usecases.login;

import conduit.shared.ResponseWrapper;
import conduit.users.usecases.shared.models.UserResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class LoginController {
    private static final Logger log = LoggerFactory.getLogger(LoginController.class);
    private final UserLogin userLogin;

    LoginController(UserLogin userLogin) {
        this.userLogin = userLogin;
    }

    @PostMapping("/api/users/login")
    ResponseWrapper<UserResponse> login(@RequestBody @Valid LoginRequest req) {
        log.info("Login request for email: {}", req.email());
        var user = userLogin.execute(req.email(), req.password());
        return new ResponseWrapper<>("user", user);
    }
}
