package conduit.users.usecases.updateuser;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UpdateUserController {
    private final UpdateUser updateUser;
    private final AuthService authService;

    UpdateUserController(UpdateUser updateUser, AuthService authService) {
        this.updateUser = updateUser;
        this.authService = authService;
    }

    @PutMapping("/api/user")
    @Operation(summary = "Update Login User Details", tags = "User API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    UserResponse update(@RequestBody @Valid UpdateUserPayload payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        return updateUser.execute(loginUser, payload.user());
    }

    record UpdateUserPayload(@Valid UpdateUserCmd user) {}
}
