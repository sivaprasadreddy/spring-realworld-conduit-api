package conduit.articles.usecases.createarticle;

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
import org.springframework.http.MediaType;

@ArticleModuleTest
class CreateArticleControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldCreateArticleSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(
                        post("/api/articles")
                                .header("Authorization", "Token " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                            {
                              "article": {
                                "title": "How to train your dragon",
                                "description": "Ever wonder how?",
                                "body": "You have to believe",
                                "tagList": ["dragons", "training"]
                              }
                            }
                          """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.article.slug").value("how-to-train-your-dragon"))
                .andExpect(jsonPath("$.article.title").value("How to train your dragon"))
                .andExpect(jsonPath("$.article.description").value("Ever wonder how?"))
                .andExpect(jsonPath("$.article.body").value("You have to believe"))
                .andExpect(jsonPath("$.article.tagList", hasItems("dragons", "training")))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt", nullValue()))
                .andExpect(jsonPath("$.article.favorited").value(false))
                .andExpect(jsonPath("$.article.favoritesCount").value(0))
                .andExpect(jsonPath("$.article.author.username").value("siva"))
                .andExpect(jsonPath("$.article.author.bio").value("I am a Software Architect"))
                .andExpect(jsonPath("$.article.author.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.article.author.following").value(false));
    }
}
