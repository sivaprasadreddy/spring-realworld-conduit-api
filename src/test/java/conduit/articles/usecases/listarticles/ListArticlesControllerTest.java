package conduit.articles.usecases.listarticles;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class ListArticlesControllerTest extends BaseIT {

    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldGetArticlesListSuccessfullyAsGuest() throws Exception {
        mockMvc.perform(get("/api/articles?limit=10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.articles.size()").value(10))
                .andExpect(jsonPath("$.articlesCount").value(14));
    }

    // Add more tests with filters
}
