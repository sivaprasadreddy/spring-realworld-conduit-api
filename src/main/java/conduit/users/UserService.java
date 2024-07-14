package conduit.users;

import conduit.shared.ResourceNotFoundException;
import conduit.users.usecases.shared.models.LoginUser;
import conduit.users.usecases.shared.models.Profile;
import conduit.users.usecases.shared.repo.GetProfileRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final GetProfileRepository getProfileRepository;

    public UserService(GetProfileRepository getProfileRepository) {
        this.getProfileRepository = getProfileRepository;
    }

    public Profile getProfile(LoginUser loginUser, String username) {
        return getProfileRepository
                .findProfile(loginUser, username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }
}
