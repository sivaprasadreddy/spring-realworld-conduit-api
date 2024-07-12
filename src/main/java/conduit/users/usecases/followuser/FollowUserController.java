package conduit.users.usecases.followuser;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.Profile;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FollowUserController {
    private final AuthService authService;
    private final FollowUserRepo followUser;

    FollowUserController(AuthService authService, FollowUserRepo followUser) {
        this.authService = authService;
        this.followUser = followUser;
    }

    @PostMapping("/api/profiles/{username}/follow")
    ResponseWrapper<Profile> followUser(@PathVariable("username") String username) {
        var profile = followUser.follow(authService.getCurrentUserOrThrow(), username);
        return new ResponseWrapper<>("profile", profile);
    }
}
