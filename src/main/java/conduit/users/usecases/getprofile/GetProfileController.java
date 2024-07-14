package conduit.users.usecases.getprofile;

import conduit.users.AuthService;
import conduit.users.usecases.shared.models.Profile;
import conduit.users.usecases.shared.models.ProfileResponse;
import conduit.users.usecases.shared.repo.GetProfileRepository;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class GetProfileController {
    private final AuthService authService;
    private final GetProfileRepository getProfile;

    GetProfileController(AuthService authService, GetProfileRepository getProfile) {
        this.authService = authService;
        this.getProfile = getProfile;
    }

    @GetMapping("/api/profiles/{username}")
    @Operation(summary = "Get User Profile", tags = "User API Endpoints")
    ResponseEntity<ProfileResponse> getProfile(@PathVariable String username) {
        Optional<Profile> optionalProfile = getProfile.findProfile(authService.getCurrentUser(), username);
        return optionalProfile
                .map(p -> ResponseEntity.ok(new ProfileResponse(p)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
