package conduit.users.usecases.registration;

import conduit.users.usecases.shared.models.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class RegistrationController {
    private final UserRegistration registration;

    RegistrationController(UserRegistration registration) {
        this.registration = registration;
    }

    @PostMapping("/api/users")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register User", tags = "User and Authentication")
    UserResponse register(@RequestBody @Valid RegistrationCmdPayload cmd) {
        return registration.execute(cmd.user());
    }

    record RegistrationCmdPayload(@Valid RegistrationCmd user) {}
}
