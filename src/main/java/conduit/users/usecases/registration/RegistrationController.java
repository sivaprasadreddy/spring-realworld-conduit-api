package conduit.users.usecases.registration;

import conduit.shared.ResponseWrapper;
import conduit.users.usecases.shared.models.UserResponse;
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
    ResponseWrapper<UserResponse> register(@RequestBody @Valid RegistrationCmd cmd) {
        return new ResponseWrapper<>("user", registration.execute(cmd));
    }
}
