package conduit.users.usecases.registration;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.users.usecases.UserModuleTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

@UserModuleTest
class RegistrationControllerTests extends BaseIT {

    @Test
    void shouldRegisterSuccessfullyGivenValidData() throws Exception {
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "username": "Jacob",
                                        "email": "jake@jake.jake",
                                        "password": "jakejake"
                                    }
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.user.email").value("jake@jake.jake"))
                .andExpect(jsonPath("$.user.token").isNotEmpty())
                .andExpect(jsonPath("$.user.username").value("Jacob"))
                .andExpect(jsonPath("$.user.bio", nullValue()))
                .andExpect(jsonPath("$.user.image", nullValue()));
    }

    @Test
    void shouldFailToRegisterWithExistingEmail() throws Exception {
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "username": "SivaPrasad",
                                        "email": "siva@gmail.com",
                                        "password": "secret"
                                    }
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.body", hasItem("User with email siva@gmail.com already exists")));
    }

    @Test
    void shouldFailToRegisterWithExistingUsername() throws Exception {
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "username": "siva",
                                        "email": "sivaprasad@gmail.com",
                                        "password": "secret"
                                    }
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.body", hasItem("User with username siva already exists")));
    }

    @Test
    void shouldGetUnprocessableEntityWhenMandatoryInputsMissing() throws Exception {
        mockMvc.perform(
                        post("/api/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                                {
                                    "user": {
                                        "username": "",
                                        "email": "",
                                        "password": ""
                                    }
                                }
                                """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.body", hasItem("Username is required")))
                .andExpect(jsonPath("$.errors.body", hasItem("Email is required")))
                .andExpect(jsonPath("$.errors.body", hasItem("Password is required")));
    }
}
