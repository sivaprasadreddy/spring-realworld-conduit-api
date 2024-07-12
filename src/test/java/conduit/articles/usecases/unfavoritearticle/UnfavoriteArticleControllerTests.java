package conduit.articles.usecases.unfavoritearticle;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.nullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import conduit.BaseIT;
import conduit.articles.ArticleModuleTest;
import conduit.users.usecases.shared.JwtHelper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@ArticleModuleTest
class UnfavoriteArticleControllerTests extends BaseIT {
    @Autowired
    JwtHelper jwtHelper;

    @Test
    void shouldUnfavoriteArticleSuccessfully() throws Exception {
        String token = jwtHelper.generateToken("john@gmail.com");
        mockMvc.perform(delete("/api/articles/{slug}/favorite", "getting-started-with-kubernetes")
                        .header("Authorization", "Token " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.article.slug").value("getting-started-with-kubernetes"))
                .andExpect(jsonPath("$.article.title").value("Getting Started with Kubernetes"))
                .andExpect(
                        jsonPath("$.article.description")
                                .value(
                                        "In this article we will learn Creating a docker image from a SpringBoot application, Local kubernetes setup using Minikube, Run the SpringBoot app in a Pod, Scaling the application using Deployment, Exposing the Deployment as a Service"))
                .andExpect(
                        jsonPath("$.article.body")
                                .value(
                                        "In this article we will learn Creating a docker image from a SpringBoot application, Local kubernetes setup using Minikube, Run the SpringBoot app in a Pod, Scaling the application using Deployment, Exposing the Deployment as a Service"))
                .andExpect(jsonPath("$.article.tagList", hasItems("kubernetes")))
                .andExpect(jsonPath("$.article.createdAt").isNotEmpty())
                .andExpect(jsonPath("$.article.updatedAt", nullValue()))
                .andExpect(jsonPath("$.article.favorited").value(false))
                .andExpect(jsonPath("$.article.favoritesCount").value(1))
                .andExpect(jsonPath("$.article.author.username").value("admin"))
                .andExpect(jsonPath("$.article.author.bio").value("I am a system administrator"))
                .andExpect(jsonPath("$.article.author.image").value("https://api.realworld.io/images/demo-avatar.png"))
                .andExpect(jsonPath("$.article.author.following").value(true));
    }
}
