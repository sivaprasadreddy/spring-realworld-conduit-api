package conduit.users.usecases.login;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.users.usecases.UserModuleTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.http.MediaType;

@UserModuleTest
class LoginControllerTests extends BaseIT {

    @Test
    void shouldLoginSuccessfullyGivenValidCredentials() throws Exception {
        mockMvc.perform(
                        post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "email": "siva@gmail.com",
                                        "password": "password"
                                    }
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").value("siva@gmail.com"))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andExpect(jsonPath("$.user.username").value("siva"))
                .andExpect(jsonPath("$.user.bio").value("I am a Software Architect"))
                .andExpect(jsonPath("$.user.image").value("https://api.realworld.io/images/demo-avatar.png"));
    }

    @ParameterizedTest
    @CsvSource({"siva@gmail.com,wrong", "nonexissting@mail.com,password", "nonexissting@mail.com,wrong"})
    void shouldGetUnauthorizedGivenInvalidCredentials(String email, String password) throws Exception {
        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(
                                """
                                {
                                    "user": {
                                        "email": "%s",
                                        "password": "%s"
                                    }
                                }
                                """
                                        .formatted(email, password)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetUnprocessableEntityWhenMandatoryInputsMissing() throws Exception {
        mockMvc.perform(
                        post("/api/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "email": "",
                                        "password": ""
                                    }
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.body", hasItem("Email is required")))
                .andExpect(jsonPath("$.errors.body", hasItem("Password is required")));
    }
}
