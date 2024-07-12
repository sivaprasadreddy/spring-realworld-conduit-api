package conduit.users.usecases.getprofile;

import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.Profile;
import conduit.users.usecases.shared.repo.GetProfileRepo;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetProfileController {
    private final AuthService authService;
    private final GetProfileRepo getProfile;

    GetProfileController(AuthService authService, GetProfileRepo getProfile) {
        this.authService = authService;
        this.getProfile = getProfile;
    }

    @GetMapping("/api/profiles/{username}")
    ResponseEntity<ResponseWrapper<Profile>> getProfile(@PathVariable String username) {
        Optional<Profile> optionalProfile = getProfile.findProfile(authService.getCurrentUser(), username);
        return optionalProfile
                .map(p -> ResponseEntity.ok(new ResponseWrapper<>("profile", p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
