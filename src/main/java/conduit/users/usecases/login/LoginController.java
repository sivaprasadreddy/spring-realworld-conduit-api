package conduit.users.usecases.login;

import conduit.users.usecases.shared.models.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "Login User", tags = "User API Endpoints")
    UserResponse login(@RequestBody @Valid LoginRequestPayload req) {
        log.info("Login request for email: {}", req.user().email());
        return userLogin.execute(req.user().email(), req.user().password());
    }

    record LoginRequestPayload(@Valid LoginRequest user) {}
}
