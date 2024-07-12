package conduit.articles.usecases.gettags;

import static org.hamcrest.Matchers.hasItems;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import org.junit.jupiter.api.Test;

@ArticleModuleTest
class GetTagsTest extends BaseIT {
    @Test
    void shouldGetTags() throws Exception {
        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath(
                        "$.tags",
                        hasItems(
                                "java",
                                "spring-boot",
                                "jpa",
                                "jooq",
                                "architecture",
                                "golang",
                                "general",
                                "testing",
                                "nodejs",
                                "kubernetes")));
    }
}
