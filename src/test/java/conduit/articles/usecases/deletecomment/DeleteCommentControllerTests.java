package conduit.articles.usecases.deletecomment;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class DeleteCommentControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldDeleteCommentSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("john@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}/comments/{id}", "how-to-not-to-ask-for-technical-help", 1)
                        .header("Authorization", "Token " + token))
                .andExpect(status().isOk());
    }

    @Test
    void shouldNotBeAbleToDeleteOthersComment() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}/comments/{id}", "getting-started-with-kubernetes", 2)
                        .header("Authorization", "Token " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldGetUnauthorizedWithoutValidToken() throws Exception {
        mockMvc.perform(delete("/api/articles/{slug}/comments/{id}", "getting-started-with-kubernetes", 2))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldGetNotFoundForNonExistingSlug() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}/comments/{id}", "non-existing-slug", 2)
                        .header("Authorization", "Token " + token))
                .andExpect(status().isNotFound());
    }
}
