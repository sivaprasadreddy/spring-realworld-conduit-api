package conduit.users.usecases.unfollowuser;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.ProfileResponse;
import conduit.users.usecases.shared.repo.GetProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UnfollowUserController {
    private final AuthService authService;
    private final UnfollowUserRepository unfollowUser;
    private final GetProfileRepository getProfileRepository;

    UnfollowUserController(
            AuthService authService, UnfollowUserRepository unfollowUser, GetProfileRepository getProfileRepository) {
        this.authService = authService;
        this.unfollowUser = unfollowUser;
        this.getProfileRepository = getProfileRepository;
    }

    @DeleteMapping("/api/profiles/{username}/follow")
    @Operation(summary = "Unfollow User", tags = "User API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    ProfileResponse unfollowUser(@PathVariable("username") String username) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        unfollowUser.unfollow(loginUser, username);
        var profile = getProfileRepository.findProfile(loginUser, username).orElseThrow();
        return new ProfileResponse(profile);
    }
}
