package conduit.users.usecases.registration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

record RegistrationCmd(
        @NotEmpty(message = "{username.required}") String username,
        @NotEmpty(message = "{email.required}") @Email(message = "{email.invalid}") String email,
        @NotEmpty(message = "{password.required}") String password) {}
