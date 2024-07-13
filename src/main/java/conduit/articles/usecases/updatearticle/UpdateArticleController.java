package conduit.articles.usecases.updatearticle;

import com.fasterxml.jackson.annotation.JsonRootName;
import conduit.articles.usecases.shared.models.Article;
import conduit.shared.ResponseWrapper;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UpdateArticleController {
    private final UpdateArticle updateArticle;
    private final AuthService authService;

    UpdateArticleController(UpdateArticle updateArticle, AuthService authService) {
        this.updateArticle = updateArticle;
        this.authService = authService;
    }

    @PutMapping("/api/articles/{slug}")
    ResponseWrapper<Article> update(@PathVariable String slug, @RequestBody @Valid UpdateArticlePayload payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        UpdateArticleCmd cmd = new UpdateArticleCmd(slug, payload.title(), payload.description(), payload.body());
        var article = updateArticle.execute(loginUser, cmd);
        return new ResponseWrapper<>("article", article);
    }

    @JsonRootName("article")
    record UpdateArticlePayload(
            @NotEmpty(message = "{title.required}") String title,
            @NotEmpty(message = "{description.required}") String description,
            @NotEmpty(message = "{body.required}") String body) {}
}