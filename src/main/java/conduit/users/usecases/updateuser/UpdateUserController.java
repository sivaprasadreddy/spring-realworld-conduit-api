package conduit.users.usecases.updateuser;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.UserResponse;
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
    ResponseWrapper<UserResponse> update(@RequestBody @Valid UpdateUserCmd cmd) {
        var user = updateUser.execute(authService.getCurrentUserOrThrow(), cmd);
        return new ResponseWrapper<>("user", user);
    }
}
