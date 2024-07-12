package conduit.users.usecases.login;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@JsonRootName("user")
record LoginRequest(
        @NotEmpty(message = "{email.required}") @Email(message = "{email.invalid}") String email,
        @NotEmpty(message = "{password.required}") String password) {}
