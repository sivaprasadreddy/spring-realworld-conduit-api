package conduit.users.usecases.unfollowuser;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import conduit.users.usecases.shared.repo.GetProfileRepo;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UnfollowUserController {
    private final AuthService authService;
    private final UnfollowUserRepo unfollowUser;
    private final GetProfileRepo getProfileRepo;

    UnfollowUserController(AuthService authService, UnfollowUserRepo unfollowUser, GetProfileRepo getProfileRepo) {
        this.authService = authService;
        this.unfollowUser = unfollowUser;
        this.getProfileRepo = getProfileRepo;
    }

    @DeleteMapping("/api/profiles/{username}/follow")
    ResponseWrapper<Profile> unfollowUser(@PathVariable("username") String username) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        unfollowUser.unfollow(loginUser, username);
        var profile = getProfileRepo.findProfile(loginUser, username).orElseThrow();
        return new ResponseWrapper<>("profile", profile);
    }
}
