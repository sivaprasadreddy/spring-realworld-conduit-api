package conduit.users.usecases.unfollowuser;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.Profile;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UnfollowUserController {
    private final AuthService authService;
    private final UnfollowUserRepo unfollowUser;

    UnfollowUserController(AuthService authService, UnfollowUserRepo unfollowUser) {
        this.authService = authService;
        this.unfollowUser = unfollowUser;
    }

    @DeleteMapping("/api/profiles/{username}/follow")
    ResponseWrapper<Profile> unfollowUser(@PathVariable("username") String username) {
        var profile = unfollowUser.unfollow(authService.getCurrentUserOrThrow(), username);
        return new ResponseWrapper<>("profile", profile);
    }
}
