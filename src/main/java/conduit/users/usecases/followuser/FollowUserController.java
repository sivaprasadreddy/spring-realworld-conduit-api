package conduit.users.usecases.followuser;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import conduit.users.usecases.shared.repo.GetProfileRepo;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class FollowUserController {
    private final AuthService authService;
    private final FollowUserRepo followUser;
    private final GetProfileRepo getProfileRepo;

    FollowUserController(AuthService authService, FollowUserRepo followUser, GetProfileRepo getProfileRepo) {
        this.authService = authService;
        this.followUser = followUser;
        this.getProfileRepo = getProfileRepo;
    }

    @PostMapping("/api/profiles/{username}/follow")
    ResponseWrapper<Profile> followUser(@PathVariable("username") String username) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        followUser.follow(loginUser, username);
        var profile = getProfileRepo.findProfile(loginUser, username).orElseThrow();
        return new ResponseWrapper<>("profile", profile);
    }
}
