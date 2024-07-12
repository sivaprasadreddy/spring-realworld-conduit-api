package conduit.articles.usecases.favoritearticle;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class FavoriteArticleControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldFavoriteArticleSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("john@gmail.com");
        mockMvc.perform(post("/api/articles/{slug}/favorite", "why-springboot")
                        .header("Authorization", "Token " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.article.slug").value("why-springboot"))
                .andExpect(jsonPath("$.article.title").value("Why SpringBoot?"))
                .andExpect(jsonPath("$.article.description").value("Why SpringBoot?"))
                .andExpect(jsonPath("$.article.body").value("https://sivalabs.in/why-springboot"))
                .andExpect(jsonPath("$.article.tagList", hasItems("java", "spring-boot")))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt", nullValue()))
                .andExpect(jsonPath("$.article.favorited").value(true))
                .andExpect(jsonPath("$.article.favoritesCount").value(1))
                .andExpect(jsonPath("$.article.author.username").value("admin"))
                .andExpect(jsonPath("$.article.author.bio").value("I am a system administrator"))
                .andExpect(jsonPath("$.article.author.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.article.author.following").value(true));
    }
}
