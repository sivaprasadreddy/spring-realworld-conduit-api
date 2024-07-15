package conduit.articles.usecases.unfavoritearticle;

import conduit.articles.usecases.shared.models.SingleArticleResponse;
import conduit.users.AuthService;
import conduit.users.usecases.shared.models.LoginUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class UnfavoriteArticleController {
    private final UnfavoriteArticle unfavoriteArticle;
    private final AuthService authService;

    UnfavoriteArticleController(UnfavoriteArticle unfavoriteArticle, AuthService authService) {
        this.unfavoriteArticle = unfavoriteArticle;
        this.authService = authService;
    }

    @DeleteMapping("/api/articles/{slug}/favorite")
    @Operation(summary = "Unfavourite Article", tags = "Favorites")
    @SecurityRequirement(name = "Token")
    SingleArticleResponse unfavoriteArticle(@PathVariable String slug) {
        LoginUser loginUser = authService.getCurrentUserOrThrow();
        var article = unfavoriteArticle.execute(loginUser, slug).orElseThrow();
        return new SingleArticleResponse(article);
    }
}
