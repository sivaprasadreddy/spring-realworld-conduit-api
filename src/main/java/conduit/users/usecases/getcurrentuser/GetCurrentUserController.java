package conduit.users.usecases.getcurrentuser;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetCurrentUserController {
    private final AuthService authService;

    GetCurrentUserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/api/user")
    @Operation(summary = "Get current user", tags = "User and Authentication")
    @SecurityRequirement(name = "Token")
    UserResponse getCurrentUser() {
        var loginUser = authService.getCurrentUserOrThrow();
        return UserResponse.from(loginUser);
    }
}
