package conduit.users.usecases.updateuser;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.users.usecases.UserModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@UserModuleTest
class UpdateUserControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldUpdateSuccessfullyGivenValidData() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");

        mockMvc.perform(
                        put("/api/user")
                                .header("Authorization", "Token " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "username": "sivanew",
                                        "email": "sivanew@gmail.com",
                                        "password": "secret",
                                        "bio": "new-bio",
                                        "image": "https://api.realworld.io/images/demo-avatar.jpg"
                                    }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("sivanew@gmail.com"))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andExpect(jsonPath("$.user.username").value("sivanew"))
                .andExpect(jsonPath("$.user.bio").value("new-bio"))
                .andExpect(jsonPath("$.user.image").value("https://api.realworld.io/images/demo-avatar.jpg"));
    }

    @ParameterizedTest
    @CsvSource({
        "john,siva@gmail.com",
        "siva,john@gmail.com",
    })
    void shouldFailToUpdateWithConflictingData(String username, String email) throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");

        mockMvc.perform(put("/api/user")
                        .header("Authorization", "Token " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                    "user": {
                                        "username": "%s",
                                        "email": "%s",
                                        "password": "secret",
                                        "bio": "new-bio",
                                        "image": "https://api.realworld.io/images/demo-avatar.jpg"
                                    }
                                }
                                """
                                        .formatted(username, email)))
                .andExpect(status().isUnprocessableEntity());
    }
}
