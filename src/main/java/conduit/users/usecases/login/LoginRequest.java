package conduit.users.usecases.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

record LoginRequest(
        @NotEmpty(message = "{email.required}") @Email(message = "{email.invalid}") String email,
        @NotEmpty(message = "{password.required}") String password) {}
