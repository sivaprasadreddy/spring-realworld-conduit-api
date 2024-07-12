package conduit.users.usecases.registration;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@JsonRootName("user")
record RegistrationCmd(
        @NotEmpty(message = "{username.required}") String username,
        @NotEmpty(message = "{email.required}") @Email(message = "{email.invalid}") String email,
        @NotEmpty(message = "{password.required}") String password) {}
