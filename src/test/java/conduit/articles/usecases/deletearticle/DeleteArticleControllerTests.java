package conduit.articles.usecases.deletearticle;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class DeleteArticleControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldDeleteArticleSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}", "few-things-i-learned-the-hardway-in-15-years-of-my-career")
                        .header("Authorization", "Token " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotBeAbleToDeleteOthersArticle() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}", "testing-rest-apis-with-postman-newman")
                        .header("Authorization", "Token " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetUnauthorizedWithoutValidToken() throws Exception {
        mockMvc.perform(delete("/api/articles/{slug}", "testing-rest-apis-with-postman-newman"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetNotFoundForNonExistingSlug() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}", "non-existing-slug").header("Authorization", "Token " + token))
                .andExpect(status().isNotFound());
    }
}
