package conduit.users.usecases.getprofile;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.users.usecases.UserModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@UserModuleTest
class GetProfileControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldGetProfileAsGuestUser() throws Exception {
        mockMvc.perform(get("/api/profiles/siva"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.username").value("siva"))
                .andExpect(jsonPath("$.profile.bio").value("I am a Software Architect"))
                .andExpect(jsonPath("$.profile.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.profile.following").value(false));
    }

    @Test
    void shouldGetProfileAsLoginUser() throws Exception {
        String token = jwtHelper.generateToken("prasad@gmail.com");
        mockMvc.perform(get("/api/profiles/siva").header("Authorization", "Token " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.profile.username").value("siva"))
                .andExpect(jsonPath("$.profile.bio").value("I am a Software Architect"))
                .andExpect(jsonPath("$.profile.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.profile.following").value(true));
    }

    @Test
    void shouldGetNotFoundWhenProfileNotFound() throws Exception {
        mockMvc.perform(get("/api/profiles/nonexistingusername")).andExpect(status().isNotFound());
    }
}
