package conduit.articles.usecases.updatearticle;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

@ArticleModuleTest
class UpdateArticleControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldUpdateArticleSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("siva@gmail.com");
        mockMvc.perform(
                        put("/api/articles/{slug}", "testing-rest-apis-with-postman-newman")
                                .header("Authorization", "Token " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(
                                        """
                            {
                              "article": {
                                "title": "REST APIs testing using Postman and Newman",
                                "description": "Learn how to test REST APIs using Postman and Newman",
                                "body": "Learn how to test REST APIs using Postman and Newman"
                              }
                            }
                          """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.slug").value("rest-apis-testing-using-postman-and-newman"))
                .andExpect(jsonPath("$.article.title").value("REST APIs testing using Postman and Newman"))
                .andExpect(
                        jsonPath("$.article.description").value("Learn how to test REST APIs using Postman and Newman"))
                .andExpect(jsonPath("$.article.body").value("Learn how to test REST APIs using Postman and Newman"))
                .andExpect(jsonPath("$.article.tagList", hasItems("testing")))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt").isNotEmpty())
                .andExpect(jsonPath("$.article.favorited").value(false))
                .andExpect(jsonPath("$.article.favoritesCount").value(1))
                .andExpect(jsonPath("$.article.author.username").value("admin"))
                .andExpect(jsonPath("$.article.author.bio").value("I am a system administrator"))
                .andExpect(jsonPath("$.article.author.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.article.author.following").value(false));
    }
}
