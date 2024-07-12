package conduit.articles.usecases.getcomments;

import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class GetCommentsControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldGetCommentsAsGuest() throws Exception {
        mockMvc.perform(get("/api/articles/{slug}/comments", "getting-started-with-kubernetes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comments[0].id").isNotEmpty())
                .andExpect(jsonPath("$.comments[0].body").value("Thanks for sharing"))
                .andExpect(jsonPath("$.comments[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.comments[0].updatedAt", nullValue()))
                .andExpect(jsonPath("$.comments[0].author.username").value("james"))
                .andExpect(jsonPath("$.comments[0].author.bio").value("I am a Java Developer"))
                .andExpect(
                        jsonPath("$.comments[0].author.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.comments[0].author.following").value(false));
    }
}
