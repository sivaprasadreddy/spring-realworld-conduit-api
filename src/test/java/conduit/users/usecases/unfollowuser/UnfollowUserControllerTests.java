package conduit.users.usecases.unfollowuser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.users.usecases.UserModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@UserModuleTest
class UnfollowUserControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldUnfollowUserSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("john@gmail.com");
        mockMvc.perform(delete("/api/profiles/{username}/follow", "siva").header("Authorization", "Token " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.username").value("siva"))
                .andExpect(jsonPath("$.profile.bio").value("I am a Software Architect"))
                .andExpect(jsonPath("$.profile.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.profile.following").value(false));
    }

    @Test
    void shouldGetUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/profiles/{username}/follow", "siva")).andExpect(status().isUnauthorized());
    }
}
