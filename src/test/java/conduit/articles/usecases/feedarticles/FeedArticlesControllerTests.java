package conduit.articles.usecases.feedarticles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class FeedArticlesControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldGetFeedArticlesSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("john@gmail.com");
        mockMvc.perform(get("/api/articles/feed?limit=10").header("Authorization", "Token " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articles.size()").value(10))
                .andExpect(jsonPath("$.articlesCount").value(12));
    }
}
