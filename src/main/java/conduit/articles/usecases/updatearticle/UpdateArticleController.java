package conduit.articles.usecases.updatearticle;

import conduit.articles.usecases.shared.models.SingleArticleResponse;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @Operation(summary = "Update Article", tags = "Article API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    SingleArticleResponse update(@PathVariable String slug, @RequestBody @Valid UpdateArticlePayloadWrapper payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var cmd = new UpdateArticleCmd(
                slug, payload.article.title(), payload.article.description(), payload.article.body());
        var article = updateArticle.execute(loginUser, cmd);
        return new SingleArticleResponse(article);
    }

    record UpdateArticlePayloadWrapper(@Valid UpdateArticlePayload article) {}

    record UpdateArticlePayload(
            @NotEmpty(message = "{title.required}") String title,
            @NotEmpty(message = "{description.required}") String description,
            @NotEmpty(message = "{body.required}") String body) {}
}
