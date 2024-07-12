package conduit.articles.usecases.createcomment;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@ArticleModuleTest
class CreateCommentControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldCreateCommentSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(
                        post("/api/articles/{slug}/comments", "testing-rest-apis-with-postman-newman")
                                .header("Authorization", "Token " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                            {
                               "comment": {
                                 "body": "Very nice post"
                               }
                             }
                          """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.comment.id").isNotEmpty())
                .andExpect(jsonPath("$.comment.body").value("Very nice post"))
                .andExpect(jsonPath("$.comment.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.comment.updatedAt", nullValue()))
                .andExpect(jsonPath("$.comment.author.username").value("siva"))
                .andExpect(jsonPath("$.comment.author.bio").value("I am a Software Architect"))
                .andExpect(jsonPath("$.comment.author.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.comment.author.following").value(false));
    }

    @Test
    void shouldGetUnprocessableEntityWithoutMandatoryData() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(
                        post("/api/articles/{slug}/comments", "testing-rest-apis-with-postman-newman")
                                .header("Authorization", "Token " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                            {
                               "comment": {
                                 "body": ""
                               }
                             }
                          """))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors.body", hasItem("Body is required")));
    }

    @Test
    void shouldGetUnauthorizedWithoutToken() throws Exception {
        mockMvc.perform(
                        post("/api/articles/{slug}/comments", "testing-rest-apis-with-postman-newman")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                            {
                               "comment": {
                                 "body": ""
                               }
                             }
                          """))
                .andExpect(status().isUnauthorized());
    }
}
