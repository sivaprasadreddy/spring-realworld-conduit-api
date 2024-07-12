package conduit.users.usecases.getcurrentuser;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.UserResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetCurrentUserController {
    private final AuthService authService;

    GetCurrentUserController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/api/user")
    ResponseWrapper<UserResponse> getCurrentUser() {
        var loginUser = authService.getCurrentUserOrThrow();
        return new ResponseWrapper<>("user", UserResponse.from(loginUser));
    }
}
