package conduit.articles.usecases.createarticle;

import conduit.articles.usecases.shared.models.SingleArticleResponse;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CreateArticleController {
    private final CreateArticle createArticle;
    private final AuthService authService;

    CreateArticleController(CreateArticle createArticle, AuthService authService) {
        this.createArticle = createArticle;
        this.authService = authService;
    }

    @PostMapping("/api/articles")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create Article", tags = "Article API Endpoints")
    @SecurityRequirement(name = "JwtToken")
    SingleArticleResponse create(@RequestBody @Valid CreateArticleCmdPayload payload) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = createArticle.execute(loginUser, payload.article());
        return new SingleArticleResponse(article);
    }

    record CreateArticleCmdPayload(@Valid CreateArticleCmd article) {}
}
