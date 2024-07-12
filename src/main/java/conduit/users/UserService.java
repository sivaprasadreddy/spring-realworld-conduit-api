package conduit.users;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import conduit.users.usecases.shared.repo.GetProfileRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final GetProfileRepo getProfileRepo;

    public UserService(GetProfileRepo getProfileRepo) {
        this.getProfileRepo = getProfileRepo;
    }

    public Profile getProfile(LoginUser loginUser, String username) {
        return getProfileRepo
                .findProfile(loginUser, username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
}
